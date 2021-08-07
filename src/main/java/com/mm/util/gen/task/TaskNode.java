package com.mm.util.gen.task;


import com.mm.util.gen.freemarker.CodeFile;
import com.mm.util.gen.wapper.TableWapper;

import java.util.List;
import java.util.Map;

public interface TaskNode {

    public List<CodeFile> execute(TableWapper table, Map<String, Object> config) throws Exception;

}
