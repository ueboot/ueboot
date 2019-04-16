const path = require('path')
module.exports = {
  pages: {
    index: {
      entry: './examples/main.js',
      template: './examples/index.html'
    }
  },
  css: {
    loaderOptions: {
      // 这里的选项会传递给 less-loader
      less: {
        // 防止出现bezierEasingMixin(); Inline JavaScript is not enabled 错误提示
        javascriptEnabled: true
      }
    }
  },
  configureWebpack: {
    resolve: {
      alias: {
        'ueboot': path.resolve(__dirname, './src/index'),
        'vue$': 'vue/dist/vue.esm.js',
        '@': path.resolve('src'),
      }
    }
  }
}
