package com.jsl.codegenerate.analyze;

import cn.hutool.core.util.StrUtil;
import cn.hutool.db.meta.Column;
import cn.hutool.db.meta.Table;
import com.jsl.codegenerate.model.AnalyzeResult;
import com.jsl.codegenerate.model.GenerateConfig;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: piaopiao
 * @date: 2024-05-29 15:10
 */
public class EntityAnalyze extends TLAnalyze {

    private List<String> existImports = new ArrayList<>();

    StringBuilder tableColumns = new StringBuilder();
    StringBuilder imports = new StringBuilder();

    public EntityAnalyze(Map<String, String> replaceVariable, Table table, GenerateConfig generateConfig, String tlPath) {
        super(replaceVariable, table, generateConfig, tlPath);
    }


    @Override
    public AnalyzeResult analyze() {
        String code = getCode();
        for (String key : replaceVariable.keySet()) {
            String value = replaceVariable.get(key);
            code = code.replace(key, value);
        }
        for (Column column : table.getColumns()) {
            //主键的加上主键注释
            if (column.isPk()) {
                imports.append("import com.baomidou.mybatisplus.annotation.TableId;").append("\n");
                if (column.isAutoIncrement()) {
                    tableColumns.append(blank).append("@TableId(value = \"" + replaceVariable.get("${Primary}") + "\", type = IdType.AUTO)").append("\n");
                    imports.append("import com.baomidou.mybatisplus.annotation.IdType;").append("\n");
                } else {
                    tableColumns.append(blank).append("@TableId(value = \"" + replaceVariable.get("${Primary}") + "\")").append("\n");
                }
            }
            //设置long类型注解
            if (column.getType() == Types.BIGINT) {
                //增加import
                addImport("com.fasterxml.jackson.databind.ser.std.ToStringSerializer");
                addImport("com.fasterxml.jackson.databind.annotation.JsonSerialize");
                //增加注解
                tableColumns.append(blank).append("@JsonSerialize(using = ToStringSerializer.class)").append("\n");
            }
            //驼峰命名
            String columnName = StrUtil.toCamelCase(column.getName());
            //获取对应数据库类型的java全类名
            String javaAllType = DbTypeToJavaTypeMapper.getJavaType(column.getType());
            //获取对应的java类型
            String[] javaAllTypes = javaAllType.split("\\.");
            String javaType = javaAllTypes[javaAllTypes.length - 1];
            //是否必填
            if (!column.isNullable() && !column.isPk()) {
                if (javaType.equals("String")) {
                    //增加import
                    addImport("javax.validation.constraints.NotBlank");
                    //增加不为空注解
                    tableColumns.append(blank).append("@NotBlank(message =\"").append(column.getComment()).append("不能为空\")").append("\n");
                } else {
                    //增加import
                    addImport("javax.validation.constraints.NotNull");
                    //增加不为空注解
                    tableColumns.append(blank).append("@NotNull(message =\"").append(column.getComment()).append("不能为空\")").append("\n");
                }
            }
            //判断是否为TIME
            if (column.getType() == Types.TIME){
                //增加import
                addImport("org.springframework.format.annotation.DateTimeFormat");
                //增加时间序列化注解
                tableColumns.append(blank).append("@DateTimeFormat(pattern = \"HH:mm:ss\")").append("\n");
            }
            if (column.getType() == Types.TIMESTAMP){
                //增加import
                addImport("org.springframework.format.annotation.DateTimeFormat");
                //增加时间序列化注解
                tableColumns.append(blank).append("@DateTimeFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")").append("\n");
            }
            if (column.getType() == Types.DATE){
                //增加import
                addImport("org.springframework.format.annotation.DateTimeFormat");
                //增加时间序列化注解
                tableColumns.append(blank).append("@DateTimeFormat(pattern = \"yyyy-MM-dd\")").append("\n");
            }
            //增加Import
            addImport(javaAllType);
            //拼接变量和注释
            tableColumns.append(blank).append("private ").append(javaType).append(" ").append(columnName)
                    .append(";").append("//").append(column.getComment()).append("\n").append("\n");

        }
        code = code.replace("${tableColumns}", tableColumns);
        code = code.replace("${import}", imports);
        AnalyzeResult analyzeResult = new AnalyzeResult();
        analyzeResult.setAnalyzeCodeTxt(code);
        //输出文件名称
        String entity = replaceVariable.get("${Entity}");
        analyzeResult.setFileName(entity + ".java");
        //输出文件路径
        analyzeResult.setOutPutPath(this.getOutPutPath() + "/entity");
        return analyzeResult;
    }

    private void addImport(String addImport) {
        if (!existImports.contains(addImport)) {
            imports.append("import ").append(addImport).append(";").append("\n");
            existImports.add(addImport);
        }
    }

}
