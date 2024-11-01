package com.jsl.codegenerate.analyze;


import cn.hutool.core.util.StrUtil;
import cn.hutool.db.meta.Table;
import com.jsl.codegenerate.model.AnalyzeResult;
import com.jsl.codegenerate.model.GenerateConfig;

import java.util.Map;

/**
 * @author: piaopiao
 * @date: 2024-05-29 14:03
 */
public class ServiceImplAnalyze extends TLAnalyze{


    public ServiceImplAnalyze(Map<String, String> replaceVariable, Table table, GenerateConfig generateConfig, String tlPath) {
        super(replaceVariable, table, generateConfig, tlPath);
    }

    @Override
    public AnalyzeResult analyze() {
        String code = getCode();
        for (String key : replaceVariable.keySet()) {
            String value = replaceVariable.get(key);
            code = code.replace(key, value);
        }
        if (this.generateConfig.isJoin()){
            code = code.replace("${implFindPage}","page(page)");
        }else {
            code = code.replace("${implFindPage}","this.baseMapper.findPage(page, queryWrapper)");
        }
        AnalyzeResult analyzeResult = new AnalyzeResult();
        analyzeResult.setAnalyzeCodeTxt(code);
        //输出文件名称
        String entity = replaceVariable.get("${Entity}");
        analyzeResult.setFileName(entity + "ServiceImpl.java");
        //输出文件路径
        analyzeResult.setOutPutPath(getOutPutPath() + "/service/impl");
        return analyzeResult;
    }

}
