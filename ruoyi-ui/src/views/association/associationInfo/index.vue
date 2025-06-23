<template>
  <div class="association-info p-4">
    <!-- 协会简介 -->
    <section class="section intro mb-8">
      <el-card shadow="hover" class="intro-card">
        <div slot="header" class="card-header">
          <h2 class="section-title">协会简介</h2>
        </div>
        <p class="section-content">{{ introduction }}</p>
      </el-card>
    </section>

    <!-- 协会活动 -->
    <section class="section activities mb-8">
      <el-card shadow="hover" class="activities-card">
        <div slot="header" class="card-header">
          <h2 class="section-title">协会活动</h2>
        </div>
        <el-row :gutter="20">
          <el-col
            v-for="activity in activities"
            :key="activity.id"
            :xs="24" :sm="12" :md="8"
          >
            <el-card class="activity-card" shadow="always">
              <h3 class="activity-title">{{ activity.title }}</h3>
              <p class="activity-date">{{ activity.date }}</p>
              <p class="activity-summary">{{ activity.summary }}</p>
            </el-card>
          </el-col>
        </el-row>
      </el-card>
    </section>

    <!-- 协会资讯 -->
    <section class="section news">
      <el-card shadow="hover" class="news-card">
        <div slot="header" class="card-header">
          <h2 class="section-title">协会资讯</h2>
        </div>
        <el-timeline>
          <el-timeline-item
            v-for="item in news"
            :key="item.id"
            :timestamp="item.date"
            class="timeline-item"
          >
            <h4 class="news-title">{{ item.title }}</h4>
            <p class="news-summary">{{ item.summary }}</p>
          </el-timeline-item>
        </el-timeline>
      </el-card>
    </section>
  </div>
</template>

<script>
export default {
  name: 'HomePage',
  data() {
    return {
      introduction: '欢迎来到梅罗足球协会！我们由足球名宿梅西和C罗联合发起，致力于推广足球运动，组织各类活动，让更多人爱上足球。协会拥有专业教练团队、完善训练体系和顶级场地设施，为不同年龄段、水平的爱好者提供优质服务。',
        activities: [
      { id: 1, title: '青少年训练营', date: '2025-07-10', summary: '为 8-16 岁青少年提供专业足球训练，由前国脚教练带队，培养基础技术与团队意识。' },
      { id: 2, title: '社区友谊赛', date: '2025-07-20', summary: '邀请周边社区队伍参与友谊赛，促进邻里交流，弘扬足球精神。' },
      { id: 3, title: '教练公开课', date: '2025-08-01', summary: '资深教练分享足球战术与训练方法，开放训练环节供爱好者体验。' },
      { id: 4, title: '女子足球日', date: '2025-08-15', summary: '专为女性举办的足球体验活动，提供免费课程和装备试用。' },
      { id: 5, title: '亲子足球嘉年华', date: '2025-09-01', summary: '家长与孩子共同参与的足球游戏和比赛，增进亲子关系。' }
    ],
      news: [
      { id: 1, date: '2025-06-01', title: '协会新网站上线', summary: '我们的官网已全新改版，新增在线报名、赛事直播和会员社区功能，欢迎访问！' },
      { id: 2, date: '2025-06-15', title: '夏季联赛报名开始', summary: '报名通道已打开，16支球队将角逐冠军，优胜者将获得职业俱乐部试训机会！' },
      { id: 3, date: '2025-06-20', title: '青训学员入选国少队', summary: '协会青训营学员李明入选U14国家队，成为本季第3位入选的学员。' },
      { id: 4, date: '2025-06-25', title: '与巴萨青训达成合作', summary: '协会与巴塞罗那足球俱乐部青训学院签署合作协议，将定期互派教练交流。' },
      { id: 5, date: '2025-06-30', title: '会员专属训练装备上线', summary: '会员可定制印有协会标志的专业训练服，享受批发价优惠。' }
    ],
      membership: {
      types: [
        { id: 'basic', name: '基础会员', price: 399, benefits: ['参加社区活动', '训练指导', '赛事门票9折'] },
        { id: 'premium', name: '高级会员', price: 799, benefits: ['基础会员权益', '专业教练1对1指导', '优先参加训练营'] },
        { id: 'pro', name: '精英会员', price: 1999, benefits: ['高级会员权益', '职业俱乐部试训机会', '装备赞助'] }
      ],
        application: {
        requirements: '年满8周岁，热爱足球运动，遵守协会章程',
          process: '在线提交申请表 → 缴纳会费 → 参加入队测试 → 审核通过后颁发会员证',
          documents: ['身份证复印件', '体检报告', '2寸照片2张']
      }
    },
      achievements: [
        { year: 2024, title: '市业余联赛冠军', description: '协会成年队击败24支队伍夺得冠军' },
        { year: 2024, title: '省青少年足球赛亚军', description: 'U16梯队获得省级比赛亚军' },
        { year: 2023, title: '最佳青训机构', description: '被体育局评为年度最佳青少年足球培训机构' }
      ],
        facilities: [
      { name: '主场体育场', address: '城市中心体育路88号', features: ['天然草坪', '可容纳5000人', '灯光球场'] },
      { name: '青训基地', address: '城郊足球产业园A区', features: ['8块训练场地', '住宿区', '康复中心'] }
      ]
    }
  }
}
</script>

<style scoped>
.association-info {
  width: 100%;
}
.card-header {
  background: linear-gradient(90deg, #1890ff, #40a9ff);
  padding: 0.5rem 1rem;
  border-radius: 8px 8px 0 0;
}
.section-title {
  color: #fff;
  font-size: 1.5rem;
  margin: 0;
}
.intro-card {
  background-color: #e6f7ff;
}
.activities-card {
  background-color: #f6ffed;
}
.news-card {
  background-color: #fffbe6;
}
.activity-card {
  padding: 1rem;
  border-radius: 8px;
  transition: transform 0.3s ease;
}
.activity-card:hover {
  transform: translateY(-5px);
}
.activity-title {
  font-size: 1.25rem;
  font-weight: bold;
  color: #096dd9;
}
.activity-date {
  font-size: 0.875rem;
  color: #8c8c8c;
}
.activity-summary {
  margin-top: 0.5rem;
  color: #595959;
}
.timeline-item .el-timeline-item__tail {
  border-color: #ffd666;
}
.timeline-item .el-timeline-item__node {
  background-color: #ffd666;
  border-color: #ffd666;
}
.news-title {
  font-size: 1.125rem;
  font-weight: bold;
  color: #d48806;
}
.news-summary {
  color: #595959;
}
</style>
