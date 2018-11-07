<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${packageRoot}.modules.${modPkg}.dao.${upperPrefix}Mapper">
	
	<select id="listForPage" resultType="${packageRoot}.modules.${modPkg}.entity.${upperPrefix}Entity">
		SELECT * FROM ${tableName} t
		<!--
		<where>
			<if test="name != null and name != ''">
				AND t.name = ${r'#{'}name${r'}'}
			</if>
		</where>
		ORDER BY 
			t.${primaryKey} ASC
		-->
	</select>
	
	<select id="list" resultType="${packageRoot}.modules.${modPkg}.entity.${upperPrefix}Entity">
		SELECT * FROM ${tableName} t
		<!--
		<where>
			<if test="name != null and name != ''">
				AND t.name = ${r'#{'}name${r'}'}
			</if>
		</where>
		ORDER BY 
			t.${primaryKey} ASC
		-->
	</select>
</mapper>