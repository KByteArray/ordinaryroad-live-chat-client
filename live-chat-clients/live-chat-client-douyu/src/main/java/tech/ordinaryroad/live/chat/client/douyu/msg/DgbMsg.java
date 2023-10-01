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

package tech.ordinaryroad.live.chat.client.douyu.msg;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.ordinaryroad.live.chat.client.commons.base.msg.IGiftMsg;
import tech.ordinaryroad.live.chat.client.douyu.api.DouyuApis;
import tech.ordinaryroad.live.chat.client.douyu.constant.DouyuCmdEnum;
import tech.ordinaryroad.live.chat.client.douyu.msg.base.BaseDouyuCmdMsg;

import java.util.List;

/**
 * 收到礼物消息
 * <pre>{@code
 * {
 * 	"type": "dgb",
 * 	"nn": "用户名",
 * 	"eid": "0",
 * 	"bst": "32",
 * 	"brid": "0",
 * 	"pma": "186963537",
 * 	"bnid": "1",
 * 	"gfid": "824",
 * 	"bl": "0",
 * 	"pid": "268",
 * 	"rid": "290935",
 * 	"mss": "186963457",
 * 	"bcst": "2",
 * 	"uid": "用户id",
 * 	"receive_uid": "接受用户id",
 * 	"ic": ["avatar_v3", "201912", "d031765fbe764a49887083bcf200da0b"],
 * 	"from": "2",
 * 	"gpf": "1",
 * 	"bnl": "1",
 * 	"ce": "1",
 * 	"bnn": null,
 * 	"receive_nn": "接受用户用户名",
 * 	"level": "43",
 * 	"bcnt": "1",
 * 	"gs": "0",
 * 	"hits": "249",
 * 	"gfcnt": "249",
 * 	"ct": "0",
 * 	"pfm": "27585",
 * 	"sahf": "0",
 * 	"hc": null,
 * 	"fc": "0",
 * 	"eic": "0"
 * }
 * }</pre>
 *
 * @author mjz
 * @date 2023/9/8
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DgbMsg extends BaseDouyuCmdMsg implements IGiftMsg {

    /**
     * 用户名
     */
    private String nn;
    private String eid;
    private String bst;
    private String brid;
    private String pma;
    private String bnid;
    private long gfid;
    private String bl;
    private String pid;
    private String rid;
    private String mss;
    private String bcst;
    /**
     * 用户id
     */
    private long uid;
    /**
     * 收礼物用户id
     */
    private long receive_uid;
    private List<String> ic;
    private String from;
    private String gpf;
    private String bnl;
    private String ce;
    private String bnn;
    /**
     * 收礼物用户名
     */
    private String receive_nn;
    private String level;
    private String bcnt;
    private String gs;
    /**
     * 连击
     */
    private String hits;
    /**
     * 礼物个数
     */
    private int gfcnt;
    private String ct;
    private String pfm;
    private String sahf;
    private String hc;
    private String fc;
    private String eic;

    @Override
    public String getType() {
        return DouyuCmdEnum.dgb.name();
    }

    @Override
    public String getUsername() {
        return this.nn;
    }

    @Override
    public String getUserAvatar() {
        return DouyuApis.getSmallAvatarUrl(ic);
    }

    @Override
    public String getGiftName() {
        return "";
    }

    @Override
    public long getGiftId() {
        return this.gfid;
    }

    @Override
    public int getGiftCount() {
        return this.gfcnt;
    }

    @Override
    public int getGiftPrice() {
        return -1;
    }

    @Override
    public long getReceiveUid() {
        return this.receive_uid;
    }

    @Override
    public String getReceiveUsername() {
        return this.receive_nn;
    }
}