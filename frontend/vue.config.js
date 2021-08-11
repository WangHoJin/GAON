// Vue3 관련 설정 파일
module.exports = {
  devServer: {
    disableHostCheck: true, //안해주면 로컬에서만 접속되고 외부에서 접속이 안됨!
    https: false,
    port: 8083,
    open: true,
    proxy: {
      "/api/v1": {
        target: process.env.VUE_APP_API_URL
      }
    },
    historyApiFallback: true,
    hot: true
  },
  css: {
    requireModuleExtension: false // import 시에 경로에 .module 포함 안해도 됨.
  },
  transpileDependencies: ["element-plus"],
  lintOnSave: false,
  outputDir: "../backend/src/main/resources/dist"
};
