package com.mm.util.gen.metadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.LinkedHashMap;
import java.util.Map;

public class DatabaseReader {
    private DatabaseReader() {
    }

    private static Map<String, String> readResultSetColumns(ResultSet rs) throws Exception {
        Map<String, String> result = new LinkedHashMap<String, String>();
        ResultSetMetaData rsmd = rs.getMetaData();
        System.out.println(rsmd.getColumnCount());
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
            String name = rsmd.getColumnName(i);
            int type = rsmd.getColumnType(i);
            result.put(name, name + "/" + type);
        }
        return result;
    }

    public static Database newDatabase(Connection conn, String catalogFilter, String schemaFilter) throws Exception {
        Database database = new Database(catalogFilter, schemaFilter);
        ResultSet rs = null;
        try {
            DatabaseMetaData dbmd = conn.getMetaData();

            try {
                rs = dbmd.getColumns(catalogFilter, schemaFilter, null, null);

                Map<String, String> columns = readResultSetColumns(rs);
                if (rs.next()) {
                    for (String name : columns.keySet()) {
                        System.out.println(columns.get(name) + " : " + rs.getObject(name));
                    }
                }

                System.out.println("++++++++++++++++++++++++");

                while (rs.next()) {
                    String catalog = rs.getString("TABLE_CAT");
                    String schema = rs.getString("TABLE_SCHEM");
                    String tableName = rs.getString("TABLE_NAME");
                    String columnName = rs.getString("COLUMN_NAME");
                    String typeName = rs.getString("TYPE_NAME");
                    String comment = rs.getString("REMARKS");
                    Table table = database.getTable(tableName);
                    if (table == null) {
                        table = new Table();
                        table.setName(tableName);
                        table.setCatalog(catalog);
                        table.setSchema(schema);
                        database.addTable(table);
                    }
                    Column column = new Column();
                    column.setName(columnName);
                    column.setDataType(typeName);
                    column.setComment(comment);

                    table.addColumn(column);
                }
            } finally {
                if (rs != null)
                    rs.close();
            }

            try {
                String[] tableNames = database.getTableNames();
                for (int i = 0; i < tableNames.length; i++) {
                    Table table = database.getTable(tableNames[i]);
                    rs = dbmd.getPrimaryKeys(catalogFilter, schemaFilter, table.getName());
                    if (rs.next()) {
                        String columnName = rs.getString("COLUMN_NAME");
                        table.setPrimaryKey(table.getColumn(columnName));
                    }
                }
            } finally {
                if (rs != null)
                    rs.close();
            }

        } finally {
            try {
                conn.rollback();
            } catch (Exception e) { /* ignore */
            }
        }
        return database;
    }

}
