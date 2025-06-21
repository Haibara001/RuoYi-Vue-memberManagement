import request from '@/utils/request'

// 查询“我的好友”
export function listMyFriends(params) {
  return request({
    url: '/memberManagement/memberFriend/myFriends',
    method: 'get',
    params
  })
}


// 查询会员好友关系列表
export function listMemberFriend(query) {
  return request({
    url: '/memberManagement/memberFriend/list',
    method: 'get',
    params: query
  })
}

// 查询会员好友关系详细
export function getMemberFriend(userA) {
  return request({
    url: '/memberManagement/memberFriend/' + userA,
    method: 'get'
  })
}

// 新增会员好友关系
export function addMemberFriend(data) {
  return request({
    url: '/memberManagement/memberFriend',
    method: 'post',
    data: data
  })
}

// 修改会员好友关系
export function updateMemberFriend(data) {
  return request({
    url: '/memberManagement/memberFriend',
    method: 'put',
    data: data
  })
}

// 删除会员好友关系
export function delMemberFriend(userA) {
  return request({
    url: '/memberManagement/memberFriend/' + userA,
    method: 'delete'
  })
}


// api/memberFriend.js
export function delMyFriend(friendId) {
  return request({
    url: `/memberManagement/memberFriend/myFriends/${friendId}`,
    method: 'delete'
  });
}

export function getFriendSpace(friendId) {
  return request({
    url: `/memberManagement/memberSpace/${friendId}`,
    method: 'get'
  });
}
