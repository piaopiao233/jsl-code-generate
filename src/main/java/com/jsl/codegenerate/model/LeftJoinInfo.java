package com.jsl.codegenerate.model;

import lombok.Data;

@Data
public class LeftJoinInfo {

    private String tableName;

    private String anotherTableName;

    private String joinOnTableColumn;

    private String joinOnAnotherTableColumn;

    private String selectJoinTableColumnName;

    private String selectJoinTableColumnAnotherName;

    private Class joinTableColumnClass;

}
