package com.mm.util.gen.task;

import com.mm.util.gen.freemarker.CodeFile;
import com.mm.util.gen.freemarker.JavaCodeFile;
import com.mm.util.gen.wapper.TableWapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class EasyuiServiceTaskNode extends AbstractTaskNode {

    private String templateService = "Service.ftl";
    private String templateServiceImpl = "easyui.ServiceImpl.ftl";
    private String templateAction = "easyui.Action.ftl";
    private String templateActionConfig = "ActionConfig.ftl";

    @Override
    public List<CodeFile> execute(TableWapper table, Map<String, Object> config) throws Exception {
        String basepkg = (String) config.get("basepkg");
        Map<String, Object> map = this.buildContext(table, config);
        // service
//		String name = table.getJavaName() + "Service.java";
//		String content = codeService.executeTemplate(templateService, map);
//		CodeFile serviceFile = new JavaCodeFile(basepkg + ".service", name, content);
        // serviceimpl
        String name = table.getJavaName() + "Service.java";
        String content = codeService.executeTemplate(templateServiceImpl, map);
        CodeFile serviceImplFile = new JavaCodeFile(basepkg + ".service", name, content);
        // action
        String actionName = table.getJavaName() + "Controller.java";
        String actionContent = codeService.executeTemplate(templateAction, map);
        CodeFile actionFile = new JavaCodeFile(basepkg + ".controller", actionName, actionContent);
        // action config
//		String configName = table.getJavaName() + "Action.xml";
//		String configContent = codeService.executeTemplate(templateActionConfig, map);
//		CodeFile actionConfigFile = new ConfigCodeFile("struts", configName, configContent);
        return Arrays.asList(serviceImplFile, actionFile);
    }

}
