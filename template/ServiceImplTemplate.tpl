package ${packageRoot}.modules.${modPkg}.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${packageRoot}.common.entity.Page;
import ${packageRoot}.common.entity.Query;
import ${packageRoot}.common.entity.R;
import ${packageRoot}.common.utils.CommonUtils;
import ${packageRoot}.modules.${modPkg}.entity.${upperPrefix}Entity;
import ${packageRoot}.modules.${modPkg}.manager.${upperPrefix}Manager;
import ${packageRoot}.modules.${modPkg}.service.${upperPrefix}Service;

/**
* @File: ${upperPrefix}ServiceImpl.java
* @Project: ${projectName}
* @Package: ${packageRoot}.modules.${modPkg}.service.impl
* @Description: ${tableDesc}Service层实现类
* @Company: ${company}
* @author: ${author}
* @date: ${currentDate}
* @version: ${projectVersion}
 */
@Service("${lowerPrefix}Service")
public class ${upperPrefix}ServiceImpl implements ${upperPrefix}Service {

	@Autowired
	private ${upperPrefix}Manager ${lowerPrefix}Manager;

	@Override
	public R remove(${primaryKeyTyp} id) {
		int count = this.${lowerPrefix}Manager.deleteByPrimaryKeyByLogic(id);
		return CommonUtils.msg(count);
	}

	@Override
	public R batchRemove(${primaryKeyTyp}[] ids) {
		int count = this.${lowerPrefix}Manager.deleteListByPrimaryKeyByLogic(ids,${upperPrefix}Entity.class);
		return CommonUtils.msg(count);
	}

	@Override
	public R getById(${primaryKeyTyp} id) {
		${upperPrefix}Entity entity = this.${lowerPrefix}Manager.getByPrimaryKey(id);
		return CommonUtils.msg(entity);
	}

	@Override
	public R list(Map<String, Object> params) {
		Query form = new Query(params);
		List<${upperPrefix}Entity> list = this.${lowerPrefix}Manager.list(form);
		return CommonUtils.msg(list);
	}

	@Override
	public Page<${upperPrefix}Entity> listPage(Map<String, Object> params) {
		Query form = new Query(params);
		Page<${upperPrefix}Entity> page = new Page<${upperPrefix}Entity>(form);
		this.${lowerPrefix}Manager.listForPage(page, form);
		return page;
	}

	@Override
	public R save(${upperPrefix}Entity entity) {
		int count = this.${lowerPrefix}Manager.insert(entity);
		return CommonUtils.msg(count);
	}

	@Override
	public R update(${upperPrefix}Entity entity) {
		int count = this.${lowerPrefix}Manager.updateByPrimaryKeySelective(entity);
		return CommonUtils.msg(count);
	}

}
