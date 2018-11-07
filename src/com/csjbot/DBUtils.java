package com.csjbot;

import java.util.Set;

import javax.swing.JOptionPane;

import sunnykid.character.UtilCharacter;
import sunnykid.jdbc.DataBaseManager;
import sunnykid.jdbc.DataBaseOperator;
import sunnykid.jdbc.JDBCException;
import sunnykid.jdbc.Table;
import sunnykid.web.log4j.LoggerFactory;
import sunnykid.web.log4j.SunnykidLogger;

/**
 * @Title: DBUtils.java
 * @Project: csjbot-code-generator
 * @Package: com.csjbot
 * @Description: 数据库操作工具类
 * @Company: 苏州穿山甲机器人股份有限公司
 * @author: 钟磊
 * @date: 2018年3月28日 上午10:00:42
 * @version: V1.0.0
 */
public class DBUtils {

	// 数据库操作组件
	private static DataBaseOperator DBO = null;
	private static final SunnykidLogger logger = LoggerFactory.getLogger(DBUtils.class);
	private static Set<String> tableNames = null;

	/**
	 * 初始化数据库连接
	 */
	static {
		try {
			DBUtils.DBO = DataBaseManager.getOperatorInstance(Constants.GENERATOR_ROOT_PATH
					+ UtilCharacter.FILE_SEPARATOR + "config" + UtilCharacter.FILE_SEPARATOR + "jdbc.properties");
			DBUtils.logger.info(Constants.DB_CONNECTION_SUCCESS_MSG);
		} catch(JDBCException ex) {
			DBUtils.logger.error(Constants.DB_CONNECTION_FAILURE_MSG, ex);
			JOptionPane.showMessageDialog(null, Constants.DB_CONNECTION_FAILURE_MSG, Constants.ERROR,
					JOptionPane.ERROR_MESSAGE);
			throw new DefinedException(Constants.DB_CONNECTION_FAILURE_MSG, ex);
		}
	}

	/**
	 * 打开数据表
	 * @param tableName
	 * @return Table
	 * @author: 钟磊
	 * @date: 2018年3月28日 下午12:59:30
	 */
	protected static Table getTable(String tableName) {
		try {
			Table table = DBUtils.DBO.analyzeTable(tableName);
			DBUtils.logger.info(Constants.TABLE_OPEN_SUCCESS_MSG + "：" + tableName);
			return table;
		} catch(JDBCException ex) {
			DBUtils.logger.error(Constants.TABLE_OPEN_FAILURE_MSG, ex);
			JOptionPane.showMessageDialog(null, Constants.TABLE_OPEN_SUCCESS_MSG + "：" + tableName, Constants.ERROR,
					JOptionPane.ERROR_MESSAGE);
			throw new DefinedException(Constants.TABLE_OPEN_SUCCESS_MSG + "：" + tableName, ex);
		}
	}

	/**
	 * 获取数据库表名称列表
	 * @return Set<String>
	 * @author: 钟磊
	 * @date: 2018年3月28日 上午9:59:15
	 */
	protected synchronized static Set<String> getTableNames() {
		if(DBUtils.tableNames != null) {
			return DBUtils.tableNames;
		}
		try {
			DBUtils.tableNames = DBUtils.DBO.getAllTableNames();
			DBUtils.logger.info(Constants.LIST_TABLE_NAMES_SUCCESS_MSG);
			return DBUtils.tableNames;
		} catch(JDBCException ex) {
			DBUtils.logger.error(Constants.LIST_TABLE_NAMES_FAILURE_MSG, ex);
			JOptionPane.showMessageDialog(null, Constants.LIST_TABLE_NAMES_FAILURE_MSG, Constants.ERROR,
					JOptionPane.ERROR_MESSAGE);
			throw new DefinedException(Constants.LIST_TABLE_NAMES_FAILURE_MSG, ex);
		}
	}

}
