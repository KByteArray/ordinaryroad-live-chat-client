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

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolConfig;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import lombok.extern.slf4j.Slf4j;
import tech.ordinaryroad.live.chat.client.codec.huya.api.HuyaApis;
import tech.ordinaryroad.live.chat.client.codec.huya.constant.HuyaCmdEnum;
import tech.ordinaryroad.live.chat.client.codec.huya.msg.WebSocketCommand;
import tech.ordinaryroad.live.chat.client.codec.huya.msg.base.IHuyaMsg;
import tech.ordinaryroad.live.chat.client.codec.huya.msg.factory.HuyaMsgFactory;
import tech.ordinaryroad.live.chat.client.codec.huya.room.HuyaRoomInitResult;
import tech.ordinaryroad.live.chat.client.commons.base.listener.IBaseConnectionListener;
import tech.ordinaryroad.live.chat.client.huya.config.HuyaLiveChatClientConfig;
import tech.ordinaryroad.live.chat.client.huya.listener.IHuyaConnectionListener;
import tech.ordinaryroad.live.chat.client.huya.listener.IHuyaMsgListener;
import tech.ordinaryroad.live.chat.client.huya.netty.handler.HuyaBinaryFrameHandler;
import tech.ordinaryroad.live.chat.client.huya.netty.handler.HuyaConnectionHandler;
import tech.ordinaryroad.live.chat.client.huya.netty.handler.HuyaLiveChatClientChannelInitializer;
import tech.ordinaryroad.live.chat.client.plugin.forward.ForwardMsgPlugin;
import tech.ordinaryroad.live.chat.client.servers.netty.client.base.BaseNettyClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * 虎牙直播间弹幕客户端
 *
 * @author mjz
 * @date 2023/8/20
 */
