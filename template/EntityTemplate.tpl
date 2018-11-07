package ${packageRoot}.modules.${modPkg}.entity;

import ${packageRoot}.common.entity.BaseEntity;
import javax.persistence.Table;
<#if primaryKeyTyp=='java.long.String'>
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;
import com.csjbot.platform.common.annotation.IgnoreBaseId;
</#if>

/**
* @File: ${upperPrefix}Entity.java
* @Project: ${projectName}
* @Package: ${packageRoot}.modules.${modPkg}.entity
* @Description: ${tableDesc}实体层
* @Company: ${company}
* @author: ${author}
* @date: ${currentDate}
* @version: ${projectVersion}
 */
 @Table(name="${tableName}")
public class ${upperPrefix}Entity extends BaseEntity<${primaryKeyTyp}>  {
	private static final long serialVersionUID = 1L;
	<#if primaryKeyTyp=='java.long.String'>
	@Id
    @KeySql(sql = "select replace(uuid(), '-', '') as id from dual", order = ORDER.BEFORE)
    @IgnoreBaseId
	private ${primaryKeyTyp} ${primaryKeyField};
	</#if>
	<#list columnInfoList as columnInfo>
	<#if columnInfo.firstLowerName!='createById' && columnInfo.firstLowerName!='createByDate' && columnInfo.firstLowerName!='modifyById' && columnInfo.firstLowerName!='modifyByDate' && columnInfo.firstLowerName!='deleted'>
    //${columnInfo.comment}
    private ${columnInfo.clsName} ${columnInfo.firstLowerName};
    </#if>
	</#list>

	<#if primaryKeyTyp=='java.long.String'>
    public ${primaryKeyTyp} get${primaryKeyField?cap_first}() {
		return ${primaryKeyField};
	}

	public void set${primaryKeyField?cap_first}(${primaryKeyTyp} ${primaryKeyField}) {
		this.${primaryKeyField} = ${primaryKeyField};
	}
	</#if>
	<#list columnInfoList as columnInfo>
	<#if columnInfo.firstLowerName!='createById' && columnInfo.firstLowerName!='createByDate' && columnInfo.firstLowerName!='modifyById' && columnInfo.firstLowerName!='modifyByDate' && columnInfo.firstLowerName!='deleted'>
	public ${columnInfo.clsName} get${columnInfo.firstUpperName}() {
		return this.${columnInfo.firstLowerName};
	}
	
	public void set${columnInfo.firstUpperName}(${columnInfo.clsName} ${columnInfo.firstLowerName}) {
		this.${columnInfo.firstLowerName} = ${columnInfo.firstLowerName};
	}
	</#if>
	  
	</#list>
}
