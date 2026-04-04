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

package tech.ordinaryroad.live.chat.client.douyin.client;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.Header;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolConfig;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import tech.ordinaryroad.live.chat.client.codec.douyin.api.DouyinApis;
import tech.ordinaryroad.live.chat.client.codec.douyin.constant.DouyinCmdEnum;
import tech.ordinaryroad.live.chat.client.codec.douyin.msg.base.IDouyinMsg;
import tech.ordinaryroad.live.chat.client.codec.douyin.room.DouyinRoomInitResult;
import tech.ordinaryroad.live.chat.client.commons.base.exception.BaseException;
import tech.ordinaryroad.live.chat.client.commons.base.listener.IBaseConnectionListener;
import tech.ordinaryroad.live.chat.client.commons.util.OrJavaScriptUtil;
import tech.ordinaryroad.live.chat.client.commons.util.OrLiveChatCollUtil;
import tech.ordinaryroad.live.chat.client.commons.util.OrLiveChatHttpUtil;
import tech.ordinaryroad.live.chat.client.douyin.config.DouyinLiveChatClientConfig;
import tech.ordinaryroad.live.chat.client.douyin.listener.IDouyinConnectionListener;
import tech.ordinaryroad.live.chat.client.douyin.listener.IDouyinMsgListener;
import tech.ordinaryroad.live.chat.client.douyin.netty.handler.DouyinBinaryFrameHandler;
import tech.ordinaryroad.live.chat.client.douyin.netty.handler.DouyinConnectionHandler;
import tech.ordinaryroad.live.chat.client.douyin.netty.handler.DouyinLiveChatClientChannelInitializer;
import tech.ordinaryroad.live.chat.client.plugin.forward.ForwardMsgPlugin;
import tech.ordinaryroad.live.chat.client.servers.netty.client.base.BaseNettyClient;

import javax.script.ScriptEngine;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author mjz
 * @date 2024/1/2
 */
@Slf4j
public class DouyinLiveChatClient extends BaseNettyClient<DouyinLiveChatClientConfig, DouyinRoomInitResult, DouyinCmdEnum, IDouyinMsg, IDouyinMsgListener, DouyinConnectionHandler, DouyinBinaryFrameHandler> {

    public static String JS_SDK;

    static {
        InputStream resourceAsStream = DouyinLiveChatClient.class.getResourceAsStream("/js/douyin-webmssdk.js");
        JS_SDK = IoUtil.readUtf8(resourceAsStream);
    }

    public DouyinLiveChatClient(DouyinLiveChatClientConfig config, List<IDouyinMsgListener> msgListeners, IDouyinConnectionListener connectionListener, EventLoopGroup workerGroup) {
        super(config, workerGroup, connectionListener, IDouyinMsgListener.class);
        addMsgListeners(msgListeners);

        // 初始化
        this.init();
    }

    public DouyinLiveChatClient(DouyinLiveChatClientConfig config, IDouyinMsgListener msgListener, IDouyinConnectionListener connectionListener, EventLoopGroup workerGroup) {
        super(config, workerGroup, connectionListener, IDouyinMsgListener.class);
        addMsgListener(msgListener);

        // 初始化
        this.init();
    }

    public DouyinLiveChatClient(DouyinLiveChatClientConfig config, IDouyinMsgListener msgListener, IDouyinConnectionListener connectionListener) {
        this(config, msgListener, connectionListener, new NioEventLoopGroup());
    }

    public DouyinLiveChatClient(DouyinLiveChatClientConfig config, IDouyinMsgListener msgListener) {
        this(config, msgListener, null, new NioEventLoopGroup());
    }

    public DouyinLiveChatClient(DouyinLiveChatClientConfig config) {
        this(config, null);
    }

    @Override
    public void init() {
        // TODO remove this
        addPlugin(new ForwardMsgPlugin(getConfig().getForwardWebsocketUri()));
        super.init();
    }

