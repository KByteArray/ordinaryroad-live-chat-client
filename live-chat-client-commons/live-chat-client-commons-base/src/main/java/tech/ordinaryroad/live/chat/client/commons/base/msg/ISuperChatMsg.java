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
 * 醒目留言（Super Chat / SC）通用接口。
 * 是一种特殊的长驻弹幕，通常伴随着金额捐赠，会在直播界面上停留一定时间。
 * 继承自 IDanmuMsg。
 *
 * @author mjz
 * @date 2023/9/22
 */
public interface ISuperChatMsg extends IDanmuMsg {

    /**
     * 获取醒目留言在屏幕上展示的持续时长，单位：秒（s）
     *
     * @return 持续时间秒数
     */
    int getDuration();

    /**
     * 获取粉丝牌名称（可选）
     *
     * @return 默认返回空字符串
     */
    @Override
    default String getBadgeName() {
        return "";
    }

    /**
     * 获取粉丝牌等级（可选）
     *
     * @return 默认返回 0
     */
    @Override
    default byte getBadgeLevel() {
        return 0;
    }
}
