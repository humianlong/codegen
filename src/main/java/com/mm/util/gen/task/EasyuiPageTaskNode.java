package com.mm.util.gen.task;

import com.mm.util.gen.freemarker.CodeFile;
import com.mm.util.gen.freemarker.ConfigCodeFile;
import com.mm.util.gen.freemarker.JavaCodeFile;
import com.mm.util.gen.wapper.TableWapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class EasyuiPageTaskNode extends AbstractTaskNode {

    private String templateList = "easyui.page.list.ftl";

    private String templateListJs = "easyui.page.listjs.ftl";

    private String templateAdd = "easyui.page.add.ftl";

    private String templateEdit = "easyui.page.edit.ftl";

    @Override
    public List<CodeFile> execute(TableWapper table, Map<String, Object> config) throws Exception {
        String catalog = (String) config.get("catalog");
        Map<String, Object> map = this.buildContext(table, config);
        String name = table.getJavaName().toLowerCase();
        String listName = name + "-list.html";
        String content = codeService.executeTemplate(templateList, map);
        CodeFile listFile = new JavaCodeFile(catalog+"/"+name, listName, content);

//		String listNameJs = name + "-list.js";
//		content = codeService.executeTemplate(templateListJs, map);
//		CodeFile listJsFile = new ConfigCodeFile(catalog, listNameJs, content);

        String addName = name + "-add.html";
        content = codeService.executeTemplate(templateAdd, map);
        CodeFile addFile = new ConfigCodeFile(catalog+"/"+name, addName, content);

        String editName = name + "-edit.html";
        content = codeService.executeTemplate(templateEdit, map);
        CodeFile editFile = new ConfigCodeFile(catalog+"/"+name, editName, content);

//		return Arrays.asList(listFile, listJsFile, addFile, editFile);
        return Arrays.asList(listFile, addFile, editFile);
    }

}
