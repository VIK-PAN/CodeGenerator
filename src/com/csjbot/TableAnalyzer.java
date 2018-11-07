package com.csjbot;

import java.util.HashSet;
import java.util.Set;

import sunnykid.web.log4j.LoggerFactory;
import sunnykid.web.log4j.SunnykidLogger;

/**
 *
 * @File: TableAnalyzer.java
 * @Project csjbot-code-generator
 * @Package com.csjbot
 * @Description: 数据表分析器（独立线程）
 * @Company: 苏州穿山甲机器人股份有限公司
 * @author 钟磊
 * @date 2018年3月29日 下午3:03:47
 * @version V1.0.0
 */
public class TableAnalyzer implements Runnable {

	public TableAnalyzer() {
		super();
	}

	public TableAnalyzer(Set<String> tableNames) {
		super();
		this.tableNames = tableNames;
	}

	/**
	 * 需要分析的数据表名称
	 */
	private Set<String> tableNames = new HashSet<String>();
	
	public Set<String> getTableNames() {
		return this.tableNames;
	}

	public void setTableNames(Set<String> tableNames) {
		this.tableNames = tableNames;
	}

	/**
	 * 日志记录组件
	 */
	static SunnykidLogger logger = LoggerFactory.getLogger(TableAnalyzer.class);

	@Override
	public void run() {
		for(String tableName : this.tableNames) {
			DBUtils.getTable(tableName);
		}
		TableAnalyzer.logger.info(String.format(Constants.ANALYZE_TABLES_COMPLETE_MSG,
				Integer.valueOf(this.tableNames.size())));
	}

}
