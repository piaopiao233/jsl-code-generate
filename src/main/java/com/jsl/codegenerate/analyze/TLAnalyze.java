package com.jsl.codegenerate.analyze;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.db.meta.Table;
import com.jsl.codegenerate.model.AnalyzeResult;
import com.jsl.codegenerate.model.GenerateConfig;
import lombok.Data;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: piaopiao
 * @date: 2024-05-29 13:54
 */
@Data
public abstract class TLAnalyze {

    public static final String blank ="    ";

    //注释模板
    public static final String COMMENT_TEMPLATE = "/**\n" +
            "     * {}\n" +
            "     */";

    public abstract AnalyzeResult analyze();

    protected String tlPath;

    protected String outPath;

    //需要替换的变量
    protected Map<String, String> replaceVariable;

    //表结构
    protected Table table;

    //导入包
    protected List<String> existImports = new ArrayList<>();

    protected StringBuilder imports = new StringBuilder();

    //配置项
    protected GenerateConfig generateConfig;

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

    protected void addImport(String addImport) {
        if (!existImports.contains(addImport)) {
            imports.append("import ").append(addImport).append(";").append("\n");
            existImports.add(addImport);
        }
    }
}
