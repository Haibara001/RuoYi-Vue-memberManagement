<template>
  <div class="app-container bg-football p-4">
    <!-- 标题区 -->
    <div class="header flex items-center mb-6">
      <i class="fas fa-futbol fa-2x text-white mr-2"></i>
      <h2 class="text-white text-2xl font-bold">足球协会成员标签管理</h2>
    </div>
    <!-- 搜索与操作 -->
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="100px"
      class="mb-4 bg-white p-4 rounded-lg shadow-md"
    >
      <el-form-item label="标签名称" prop="name">
        <el-input
          type="text"
          v-model="queryParams.name"
          placeholder="请输入标签名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建人" prop="createBy">
        <el-input
          type="text"
          v-model="queryParams.createBy"
          placeholder="请输入创建人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="更新时间" prop="updateTime">
        <el-date-picker
          clearable
          v-model="queryParams.updateTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择更新时间"
        />
      </el-form-item>
      <el-form-item>
        <!-- 切换按钮 -->
        <el-button
          type="primary"
          icon="el-icon-user"
          size="mini"
          style="background-color: #722ED1; color: white; border-color: #722ED1;"
          @click="toggleMyTagsView"
        >
          {{ showTable ? '返回卡片视图' : '我的标签' }}
        </el-button>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button type="warning" icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮组 -->
    <div class="actions flex mb-4">
      <el-button type="success" icon="el-icon-plus" size="mini" class="mr-2" @click="handleAdd">新增</el-button>
      <el-button :disabled="single" type="info" icon="el-icon-edit" size="mini" class="mr-2" @click="handleUpdate">修改</el-button>
      <el-button :disabled="multiple" type="danger" icon="el-icon-delete" size="mini" class="mr-2" @click="handleDelete">删除</el-button>
      <el-button type="primary" icon="el-icon-download" size="mini" @click="handleExport">导出</el-button>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" class="ml-auto" />
    </div>

    <!--    <el-button type="primary" @click="showTable = !showTable" class="mb-4">-->
    <!--      {{ showTable ? '返回卡片视图' : '查看我的标签表格' }}-->
    <!--    </el-button>-->

    <!-- 卡片视图 -->
    <el-row v-if="!showTable" :gutter="20">
      <el-col
        :xs="24" :sm="12" :md="12" :lg="6"
        v-for="tag in memberTagList"
        :key="tag.tagId"
      >
        <el-card class="tag-card h-full flex flex-col justify-between p-4" shadow="hover">
          <div>
            <i class="fas fa-tag fa-lg text-primary mr-2"></i>
            <span class="text-lg font-semibold">{{ tag.name }}</span>
          </div>
          <div class="text-sm text-gray-600 mt-2">
            创建者ID：{{ tag.createBy }}<br/>
            更新：{{ parseTime(tag.updateTime, '{y}-{m}-{d}') }}
          </div>
          <div class="text-right mt-4">
            <el-button size="medium" type="text" @click="handleUpdate(tag)">编辑</el-button>
            <el-button size="medium" type="text" @click="handleDelete(tag)">删除</el-button>
            <div class="mt-2">
              <el-button size="medium" type="primary" @click="addToMyTags(tag)">
                添加为我的标签
              </el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 表格视图 -->
    <!-- “我的标签” 卡片视图 -->
    <el-row v-else :gutter="20">
      <el-col
        v-for="item in userTagsWithName"
        :key="item.tagId"
        :xs="24" :sm="12" :md="12" :lg="6"
      >
        <el-card class="tag-card h-full flex flex-col justify-between p-4" shadow="hover">
          <div>
            <i class="fas fa-tag fa-lg text-primary mr-2"></i>
            <!-- 显示关联表里的标签名字 -->
            <span class="text-lg font-semibold">{{ item.tagName }}</span>
          </div>
          <div class="text-sm text-gray-600 mt-2">
            用户ID：{{ item.userId }}<br/>
            标签ID：{{ item.tagId }}
          </div>
          <div class="text-right mt-4">
            <el-button
              size="medium"
              type="text"
              @click="handleUpdate(item)"
              v-hasPermi="['memberManagement:memberUserTag:edit']"
            >修改</el-button>
            <el-button
              size="medium"
              type="text"
              @click="handleDelete(item)"
              v-hasPermi="['memberManagement:memberUserTag:remove']"
            >删除</el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>


    <!--    <el-table-->
    <!--      v-else-->
    <!--      v-loading="loading"-->
    <!--      :data="userTagsWithName"-->
    <!--      @selection-change="handleSelectionChange"-->
    <!--      style="width: 100%; margin-top: 16px;"-->
    <!--    >-->
    <!--      <el-table-column type="selection" width="55" align="center" />-->
    <!--      <el-table-column prop="userId" label="用户 ID" align="center" />-->
    <!--      <el-table-column prop="tagName" label="标签名称" align="center" />-->
    <!--      <el-table-column label="操作" align="center" width="180">-->
    <!--        <template #default="{ row }">-->
    <!--          <el-button-->
    <!--            size="mini"-->
    <!--            type="text"-->
    <!--            icon="el-icon-edit"-->
    <!--            @click="handleUpdate(row)"-->
    <!--            v-hasPermi="['memberManagement:memberUserTag:edit']"-->
    <!--          >修改</el-button>-->
    <!--          <el-button-->
    <!--            size="mini"-->
    <!--            type="text"-->
    <!--            icon="el-icon-delete"-->
    <!--            @click="handleDelete(row)"-->
    <!--            v-hasPermi="['memberManagement:memberUserTag:remove']"-->
    <!--          >删除</el-button>-->
    <!--        </template>-->
    <!--      </el-table-column>-->
    <!--    </el-table>-->




    <!--    &lt;!&ndash; 标签卡片展示 &ndash;&gt;-->
    <!--    <el-row :gutter="20">-->
    <!--      <el-col :xs="24" :sm="12" :md="12" :lg="6" v-for="tag in memberTagList" :key="tag.tagId">-->
    <!--        <el-card class="tag-card h-full flex flex-col justify-between p-4" shadow="hover">-->
    <!--          <div>-->
    <!--            <i class="fas fa-tag fa-lg text-primary mr-2"></i>-->
    <!--            <span class="text-lg font-semibold">{{ tag.name }}</span>-->
    <!--          </div>-->
    <!--          <div class="text-sm text-gray-600 mt-2">-->
    <!--            创建：{{ tag.createBy }}<br/>-->
    <!--            更新：{{ parseTime(tag.updateTime, '{y}-{m}-{d}') }}-->
    <!--          </div>-->
    <!--          <div class="text-right mt-4">-->
    <!--            <el-button size="medium" type="text" @click="handleUpdate(tag)">编辑</el-button>-->
    <!--            <el-button size="medium" type="text" @click="handleDelete(tag)">删除</el-button>-->
    <!--            &lt;!&ndash; 第二行：添加为我的标签 &ndash;&gt;-->
    <!--            <div class="mt-2">-->
    <!--              <el-button size="medium" type="primary" @click="addToMyTags(tag)">-->
    <!--                添加为我的标签-->
    <!--              </el-button>-->
    <!--            </div>-->
    <!--          </div>-->
    <!--        </el-card>-->
    <!--      </el-col>-->
    <!--    </el-row>-->

    <!--    <el-table v-loading="loading" :data="memberUserTagList" @selection-change="handleSelectionChange">-->
    <!--      <el-table-column type="selection" width="55" align="center" />-->
    <!--      <el-table-column label="sys_user.user_id" align="center" prop="userId" />-->
    <!--      <el-table-column label="association_tag.tag_id" align="center" prop="tagId" />-->
    <!--      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">-->
    <!--        <template slot-scope="scope">-->
    <!--          <el-button-->
    <!--            size="mini"-->
    <!--            type="text"-->
    <!--            icon="el-icon-edit"-->
    <!--            @click="handleUpdate(scope.row)"-->
    <!--            v-hasPermi="['memberManagement:memberUserTag:edit']"-->
    <!--          >修改</el-button>-->
    <!--          <el-button-->
    <!--            size="mini"-->
    <!--            type="text"-->
    <!--            icon="el-icon-delete"-->
    <!--            @click="handleDelete(scope.row)"-->
    <!--            v-hasPermi="['memberManagement:memberUserTag:remove']"-->
    <!--          >删除</el-button>-->
    <!--        </template>-->
    <!--      </el-table-column>-->
    <!--    </el-table>-->

    <!-- 分页 -->
    <el-pagination
      v-show="total > 0"
      :total="total"
      :page-size.sync="queryParams.pageSize"
      :current-page.sync="queryParams.pageNum"
      layout="total, prev, pager, next, sizes"
      @size-change="getList"
      @current-change="getList"
      class="mt-6"
    />

    <!-- 添加或修改标签定义对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="标签名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入标签名称" />
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
import { listMemberTag, getMemberTag, delMemberTag, addMemberTag, updateMemberTag } from "@/api/memberManagement/memberTag"
import { listMemberUserTag, getMemberUserTag, delMemberUserTag, addMemberUserTag, updateMemberUserTag } from "@/api/memberManagement/memberUserTag"

