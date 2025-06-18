<!-- ruoyi-ui/src/views/public/index.vue -->
<template>
  <div class="iframe-wrapper">
    <iframe
      src="/external.html"
      frameborder="0"
      @load="onLoadAdjust"
      ref="myIframe"
    ></iframe>
  </div>
</template>

<script>
export default {
  name: 'PublicIndex',
  methods: {
    onLoadAdjust() {
      // 如果同源，可以做自动高度计算。可选：
      try {
        const iframe = this.$refs.myIframe;
        const innerDoc = iframe.contentDocument || iframe.contentWindow.document;
        const h = innerDoc.documentElement.scrollHeight || innerDoc.body.scrollHeight;
        iframe.style.height = h + 'px';
      } catch (e) {
        // 跨域或读取不到就忽略
      }
    }
  }
};
</script>

<style scoped>
/* “100vh - 100px” = 剩余可视高度 */
.iframe-wrapper {
  width: 100%;
  height: calc(100vh - 100px);
  position: relative;
}
.iframe-wrapper iframe {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  /* 先给个 100% 填满父容器，onLoadAdjust 会根据实际内容动态重置 */
  height: 100%;
  border: none;
}
</style>
