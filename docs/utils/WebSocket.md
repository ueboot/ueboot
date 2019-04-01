```javascript 1.6
    import {WebSocket} from "ueboot";


let socket = new WebSocket({
          url: url,
          onopen: function() {
            // 告知服务端用户已经在线，并获取userId和session信息
            this.send(JSON.stringify({"type": "login", "userId": this.userId, "registryId": _this.registryId}));
          },
          onmessage: (message) => {
            this.onMessage(message.data);
          },
          onclose: () => {
            this.socket = null;
            this.$Notice.error({title: "登录会话", desc: "当前登录会话已经丢失，请重新刷新尝试连接！"});
          },
          onerror: () => {
            this.socket = null;
            this.$Notice.error({title: "登录会话", desc: "当前登录会话发生异常，请重新刷新尝试连接！"});
          }
        });



```