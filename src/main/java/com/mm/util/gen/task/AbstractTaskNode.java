package com.mm.util.gen.task;


import com.mm.util.gen.freemarker.CodeService;
import com.mm.util.gen.freemarker.Helper;
import com.mm.util.gen.wapper.TableWapper;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractTaskNode implements TaskNode {

    @Resource
    protected CodeService codeService;

    protected Helper helper = new Helper();

    protected Map<String, Object> buildContext(TableWapper table, Map<String, Object> config) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("table", table);
        map.put("config", config);
        map.put("helper", helper);
        return map;
    }
}
