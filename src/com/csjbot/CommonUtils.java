package com.csjbot;

import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import sunnykid.character.UtilCharacter;
import sunnykid.file.FileOperator;
import sunnykid.text.property.ParserFactory;
import sunnykid.text.property.PropertyException;
import sunnykid.text.property.PropertyParser;
import sunnykid.text.property.PropertyWriter;
import sunnykid.web.log4j.LoggerFactory;
import sunnykid.web.log4j.SunnykidLogger;

/**
 *
 * @Title: Common.java
 * @Project: csjbot-code-generator
 * @Package: com.csjbot
 * @Description: 系统公共函数
 * @Company: 苏州穿山甲机器人股份有限公司
 * @author: 钟磊
 * @date: 2018年3月28日 上午12:15:41
 * @version: V1.0.0
 */
public class CommonUtils {

	static SunnykidLogger logger = LoggerFactory.getLogger(CommonUtils.class);

	/**
	 * 打开项目根路径打开历史记录
	 * @param projectType String
	 * @author 钟磊
	 * @date 2018年4月13日 上午9:08:07
	 */
	public static String openProjRootHistory(String projectType) {
		String typeText = null;
		if(Constants.SRV_PROJECT_ROOT_HISTORY.equals(projectType)) {
			typeText = "服务";
		} else if(Constants.PAGE_PROJECT_ROOT_HISTORY.equals(projectType)) {
			typeText = "页面";
		}
		String historyPath = CommonUtils.getProjRootHistory(projectType);
		JFileChooser chooser = new JFileChooser(historyPath);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setApproveButtonText(Constants.OK);
		chooser.setDialogTitle("选择" + typeText + "项目根目录");
		int returnVal = chooser.showOpenDialog(chooser);
		String projRoot = null;
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			projRoot = chooser.getSelectedFile().getAbsolutePath();
			if((projRoot != historyPath) && !projRoot.equals(historyPath)) {
				CommonUtils.updateProjRootHistory(projRoot, projectType);
				MainWin.logger.info(String.format(Constants.UPDATE_PROJ_ROOT_HISTORY_MSG, typeText, projRoot));
			}
		}
		return projRoot;
	}

	/**
	 * 读取项目根路径打开历史记录
	 * @param projectType String
	 * @return String
	 * @author 钟磊
	 * @date 2018年3月29日 上午9:43:21
	 */
	protected static String getProjRootHistory(String projectType) {
		File configFile = new File(Constants.GENERATOR_ROOT_PATH + UtilCharacter.FILE_SEPARATOR
				+ Constants.HISTORY_CONFIG_FILE);
		if(FileOperator.isExistingFile(configFile)) {
			PropertyParser parser = ParserFactory.getParserInstance(configFile);
			return parser.getProperty(projectType);
		}
		return null;
	}

	/**
	 * 初始化系统字体
	 */
	protected static void initGlobalFont() {
		FontUIResource fontUIResource = new FontUIResource(new Font(Constants.GLOBAL_FONT_FAMILY, Font.PLAIN,
				Constants.GLOBAL_FONT_SIZE));
		for(Enumeration<?> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if(value instanceof FontUIResource) {
				UIManager.put(key, fontUIResource);
			}
		}
	}

	/**
	 * 更新项目根路径打开历史记录
	 * @param projRoot String
	 * @param projectType String
	 * @author 钟磊
	 * @date 2018年3月29日 上午9:27:05
	 */
	protected static void updateProjRootHistory(final String projRoot, final String projectType) {
		File configFile = new File(Constants.GENERATOR_ROOT_PATH + UtilCharacter.FILE_SEPARATOR
				+ Constants.HISTORY_CONFIG_FILE);
		PropertyWriter pw = ParserFactory.getWriterInstance(configFile);
		if(FileOperator.isExistingFile(configFile)) {
			try {
				pw.updatePropertyFile(projectType, projRoot);
			} catch(PropertyException ex) {
				CommonUtils.logger.error(Constants.UPDATE_HISTORY_FILE_FAILURE_MSG, ex);
			}
		} else {
			try {
				pw.createPropertyFile(new HashMap<String, String>() {
					private static final long serialVersionUID = -9144202368309167452L;
					{
						this.put(projectType, projRoot);
					}
				});
			} catch(PropertyException ex) {
				CommonUtils.logger.error(Constants.UPDATE_HISTORY_FILE_FAILURE_MSG, ex);
			}
		}
	}

	/**
	 * 根据name在指定容器中查找组件
	 * @param container JComponent
	 * @param name String
	 * @return Component
	 * @author 钟磊
	 * @date 2018年4月12日 下午5:44:04
	 */
	public static Component getComponentByName(JComponent container, String name) {
		Component[] cs = container.getComponents();
		for(Component c : cs) {
			if(c.getName() != null && c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}
}
