package com.mm.util.gen.metadata;

import java.util.LinkedHashMap;
import java.util.Map;

public class Table {

    private String catalog;
    private String schema;
    private String name;
    private String comment;

    private final Map<String, Column> columns = new LinkedHashMap<String, Column>();
    private Column primaryKey;

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Map<String, Column> getColumns() {
        return columns;
    }

    public void addColumn(Column column) {
        this.columns.put(column.getName().toUpperCase(), column);
    }

    public Column getColumn(String name) {
        return this.columns.get(name);
    }

    public Column getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Column primaryKey) {
        this.primaryKey = primaryKey;
    }
}
