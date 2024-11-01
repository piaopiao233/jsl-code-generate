package com.jsl.codegenerate.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LeftJoinInfo {

    //关联的其他表名
    private String tableName;

    //关联的其他表名的别名
    private String anotherTableName;

    //表之间关联的规则
    private String joinOnTableColumnRules;

    //需要查询的其他表的列名
    private String selectJoinTableColumnName;

    //需要查询的其他表的列名的别名
    private String selectJoinTableColumnAnotherName;

    //需要查询的其他表的列名的java对象
    private Class joinTableColumnClass;

}
