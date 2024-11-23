package com.jsl.codegenerate.main;

import com.jsl.codegenerate.model.DataSource;
import com.jsl.codegenerate.model.GenerateConfig;
import com.jsl.codegenerate.model.LeftJoinInfo;


/**
 * @author: piaopiao
 * @date: 2024-05-29 10:48
 */
public class Main {

    public static void main(String[] args) {
        //数据库配置
        DataSource dsc = new DataSource();
        dsc.setUrl("jdbc:mysql://192.168.0.132:3306/iep?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai");
        dsc.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
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
        generateConfig.setJoin(true);
        LeftJoinInfo leftJoinInfo1 = LeftJoinInfo.builder().tableName("dict").anotherTableName("b").joinOnTableColumnRules("a.apply_school_dict_id = b.id")
                .selectJoinTableColumnName("cotent").selectJoinTableColumnAnotherName("apply_school_name").joinTableColumnClass(String.class).build();
        generateConfig.addLeftJoinInfo(leftJoinInfo1);
        LeftJoinInfo leftJoinInfo2= LeftJoinInfo.builder().tableName("sys_user").anotherTableName("c").joinOnTableColumnRules("a.apply_user_id = c.id")
                .selectJoinTableColumnName("name").selectJoinTableColumnAnotherName("apply_username").joinTableColumnClass(String.class).build();
        generateConfig.addLeftJoinInfo(leftJoinInfo2);
        LeftJoinInfo leftJoinInfo3= LeftJoinInfo.builder().tableName("dict").anotherTableName("d").joinOnTableColumnRules("a.receive_school_dict_id = d.id")
                .selectJoinTableColumnName("cotent").selectJoinTableColumnAnotherName("receive_school_name").joinTableColumnClass(String.class).build();
        generateConfig.addLeftJoinInfo(leftJoinInfo3);

        LeftJoinInfo leftJoinInfo4= LeftJoinInfo.builder().tableName("sys_user").anotherTableName("e").joinOnTableColumnRules("a.handle_user_id = e.id")
                .selectJoinTableColumnName("name").selectJoinTableColumnAnotherName("handle_username").joinTableColumnClass(String.class).build();
        generateConfig.addLeftJoinInfo(leftJoinInfo4);

        LeftJoinInfo leftJoinInfo5 = LeftJoinInfo.builder().tableName("student").anotherTableName("f").joinOnTableColumnRules("a.student_id = f.id")
                .selectJoinTableColumnName("name").selectJoinTableColumnAnotherName("student_name").joinTableColumnClass(String.class).build();
        generateConfig.addLeftJoinInfo(leftJoinInfo5);

        LeftJoinInfo leftJoinInfo6 = LeftJoinInfo.builder().tableName("team").anotherTableName("g").joinOnTableColumnRules("a.team_id = g.id")
                .selectJoinTableColumnName("name").selectJoinTableColumnAnotherName("team_name").joinTableColumnClass(String.class).build();
        generateConfig.addLeftJoinInfo(leftJoinInfo6);
        AutoGenerator autoGenerator = new AutoGenerator(generateConfig,"student_extraction_apply");
        autoGenerator.setDefaultAnalyzes();
        autoGenerator.execute();
    }
}
