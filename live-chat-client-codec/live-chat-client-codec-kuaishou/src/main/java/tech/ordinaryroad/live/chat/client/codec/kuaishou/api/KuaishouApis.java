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

package tech.ordinaryroad.live.chat.client.codec.kuaishou.api;

import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import tech.ordinaryroad.live.chat.client.codec.kuaishou.constant.RoomInfoGetTypeEnum;
import tech.ordinaryroad.live.chat.client.codec.kuaishou.msg.KuaishouGiftMsg;
import tech.ordinaryroad.live.chat.client.codec.kuaishou.protobuf.LiveAudienceStateOuterClass;
import tech.ordinaryroad.live.chat.client.codec.kuaishou.protobuf.WebGiftFeedOuterClass;
import tech.ordinaryroad.live.chat.client.codec.kuaishou.resp.InterestMaskListResponse;
import tech.ordinaryroad.live.chat.client.codec.kuaishou.resp.KuaishouUserInfoResponse;
import tech.ordinaryroad.live.chat.client.codec.kuaishou.room.KuaishouRoomInitResult;
import tech.ordinaryroad.live.chat.client.commons.base.exception.BaseException;
import tech.ordinaryroad.live.chat.client.commons.util.OrJacksonUtil;
import tech.ordinaryroad.live.chat.client.commons.util.OrLiveChatCookieUtil;
import tech.ordinaryroad.live.chat.client.commons.util.OrLiveChatHttpUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 快手直播API工具类
 * 
 * <p>该类封装了快手直播平台的各种API接口，包括直播间初始化、用户信息获取、礼物系统、互动功能等。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>直播间初始化：获取直播间信息、WebSocket连接信息</li>
 *   <li>用户信息获取：获取用户基本信息</li>
 *   <li>礼物系统：获取礼物列表和礼物信息</li>
 *   <li>互动功能：发送评论、点赞等</li>
 *   <li>兴趣面具：获取兴趣面具列表</li>
 * </ul>
 * 
 * @author mjz
 * @date 2024/1/5
 */
@Slf4j
public class KuaishouApis {

    /**
     * 接口返回结果缓存，缓存时间为1天
     * {@link #KEY_RESULT_CACHE_GIFT_ITEMS}：所有礼物信息
     */
    public static final TimedCache<String, Map<String, GiftInfo>> RESULT_CACHE = new TimedCache<>(TimeUnit.DAYS.toMillis(1));
    
    /**
     * 礼物信息缓存键
     */
    public static final String KEY_RESULT_CACHE_GIFT_ITEMS = "GIFT_ITEMS";
    
    /**
     * 直播间详情JSON中播放列表的正则表达式模式
     */
    public static final String PATTERN_LIVE_ROOM_DETAIL = "\"playList\":\\s*\\[([\\s\\S]*?)\\](?=,\\s*\"loading\"|$)";
    
    /**
     * 礼物连击缓存，缓存时间为5分钟
     */
    private static final TimedCache<String, WebGiftFeedOuterClass.WebGiftFeed> WEB_GIFT_FEED_CACHE = new TimedCache<>(300 * 1000L, new ConcurrentHashMap<>());


