import request from '@/utils/request'

// 查询会员好友申请列表
export function listMemberFriendRequest(query) {
  return request({
    url: '/memberManagement/memberFriendRequest/list',
    method: 'get',
    params: query
  })
}

// 查询会员好友申请详细
export function getMemberFriendRequest(requestId) {
  return request({
    url: '/memberManagement/memberFriendRequest/' + requestId,
    method: 'get'
  })
}

// 新增会员好友申请
export function addMemberFriendRequest(data) {
  return request({
    url: '/memberManagement/memberFriendRequest',
    method: 'post',
    data: data
  })
}

// 修改会员好友申请
export function updateMemberFriendRequest(data) {
  return request({
    url: '/memberManagement/memberFriendRequest',
    method: 'put',
    data: data
  })
}

// 删除会员好友申请
export function delMemberFriendRequest(requestId) {
  return request({
    url: '/memberManagement/memberFriendRequest/' + requestId,
    method: 'delete'
  })
}
