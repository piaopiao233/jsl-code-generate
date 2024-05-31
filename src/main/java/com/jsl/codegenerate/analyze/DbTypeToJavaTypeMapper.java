package com.jsl.codegenerate.analyze;

import java.math.BigDecimal;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DbTypeToJavaTypeMapper {

    private static final Map<Integer, String> dbTypeToJavaTypeMap;

    static {
        dbTypeToJavaTypeMap = new HashMap<>();

        // 精确数值类型  
        dbTypeToJavaTypeMap.put(Types.BIT, Boolean.class.getName()); // 注意：这取决于数据库和JDBC驱动如何处理BIT类型  
        dbTypeToJavaTypeMap.put(Types.TINYINT, Integer.class.getName());
        dbTypeToJavaTypeMap.put(Types.SMALLINT, Short.class.getName());
        dbTypeToJavaTypeMap.put(Types.INTEGER, Integer.class.getName());
        dbTypeToJavaTypeMap.put(Types.BIGINT, Long.class.getName());

        // 浮点数类型  
        dbTypeToJavaTypeMap.put(Types.FLOAT, Float.class.getName());
        dbTypeToJavaTypeMap.put(Types.REAL, Float.class.getName()); // REAL在某些数据库中可能是FLOAT的别名  
        dbTypeToJavaTypeMap.put(Types.DOUBLE, Double.class.getName());
        dbTypeToJavaTypeMap.put(Types.DECIMAL, BigDecimal.class.getName());
        dbTypeToJavaTypeMap.put(Types.NUMERIC, BigDecimal.class.getName()); // NUMERIC在某些数据库中与DECIMAL相同  

        // 字符类型  
        dbTypeToJavaTypeMap.put(Types.CHAR, String.class.getName());
        dbTypeToJavaTypeMap.put(Types.VARCHAR, String.class.getName());
        dbTypeToJavaTypeMap.put(Types.LONGVARCHAR, String.class.getName()); // 通常可以用String处理  

        // 二进制类型  
        dbTypeToJavaTypeMap.put(Types.BINARY, byte[].class.getName());
        dbTypeToJavaTypeMap.put(Types.VARBINARY, byte[].class.getName());
        dbTypeToJavaTypeMap.put(Types.LONGVARBINARY, byte[].class.getName()); // 通常可以用byte[]处理  

        // 日期时间类型  
        dbTypeToJavaTypeMap.put(Types.DATE, LocalDate.class.getName());
        dbTypeToJavaTypeMap.put(Types.TIME, LocalTime.class.getName());
        dbTypeToJavaTypeMap.put(Types.TIMESTAMP, LocalDateTime.class.getName());

        // 其他类型  
        dbTypeToJavaTypeMap.put(Types.ARRAY, Object[].class.getName()); // 数组类型可能需要特殊处理  
        dbTypeToJavaTypeMap.put(Types.BLOB, java.sql.Blob.class.getName());
        dbTypeToJavaTypeMap.put(Types.CLOB, java.sql.Clob.class.getName());
        dbTypeToJavaTypeMap.put(Types.DISTINCT, Object.class.getName()); // DISTINCT类型可能需要特殊处理  
        dbTypeToJavaTypeMap.put(Types.JAVA_OBJECT, Object.class.getName()); // 可能是任何Java对象，取决于具体实现  

        // 其他未明确列出的类型可以映射到Object.class，或者根据需要进行扩展  
        // ...  

        // 默认值，如果找不到匹配的类型  
        dbTypeToJavaTypeMap.put(Types.OTHER, Object.class.getName());
    }

    public static String getJavaType(int dbType) {
        return dbTypeToJavaTypeMap.getOrDefault(dbType, Object.class.getName());
    }

    // ... 其他可能的方法 ...  
}