    /**
     * 使用Cookie初始化直播间信息
     * 
     * @param roomId 房间ID
     * @param cookie Cookie信息
     * @param kww Kww参数
     * @param roomInitResult 可选的房间初始化结果对象，如果为null则创建新对象
     * @return 房间初始化结果
     */
    public static KuaishouRoomInitResult roomInitSetCookie(Object roomId, String cookie, String kww, KuaishouRoomInitResult roomInitResult) {
        // KuaishouUserInfoResponse kuaishouUserInfoResponse = userInfo(cookie, kww);

        // String kuaishouUserId = kuaishouUserInfoResponse.getOwnerInfo().getId();

//        @Cleanup
//        HttpResponse response = createGetRequest("https://live.kuaishou.com/u/" + kuaishouUserId, cookie).execute();
        @Cleanup
        HttpResponse response = createGetRequest("https://live.kuaishou.com/u/" + roomId, cookie)
                .execute();

        if (StrUtil.isBlank(cookie)) {
            cookie = OrLiveChatCookieUtil.toString(response.getCookies());
        }

        String body = response.body();

        JsonNode livedetailJsonNode = null;
        String liveRoomDetailJsonString = ReUtil.getGroup1(PATTERN_LIVE_ROOM_DETAIL, body);
        liveRoomDetailJsonString = liveRoomDetailJsonString.replace("undefined", "null");
        try {
            livedetailJsonNode = OrJacksonUtil.getInstance().readTree(liveRoomDetailJsonString);
        } catch (Exception e) {
            throwExceptionWithTip(ExceptionUtil.getSimpleMessage(e));
        }

        String token = null;
        ArrayList<String> websocketUrlList = CollUtil.newArrayList();
        String liveStreamId = null;
        if (livedetailJsonNode.has("liveStream") && livedetailJsonNode.get("liveStream").has("id")) {
            liveStreamId = livedetailJsonNode.get("liveStream").get("id").asText();
//            JsonNode websocketinfo = websocketinfo(kuaishouUserId, liveStreamId, cookie, kww);
            JsonNode websocketinfo = websocketinfo(roomId, liveStreamId, cookie, kww);
            if (websocketinfo.has("token")) {
                token = websocketinfo.get("token").asText();
            }

            ArrayNode websocketUrls = websocketinfo.withArray("websocketUrls");
            for (JsonNode websocketUrl : websocketUrls) {
                websocketUrlList.add(websocketUrl.asText());
            }
        }

        roomInitResult = Optional.ofNullable(roomInitResult).orElseGet(() -> KuaishouRoomInitResult.builder().build());
        roomInitResult.setToken(token);
        roomInitResult.setWebsocketUrls(websocketUrlList);
        roomInitResult.setLiveStreamId(liveStreamId);
        roomInitResult.setLivedetailJsonNode(livedetailJsonNode);
        // roomInitResult.setKuaishouUserInfo(kuaishouUserInfoResponse);
        return roomInitResult;
    }

    /**
     * 使用Cookie初始化直播间信息（简化版本）
     * 
     * @param roomId 房间ID
     * @param cookie Cookie信息
     * @param kww Kww参数
     * @return 房间初始化结果
     */
    public static KuaishouRoomInitResult roomInitSetCookie(Object roomId, String cookie, String kww) {
        return roomInitSetCookie(roomId, cookie, kww, null);
    }


    /**
     * 根据获取类型初始化直播间信息
     * 
     * @param roomId 房间ID
     * @param roomInfoGetType 房间信息获取类型枚举
     * @param cookie Cookie信息
     * @param kww Kww参数
     * @param roomInitResult 可选的房间初始化结果对象
     * @return 房间初始化结果
     */
    public static KuaishouRoomInitResult roomInit(Object roomId, RoomInfoGetTypeEnum roomInfoGetType, String cookie, String kww, KuaishouRoomInitResult roomInitResult) {
        switch (roomInfoGetType) {
            case COOKIE: {
                return roomInitSetCookie(roomId, cookie, kww, roomInitResult);
            }
            case NOT_COOKIE: {
                return roomInitGet(roomId, roomInitResult);
            }
            default: {
                throwExceptionWithTip("错误获取类型");
                return null;
            }
        }
    }


    /**
     * 根据获取类型初始化直播间信息（简化版本）
     * 
     * @param roomId 房间ID
     * @param roomInfoGetType 房间信息获取类型枚举
     * @param cookie Cookie信息
     * @param kww Kww参数
     * @return 房间初始化结果
     */
    public static KuaishouRoomInitResult roomInit(Object roomId, RoomInfoGetTypeEnum roomInfoGetType, String cookie, String kww) {
        return roomInit(roomId, roomInfoGetType, cookie, kww, null);
    }

    // region KuaishouRoomInitResult NOT_COOKIE
    
