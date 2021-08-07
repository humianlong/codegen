package ${config.basepkg}.controller;

import com.sendinfo.dataplus.core.Pagination;
import com.sendinfo.dataplus.core.mvc.BaseController;
import com.sendinfo.dataplus.core.mvc.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ${config.basepkg}.entity.${table.javaName};
import ${config.basepkg}.service.${table.javaName}Service;

/**
* @类名: ${table.comment}Controller
* @描述: ${table.comment}
* @作者: ${config.user}
* @日期: ${config.date}
*/
@Api(value = "${table.javaName}", tags = "${table.comment}")
@RestController
@RequestMapping("${config.catalog}/${table.javaNameL}")
public class ${table.javaName}Controller extends BaseController<${table.javaName}Service, ${table.javaName}, Long> {

@ApiOperation("按条件分页查询")
@PostMapping("listByPage")
public Response listByPage(Pagination page) {
return Response.build(() -> service.listByPage(page));
}

}