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

package tech.ordinaryroad.live.chat.client.huya.client;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import tech.ordinaryroad.live.chat.client.commons.base.msg.ICmdMsg;
import tech.ordinaryroad.live.chat.client.commons.base.msg.IMsg;
import tech.ordinaryroad.live.chat.client.huya.config.HuyaLiveChatClientConfig;
import tech.ordinaryroad.live.chat.client.huya.constant.HuyaCmdEnum;
import tech.ordinaryroad.live.chat.client.huya.listener.IHuyaMsgListener;
import tech.ordinaryroad.live.chat.client.huya.msg.MessageNoticeMsg;
import tech.ordinaryroad.live.chat.client.huya.msg.SendItemSubBroadcastPacketMsg;
import tech.ordinaryroad.live.chat.client.huya.netty.handler.HuyaBinaryFrameHandler;

/**
 * @author mjz
 * @date 2023/9/5
 */
@Slf4j
class HuyaLiveChatClientTest {

    static Object lock = new Object();
    HuyaChatClient client;

    @Test
    void example() throws InterruptedException {
        HuyaLiveChatClientConfig config = HuyaLiveChatClientConfig.builder()
                // bagea
//                .roomId(189201)
                .roomId(353322)
                .roomId(390001)
                .roomId(1995)
                .build();

        client = new HuyaChatClient(config, new IHuyaMsgListener() {
            @Override
            public void onDanmuMsg(HuyaBinaryFrameHandler binaryFrameHandler, MessageNoticeMsg msg) {
                log.info("{} 收到弹幕 {}({})：{}", binaryFrameHandler.getRoomId(), msg.getUsername(), msg.getUid(), msg.getContent());
            }

            @Override
            public void onGiftMsg(HuyaBinaryFrameHandler binaryFrameHandler, SendItemSubBroadcastPacketMsg msg) {
                log.info("{} 收到礼物 {}({}) {} {}({})x{}({})", binaryFrameHandler.getRoomId(), msg.getUsername(), msg.getUid(), "赠送", msg.getGiftName(), msg.getGiftId(), msg.getGiftCount(), msg.getGiftPrice());
            }

            @Override
            public void onMsg(HuyaBinaryFrameHandler binaryFrameHandler, IMsg msg) {
                log.debug("{} 收到{}消息 {}", binaryFrameHandler.getRoomId(), msg.getClass(), msg);
            }

            @Override
            public void onCmdMsg(HuyaBinaryFrameHandler binaryFrameHandler, HuyaCmdEnum cmd, ICmdMsg<HuyaCmdEnum> cmdMsg) {
                log.info("{} 收到CMD消息{} {}", binaryFrameHandler.getRoomId(), cmd, cmdMsg);
            }

            @Override
            public void onOtherCmdMsg(HuyaBinaryFrameHandler binaryFrameHandler, HuyaCmdEnum cmd, ICmdMsg<HuyaCmdEnum> cmdMsg) {
                log.debug("{} 收到其他CMD消息 {}", binaryFrameHandler.getRoomId(), cmd);
            }

            @Override
            public void onUnknownCmd(HuyaBinaryFrameHandler binaryFrameHandler, String cmdString, IMsg msg) {
                log.debug("{} 收到未知CMD消息 {}", binaryFrameHandler.getRoomId(), cmdString);
            }
        });
        client.connect();

        // 防止测试时直接退出
        while (true) {
            synchronized (lock) {
                lock.wait();
            }
        }
    }

}