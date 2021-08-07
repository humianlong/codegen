<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${config.basepkg}.dao.${table.javaName}Mapper">

    <resultMap id="BaseResultMap" type="${config.basepkg}.entity.${table.javaName}">
        <#list table.columns as column>
            <#if column.name == 'id'>
                <id column="id" jdbcType="BIGINT" property="id" />
            <#else>
                <result column="${column.name}" jdbcType="${column.jdbcType}" property="${column.javaName}" />
            </#if>
        </#list>
    </resultMap>

    <select id="listByPage" parameterType="java.util.Map" resultMap="BaseResultMap">
        SELECT * from ${table.name}
        <where>
            and deleted = 'F'
        </where>
    </select>

</mapper>
