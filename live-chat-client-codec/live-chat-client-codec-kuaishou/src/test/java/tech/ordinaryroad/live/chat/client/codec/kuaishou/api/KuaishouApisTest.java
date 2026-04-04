package tech.ordinaryroad.live.chat.client.codec.kuaishou.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import tech.ordinaryroad.live.chat.client.codec.kuaishou.constant.RoomInfoGetTypeEnum;
import tech.ordinaryroad.live.chat.client.codec.kuaishou.room.KuaishouRoomInitResult;
import tech.ordinaryroad.live.chat.client.commons.base.constant.RoomLiveStatusEnum;
import tech.ordinaryroad.live.chat.client.commons.base.room.IRoomLiveStreamInfo;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author mjz
 * @date 2024/1/6
 */
@Slf4j
class KuaishouApisTest {

    @Test
    void allgifts() {
        Map<String, KuaishouApis.GiftInfo> allgifts = KuaishouApis.allgifts();
        assertNotEquals(0, allgifts.size());
    }

    @Test
    void getGiftInfoById() {
        KuaishouApis.GiftInfo giftInfoById = KuaishouApis.getGiftInfoById("1");
        assertEquals("荧光棒", giftInfoById.getGiftName());

    }

    @Test
    void sendComment() {
        String roomId = "KPL704668133";
        KuaishouRoomInitResult roomInitResult = KuaishouApis.roomInit(roomId);

        System.out.println(KuaishouApis.sendComment(System.getenv("cookie"), System.getenv("kww"),
                roomId,
                KuaishouApis.SendCommentRequest
                        .builder()
                        .liveStreamId(roomInitResult.getLiveStreamId())
                        .content("66666a")
                        .build()
        ));
    }

    private String cookie =
            "did=web_25b7e84f20be4f9a806d97b2b89a8535; didv=1753882864000; userId=327228032; _tea_utm_cache_10000007=undefined; kwpsecproductname=PCLive; kwfv1=PAPF+eYfG008S+9P0U+/rlw/PIP0LI+0GUG/cE8/DEPBzjGA4D+nbDPfrh+0qAP/W7G/c78BzD8e+D8BHMGAzD+AL7G0D780chP/H9P/c7+AbD+nGE+e+f+nQj+fc7P/GF+eD98emDG/WlGfcIP9c7+04SwemfPep0we8Sw/Pl80Z=; kwssectoken=1JSNDbtTAxuE0r/VGa14f2BNP6cLieamB2z3A5Qv5BsCGewpCitnH7T34/dHDuodw08vceaOQSTRsH34c/aPCQ==; kwscode=a715a587d1b6832f2760590584b05c42663b5eae5c68614a6b055f2e0f6069b7";

    @Test
    void testRoomTitle() {
        KuaishouRoomInitResult init = KuaishouApis.roomInit("3xs856itqc5ivgm", RoomInfoGetTypeEnum.COOKIE, cookie, null);
        System.out.println(init.getRoomTitle());
//        KuaishouApis.roomInit("3x6pb6bcmjrarvs", RoomInfoGetTypeEnum.NOT_COOKIE, null, null).getRoomTitle();
//        KuaishouApis.roomInit("t8888888", RoomInfoGetTypeEnum.NOT_COOKIE, null, null).getRoomTitle();
//        KuaishouApis.roomInit("3x3gjx4jfca4zfs", RoomInfoGetTypeEnum.NOT_COOKIE, null, null).getRoomTitle();
//        KuaishouApis.roomInit("3xkz5pb2kx3q4u6", RoomInfoGetTypeEnum.NOT_COOKIE, null, null).getRoomTitle();
//        KuaishouApis.roomInit("kslibai66", RoomInfoGetTypeEnum.NOT_COOKIE, null, null).getRoomTitle();
    }

    @Test
    void testRoomLiveStatus() {
        assertEquals(RoomLiveStatusEnum.LIVING, KuaishouApis.roomInit("KPL704668133", RoomInfoGetTypeEnum.NOT_COOKIE, null, null).getRoomLiveStatus());
        assertEquals(RoomLiveStatusEnum.STOPPED, KuaishouApis.roomInit("t8888888", RoomInfoGetTypeEnum.NOT_COOKIE, null, null).getRoomLiveStatus());
    }

    @Test
    void testRoomLiveStreamUrls() {
        KuaishouRoomInitResult roomInitResult = KuaishouApis.roomInit("3xs7zihgas9kfw9", RoomInfoGetTypeEnum.NOT_COOKIE, null, null);
        List<IRoomLiveStreamInfo> roomLiveStreamUrls = roomInitResult.getRoomLiveStreamUrls();
        assertNotEquals(0, roomLiveStreamUrls.size());
    }

}