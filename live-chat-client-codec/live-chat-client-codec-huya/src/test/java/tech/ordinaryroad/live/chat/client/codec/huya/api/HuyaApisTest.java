package tech.ordinaryroad.live.chat.client.codec.huya.api;

import cn.hutool.core.lang.Assert;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import tech.ordinaryroad.live.chat.client.codec.huya.room.HuyaRoomInitResult;
import tech.ordinaryroad.live.chat.client.commons.base.exception.BaseException;
import tech.ordinaryroad.live.chat.client.commons.base.room.IRoomLiveStreamInfo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author mjz
 * @date 2023/10/1
 */
class HuyaApisTest {

    private static void checkIfEquals(HuyaRoomInitResult roomInitResult) {
        long lp = roomInitResult.getTtProfileInfo().getLp();
        JsonNode jsonNode = roomInitResult.getHyPlayerConfigStream().withArray("data").get(0);
        long uid = jsonNode.get("gameLiveInfo").get("uid").asLong();
        long liveChannel = jsonNode.get("gameLiveInfo").get("liveChannel").asLong();
        long channel = jsonNode.get("gameLiveInfo").get("channel").asLong();
        Assert.equals(lp, uid);
        Assert.equals(lp, liveChannel);
        Assert.equals(lp, channel);
    }

    @Test
    void roomInit() {
//        assertEquals(HuyaApis.roomInit(189201).getTtRoomData(), 3);
        assertThrows(BaseException.class, () -> HuyaApis.roomInit(-1));
    }

    @Test
    void testRoomTitle() {
        Assert.notBlank(HuyaApis.roomInit(189201, null).getRoomTitle());
        Assert.notBlank(HuyaApis.roomInit(333003, null).getRoomTitle());
        Assert.notBlank(HuyaApis.roomInit("bagea", null).getRoomTitle());
        Assert.notBlank(HuyaApis.roomInit("527988", null).getRoomTitle());
        Assert.notBlank(HuyaApis.roomInit(1995, null).getRoomTitle());
        Assert.notBlank(HuyaApis.roomInit(116, null).getRoomTitle());
    }

    @Test
    void testRoomLiveStatus() {
        System.out.println(HuyaApis.roomInit("bagea", null).getRoomLiveStatus());
    }

    @Test
    void testRoomLiveStreamUrls() {
        List<IRoomLiveStreamInfo> roomLiveStreamUrls = HuyaApis.roomInit("lpl", null).getRoomLiveStreamUrls();
        Assert.notEmpty(roomLiveStreamUrls);
    }

    @Test
    void testChannelId() {
        checkIfEquals(HuyaApis.roomInit(333003, null));
        checkIfEquals(HuyaApis.roomInit("bagea", null));
        checkIfEquals(HuyaApis.roomInit("527988", null));
        checkIfEquals(HuyaApis.roomInit(1995, null));
        checkIfEquals(HuyaApis.roomInit(116, null));
    }
}