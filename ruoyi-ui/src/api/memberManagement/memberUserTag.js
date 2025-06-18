import request from '@/utils/request'

// 查询会员-标签关联列表
export function listMemberUserTag(query) {
  return request({
    url: '/memberManagement/memberUserTag/list',
    method: 'get',
    params: query
  })
}

// 查询会员-标签关联详细
export function getMemberUserTag(userId) {
  return request({
    url: '/memberManagement/memberUserTag/' + userId,
    method: 'get'
  })
}

// 新增会员-标签关联
export function addMemberUserTag(data) {
  return request({
    url: '/memberManagement/memberUserTag',
    method: 'post',
    data: data
  })
}

// 修改会员-标签关联
export function updateMemberUserTag(data) {
  return request({
    url: '/memberManagement/memberUserTag',
    method: 'put',
    data: data
  })
}

// 删除会员-标签关联
export function delMemberUserTag(userId) {
  return request({
    url: '/memberManagement/memberUserTag/' + userId,
    method: 'delete'
  })
}
