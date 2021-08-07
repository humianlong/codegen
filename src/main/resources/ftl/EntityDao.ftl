package ${config.basepkg}.dao;

import ${config.basepkg}.entity.${table.javaName};
import com.sendinfo.dataplus.core.BaseMapper;
import com.sendinfo.dataplus.core.QueryMap;

import java.util.List;

/**
* @类名: ${table.comment}Mapper
* @描述: ${table.comment}
* @作者: ${config.user}
* @日期: ${config.date}
*/
public interface ${table.javaName}Mapper extends BaseMapper<${table.javaName}> {

List<${table.javaName}> listByPage(QueryMap<Object> wheres);

    }