    @Override
    public DouyinConnectionHandler initConnectionHandler(IBaseConnectionListener<DouyinConnectionHandler> clientConnectionListener) {
        return new DouyinConnectionHandler(() -> {
            DefaultHttpHeaders headers = new DefaultHttpHeaders();
            headers.add(Header.COOKIE.name(), DouyinApis.KEY_COOKIE_TTWID + "=" + roomInitResult.getTtwid());
            headers.add(Header.USER_AGENT.name(), getConfig().getUserAgent());
            return new WebSocketClientProtocolHandler(
                    WebSocketClientProtocolConfig.newBuilder()
                            .webSocketUri(getWebsocketUri())
                            .version(WebSocketVersion.V13)
                            .subprotocol(null)
                            .allowExtensions(true)
                            .customHeaders(headers)
                            .maxFramePayloadLength(getConfig().getMaxFramePayloadLength())
                            .handshakeTimeoutMillis(getConfig().getHandshakeTimeoutMillis())
                            .build()
            );
        }, DouyinLiveChatClient.this, clientConnectionListener);
    }

    @Override
    protected void initChannel(SocketChannel channel) {
        channel.pipeline().addLast(new DouyinLiveChatClientChannelInitializer(this));
    }

    @Override
    public DouyinRoomInitResult initRoom() {
        return DouyinApis.roomInit(getConfig().getRoomId(), getConfig().getCookie(), roomInitResult);
    }

    @Override
    protected String getWebSocketUriString() {
        String webSocketUriString = super.getWebSocketUriString();
        if (StrUtil.isBlank(webSocketUriString)) {
            webSocketUriString = OrLiveChatCollUtil.getRandom(DouyinLiveChatClientConfig.WEB_SOCKET_URIS);
        }

        long realRoomId = roomInitResult.getRealRoomId();
        String userUniqueId = roomInitResult.getUserUniqueId();

        Map<String, String> queryParams = new LinkedHashMap<>();
        queryParams.put("app_name", "douyin_web");
        queryParams.put("version_code", getConfig().getVersionCode());
        queryParams.put("webcast_sdk_version", getConfig().getWebcastSdkVersion());
        queryParams.put("update_version_code", getConfig().getUpdateVersionCode());
        queryParams.put("compress", "gzip");
        queryParams.put("device_platform", "web");
        queryParams.put("cookie_enabled", "true");
        queryParams.put("screen_width", "1280");
        queryParams.put("screen_height", "800");
        queryParams.put("browser_language", "zh-CN");
        queryParams.put("browser_platform", "MacIntel");
        queryParams.put("browser_name", "Mozilla");
        queryParams.put("browser_version", getConfig().getBrowserVersion());
        queryParams.put("browser_online", "true");
        queryParams.put("tz_name", "Asia/Shanghai");
        // cursor=r-1_d-1_u-1_fh-743192 5703690294282_t-1730380287061&
        // cursor=t-1730378827676_r-1_d-1_u-1_fh-743192 0359546983464&
//        queryParams.put("cursor", "t-" + System.currentTimeMillis() + "_r-1_d-1_u-1_h-1");
        queryParams.put("cursor", "t-" + System.currentTimeMillis() + "_r-1_d-1_u-1_fh-743192" + RandomUtil.randomNumbers(13));
        queryParams.put("internal_ext", "internal_src:dim|" +
                        "wss_push_room_id:" + realRoomId + "|" +
                        "wss_push_did:" + userUniqueId + "|" +
                        "first_req_ms:" + System.currentTimeMillis() + "|" +
                        "fetch_time:" + System.currentTimeMillis() + "|" +
                        "seq:1|" +
                        "wss_info:0-" + System.currentTimeMillis() + "-0-0|" +
                        // wrds_v:743192 6738013141490&
                        // wrds_v:743192 0463065920405&
                        "wrds_v:743192" + RandomUtil.randomNumbers(13)
//                "wrds_kvs:WebcastRoomStatsMessage-" + System.nanoTime() + "_WebcastRoomRankMessage-" + System.nanoTime() + "_LotteryInfoSyncData-" + System.nanoTime() + "_WebcastActivityEmojiGroupsMessage-" + System.nanoTime()
        );
        queryParams.put("host", "https://live.douyin.com");
        queryParams.put("aid", "6383");
        queryParams.put("live_id", "1");
        queryParams.put("did_rule", "3");
        queryParams.put("endpoint", "live_pc");
        queryParams.put("support_wrds", "1");
        queryParams.put("user_unique_id", userUniqueId);
        queryParams.put("im_path", "/webcast/im/fetch/");
        queryParams.put("identity", "audience");
        queryParams.put("need_persist_msg_count", "15");
        queryParams.put("insert_task_id", "");
        queryParams.put("live_reason", "");
        queryParams.put("room_id", Long.toString(realRoomId));
        queryParams.put("heartbeatDuration ", "0");
        queryParams.put("signature", getSignature(getConfig().getUserAgent(), roomInitResult.getRealRoomId(), roomInitResult.getUserUniqueId()));
        return webSocketUriString + "?" + OrLiveChatHttpUtil.toParams(queryParams);
    }

