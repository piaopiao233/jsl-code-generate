package com.jsl.codegenerate.model;


import lombok.Data;

@Data
/**
 * @author: piaopiao
 * @date: 2024-05-29 10:46
 */
public class DataSource {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
}
