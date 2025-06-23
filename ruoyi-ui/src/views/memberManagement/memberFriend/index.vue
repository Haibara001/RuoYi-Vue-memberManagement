<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
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
          v-hasPermi="['memberManagement:memberFriend:add']"
        >新增</el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="success"-->
<!--          plain-->
<!--          icon="el-icon-edit"-->
<!--          size="mini"-->
<!--          :disabled="single"-->
<!--          @click="handleUpdate"-->
<!--          v-hasPermi="['memberManagement:memberFriend:edit']"-->
<!--        >修改</el-button>-->
<!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['memberManagement:memberFriend:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['memberManagement:memberFriend:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>



    <template v-if="memberFriendList.length > 0">
      <el-card class="detail-card mb-6" v-for="memberFriend in memberFriendList">
        <!-- 自定义卡片头：左侧显示“日志详情”，右侧放按钮 -->
        <template #header>
          <div class="flex justify-between items-center">
            <span class="text-lg bold-text">好友列表</span>
            <div class="space-x-2">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleViewFriend(memberFriend)"
              >查看好友空间</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(memberFriend)"
                v-hasPermi="['memberManagement:memberFriend:remove']"
              >删除</el-button>
            </div>
          </div>
        </template>

        <el-descriptions
          :column="2"
          border
          size="medium"
        >

          <el-descriptions-item label="好友ID">
            {{ memberFriend.friendId }}
          </el-descriptions-item>

          <el-descriptions-item label="好友姓名">
            {{ memberFriend.friendName }}
          </el-descriptions-item>
          <el-descriptions-item label="成为好友时间">
            {{ parseTime(memberFriend.createTime, '{y}-{m}-{d}') }}
          </el-descriptions-item>

        </el-descriptions>
      </el-card>
    </template>


<!--     无申请时，渲染一个占位卡片-->
    <template v-else>
      <el-card class="detail-card mb-6">
        <template #header>
          <span class="text-lg bold-text">好友关系</span>
        </template>
        <div class="p-8 text-center text-gray-500">
          暂无好友
        </div>
      </el-card>
    </template>



<!--    <el-table v-loading="loading" :data="memberFriendList" @selection-change="handleSelectionChange">-->
<!--      <el-table-column type="selection" width="55" align="center" />-->
<!--      &lt;!&ndash; 只显示对方姓名 &ndash;&gt;-->
<!--      <el-table-column label="调试" align="center">-->
<!--        <template  slot-scope="scope">-->
<!--          <pre>{{scope.row}}</pre>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="好友姓名" prop="friendName" align="center"/>-->

<!--      &lt;!&ndash; 显示成为好友时间 &ndash;&gt;-->
<!--      <el-table-column label="成为好友时间" prop="createTime" align="center"/>-->
<!--&lt;!&ndash;      <el-table-column label="好友一sys_user.user_id" align="center" prop="userA" />&ndash;&gt;-->
<!--&lt;!&ndash;      <el-table-column label="好友二sys_user.user_id" align="center" prop="userB" />&ndash;&gt;-->
<!--      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">-->
<!--        <template slot-scope="scope">-->
<!--&lt;!&ndash;          <el-button&ndash;&gt;-->
<!--&lt;!&ndash;            size="mini"&ndash;&gt;-->
<!--&lt;!&ndash;            type="text"&ndash;&gt;-->
<!--&lt;!&ndash;            icon="el-icon-edit"&ndash;&gt;-->
<!--&lt;!&ndash;            @click="handleUpdate(scope.row)"&ndash;&gt;-->
<!--&lt;!&ndash;            v-hasPermi="['memberManagement:memberFriend:edit']"&ndash;&gt;-->
<!--&lt;!&ndash;          >修改</el-button>&ndash;&gt;-->

<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-user"-->
<!--            @click="handleViewFriend(scope.row)"-->
<!--          >查看好友空间</el-button>-->

<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-delete"-->
<!--            @click="handleDelete(scope.row)"-->
<!--            v-hasPermi="['memberManagement:memberFriend:remove']"-->
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

    <!-- 添加或修改会员好友关系对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

<!--    <el-dialog :visible.sync="showFriendSpace" width="600px" title="好友空间">-->
<!--      <div v-if="friendSpaceData">-->
<!--        <el-dialog :visible.sync="showFriendSpace" width="600px" title="好友空间">-->
<!--          &lt;!&ndash; 先始终渲染，看看数据结构 &ndash;&gt;-->
<!--          <pre>{{ friendSpaceData }}</pre>-->
<!--        </el-dialog>-->
<!--&lt;!&ndash;        <h3>基本资料</h3>&ndash;&gt;-->
<!--&lt;!&ndash;        <p>昵称: {{ friendSpaceData.profile.nickName }}</p>&ndash;&gt;-->
<!--&lt;!&ndash;        <p>签名: {{ friendSpaceData.profile.signature }}</p>&ndash;&gt;-->
<!--&lt;!&ndash;        <p>是否好友: {{ friendSpaceData.isFriend ? '是' : '否' }}</p>&ndash;&gt;-->

<!--&lt;!&ndash;        <h3>动态列表</h3>&ndash;&gt;-->
<!--&lt;!&ndash;        <el-table :data="friendSpaceData.posts" style="width: 100%">&ndash;&gt;-->
<!--&lt;!&ndash;          <el-table-column prop="title" label="标题" />&ndash;&gt;-->
<!--&lt;!&ndash;          <el-table-column prop="createTime" label="发布时间" />&ndash;&gt;-->
<!--&lt;!&ndash;          <el-table-column prop="visibility" label="可见范围" />&ndash;&gt;-->
<!--&lt;!&ndash;        </el-table>&ndash;&gt;-->
<!--        -->
<!--      </div>-->
<!--    </el-dialog>-->
    <el-dialog :visible.sync="showFriendSpace" width="700px" title="好友空间">
      <div v-if="friendSpaceData" class="friend-space-container">
        <!-- 基本资料区域 -->
        <el-card shadow="hover" class="mb-6">
          <div slot="header" class="flex justify-between items-center">
            <span class="text-lg font-bold">基本资料</span>
          </div>
          <el-descriptions border column="2" size="small">
            <el-descriptions-item label="学号">
              {{ friendSpaceData.profile.studentId }}
            </el-descriptions-item>
            <el-descriptions-item label="入会时间">
              {{ parseTime(friendSpaceData.profile.joinTime, '{y}-{m}-{d}') }}
            </el-descriptions-item>
            <el-descriptions-item label="协会职务">
              {{ friendSpaceData.profile.position }}
            </el-descriptions-item>
            <el-descriptions-item label="是否好友">
              <el-tag :type="friendSpaceData.isFriend ? 'success' : 'info'">
                {{ friendSpaceData.isFriend ? '是' : '否' }}
              </el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="个人介绍" :span="2">
              <div class="whitespace-pre-wrap text-sm text-gray-700">
                {{ friendSpaceData.profile.introduction }}
              </div>
            </el-descriptions-item>
          </el-descriptions>
        </el-card>

        <!-- 动态列表区域 -->
        <template v-if="visiblePosts.length > 0">
          <el-card shadow="hover" v-for="post in visiblePosts">
            <template #header>
              <div class="flex justify-between items-center">
                <span class="text-lg font-bold">日志列表</span>
              </div>
            </template>

            <el-descriptions border column="1" size="small">
              <el-descriptions-item label="日志数量">
                {{ visiblePosts.length }}
              </el-descriptions-item>
            </el-descriptions>


            <el-descriptions border column="2" size="small">
              <el-descriptions-item label="作者ID">
                {{ post.authorId }}
              </el-descriptions-item>
              <el-descriptions-item label="可见范围">
                {{ post.visibility }}
              </el-descriptions-item>
              <el-descriptions-item label="发布时间">
                {{post.createTime}}
              </el-descriptions-item>
              <el-descriptions-item label="标题">
                {{ post.title }}
              </el-descriptions-item>

              <!--            <el-descriptions-item label="调试信息">-->
              <!--              {{ post}}-->
              <!--            </el-descriptions-item>-->

            </el-descriptions>

            <el-descriptions border column="1" size="small">
              <el-descriptions-item label="内容">
                <div class="Application-content" v-html="post.content || '暂无日志内容'"></div>
              </el-descriptions-item>
            </el-descriptions>

            <!--          <el-table-->
            <!--            :data="visiblePosts"-->
            <!--            empty-text="日志动态"-->
            <!--            border-->
            <!--            stripe-->
            <!--            size="small"-->
            <!--            v-loading="postsLoading"-->
            <!--          >-->
            <!--            <el-table-column type="index" width="50" label="序号" />-->
            <!--            <el-table-column prop="title" label="标题" />-->
            <!--            <el-descriptions-item label="调试">-->
            <!--              <template slot-scope="scope">-->
            <!--                <pre>{{ scope.row }}<</pre>-->
            <!--              </template>-->
            <!--            </el-descriptions-item>-->
            <!--            <el-table-column-->
            <!--              prop="createTime"-->
            <!--              label="发布时间"-->
            <!--              :formatter="row => parseTime(row.createTime, '{y}-{m}-{d} {h}:{i}:{s}')"-->
            <!--              width="160"-->
            <!--            />-->
            <!--            <el-table-column prop="visibility" label="可见范围" width="100" />-->
            <!--            <el-table-column prop="content" label="日志内容" />-->
            <!--          </el-table>-->
          </el-card>
        </template>
        <template v-else>
          <el-card shadow="hover">
            <div class="flex justify-between items-center">
              <span class="text-lg font-bold">暂无日志</span>
            </div>
          </el-card>
        </template>

      </div>
    </el-dialog>




  </div>
</template>

<script>
import { listMemberFriend, getMemberFriend, delMemberFriend, addMemberFriend, updateMemberFriend } from "@/api/memberManagement/memberFriend"
import { listMyFriends, delMyFriend } from '@/api/memberManagement/memberFriend'
import { getFriendSpace}  from "@/api/memberManagement/memberFriend";
import post from "../../system/post/index.vue";


export default {
  name: "MemberFriend",
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
      // 会员好友关系表格数据
      memberFriendList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,

      //好友空间数据
      friendSpaceData: null,
      showFriendSpace: false,


      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        createTime: [
          { required: true, message: "成为好友时间不能为空", trigger: "blur" }
        ]
      }
    }
  },
  created() {
    // 带上当前登录用户 ID
    this.queryParams.userId = this.currentUserId
    this.getList()
  },
  computed: {
    post() {
      return post
    },
    currentUserId() {
      return this.$store.state.user.id
    },
    visiblePosts() {
      const data = this.friendSpaceData;
      if (!data || !Array.isArray(data.posts)) {
        return [];
      }
      // 取出好友的 userId
      const friendId = data.profile.userId;

      return data.posts.filter(post => {
        // 先过滤「只有本人的」
        if (post.authorId !== friendId) {
          return false;
        }
        // 全部可见
        if (post.visibility === '全部可见') {
          return true;
        }
        // 好友可见，且 isFriend 为 true
        if (post.visibility === '好友可见' && data.isFriend) {
          return true;
        }
        return false;
      });
    }
  },
  methods: {


    // 获取好友空间信息
    handleViewFriend(row) {
      const friendId = row.friendId;
      getFriendSpace(friendId)
        .then(response => {
          console.log("好友空间数据：", response.data);
          this.friendSpaceData = response.data;
          this.showFriendSpace = true;
        })
        .catch(err => {
          this.$modal.msgError("加载失败");
          console.error(err);
        });




      // // 方案1：直接跳转（推荐）
      // // this.$router.push(`/space/${friendId}`)
      // // getFriendSpace(friendId)
    },



    /** 查询会员好友关系列表 */
    getList() {
      this.loading = true
      listMyFriends(this.queryParams)
        .then(response => {
          this.memberFriendList = response.rows
          this.total = response.total
        })
        .finally(() => {
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
        userA: null,
        userB: null,
        createTime: null
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
      this.ids = selection.map(item => item.userA)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加会员好友关系"
    },
    /** 修改按钮操作 */
    // handleUpdate(row) {
    //   this.reset()
    //   const userA = row.userA || this.ids
    //   getMemberFriend(userA).then(response => {
    //     this.form = response.data
    //     this.open = true
    //     this.title = "修改会员好友关系"
    //   })
    // },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.userA != null) {
            updateMemberFriend(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addMemberFriend(this.form).then(response => {
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
      const friendId = row.friendId;
      if (!friendId) {
        this.$modal.msgError('请先选择要删除的好友');
        return;
      }
      this.$modal.confirm(`确认删除好友 “${row.friendName}”？`)
        .then(() => delMyFriend(friendId))
        .then(() => {
          this.getList();
          this.$modal.msgSuccess('删除成功');
        })
        .catch(() => {});
    },
    // handleDelete(row) {
    //
    //   let userAs = row && row.friendId ? row.friendId : this.ids
    //   let currentUserId = this.currentUserId
    //   console.log('即将删除用户：', userAs)
    //   userAs = userAs+currentUserId
    //
    //   this.$modal.confirm('是否确认删除会员好友关系编号为"' + userAs + '"的数据项？'+'当前用户为:'+ currentUserId).then(function() {
    //     return delMemberFriend(userAs)
    //   }).then(() => {
    //     this.getList()
    //     this.$modal.msgSuccess("删除成功")
    //   }).catch(() => {})
    // },
    /** 导出按钮操作 */
    handleExport() {
      this.download('memberManagement/memberFriend/export', {
        ...this.queryParams
      }, `memberFriend_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
