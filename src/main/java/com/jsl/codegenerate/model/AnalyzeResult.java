package com.jsl.codegenerate.model;

/**
 * @author: piaopiao
 * @date: 2024-05-29 14:21
 */
public class AnalyzeResult {

   private String analyzeCodeTxt;

   private String outPutPath;

   private String fileName;

   public String getAnalyzeCodeTxt() {
      return analyzeCodeTxt;
   }

   public void setAnalyzeCodeTxt(String analyzeCodeTxt) {
      this.analyzeCodeTxt = analyzeCodeTxt;
   }

   public String getOutPutPath() {
      return outPutPath;
   }

   public void setOutPutPath(String outPutPath) {
      this.outPutPath = outPutPath;
   }

   public String getFileName() {
      return fileName;
   }

   public void setFileName(String fileName) {
      this.fileName = fileName;
   }
}
