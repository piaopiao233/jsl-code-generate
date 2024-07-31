package com.jsl.codegenerate.main;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.ds.simple.SimpleDataSource;
import cn.hutool.db.meta.Column;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.Table;
import com.jsl.codegenerate.analyze.*;
import com.jsl.codegenerate.model.AnalyzeResult;
import com.jsl.codegenerate.model.GenerateConfig;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author: piaopiao
 * @date: 2024-05-29 15:31
 */
public class AutoGenerator {

    //生成配置
    private GenerateConfig generateConfig;

    private Map<String, TLAnalyze> tlAnalyzes = new HashMap<>();

    //表名
    private String tableName;

    //表结构
    private Table table;

    //需要替换的变量
    private Map<String, String> replaceVariable;

    public AutoGenerator(GenerateConfig generateConfig, String tableName) {
        this.generateConfig = generateConfig;
        this.tableName = tableName;
        //解析表数据
        SimpleDataSource simpleDataSource = new SimpleDataSource(generateConfig.getDataSource().getUrl(), generateConfig.getDataSource().getUsername(),
                generateConfig.getDataSource().getPassword(), generateConfig.getDataSource().getDriverClassName());
        this.table = MetaUtil.getTableMeta(simpleDataSource, tableName);
        //将表名转为对象名称
        // 先转换为驼峰命名
        String camelCaseEntity = StrUtil.toCamelCase(tableName);
        // 然后确保首字母大写
        String entity = StrUtil.upperFirst(camelCaseEntity);
        //填充replaceVariable
        //获取主键
        String primary = this.table.getPkNames().iterator().next();
        replaceVariable = new HashMap<>();
        replaceVariable.put("${packageName}", generateConfig.getPackageName());
        replaceVariable.put("${controllerResultPackagePath}", generateConfig.getControllerResultPackagePath());
        replaceVariable.put("${Entity}", entity);
        replaceVariable.put("${entity}", camelCaseEntity);
        //获取主键类型
        Column primaryColumn = table.getColumn(primary);
        String javaAllType = DbTypeToJavaTypeMapper.getJavaType(primaryColumn.getType());
        //获取对应的java类型
        String[] javaAllTypes = javaAllType.split("\\.");
        String primaryType = javaAllTypes[javaAllTypes.length - 1];
        replaceVariable.put("${PrimaryType}", primaryType);
        replaceVariable.put("${Primary}", primary);
        String[] controllerResultPackagePaths = generateConfig.getControllerResultPackagePath().split("\\.");
        replaceVariable.put("${controllerResultName}", controllerResultPackagePaths[controllerResultPackagePaths.length - 1]);
    }

    public void setDefaultAnalyzes() {
        tlAnalyzes.put("ControllerAnalyze", new ControllerAnalyze(replaceVariable, table, generateConfig, "ftl/DefaultController.tl"));
        tlAnalyzes.put("MapperAnalyze", new MapperAnalyze(replaceVariable, table, generateConfig, "ftl/DefaultMapper.tl"));
        tlAnalyzes.put("MapperXMLAnalyze", new MapperXMLAnalyze(replaceVariable, table, generateConfig, "ftl/DefaultMapperXML.tl"));
        tlAnalyzes.put("ServiceAnalyze", new ServiceAnalyze(replaceVariable, table, generateConfig, "ftl/DefaultService.tl"));
        tlAnalyzes.put("ServiceImplAnalyze", new ServiceImplAnalyze(replaceVariable, table, generateConfig, "ftl/DefaultServiceImpl.tl"));
        tlAnalyzes.put("VueElementTableAnalyze", new VueElementTableAnalyze(replaceVariable, table, generateConfig, "ftl/DefaultVueElementTable.tl"));
        tlAnalyzes.put("EntityAnalyze", new EntityAnalyze(replaceVariable, table, generateConfig, "ftl/DefaultEntity.tl"));
    }

    public void addAnalyzes(String name, Class<? extends TLAnalyze> analyzeClass, String tlPath) {
        TLAnalyze tlAnalyze = ReflectUtil.newInstance(analyzeClass, replaceVariable, table, generateConfig, tlPath);
        tlAnalyzes.put(name, tlAnalyze);
    }

    public void execute() {
        List<Column> columns = new ArrayList<>(table.getColumns());
        Assert.isTrue(columns.size() > 0, tableName + "表不存在");
        List<AnalyzeResult> analyzeResults = new ArrayList<>();
        for (TLAnalyze tlAnalyze : tlAnalyzes.values()) {
            AnalyzeResult controllerAnalyzeResult = tlAnalyze.analyze();
            analyzeResults.add(controllerAnalyzeResult);
        }
        for (AnalyzeResult analyzeResult : analyzeResults) {
            Assert.notBlank(analyzeResult.getOutPutPath(), "输出路径有误");
            Assert.notBlank(analyzeResult.getFileName(), "文件名有误");
            String out = analyzeResult.getOutPutPath() + "/" + analyzeResult.getFileName();
            if(!FileUtil.exist(analyzeResult.getOutPutPath())){
                FileUtil.writeString(analyzeResult.getAnalyzeCodeTxt(), out, StandardCharsets.UTF_8);
            }
        }
    }

    public GenerateConfig getGenerateConfig() {
        return generateConfig;
    }

    public void setGenerateConfig(GenerateConfig generateConfig) {
        this.generateConfig = generateConfig;
    }

}
