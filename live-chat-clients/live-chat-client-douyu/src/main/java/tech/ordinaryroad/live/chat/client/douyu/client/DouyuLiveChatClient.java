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

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.RandomUtil;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import tech.ordinaryroad.live.chat.client.codec.douyu.api.DouyuApis;
import tech.ordinaryroad.live.chat.client.codec.douyu.constant.DouyuCmdEnum;
import tech.ordinaryroad.live.chat.client.codec.douyu.msg.ChatmsgMsg;
import tech.ordinaryroad.live.chat.client.codec.douyu.msg.DgbMsg;
import tech.ordinaryroad.live.chat.client.codec.douyu.msg.MsgrepeaterproxylistMsg;
import tech.ordinaryroad.live.chat.client.codec.douyu.msg.UenterMsg;
import tech.ordinaryroad.live.chat.client.codec.douyu.room.DouyuRoomInitResult;
import tech.ordinaryroad.live.chat.client.commons.base.listener.IBaseConnectionListener;
import tech.ordinaryroad.live.chat.client.commons.base.msg.ICmdMsg;
import tech.ordinaryroad.live.chat.client.commons.base.msg.IMsg;
import tech.ordinaryroad.live.chat.client.commons.client.enums.ClientStatusEnums;
import tech.ordinaryroad.live.chat.client.douyu.config.DouyuLiveChatClientConfig;
import tech.ordinaryroad.live.chat.client.douyu.listener.IDouyuConnectionListener;
import tech.ordinaryroad.live.chat.client.douyu.listener.IDouyuMsgListener;
import tech.ordinaryroad.live.chat.client.douyu.netty.handler.DouyuBinaryFrameHandler;
import tech.ordinaryroad.live.chat.client.douyu.netty.handler.DouyuConnectionHandler;
import tech.ordinaryroad.live.chat.client.plugin.forward.ForwardMsgPlugin;

import java.util.List;
import java.util.Map;

/**
 * 直播间弹幕客户端
 *
 * @author mjz
 * @date 2023/8/20
 */
@Slf4j
public class DouyuLiveChatClient extends DouyuWsLiveChatClient implements IDouyuMsgListener {
    private final DouyuWsLiveChatClient proxyClient = this;
    /**
     * 统一管理Ws、Danmu的连接状态
     */
    private final IDouyuConnectionListener connectionListener;
    private DouyuDanmuLiveChatClient danmuClient = null;
    private DouyuConnectionHandler connectionHandler;

    public DouyuLiveChatClient(DouyuLiveChatClientConfig config, List<IDouyuMsgListener> msgListeners, IDouyuConnectionListener connectionListener, EventLoopGroup workerGroup) {
        super(config, msgListeners, null, workerGroup);
        this.connectionListener = connectionListener;
    }

    public DouyuLiveChatClient(DouyuLiveChatClientConfig config, IDouyuMsgListener msgListener, IDouyuConnectionListener connectionListener, EventLoopGroup workerGroup) {
        super(config, msgListener, null, workerGroup);
        this.connectionListener = connectionListener;
    }

    public DouyuLiveChatClient(DouyuLiveChatClientConfig config, IDouyuMsgListener msgListener, IDouyuConnectionListener connectionListener) {
        this(config, msgListener, connectionListener, new NioEventLoopGroup());
    }

    public DouyuLiveChatClient(DouyuLiveChatClientConfig config, IDouyuMsgListener msgListener) {
        this(config, msgListener, null);
    }

    public DouyuLiveChatClient(DouyuLiveChatClientConfig config) {
        this(config, null);
    }

    @Override
    public void init() {
        // TODO remove this
        addPlugin(new ForwardMsgPlugin(getConfig().getForwardWebsocketUri()));
        super.init();
    }

    @Override
    public DouyuRoomInitResult initRoom() {
        DouyuRoomInitResult douyuRoomInitResult = super.initRoom();
        DouyuApis.updateRoomGiftMapCache(douyuRoomInitResult.getRealRoomId());
        return douyuRoomInitResult;
    }

    @Override
    public DouyuConnectionHandler initConnectionHandler(IBaseConnectionListener<DouyuConnectionHandler> clientConnectionListener) {
        this.connectionHandler = super.initConnectionHandler(super.clientConnectionListener);
        return connectionHandler;
    }

