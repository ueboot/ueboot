/**
 * Created by yangkui on 17/4/7.
 */
/**
 * 导出一个默认的方法，实际上就是将这个方法整体导出
 * @returns {"default"}
 */
export default function (options) {
  this.createMethod = function (method, options, stateCallback) {
    let that = this
    this[method] = function () {
      if (stateCallback && stateCallback.apply) {
        stateCallback(method)
      }
      if (options[method] && options[method].apply) {
        options[method].apply(that, arguments)
      }
    }
  }

  let ws

  let events = ['onopen', 'onmessage', 'onclose', 'onerror']

  let prop = {
    opened: false,
    closed: false,
    error: false
  }

  let method

  if (typeof options === 'undefined' || !options) {
    throw new Error('ArgumentException: please add default constructor options')
  }

  this.queue = []

  this.onEventTrigger = function (eventName) {
    let i
    if (eventName === 'onopen') {
      prop.opened = true
      prop.closed = false
      // openend send queue
      if (this.queue.length > 0) {
        for (i = this.queue.length; --i >= 0;) {
          this.send.apply(this, this.queue[0])
          this.queue.splice(0, 1)
        }
      }
    }
    if (eventName === 'onerror') {
      prop.error = true
    }
    if (eventName === 'onclosed') {
      prop.opened = false
      prop.closed = true
    }
  }

  this.init = function () {
    let cb = this.onEventTrigger.bind(this)
    ws = new WebSocket(options.url)

    for (let i = 0; i < events.length; i++) {
      method = events[i]
      this.createMethod.apply(ws, [method, options, cb])
    }
  }

  this.send = function () {
    if (prop.closed) {
      // throw 'InvalidOperation: Cannot send messages to a closed Websocket!';
      console.log('连接已经关闭，尝试重新打开!')
      alert('当前链接已经关闭，请重新刷新尝试连接！')
    }
    if (!prop.opened) {
      this.queue.push(arguments)
    } else {
      ws.send.apply(ws, arguments)
    }
  }
  this.close = function () {
    if (prop.opened) {
      ws.close()
    }
  }

  this.init()
  return this
}