    /**
     * 不使用Cookie获取房间初始化信息
     * 
     * @param roomId 房间ID
     * @param roomInitResult 可选的房间初始化结果对象
     * @return 房间初始化结果
     */
    public static KuaishouRoomInitResult roomInitGet(Object roomId, KuaishouRoomInitResult roomInitResult) {
        @Cleanup
        HttpResponse response = createGetRequest("https://live.kuaishou.com/live_api/liveroom/livedetail?principalId=" + roomId, StrUtil.EMPTY)
                .execute();

        JsonNode livedetailJsonNode = responseInterceptor(response.body());
        JsonNode websocketInfoNode = livedetailJsonNode.get("websocketInfo");
        JsonNode liveStreamJsonNode = livedetailJsonNode.get("liveStream");

        String liveStreamId = OrJacksonUtil.getTextOrDefault(liveStreamJsonNode, "id", StrUtil.EMPTY);
        String token = OrJacksonUtil.getTextOrDefault(websocketInfoNode, "token", StrUtil.EMPTY);

        List<String> websocketUrlList = null;
        if (websocketInfoNode.has("webSocketAddresses")) {
            JsonNode webSocketAddressesNode = websocketInfoNode.get("webSocketAddresses");
            websocketUrlList = new ArrayList<>(webSocketAddressesNode.size());
            for (JsonNode tempJsonNode : webSocketAddressesNode) {
                websocketUrlList.add(tempJsonNode.asText());
            }
        }

        roomInitResult = Optional.ofNullable(roomInitResult).orElseGet(() -> KuaishouRoomInitResult.builder().build());
        roomInitResult.setToken(token);
        roomInitResult.setWebsocketUrls(websocketUrlList);
        roomInitResult.setLiveStreamId(liveStreamId);
        roomInitResult.setLivedetailJsonNode(livedetailJsonNode);
        return roomInitResult;
    }


    /**
     * 不使用Cookie获取房间初始化信息（简化版本）
     * 
     * @param roomId 房间ID
     * @return 房间初始化结果
     */
    public static KuaishouRoomInitResult roomInitGet(Object roomId) {
        return roomInitGet(roomId, null);
    }



    /**
     * 默认方式初始化直播间信息（不使用Cookie）
     * 
     * @param roomId 房间ID
     * @return 房间初始化结果
     */
    public static KuaishouRoomInitResult roomInit(Object roomId) {
        return roomInit(roomId, RoomInfoGetTypeEnum.NOT_COOKIE, null, null);
    }


    /**
     * 默认方式初始化直播间信息（不使用Cookie），可传入现有结果对象
     * 
     * @param roomId 房间ID
     * @param roomInitResult 房间初始化结果对象
     * @return 房间初始化结果
     */
    public static KuaishouRoomInitResult roomInit(Object roomId, KuaishouRoomInitResult roomInitResult) {
        return roomInit(roomId, RoomInfoGetTypeEnum.NOT_COOKIE, null, null, roomInitResult);
    }
    // endregion


    /**
     * 获取WebSocket连接信息
     * 
     * @param roomId 房间ID
     * @param liveStreamId 直播流ID
     * @param cookie Cookie信息
     * @param kww Kww参数
     * @return WebSocket连接信息的JSON响应
     */
    public static JsonNode websocketinfo(Object roomId, String liveStreamId, String cookie, String kww) {
        if (StrUtil.isBlank(liveStreamId)) {
            throwExceptionWithTip("主播未开播，liveStreamId为空");
        }
        @Cleanup
        HttpResponse response = createGetRequest("https://live.kuaishou.com/live_api/liveroom/websocketinfo?caver=2&liveStreamId=" + liveStreamId, cookie)
                .header(Header.REFERER, "https://live.kuaishou.com/u/" + roomId)
                .header("Kww", kww)
                .execute();
        return responseInterceptor(response.body());
    }