    public void sendDanmu(Object danmu, Runnable success, Consumer<Throwable> failed) {
        super.sendDanmu(danmu, success, failed);
    }

    /**
     * 生成抖音直播WebSocket连接的签名
     * 
     * <p>抖音直播协议使用复杂的签名机制来验证请求的合法性，该方法通过以下步骤生成签名：</p>
     * 
     * <ol>
     *   <li>创建JavaScript执行环境，模拟浏览器环境</li>
     *   <li>构建签名参数字符串，包含直播相关的各种参数</li>
     *   <li>对参数字符串进行MD5哈希计算</li>
     *   <li>调用JavaScript SDK中的加密函数对MD5值进行二次加密</li>
     * </ol>
     * 
     * <p>签名参数说明：</p>
     * <ul>
     *   <li>live_id: 直播ID，固定为1</li>
     *   <li>aid: 应用ID，固定为6383（抖音Web端）</li>
     *   <li>version_code: 版本号，从配置获取</li>
     *   <li>webcast_sdk_version: Web直播SDK版本，从配置获取</li>
     *   <li>room_id: 直播间ID</li>
     *   <li>user_unique_id: 用户唯一标识</li>
     *   <li>device_platform: 设备平台，固定为web</li>
     *   <li>identity: 身份标识，固定为audience（观众）</li>
     * </ul>
     * 
     * @param userAgent 用户代理字符串，用于模拟浏览器环境
     * @param roomId 直播间ID
     * @param userUniqueId 用户唯一标识
     * @return 生成的签名字符串
     */
    @SneakyThrows
    public String getSignature(String userAgent, long roomId, String userUniqueId) {
        // 1. 创建JavaScript执行环境，模拟浏览器环境
        // 设置document、window、navigator等全局对象，确保JavaScript SDK能正常执行
        String JS_ENV = " document = {};\nwindow = {};\nnavigator = {\nuserAgent: '" + userAgent + "'\n};\n";
        ScriptEngine scriptEngine = OrJavaScriptUtil.createScriptEngine();
        
        // 加载JavaScript SDK（JS_SDK是包含加密算法的JavaScript代码）
        scriptEngine.eval(JS_ENV + JS_SDK);
        
        // 2. 构建签名参数字符串
        // 按照抖音协议的格式拼接参数，参数顺序和格式都有严格要求
        String signPram = ("live_id=1,aid=6383," +
                "version_code=$version_code$," +
                "webcast_sdk_version=$webcast_sdk_version$," +
                "room_id=$roomId$," +
                "sub_room_id=,sub_channel_id=,did_rule=3," +
                "user_unique_id=$userId$," +
                "device_platform=web,device_type=,ac=,identity=audience")
                .replace("$webcast_sdk_version$", getConfig().getWebcastSdkVersion())
                .replace("$version_code$", getConfig().getVersionCode())
                .replace("$roomId$", String.valueOf(roomId))
                .replace("$userId$", userUniqueId);
        
        // 3. 对参数字符串进行MD5哈希计算
        // 这是签名的第一步加密，确保参数完整性
        String md5Hex = DigestUtil.md5Hex(signPram.getBytes(StandardCharsets.UTF_8));
        
        try {
            // 4. 调用JavaScript SDK中的get_sign函数进行二次加密
            // JavaScript SDK中包含抖音特有的加密算法，这是签名的核心部分
            Object eval = scriptEngine.eval("get_sign('" + md5Hex + "')");
            return eval.toString();
        } catch (Exception e) {
            throw new BaseException("签名生成失败: getSignature", e);
        }
    }
}