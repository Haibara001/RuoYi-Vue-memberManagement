<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="发起者sys_user.user_id" prop="fromUser">
        <el-input
          v-model="queryParams.fromUser"
          placeholder="请输入发起者sys_user.user_id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="接收者sys_user.user_id" prop="toUser">
        <el-input
          v-model="queryParams.toUser"
          placeholder="请输入接收者sys_user.user_id"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['memberManagement:memberFriendRequest:add']"
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
          v-hasPermi="['memberManagement:memberFriendRequest:edit']"
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
          v-hasPermi="['memberManagement:memberFriendRequest:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['memberManagement:memberFriendRequest:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="memberFriendRequestList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="申请ID" align="center" prop="requestId" />
      <el-table-column label="发起者sys_user.user_id" align="center" prop="fromUser" />
      <el-table-column label="接收者sys_user.user_id" align="center" prop="toUser" />
      <el-table-column label="申请状态" align="center" prop="status" />

      <el-table-column label="调试" width="200">
        <template slot-scope="scope">
          toUser:{{scope.row.toUser}}<br>
          me:{{currentUserId}}<br>
          status:{{scope.row.status}}

          <pre>{{ scope.row }}</pre>

        </template>
      </el-table-column>


      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <!-- 只给接收者(toUser) 且状态为“申请中”时显示按钮 -->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-check"
            v-if="scope.row.toUser == currentUserId && scope.row.status == '申请中'"
            @click="agreeRequest(scope.row)"
          >同意</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-close"
            v-if="scope.row.toUser == currentUserId && scope.row.status == '申请中'"
            @click="rejectRequest(scope.row)"
          >不同意</el-button>

          <!-- 如果已经同意/拒绝，显示标签 -->
          <el-tag
            v-if="scope.row.status == '已同意'"
            size="small"
            type="success"
          >已同意</el-tag>
          <el-tag
            v-else-if="scope.row.status == '已拒绝'"
            size="small"
            type="danger"
          >已拒绝</el-tag>
        </template>


<!--        <template #default="{ row }">-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            v-if="row.fromUser === currentUserId"-->

<!--            @click="handleUpdate(scope.row)"-->

<!--            v-hasPermi="['memberManagement:memberFriendRequest:edit']"-->
<!--          >同意</el-button>-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-delete"-->
<!--            v-if="row.fromUser === currentUserId"-->

<!--            @click="handleDelete(scope.row)"-->
<!--            v-hasPermi="['memberManagement:memberFriendRequest:remove']"-->
<!--          >拒绝</el-button>-->
<!--        </template>-->
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改会员好友申请对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="发起者sys_user.user_id" prop="fromUser">
          <el-input v-model="form.fromUser" placeholder="请输入发起者sys_user.user_id" />
        </el-form-item>
        <el-form-item label="接收者sys_user.user_id" prop="toUser">
          <el-input v-model="form.toUser" placeholder="请输入接收者sys_user.user_id" />
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
import { listMemberFriendRequest, getMemberFriendRequest, delMemberFriendRequest, addMemberFriendRequest, updateMemberFriendRequest } from "@/api/memberManagement/memberFriendRequest"
import { listMemberFriend, getMemberFriend, delMemberFriend, addMemberFriend, updateMemberFriend } from "@/api/memberManagement/memberFriend"

export default {
  name: "MemberFriendRequest",
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
      // 会员好友申请表格数据
      memberFriendRequestList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        fromUser: null,
        toUser: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        fromUser: [
          { required: true, message: "发起者sys_user.user_id不能为空", trigger: "blur" }
        ],
        toUser: [
          { required: true, message: "接收者sys_user.user_id不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "申请状态不能为空", trigger: "change" }
        ],
        createTime: [
          { required: true, message: "申请时间不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  computed: {
    currentUserId() {
      // 如果你在 store.state.user.userId 是数字，就返回数字
      return this.$store.state.user.id
    },
    isAdminEditing() {
      const userRoles = this.$store.getters.roles || [];
      return userRoles.some(role => ['admin', 'adminCommon'].includes(role));
    }
  },
  methods: {

    /** 新增关系操作 */
    createFriendRelation(row) {
      const payload = {
        userA: row.fromUser,
        userB: row.toUser
      }
      addMemberFriend(payload)
        .then(() => {
          this.$modal.msgSuccess('好友关系添加成功')
        })
        .catch(() => {
          this.$modal.msgError('好友关系添加失败')
        })
    },

    /** 同意好友请求 */
    agreeRequest(row) {
      this.$modal.confirm("确认同意该好友申请？")
        .then(() => {
          // 1. 更新申请状态
          return updateMemberFriendRequest({
            ...row,
            status: "已同意"
          })
        })
        .then(() => {
          this.$modal.msgSuccess("已同意")
          // 2. 真正去创建好友关系
          this.createFriendRelation(row)
          // 3. 刷新列表
          this.getList()
        })
        .catch(() => {
          this.$modal.msgError("操作失败")
        })
    },

    /** 拒绝好友请求 */
    rejectRequest(row) {
      this.$modal.confirm("确认拒绝该好友申请？")
        .then(() => {
          return updateMemberFriendRequest({
            ...row,
            status: "已拒绝"
          })
        })
        .then(() => {
          this.$modal.msgSuccess("已拒绝")
          this.getList()
        })
        .catch(() => {
          this.$modal.msgError("操作失败")
        })
    },


    /** 查询会员好友申请列表 */
    getList() {
      this.loading = true

      listMemberFriendRequest(this.queryParams).then(response => {
        // 前端过滤：只保留与当前用户相关的记录
        this.memberFriendRequestList = response.rows.filter(item =>
          item.fromUser === this.currentUserId ||
          item.toUser === this.currentUserId
        );

        this.total = this.memberFriendRequestList.length;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        userA: null,
        userB: null,
        requestId: null,
        fromUser: null,
        toUser: null,
        status: null,
        createTime: null,
        updateBy: null,
        updateTime: null
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
      this.ids = selection.map(item => item.requestId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },


    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加会员好友申请"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const requestId = row.requestId || this.ids
      getMemberFriendRequest(requestId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改会员好友申请"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.requestId != null) {
            updateMemberFriendRequest(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addMemberFriendRequest(this.form).then(response => {
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
      const requestIds = row.requestId || this.ids
      this.$modal.confirm('是否确认删除会员好友申请编号为"' + requestIds + '"的数据项？').then(function() {
        return delMemberFriendRequest(requestIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('memberManagement/memberFriendRequest/export', {
        ...this.queryParams
      }, `memberFriendRequest_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
