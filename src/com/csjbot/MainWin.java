package com.csjbot;

import static com.csjbot.LoadingWin.configMap;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import sunnykid.character.UtilCharacter;
import sunnykid.gui.Location;
import sunnykid.lang.SunnykidDate;
import sunnykid.text.SunnykidText;
import sunnykid.util.ArrayUtils;
import sunnykid.util.MathUtils;
import sunnykid.web.log4j.LoggerFactory;
import sunnykid.web.log4j.SunnykidLogger;

/**
 *
 * @Title: MainWin.java
 * @Project: csjbot-code-generator
 * @Package: com.csjbot
 * @Description: 系统主界面窗口
 * @Company: 苏州穿山甲机器人股份有限公司
 * @author: 钟磊
 * @date: 2018年3月27日 下午4:58:26
 * @version: V1.0.0
 */
public class MainWin {

	static SunnykidLogger logger = LoggerFactory.getLogger(MainWin.class);
	protected JFrame frame;
	protected JTextField packageNameText;
	protected JTextField classNamePrefixText;
	protected JTextField tableDescText;
	protected JCheckBox globalChkBox;
	protected JPanel chkFormPanel;
	protected JPanel txtFormPanel;
	protected JComboBox tableNameBox;

	protected JTextField srvProjRootText;
	protected JButton srvRootChooserBtn;

	protected JTextField pageProjRootText;
	protected JButton pageRootChooserBtn;

	protected JButton generateBtn;

	/**
	 * Create the application.
	 */
	public MainWin() {
		CommonUtils.initGlobalFont();
		this.initialize();
	}

