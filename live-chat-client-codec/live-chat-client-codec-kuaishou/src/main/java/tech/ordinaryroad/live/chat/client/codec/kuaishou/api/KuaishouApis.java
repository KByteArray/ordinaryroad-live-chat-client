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
import tech.ordinaryroad.live.chat.client.codec.kuaishou.constant.RoomInfoGetTypeEnum;
import tech.ordinaryroad.live.chat.client.codec.kuaishou.msg.KuaishouGiftMsg;
import tech.ordinaryroad.live.chat.client.codec.kuaishou.protobuf.LiveAudienceStateOuterClass;
import tech.ordinaryroad.live.chat.client.codec.kuaishou.protobuf.WebGiftFeedOuterClass;
import tech.ordinaryroad.live.chat.client.codec.kuaishou.room.KuaishouRoomInitResult;
import tech.ordinaryroad.live.chat.client.commons.base.exception.BaseException;
import tech.ordinaryroad.live.chat.client.commons.util.OrJacksonUtil;
import tech.ordinaryroad.live.chat.client.commons.util.OrLiveChatCookieUtil;
import tech.ordinaryroad.live.chat.client.commons.util.OrLiveChatHttpUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author mjz
 * @date 2024/1/5
 */
public class KuaishouApis {

    /**
     * 接口返回结果缓存
     * {@link #KEY_RESULT_CACHE_GIFT_ITEMS}：所有礼物信息
     */
    public static final TimedCache<String, Map<String, GiftInfo>> RESULT_CACHE = new TimedCache<>(TimeUnit.DAYS.toMillis(1));
    public static final String KEY_RESULT_CACHE_GIFT_ITEMS = "GIFT_ITEMS";
    public static final String PATTERN_LIVE_ROOM_DETAIL = "\"playList\":\\s*\\[([\\s\\S]*?)\\](?=,\\s*\"loading\"|$)";
    /**
     * 礼物连击缓存
     */
    private static final TimedCache<String, WebGiftFeedOuterClass.WebGiftFeed> WEB_GIFT_FEED_CACHE = new TimedCache<>(300 * 1000L, new ConcurrentHashMap<>());

    public static KuaishouRoomInitResult roomInitSetCookie(Object roomId, String cookie, KuaishouRoomInitResult roomInitResult) {
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
            JsonNode websocketinfo = websocketinfo(roomId, liveStreamId, cookie);
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
        return roomInitResult;
    }

    public static KuaishouRoomInitResult roomInitSetCookie(Object roomId, String cookie) {
        return roomInitSetCookie(roomId, cookie, null);
    }

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

    public static KuaishouRoomInitResult roomInitGet(Object roomId) {
        return roomInitGet(roomId, null);
    }

    public static KuaishouRoomInitResult roomInit(Object roomId, RoomInfoGetTypeEnum roomInfoGetType, String cookie, KuaishouRoomInitResult roomInitResult) {
        switch (roomInfoGetType) {
            case COOKIE: {
                return roomInitSetCookie(roomId, cookie, roomInitResult);
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

    public static KuaishouRoomInitResult roomInit(Object roomId, RoomInfoGetTypeEnum roomInfoGetType, String cookie) {
        return roomInit(roomId, roomInfoGetType, cookie, null);
    }

    public static KuaishouRoomInitResult roomInit(Object roomId) {
        return roomInit(roomId, RoomInfoGetTypeEnum.NOT_COOKIE, null);
    }

    public static KuaishouRoomInitResult roomInit(Object roomId, KuaishouRoomInitResult roomInitResult) {
        return roomInit(roomId, RoomInfoGetTypeEnum.NOT_COOKIE, null, roomInitResult);
    }

    public static JsonNode websocketinfo(Object roomId, String liveStreamId, String cookie) {
        if (StrUtil.isBlank(liveStreamId)) {
            throwExceptionWithTip("主播未开播，liveStreamId为空");
        }
        @Cleanup
        HttpResponse response = createGetRequest("https://live.kuaishou.com/live_api/liveroom/websocketinfo?liveStreamId=" + liveStreamId, cookie)
                .header(Header.REFERER, "https://live.kuaishou.com/u/" + roomId)
                .execute();
        return responseInterceptor(response.body());
    }

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

    @SneakyThrows
    public static JsonNode sendComment(String cookie, Object roomId, SendCommentRequest request) {
        @Cleanup
        HttpResponse response = createPostRequest("https://live.kuaishou.com/live_api/liveroom/sendComment", cookie)
                .body(OrJacksonUtil.getInstance().writeValueAsString(request), ContentType.JSON.getValue())
                .header(Header.REFERER, "https://live.kuaishou.com/u/" + roomId)
                .execute();
        return responseInterceptor(response.body());
    }

    @SneakyThrows
    public static JsonNode clickLike(String cookie, Object roomId, String liveStreamId, int count) {
        @Cleanup
        HttpResponse response = createPostRequest("https://live.kuaishou.com/live_api/liveroom/like", cookie)
                .body(OrJacksonUtil.getInstance().createObjectNode()
                        .put("liveStreamId", liveStreamId)
                        .put("count", count)
                        .toString(), ContentType.JSON.getValue()
                )
                .header(Header.ORIGIN, "https://live.kuaishou.com")
                .header(Header.REFERER, "https://live.kuaishou.com/u/" + roomId)
                .execute();
        return responseInterceptor(response.body());
    }

    public static HttpRequest createRequest(Method method, String url, String cookie) {
        return OrLiveChatHttpUtil.createRequest(method, url)
                .cookie(cookie)
                .header(Header.HOST, URLUtil.url(url).getHost());
    }

    public static HttpRequest createGetRequest(String url, String cookie) {
        return createRequest(Method.GET, url, cookie);
    }

    public static HttpRequest createPostRequest(String url, String cookie) {
        return createRequest(Method.POST, url, cookie);
    }

    private static JsonNode responseInterceptor(String responseString) {
        List<Integer> notLivingCode = CollUtil.newArrayList(671, 677);
        try {
            JsonNode jsonNode = OrJacksonUtil.getInstance().readTree(responseString);
            JsonNode data = jsonNode.required("data");
            if (data.has("result")) {
                int result = data.get("result").asInt();
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
        throw new BaseException("『可能已触发滑块验证，建议配置Cookie或打开浏览器进行滑块验证后重试』" + message);
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

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SendCommentRequest {
        private String liveStreamId;
        private String content;
        private String color;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GiftInfo {
        private String giftName;
        private String giftUrl;
    }
}
