package com.jsl.codegenerate.analyze;

import cn.hutool.core.util.StrUtil;
import cn.hutool.db.meta.Column;
import cn.hutool.db.meta.Table;
import cn.hutool.json.JSONUtil;
import com.jsl.codegenerate.model.AnalyzeResult;
import com.jsl.codegenerate.model.GenerateConfig;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: piaopiao
 * @date: 2024-05-29 17:12
 */
public class VueElementTableAnalyze extends TLAnalyze {


    StringBuilder tableCloums = new StringBuilder();
    StringBuilder formList = new StringBuilder();
    Map<String, Object> formRules = new HashMap();
    Map<String, Object> addForm = new HashMap();

    public VueElementTableAnalyze(Map<String, String> replaceVariable, Table table, GenerateConfig generateConfig, String tlPath) {
        super(replaceVariable, table, generateConfig, tlPath);
    }

    @Override
    public AnalyzeResult analyze() {
        String code = getCode();
        for (String key : replaceVariable.keySet()) {
            String value = replaceVariable.get(key);
            code = code.replace(key, value);
        }

        //添加行
        for (Column column : table.getColumns()) {
            //生成表格代码
            String columnName = StrUtil.toCamelCase(column.getName());
            //设置addForm
            addForm.put(columnName, "");
            if (column.isPk()) {
                continue;
            }
            tableCloums.append("<el-table-column label='").append(column.getComment()).append("' prop='").append(columnName).append("'></el-table-column>");
            //生成表单代码
            //先判断是否室时间
            //判断是否为TIME
            String form;
            if (column.getType() == Types.TIME) {
                form = "<el-form-item  prop='{}' label='{}'><el-time-picker format='HH:mm:ss' value-format='HH:mm:ss' v-model='addForm.{}' placeholder='选择{}'></el-time-picker></el-form-item>";
                form = StrUtil.format(form, columnName, column.getComment(), columnName, column.getComment());
            } else if (column.getType() == Types.TIMESTAMP) {
                form = "<el-form-item label='{}' prop='{}'><el-date-picker v-model='addForm.{}' type='datetime' placeholder='选择{}' value-format='yyyy-MM-dd HH:mm:ss'></el-date-picker></el-form-item>";
                form = StrUtil.format(form, column.getComment(), columnName, columnName, column.getComment());
            } else if (column.getType() == Types.DATE) {
                form = "<el-form-item label='{}' prop='{}'><el-date-picker v-model='addForm.{}' type='date' placeholder='选择{}' value-format='yyyy-MM-dd'></el-date-picker></el-form-item>";
                form = StrUtil.format(form, column.getComment(), columnName, columnName, column.getComment());
            } else {
                form = "<el-form-item label='{}' prop='{}' label-width='80px'><el-input v-model='addForm.{}' placeholder='请输入{}' autocomplete='off'/></el-form-item>";
                form = StrUtil.format(form, column.getComment(), columnName, columnName, column.getComment());
            }
            formList.append(form);
            //设置formRules
            if (!column.isNullable()) {
                List<Map> rules = setFormRules(column);
                formRules.put(columnName, rules);
            }
        }
        code = code.replace("${tableCloums}", tableCloums);
        code = code.replace("${formList}", formList);
        code = code.replace("${formRules}", JSONUtil.toJsonStr(formRules));
        code = code.replace("${addForm}", JSONUtil.toJsonStr(addForm));
        AnalyzeResult analyzeResult = new AnalyzeResult();
        analyzeResult.setAnalyzeCodeTxt(code);
        //输出文件名称
        String entity = replaceVariable.get("${Entity}");
        analyzeResult.setFileName(entity + ".vue");
        //输出文件路径
        analyzeResult.setOutPutPath("C:\\Users\\Administrator\\Desktop");
        return analyzeResult;
    }

    private List<Map> setFormRules(Column column) {
        List<Map> list = new ArrayList<>();
        Map map = new HashMap();
        map.put("required", true);
        map.put("message", "请输入" + column.getComment());
        map.put("trigger", "blur");
        list.add(map);
        return list;
    }


}
