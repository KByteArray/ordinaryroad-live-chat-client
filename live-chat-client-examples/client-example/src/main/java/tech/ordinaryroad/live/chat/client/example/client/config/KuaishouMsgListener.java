 /*
  * MIT License
  *
  * Copyright (c) 2023 OrdinaryRoad
  *
  * Permission is hereby granted, free of charge, to any person obtaining a copy
  * of this software and associated documentation files (the "Software"), to deal
  * in the Software without restriction, including without limitation the rights
  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  * copies of the Software, and to permit persons to whom the Software is
  * furnished to do so, subject to the following conditions:
  *
  * The above copyright notice and this permission notice shall be included in all
  * copies or substantial portions of the Software.
  *
  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  * SOFTWARE.
  */

 package tech.ordinaryroad.live.chat.client.example.client.config;

 import cn.hutool.extra.spring.SpringUtil;
 import lombok.extern.slf4j.Slf4j;
 import org.springframework.stereotype.Service;
 import tech.ordinaryroad.live.chat.client.codec.kuaishou.msg.KuaishouDanmuMsg;
 import tech.ordinaryroad.live.chat.client.codec.kuaishou.msg.KuaishouGiftMsg;
 import tech.ordinaryroad.live.chat.client.codec.kuaishou.msg.KuaishouRoomStatsMsg;
 import tech.ordinaryroad.live.chat.client.commons.base.constant.RoomLiveStatusEnum;
 import tech.ordinaryroad.live.chat.client.commons.base.msg.IMsg;
 import tech.ordinaryroad.live.chat.client.kuaishou.client.KuaishouLiveChatClient;
 import tech.ordinaryroad.live.chat.client.kuaishou.listener.IKuaishouMsgListener;
 import tech.ordinaryroad.live.chat.client.kuaishou.netty.handler.KuaishouBinaryFrameHandler;

 /**
  * 快手直播消息监听器实现类
  *
  * @author mjz
  * @date 2024/1/2
  */
 @Slf4j
 @Service
 public class KuaishouMsgListener implements IKuaishouMsgListener {

     /**
      * 从 Spring 容器中获取快手直播客户端实例
      *
      * @return KuaishouLiveChatClient
      */
     private KuaishouLiveChatClient getKuaishouLiveChatClient() {
         return SpringUtil.getBean(KuaishouLiveChatClient.class);
     }

     /**
      * 收到弹幕消息时的回调
      *
      * @param binaryFrameHandler 处理器
      * @param msg                弹幕消息内容
      */
     @Override
     public void onDanmuMsg(KuaishouBinaryFrameHandler binaryFrameHandler, KuaishouDanmuMsg msg) {
         IKuaishouMsgListener.super.onDanmuMsg(binaryFrameHandler, msg);
         String content = msg.getContent();
         String uid = msg.getUid();
         log.info("{} 收到弹幕 {} {}({})：{}", binaryFrameHandler.getRoomId(), msg.getBadgeLevel() != 0 ? msg.getBadgeLevel() + msg.getBadgeName() : "", msg.getUsername(), uid, content);

         // TODO 可以用大模型进行FAQ回复
         // String answer = content + "  的回复";
         // kuaishouLiveChatClient.sendDanmu(answer);

         // 获取当前直播间的实时存活状态
         RoomLiveStatusEnum roomLiveStatus = getKuaishouLiveChatClient().getRoomInitResult().getRoomLiveStatus();
         log.debug("直播间状态 {}", roomLiveStatus);
         tech.ordinaryroad.live.chat.client.codec.kuaishou.protobuf.SimpleUserInfoOuterClass.SimpleUserInfo user = msg.getMsg().getUser();
         String displayId = user.getPrincipalId();
         log.info("{}:到弹幕消息 用户：{} 快手号：{},内容:{}", binaryFrameHandler.getRoomId(), msg.getUsername(), displayId, content);
     }

     /**
      * 收到礼物消息时的回调
      *
      * @param binaryFrameHandler 处理器
      * @param msg                礼物消息内容
      */
     @Override
     public void onGiftMsg(KuaishouBinaryFrameHandler binaryFrameHandler, KuaishouGiftMsg msg) {
         IKuaishouMsgListener.super.onGiftMsg(binaryFrameHandler, msg);
         // log.info("{} 收到礼物 {} {}({}) {} {}({})x{}({})", binaryFrameHandler.getRoomId(), msg.getBadgeLevel() != 0 ? msg.getBadgeLevel() + msg.getBadgeName() : "", msg.getUsername(), msg.getUid(), msg.getUid(), msg.getGiftName(), msg.getGiftId(), msg.getGiftCount(), msg.getGiftPrice());
     }

     /**
      * 收到房间统计信息（如人数、点赞数）时的回调
      *
      * @param binaryFrameHandler 处理器
      * @param msg                统计消息内容
      */
     @Override
     public void onRoomStatsMsg(KuaishouBinaryFrameHandler binaryFrameHandler, KuaishouRoomStatsMsg msg) {
         IKuaishouMsgListener.super.onRoomStatsMsg(binaryFrameHandler, msg);
         log.info("{} 统计信息 累计点赞数: {}, 当前观看人数: {}, 累计观看人数: {}", binaryFrameHandler.getRoomId(), msg.getLikedCount(), msg.getWatchingCount(), msg.getWatchedCount());
     }

     /**
      * 收到所有类型消息的通用回调
      *
      * @param msg 原始消息对象
      */
     @Override
     public void onMsg(IMsg msg) {
         // KuaishouCmdMsg cmdMsg = (KuaishouCmdMsg) msg;
         // log.info("收到{}消息 {}", msg.getClass(), msg);
     }

     /**
      * 收到无法识别的 CMD 指令时的回调
      *
      * @param cmdString 指令名称
      * @param msg       原始消息对象
      */
     @Override
     public void onUnknownCmd(String cmdString, IMsg msg) {
         log.info("收到未知CMD消息 {}", cmdString);
     }
 }
