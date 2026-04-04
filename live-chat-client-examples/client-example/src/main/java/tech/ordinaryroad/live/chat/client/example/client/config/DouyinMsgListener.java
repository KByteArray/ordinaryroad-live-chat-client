

 package tech.ordinaryroad.live.chat.client.example.client.config;

 import cn.hutool.extra.spring.SpringUtil;
 import lombok.extern.slf4j.Slf4j;
 import org.springframework.stereotype.Service;
 import tech.ordinaryroad.live.chat.client.codec.douyin.constant.DouyinCmdEnum;
 import tech.ordinaryroad.live.chat.client.codec.douyin.msg.DouyinCmdMsg;
 import tech.ordinaryroad.live.chat.client.codec.douyin.msg.DouyinDanmuMsg;
 import tech.ordinaryroad.live.chat.client.codec.douyin.msg.DouyinEnterRoomMsg;
 import tech.ordinaryroad.live.chat.client.codec.douyin.msg.DouyinGiftMsg;
 import tech.ordinaryroad.live.chat.client.codec.douyin.msg.DouyinRoomStatsMsg;
 import tech.ordinaryroad.live.chat.client.commons.base.constant.RoomLiveStatusEnum;
 import tech.ordinaryroad.live.chat.client.commons.base.msg.ICmdMsg;
 import tech.ordinaryroad.live.chat.client.commons.base.msg.IMsg;
 import tech.ordinaryroad.live.chat.client.douyin.client.DouyinLiveChatClient;
 import tech.ordinaryroad.live.chat.client.douyin.listener.IDouyinMsgListener;
 import tech.ordinaryroad.live.chat.client.douyin.netty.handler.DouyinBinaryFrameHandler;

 @Slf4j
 @Service
 public class DouyinMsgListener implements IDouyinMsgListener {

     private DouyinLiveChatClient getDouyinLiveChatClient() {
         return SpringUtil.getBean(DouyinLiveChatClient.class);
     }

     @Override
     public void onDanmuMsg(DouyinBinaryFrameHandler binaryFrameHandler, DouyinDanmuMsg msg) {
         IDouyinMsgListener.super.onDanmuMsg(binaryFrameHandler, msg);
         String content = msg.getContent();
         String uid = msg.getUid();
         Object roomId = binaryFrameHandler.getRoomId();
         log.debug("{} 收到弹幕 {} {}({})：{}", roomId, msg.getBadgeLevel() != 0 ? msg.getBadgeLevel() + msg.getBadgeName() : "", msg.getUsername(), uid, content);
         tech.ordinaryroad.live.chat.client.codec.douyin.protobuf.User   user = msg.getMsg().getUser();
         String displayId = user.getDisplayId(); // 抖音号 (最常用)
         log.info("用户 :{} content ：{} 抖音号：{}", msg.getUsername(), content, displayId);
         // TODO 可以用大模型进行FAQ回复
//         String answer = content + "  的回复";
//         DouyinLiveChatClient.sendDanmu(answer);
         RoomLiveStatusEnum roomLiveStatus = getDouyinLiveChatClient().getRoomInitResult().getRoomLiveStatus();
         log.info("直播间状态 {}", roomLiveStatus);
     }

     @Override
     public void onGiftMsg(DouyinBinaryFrameHandler binaryFrameHandler, DouyinGiftMsg msg) {
         IDouyinMsgListener.super.onGiftMsg(binaryFrameHandler, msg);
     }

     @Override
     public void onEnterRoomMsg(DouyinBinaryFrameHandler binaryFrameHandler, DouyinEnterRoomMsg msg) {
         IDouyinMsgListener.super.onEnterRoomMsg(binaryFrameHandler, msg);

         tech.ordinaryroad.live.chat.client.codec.douyin.protobuf.User user = msg.getMsg().getUser();
         String displayId = user.getDisplayId();
         log.info("{} 收到入房消息 用户：{} 抖音号：{}", binaryFrameHandler.getRoomId(), msg.getUsername(), displayId);

     }

     @Override
     public void onRoomStatsMsg(DouyinBinaryFrameHandler binaryFrameHandler, DouyinRoomStatsMsg msg) {
         IDouyinMsgListener.super.onRoomStatsMsg(binaryFrameHandler, msg);
         log.debug("{} 统计信息 累计点赞数: {}, 当前观看人数: {}, 累计观看人数: {}", binaryFrameHandler.getRoomId(), msg.getLikedCount(), msg.getWatchingCount(), msg.getWatchedCount());
     }

     @Override
     public void onCmdMsg(DouyinBinaryFrameHandler binaryFrameHandler, DouyinCmdEnum cmd, ICmdMsg<DouyinCmdEnum> cmdMsg) {
         IDouyinMsgListener.super.onCmdMsg(binaryFrameHandler, cmd, cmdMsg);
         if (cmd == null && cmdMsg instanceof DouyinCmdMsg) {
             DouyinCmdMsg douyinCmdMsg = (DouyinCmdMsg) cmdMsg;
             String method = douyinCmdMsg.getMsg().getMethod();
             // 在这里拦截并处理特定的未知 CMD
             switch (method) {
                 case "WebcastRoomRankMessage":
                     log.debug("{} 收到排行榜消息", binaryFrameHandler.getRoomId());
                     break;
                 case "WebcastRoomDataSyncMessage":
                     log.debug("{} 收到房间数据同步消息", binaryFrameHandler.getRoomId());
                     break;
                 default:
                     // 其他暂不处理的消息可以保持默认或打印简要日志
                     break;
             }
         }
     }

     @Override
     public void onMsg(IMsg msg) {
     }

     @Override
     public void onUnknownCmd(String cmdString, IMsg msg) {
         // 已经在 onCmdMsg 中通过 method 进行了细分处理，这里可以按需保留或关闭
         log.debug("收到未知CMD消息 {}", cmdString);
     }
 }
