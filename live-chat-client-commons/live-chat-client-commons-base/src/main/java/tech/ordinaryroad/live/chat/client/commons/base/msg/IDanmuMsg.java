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
 * 弹幕消息通用接口。
 * 提供获取发送者信息（ID、用户名、头像）、粉丝牌状态以及弹幕内容的方法。
 *
 * @author mjz
 * @date 2023/9/8
 */
public interface IDanmuMsg extends IMsg {

    /**
     * 获取粉丝牌名称
     *
     * @return 粉丝牌名称，若无则返回空字符串或 null
     */
    String getBadgeName();

    /**
     * 获取粉丝牌等级
     *
     * @return 粉丝牌等级，通常为 0-100 的数值
     */
    byte getBadgeLevel();

    /**
     * 获取弹幕发送者的唯一标识 (UID)
     *
     * @return 发送者 UID
     */
    String getUid();

    /**
     * 获取弹幕发送者的用户名
     *
     * @return 用户名
     */
    String getUsername();

    /**
     * 获取弹幕发送者的头像地址
     *
     * @return 头像 URL
     * @since 0.0.11
     */
    default String getUserAvatar() {
        return null;
    }

    /**
     * 获取弹幕的具体文本内容
     *
     * @return 弹幕内容
     */
    String getContent();

}
