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
  ALL: 4,
};

let logLevel = level.ALL;

export default {
  d() {
    if (logLevel < level.DEBUG) return;
    let args = Array.prototype.slice.call(arguments);
    console.log.apply(this, args);
  },

  i() {
    if (logLevel < level.INFO) return;
    let args = Array.prototype.slice.call(arguments);
    console.info.apply(this, args);

  },

  e() {
    if (logLevel < level.ERROR) return;
    let args = Array.prototype.slice.call(arguments);
    console.error.apply(this, args);

  },
  config(options) {
    if (options.level) {
      logLevel = options.level;
    }
  }
};