    /**
     * 获取所有礼物信息
     * 
     * @return 礼物ID到礼物信息的映射
     */
    public static Map<String, GiftInfo> allgifts() {
        Map<String, GiftInfo> map = new HashMap<>();
        @Cleanup
        HttpResponse response = createGetRequest("https://live.kuaishou.com/live_api/emoji/allgifts", null).execute();
        JsonNode jsonNode = responseInterceptor(response.body());
        jsonNode.fields().forEachRemaining(stringJsonNodeEntry -> map.put(stringJsonNodeEntry.getKey(), OrJacksonUtil.getInstance().convertValue(stringJsonNodeEntry.getValue(), GiftInfo.class)));
        return map;
    }

    /**
     * 根据礼物ID获取礼物信息
     *
     * @param id 礼物ID
     * @return 礼物信息
     */
    public static GiftInfo getGiftInfoById(String id) {
        if (!RESULT_CACHE.containsKey(KEY_RESULT_CACHE_GIFT_ITEMS)) {
            RESULT_CACHE.put(KEY_RESULT_CACHE_GIFT_ITEMS, allgifts());
        }
        return RESULT_CACHE.get(KEY_RESULT_CACHE_GIFT_ITEMS).get(id);
    }





    /**
     * 发送评论
     * 
     * @param cookie Cookie信息
     * @param kww Kww参数
     * @param roomId 房间ID
     * @param request 发送评论请求对象
     * @return 发送结果的JSON响应
     */
    @SneakyThrows
    public static JsonNode sendComment(String cookie, String kww, Object roomId, SendCommentRequest request) {
        @Cleanup
        HttpResponse response = createPostRequest("https://live.kuaishou.com/live_api/liveroom/sendComment", cookie)
                .body(OrJacksonUtil.getInstance().writeValueAsString(request), ContentType.JSON.getValue())
                .header(Header.ORIGIN, "https://live.kuaishou.com")
                .header(Header.REFERER, "https://live.kuaishou.com/u/" + roomId)
                .header("Kww", kww)
                .execute();
        return responseInterceptor(response.body());
    }





    /**
     * 点赞
     * 
     * @param cookie Cookie信息
     * @param kww Kww参数
     * @param roomId 房间ID
     * @param liveStreamId 直播流ID
     * @param count 点赞次数
     * @return 点赞结果的JSON响应
     */
    @SneakyThrows
    public static JsonNode clickLike(String cookie, String kww, Object roomId, String liveStreamId, int count) {
        @Cleanup
        HttpResponse response = createPostRequest("https://live.kuaishou.com/live_api/liveroom/like", cookie)
                .body(OrJacksonUtil.getInstance().createObjectNode()
                        .put("liveStreamId", liveStreamId)
                        .put("count", count)
                        .toString(), ContentType.JSON.getValue()
                )
                .header(Header.ORIGIN, "https://live.kuaishou.com")
                .header(Header.REFERER, "https://live.kuaishou.com/u/" + roomId)
                .header("Kww", kww)
                .execute();
        return responseInterceptor(response.body());
    }


    /**
     * 创建HTTP请求
     * 
     * @param method HTTP方法
     * @param url 请求URL
     * @param cookie Cookie信息
     * @return HTTP请求对象
     */
    public static HttpRequest createRequest(Method method, String url, String cookie) {
        return OrLiveChatHttpUtil.createRequest(method, url)
                .cookie(cookie)
                .header(Header.HOST, URLUtil.url(url).getHost());
    }


    /**
     * 创建GET请求
     * 
     * @param url 请求URL
     * @param cookie Cookie信息
     * @return GET请求对象
     */
    public static HttpRequest createGetRequest(String url, String cookie) {
        return createRequest(Method.GET, url, cookie);
    }


    /**
     * 创建POST请求
     * 
     * @param url 请求URL
     * @param cookie Cookie信息
     * @return POST请求对象
     */
    public static HttpRequest createPostRequest(String url, String cookie) {
        return createRequest(Method.POST, url, cookie);
    }

