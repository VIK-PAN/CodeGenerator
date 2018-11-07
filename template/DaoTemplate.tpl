package ${packageRoot}.modules.${modPkg}.dao;

import org.apache.ibatis.annotations.Mapper;
import ${packageRoot}.common.mapper.BaseDefMapper;
import ${packageRoot}.modules.${modPkg}.entity.${upperPrefix}Entity;

/**
* @File: ${upperPrefix}Mapper.java
* @Project: ${projectName}
* @Package: ${packageRoot}.modules.${modPkg}.dao
* @Description: ${tableDesc}数据库层myBatis映射接口
* @Company: ${company}
* @author: ${author}
* @date: ${currentDate}
* @version: ${projectVersion}
 */
@Mapper
public interface ${upperPrefix}Mapper extends BaseDefMapper<${upperPrefix}Entity,${primaryKeyTyp}> {
	//TODO:
}
