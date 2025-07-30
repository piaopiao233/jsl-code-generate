package com.jsl.codegenerate.model;


import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: piaopiao
 * @date: 2024-05-29 11:15
 */
@Data
public class GenerateConfig {

    /**
     * java代码输出目录
     */
    private String outPutJavaDir;

    /**
     * 数据源
     */
    private DataSource dataSource;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 是否是多表关联
     */
    private boolean isJoin;

    /**
     * 多表关联信息
     */
    private List<LeftJoinInfo> leftJoinInfos = new ArrayList<>();

    //控制层返回的结果
    private String controllerResultPackagePath;

    //是否启用knife4j
    private boolean isEnableKnife = false;


    public String getOutPutPath() {
        String packagePath = packageName.replaceAll("\\.", "/");
        return outPutJavaDir + "/" + packagePath;
    }

    public void addLeftJoinInfo(LeftJoinInfo leftJoinInfo){
        leftJoinInfos.add(leftJoinInfo);
    }

    public LeftJoinInfo.LeftJoinInfoBuilder leftJoinInfoBuilder(){
        return LeftJoinInfo.builder().generateConfig(this);
    }

}
