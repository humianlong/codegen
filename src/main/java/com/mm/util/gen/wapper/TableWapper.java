package com.mm.util.gen.wapper;


import com.mm.util.gen.metadata.Column;
import com.mm.util.gen.metadata.Table;
import com.mm.util.gen.metadata.TypeConverter;
import com.mm.util.gen.utils.NameUtils;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class TableWapper implements Wapper<Table> {

    private Table table;
    private ColumnWapper primaryKey;
    private Map<String, ColumnWapper> columns = new LinkedHashMap<String, ColumnWapper>();

    private String javaName;
    private boolean deletedEntity;
    private boolean operationEntity;
    private boolean groupEntity;

    public TableWapper(Table table, TypeConverter typeConverter) {
        super();
        this.table = table;
        if (table.getPrimaryKey() != null) {
            this.primaryKey = new ColumnWapper(table.getPrimaryKey(), typeConverter);
        }
        for (Map.Entry<String, Column> entry : table.getColumns().entrySet()) {
            this.columns.put(entry.getKey(), new ColumnWapper(entry.getValue(), typeConverter));
            if (entry.getKey().equals("DELETED")) {
                this.deletedEntity = true;
            }
            if (entry.getKey().equals("MODIFY_BY")) {
                this.operationEntity = true;
            }
            if (entry.getKey().equals("CORP_CODE")) {
                this.groupEntity = true;
            }
        }
        // 根据规则生成实体名称
        this.javaName = NameUtils.tableNameToClassName(this.getName());
    }

    @Override
    public Table getTarget() {
        return this.table;
    }

    public String getCatalog() {
        return table.getCatalog();
    }

    public String getSchema() {
        return table.getSchema();
    }

    public String getName() {
        return table.getName().toLowerCase();
    }

    public String getComment() {
        return table.getComment();
    }

    public Map<String, ColumnWapper> getColumnMap() {
        return this.columns;
    }

    public Collection<ColumnWapper> getColumns() {
        return this.columns.values();
    }

    public ColumnWapper getColumn(String name) {
        return this.columns.get(name);
    }

    public ColumnWapper getPrimaryKey() {
        return this.primaryKey;
    }

    public String getJavaName() {
        return this.javaName;
    }

    public String getJavaNameL() {
        return NameUtils.firstLowerCase(this.javaName);
    }

    public String getJavaNamePage() {
        return this.javaName.toLowerCase();
    }

    public boolean isDeletedEntity() {
        return deletedEntity;
    }

    public boolean isOperationEntity() {
        return operationEntity;
    }

    public boolean isGroupEntity() {
        return groupEntity;
    }
}
