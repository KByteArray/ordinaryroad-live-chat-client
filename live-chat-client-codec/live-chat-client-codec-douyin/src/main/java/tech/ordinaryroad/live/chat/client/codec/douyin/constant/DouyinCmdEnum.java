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

package tech.ordinaryroad.live.chat.client.codec.douyin.constant;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 抖音直播间 WebSocket 消息指令（CMD）枚举。
 * 对应抖音 Webcast 协议中的不同消息类型。
 *
 * @author mjz
 * @date 2024/1/2
 */
@Getter
@RequiredArgsConstructor
public enum DouyinCmdEnum {

    /**
     * 弹幕消息
     * 包含文本内容、发送者信息等。
     */
    WebcastChatMessage,

    /**
     * 礼物消息
     * 包含礼物 ID、名称、连击数、赠送者及接收者信息。
     */
    WebcastGiftMessage,

    /**
     * 点赞消息
     * 包含用户点赞行为以及点赞总数的实时更新。
     */
    WebcastLikeMessage,

    /**
     * 成员消息（入房/等级提升等）
     * 主要用于监听用户进入直播间的事件。
     */
    WebcastMemberMessage,

    /**
     * 房间统计消息
     * 包含直播间的实时在线人数、累计观看人数、累计点赞数等。
     */
    WebcastRoomStatsMessage,

    /**
     * 社交行为消息
     * 对应 Action=1 为关注，Action=3 为分享直播间。
     * {@link tech.ordinaryroad.live.chat.client.codec.douyin.protobuf.DouyinWebcastSocialMessageMsgOuterClass.DouyinWebcastSocialMessageMsg#getAction()}
     */
    WebcastSocialMessage,

    /**
     * 房间用户序列消息
     * 通常包含当前房间内在线用户的简略列表或排行。
     */
    WebcastRoomUserSeqMessage,

    /**
     * 粉丝团消息
     * 包含用户加入粉丝团、粉丝团等级升级等相关事件。
     */
    WebcastFansclubMessage,

    /**
     * 房间控制消息（状态变化）
     * 包含直播间状态变更，如直播结束、暂停、下播等指令。
     */
    WebcastControlMessage,
    ;

    /**
     * 根据指令名称字符串获取对应的枚举对象。
     *
     * @param name 指令名称字符串
     * @return 对应的 DouyinCmdEnum 枚举，若未找到则返回 null
     */
    public static DouyinCmdEnum getByName(String name) {
        if (StrUtil.isBlank(name)) {
            return null;
        }

        for (DouyinCmdEnum value : values()) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        return null;
    }
}