@Slf4j
public class HuyaLiveChatClient extends BaseNettyClient<
        HuyaLiveChatClientConfig,
        HuyaRoomInitResult,
        HuyaCmdEnum,
        IHuyaMsg,
        IHuyaMsgListener,
        HuyaConnectionHandler,
        HuyaBinaryFrameHandler> {

    /**
     * 待完成的 payId Future（getSequence 响应到达时完成）
     */
    private final AtomicReference<CompletableFuture<String>> pendingPayIdFuture = new AtomicReference<>();

    public HuyaLiveChatClient(HuyaLiveChatClientConfig config, List<IHuyaMsgListener> msgListeners, IHuyaConnectionListener connectionListener, EventLoopGroup workerGroup) {
        super(config, workerGroup, connectionListener, IHuyaMsgListener.class);
        addMsgListeners(msgListeners);

        // 初始化
        this.init();
    }

    public HuyaLiveChatClient(HuyaLiveChatClientConfig config, IHuyaMsgListener msgListener, IHuyaConnectionListener connectionListener, EventLoopGroup workerGroup) {
        super(config, workerGroup, connectionListener, IHuyaMsgListener.class);
        addMsgListener(msgListener);

        // 初始化
        this.init();
    }

    public HuyaLiveChatClient(HuyaLiveChatClientConfig config, IHuyaMsgListener msgListener, IHuyaConnectionListener connectionListener) {
        this(config, msgListener, connectionListener, new NioEventLoopGroup());
    }

    public HuyaLiveChatClient(HuyaLiveChatClientConfig config, IHuyaMsgListener msgListener) {
        this(config, msgListener, null, new NioEventLoopGroup());
    }

    public HuyaLiveChatClient(HuyaLiveChatClientConfig config) {
        this(config, null);
    }

    /**
     * 获取 payId（sSeq），通过 getSequence 请求，响应到达时 Future 完成
     *
     * @return 收到 getSequence 响应时以 sSeq 完成；请求发送失败时 exceptionally 完成
     */
    public CompletableFuture<String> getPayId() {
        CompletableFuture<String> future = new CompletableFuture<>();
        pendingPayIdFuture.set(future);
        WebSocketCommand getSequenceReq = HuyaMsgFactory.getInstance(getConfig().getRoomId())
                .createGetSequenceReq(getConfig().getVer(), getConfig().getCookie());
        send(getSequenceReq, () -> {
            if (log.isDebugEnabled()) {
                log.debug("getSequence 请求已发送，等待 payId");
            }
        }, (e) -> {
            pendingPayIdFuture.set(null);
            log.error("getSequence 请求发送失败, error: {}", e.getMessage());
            future.completeExceptionally(e);
        });
        return future;
    }

    /**
     * 获取并清除待完成的 payId Future，供 getSequence 响应处理时使用
     */
    public CompletableFuture<String> getAndClearPendingPayIdFuture() {
        return pendingPayIdFuture.getAndSet(null);
    }

    @Override
    public void init() {
        // TODO remove this
        addPlugin(new ForwardMsgPlugin(getConfig().getForwardWebsocketUri()));
        super.init();
    }

    @Override
    public HuyaRoomInitResult initRoom() {
        return HuyaApis.roomInit(getConfig().getRoomId(), getConfig().getCookie(), roomInitResult);
    }

    @Override
    public HuyaConnectionHandler initConnectionHandler(IBaseConnectionListener<HuyaConnectionHandler> clientConnectionListener) {
        return new HuyaConnectionHandler(
                () -> new WebSocketClientProtocolHandler(
                        WebSocketClientProtocolConfig.newBuilder()
                                .webSocketUri(getWebsocketUri())
                                .version(WebSocketVersion.V13)
                                .subprotocol(null)
                                .allowExtensions(true)
                                .customHeaders(new DefaultHttpHeaders())
                                .maxFramePayloadLength(getConfig().getMaxFramePayloadLength())
                                .handshakeTimeoutMillis(getConfig().getHandshakeTimeoutMillis())
                                .build()
                ),
                HuyaLiveChatClient.this, clientConnectionListener
        );
    }

    @Override
    protected void initChannel(SocketChannel channel) {
        channel.pipeline().addLast(new HuyaLiveChatClientChannelInitializer(HuyaLiveChatClient.this));
    }

    @Override
    public void sendDanmu(Object danmu, Runnable success, Consumer<Throwable> failed) {
        if (!checkCanSendDanmu()) {
            return;
        }

        if (danmu instanceof String) {
            String msg = (String) danmu;
            if (log.isDebugEnabled()) {
                log.debug("{} huya发送弹幕 {}", getConfig().getRoomId(), danmu);
            }

            WebSocketCommand webSocketCommand = null;
            try {
                webSocketCommand = HuyaMsgFactory.getInstance(getConfig().getRoomId()).createSendMessageReq(roomInitResult, msg, getConfig().getVer(), getConfig().getCookie());
            } catch (Exception e) {
                log.error("huya弹幕包创建失败", e);
                if (failed != null) {
                    failed.accept(e);
                }
            }
            if (webSocketCommand == null) {
                return;
            }

            send(webSocketCommand, () -> {
                if (log.isDebugEnabled()) {
                    log.debug("huya弹幕发送成功 {}", danmu);
                }
                if (success != null) {
                    success.run();
                }
                finishSendDanmu();
            }, throwable -> {
                log.error("huya弹幕发送失败", throwable);
                if (failed != null) {
                    failed.accept(throwable);
                }
            });
        } else {
            super.sendDanmu(danmu);
        }
    }

    /**
     * 发送礼物（事务：先通过 getPayId() Future 获取 payId，再 createSendGiftReq 发送礼物）
     *
     * @param giftId    礼物 ID
     * @param giftCount 礼物个数
     */
    public void sendGift(int giftId, int giftCount) {
        sendGift(giftId, giftCount, null, null);
    }

    /**
     * 发送礼物（事务：先 getPayId 获取 payId，再 createSendGiftReq 发送礼物），带回调
     *
     * @param giftId    礼物 ID
     * @param giftCount 礼物个数
     * @param success   发送成功回调（在 createSendGiftReq 发送成功时调用）
     * @param failed    发送失败回调（getPayId 或 createSendGiftReq 失败时调用）
     */
    public void sendGift(int giftId, int giftCount, Runnable success, Consumer<Throwable> failed) {
        getPayId()
                .thenAccept(sSeq -> {
                    WebSocketCommand webSocketCommand = HuyaMsgFactory.getInstance(getConfig().getRoomId()).createSendGiftReq(
                            getRoomInitResult(), sSeq, giftId, giftCount, getConfig().getVer(), getConfig().getCookie());
                    send(webSocketCommand, () -> {
                        if (log.isDebugEnabled()) {
                            log.debug("送礼物请求发送成功，payId: {}, giftId: {}, giftCount: {}", sSeq, giftId, giftCount);
                        }
                        if (success != null) {
                            success.run();
                        }
                    }, (e) -> {
                        log.error("送礼物请求发送失败，payId: {}, giftId: {}, giftCount: {}, error: {}", sSeq, giftId, giftCount, e.getMessage());
                        if (failed != null) {
                            failed.accept(e);
                        }
                    });
                })
                .exceptionally(e -> {
                    if (failed != null) {
                        failed.accept(e);
                    }
                    return null;
                });
    }

}
