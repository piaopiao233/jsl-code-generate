<template>
    <div>
        <el-form :inline="true">
            <el-form-item>
                <el-button size="mini" type="primary" @click="handleAdd">新增</el-button>
            </el-form-item>
        </el-form>
        <el-table :data="tableData" tooltip-effect="dark" style="width:100%" border stripe>
            <el-table-column label="序号" width="60">
                <template slot-scope="scope">
                    <div>{{scope.$index+(current - 1) * size + 1}}</div>
                </template>
            </el-table-column>
            ${tableCloums}
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button size="mini" @click="editOpen(scope.row)">编辑</el-button>
                    <el-popconfirm title="是否确认删除？" @confirm="del(scope.row.${Primary})">
                        <el-button style="margin-left: 10px" slot="reference" type="danger" size="mini">删除</el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                       layout="total, sizes, prev, pager, next, jumper"
                       :page-sizes="[10, 20, 50, 100]" :current-page.sync="current" :page-size="size" :total="total"
                       style="margin-top: 20px"/>
        <el-dialog :close-on-click-modal="false" :title="type" :visible.sync="dialogVisible" width="600px" v-dialogDrag>
            <el-form :model="addForm" ref="addForm" :rules="formRules" label-width="80px">
                ${formList}
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="submitForm">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    export default {
        name: 'Templeate',
        data() {
            return {
                dialogVisible: false,
                tableData: [],
                type: '新增',
                current: 1,
                size: 10,
                total: 0,
                formRules:${formRules},
                addForm: ${addForm}
            }
        },
        methods: {
            empity(form) {
                for (let key in form) {
                    form[key] = ''
                }
                return form
            },
            handleAdd() {
                this.dialogVisible = true
                this.type = '新增'
                if (this.type != '编辑') {
                    this.empity(this.addForm)
                }
            },
            editOpen(item) {
                this.type = '编辑'
                this.dialogVisible = true
                for (let key in this.addForm) {
                    this.addForm[key] = item[key]
                }
            },
            del(id) {
                const params = {${Primary}:id}
                this.$axios.get('/${Entity}/delById', {params}).then(res => {
                    this.$message.success(res.msg)
                    this.fetchData()
                })
            },
            submitForm() {
                this.$refs['addForm'].validate((valid) => {
                    if (valid) {
                        var url = '/${Entity}/insert'
                        if (this.type == '编辑') {
                            url = '/${Entity}/update'
                        }
                        this.$axios.post(url,
                            this.addForm
                        ).then(res => {
                            this.$message.success(res.msg)
                            this.empity(this.addForm)
                            this.fetchData()
                            this.dialogVisible = false
                        });
                    }
                })
            },
            //分页改变数量
            handleSizeChange(val) {
                this.size = val
                this.fetchData()
            },
            //改变当前页
            handleCurrentChange(val) {
                this.current = val
                this.fetchData()
            },
            fetchData() {
                var params = {
                    current: this.current,
                    size: this.size,
                }
                this.$axios.get('/${Entity}/findPage', {params}).then(res => {
                    this.tableData = res.data.records
                    this.size = res.data.size
                    this.current = res.data.current
                    this.total = res.data.total
                })
            },
            handleValueChange() {
                this.current = 1
            },
        },
        mounted() {
            this.fetchData()
        },
    }
</script>
