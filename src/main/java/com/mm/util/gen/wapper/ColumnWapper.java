package com.mm.util.gen.wapper;


import com.mm.util.gen.metadata.Column;
import com.mm.util.gen.metadata.TypeConverter;
import com.mm.util.gen.utils.NameUtils;

import java.util.Arrays;
import java.util.List;

public class ColumnWapper implements Wapper<Column> {

    private static final List<String> ignoreViewProps = Arrays.asList("id", "deleted", "createBy", "createTime",
            "modifyBy", "modifyTime", "corpCode");

    private static final List<String> ignoreConditionProps = Arrays.asList("id", "createBy", "createTime", "modifyBy",
            "modifyTime");

    private Column column;

    private String javaName;

    private TypeConverter typeConverter;

    public ColumnWapper(Column column, TypeConverter typeConverter) {
        super();
        this.column = column;
        this.typeConverter = typeConverter;
        this.javaName = NameUtils.firstLowerCase(NameUtils.columnNameToPropName(this.getName()));
    }

    public Column getTarget() {
        return column;
    }

    public String getName() {
        return column.getName();
    }

    public Long getOrdinal() {
        return column.getOrdinal();
    }

    public String getType() {
        return column.getType();
    }

    public String getDataType() {
        return column.getDataType();
    }

    public String getDefValue() {
        return column.getDefValue();
    }

    public String getNullable() {
        return column.getNullable();
    }

    public Long getLength() {
        return column.getLength();
    }

    public Long getPrecision() {
        return column.getPrecision();
    }

    public Long getScale() {
        return column.getScale();
    }

    public String getKey() {
        return column.getKey();
    }

    public String getExtra() {
        return column.getExtra();
    }

    public String getComment() {
        return column.getComment();
    }

    public boolean getView() {
        return !ignoreViewProps.contains(this.javaName);
    }

    public boolean getCondition() {
        return !ignoreConditionProps.contains(this.javaName);
    }

    public boolean getStringType() {
        return this.getJavaType().equals("String");
    }

    public String getJavaName() {
        return this.javaName;
    }

    public String getJavaType() {
        return this.typeConverter.toJavaType(this.column);
    }

    public String getJdbcType() {
        return this.typeConverter.toJdbcType(this.column);
    }
}
