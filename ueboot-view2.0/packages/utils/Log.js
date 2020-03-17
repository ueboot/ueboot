/**
 * Created by Richard on 17/4/7.
 */

/**
 * 封装Log类
 */

const level = {
  NONE: 0,
  ERROR: 1,
  INFO: 2,
  DEBUG: 3,
  ALL: 4
}

let logLevel = level.DEBUG

export default {
  d () {
    if (logLevel < level.DEBUG) return
    let args = Array.prototype.slice.call(arguments)
    try {
      window.console.log.apply(this, args)
    } catch (e) {
      // IE下无法使用，吃掉异常，只有打开开发者工具才可以使用
    }
  },

  i () {
    if (logLevel < level.INFO) return
    let args = Array.prototype.slice.call(arguments)
    try {
      window.console.info.apply(this, args)
    } catch (e) {
      // IE下无法使用，吃掉异常，只有打开开发者工具才可以使用
    }
  },

  e () {
    if (logLevel < level.ERROR) return
    let args = Array.prototype.slice.call(arguments)
    try {
      window.console.error.apply(this, args)
    } catch (e) {
      // IE下无法使用，吃掉异常，只有打开开发者工具才可以使用
    }
  },
  config (options) {
    if (options.level) {
      logLevel = options.level
    }
  }
}
