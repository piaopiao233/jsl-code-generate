package com.jsl.codegenerate.analyze;

import cn.hutool.core.util.StrUtil;
import cn.hutool.db.meta.Column;
import cn.hutool.db.meta.Table;
import com.jsl.codegenerate.model.AnalyzeResult;
import com.jsl.codegenerate.model.GenerateConfig;
import java.sql.Types;
import java.util.Map;
import static com.jsl.codegenerate.analyze.EntityAnalyze.COMMENT_TEMPLATE;

public class EntityDtoAnalyze extends TLAnalyze {

    StringBuilder tableColumns = new StringBuilder();

    public EntityDtoAnalyze(Map<String, String> replaceVariable, Table table, GenerateConfig generateConfig, String tlPath) {
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
            //加注释
            tableColumns.append(blank).append(StrUtil.format(COMMENT_TEMPLATE, column.getComment())).append("\n");
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
            //增加Import
            addImport(javaAllType);
            //开启了knife4j
            if (this.getGenerateConfig().isEnableKnife()){
                addImport("io.swagger.v3.oas.annotations.media.Schema");
                tableColumns.append(blank).append(StrUtil.format("@Schema(description =\"{}\")", column.getComment())).append("\n");
            }
            //拼接变量和注释
            tableColumns.append(blank).append("private ").append(javaType).append(" ").append(columnName)
                    .append(";").append("//").append(column.getComment()).append("\n").append("\n");

        }
        this.generateConfig.getLeftJoinInfos().forEach(leftJoinInfo -> {
            String columnName = StrUtil.toCamelCase(leftJoinInfo.getSelectJoinTableColumnAnotherName());
            String javaAllType = leftJoinInfo.getJoinTableColumnClass().getName();
            //增加Import
            addImport(javaAllType);
            //获取对应的java类型
            String[] javaAllTypes = javaAllType.split("\\.");
            String javaType = javaAllTypes[javaAllTypes.length - 1];
            //拼接变量和注释
            if (this.getGenerateConfig().isEnableKnife()) {
                tableColumns.append(blank).append(StrUtil.format("@Schema(description =\"{}\")", columnName)).append("\n");
            }
            tableColumns.append(blank).append("private ").append(javaType).append(" ").append(columnName)
                    .append(";").append("\n").append("\n");
        });
        //输出文件名称
        String entity = replaceVariable.get("${Entity}");
        code = code.replace("${tableColumns}", tableColumns);
        code = code.replace("${import}", imports);
        code = code.replace(".entity", ".dto");
        code = code.replace(entity, entity + "Dto");
        AnalyzeResult analyzeResult = new AnalyzeResult();
        analyzeResult.setAnalyzeCodeTxt(code);
        analyzeResult.setFileName(entity + "Dto.java");
        //输出文件路径
        analyzeResult.setOutPutPath(getOutPutPath() + "/dto");
        return analyzeResult;
    }

}
