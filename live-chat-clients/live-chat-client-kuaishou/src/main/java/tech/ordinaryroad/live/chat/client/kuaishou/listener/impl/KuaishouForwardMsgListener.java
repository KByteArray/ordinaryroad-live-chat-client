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

package tech.ordinaryroad.live.chat.client.kuaishou.listener.impl;

import cn.hutool.core.util.StrUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import tech.ordinaryroad.live.chat.client.codec.kuaishou.msg.KuaishouDanmuMsg;
import tech.ordinaryroad.live.chat.client.codec.kuaishou.msg.KuaishouGiftMsg;
import tech.ordinaryroad.live.chat.client.codec.kuaishou.msg.KuaishouLikeMsg;
import tech.ordinaryroad.live.chat.client.codec.kuaishou.msg.KuaishouRoomStatsMsg;
import tech.ordinaryroad.live.chat.client.commons.base.exception.BaseException;
import tech.ordinaryroad.live.chat.client.commons.base.msg.IMsg;
import tech.ordinaryroad.live.chat.client.kuaishou.listener.IKuaishouMsgListener;
import tech.ordinaryroad.live.chat.client.websocket.client.WebSocketLiveChatClient;
import tech.ordinaryroad.live.chat.client.websocket.config.WebSocketLiveChatClientConfig;

import java.nio.charset.StandardCharsets;

/**
 * @author mjz
 * @date 2024/3/8
 * @since 0.3.0
 */
public class KuaishouForwardMsgListener implements IKuaishouMsgListener {

    private final WebSocketLiveChatClient webSocketLiveChatClient;

    public KuaishouForwardMsgListener(String webSocketUri) {
        if (StrUtil.isBlank(webSocketUri)) {
            throw new BaseException("转发地址不能为空");
        }
        webSocketLiveChatClient = new WebSocketLiveChatClient(
                WebSocketLiveChatClientConfig.builder()
                        .websocketUri(webSocketUri)
                        .build()
        );
        webSocketLiveChatClient.connect();
    }

    @Override
    public void onDanmuMsg(KuaishouDanmuMsg msg) {
        forward(msg);
    }

    @Override
    public void onGiftMsg(KuaishouGiftMsg msg) {
        forward(msg);
    }

    @Override
    public void onLikeMsg(KuaishouLikeMsg msg) {
        forward(msg);
    }

    @Override
    public void onRoomStatsMsg(KuaishouRoomStatsMsg msg) {
        forward(msg);
    }

    private void forward(IMsg msg) {
        if (webSocketLiveChatClient == null) {
            return;
        }
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
        byteBuf.writeCharSequence(msg.toString(), StandardCharsets.UTF_8);
        webSocketLiveChatClient.send(new BinaryWebSocketFrame(byteBuf));
    }

    public void destroyForwardClient() {
        webSocketLiveChatClient.destroy();
    }
}
