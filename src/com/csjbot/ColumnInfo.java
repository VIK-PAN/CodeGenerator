package com.csjbot;

import java.io.Serializable;

/**
 *
 * @Title: ColumnInfo.java
 * @Project: csjbot-code-generator
 * @Package: com.csjbot
 * @Description: 字段信息封装类
 * @Company: 苏州穿山甲机器人股份有限公司
 * @author: 钟磊
 * @date: 2018年3月27日 下午4:58:04
 * @version: V1.0.0
 */
public class ColumnInfo implements Serializable {

	private static final long serialVersionUID = -9110727430536323800L;

	private String clsName;
	private String comment;
	private String firstLowerName;
	private String firstUpperName;
	private String columnName;

	public ColumnInfo() {
		super();
	}

	public ColumnInfo(String columnName, String firstLowerName) {
		super();
		this.columnName = columnName;
		this.firstLowerName = firstLowerName;
	}

	public ColumnInfo(String clsName, String comment, String firstLowerName, String firstUpperName) {
		super();
		this.clsName = clsName;
		this.comment = comment;
		this.firstLowerName = firstLowerName;
		this.firstUpperName = firstUpperName;
	}

	public String getClsName() {
		return this.clsName;
	}

	public String getColumnName() {
		return this.columnName;
	}

	public String getComment() {
		return this.comment;
	}

	public String getFirstLowerName() {
		return this.firstLowerName;
	}

	public String getFirstUpperName() {
		return this.firstUpperName;
	}

	public void setClsName(String clsName) {
		this.clsName = clsName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setFirstLowerName(String firstLowerName) {
		this.firstLowerName = firstLowerName;
	}

	public void setFirstUpperName(String firstUpperName) {
		this.firstUpperName = firstUpperName;
	}

}
