package ${packageRoot}.modules.${modPkg}.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ${packageRoot}.common.mapper.BaseDefMapper;
import ${packageRoot}.common.entity.Page;
import ${packageRoot}.common.entity.Query;
import ${packageRoot}.common.manager.BaseManagerImpl;
import ${packageRoot}.modules.${modPkg}.dao.${upperPrefix}Mapper;
import ${packageRoot}.modules.${modPkg}.entity.${upperPrefix}Entity;
import ${packageRoot}.modules.${modPkg}.manager.${upperPrefix}Manager;

/**
* @File: ${upperPrefix}ManagerImpl.java
* @Project: ${projectName}
* @Package: ${packageRoot}.modules.${modPkg}.manager.impl
* @Description: ${tableDesc}Manager层实现类
* @Company: ${company}
* @author: ${author}
* @date: ${currentDate}
* @version: ${projectVersion}
 */
@Component("${lowerPrefix}Manager")
public class ${upperPrefix}ManagerImpl extends BaseManagerImpl<${upperPrefix}Entity,${primaryKeyTyp}> implements ${upperPrefix}Manager {

	@Autowired
	private ${upperPrefix}Mapper ${lowerPrefix}Mapper;

    @Override
	public BaseDefMapper<${upperPrefix}Entity, ${primaryKeyTyp}> getMapper() {
		return ${lowerPrefix}Mapper;
	}


	@Override
	public List<${upperPrefix}Entity> list(Query query) {
		return this.${lowerPrefix}Mapper.list(query);
	}

	@Override
	public List<${upperPrefix}Entity> listPage(Page<${upperPrefix}Entity> page, Query query) {
		return this.${lowerPrefix}Mapper.listForPage(page, query);
	}



}