    private static JsonNode responseInterceptor(String responseString) {
        log.info("responseString: {}", responseString);
        try {
            JsonNode jsonNode = OrJacksonUtil.getInstance().readTree(responseString);
            JsonNode data = jsonNode.required("data");
            if (data.has("result")) {
                int result = data.get("result").asInt();
                List<Integer> notLivingCode = CollUtil.newArrayList(671, 677);
                if (result != 1 && !CollUtil.contains(notLivingCode, result)) {
                    String message = "";
                    switch (result) {
                        case 2: {
                            message = "请求过快，请稍后重试";
                            break;
                        }
                        case 400002: {
                            message = "需要进行验证";
                            break;
                        }
                        default: {
                            message = "";
                        }
                    }
                    throwExceptionWithTip("接口访问失败：" + message + "，返回结果：" + jsonNode);
                }
            }
            return data;
        } catch (JsonProcessingException e) {
            throw new BaseException(e);
        }
    }

    private static void throwExceptionWithTip(String message) {
        throw new BaseException("『可能已触发滑块验证，建议配置Cookie和Kww后或打开浏览器进行滑块验证后重试』" + message);
    }

    /**
     * 计算快手直播间收到礼物的个数
     *
     * @param msg KuaishouGiftMsg
     * @return 礼物个数
     */
    public static int calculateGiftCount(KuaishouGiftMsg msg) {
        if (msg == null || msg.getMsg() == null) {
            return 0;
        }

        int giftCount;
        WebGiftFeedOuterClass.WebGiftFeed webGiftFeed = msg.getMsg();
        String mergeKey = webGiftFeed.getMergeKey();
        if (WEB_GIFT_FEED_CACHE.containsKey(mergeKey)) {
            WebGiftFeedOuterClass.WebGiftFeed webGiftFeedByMergeKey = WEB_GIFT_FEED_CACHE.get(mergeKey);
            int comboCountByMergeKey = webGiftFeedByMergeKey.getComboCount();
            giftCount = webGiftFeed.getComboCount() - comboCountByMergeKey;
        } else {
            int batchSize = webGiftFeed.getBatchSize();
            int comboCount = webGiftFeed.getComboCount();
            if (comboCount == 1) {
                giftCount = batchSize;
            } else {
                giftCount = comboCount;
            }
        }
        WEB_GIFT_FEED_CACHE.put(mergeKey, webGiftFeed);

        msg.setCalculatedGiftCount(giftCount);
        return giftCount;
    }

    /**
     * 获取粉丝牌名称
     */
    public static String getBadgeName(LiveAudienceStateOuterClass.LiveAudienceState liveAudienceState) {
        String badgeName = null;
        try {
            for (LiveAudienceStateOuterClass.LiveAudienceState.LiveAudienceState_11 liveAudienceState11 : liveAudienceState.getLiveAudienceState11List()) {
                String badgeIcon = liveAudienceState11.getLiveAudienceState111().getBadgeIcon();
                if (StrUtil.startWithIgnoreCase(badgeIcon, "fans")) {
                    badgeName = liveAudienceState11.getLiveAudienceState111().getBadgeName();
                    break;
                }
            }
        } catch (Exception e) {
            // ignore
        }
        return badgeName;
    }

    /**
     * 获取粉丝牌等级
     */
    public static byte getBadgeLevel(LiveAudienceStateOuterClass.LiveAudienceState liveAudienceState) {
        byte badgeLevel = 0;
        try {
            badgeLevel = (byte) liveAudienceState.getLiveFansGroupState().getIntimacyLevel();
        } catch (Exception e) {
            // ignore
        }
        return badgeLevel;
    }