export default {
  name: "MemberTag",
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
      // 标签定义表格数据
      memberTagList: [],

      memberUserTagList: [],

      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示
      showTable: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        createBy: null,
        updateBy: null,
        updateTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "标签名称不能为空", trigger: "blur" }
        ],
        createBy: [
          { required: true, message: "创建人(sys_user.user_id)不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  computed: {
    userTagsWithName() {
      return this.memberUserTagList.map(item => {
        const tag = this.memberTagList.find(t => t.tagId === item.tagId) || {}
        return {
          ...item,
          tagName: tag.name || '（已删除）'
        }
      })
    }
  },
  methods: {
    /** 查询标签定义列表 */
    getList() {
      this.loading = true
      listMemberTag(this.queryParams).then(response => {
        this.memberTagList = response.rows
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
        tagId: null,
        name: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        delFlag: null
      }
      this.resetForm("form")
    },

    toggleMyTagsView() {
      this.showTable = !this.showTable;
      if (this.showTable) {
        // 切到表格视图时拉数据
        this.handleQueryMyTags();
      }
    },
    handleQueryMyTags() {
      // 准备专用查询参数
      const query = {
        pageNum: this.queryParams.pageNum,
        pageSize: this.queryParams.pageSize,
      };
      this.loading = true
      listMemberUserTag(query).then(response => {
        this.memberUserTagList = response.rows
        this.total = response.total
        this.loading = false
      })
    },

    addToMyTags(tag) {
      this.$set(tag, '_adding', true)
      addMemberUserTag({ userId: this.currentUserId, tagId: tag.tagId })
        .then(() => {
          this.$message.success(`标签“${tag.name}”已添加`)
          // 如果当前正看“我的标签”视图，刷新列表
          if (this.showTable) this.handleQueryMyTags()
        })
        .catch(() => {
          this.$message.error('添加失败，请重试')
        })
        .finally(() => {
          this.$set(tag, '_adding', false)
        })
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
      this.ids = selection.map(item => item.tagId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加标签定义"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const tagId = row.tagId || this.ids
      getMemberTag(tagId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改标签定义"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.tagId != null) {
            updateMemberTag(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addMemberTag(this.form).then(response => {
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
      const tagIds = row.tagId || this.ids
      this.$modal.confirm('是否确认删除标签定义编号为"' + tagIds + '"的数据项？').then(function() {
        return delMemberTag(tagIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('memberManagement/memberTag/export', {
        ...this.queryParams
      }, `memberTag_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>

<style scoped>
/* 全局背景渐变 */
/* 全局背景样式 */
.app-container {
  background: linear-gradient(135deg, #1a2a3a 0%, #f0c78a 100%);
  color: #ffffff;
  font-family: 'Open Sans', sans-serif;

}

/* 卡片悬停效果增强 */
.tag-card:hover {
  transform: translateY(-5px);
  transition: transform 0.3s ease;
}

/* 按钮样式微调 */
.el-button {
  transition: all 0.3s ease;
}

/* 标题文字阴影增强可读性 */
.header h2 {
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
}

.tag-card {
  background: rgba(139, 195, 74, 0.8); /* 对应 #8BC34A 的半透明版本 */
  border-radius: var(--border-radius);
  padding: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  transition: var(--transition);
  cursor: pointer;
  border: 2px solid transparent;
  position: relative;
  overflow: hidden;
  text-align: center;
  margin-bottom: 16px;  /* 或 20px，按需调整 */
}

.tag-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
  border-color: var(--primary);
}

.text-right {

}


.tag-card.selected {
  border-color: var(--primary);
  background: rgba(46, 204, 3, 0.1);
}

.tag-card.selected::after {
  content: "\f00c";
  font-family: "Font Awesome 5 Free";
  font-weight: 900;
  position: absolute;
  top: 10px;
  right: 10px;
  width: 24px;
  height: 24px;
  background: var(--primary);
  color: white;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
}

</style>
