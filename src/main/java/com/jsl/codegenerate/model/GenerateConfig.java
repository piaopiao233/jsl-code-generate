package com.jsl.codegenerate.model;


/**
 * @author: piaopiao
 * @date: 2024-05-29 11:15
 */
public class GenerateConfig {

    private String outPutJavaDir;

    private DataSource dataSource;

    private String packageName;

    //控制层返回的结果
    private String controllerResultPackagePath;

    public String getOutPutJavaDir() {
        return outPutJavaDir;
    }

    public void setOutPutJavaDir(String outPutJavaDir) {
        this.outPutJavaDir = outPutJavaDir;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getOutPutPath() {
        String packagePath = packageName.replaceAll("\\.", "/");
        return outPutJavaDir + "/" + packagePath;
    }

    public String getControllerResultPackagePath() {
        return controllerResultPackagePath;
    }

    public void setControllerResultPackagePath(String controllerResultPackagePath) {
        this.controllerResultPackagePath = controllerResultPackagePath;
    }
}
