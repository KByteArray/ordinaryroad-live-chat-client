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
 * 礼物消息通用接口。
 * 包含礼物名称、ID、价格、数量以及赠送者和接收者的详细信息。
 *
 * @author mjz
 * @date 2023/9/8
 */
public interface IGiftMsg extends IMsg {

    /**
     * 获取粉丝牌名称
     *
     * @return 粉丝牌名称，默认返回空字符串
     */
    default String getBadgeName() {
        return "";
    }

    /**
     * 获取粉丝牌等级
     *
     * @return 粉丝牌等级，默认返回 0
     */
    default byte getBadgeLevel() {
        return 0;
    }

    /**
     * 获取礼物赠送者的 UID
     *
     * @return 赠送者 UID
     */
    String getUid();

    /**
     * 获取礼物赠送者的用户名
     *
     * @return 赠送者用户名
     */
    String getUsername();

    /**
     * 获取礼物赠送者的头像地址
     *
     * @return 头像 URL
     * @since 0.0.11
     */
    default String getUserAvatar() {
        return null;
    }

    /**
     * 获取礼物的名称（如：小电视、大火箭等）
     *
     * @return 礼物名称
     */
    String getGiftName();

    /**
     * 获取礼物的图标或图片地址
     *
     * @return 礼物图片 URL
     */
    String getGiftImg();

    /**
     * 获取礼物的唯一标识 ID
     *
     * @return 礼物 ID
     */
    String getGiftId();

    /**
     * 获取本次赠送的礼物总数量
     *
     * @return 礼物数量，注意某些平台可能存在组合包导致数量为负值或异常
     */
    int getGiftCount();

    /**
     * 获取礼物的单价
     *
     * @return 单个礼物的价格值
     */
    int getGiftPrice();

    /**
     * 获取礼物接收者的 UID（通常是主播）
     *
     * @return 接收者 UID
     */
    String getReceiveUid();

    /**
     * 获取礼物接收者的用户名
     *
     * @return 接收者用户名
     */
    String getReceiveUsername();
}
