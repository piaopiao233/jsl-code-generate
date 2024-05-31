package com.jsl.codegenerate.analyze;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.meta.Table;
import com.jsl.codegenerate.model.AnalyzeResult;
import com.jsl.codegenerate.model.GenerateConfig;

import java.io.InputStream;
import java.util.Map;

/**
 * @author: piaopiao
 * @date: 2024-05-29 13:54
 */
public abstract class TLAnalyze {

    public String blank ="    ";

    public abstract AnalyzeResult analyze();

    protected String tlPath;

    //需要替换的变量
    protected Map<String, String> replaceVariable;

    //表结构
    protected Table table;

    //配置项
    protected GenerateConfig generateConfig;

    public String getTlPath() {
        return tlPath;
    }

    public void setTlPath(String tlPath) {
        this.tlPath = tlPath;
    }

    public TLAnalyze(Map<String, String> replaceVariable, Table table, GenerateConfig generateConfig, String tlPath) {
        this.tlPath = tlPath;
        this.replaceVariable = replaceVariable;
        this.table = table;
        this.generateConfig = generateConfig;
    }

    public String getOutPutPath() {
        return generateConfig.getOutPutPath();
    }

    public String getCode() {
        Assert.notBlank(tlPath);
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(tlPath);
        return IoUtil.readUtf8(inputStream);
    }
}
