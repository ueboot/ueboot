console.log("#######读取到babel配置文件，开始ES6转ES5#####");
module.exports = {
  presets: [
    ["@vue/cli-plugin-babel/preset", {
      "useBuiltIns": false
    }]
  ]
}
