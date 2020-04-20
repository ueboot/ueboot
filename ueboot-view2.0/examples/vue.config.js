module.exports = {
  devServer: {
    port: 7000,
    // 设置代理
    proxy: {
      //本地代理
      '/ueboot/*': {
        target: 'http://localhost:8000',
        debug: true,
        changeOrigin: true,
        secure: false
      }
    }
  },

  css:{
    loaderOptions:{
      less:{
        javascriptEnabled:true
      }
    }
  }
}