	/**
	 * 初始化目录选择按钮
	 *
	 * @author: 钟磊
	 * @date: 2018年3月28日 上午10:51:05
	 */
	private void initDirChooserBtn() {
		this.srvRootChooserBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String srvProjRoot = CommonUtils.openProjRootHistory(Constants.SRV_PROJECT_ROOT_HISTORY);
				if(srvProjRoot != null) {
					MainWin.this.srvProjRootText.setText(srvProjRoot);
				}
			}
		});

		this.pageRootChooserBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String pageProjRoot = CommonUtils.openProjRootHistory(Constants.PAGE_PROJECT_ROOT_HISTORY);
				if(pageProjRoot != null) {
					MainWin.this.pageProjRootText.setText(pageProjRoot);
				}
			}
		});
	}

	/**
	 * 初始化目录选择按钮
	 *
	 * @author: 钟磊
	 * @date: 2018年3月28日 上午10:51:05
	 */
	private void initGenerateBtn() {
		this.generateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String tableName = MainWin.this.tableNameBox.getSelectedItem().toString();
				String tableDesc = MainWin.this.tableDescText.getText();
				String packageName = MainWin.this.packageNameText.getText();
				String classNamePrefix = MainWin.this.classNamePrefixText.getText();
				String srvProjRoot = MainWin.this.srvProjRootText.getText();
				String pageProjRoot = MainWin.this.pageProjRootText.getText();
				Component[] txts = MainWin.this.txtFormPanel.getComponents();

				Component[] chks = MainWin.this.chkFormPanel.getComponents();
				int srvCount = 0x0, pageCount = 0x0;
				for(Component chk : chks) {
					String cn = chk.getName();
					if(cn == null) {
						continue;
					}
					if(cn.matches("^s.+k$") && ((JCheckBox)chk).isSelected()) {
						srvCount++;
					}
					if(cn.matches("^p.+k$") && ((JCheckBox)chk).isSelected()) {
						pageCount++;
					}
				}
				for(Component txt : txts) {
					String cn = txt.getName();
					if(((cn.matches("^r.+t$") && ((JTextField)txt).getText().trim().length() == 0x0) || (cn
							.matches("^r.+o$") && ((JComboBox)txt).getSelectedIndex() == 0x0))
							|| ((((cn.matches("^u.+srv.+t$")) && (srvCount > 0x0)) || ((cn.matches("^u.+page.+t$")) && (pageCount > 0x0))) && (((JTextField)txt)
									.getText().trim().length() == 0x0))) {
						String seperator = "_";
						String[] ts = cn.split(seperator);
						ts[0x2] = "label";
						String labelName = ArrayUtils.toString(ts, seperator);
						JLabel label = (JLabel)CommonUtils.getComponentByName(MainWin.this.txtFormPanel, labelName);
						if(label != null) {
							JOptionPane.showMessageDialog(null, label.getText().replace("：", UtilCharacter.EMPTY)
									+ Constants.REQUIRED, Constants.WARN, JOptionPane.WARNING_MESSAGE);
						}
						return;
					}
				}
				Component[] coms = MainWin.this.chkFormPanel.getComponents();
				int[] result = new int[]{0x0, 0x0, 0x0};
				for(Component com : coms) {
					if((com instanceof JCheckBox) && ((JCheckBox)com).isSelected()
							&& (com != MainWin.this.globalChkBox)) {
						Map<String, Object> config = new HashMap<String, Object>();
						config.put("tableName", tableName);
						config.put("lowerPrefix", packageName);
						config.put("upperPrefix", classNamePrefix);
						config.put("tableDesc", tableDesc);
						config.put("currentDate", (new SunnykidDate()).getChineseDateTime());
						config.put("author", configMap.get(Constants.AUTHOR));
						config.put("company", configMap.get(Constants.COMPANY));
						config.put("packageRoot", configMap.get(Constants.PACKAGE_ROOT));
						config.put("projectName", configMap.get(Constants.PROJECT_NAME));
						config.put("projectVersion", configMap.get(Constants.PROJECT_VERSION));
						config.put("modPkg", tableName.startsWith("sys_") ? "sys" : packageName);
						String suffix = ((JCheckBox)com).getText();
						int x = CodeGenerator.generateCodeFile(config, srvProjRoot, pageProjRoot, suffix);
						result[x + 0x1] = result[x + 0x1] + 0x1;
						if(suffix.equalsIgnoreCase("service") || suffix.equalsIgnoreCase("manager")) {
							int y = CodeGenerator.generateCodeFile(config, srvProjRoot, pageProjRoot, suffix + ".Impl");
							result[y + 0x1] = result[y + 0x1] + 0x1;
						}
					}
				}
				String resultMsg = String.format("总计%d个\n成功%d个\n失败%d个\n忽略%d个", Long.valueOf(MathUtils.sumInt(result)),
						Integer.valueOf(result[2]), Integer.valueOf(result[0]), Integer.valueOf(result[1]));
				JOptionPane.showMessageDialog(null, Constants.GENERATE_FILES_COMPLETE_MSG + UtilCharacter.WRAP
						+ resultMsg, Constants.INFO, JOptionPane.INFORMATION_MESSAGE);
				MainWin.logger.info(Constants.GENERATE_FILES_COMPLETE_MSG
						+ resultMsg.replace(UtilCharacter.WRAP, UtilCharacter.COMMA));
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Font commonFont = new Font("华文细黑", Font.PLAIN, 13);
		this.frame = new JFrame();
		this.frame.setTitle("CSJBOT代码生成工具");

		this.frame.setSize(500, 450);
		this.frame.setResizable(false);
		Location.setCenter(this.frame);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container frameContainer = this.frame.getContentPane();
		frameContainer.setLayout(null);

		JLabel titleLabel = new JLabel("穿山甲《机器人管理平台》代码生成器");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("华文细黑", Font.BOLD, 17));
		titleLabel.setBounds(0, 0, 495, 40);
		frameContainer.add(titleLabel);

		this.generateBtn = new JButton("生成代码");
		// btnOK.setFont(commonFont);
		this.generateBtn.setBounds(10, 385, 475, 25);
		frameContainer.add(this.generateBtn);

		this.txtFormPanel = new JPanel();
		frameContainer.add(this.txtFormPanel);
		this.txtFormPanel.setBounds(10, 45, 475, 255);
		this.txtFormPanel.setBorder(BorderFactory.createTitledBorder("数据信息"));
		this.txtFormPanel.setLayout(null);

		JLabel tableNameLabel = new JLabel("数据表：", SwingConstants.RIGHT);
		tableNameLabel.setName("required_tableName_label");
		tableNameLabel.setBounds(25, 20, 100, 15);
		this.txtFormPanel.add(tableNameLabel);

		JLabel tableDescLabel = new JLabel("表注释：", SwingConstants.RIGHT);
		tableDescLabel.setName("required_tableDesc_label");
		tableDescLabel.setBounds(25, 60, 100, 15);
		this.txtFormPanel.add(tableDescLabel);

		JLabel packageNameLabel = new JLabel("包名：", SwingConstants.RIGHT);
		packageNameLabel.setName("required_packageName_label");
		packageNameLabel.setBounds(25, 100, 100, 15);
		this.txtFormPanel.add(packageNameLabel);

		JLabel classNamePrefixLabel = new JLabel("类名前缀：", SwingConstants.RIGHT);
		classNamePrefixLabel.setName("required_classNamePrefix_label");
		classNamePrefixLabel.setBounds(25, 140, 100, 15);
		this.txtFormPanel.add(classNamePrefixLabel);

		JLabel srvProjRootLabel = new JLabel("服务项目根路径：", SwingConstants.RIGHT);
		srvProjRootLabel.setName("unrequired_srvProjRoot_label");
		srvProjRootLabel.setBounds(25, 180, 100, 15);
		this.txtFormPanel.add(srvProjRootLabel);

		JLabel pageProjRootLabel = new JLabel("页面项目根路径：", SwingConstants.RIGHT);
		pageProjRootLabel.setName("unrequired_pageProjRoot_label");
		pageProjRootLabel.setBounds(25, 220, 100, 15);
		this.txtFormPanel.add(pageProjRootLabel);

		this.tableNameBox = new JComboBox(new Object[]{"--数据表--"});
		this.tableNameBox.setName("required_tableName_combo");
		this.tableNameBox.setBounds(130, 20, 315, 20);
		this.txtFormPanel.add(this.tableNameBox);

		this.tableDescText = new JTextField();
		this.tableDescText.setName("required_tableDesc_text");
		this.tableDescText.setBounds(130, 60, 315, 20);
		this.txtFormPanel.add(this.tableDescText);

		this.packageNameText = new JTextField();
		this.packageNameText.setName("required_packageName_text");
		this.packageNameText.setBounds(130, 100, 315, 20);
		this.txtFormPanel.add(this.packageNameText);
		this.packageNameText.setColumns(10);

		this.classNamePrefixText = new JTextField();
		this.classNamePrefixText.setName("required_classNamePrefix_text");
		this.classNamePrefixText.setBounds(130, 140, 315, 20);
		this.txtFormPanel.add(this.classNamePrefixText);
		this.classNamePrefixText.setColumns(10);

		this.srvProjRootText = new JTextField();
		this.srvProjRootText.setName("unrequired_srvProjRoot_text");
		this.srvProjRootText.setBounds(130, 180, 250, 20);
		this.txtFormPanel.add(this.srvProjRootText);
		this.srvProjRootText.setColumns(10);

		this.srvRootChooserBtn = new JButton("浏览");
		this.srvRootChooserBtn.setName("srvRootChooser_btn");
		this.srvRootChooserBtn.setBounds(385, 180, 60, 20);
		this.txtFormPanel.add(this.srvRootChooserBtn);

		this.pageProjRootText = new JTextField();
		this.pageProjRootText.setName("unrequired_pageProjRoot_text");
		this.pageProjRootText.setBounds(130, 220, 250, 20);
		this.txtFormPanel.add(this.pageProjRootText);
		this.pageProjRootText.setColumns(10);

		this.pageRootChooserBtn = new JButton("浏览");
		this.pageRootChooserBtn.setName("pageRootChooser_btn");
		this.pageRootChooserBtn.setBounds(385, 220, 60, 20);
		this.txtFormPanel.add(this.pageRootChooserBtn);

		this.chkFormPanel = new JPanel();
		frameContainer.add(this.chkFormPanel);
		this.chkFormPanel.setBounds(10, 305, 475, 75);
		this.chkFormPanel.setBorder(BorderFactory.createTitledBorder("生成范围"));
		this.chkFormPanel.setLayout(null);

		JCheckBox chkEntity = new JCheckBox("Entity");
		chkEntity.setName("srv_entity_chk");
		chkEntity.setBounds(80, 12, 90, 23);
		this.chkFormPanel.add(chkEntity);

		JCheckBox chkController = new JCheckBox("Controller");
		chkController.setName("srv_controller_chk");
		chkController.setBounds(180, 12, 90, 23);
		this.chkFormPanel.add(chkController);

		JCheckBox chkService = new JCheckBox("Service");
		chkService.setName("srv_service_chk");
		chkService.setBounds(280, 12, 90, 23);
		this.chkFormPanel.add(chkService);

		JCheckBox chkManager = new JCheckBox("Manager");
		chkManager.setName("srv_manager_chk");
		chkManager.setBounds(380, 12, 90, 23);
		this.chkFormPanel.add(chkManager);

		JCheckBox chkDao = new JCheckBox("Dao");
		chkDao.setName("srv_dao_chk");
		chkDao.setBounds(80, 42, 90, 23);
		this.chkFormPanel.add(chkDao);

		JCheckBox chkMapper = new JCheckBox("Mapper");
		chkMapper.setName("srv_mapper_chk");
		chkMapper.setBounds(180, 42, 90, 23);
		this.chkFormPanel.add(chkMapper);

		JCheckBox chkHtml = new JCheckBox("Html");
		chkHtml.setName("page_html_chk");
		chkHtml.setBounds(280, 42, 90, 23);
		this.chkFormPanel.add(chkHtml);

		JCheckBox chkJs = new JCheckBox("JavaScript");
		chkJs.setName("page_js_chk");
		chkJs.setBounds(380, 42, 90, 23);
		this.chkFormPanel.add(chkJs);

		this.globalChkBox = new JCheckBox("全选");
		this.globalChkBox.setName("global_all_chk");
		this.globalChkBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Component[] coms = MainWin.this.chkFormPanel.getComponents();
				for(Component com : coms) {
					if((com instanceof JCheckBox) && (com != MainWin.this.globalChkBox)) {
						((JCheckBox)com).setSelected(MainWin.this.globalChkBox.isSelected());
					}
				}
			}
		});
		this.globalChkBox.setBounds(20, 26, 50, 24);
		this.chkFormPanel.add(this.globalChkBox);
		this.initTableNameComboBox();
		this.initDirChooserBtn();
		this.initGenerateBtn();
	}

	/**
	 * 初始化数据库表名下拉框
	 *
	 * @author: 钟磊
	 * @date: 2018年3月28日 上午10:02:15
	 */
	private void initTableNameComboBox() {
		Set<String> tableNames = DBUtils.getTableNames();
		for(String tname : tableNames) {
			this.tableNameBox.addItem(tname);
		}
		this.tableNameBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					int selectIndex = MainWin.this.tableNameBox.getSelectedIndex();
					String tableName = (selectIndex == 0x0) ? UtilCharacter.EMPTY : MainWin.this.tableNameBox
							.getSelectedItem().toString();
					MainWin.this.tableDescText.setText((selectIndex == 0x0) ? UtilCharacter.EMPTY : DBUtils.getTable(
							tableName).getComment());
					String camelText = SunnykidText.underlineToCamel(tableName);
					MainWin.this.packageNameText.setText(SunnykidText.lowerFirstChar(camelText));
					MainWin.this.classNamePrefixText.setText(SunnykidText.upperFirstChar(camelText));
				}
			}
		});
	}
}
