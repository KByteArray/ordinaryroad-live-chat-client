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
 * 点赞消息通用接口。
 * 记录用户在直播间执行的点赞（Like/Click）行为及其次数。
 *
 * @author mjz
 * @date 2024/1/31
 * @since 0.2.0
 */
public interface ILikeMsg extends IMsg {

    /**
     * 获取点赞者的粉丝牌名称
     *
     * @return 粉丝牌名称，默认返回空字符串
     */
    default String getBadgeName(){
        return "";
    }

    /**
     * 获取点赞者的粉丝牌等级
     *
     * @return 粉丝牌等级，默认返回 0
     */
    default byte getBadgeLevel(){
        return 0;
    }

    /**
     * 获取点赞用户的 UID
     *
     * @return 用户 UID
     */
    String getUid();

    /**
     * 获取点赞用户的用户名
     *
     * @return 用户名
     */
    String getUsername();

    /**
     * 获取点赞用户的头像地址
     *
     * @return 用户头像 URL
     */
    default String getUserAvatar() {
        return null;
    }

    /**
     * 获取本次点赞的数量（部分平台支持连点，此字段表示连点次数）
     *
     * @return 点赞次数，默认为 1
     */
    default int getClickCount() {
        return 1;
    }
}
