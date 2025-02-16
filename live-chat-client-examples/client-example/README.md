# client-example

## 1 配置

```yaml
spring:
  application:
    name: ordinaryroad-live-chat-client-example
tech:
  ordinaryroad:
    live:
      chat:
        client:
          example:
            client:
              config:
                bilibili:
                  # TODO 直播间id
                  roomId: 7777
                  # TODO 浏览器Cookie
                  cookie: ${bilibiliCookie:}
                douyu:
                  # TODO 直播间id
                  roomId: 74751
                  # TODO 浏览器Cookie
                  cookie: ${douyuCookie:}
```

## 2 LiveChatClientController

> 连接
> http://localhost:8080/client/connect?platform=bilibili
> http://localhost:8080/client/connect?platform=douyu
> http://localhost:8080/client/connect?platform=kuaishou

> 断开链接
> http://localhost:8080/client/disconnect/false?platform=bilibili
> http://localhost:8080/client/disconnect/false?platform=douyu
> http://localhost:8080/client/disconnect/false?platform=kuaishou
>
> 断开链接（取消此次的自动重连）
> http://localhost:8080/client/disconnect/true?platform=bilibili
> http://localhost:8080/client/disconnect/true?platform=douyu
> http://localhost:8080/client/disconnect/true?platform=kuaishou

> 修改房间id
> http://localhost:8080/client/roomId/{roomId}?platform=bilibili
> http://localhost:8080/client/roomId/{roomId}?platform=douyu
> http://localhost:8080/client/roomId/{roomId}?platform=kuaishou

> 禁用自动重新连接
> http://localhost:8080/client/autoReconnect/false?platform=bilibili
> http://localhost:8080/client/autoReconnect/false?platform=douyu
> http://localhost:8080/client/autoReconnect/false?platform=kuaishou
>
> 启用自动重新连接
> http://localhost:8080/client/autoReconnect/true?platform=bilibili
> http://localhost:8080/client/autoReconnect/true?platform=douyu
> http://localhost:8080/client/autoReconnect/true?platform=kuaishou

> 更新cookie（清空）
> http://localhost:8080/client/cookie?cookie=&platform=bilibili
> http://localhost:8080/client/cookie?cookie=&platform=douyu
> http://localhost:8080/client/cookie?cookie=&platform=kuaishou

> 发送弹幕“666666“+一位随机数
> http://localhost:8080/client/sendDanmu/666666?platform=bilibili
> http://localhost:8080/client/sendDanmu/666666?platform=douyu
> http://localhost:8080/client/sendDanmu/666666?platform=kuaishou

## 支持同时监听多个直播间

> http://localhost:8080/client/multiply/newClientAndStart/7777?platform=bilibili
> http://localhost:8080/client/multiply/newClientAndStart/6?platform=bilibili