    /**
     * 获取用户信息
     * 
     * @param cookie Cookie信息
     * @param kww Kww参数
     * @return 用户信息响应对象
     */
    @SneakyThrows
    public static KuaishouUserInfoResponse userInfo(String cookie, String kww) {
        @Cleanup
        HttpResponse response = createPostRequest("https://live.kuaishou.com/live_api/baseuser/userinfo", cookie)
                .body("{}", ContentType.JSON.getValue()) // 发送空JSON对象
                .header("Accept", "application/json, text/plain, */*")
                .header("Accept-Encoding", "gzip, deflate, br, zstd")
                .header("Accept-Language", "en,zh-CN;q=0.9,zh;q=0.8,en-GB;q=0.7,en-US;q=0.6")
                .header("baggage", "sentry-environment=prod,sentry-release=80c170a")
                .header("Connection", "keep-alive")
                .header("Content-Type", "application/json")
                .header(Header.ORIGIN, "https://live.kuaishou.com")
                .header(Header.REFERER, "https://live.kuaishou.com/")
                .header("kww", kww)
                .header("sec-ch-ua", "\"Chromium\";v=\"146\", \"Not-A.Brand\";v=\"24\", \"Microsoft Edge\";v=\"146\"")
                .header("sec-ch-ua-mobile", "?0")
                .header("sec-ch-ua-platform", "\"Windows\"")
                .header("Sec-Fetch-Dest", "empty")
                .header("Sec-Fetch-Mode", "cors")
                .header("Sec-Fetch-Site", "same-origin")
                .header("sentry-trace", "1b50c275963146e48c1db279e4044cb4-ba9c580d12c7cadd-0")
                .header(Header.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/146.0.0.0 Safari/537.36 Edg/146.0.0.0")
                .execute();
        JsonNode jsonNode = responseInterceptor(response.body());
        String json = jsonNode.toString();
        // 使用Gson进行JSON转换
        com.google.gson.Gson gson = new com.google.gson.Gson();
        return gson.fromJson(json, KuaishouUserInfoResponse.class);
    }

    /**
     * 获取兴趣面具列表
     *
     * @param cookie Cookie信息
     * @param kww Kww参数
     * @return 兴趣面具列表的JSON响应
     */
    @SneakyThrows
    public static JsonNode interestMaskList(String cookie, String kww) {
        @Cleanup
        HttpResponse response = createGetRequest("https://live.kuaishou.com/live_api/interestMask/list", cookie)
                .header("Accept", "application/json, text/plain, */*")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Accept-Language", "en,zh-CN;q=0.9,zh;q=0.8,en-GB;q=0.7,en-US;q=0.6")
                .header("baggage", "sentry-environment=prod,sentry-release=80c170a")
                .header("Connection", "keep-alive")
                .header(Header.ORIGIN, "https://live.kuaishou.com")
                .header(Header.REFERER, "https://live.kuaishou.com/")
                .header("kww", kww)
                .header("sec-ch-ua", "\"Chromium\";v=\"146\", \"Not-A.Brand\";v=\"24\", \"Microsoft Edge\";v=\"146\"")
                .header("sec-ch-ua-mobile", "?0")
                .header("sec-ch-ua-platform", "\"Windows\"")
                .header("Sec-Fetch-Dest", "empty")
                .header("Sec-Fetch-Mode", "cors")
                .header("Sec-Fetch-Site", "same-origin")
                .header("sentry-trace", "1b50c275963146e48c1db279e4044cb4-ba42c04eb4afeacf-0")
                .header(Header.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/146.0.0.0 Safari/537.36 Edg/146.0.0.0")
                .execute();
        return responseInterceptor(response.body());
    }

    /**
     * 获取兴趣面具列表并转换为Java对象
     *
     * @param cookie Cookie信息
     * @param kww Kww参数
     * @return 兴趣面具列表的Java对象响应数组
     */
    @SneakyThrows
    public static List<InterestMaskListResponse> interestMaskListResponse(String cookie, String kww) {
        JsonNode jsonNode = interestMaskList(cookie, kww);
        String json = jsonNode.toString();
        // 使用Gson进行JSON转换，支持数组格式
        com.google.gson.Gson gson = new com.google.gson.Gson();
        java.lang.reflect.Type listType = new com.google.gson.reflect.TypeToken<List<InterestMaskListResponse>>(){}.getType();
        return gson.fromJson(json, listType);

    }

    /**
     * 发送评论请求对象
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SendCommentRequest {
        /** 直播流ID */
        private String liveStreamId;
        /** 评论内容 */
        private String content;
        /** 评论颜色 */
        private String color;
    }

    /**
     * 礼物信息对象
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GiftInfo {
        /** 礼物名称 */
        private String giftName;
        /** 礼物图片URL */
        private String giftUrl;
    }







}