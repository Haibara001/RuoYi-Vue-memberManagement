<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="日志标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入日志标题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="可见范围" prop="visibility">
        <el-select v-model="queryParams.visibility" placeholder="请选择可见范围" clearable>
          <el-option
            v-for="dict in dict.type.visible_range"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
<!--      <el-form-item label="更新人(sys_user.user_id)" prop="updateBy">-->
<!--        <el-input-->
<!--          v-model="queryParams.updateBy"-->
<!--          placeholder="请输入更新人(sys_user.user_id)"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="更新时间" prop="updateTime">
        <el-date-picker clearable
          v-model="queryParams.updateTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择更新时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['memberManagement:memberPost:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['memberManagement:memberPost:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['memberManagement:memberPost:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['memberManagement:memberPost:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="filteredPosts" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="日志ID" align="center" prop="postId" />
      <el-table-column label="日志标题" align="center" prop="title" />
      <el-table-column label="日志内容" align="center" prop="content" />
      <el-table-column label="可见范围" align="center" prop="visibility">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.visible_range" :value="scope.row.visibility"/>
        </template>
      </el-table-column>
<!--      <el-table-column label="更新人(sys_user.user_id)" align="center" prop="updateBy" />-->
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['memberManagement:memberPost:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['memberManagement:memberPost:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改会员日志/帖子对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="日志标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入日志标题" />
        </el-form-item>
        <el-form-item label="日志内容">
          <editor v-model="form.content" :min-height="192"/>
        </el-form-item>
        <el-form-item label="可见范围" prop="visibility">
          <el-radio-group v-model="form.visibility">
            <el-radio
              v-for="dict in dict.type.visible_range"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="删除标志(0=正常,1=删除)" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入删除标志(0=正常,1=删除)" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listMemberPost, getMemberPost, delMemberPost, addMemberPost, updateMemberPost } from "@/api/memberManagement/memberPost"

export default {
  name: "MemberPost",
  dicts: ['visible_range'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 会员日志/帖子表格数据
      memberPostList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        content: null,
        visibility: null,
        updateBy: null,
        updateTime: null,
        authorId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        title: [
          { required: true, message: "日志标题不能为空", trigger: "blur" }
        ],
        content: [
          { required: true, message: "日志内容不能为空", trigger: "blur" }
        ],
        visibility: [
          { required: true, message: "可见范围不能为空", trigger: "change" }
        ],
        createTime: [
          { required: true, message: "发布时间不能为空", trigger: "blur" }
        ],
        delFlag: [
          { required: true, message: "删除标志(0=正常,1=删除)不能为空", trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  computed: {
    currentUserId() {
      return this.$store.state.user.id
    },
    filteredPosts() {
      return this.memberPostList.filter(
        post => post.authorId === this.currentUserId
      )
    },
  },
  methods: {
    /** 查询会员日志/帖子列表 */
    getList() {
      this.loading = true
      this.queryParams.authorId = this.currentUserId
      listMemberPost(this.queryParams).then(response => {
        this.memberPostList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        postId: null,
        authorId: null,
        title: null,
        content: null,
        visibility: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        delFlag: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.postId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加会员日志/帖子"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const postId = row.postId || this.ids
      getMemberPost(postId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改会员日志/帖子"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.postId != null) {
            updateMemberPost(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addMemberPost(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const postIds = row.postId || this.ids
      this.$modal.confirm('是否确认删除会员日志/帖子编号为"' + postIds + '"的数据项？').then(function() {
        return delMemberPost(postIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('memberManagement/memberPost/export', {
        ...this.queryParams
      }, `memberPost_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