    @Override
    public void onMsg(DouyuBinaryFrameHandler binaryFrameHandler, IMsg msg) {
        super.onMsg(binaryFrameHandler, msg);
        if (msg instanceof MsgrepeaterproxylistMsg) {
            MsgrepeaterproxylistMsg msgrepeaterproxylistMsg = (MsgrepeaterproxylistMsg) msg;
            List<Map<String, String>> list = msgrepeaterproxylistMsg.getList();
            if (list.isEmpty()) {
                log.error("弹幕服务器列表为空");
                if (connectionListener != null) {
                    connectionListener.onConnectFailed(connectionHandler);
                }
                disconnect();
            } else {
                // 初始化danmuClient
                int randomIndex = RandomUtil.randomInt(0, list.size());
                Map<String, String> randomMap = list.get(randomIndex);
                DouyuLiveChatClientConfig danmuClientConfig = BeanUtil.toBean(getConfig(), DouyuLiveChatClientConfig.class, CopyOptions.create().ignoreNullValue());
                danmuClientConfig.setWebsocketUri(String.format("wss://%s:%s/", randomMap.get("ip"), randomMap.get("port")));
                destroyDanmuClient();
                this.danmuClient = new DouyuDanmuLiveChatClient(danmuClientConfig, new IDouyuMsgListener() {
                    @Override
                    public void onMsg(DouyuBinaryFrameHandler binaryFrameHandler, IMsg msg) {
                        proxyClient.iteratorMsgListeners(msgListener -> msgListener.onMsg(binaryFrameHandler, msg));
                    }

                    @Override
                    public void onDanmuMsg(DouyuBinaryFrameHandler binaryFrameHandler, ChatmsgMsg msg) {
                        proxyClient.iteratorMsgListeners(msgListener -> msgListener.onDanmuMsg(binaryFrameHandler, msg));
                    }

                    @Override
                    public void onGiftMsg(DouyuBinaryFrameHandler binaryFrameHandler, DgbMsg msg) {
                        proxyClient.iteratorMsgListeners(msgListener -> msgListener.onGiftMsg(binaryFrameHandler, msg));
                    }

                    @Override
                    public void onEnterRoomMsg(DouyuBinaryFrameHandler binaryFrameHandler, UenterMsg uenterMsg) {
                        proxyClient.iteratorMsgListeners(msgListener -> msgListener.onEnterRoomMsg(binaryFrameHandler, uenterMsg));
                    }

                    @Override
                    public void onCmdMsg(DouyuBinaryFrameHandler binaryFrameHandler, DouyuCmdEnum cmd, ICmdMsg<DouyuCmdEnum> cmdMsg) {
                        proxyClient.iteratorMsgListeners(msgListener -> msgListener.onCmdMsg(binaryFrameHandler, cmd, cmdMsg));
                    }

                    @Override
                    public void onOtherCmdMsg(DouyuBinaryFrameHandler binaryFrameHandler, DouyuCmdEnum cmd, ICmdMsg<DouyuCmdEnum> cmdMsg) {
                        proxyClient.iteratorMsgListeners(msgListener -> msgListener.onOtherCmdMsg(binaryFrameHandler, cmd, cmdMsg));
                    }

                    @Override
                    public void onUnknownCmd(DouyuBinaryFrameHandler binaryFrameHandler, String cmdString, IMsg msg) {
                        proxyClient.iteratorMsgListeners(msgListener -> msgListener.onUnknownCmd(binaryFrameHandler, cmdString, msg));
                    }
                }, new IDouyuConnectionListener() {
                    @Override
                    public void onConnected(DouyuConnectionHandler connectionHandler) {
                        if (connectionListener != null) {
                            connectionListener.onConnected(connectionHandler);
                        }
                    }

                    @Override
                    public void onConnectFailed(DouyuConnectionHandler connectionHandler) {
                        if (connectionListener != null) {
                            connectionListener.onConnectFailed(connectionHandler);
                        }
                    }

                    @Override
                    public void onDisconnected(DouyuConnectionHandler connectionHandler) {
                        if (connectionListener != null) {
                            connectionListener.onDisconnected(connectionHandler);
                        }
                    }
                }) {
                    @Override
                    public DouyuRoomInitResult initRoom() {
                        return DouyuLiveChatClient.this.getRoomInitResult();
                    }
                };
                this.danmuClient.addStatusChangeListener((evt, oldStatus, newStatus) -> {
                    switch (newStatus) {
                        case CONNECTED:
                        case RECONNECTING:
                        case CONNECT_FAILED:
                        case DISCONNECTED: {
                            super.setStatus(newStatus);
                            break;
                        }
                        default: {
                            // ignore
                        }
                    }

                });
                this.danmuClient.connect();
            }
        }
    }

    @Override
    protected void setStatus(ClientStatusEnums status) {
        if (status == ClientStatusEnums.CONNECTED) {
            return;
        }
        // 只要不是连接成功就直接销毁DanmuClient
        destroyDanmuClient();
        super.setStatus(status);
    }

    private void destroyDanmuClient() {
        if (danmuClient != null) {
            danmuClient.destroy();
            danmuClient = null;
        }
    }
}
