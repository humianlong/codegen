package com.mm.util.gen.wapper;

import com.mm.util.gen.metadata.Database;
import com.mm.util.gen.metadata.Table;
import com.mm.util.gen.metadata.TypeConverter;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DatabaseWapper implements Wapper<Database> {

    private Database database;
    private Map<String, TableWapper> tables = new HashMap<String, TableWapper>();

    public DatabaseWapper(Database database, TypeConverter typeConverter) {
        super();
        this.database = database;
        for (String name : database.getTableNames()) {
            Table table = database.getTable(name);
            this.tables.put(name, new TableWapper(table, typeConverter));
        }
    }

    @Override
    public Database getTarget() {
        return this.database;
    }

    public String getCatalog() {
        return database.getCatalog();
    }

    public String getSchema() {
        return database.getSchema();
    }

    public TableWapper getTable(String name) {
        return tables.get(name.toUpperCase(Locale.ENGLISH));
    }

    public String[] getTableNames() {
        return tables.keySet().toArray(new String[tables.size()]);
    }
}
