import request from '@/utils/request'

// 查询会员日志/帖子列表
export function listMemberPost(query) {
  return request({
    url: '/memberManagement/memberPost/list',
    method: 'get',
    params: query
  })
}

// 查询会员日志/帖子详细
export function getMemberPost(postId) {
  return request({
    url: '/memberManagement/memberPost/' + postId,
    method: 'get'
  })
}

// 新增会员日志/帖子
export function addMemberPost(data) {
  return request({
    url: '/memberManagement/memberPost',
    method: 'post',
    data: data
  })
}

// 修改会员日志/帖子
export function updateMemberPost(data) {
  return request({
    url: '/memberManagement/memberPost',
    method: 'put',
    data: data
  })
}

// 删除会员日志/帖子
export function delMemberPost(postId) {
  return request({
    url: '/memberManagement/memberPost/' + postId,
    method: 'delete'
  })
}
