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
 * 房间统计信息消息通用接口。
 * 定期下发或实时通知直播间的实时互动数据，如点赞数、在线人数等。
 *
 * @author mjz
 * @date 2024/4/23
 */
public interface IRoomStatsMsg extends IMsg {

    /**
     * 获取直播间的累计点赞总数（显示数值）
     *
     * @return 累计点赞数，例如 "1.5w" 或 "15000"
     */
    default String getLikedCount() {
        return null;
    }

    /**
     * 获取当前直播间实时观看的人数（人气值或人数）
     *
     * @return 当前观看人数，例如 "100" 或 "人气 1000"
     */
    default String getWatchingCount() {
        return null;
    }

    /**
     * 获取该场直播自开播以来的累计观看总人数
     *
     * @return 累计观看人数
     */
    default String getWatchedCount() {
        return null;
    }
}
