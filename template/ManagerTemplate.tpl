package ${packageRoot}.modules.${modPkg}.manager;

import java.util.List;

import ${packageRoot}.common.entity.Page;
import ${packageRoot}.common.entity.Query;
import ${packageRoot}.common.manager.BaseManager;
import ${packageRoot}.modules.${modPkg}.entity.${upperPrefix}Entity;

/**
* @File: ${upperPrefix}.manager.java
* @Project: ${projectName}
* @Package: ${packageRoot}.modules.${modPkg}.manager
* @Description: ${tableDesc}Manager层接口
* @Company: ${company}
* @author: ${author}
* @date: ${currentDate}
* @version: ${projectVersion}
 */
public interface ${upperPrefix}Manager extends BaseManager<${upperPrefix}Entity,${primaryKeyTyp}>{


	/**
	 * 根据条件查询
	 * @param query Query
	 * @return List<${upperPrefix}Entity>
	 * @author ${author}
	 * @date ${currentDate}
	 */
	List<${upperPrefix}Entity> list(Query query);

	/**
	 * 根据条件分页查询
	 * @param page Page<${upperPrefix}Entity>
	 * @param query Query
	 * @return List<${upperPrefix}Entity>
	 * @author ${author}
	 * @date ${currentDate}
	 */
	List<${upperPrefix}Entity> listPage(Page<${upperPrefix}Entity> page, Query query);



}
