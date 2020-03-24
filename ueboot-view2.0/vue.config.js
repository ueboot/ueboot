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
  // 修改 src 目录 为 examples 目录
  pages: {
    index: {
      entry: 'examples/main.js',
      template: 'examples/index.html',
      filename: 'index.html'
    }
  },
  // 扩展 webpack 配置，使 packages 加入编译
  chainWebpack: config => {
    config.module
        .rule('js')
        .include
        .add(__dirname+ +'/packages')
        .end()
        .use('babel')
        .loader('babel-loader')
        .tap(options => {
          // 修改它的选项...
          return options
        })
  },
  css:{
    loaderOptions:{
      less:{
        javascriptEnabled:true
      }
    }
  }
}
