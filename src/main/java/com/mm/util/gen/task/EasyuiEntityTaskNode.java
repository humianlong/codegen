package com.mm.util.gen.task;

import com.mm.util.gen.freemarker.CodeFile;
import com.mm.util.gen.freemarker.JavaCodeFile;
import com.mm.util.gen.wapper.TableWapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class EasyuiEntityTaskNode extends AbstractTaskNode {

    private String templateEntity = "Entity.ftl";
    private String templateMapper = "easyui.EntityMapper.ftl";

    //	private String templateMapper = "EntityMapper.ftl";
    private String templateCustom = "EntityCustom.ftl";
    private String templateDao = "EntityDao.ftl";
    private String templateVo = "EntityVO.ftl";
    private String templateDaoImpl = "EntityDaoImpl.ftl";

    @Override
    public List<CodeFile> execute(TableWapper table, Map<String, Object> config) throws Exception {
        String basepkg = (String) config.get("basepkg");
        Map<String, Object> map = this.buildContext(table, config);
        // entity
        String name = table.getJavaName() + ".java";
        String content = codeService.executeTemplate(templateEntity, map);
        CodeFile entityFile = new JavaCodeFile(basepkg + ".entity", name, content);
        // mapper
        name = table.getJavaName() + "Mapper.xml";
        content = codeService.executeTemplate(templateMapper, map);
        CodeFile mapperFile = new JavaCodeFile(basepkg + ".dao.mapper", name, content);
        // custom
    /*name = table.getJavaName() + "Custom.xml";
    content = codeService.executeTemplate(templateCustom, map);
    CodeFile customFile = new JavaCodeFile(basepkg + ".dao.custom", name, content);*/
        // dao
        name = table.getJavaName() + "Mapper.java";
        content = codeService.executeTemplate(templateDao, map);
        CodeFile daoFile = new JavaCodeFile(basepkg + ".dao", name, content);
        // vo (swagger)
    /*name = table.getJavaName() + "VO.java";
    content = codeService.executeTemplate(templateVo, map);
    CodeFile voFile = new JavaCodeFile(basepkg + ".vo", name, content);*/
        // daoimpl
//		name = table.getJavaName() + "DaoImpl.java";
//		content = codeService.executeTemplate(templateDaoImpl, map);
//		CodeFile daoImplFile = new JavaCodeFile(basepkg + ".dao.impl", name, content);
        //return Arrays.asList(entityFile, mapperFile, customFile, daoFile, voFile);
        return Arrays.asList(entityFile, mapperFile, daoFile);
    }

}
