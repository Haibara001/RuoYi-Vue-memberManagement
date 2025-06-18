import request from '@/utils/request'

// 查询会员扩展资料列表
export function listMemberProfile(query) {
  return request({
    url: '/memberManagement/memberProfile/list',
    method: 'get',
    params: query
  })
}

// 查询会员扩展资料详细
export function getMemberProfile(userId) {
  return request({
    url: '/memberManagement/memberProfile/' + userId,
    method: 'get'
  })
}

// 新增会员扩展资料
export function addMemberProfile(data) {
  return request({
    url: '/memberManagement/memberProfile',
    method: 'post',
    data: data
  })
}

// 修改会员扩展资料
export function updateMemberProfile(data) {
  return request({
    url: '/memberManagement/memberProfile',
    method: 'put',
    data: data
  })
}

// 删除会员扩展资料
export function delMemberProfile(userId) {
  return request({
    url: '/memberManagement/memberProfile/' + userId,
    method: 'delete'
  })
}
