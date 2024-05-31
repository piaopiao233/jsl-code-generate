package com.jsl.codegenerate.main;

import com.jsl.codegenerate.model.DataSource;
import com.jsl.codegenerate.model.GenerateConfig;


/**
 * @author: piaopiao
 * @date: 2024-05-29 10:48
 */
public class Main {

    public static void main(String[] args) {
        //数据库配置
        DataSource dsc = new DataSource();
        dsc.setUrl("jdbc:mysql://localhost:3306/iep?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai");
        dsc.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("pwd");
        //生成配置
        GenerateConfig generateConfig = new GenerateConfig();
        generateConfig.setDataSource(dsc);
        String projectPath = System.getProperty("user.dir");
        //代码输出目录
        generateConfig.setOutPutJavaDir(projectPath + "/src/main/java");
        //包名
        generateConfig.setPackageName("com.jsl.codegenerate");
        //Controller层返回结果
        generateConfig.setControllerResultPackagePath("com.jsl.codegenerate.model.Result");
        //people需要生成的表名
        AutoGenerator autoGenerator = new AutoGenerator(generateConfig,"people");
        autoGenerator.setDefaultAnalyzes();
        autoGenerator.execute();
    }
}
