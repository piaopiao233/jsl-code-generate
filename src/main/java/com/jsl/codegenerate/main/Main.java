package com.jsl.codegenerate.main;

import com.jsl.codegenerate.model.DataSource;
import com.jsl.codegenerate.model.GenerateConfig;


/**
 * @author: piaopiao
 * @date: 2024-05-29 10:48
 */
public class Main {

    public static void main(String[] args) {
        DataSource dsc = new DataSource();
        dsc.setUrl("jdbc:mysql://localhost:3306/iep?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai");
        dsc.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("pwd");
        GenerateConfig generateConfig = new GenerateConfig();
        generateConfig.setDataSource(dsc);
        String projectPath = System.getProperty("user.dir");
        generateConfig.setOutPutJavaDir(projectPath + "/src/main/java");
        generateConfig.setPackageName("com.jsl.codegenerate");
        generateConfig.setControllerResultPackagePath("com.jsl.codegenerate.model.DataSource");
        AutoGenerator autoGenerator = new AutoGenerator(generateConfig,"people");
        autoGenerator.setDefaultAnalyzes();
        autoGenerator.execute();
    }
}
