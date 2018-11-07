package com.csjbot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import sunnykid.character.UtilCharacter;
import sunnykid.gui.Location;
import sunnykid.text.property.ParserFactory;
import sunnykid.text.property.PropertyParser;
import sunnykid.util.CollectionUtils;

/**
 *
 * @Title: LoadingWin.java
 * @Project: csjbot-code-generator
 * @Package: com.csjbot
 * @Description: 程序启动窗口
 * @Company: 苏州穿山甲机器人股份有限公司
 * @author: 钟磊
 * @date: 2018年3月26日 下午2:19:05
 * @version: V1.0.0
 */
public class LoadingWin implements ActionListener, ChangeListener {

	/**
	 * 杂项配置存储对象
	 */
	static Map<String, Serializable> configMap = new HashMap<String, Serializable>();

	static {
		Constants.GENERATOR_ROOT_PATH = System.getProperty("user.dir");
		System.setProperty(Constants.GENERATOR_ROOT_KEY, Constants.GENERATOR_ROOT_PATH);
		PropertyParser parser = ParserFactory.getParserInstance(Constants.GENERATOR_ROOT_PATH
				+ UtilCharacter.FILE_SEPARATOR + Constants.OTHERS_CONFIG_FILE);
		configMap.put(
				Constants.PREANALYSIS_DATABASE,
				Boolean.valueOf(Boolean.TRUE.toString().equalsIgnoreCase(
						parser.getProperty(Constants.PREANALYSIS_DATABASE))));
		configMap.put(
				Constants.SHOW_WELCOME_WINDOW,
				Boolean.valueOf(!Boolean.FALSE.toString().equalsIgnoreCase(
						parser.getProperty(Constants.SHOW_WELCOME_WINDOW))));
		String threads = parser.getProperty(Constants.PREANALYSIS_THREADS);
		configMap.put(Constants.PREANALYSIS_THREADS,
				Integer.valueOf((threads != null && threads.matches("^\\d+$")) ? threads : "1"));
		String author = parser.getProperty(Constants.AUTHOR);
		String company = parser.getProperty(Constants.COMPANY);
		String packageRoot = parser.getProperty(Constants.PACKAGE_ROOT);
		String projectName = parser.getProperty(Constants.PROJECT_NAME);
		String projectVersion = parser.getProperty(Constants.PROJECT_VERSION);
		
		configMap.put(Constants.AUTHOR, author == null ? UtilCharacter.EMPTY : author);
		configMap.put(Constants.COMPANY, company == null ? UtilCharacter.EMPTY : company);
		configMap.put(Constants.PACKAGE_ROOT, packageRoot == null ? UtilCharacter.EMPTY : packageRoot);
		configMap.put(Constants.PROJECT_NAME, projectName == null ? UtilCharacter.EMPTY : projectName);
		configMap.put(Constants.PROJECT_VERSION, projectVersion == null ? UtilCharacter.EMPTY : projectVersion);
	}

	/**
	 * 启动应用程序
	 * @param args
	 * @author 钟磊
	 * @date 2018年3月29日 下午3:21:41
	 */
	public static void main(String[] args) {
		if(((Boolean)configMap.get(Constants.PREANALYSIS_DATABASE)).booleanValue()) {
			Set<String> tableNames = DBUtils.getTableNames();
			int tableCount = tableNames.size();

			int threadCount = ((Integer)configMap.get(Constants.PREANALYSIS_THREADS)).intValue();
			if(threadCount > tableCount) {
				threadCount = tableCount;
			}
			TableAnalyzer[] analyzers = new TableAnalyzer[threadCount];// 定义线程组
			int tCountPreThread = tableCount / threadCount;
			String[] tns = CollectionUtils.toArray(tableNames);

			for(int i = 0; i < analyzers.length; i++) {// 为每个线程分配任务
				analyzers[i] = new TableAnalyzer();
				int start = i * tCountPreThread;
				int end = (i == analyzers.length - 0x1) ? tns.length : (i + 0x1) * tCountPreThread;
				for(int k = start; k < end; k++) {// 将需要分析的数据表名作为任务加入到线程
					analyzers[i].getTableNames().add(tns[k]);
				}
				Thread tableAnalyzer = new Thread(analyzers[i]);// 分配完毕！准备启动！
				tableAnalyzer.start();// 启动数据库分析线程
			}
		}
		LoadingWin window = new LoadingWin();
		if(((Boolean)configMap.get(Constants.SHOW_WELCOME_WINDOW)).booleanValue()) {
			window.frame.setVisible(true);// 启动主界面
			window.timer.start();// 启动进度条
		} else {
			window.loadMainFrame();
		}
	}

