<template>
  <div class="app-container">

    <el-row :gutter="20">
      <!-- 左侧头像卡片 -->
      <el-col :span="6" :xs="24">
        <el-card class="box-card" text-align="center">
          <div slot="header" class="clearfix">
            <span>我的资料</span>
          </div>
          <div class="text-center">
            <userAvatar />
          </div>
          <el-descriptions :column="1" border>
            <el-descriptions-item label="用户昵称">
              {{ currentUserName}}
            </el-descriptions-item>
          </el-descriptions>

        </el-card>
      </el-col>

      <!-- 右侧详情卡片 -->
      <el-col :span="18" :xs="24">
        <el-card class="detail-card">
          <el-descriptions
            title="会员资料详情"
            :column="2"
            border
            size="medium"
          >

            <el-descriptions-item label="用户ID">
              {{ memberProfileList[0].userId }}
            </el-descriptions-item>
            <el-descriptions-item label="学号">
              {{ memberProfileList[0].studentId }}
            </el-descriptions-item>
            <el-descriptions-item label="入会时间">
              {{ parseTime(memberProfileList[0].joinTime, '{y}-{m}-{d}') }}
            </el-descriptions-item>
            <el-descriptions-item label="协会职务">
              {{ memberProfileList[0].position }}
            </el-descriptions-item>
            <el-descriptions-item label="更新人">
              {{ memberProfileList[0].updateBy }}
            </el-descriptions-item>
            <el-descriptions-item label="更新时间">
              {{ parseTime(memberProfileList[0].updateTime, '{y}-{m}-{d}') }}
            </el-descriptions-item>
          </el-descriptions>

          <el-descriptions
            title="个人介绍"
            :column="1"
            border
            size="medium"
            class="mt-20"
          >
            <el-descriptions-item>
              <div class="introduction-content">
                {{ memberProfileList[0].introduction || '暂无个人介绍' }}
              </div>
            </el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="10" class="mb8 move-up">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['memberManagement:memberProfile:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          @click="handleUpdate"
          v-hasPermi="['memberManagement:memberProfile:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          @click="handleDelete"
          v-hasPermi="['memberManagement:memberProfile:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['memberManagement:memberProfile:export']"
        >导出</el-button>
      </el-col>
<!--      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>-->
    </el-row>





      <!--    <el-table v-loading="loading" :data="memberProfileList" @selection-change="handleSelectionChange">-->
<!--      <el-table-column type="selection" width="55" align="center" />-->
<!--      <el-table-column label="对应sys_user.user_id" align="center" prop="userId" />-->
<!--      <el-table-column label="学号" align="center" prop="studentId" />-->
<!--      <el-table-column label="入会时间" align="center" prop="joinTime" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ parseTime(scope.row.joinTime, '{y}-{m}-{d}') }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="协会职务" align="center" prop="position" />-->
<!--      <el-table-column label="个人介绍" align="center" prop="introduction" />-->
<!--      <el-table-column label="更新人(sys_user.user_id)" align="center" prop="updateBy" />-->
<!--      <el-table-column label="更新时间" align="center" prop="updateTime" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--            v-hasPermi="['memberManagement:memberProfile:edit']"-->
<!--          >修改</el-button>-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-delete"-->
<!--            @click="handleDelete(scope.row)"-->
<!--            v-hasPermi="['memberManagement:memberProfile:remove']"-->
<!--          >删除</el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--    </el-table>-->

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改会员扩展资料对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <!-- 头像上传 -->
        <el-form-item label="头像" prop="avatar">
          <div class="text-center">
            <userAvatar />
          </div>
        </el-form-item>
        <el-form-item label="学号" prop="studentId">
          <el-input v-model="form.studentId" placeholder="请输入学号" />
        </el-form-item>
        <el-form-item label="入会时间" prop="joinTime">
          <el-date-picker clearable
            v-model="form.joinTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择入会时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="协会职务" prop="position">
          <el-input v-model="form.position" placeholder="请输入协会职务" />
        </el-form-item>
        <el-form-item label="个人介绍" prop="introduction">
          <el-input v-model="form.introduction" type="textarea" placeholder="请输入内容" />
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
import { listMemberProfile, getMemberProfile, delMemberProfile, addMemberProfile, updateMemberProfile } from "@/api/memberManagement/memberProfile"
import userAvatar from "@/views/system/user/profile/userAvatar.vue";
import memberFriend from "../memberFriend/index.vue";
import {Upload} from "element-ui";
import ImageUpload from "@/components/ImageUpload/index.vue";

export default {
  name: "MemberProfile",
  components: {userAvatar},
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
      // 会员扩展资料表格数据
      memberProfileList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,

      // 照片
      uploadUrl: process.env.VUE_APP_BASE_API + '/memberManagement/memberProfile/uploadAvatar',
      previewAvatar: '',    // 上传后或者编辑时显示的头像 URL

      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        studentId: [
          { required: true, message: "学号不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  computed: {
    ImageUpload() {
      return ImageUpload
    },
    Upload() {
      return Upload
    },
    memberFriend() {
      return memberFriend
    },
    currentUserId() {
      return this.$store.state.user.id
    },
    currentUserName() {
      return this.$store.state.user.nickName
    },
    isAdminEditing() {
      const userRoles = this.$store.getters.roles || [];
      return userRoles.some(role => ['admin', 'adminCommon'].includes(role));
    }
  },
  methods: {
    /** 查询会员扩展资料列表 */
    getList() {
      // this.$modal.confirm(`是否查看会员扩展资料 ${this.currentUserId} 的数据项？`)
      this.loading = true
      this.queryParams.userId = this.currentUserId;
      // listMemberProfile(this.queryParams).then(response => {
      //   this.memberProfileList = response.rows
      //   this.total = response.total
      //   this.loading = false
      // })
      getMemberProfile(this.currentUserId).then(response => {
        // 假设 response.data 是单个对象或 null
        const data = response.data;
        if (data) {
          // 包装成数组供 el-table 使用
          this.memberProfileList = [data];
          this.total = 1;
        } else {
          this.memberProfileList = [];
          this.total = 0;
        }
      }).catch(() => {
        this.memberProfileList = [];
        this.total = 0;
      }).finally(() => {
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
        userId: null,
        studentId: null,
        joinTime: null,
        position: null,
        introduction: null,
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
      this.ids = selection.map(item => item.userId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      if (this.memberProfileList && this.memberProfileList.length > 0) {
        this.$modal.msgWarning("每位用户只能有一条会员资料记录");
        return;
      }

      this.reset()
      this.open = true
      this.title = "添加会员扩展资料"
    },
    /** 修改按钮操作 */
    handleUpdate() {
      this.reset()
      const userId = this.currentUserId
      getMemberProfile(userId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改会员扩展资料"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.userId != null) {
            updateMemberProfile(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addMemberProfile(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete() {
      const userIds = this.currentUserId
      this.$modal.confirm('是否确认删除会员扩展资料编号为"' + userIds + '"的数据项？').then(function() {
        return delMemberProfile(userIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('memberManagement/memberProfile/export', {
        ...this.queryParams
      }, `memberProfile_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
<style scoped>
.move-up {
  top: -40px; /* 上移10px */
}
.mb8 {
  margin-bottom: 4px;
  margin-top: -10px;
  height: 80px;
}
.app-container {
  background: linear-gradient(135deg, #1a2a3a 0%, #f0c78a 100%);
  color: #ffffff;
  font-family: 'Open Sans', sans-serif;

}
</style>
