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

package tech.ordinaryroad.live.chat.client.commons.base.msg;

/**
 * 入房消息（进入直播间）通用接口。
 * 当用户（不论是普通观众、房管还是大航海等高级用户）进入直播间时触发。
 *
 * @author mjz
 * @date 2023/12/26
 * @since 0.0.16
 */
public interface IEnterRoomMsg extends IMsg {

    /**
     * 获取进入直播间用户的粉丝牌名称
     *
     * @return 粉丝牌名称
     */
    String getBadgeName();

    /**
     * 获取进入直播间用户的粉丝牌等级
     *
     * @return 粉丝牌等级
     */
    byte getBadgeLevel();

    /**
     * 获取该用户的唯一标识 (UID)
     *
     * @return 用户 UID
     */
    String getUid();

    /**
     * 获取该用户的用户名
     *
     * @return 用户名
     */
    String getUsername();

    /**
     * 获取该用户的头像地址
     *
     * @return 头像 URL
     */
    default String getUserAvatar() {
        return null;
    }
}
