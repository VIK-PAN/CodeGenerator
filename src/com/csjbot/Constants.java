package com.csjbot;

import sunnykid.character.UtilCharacter;

/**
 * @Title: Constants.java
 * @Project: csjbot-code-generator
 * @Package: com.csjbot
 * @Description: 系统常量类
 * @Company: 苏州穿山甲机器人股份有限公司
 * @author: 钟磊
 * @date: 2018年3月27日 下午4:44:59
 * @version: V1.0.0
 */
public class Constants {

	// 预分析数据库
	protected static final String PREANALYSIS_DATABASE = "preanalysis_database";

	// 数据库预分析启用的线程数
	protected static final String PREANALYSIS_THREADS = "preanalysis_threads";

	// 显示欢迎界面
	protected static final String SHOW_WELCOME_WINDOW = "show_welcome_window";

	// 程序根路径系统键名
	protected static final String GENERATOR_ROOT_KEY = "GENERATOR_ROOT";

	// 服务项目根路径选择历史记录
	protected static final String SRV_PROJECT_ROOT_HISTORY = "SrvProjRootHistory";
	
	// 页面项目根路径选择历史记录
	protected static final String PAGE_PROJECT_ROOT_HISTORY = "PageProjRootHistory";

	// 代码作者
	protected static final String AUTHOR = "author";

	// 开发企业
	protected static final String COMPANY = "company";

	// 包路径根目录
	protected static final String PACKAGE_ROOT = "package_root";
	
	// 项目名称
	protected static final String PROJECT_NAME = "project_name";
	
	// 项目版本
	protected static final String PROJECT_VERSION = "project_version";

	// 程序根路径
	protected static String GENERATOR_ROOT_PATH = null;

	// 模版文件所在目录
	protected static final String TEMPLATE_DIR = "template";

	// 根路径打开历史配置文件所在目录
	protected static final String HISTORY_CONFIG_FILE = "config" + UtilCharacter.FILE_SEPARATOR + "history.properties";

	// 杂项参数配置文件所在目录
	protected static final String OTHERS_CONFIG_FILE = "config" + UtilCharacter.FILE_SEPARATOR + "config.properties";

	// 定义各种模版文件路径
	protected static final String CONTROLLER_TEMPLATE = "ControllerTemplate.tpl";
	protected static final String DAO_TEMPLATE = "DaoTemplate.tpl";
	protected static final String ENTITY_TEMPLATE = "EntityTemplate.tpl";
	protected static final String MANAGER_TEMPLATE = "ManagerTemplate.tpl";
	protected static final String MANAGERIMPL_TEMPLATE = "ManagerImplTemplate.tpl";
	protected static final String MAPPER_TEMPLATE = "MapperTemplate.tpl";
	protected static final String SERVICEIMPL_TEMPLATE = "ServiceImplTemplate.tpl";
	protected static final String SERVICE_TEMPLATE = "ServiceTemplate.tpl";

	// 对话框标题文字
	protected static final String INFO = "提示";
	protected static final String ERROR = "错误";
	protected static final String WARN = "警告";
	protected static final String OK = "确定";
	protected static final String REQUIRED = "必须指定！";

	// 错误日志信息
	protected static final String DB_CONNECTION_FAILURE_MSG = "数据库连接失败";
	protected static final String TABLE_OPEN_FAILURE_MSG = "数据表打开失败";
	protected static final String LIST_TABLE_NAMES_FAILURE_MSG = "获取数据表名称列表失败";
	protected static final String CREATE_HISTORY_FILE_FAILURE_MSG = "创建项目根路径历史记录文件失败";
	protected static final String UPDATE_HISTORY_FILE_FAILURE_MSG = "更新项目根路径历史记录文件失败";
	protected static final String GENERATE_FILE_FAILURE_MSG = "生成代码文件失败";

	// 系统提示消息
	protected static final String DB_CONNECTION_SUCCESS_MSG = "数据库连接成功";
	protected static final String TABLE_OPEN_SUCCESS_MSG = "数据表打开成功";
	protected static final String LIST_TABLE_NAMES_SUCCESS_MSG = "获取数据表名称列表成功";
	protected static final String GENERATE_FILE_SUCCESS_MSG = "生成代码文件成功";
	protected static final String GENERATE_FILE_IGNORE_MSG = "忽略生成代码文件";
	protected static final String UPDATE_PROJ_ROOT_HISTORY_MSG = "%s项目根路径历史记录更改为：%s";
	protected static final String GENERATE_FILES_COMPLETE_MSG = "代码文件全部生成完毕";
	protected static final String ANALYZE_TABLES_COMPLETE_MSG = "所有数据库表分析完毕, 总共%d张数据表";

	// 文件生成的三种返回值定义
	protected static final int SUCCESS = 0x00000001;
	protected static final int FAILURE = 0xFFFFFFFF;
	protected static final int IGNORE = 0x00000000;

	// 全局界面字体名称
	protected static final String GLOBAL_FONT_FAMILY = "宋体";
	// 全局界面字体大小
	protected static final int GLOBAL_FONT_SIZE = 0xC;
}
