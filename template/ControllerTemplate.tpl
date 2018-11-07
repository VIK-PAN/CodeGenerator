package ${packageRoot}.modules.${modPkg}.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import ${packageRoot}.common.annotation.SysLog;
import ${packageRoot}.common.entity.Page;
import ${packageRoot}.common.entity.R;
import ${packageRoot}.modules.${modPkg}.entity.${upperPrefix}Entity;
import ${packageRoot}.modules.${modPkg}.service.${upperPrefix}Service;

/**
* @File: ${upperPrefix}Controller.java
* @Project: ${projectName}
* @Package: ${packageRoot}.modules.${modPkg}.controller
* @Description: ${tableDesc}Controller层
* @Company: ${company}
* @author: ${author}
* @date: ${currentDate}
* @version: ${projectVersion}
 */
@RestController
@RequestMapping("/${lowerPrefix}")
public class ${upperPrefix}Controller {

	@Autowired
	private ${upperPrefix}Service ${lowerPrefix}Service;

	/**
	 * 根据id数组批量删除多条记录
	 * @param ids Long[]
	 * @return R
	 */
	@SysLog("批量删除${tableDesc}")
	@RequestMapping(value = "/batchRemove/{ids}",method = RequestMethod.DELETE)
	public R batchRemove(@PathVariable("ids") ${primaryKeyTyp}[] ids) {
		return this.${lowerPrefix}Service.batchRemove(ids);
	}

	/**
	 * 根据主键id删除单条记录
	 * @param id
	 * @return R
	 */
	@SysLog("单条删除${tableDesc}")
	@RequestMapping(value ="/remove/{id}",method = RequestMethod.DELETE)
	public R remove(@PathVariable("id") ${primaryKeyTyp} id) {
		return this.${lowerPrefix}Service.remove(id);
	}

	/**
	 * 根据id查询详情
	 * @param id Long
	 * @return R
	 */
	@RequestMapping("/getById")
	public R getById(${primaryKeyTyp} id) {
		return this.${lowerPrefix}Service.getById(id);
	}

	/**
	 * 根据条件分页查询
	 * @param params Map<String, Object>
	 * @return Page<${upperPrefix}Entity>
	 */
	@RequestMapping("/listPage")
	public Page<${upperPrefix}Entity> listPage(@RequestParam Map<String, Object> params) {
		return this.${lowerPrefix}Service.listPage(params);
	}

	/**
	 * 根据条件查询
	 * @param params Map<String, Object>
	 * @return R
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params) {
		return this.${lowerPrefix}Service.list(params);
	}

	/**
	 * 新增记录
	 * @param entity ${upperPrefix}Entity
	 * @return R
	 */
	@SysLog("新增${tableDesc}")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public R save(@RequestBody ${upperPrefix}Entity entity) {
		return this.${lowerPrefix}Service.save(entity);
	}

	/**
	 * 更新记录
	 * @param entity ${upperPrefix}Entity
	 * @return R
	 */
	@SysLog("修改${tableDesc}")
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	public R update(@RequestBody ${upperPrefix}Entity entity) {
		return this.${lowerPrefix}Service.update(entity);
	}

}
