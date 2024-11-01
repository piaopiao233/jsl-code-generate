package com.jsl.codegenerate.analyze;

import cn.hutool.core.util.StrUtil;
import cn.hutool.db.meta.Table;
import com.jsl.codegenerate.model.AnalyzeResult;
import com.jsl.codegenerate.model.GenerateConfig;
import com.jsl.codegenerate.model.LeftJoinInfo;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author: piaopiao
 * @date: 2024-05-29 14:02
 */
public class MapperXMLAnalyze extends TLAnalyze {

    private StringBuilder xmlBuilder = new StringBuilder();

    public MapperXMLAnalyze(Map<String, String> replaceVariable, Table table, GenerateConfig generateConfig, String tlPath) {
        super(replaceVariable, table, generateConfig, tlPath);
    }

    @Override
    public AnalyzeResult analyze() {
        String code = getCode();
        for (String key : replaceVariable.keySet()) {
            String value = replaceVariable.get(key);
            code = code.replace(key, value);
        }
        if (generateConfig.isJoin()) {
            buildFindXML();
            code = code.replace("${MapperXML}", xmlBuilder);
        } else {
            code = code.replace("${MapperXML}", "");
        }
        AnalyzeResult analyzeResult = new AnalyzeResult();
        analyzeResult.setAnalyzeCodeTxt(code);
        //输出文件名称
        String entity = replaceVariable.get("${Entity}");
        analyzeResult.setFileName(entity + "Mapper.xml");
        //输出文件路径
        File file = new File(generateConfig.getOutPutJavaDir());
        String outPutMapperDir = file.getParentFile().getAbsolutePath() + "/" + "resources/mapper";
        //输出文件路径
        if (StrUtil.isNotBlank(this.outPath)) {
            analyzeResult.setOutPutPath(this.outPath);
        } else {
            analyzeResult.setOutPutPath(outPutMapperDir);
        }
        return analyzeResult;
    }

    private void buildFindXML() {
        String entity = replaceVariable.get("${Entity}");
        String entityDto = entity + "Dto";
        String entityDtoPack = generateConfig.getPackageName() + "." + "dto" + "." + entityDto;
        List<LeftJoinInfo> leftJoinInfos = generateConfig.getLeftJoinInfos();
        xmlBuilder.append(blank).append("<select id=\"findPage\" resultType=\"").append(entityDtoPack).append("\">").append("\n");
        xmlBuilder.append(blank).append(blank).append("SELECT").append("\n");
        xmlBuilder.append(blank).append(blank).append("a.*");
        for (LeftJoinInfo leftJoinInfo : leftJoinInfos) {
            xmlBuilder.append(", ");
            xmlBuilder.append(leftJoinInfo.getAnotherTableName()).append(".")
                    .append(leftJoinInfo.getSelectJoinTableColumnName()).append(" as ").append(leftJoinInfo.getSelectJoinTableColumnAnotherName());
        }
        xmlBuilder.append("\n");
        xmlBuilder.append(blank).append(blank).append("FROM").append("\n");
        xmlBuilder.append(blank).append(blank).append(getTable().getTableName()).append(" a").append("\n");
        for (LeftJoinInfo leftJoinInfo : leftJoinInfos) {
            xmlBuilder.append(blank).append(blank).append("LEFT JOIN ").append(leftJoinInfo.getTableName()).append(" ")
                    .append(leftJoinInfo.getAnotherTableName()).append(" ").append("ON ").append(leftJoinInfo.getJoinOnTableColumnRules()).append("\n");
        }
        xmlBuilder.append(blank).append(blank).append("where 1=1").append("\n");
        xmlBuilder.append(blank).append(blank).append("<if test=\"ew.sqlSegment != null and ew.sqlSegment != ''\">").append("\n");
        xmlBuilder.append(blank).append(blank).append(blank).append("AND ${ew.sqlSegment}").append("\n");
        xmlBuilder.append(blank).append(blank).append("</if>").append("\n");
        xmlBuilder.append(blank).append(blank).append("order by a.id desc").append("\n");
        xmlBuilder.append(blank).append("</select>");
    }

}
