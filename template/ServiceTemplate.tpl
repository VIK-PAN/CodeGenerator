package ${packageRoot}.modules.${modPkg}.service;

import java.util.Map;

import ${packageRoot}.common.entity.Page;
import ${packageRoot}.common.entity.R;
import ${packageRoot}.modules.${modPkg}.entity.${upperPrefix}Entity;

/**
* @File: ${upperPrefix}Service.java
* @Project: ${projectName}
* @Package: ${packageRoot}.modules.${modPkg}.service
* @Description: ${tableDesc}Service层接口
* @Company: ${company}
* @author: ${author}
* @date: ${currentDate}
* @version: ${projectVersion}
 */
public interface ${upperPrefix}Service {

	/**
	 * 根据主键id删除单条记录
	 * @param id ${primaryKeyTyp}
	 * @return R
	 * @author ${author}
	 * @date ${currentDate}
	 */
	R remove(${primaryKeyTyp} id);

	/**
	 * 根据id数组批量删除多条记录
	 * @param ids ${primaryKeyTyp}[]
	 * @return R
	 * @author ${author}
	 * @date ${currentDate}
	 */
	R batchRemove(${primaryKeyTyp}[] ids);

	/**
	 * 根据主键id查询记录
	 * @param id ${primaryKeyTyp}
	 * @return R
	 * @author ${author}
	 * @date ${currentDate}
	 */
	R getById(${primaryKeyTyp} id);

	/**
	 * 根据条件查询
	 * @param params Map<String, Object>
	 * @return R
	 * @author ${author}
	 * @date ${currentDate}
	 */
	R list(Map<String, Object> params);

	/**
	 * 根据条件分页查询
	 * @param params Map<String, Object>
	 * @return Page<${upperPrefix}Entity>
	 * @author ${author}
	 * @date ${currentDate}
	 */
	Page<${upperPrefix}Entity> listPage(Map<String, Object> params);

	/**
	 * 新增记录
	 * @param entity ${upperPrefix}Entity
	 * @return int
	 * @author ${author}
	 * @date ${currentDate}
	 */
	R save(${upperPrefix}Entity entity);

	/**
	 * 更新记录
	 * @param entity ${upperPrefix}Entity
	 * @return int
	 * @author ${author}
	 * @date ${currentDate}
	 */
	R update(${upperPrefix}Entity entity);

}
