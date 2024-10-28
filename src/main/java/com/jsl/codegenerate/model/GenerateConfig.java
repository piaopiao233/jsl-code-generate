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

    private String outPutJavaDir;

    private DataSource dataSource;

    private String packageName;

    private boolean isJoin;

    private List<LeftJoinInfo> leftJoinInfos = new ArrayList<>();

    //控制层返回的结果
    private String controllerResultPackagePath;


    public String getOutPutPath() {
        String packagePath = packageName.replaceAll("\\.", "/");
        return outPutJavaDir + "/" + packagePath;
    }

}
