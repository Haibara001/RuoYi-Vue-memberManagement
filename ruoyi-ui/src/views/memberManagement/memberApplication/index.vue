<template>
  <div class="app-container">
<!--    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">-->
<!--      <el-form-item label="申请人sys_user.user_id" prop="userId">-->
<!--        <el-input-->
<!--          v-model="queryParams.userId"-->
<!--          placeholder="请输入申请人sys_user.user_id"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="申请人姓名" prop="name">-->
<!--        <el-input-->
<!--          v-model="queryParams.name"-->
<!--          placeholder="请输入申请人姓名"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="联系方式" prop="contact">-->
<!--        <el-input-->
<!--          v-model="queryParams.contact"-->
<!--          placeholder="请输入联系方式"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="审核人sys_user.user_id" prop="reviewerId">-->
<!--        <el-input-->
<!--          v-model="queryParams.reviewerId"-->
<!--          placeholder="请输入审核人sys_user.user_id"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="审核时间" prop="reviewTime">-->
<!--        <el-date-picker clearable-->
<!--          v-model="queryParams.reviewTime"-->
<!--          type="date"-->
<!--          value-format="yyyy-MM-dd"-->
<!--          placeholder="请选择审核时间">-->
<!--        </el-date-picker>-->
<!--      </el-form-item>-->
<!--      <el-form-item>-->
<!--        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>-->
<!--        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>-->
<!--      </el-form-item>-->
<!--    </el-form>-->

    <el-row :gutter="10" class="mb8" v-if="!isAdminEditing">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['memberManagement:memberApplication:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['memberManagement:memberApplication:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <template v-if="memberApplicationList.length > 0">
      <el-card class="detail-card mb-6" v-for="memberApplication in memberApplicationList" :key="memberApplication.appId">
      <!-- 自定义卡片头：左侧显示“日志详情”，右侧放按钮 -->
        <template #header>
          <div class="flex justify-between items-center">
            <span class="text-lg bold-text">入会申请</span>
            <div class="space-x-2">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(memberApplication)"
                v-hasPermi="['memberManagement:memberApplication:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(memberApplication)"
                v-hasPermi="['memberManagement:memberApplication:remove']"
              >删除</el-button>
            </div>
          </div>
        </template>

        <el-descriptions
          :column="2"
          border
          size="medium"
        >

          <el-descriptions-item label="申请ID">
            {{ memberApplication.appId }}
          </el-descriptions-item>
          <el-descriptions-item label="申请人ID">
            {{ memberApplication.userId }}
          </el-descriptions-item>
          <el-descriptions-item label="申请人姓名">
            {{ memberApplication.name }}
          </el-descriptions-item>
          <el-descriptions-item label="联系方式">
            {{ memberApplication.contact }}
          </el-descriptions-item>
          <el-descriptions-item label="申请状态">
            {{ memberApplication.status }}
          </el-descriptions-item>
          <el-descriptions-item label="审核人ID">
            {{ memberApplication.reviewerId }}
          </el-descriptions-item>
          <el-descriptions-item label="审核时间">
            {{ parseTime(memberApplication.reviewTime, '{y}-{m}-{d}') }}
          </el-descriptions-item>

        </el-descriptions>


        <el-descriptions
          title="申请理由"
          :column="1"
          border
          size="medium"
          class="mt-20"
        >
          <el-descriptions-item>
            <div class="Application-content" v-html="memberApplication.reason || '暂无申请理由'"></div>
          </el-descriptions-item>
        </el-descriptions>
      </el-card>
    </template>


    <!-- 无申请时，渲染一个占位卡片 -->
    <template v-else>
      <el-card class="detail-card mb-6">
        <template #header>
          <span class="text-lg bold-text">入会申请</span>
        </template>
        <div class="p-8 text-center text-gray-500">
          暂无申请记录
        </div>
      </el-card>
    </template>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改会员入会申请对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <!-- 非管理员可编辑字段，管理员编辑时禁用 -->
        <el-form-item label="申请人姓名" prop="name">
          <el-input
            v-model="form.name"
            placeholder="请输入申请人姓名"
            :disabled="isAdminEditing"
          />
        </el-form-item>
        <el-form-item label="联系方式" prop="contact">
          <el-input
            v-model="form.contact"
            placeholder="请输入联系方式"
            :disabled="isAdminEditing"
          />
        </el-form-item>
        <el-form-item label="申请理由" prop="reason">
          <el-input
            v-model="form.reason"
            type="textarea"
            placeholder="请输入内容"
            :disabled="isAdminEditing"
          />
        </el-form-item>


        <!-- 仅管理员有权限修改审核状态 -->
        <el-form-item
            label="审核状态"
            prop="status"
            v-hasRole="['admin','adminCommon']"
            v-hasPermi="['memberManagement:memberApplication:edit']"
        >
          <el-select
              v-model="form.status"
              placeholder="请选择审核状态"
              v-hasRole="['admin','adminCommon']"
              clearable
          >
            <el-option label="审核中" value="审核中" />
            <el-option label="已通过" value="已通过" />
            <el-option label="已拒绝" value="已拒绝" />
          </el-select>
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
import { listMemberApplication, getMemberApplication, delMemberApplication, addMemberApplication, updateMemberApplication } from "@/api/memberManagement/memberApplication"

export default {
  name: "MemberApplication",
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
      // 会员入会申请表格数据
      memberApplicationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        name: null,
        contact: null,
        reason: null,
        status: null,
        reviewerId: null,
        reviewTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "申请人姓名不能为空", trigger: "blur" }
        ],
        contact: [
          { required: true, message: "联系方式不能为空", trigger: "blur" }
        ],
        reason: [
          { required: true, message: "申请理由不能为空", trigger: "blur" }
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
    // 判断是否是管理员在编辑
    isAdminEditing() {
      const userRoles = this.$store.getters.roles || [];
      return userRoles.some(role => ['admin', 'adminCommon'].includes(role));
    }
  },
  methods: {
    /** 查询会员入会申请列表 */
    getList() {
      if(!this.isAdminEditing) {
        this.queryParams.userId = this.currentUserId
      }
      this.loading = true
      listMemberApplication(this.queryParams).then(response => {
        this.memberApplicationList = response.rows
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
        appId: null,
        userId: null,
        name: null,
        contact: null,
        reason: null,
        status: null,
        reviewerId: null,
        reviewTime: null,
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
      // 重置后，如果是普通用户，仍然保持用户ID条件
      if (!hasRole(['admin', 'adminCommon'])) {
        this.queryParams.userId = getUserId()
      }
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.appId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加会员入会申请"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const appId = row.appId || this.ids
      getMemberApplication(appId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改会员入会申请"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.appId != null) {
            updateMemberApplication(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addMemberApplication(this.form).then(response => {
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
      const appIds = row.appId || this.ids
      this.$modal.confirm('是否确认删除会员入会申请编号为"' + appIds + '"的数据项？').then(function() {
        return delMemberApplication(appIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('memberManagement/memberApplication/export', {
        ...this.queryParams
      }, `memberApplication_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
