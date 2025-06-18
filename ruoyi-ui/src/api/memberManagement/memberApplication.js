import request from '@/utils/request'

// 查询会员入会申请列表
export function listMemberApplication(query) {
  return request({
    url: '/memberManagement/memberApplication/list',
    method: 'get',
    params: query
  })
}

// 查询会员入会申请详细
export function getMemberApplication(appId) {
  return request({
    url: '/memberManagement/memberApplication/' + appId,
    method: 'get'
  })
}

// 新增会员入会申请
export function addMemberApplication(data) {
  return request({
    url: '/memberManagement/memberApplication',
    method: 'post',
    data: data
  })
}

// 修改会员入会申请
export function updateMemberApplication(data) {
  return request({
    url: '/memberManagement/memberApplication',
    method: 'put',
    data: data
  })
}

// 删除会员入会申请
export function delMemberApplication(appId) {
  return request({
    url: '/memberManagement/memberApplication/' + appId,
    method: 'delete'
  })
}
