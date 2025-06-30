package com.jsl.codegenerate.analyze;

import cn.hutool.core.util.StrUtil;
import cn.hutool.db.meta.Table;
import com.jsl.codegenerate.model.AnalyzeResult;
import com.jsl.codegenerate.model.GenerateConfig;

import java.util.Map;

/**
 * @author: piaopiao
 * @date: 2024-05-29 14:01
 */
public class ControllerAnalyze extends TLAnalyze {


    public ControllerAnalyze(Map<String, String> replaceVariable, Table table, GenerateConfig generateConfig, String tlPath) {
        super(replaceVariable, table, generateConfig, tlPath);
    }

    @Override
    public AnalyzeResult analyze() {
        String pageResultName;
        if (this.getGenerateConfig().isEnableKnife()){
            this.setTlPath("ftl/KnifeController.tl");
        }
        pageResultName = "{}<Page<{}>>";
        if (this.getGenerateConfig().isJoin()){
            pageResultName = StrUtil.format(pageResultName, replaceVariable.get("${ResultName}"), replaceVariable.get("${EntityDto}"));
            replaceVariable.put("${PageGenerics}", replaceVariable.get("${EntityDto}"));
            //增加dto import
            addImport(StrUtil.format("{}.dto.{}", replaceVariable.get("${packageName}"), replaceVariable.get("${EntityDto}")));
        }else {
            pageResultName = StrUtil.format(pageResultName, replaceVariable.get("${ResultName}"), replaceVariable.get("${Entity}"));
            replaceVariable.put("${PageGenerics}", replaceVariable.get("${Entity}"));
        }
        replaceVariable.put("${PageResultName}", pageResultName);
        String code = getCode();
        for (String key : replaceVariable.keySet()) {
            String value = replaceVariable.get(key);
            code = code.replace(key, value);
        }
        code = code.replace("${ControllerImport}", imports);
        AnalyzeResult analyzeResult = new AnalyzeResult();
        analyzeResult.setAnalyzeCodeTxt(code);
        //输出文件名称
        String entity = replaceVariable.get("${Entity}");
        analyzeResult.setFileName(entity + "Controller.java");
        //输出文件路径
        analyzeResult.setOutPutPath(getOutPutPath() + "/controller");
        return analyzeResult;
    }
}
