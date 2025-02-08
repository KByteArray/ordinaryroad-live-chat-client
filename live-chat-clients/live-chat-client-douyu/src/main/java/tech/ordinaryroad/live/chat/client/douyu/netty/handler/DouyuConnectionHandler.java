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

package tech.ordinaryroad.live.chat.client.douyu.netty.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import tech.ordinaryroad.live.chat.client.codec.douyu.constant.DouyuClientModeEnum;
import tech.ordinaryroad.live.chat.client.codec.douyu.msg.factory.DouyuMsgFactory;
import tech.ordinaryroad.live.chat.client.commons.base.listener.IBaseConnectionListener;
import tech.ordinaryroad.live.chat.client.douyu.client.base.BaseDouyuLiveChatClient;
import tech.ordinaryroad.live.chat.client.servers.netty.client.handler.BaseNettyClientConnectionHandler;

import java.util.function.Supplier;


/**
 * 连接处理器
 *
 * @author mjz
 * @date 2023/8/21
 */
@Slf4j
@ChannelHandler.Sharable
public class DouyuConnectionHandler extends BaseNettyClientConnectionHandler<BaseDouyuLiveChatClient, DouyuConnectionHandler> {

    @Getter
    private final DouyuClientModeEnum mode;
    /**
     * 以ClientConfig为主
     */
    private final Object roomId;
    /**
     * 以ClientConfig为主
     */
    private final String ver;
    /**
     * 以ClientConfig为主
     */
    private final String aver;
    /**
     * 以ClientConfig为主
     */
    private String cookie;

    public DouyuConnectionHandler(DouyuClientModeEnum mode, Supplier<WebSocketClientProtocolHandler> webSocketProtocolHandler, BaseDouyuLiveChatClient client, IBaseConnectionListener<DouyuConnectionHandler> listener) {
        super(webSocketProtocolHandler, client, listener);
        this.mode = mode;
        this.roomId = client.getConfig().getRoomId();
        this.ver = client.getConfig().getVer();
        this.aver = client.getConfig().getAver();
        this.cookie = client.getConfig().getCookie();
    }

    public DouyuConnectionHandler(DouyuClientModeEnum mode, Supplier<WebSocketClientProtocolHandler> webSocketProtocolHandler, BaseDouyuLiveChatClient client) {
        this(mode, webSocketProtocolHandler, client, null);
    }

    public DouyuConnectionHandler(DouyuClientModeEnum mode, Supplier<WebSocketClientProtocolHandler> webSocketProtocolHandler, long roomId, String ver, String aver, IBaseConnectionListener<DouyuConnectionHandler> listener, String cookie) {
        super(webSocketProtocolHandler, listener);
        this.mode = mode;
        this.roomId = roomId;
        this.ver = ver;
        this.aver = aver;
        this.cookie = cookie;
    }

    public DouyuConnectionHandler(DouyuClientModeEnum mode, Supplier<WebSocketClientProtocolHandler> webSocketProtocolHandler, long roomId, String ver, String aver, IBaseConnectionListener<DouyuConnectionHandler> listener) {
        this(mode, webSocketProtocolHandler, roomId, ver, aver, listener, null);
    }

    public DouyuConnectionHandler(DouyuClientModeEnum mode, Supplier<WebSocketClientProtocolHandler> webSocketProtocolHandler, long roomId, String ver, String aver, String cookie) {
        this(mode, webSocketProtocolHandler, roomId, ver, aver, null, cookie);
    }

    public DouyuConnectionHandler(DouyuClientModeEnum mode, Supplier<WebSocketClientProtocolHandler> webSocketProtocolHandler, long roomId, String ver, String aver) {
        this(mode, webSocketProtocolHandler, roomId, ver, aver, null, null);
    }

    @Override
    public void sendHeartbeat(Channel channel) {
        if (log.isDebugEnabled()) {
            log.debug("发送心跳包");
        }
        WebSocketFrame webSocketFrame;
        if (mode == DouyuClientModeEnum.DANMU) {
            webSocketFrame = new BinaryWebSocketFrame(getMsgFactory(getRoomId()).createHeartbeat());
        } else {
            webSocketFrame = new BinaryWebSocketFrame(getMsgFactory(getRoomId()).createKeeplive(getCookie()));
        }
        channel.writeAndFlush(webSocketFrame).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                if (log.isDebugEnabled()) {
                    log.debug("心跳包发送完成");
                }
            } else {
                log.error("心跳包发送失败", future.cause());
            }
        });
    }

    @Override
    public void sendAuthRequest(Channel channel) {
        if (log.isDebugEnabled()) {
            log.debug("发送认证包");
        }
        channel.writeAndFlush(new BinaryWebSocketFrame(getMsgFactory(getRoomId()).createAuth(getClient().getRoomInitResult(), mode, getVer(), getAver(), getCookie())))
                .addListener(future -> {
                    if (future.isSuccess()) {
                        if (log.isDebugEnabled()) {
                            log.debug("认证包发送完成");
                        }
                    } else {
                        log.error("认证包发送失败", future.cause());
                    }
                });
    }

    private DouyuMsgFactory getMsgFactory(Object roomId) {
        return DouyuMsgFactory.getInstance(roomId);
    }

    public Object getRoomId() {
        return client != null ? client.getConfig().getRoomId() : roomId;
    }

    private String getVer() {
        return client != null ? client.getConfig().getVer() : ver;
    }

    private String getAver() {
        return client != null ? client.getConfig().getAver() : aver;
    }

    private String getCookie() {
        return client != null ? client.getConfig().getCookie() : cookie;
    }
}
