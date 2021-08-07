package ${config.basepkg}.biz;

import java.util.List;
import org.springframework.stereotype.Service;

import com.sendinfo.dataplus.core.BaseService;
import com.sendinfo.dataplus.core.Page;
import com.sendinfo.dataplus.core.Pagination;
import com.sendinfo.dataplus.core.QueryMap;
import ${config.basepkg}.dao.${table.javaName}Mapper;
import ${config.basepkg}.entity.${table.javaName};

/**
* @类名: ${table.javaName}Service
* @描述: service ${table.comment}
* @作者: ${config.user}
* @日期: ${config.date}
*/
@Service
public class ${table.javaName}Service extends BaseService<${table.javaName}Mapper, ${table.javaName}, Long>{

/**
* 分页列表查询
*/
public Page<${table.javaName}> listByPage(Pagination page) {
QueryMap<Object> wheres = new QueryMap<Object>();
        wheres.put("page", page);
        List<${table.javaName}> list = mapper.listByPage(wheres);
        return new Page<${table.javaName}>(page, list);
        }
        }
