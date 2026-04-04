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

import tech.ordinaryroad.live.chat.client.commons.base.constant.SocialActionEnum;

/**
 * 社交行为消息通用接口。
 * 包含用户执行的具体社交动作（如关注、分享直播间等）。
 *
 * @author mjz
 * @date 2024/5/9
 * @since 0.7.1
 */
public interface ISocialMsg extends IMsg {

    /**
     * 获取执行者粉丝牌名称
     *
     * @return 粉丝牌名称
     */
    String getBadgeName();

    /**
     * 获取执行者粉丝牌等级
     *
     * @return 粉丝牌等级
     */
    byte getBadgeLevel();

    /**
     * 获取社交动作触发者的 UID
     *
     * @return 用户 UID
     */
    String getUid();

    /**
     * 获取社交动作触发者的用户名
     *
     * @return 用户名
     */
    String getUsername();

    /**
     * 获取社交动作触发者的头像地址
     *
     * @return 用户头像 URL
     * @since 0.0.11
     */
    default String getUserAvatar() {
        return null;
    }

    /**
     * 获取具体的社交动作类型
     *
     * @return 社交动作枚举，例如 FOLLOW（关注）、SHARE（分享）
     */
    SocialActionEnum getSocialAction();

}
