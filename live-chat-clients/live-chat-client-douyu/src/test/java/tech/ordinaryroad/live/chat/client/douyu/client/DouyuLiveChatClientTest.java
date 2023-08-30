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

package tech.ordinaryroad.live.chat.client.douyu.client;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import tech.ordinaryroad.live.chat.client.commons.base.listener.IBaseConnectionListener;
import tech.ordinaryroad.live.chat.client.commons.base.msg.BaseCmdMsg;
import tech.ordinaryroad.live.chat.client.commons.base.msg.BaseMsg;
import tech.ordinaryroad.live.chat.client.commons.base.msg.IMsg;
import tech.ordinaryroad.live.chat.client.douyu.config.DouyuLiveChatClientConfig;
import tech.ordinaryroad.live.chat.client.douyu.constant.DouyuCmdEnum;
import tech.ordinaryroad.live.chat.client.douyu.listener.IDouyuDouyuCmdMsgListener;
import tech.ordinaryroad.live.chat.client.douyu.msg.ChatmsgMsg;
import tech.ordinaryroad.live.chat.client.douyu.netty.handler.DouyuBinaryFrameHandler;
import tech.ordinaryroad.live.chat.client.douyu.netty.handler.DouyuConnectionHandler;

/**
 * @author mjz
 * @date 2023/8/26
 */
@Slf4j
class DouyuLiveChatClientTest implements IBaseConnectionListener<DouyuConnectionHandler>, IDouyuDouyuCmdMsgListener {

    static Object lock = new Object();
    DouyuLiveChatClient client;

    @Test
    void example() throws InterruptedException {
        DouyuLiveChatClientConfig config = DouyuLiveChatClientConfig.builder()
                // 暂不支持浏览器Cookie
                // TODO 修改房间id（支持短id）
                .roomId(92000)
                .build();

        client = new DouyuLiveChatClient(config, new IDouyuDouyuCmdMsgListener() {
            @Override
            public void onMsg(DouyuBinaryFrameHandler binaryFrameHandler, IMsg msg) {
                IDouyuDouyuCmdMsgListener.super.onMsg(binaryFrameHandler, msg);

                log.debug("{} 收到{}消息 {}", binaryFrameHandler.getRoomId(), msg.getClass(), msg);
            }

            @Override
            public void onDanmuMsg(DouyuBinaryFrameHandler binaryFrameHandler, ChatmsgMsg msg) {
                IDouyuDouyuCmdMsgListener.super.onDanmuMsg(binaryFrameHandler, msg);

                log.info("{} 收到弹幕 {}({})：{}", binaryFrameHandler.getRoomId(), msg.getNn(), msg.getUid(), msg.getTxt());
            }

            @Override
            public void onCmdMsg(DouyuBinaryFrameHandler binaryFrameHandler, DouyuCmdEnum cmd, BaseCmdMsg<DouyuCmdEnum> cmdMsg) {
                IDouyuDouyuCmdMsgListener.super.onCmdMsg(binaryFrameHandler, cmd, cmdMsg);

                log.info("{} 收到CMD消息{} {}", binaryFrameHandler.getRoomId(), cmd, cmdMsg);
            }

            @Override
            public void onOtherCmdMsg(DouyuBinaryFrameHandler binaryFrameHandler, DouyuCmdEnum cmd, BaseCmdMsg<DouyuCmdEnum> cmdMsg) {
                IDouyuDouyuCmdMsgListener.super.onOtherCmdMsg(binaryFrameHandler, cmd, cmdMsg);

                log.debug("{} 收到其他CMD消息 {}", binaryFrameHandler.getRoomId(), cmd);
            }

            @Override
            public void onUnknownCmd(DouyuBinaryFrameHandler binaryFrameHandler, String cmdString, BaseMsg msg) {
                IDouyuDouyuCmdMsgListener.super.onUnknownCmd(binaryFrameHandler, cmdString, msg);

                log.debug("{} 收到未知CMD消息 {}", binaryFrameHandler.getRoomId(), cmdString);
            }
        }, new IBaseConnectionListener<DouyuConnectionHandler>() {
            @Override
            public void onConnected(DouyuConnectionHandler connectionHandler) {
                log.info("{} onConnected", connectionHandler.getRoomId());
            }

            @Override
            public void onConnectFailed(DouyuConnectionHandler connectionHandler) {
                log.info("{} onConnectFailed", connectionHandler.getRoomId());
            }

            @Override
            public void onDisconnected(DouyuConnectionHandler connectionHandler) {
                log.info("{} onDisconnected", connectionHandler.getRoomId());
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

    @Test
    void multiplyClient() throws InterruptedException {
        DouyuLiveChatClientConfig config1 = DouyuLiveChatClientConfig.builder().roomId(890074).build();
        DouyuLiveChatClient client1 = new DouyuLiveChatClient(config1, DouyuLiveChatClientTest.this, DouyuLiveChatClientTest.this);

        DouyuLiveChatClientConfig config2 = DouyuLiveChatClientConfig.builder().roomId(718133).build();
        DouyuLiveChatClient client2 = new DouyuLiveChatClient(config2, DouyuLiveChatClientTest.this, DouyuLiveChatClientTest.this);

        client1.connect(() -> {
            log.warn("client1 connect successfully, start connecting client2");
            client2.connect(()->{
                log.warn("client2 connect successfully");
            });
        });

        // 防止测试时直接退出
        while (true) {
            synchronized (lock) {
                lock.wait();
            }
        }
    }

    @Override
    public void onConnected(DouyuConnectionHandler connectionHandler) {
        log.info("{} onConnected", connectionHandler.getRoomId());
    }

    @Override
    public void onConnectFailed(DouyuConnectionHandler connectionHandler) {
        log.info("{} onConnectFailed", connectionHandler.getRoomId());
    }

    @Override
    public void onDisconnected(DouyuConnectionHandler connectionHandler) {
        log.info("{} onDisconnected", connectionHandler.getRoomId());
    }

    @Override
    public void onDanmuMsg(DouyuBinaryFrameHandler binaryFrameHandler, ChatmsgMsg msg) {
        IDouyuDouyuCmdMsgListener.super.onDanmuMsg(binaryFrameHandler, msg);

        log.info("{} 收到弹幕 {}({})：{}", binaryFrameHandler.getRoomId(), msg.getNn(), msg.getUid(), msg.getTxt());
    }
}