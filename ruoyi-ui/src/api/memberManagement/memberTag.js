import request from '@/utils/request'

// 查询标签定义列表
export function listMemberTag(query) {
  return request({
    url: '/memberManagement/memberTag/list',
    method: 'get',
    params: query
  })
}

// 查询标签定义详细
export function getMemberTag(tagId) {
  return request({
    url: '/memberManagement/memberTag/' + tagId,
    method: 'get'
  })
}

// 新增标签定义
export function addMemberTag(data) {
  return request({
    url: '/memberManagement/memberTag',
    method: 'post',
    data: data
  })
}

// 修改标签定义
export function updateMemberTag(data) {
  return request({
    url: '/memberManagement/memberTag',
    method: 'put',
    data: data
  })
}

// 删除标签定义
export function delMemberTag(tagId) {
  return request({
    url: '/memberManagement/memberTag/' + tagId,
    method: 'delete'
  })
}
