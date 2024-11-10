package com.jsl.codegenerate.analyze;

import cn.hutool.db.meta.Table;
import com.jsl.codegenerate.model.AnalyzeResult;
import com.jsl.codegenerate.model.GenerateConfig;

import java.util.Map;

/**
 * @author: piaopiao
 * @date: 2024-05-29 14:02
 */
public class MapperAnalyze extends TLAnalyze {

    public MapperAnalyze(Map<String, String> replaceVariable, Table table, GenerateConfig generateConfig, String tlPath) {
        super(replaceVariable, table, generateConfig, tlPath);
    }

    @Override
    public AnalyzeResult analyze() {
        String code = getCode();
        for (String key : replaceVariable.keySet()) {
            String value = replaceVariable.get(key);
            code = code.replace(key, value);
        }
        String entity = replaceVariable.get("${Entity}");
        String entityDto = entity + "Dto";
        if (generateConfig.isJoin()) {
            //生成分页
            addImport("com.baomidou.mybatisplus.core.conditions.Wrapper");
            addImport("com.baomidou.mybatisplus.core.toolkit.Constants");
            addImport("com.baomidou.mybatisplus.extension.plugins.pagination.Page");
            addImport("org.apache.ibatis.annotations.Param");
            //导入自己的Dto
            addImport(generateConfig.getPackageName() + ".dto." + entityDto);
            code = code.replace("${mapperImport}", imports);
            String findPageName = "Page<" + entityDto + "> findPage(Page page, @Param(Constants.WRAPPER) Wrapper wrapper);";
            code = code.replace("${mapperMethod}", blank + findPageName + "\n");
        } else {
            code = code.replace("${mapperImport}", "");
            code = code.replace("${mapperMethod}", "");
        }
        AnalyzeResult analyzeResult = new AnalyzeResult();
        analyzeResult.setAnalyzeCodeTxt(code);
        //输出文件名称
        analyzeResult.setFileName(entity + "Mapper.java");
        //输出文件路径
        analyzeResult.setOutPutPath(getOutPutPath() + "/mapper");
        return analyzeResult;
    }


}
