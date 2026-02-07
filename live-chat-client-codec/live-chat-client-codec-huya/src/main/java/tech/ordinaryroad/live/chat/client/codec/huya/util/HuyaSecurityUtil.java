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

package tech.ordinaryroad.live.chat.client.codec.huya.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;

import java.util.Date;

/**
 * 虎牙安全参数生成工具类
 */
public class HuyaSecurityUtil {

    // 正式环境盐值 (AzCXiouW6aLc4AsVGKAOOLlNLawNQsuV)
    private static final String SALT_RELEASE = "AzCXiouW6aLc4AsVGKAOOLlNLawNQsuV";

    // 测试环境盐值 (c84eWpKudhlb0T2JQbMM96RJpgqQRwEq)
    private static final String SALT_DEBUG = "c84eWpKudhlb0T2JQbMM96RJpgqQRwEq";

    /**
     * 生成 sSign 签名
     * 逻辑：MD5(yyuid + iSeqNum + iFromType + iBusinessType + Salt)
     *
     * @param yyuid         用户的 yyuid
     * @param iSeqNum       序列号数量 (默认传 1)
     * @param iBusinessType 业务类型 (默认传 1)
     * @param isDebug       是否为开发调试模式
     * @return 32位小写 MD5 签名串
     */
    public static String generateSSign(String yyuid, int iSeqNum, int iBusinessType, boolean isDebug) {
        // 1. iFromType 在 JS 中固定为 5
        int iFromType = 5;

        // 2. 根据模式选择盐值
        String salt = isDebug ? SALT_DEBUG : SALT_RELEASE;

        // 3. 按照 JS 的 [r.yyuid, l.iSeqNum, l.iFromType, l.iBusinessType, t].join("") 顺序拼接

        String rawStr = yyuid +          // 用户ID
                iSeqNum +        // 序列号数量
                iFromType +      // 固定值5
                iBusinessType +  // 业务类型
                salt // 盐值
                ;

        // 4. 使用 Hutool 生成 MD5 16进制字符串 (小写)
        return DigestUtil.md5Hex(rawStr);
    }

    /**
     * 生成本地备用订单号 (当 getSequence 接口调用失败时的 Fallback 逻辑)
     * 逻辑：时间戳 + 050010 + 12位随机大写字母数字
     *
     * @return 32位序列号串
     */
    public static String generateLocalSequence() {
        // 1. 获取当前时间 yyyyMMddHHmmss
        String timePart = DateUtil.format(new Date(), "yyyyMMddHHmmss");

        // 2. JS 逻辑中硬编码的中间段
        String middlePart = "050010";

        // 3. 12位随机大写字母或数字
        String baseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String randomPart = RandomUtil.randomString(baseChars, 12);

        return timePart + middlePart + randomPart;
    }
}