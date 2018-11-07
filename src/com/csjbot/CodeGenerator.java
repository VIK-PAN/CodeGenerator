package com.csjbot;

import static com.csjbot.LoadingWin.configMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import sunnykid.character.UtilCharacter;
import sunnykid.file.DirectoryOperator;
import sunnykid.file.FileOperator;
import sunnykid.jdbc.Column;
import sunnykid.jdbc.Table;
import sunnykid.text.SunnykidText;
import sunnykid.text.TemplateWriter;
import sunnykid.text.TextException;
import sunnykid.web.log4j.LoggerFactory;
import sunnykid.web.log4j.SunnykidLogger;

/**
 *
 * @Title: CodeGenerator.java
 * @Project: csjbot-code-generator
 * @Package: com.csjbot
 * @Description: 代码生成功能实现类
 * @Company: 苏州穿山甲机器人股份有限公司
 * @author: 钟磊
 * @date: 2018年3月27日 下午4:58:04
 * @version: V1.0.0
 */
public class CodeGenerator {

	private static SunnykidLogger logger = LoggerFactory.getLogger(CodeGenerator.class);

	/**
	 * 根据不同类型生成对应的代码文件
	 * @param config
	 * @param srvProjRoot
	 * @param pageProjRoot
	 * @param suffix
	 * @return int
	 */
	protected static int generateCodeFile(Map<String, Object> config, String srvProjRoot, String pageProjRoot,
			String suffix) {
		String templateDir = Constants.GENERATOR_ROOT_PATH + UtilCharacter.FILE_SEPARATOR + Constants.TEMPLATE_DIR;
		String fileSuffix = null;
		String modPkg = config.get("lowerPrefix").toString();
		String[] archPkg = suffix.split("\\.");
		String path = null;

		String codeFileDir = null;// 后端服务代码文件生成目录
		String codeFileName = null;// 代码文件名称
		String tableName = config.get("tableName").toString();

		for(Column column:DBUtils.getTable(tableName).getColumns()){
			String fieldName = SunnykidText.lowerFirstChar(SunnykidText.underlineToCamel(column.getName()));
			config=primaryJudge(column,config,fieldName);
		}

		if(suffix.equalsIgnoreCase("html")) {
			fileSuffix = ".html";
			path = UtilCharacter.FILE_SEPARATOR + ((tableName.toLowerCase().startsWith("sys_")) ? "sys" : "modules")
					+ UtilCharacter.FILE_SEPARATOR + config.get("lowerPrefix");
			config.put("jsModPkg", (tableName.toLowerCase().startsWith("sys_")) ? "sys" : "modules");
			codeFileDir = pageProjRoot + path;// 后端服务代码文件生成目录
		} else if(suffix.equalsIgnoreCase("javascript")) {
			path = UtilCharacter.FILE_SEPARATOR + "js" + UtilCharacter.FILE_SEPARATOR
					+ ((tableName.toLowerCase().startsWith("sys_")) ? "sys" : "modules") + UtilCharacter.FILE_SEPARATOR
					+ config.get("lowerPrefix");
			codeFileDir = pageProjRoot + path;// 后端服务代码文件生成目录
			fileSuffix = ".js";
		} else if(suffix.equalsIgnoreCase("mapper")) {
			List<String> columnNameList = new ArrayList<String>();
			List<String> fieldNameList = new ArrayList<String>();
			List<ColumnInfo> columnInfoList = new ArrayList<ColumnInfo>();
			Table table = DBUtils.getTable(tableName);
			Column[] columns = table.getColumns();
			for(Column column : columns) {
				String columnName = column.getName();
				String fieldName = SunnykidText.lowerFirstChar(SunnykidText.underlineToCamel(columnName));
				fieldNameList.add(fieldName);
				ColumnInfo columnInfo = new ColumnInfo(columnName, fieldName);
			}
			config.put("columnNameList", columnNameList);
			config.put("fieldNameList", fieldNameList);
			config.put("columnInfoList", columnInfoList);
			fileSuffix = ".xml";
			path = SunnykidText.format("{0}src{0}main{0}resources", UtilCharacter.FILE_SEPARATOR);
			codeFileDir = srvProjRoot + path;// 后端服务代码文件生成目录
		} else {
			path = SunnykidText.format("{0}src{0}main{0}java{0}"
					+ configMap.get(Constants.PACKAGE_ROOT).toString().replace(UtilCharacter.DOT, "{0}")
					+ "{0}modules{0}{1}", UtilCharacter.FILE_SEPARATOR,
					((tableName.toLowerCase().startsWith("sys_")) ? "sys" : modPkg));
			fileSuffix = ".java";
			codeFileDir = srvProjRoot + path;// 后端服务代码文件生成目录
		}
		if(suffix.equalsIgnoreCase("entity")) {
			List<ColumnInfo> columnInfoList = new ArrayList<ColumnInfo>();
			Column[] columns = DBUtils.getTable(tableName).getColumns();
			for(Column column : columns) {
				String fieldName = SunnykidText.underlineToCamel(column.getName());
				ColumnInfo columnInfo = new ColumnInfo(column.getClassName(), column.getComment(),
						SunnykidText.lowerFirstChar(fieldName), SunnykidText.upperFirstChar(fieldName));
				if(!column.isPrimaryKey()){
					columnInfoList.add(columnInfo);
				}
			}
			config.put("columnInfoList", columnInfoList);
		}
		String templateFileName = UtilCharacter.EMPTY;
		if(suffix.equalsIgnoreCase("html") || suffix.equalsIgnoreCase("javascript")) {
			codeFileName = "list";// 代码文件名称
			templateFileName += suffix;
		} else {
			codeFileName = config.get("upperPrefix").toString();// 代码文件名称
			for(String apk : archPkg) {
				codeFileDir += (UtilCharacter.FILE_SEPARATOR + apk.toLowerCase());
				codeFileName += suffix.equalsIgnoreCase("dao") ? "Mapper" : apk;
				templateFileName += apk;
			}
		}
		if(suffix.equalsIgnoreCase("mapper")) {
			codeFileDir += (UtilCharacter.FILE_SEPARATOR + ((tableName.toLowerCase().startsWith("sys_")) ? "sys"
					: modPkg));
		}
		if(!DirectoryOperator.isExistingDirectory(codeFileDir)) {
			DirectoryOperator.makeDirectory(codeFileDir);
		}
		String codeFullPath = codeFileDir + UtilCharacter.FILE_SEPARATOR + codeFileName + fileSuffix;
		if(FileOperator.isExistingFile(codeFullPath)
				&& (JOptionPane.showConfirmDialog(null, "目标文件" + codeFileName + fileSuffix + "已存在！\n是否覆盖现有文件？",
						Constants.INFO, JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)) {
			CodeGenerator.logger.info(Constants.GENERATE_FILE_IGNORE_MSG + "：" + codeFullPath);
			return Constants.IGNORE;
		}
		TemplateWriter tw = new TemplateWriter();
		try {
			tw.createTemplate(templateDir, templateFileName + "Template.tpl");
			tw.write(codeFullPath, config);
			CodeGenerator.logger.info(Constants.GENERATE_FILE_SUCCESS_MSG + "：" + codeFullPath);
			return Constants.SUCCESS;
		} catch(TextException ex) {
			CodeGenerator.logger.error(Constants.GENERATE_FILE_FAILURE_MSG + "：" + codeFullPath);
			return Constants.FAILURE;
		}
	}


	public static Map<String,Object> primaryJudge(Column column,Map<String,Object> config,String fieldName){
		if(column.isPrimaryKey() && !config.containsKey("primaryKey")) {
			config.put("primaryKey", column.getName());
			config.put("primaryKeyField", fieldName);
			//config.put("primaryKeyTyp",column.getClassName().equals("java.lang.Long")?"Long":"String");
			config.put("primaryKeyTyp",column.getClassName());
		}
		return config;
	}
}
