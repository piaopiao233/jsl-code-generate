# jsl-code-generate

#### 介绍
java的springboot框架代码生成器，支持生成表的分页，删除，新增，修改，支持生成vue页面。生成的代码需要依赖springboot和mybatis-plus框架。

#### 使用说明
```
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
```

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)