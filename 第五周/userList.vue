<template>
    <div class="fillcontain">
        <head-top></head-top>
        <div class="filter-container">
            <!--查询表单-->
            <el-form :inline="true">
                <el-form-item label="用户名">
                    <el-input v-model="searchMap.loginName" placeholder="用户名" class="filter-item"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="searchMap.password" placeholder="密码" class="filter-item"></el-input>
                </el-form-item>
                <el-form-item label="状态">
                    <el-input v-model="searchMap.status" placeholder="状态" class="filter-item"></el-input>
                </el-form-item>
                <el-button class="dalfBut" @click="fetchData()">查询</el-button>
                <el-button type="primary" class="butT" @click="formVisible=true;pojo={}">新增</el-button>
            </el-form>
        </div>
        <div class="table_container">
            <el-table :data="tableData" border style="width: 100%">
                <el-table-column prop="id" label="id" width="80"></el-table-column>
                <el-table-column prop="loginName" label="用户名" width="80"></el-table-column>
                <el-table-column prop="password" label="密码" width="80"></el-table-column>
                <el-table-column prop="status" label="状态" width="80"></el-table-column>

                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button @click="edit(scope.row.id)" size="mini" type="primary">修改</el-button>
                        <el-button @click="dele(scope.row.id)" size="mini" type="danger">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="Pagination" style="text-align: left;margin-top: 10px;">
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                    :current-page="currentPage" :page-size="20" layout="total, prev, pager, next" :total="count">
                </el-pagination>
            </div>
            <div class="add-form">
                <!--弹出窗口-->
                <el-dialog title="编辑" :visible.sync="formVisible">
                    <el-form label-width="80px">
                        <el-form-item label="用户名">
                            <el-input v-model="pojo.loginName"></el-input>
                        </el-form-item>
                        <el-form-item label="密码">
                            <el-input v-model="pojo.password"></el-input>
                        </el-form-item>
                        <el-form-item label="状态">
                            <el-input v-model="pojo.status"></el-input>
                        </el-form-item>

                        <!-- 图片上传代码 如页面有图片上传功能放开注释 ****
                            <el-form-item label="图片">
                                <el-upload
                                        class="avatar-uploader"
                                        action="/upload/native.do"
                                        :show-file-list="false"
                                        :on-success="handleAvatarSuccess"
                                        :before-upload="beforeAvatarUpload">
                                    <img v-if="imageUrl" :src="imageUrl" class="avatar">
                                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                                </el-upload>
                            </el-form-item>
                            -->

                        <el-form-item>
                            <el-button type="primary" @click="save()">保存</el-button>
                            <el-button @click="formVisible = false">关闭</el-button>
                        </el-form-item>
                    </el-form>
                </el-dialog>
            </div>
        </div>
    </div>
</template>
<script>
    import headTop from '../components/headTop'
    export default {
        data() {
            return {
                tableData: [],
                currentPage: 1,
                total: 10,
                size: 10,
                searchMap: {},
                pojo: {},
                formVisible: false,
                imageUrl: ''
            }
        },
        components: {
            headTop,
        },
        created() {
            this.fetchData();
        },
        methods: {
            fetchData() {
                this.$axios.post(`/admin/findPage.do?page=${this.currentPage}&size=${this.size}`, this
                    .searchMap).then(response => {
                    this.tableData = response.data.rows;
                    console.log(response);
                    this.total = response.data.total;
                });
            },
            save() {
                // this.pojo.image= this.imageUrl; //如页面有图片上传功能放开注释
                this.$axios.post(`/admin/${this.pojo.id==null?'add':'update'}.do`, this.pojo).then(response => {
                    this.fetchData(); //刷新列表
                    this.formVisible = false; //关闭窗口
                });
            },
            edit(id) {
                this.formVisible = true // 打开窗口
                // 调用查询
                this.$axios.get(`/admin/findById.do?id=${id}`).then(response => {
                    this.pojo = response.data;
                    // this.imageUrl=this.pojo.image //显示图片  如页面有图片上传功能放开注释
                })
            },
            dele(id) {
                this.$confirm('确定要删除此记录吗?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.get(`/admin/delete.do?id=${id}`).then(response => {
                        this.fetchData(); //刷新列表
                    })
                })
            },
        },
    }

</script>

<style lang="less">
    @import '../style/mixin';

    .table_container {
        padding: 20px;
    }

</style>
