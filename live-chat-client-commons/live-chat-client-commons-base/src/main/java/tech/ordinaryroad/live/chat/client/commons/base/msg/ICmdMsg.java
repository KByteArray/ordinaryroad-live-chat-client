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
 * 命令类消息接口，用于处理带有具体指令名称（CMD）的消息。
 *
 * @param <CmdEnum> 各平台自定义的命令枚举类型
 * @author mjz
 * @date 2023/10/2
 */
public interface ICmdMsg<CmdEnum extends Enum<CmdEnum>> extends IMsg {

    /**
     * 获取命令名称字符串（原始值）
     *
     * @return 命令字符串
     */
    String getCmd();

    /**
     * 设置命令名称字符串
     *
     * @param cmd 命令字符串
     */
    void setCmd(String cmd);

    /**
     * 获取解析后的命令枚举对象
     *
     * @return 对应的命令枚举
     */
    CmdEnum getCmdEnum();
}
