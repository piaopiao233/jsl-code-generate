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

#### 生成结果图片
entity生成展示