	private JFrame frame;
	private JProgressBar progressBar;
	private Timer timer;
	private JLabel textLabel;

	/**
	 * 构造函数
	 */
	public LoadingWin() {
		CommonUtils.initGlobalFont();
		this.initialize();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.timer) {
			int v = this.progressBar.getValue();
			if(v < 100) {
				this.progressBar.setValue(++v);
			} else {
				this.timer.stop();
				this.loadMainFrame();
			}
		}
	}

	/**
	 * 初始化应用程序界面
	 */
	private void initialize() {
		this.frame = new JFrame("\u6B22\u8FCE\u4F7F\u7528");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setUndecorated(true);
		this.frame.setSize(500, 370);
		Location.setCenter(this.frame);

		JPanel imgPanel = new ImgPanel(Toolkit.getDefaultToolkit().createImage(
				Constants.GENERATOR_ROOT_PATH + UtilCharacter.FILE_SEPARATOR + "img" + UtilCharacter.FILE_SEPARATOR
						+ "loading_bg_black.jpg"));
		imgPanel.setPreferredSize(new Dimension(500, 330));

		JPanel textPanel = new JPanel();
		textPanel.setLayout(null);
		textPanel.setPreferredSize(new Dimension(500, 20));

		this.textLabel = new JLabel();
		this.textLabel.setBounds(170, 0, 500, 20);
		this.textLabel.setOpaque(true);
		this.textLabel.setBackground(Color.BLACK);
		this.textLabel.setForeground(Color.WHITE);
		textPanel.add(this.textLabel);
		textPanel.setBackground(Color.BLACK);

		this.progressBar = new JProgressBar();
		this.progressBar.setOrientation(SwingConstants.HORIZONTAL);
		this.progressBar.setPreferredSize(new Dimension(500, 20));
		this.progressBar.setMaximum(100);
		this.progressBar.setMinimum(0);
		this.progressBar.setValue(0);
		this.progressBar.setStringPainted(true);
		this.progressBar.addChangeListener(this);

		Container contentPane = this.frame.getContentPane();
		contentPane.add(imgPanel, BorderLayout.NORTH);
		contentPane.add(textPanel, BorderLayout.CENTER);
		contentPane.add(this.progressBar, BorderLayout.SOUTH);

		this.timer = new Timer(50, this);
	}

	public void loadMainFrame() {
		this.frame.dispose();
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		MainWin mainWin = new MainWin();
		mainWin.frame.setVisible(true);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == this.progressBar) {
			int v = this.progressBar.getValue();
			String statusText = null;
			if(v <= 25) {
				statusText = "\u6B63\u5728\u521D\u59CB\u5316\u8FD0\u884C\u73AF\u5883\uFF0C\u8BF7\u7A0D\u5019";
			} else if(v <= 50) {
				statusText = "\u6B63\u5728\u521D\u59CB\u5316\u6570\u636E\u5E93\u64CD\u4F5C\u7EC4\u4EF6\uFF0C\u8BF7\u7A0D\u5019";
			} else if(v <= 75) {
				statusText = "\u6B63\u5728\u8F7D\u5165\u5927\u578B\u7B97\u6CD5\uFF0C\u8BF7\u7A0D\u5019";
			} else {
				statusText = "\u6B63\u5728\u6784\u9020\u56FE\u5F62\u754C\u9762\uFF0C\u8BF7\u7A0D\u5019";
			}
			int x = v % 7;
			for(int i = 0; i < x; i++) {
				statusText += UtilCharacter.DOT;
			}
			this.textLabel.setText(statusText);
		}
	}
}
