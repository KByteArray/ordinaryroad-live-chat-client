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

package tech.ordinaryroad.live.chat.client.codec.huya.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author mjz
 * @date 2023/10/2
 */
@Getter
@RequiredArgsConstructor
public enum HuyaStreamLineTypeEnum {

    STREAM_LINE_OLD_YY(0),
    STREAM_LINE_WS(1),
    STREAM_LINE_NEW_YY(2),
    STREAM_LINE_AL(3),
    STREAM_LINE_HUYA(4),
    STREAM_LINE_TX(5),
    STREAM_LINE_CDN(8),
    STREAM_LINE_HW(6),
    STREAM_LINE_BD(7),
    STREAM_LINE_GG(9),
    STREAM_LINE_CF(10),
    STREAM_LINE_QUICK_HUYA(99),
    ;

    private final int code;

    public static HuyaStreamLineTypeEnum getByCode(int code) {
        for (HuyaStreamLineTypeEnum value : values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }
}
