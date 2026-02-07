package tech.ordinaryroad.live.chat.client.codec.huya.util;

import cn.hutool.core.codec.Base64;
import org.junit.jupiter.api.Test;
import tech.ordinaryroad.live.chat.client.codec.huya.msg.*;
import tech.ordinaryroad.live.chat.client.codec.huya.msg.dto.UserId;
import tech.ordinaryroad.live.chat.client.codec.huya.msg.factory.HuyaMsgFactory;
import tech.ordinaryroad.live.chat.client.codec.huya.msg.req.*;
import tech.ordinaryroad.live.chat.client.codec.huya.room.HuyaRoomInitResult;

import java.util.Arrays;
import java.util.List;

/**
 * @author mjz
 * @date 2023/10/3
 */
class HuyaCodecUtilTest {

    private String ver = "2309271152";
    private String exp = "15547.23738,16582.25335,32083.50834";
    private String appSrc = "HUYA&ZH&2052";

    @Test
    void ab2str() {
        ConnectParaInfo wsConnectParaInfo = ConnectParaInfo.newWSConnectParaInfo(ver, exp, appSrc);
        byte[] byteArray = wsConnectParaInfo.toByteArray();
        String s = HuyaCodecUtil.ab2str(byteArray);
        System.out.println(s);
    }

    @Test
    void btoa() {
        ConnectParaInfo wsConnectParaInfo = ConnectParaInfo.newWSConnectParaInfo(ver, exp, appSrc);
        byte[] byteArray = wsConnectParaInfo.toByteArray();
        String s = HuyaCodecUtil.ab2str(byteArray);

        String btoa = HuyaCodecUtil.btoa(s);
        System.out.println(btoa);
    }

    @Test
    void decodeHeartbeatTest() {
        byte[] decode = Base64.decode("AAMdAAEEKAAABCgQAyw8QAFWCG9ubGluZXVpZg9PblVzZXJIZWFydEJlYXR9AAED+QgAAQYEdFJlcR0AAQPrCgoMFiAwYTdkY2E3MmEzY2UxYjY1NDAwMWRkMmFkZTJhZTg1NyYANhp3ZWJoNSYyMzA5MjcxMTUyJndlYnNvY2tldEcAAAOKdnBsYXllcl9zYmFubmVyXzE3MjQ2OTFfMTcyNDY5MT0xOyBTb3VuZFZhbHVlPTAuNTA7IGFscGhhVmFsdWU9MC44MDsgZ2FtZV9kaWQ9UjI0SjJnMG1CenZkWEpmN2E5bmhlU2wzekljaTJCT3AwLXQ7IGlzSW5MaXZlUm9vbT10cnVlOyBndWlkPTBhN2RjYTcyYTNjZTFiNjU0MDAxZGQyYWRlMmFlODU3OyBfX3lhbWlkX3R0MT0wLjgwMjk5MzUwNjUwMTEyNjk7IF9feWFtaWRfbmV3PUNBNzVENENENUMxMDAwMDExNjVCODJCNTIxNDBDOTAwOyBndWlkPTBhN2RjYTcyYTNjZTFiNjU0MDAxZGQyYWRlMmFlODU3OyB1ZGJfZ3VpZGRhdGE9YWY1ZGJkYmY3NjI1NGE2ZThhMGEyOTNjY2FlOWI2ODg7IHVkYl9kZXZpY2VpZD13Xzc2MTYyMzQ4Mzg2MTgxNTI5NjsgdWRiX3Bhc3NkYXRhPTM7IF9feWFzbWlkPTAuODAyOTkzNTA2NTAxMTI2OTsgSG1fbHZ0XzUxNzAwYjZjNzIyZjViYjRjZjM5OTA2YTU5NmVhNDFmPTE2OTY0MDk0MjAsMTY5NjQxMTgwNiwxNjk2NDczNzYwLDE2OTY0NzY3NDU7IF95YXNpZHM9X19yb290c2lkJTNEQ0E3NjY5MjcwM0IwMDAwMTNGRkJCRkVBNjY4Nzk5RDA7IEhtX2xwdnRfNTE3MDBiNmM3MjJmNWJiNGNmMzk5MDZhNTk2ZWE0MWY9MTY5NjQ3Njg4MDsgaHV5YV91YT13ZWJoNSYwLjAuMSZhY3Rpdml0eTsgX3JlcF9jbnQ9Mzsgc2RpZD0wVW5IVWd2MC9xbWZENEtBS2x3emhxU1drU1d3a3RzZXhQSWNsTnQzUGJ3TVFha1dOTmZrMlc4KzFkNlFNZzVZYnU1bC9GcDRpaHRUTDdCd0Q2bTQ0MENHUUNkU0htZW1kOUNNLzVKRVZkanJXVmtuOUx0ZkZKdy9RbzRrZ0tyOE9aSERxTm51d2c2MTJzR3lmbEZuMWRrVWVaWVRUb0N6emw0R0NIcTdNVURhaHhHdVBSOG1VZGRmSW1GdGpjY3MxOyBodXlhX2ZsYXNoX3JlcF9jbnQ9NTc7IHJlcF9jbnQ9NDA7IGh1eWFfd2ViX3JlcF9jbnQ9MTY2XGYGY2hyb21lCxwsQgAaURNcYP98jJysC4yYDKgMLDYlYTgzMDdmMWM0YzRmNGVmMDphODMwN2YxYzRjNGY0ZWYwOjA6MExcZiAyYmZlZjAzNmEwMzBkOTgyN2ZjYmQwMmU5ZmM0NzY1OQ==");
        WupReq wupReq = new WupReq();
        wupReq.decode(decode);
        UserHeartBeatReq userHeartBeatReq = new UserHeartBeatReq();
        userHeartBeatReq = wupReq.getUniAttribute().getByClass("tReq", userHeartBeatReq);
        long lPid = userHeartBeatReq.getLPid();
    }

    @Test
    void decodeRegisterGroupReq() {
        byte[] decode = Base64.decode("ABAdAAAhCQACBgxsaXZlOjE3MjQ2OTEGDGNoYXQ6MTcyNDY5MRYAIAE2AExcZgA=");
        WebSocketCommand webSocketCommand = new WebSocketCommand(HuyaCodecUtil.newUtf8TarsInputStream(decode));
        byte[] vData = webSocketCommand.getVData();
        WSRegisterGroupReq wsRegisterGroupReq = new WSRegisterGroupReq();
        wsRegisterGroupReq.readFrom(HuyaCodecUtil.newUtf8TarsInputStream(vData));
        List<String> vGroupId = wsRegisterGroupReq.getVGroupId();
    }

    @Test
    void decodeLiveLaunchReq() {
        byte[] decode = Base64.decode("AAMdAAEEJAAABCQQAyw8QARWBmxpdmV1aWYIZG9MYXVuY2h9AAED/ggAAQYEdFJlcR0AAQPwCgoMFiAwYTdkY2E3MmEzY2UxYjY1NDAwMWRkMmFkZTJhZTg1NyYANhp3ZWJoNSYyMzA5MjcxMTUyJndlYnNvY2tldEcAAAOKdnBsYXllcl9zYmFubmVyXzE3MjQ2OTFfMTcyNDY5MT0xOyBTb3VuZFZhbHVlPTAuNTA7IGFscGhhVmFsdWU9MC44MDsgZ2FtZV9kaWQ9UjI0SjJnMG1CenZkWEpmN2E5bmhlU2wzekljaTJCT3AwLXQ7IGlzSW5MaXZlUm9vbT10cnVlOyBndWlkPTBhN2RjYTcyYTNjZTFiNjU0MDAxZGQyYWRlMmFlODU3OyBfX3lhbWlkX3R0MT0wLjgwMjk5MzUwNjUwMTEyNjk7IF9feWFtaWRfbmV3PUNBNzVENENENUMxMDAwMDExNjVCODJCNTIxNDBDOTAwOyBndWlkPTBhN2RjYTcyYTNjZTFiNjU0MDAxZGQyYWRlMmFlODU3OyB1ZGJfZ3VpZGRhdGE9YWY1ZGJkYmY3NjI1NGE2ZThhMGEyOTNjY2FlOWI2ODg7IHVkYl9kZXZpY2VpZD13Xzc2MTYyMzQ4Mzg2MTgxNTI5NjsgdWRiX3Bhc3NkYXRhPTM7IF9feWFzbWlkPTAuODAyOTkzNTA2NTAxMTI2OTsgSG1fbHZ0XzUxNzAwYjZjNzIyZjViYjRjZjM5OTA2YTU5NmVhNDFmPTE2OTY0MDk0MjAsMTY5NjQxMTgwNiwxNjk2NDczNzYwLDE2OTY0NzY3NDU7IF95YXNpZHM9X19yb290c2lkJTNEQ0E3NjY5MjcwM0IwMDAwMTNGRkJCRkVBNjY4Nzk5RDA7IEhtX2xwdnRfNTE3MDBiNmM3MjJmNWJiNGNmMzk5MDZhNTk2ZWE0MWY9MTY5NjQ3Njg4MDsgaHV5YV91YT13ZWJoNSYwLjAuMSZhY3Rpdml0eTsgX3JlcF9jbnQ9Mzsgc2RpZD0wVW5IVWd2MC9xbWZENEtBS2x3emhxU1drU1d3a3RzZXhQSWNsTnQzUGJ3TVFha1dOTmZrMlc4KzFkNlFNZzVZYnU1bC9GcDRpaHRUTDdCd0Q2bTQ0MENHUUNkU0htZW1kOUNNLzVKRVZkanJXVmtuOUx0ZkZKdy9RbzRrZ0tyOE9aSERxTm51d2c2MTJzR3lmbEZuMWRrVWVaWVRUb0N6emw0R0NIcTdNVURhaHhHdVBSOG1VZGRmSW1GdGpjY3MxOyBodXlhX2ZsYXNoX3JlcF9jbnQ9NTc7IHJlcF9jbnQ9NDA7IGh1eWFfd2ViX3JlcF9jbnQ9MTY2XGYGY2hyb21lCxoAAxwqFgAmADYARgBWAAsLIAELjJgMqAwsNiU5N2Q5NzQ4NTYyMzdiMWZiOjk3ZDk3NDg1NjIzN2IxZmI6MDowTFxmIGU2OTYzZjMwMTRmYzZlY2U2M2ExNmU3ZTlhMzMzOWVl");
        WupReq wupReq = new WupReq();
        wupReq.decode(decode);
        LiveLaunchReq liveLaunchReq = new LiveLaunchReq();
        liveLaunchReq = wupReq.getUniAttribute().getByClass("tReq", liveLaunchReq);
        UserId tId = liveLaunchReq.getTId();
        System.out.println(wupReq.getTarsServantRequest().getVersion());
    }

    @Test
    void decodeLiveLaunchRsp() {
        byte[] decode = Base64.decode("AAQdAAECXAAAAlwQAyw8QARWBmxpdmV1aWYIZG9MYXVuY2h9AAECNggAAgYAHQAAAQwGBHRSc3AdAAECIQoGIDBhN2RjYTcyYTNjZTFiNjU0MDAxZGQyYWRlMmFlODU3EmUeNXopAAUKAAEZAAQGETExMS4xOS4yMzkuMTExOjgwBhAxMTEuMTkuMjI1LjIzOjgwBhAxMTEuNjMuMTgwLjk4OjgwBhExMTEuNjMuMTgwLjEwMDo4MAsKAAUZAAQGFzZmMTNlZjZmLXdzLnZhLmh1eWEuY29tBhc2ZjEzZTExNy13cy52YS5odXlhLmNvbQYXNmYzZmI0NjItd3MudmEuaHV5YS5jb20GFzZmM2ZiNDY0LXdzLnZhLmh1eWEuY29tCwoABhkABAYRMTExLjE5LjIzOS4xMTE6ODAGEDExMS4xOS4yMjUuMjM6ODAGEDExMS42My4xODAuOTg6ODAGETExMS42My4xODAuMTAwOjgwCwoABxkABAYRMTExLjE5LjIzOS4xMTE6ODAGEDExMS4xOS4yMjUuMjM6ODAGEDExMS42My4xODAuOTg6ODAGETExMS42My4xODAuMTAwOjgwCwoACRkACAYOMTIwLjE5NS4xNTguNDYGDzExMy4xMDcuMjM2LjE5NQYMMTQuMTcuMTA5LjY2Bg8xMDMuMjI3LjEyMS4xMDAGDzExNS4yMzguMTg5LjIyNQYPMTgzLjIzMi4xMzYuMTMwBg4yMjEuMjI4Ljc5LjIyNQYMNjAuMjE3LjI1MC4xCzxGDTExMi40My45Mi4xMTgLjJgMqAwsNiU5N2Q5NzQ4NTYyMzdiMWZiOjk3ZDk3NDg1NjIzN2IxZmI6MDowTFxmAA==");
        WupRsp wupRsp = new WupRsp();
        wupRsp.decode(decode);
        LiveLaunchRsp liveLaunchRsp = new LiveLaunchRsp();
        liveLaunchRsp = wupRsp.getUniAttribute().getByClass("tRsp", liveLaunchRsp);
        int eAccess = liveLaunchRsp.getEAccess();
    }

    @Test
    void decodeGetLivingInfoReq() {
        byte[] decode = Base64.decode("AAMdAAEEKAAABCgQAyw8QAFWCmh1eWFsaXZldWlmDWdldExpdmluZ0luZm99AAED+QgAAQYEdFJlcR0AAQPrCgoMFiAwYTdkY2E3MmEzY2UxYjY1NDAwMWRkMmFkZTJhZTg1NyYANhp3ZWJoNSYyMzA5MjcxMTUyJndlYnNvY2tldEcAAAOKdnBsYXllcl9zYmFubmVyXzE3MjQ2OTFfMTcyNDY5MT0xOyBTb3VuZFZhbHVlPTAuNTA7IGFscGhhVmFsdWU9MC44MDsgZ2FtZV9kaWQ9UjI0SjJnMG1CenZkWEpmN2E5bmhlU2wzekljaTJCT3AwLXQ7IGlzSW5MaXZlUm9vbT10cnVlOyBndWlkPTBhN2RjYTcyYTNjZTFiNjU0MDAxZGQyYWRlMmFlODU3OyBfX3lhbWlkX3R0MT0wLjgwMjk5MzUwNjUwMTEyNjk7IF9feWFtaWRfbmV3PUNBNzVENENENUMxMDAwMDExNjVCODJCNTIxNDBDOTAwOyBndWlkPTBhN2RjYTcyYTNjZTFiNjU0MDAxZGQyYWRlMmFlODU3OyB1ZGJfZ3VpZGRhdGE9YWY1ZGJkYmY3NjI1NGE2ZThhMGEyOTNjY2FlOWI2ODg7IHVkYl9kZXZpY2VpZD13Xzc2MTYyMzQ4Mzg2MTgxNTI5NjsgdWRiX3Bhc3NkYXRhPTM7IF9feWFzbWlkPTAuODAyOTkzNTA2NTAxMTI2OTsgX3lhc2lkcz1fX3Jvb3RzaWQlM0RDQTc2NzA0NUMxMTAwMDAxRUM1QTE0NUMxRTkwRkUwMDsgSG1fbHZ0XzUxNzAwYjZjNzIyZjViYjRjZjM5OTA2YTU5NmVhNDFmPTE2OTY0NzM3NjAsMTY5NjQ3Njc0NSwxNjk2NDgzNTY1LDE2OTY0ODQyMTI7IEhtX2xwdnRfNTE3MDBiNmM3MjJmNWJiNGNmMzk5MDZhNTk2ZWE0MWY9MTY5NjQ4NDIxMjsgaHV5YV91YT13ZWJoNSYwLjAuMSZhY3Rpdml0eTsgX3JlcF9jbnQ9Mjsgc2RpZD0wVW5IVWd2MC9xbWZENEtBS2x3emhxWDk4UXJuUENjY2s2Zk40OTRpYXdTNUtteW1ncmV1ODlvN2dZdGEvUXZRc0JEZVNlU1JwNC9nclE1ZkV1RkFZckw1OWNvQUt1eHV1a2l3Z1RMamZqRURXVmtuOUx0ZkZKdy9RbzRrZ0tyOE9aSERxTm51d2c2MTJzR3lmbEZuMWRrVWVaWVRUb0N6emw0R0NIcTdNVURhaHhHdVBSOG1VZGRmSW1GdGpjY3MxOyBodXlhX2ZsYXNoX3JlcF9jbnQ9NzQ7IGh1eWFfd2ViX3JlcF9jbnQ9MTI1OyByZXBfY250PTQ0XGYGY2hyb21lCxwsMgAaURNGAFYAbHyMC4yYDKgMLDYlZTcxMDQ2OTExYzk2N2JjNDplNzEwNDY5MTFjOTY3YmM0OjA6MExcZiBjNTRlM2NkYmIyNGJjYzcyYmU1MjU5NTY4ZGVmY2Q1Ng==");
        WupReq wupReq = new WupReq();
        wupReq.decode(decode);
        GetLivingInfoReq getLivingInfoReq = new GetLivingInfoReq();
        getLivingInfoReq = wupReq.getUniAttribute().getByClass("tReq", getLivingInfoReq);

        byte[] decode2 = Base64.decode("AAAEJxwsPEABVgpodXlhbGl2ZXVpZg1nZXRMaXZpbmdJbmZvfQABA/kIAAEGBHRSZXEdAAED6woKDBYgMGE3ZGNhNzJhM2NlMWI2NTQwMDFkZDJhZGUyYWU4NTcmADYad2ViaDUmMjMwOTI3MTE1MiZ3ZWJzb2NrZXRHAAADinZwbGF5ZXJfc2Jhbm5lcl8xNzI0NjkxXzE3MjQ2OTE9MTsgU291bmRWYWx1ZT0wLjUwOyBhbHBoYVZhbHVlPTAuODA7IGdhbWVfZGlkPVIyNEoyZzBtQnp2ZFhKZjdhOW5oZVNsM3pJY2kyQk9wMC10OyBpc0luTGl2ZVJvb209dHJ1ZTsgZ3VpZD0wYTdkY2E3MmEzY2UxYjY1NDAwMWRkMmFkZTJhZTg1NzsgX195YW1pZF90dDE9MC44MDI5OTM1MDY1MDExMjY5OyBfX3lhbWlkX25ldz1DQTc1RDRDRDVDMTAwMDAxMTY1QjgyQjUyMTQwQzkwMDsgZ3VpZD0wYTdkY2E3MmEzY2UxYjY1NDAwMWRkMmFkZTJhZTg1NzsgdWRiX2d1aWRkYXRhPWFmNWRiZGJmNzYyNTRhNmU4YTBhMjkzY2NhZTliNjg4OyB1ZGJfZGV2aWNlaWQ9d183NjE2MjM0ODM4NjE4MTUyOTY7IHVkYl9wYXNzZGF0YT0zOyBfX3lhc21pZD0wLjgwMjk5MzUwNjUwMTEyNjk7IF95YXNpZHM9X19yb290c2lkJTNEQ0E3NjcwNDVDMTEwMDAwMUVDNUExNDVDMUU5MEZFMDA7IEhtX2x2dF81MTcwMGI2YzcyMmY1YmI0Y2YzOTkwNmE1OTZlYTQxZj0xNjk2NDczNzYwLDE2OTY0NzY3NDUsMTY5NjQ4MzU2NSwxNjk2NDg0MjEyOyBIbV9scHZ0XzUxNzAwYjZjNzIyZjViYjRjZjM5OTA2YTU5NmVhNDFmPTE2OTY0ODQyMTI7IGh1eWFfdWE9d2ViaDUmMC4wLjEmYWN0aXZpdHk7IF9yZXBfY250PTI7IHNkaWQ9MFVuSFVndjAvcW1mRDRLQUtsd3pocVg5OFFyblBDY2NrNmZONDk0aWF3UzVLbXltZ3JldTg5bzdnWXRhL1F2UXNCRGVTZVNScDQvZ3JRNWZFdUZBWXJMNTljb0FLdXh1dWtpd2dUTGpmakVEV1ZrbjlMdGZGSncvUW80a2dLcjhPWkhEcU5udXdnNjEyc0d5ZmxGbjFka1VlWllUVG9DenpsNEdDSHE3TVVEYWh4R3VQUjhtVWRkZkltRnRqY2NzMTsgaHV5YV9mbGFzaF9yZXBfY250PTc0OyBodXlhX3dlYl9yZXBfY250PTEyNTsgcmVwX2NudD00NFxmBmNocm9tZQscLDIAGlETRgBWAGx8jAuMmAyoDA==");
        WupReq wupReq2 = new WupReq();
        wupReq2.decode(decode2);
        GetLivingInfoReq getLivingInfoReq2 = new GetLivingInfoReq();
        getLivingInfoReq2 = wupReq2.getUniAttribute().getByClass("tReq", getLivingInfoReq2);

        UserId tId = getLivingInfoReq.getTId();
    }

    @Test
    void decodeGetPropListReq() {
        byte[] decode = Base64.decode("AAMdAAB4AAAAeBADLDxAA1YNUHJvcHNVSVNlcnZlcmYMZ2V0UHJvcHNMaXN0fQAASAgAAQYEdFJlcR0AADsKGgIBrCfyFgAmADYad2ViaDUmMjMwOTI3MTE1MiZ3ZWJzb2NrZXRGAFxmAHYACyYAMAFGAFxsfIycC4yYDKgMLDYATFxmAA==");
        WebSocketCommand webSocketCommand = new WebSocketCommand();
        webSocketCommand.readFrom(HuyaCodecUtil.newUtf8TarsInputStream(decode));

        byte[] vData = webSocketCommand.getVData();
        WupReq wupReq = new WupReq();
        wupReq.decode(vData);

        GetPropsListReq tReq = wupReq.getUniAttribute().getByClass("tReq", new GetPropsListReq());
        assert tReq.getITemplateType() == 1;
    }

    @Test
    void decodeGetPropListReq2() {
        HuyaRoomInitResult roomInitResult = HuyaRoomInitResult.builder()
                .lYyid(666L)
                .lChannelId(777L)
                .lSubChannelId("778")
                .build();

        WebSocketCommand giftListReq = HuyaMsgFactory.getInstance(666).createGiftListReq(roomInitResult, "20266666");

        byte[] vData = giftListReq.getVData();
        System.out.println(Arrays.toString(vData));
        WupReq wupReq = new WupReq();
        wupReq.readFrom(HuyaCodecUtil.newUtf8TarsInputStream(vData));

        GetPropsListReq getPropsListReq = new GetPropsListReq();
        GetPropsListReq tReq = wupReq.getUniAttribute().getByClass("tReq", getPropsListReq);
    }

    @Test
    void decodeRegisterGroupReq2() {
        HuyaRoomInitResult roomInitResult = HuyaRoomInitResult.builder()
                .lYyid(666L)
                .lChannelId(777L)
                .lSubChannelId("778")
                .build();

        WebSocketCommand webSocketCommand = HuyaMsgFactory.getInstance(666).createRegisterGroupReq(roomInitResult);

        byte[] vData = webSocketCommand.getVData();
        System.out.println(Arrays.toString(vData));
        WSRegisterGroupReq wsRegisterGroupReq = new WSRegisterGroupReq();
        wsRegisterGroupReq.readFrom(HuyaCodecUtil.newUtf8TarsInputStream(vData));
        List<String> vGroupId = wsRegisterGroupReq.getVGroupId();
    }

    @Test
    void decodeSendGiftReq() {
        byte[] decode = Base64.decode("AAMdAAEI4wAACOMQAyw8QElWBXd1cHVpZghzZW5kR2lmdH0AAQi+CAABBgR0UmVxHQABCLAKCgJkMqW/FiAwYTg5M2E1YjRkYTk2ODY5NDgwMTZhOWQ2YjlkY2Y4MCYANhp3ZWJoNSYyNjAxMjYxNTE3JndlYnNvY2tldEcAAAgDdnBsYXllcl9zYmFubmVyXzIzNjc1NDczODdfMjM2NzU0NzM4Nz0xOyBfX3lhbWlkX25ldz1DQjgyOTg4MzMyODAwMDAxMTAyNzRGQTAxRDYwMTU0RjsgZ2FtZV9kaWQ9bWUtMlVMN3BzYTdhZ3pYdW9kWDhEZTlTM1JKcUtqSTZiZ2w7IGd1aWQ9MGE4OTNhNWI0ZGE5Njg2OTQ4MDE2YTlkNmI5ZGNmODA7IHVkYl9ndWlkZGF0YT1lZjFlNjk5OWUwYWQ0YjU1YTgxY2YxYWJmN2Q3MTg3YzsgX3FpbWVpX3V1aWQ0Mj0xYTEwZjEwMmUwNjEwMDYwMDNhNGJiNDE4YmMwYjUwZTNjM2EwNTNiM2Y7IHVkYl9kZXZpY2VpZD13XzEwNjQyMjM5NjkyNzM0MzgyMDg7IF9xaW1laV9oMzg9ZTY4MWFkOGMwM2E0YmI0MThiYzBiNTBlMDMwMDAwMDhmMWExMGY7IF9feWFzbWlkPTAuMjg1OTcwMzY0NDIwNzMyMjsgX195YW1pZF90dDE9MC4yODU5NzAzNjQ0MjA3MzIyOyBndWlkPTBhODkzYTViNGRhOTY4Njk0ODAxNmE5ZDZiOWRjZjgwOyBhbHBoYVZhbHVlPTAuODA7IF9yZXBfY250PTI7IHVkYl9wYXNzZGF0YT0zOyBoZGlkPWJhYjhlZTA3MzBiNGNmNGZlNGE1NjZlZTE0NjA1MDJlMTRmYTI3ZmI7IEhtX2x2dF81MTcwMGI2YzcyMmY1YmI0Y2YzOTkwNmE1OTZlYTQxZj0xNzY4NDY2Nzg4OyBITUFDQ09VTlQ9NzhEMTY0NUNCQzU0QTk5MTsgdWRiX2FwcGlkPTUwMDI7IG51bGxfcmVwX2NudD0yOyBodXlhd2FwX3JlcF9jbnQ9NzsgdWRiX2NyZWQ9Q2hBdGdONVh5Y0FLamhjME5BeVY2UllGbk83dl9MSGhWbnEzNlp4WkdrWkJ1LWFnQllMaEkyYkJfRnQxemhpM000S3hoaXJOOElncWdEQzV5Unl1OW9nVUdvNUNtWkNYRmNzSThhamlNRldaWXZCUWlJVWhkb1gxOU5JWVVDZTBNU0hjNlR0anhVNlVvTG9ZSzRPVUlma2Q7IHVkYl9vcmlnaW49MTAwOyB1ZGJfb3RoZXI9JTdCJTIybHQlMjIlM0ElMjIxNzY5MjYwNjY0ODMzJTIyJTJDJTIyaXNSZW0lMjIlM0ElMjIxJTIyJTdEOyB1ZGJfcGFzc3BvcnQ9bmV3cXFfamEzMHV4cHNuOyB1ZGJfc3RhdHVzPTE7IHVkYl91aWQ9MTY4MTA0MDgzMTsgdWRiX3ZlcnNpb249MS4wOyB1c2VybmFtZT1uZXdxcV9qYTMwdXhwc247IHl5dWlkPTE2ODEwNDA4MzE7IHVkYl9hY2NkYXRhPTA4NjE3MzE1MDM3OTAzOyBfX3lhb2xkeXl1aWQ9MTY4MTA0MDgzMTsgX3lhc2lkcz1fX3Jvb3RzaWQlM0RDQjg1OERBMkMzMjAwMDAxRkYzRUFENjgxRTQ5RkJDMDsgdWRiX2JpenRva2VuPUFRQzdMSU1yS1pkNVQ5TjJSUFNHc2JEYU8yanY4TFJwcmhFa0dEX2V4VW15VTIxNDBXYllld3pJaXk3Sk95OHFtdENPeFZnS1hYODFsMWtGVktheEh6dWlGOG5qSEY0VGhvdmhISUx4amdZTFZYYXBod1R3elBVSlNEaGUybEhsSG52MnZtRVZTVW43TVA3X1E2dkxNV29NMlExb0VUVEZkT1d4SDIzSE1fU2k2UTdTdEt1eUQ2dzR4YmkwWm13QTdiaGNJRlVFZGdXRkJSM2pCMlctRTZmVXB6NklrZFBma0VFV29kMG92Ti15XzJHcTlENnNmREw1dHR5M0xVUlFQbjU5dVN2RXNWaVhoVWk3T0dXR3VnbEllc3BYQ3RoUi1SN2xvcjlqUVItYnJkRFZvRjBMXzJwdGV0WnNPVldvLVVLYi1xeGVOVExrUmh1aHFIdkRLQTNYOyBfcWltZWlfZmluZ2VycHJpbnQ9MWMyMTZkYjg1MjM1MDFjYWFkZDMzOGI2OGU2MDg0MTU7IHJlcF9jbnQ9MzAzOyBTb3VuZFZhbHVlPTAuMDA7IHZpZGVvTGluZT0xNDsgaF91bnQ9MTc2OTQ5ODI2Mzsgc2RpZD0wVW5IVWd2MF9xbWZENEtBS2x3emhxWFdvdlZBMXpxblNsTWxCZjlRRnI5U0NZZGF2VldXYjVrTm5aeFluMldRejZra09RN2MwMnRiZW1rcDBkQnZnQWQ3MWFBRndFMU1scktWS01LUEtuejdXVmtuOUx0ZkZKd19RbzRrZ0tyOE9aSERxTm51d2c2MTJzR3lmbEZuMWRoUEtRZmJWa3k2RVcwSWh0WEtXU0lfMnJnYzlWa05vWEhiajZVMkQ5blFVOyBQSFBTRVNTSUQ9Y2kzNDQ5N29ydmVwbmN1N29zajZzODBvbjU7IGlzSW5MaXZlUm9vbT10cnVlOyBodXlhX2hkX3JlcF9jbnQ9MTY0OyBodXlhX2ZsYXNoX3JlcF9jbnQ9NzEzOyBIbV9scHZ0XzUxNzAwYjZjNzIyZjViYjRjZjM5OTA2YTU5NmVhNDFmPTE3Njk0OTg0MzU7IGh1eWFfd2ViX3JlcF9jbnQ9MjU1NTsgaHV5YV91YT13ZWJoNSYwLjEuMCZ3ZWJzb2NrZXRcZgZjaHJvbWV2AAsTAAAAAI0d5/sjAAAAAI0d5/sxTpJAAVYgMjAyNjAxMjcxNTM3NDgwNTAwMTE1QUdBQUFBQUFBQUFmB2dpZnRiYXJ6AQH/EAosC4oJDBkMKQwLmAwLjJgMqAwsNiVhMTYxMjBiODc3OTJhNDA3OmExNjEyMGI4Nzc5MmE0MDc6MDowTFxmIDQyNzYwYjdjZjJhNmQxODkwZGU3M2Q4ZTY2NjRkM2E1");

        WebSocketCommand webSocketCommand = new WebSocketCommand();
        webSocketCommand.readFrom(HuyaCodecUtil.newUtf8TarsInputStream(decode));

        WupReq wupReq = new WupReq();
        wupReq.decode(webSocketCommand.getVData());

        SendGiftReq sendGiftReq = new SendGiftReq();
        sendGiftReq = wupReq.getUniAttribute().getByClass("tReq", sendGiftReq);
    }

    @Test
    void decodeGetSequenceReq() {
        byte[] decode = Base64.decode("AAMdAAEIDgAACA4QAyw8QBRWCnNlcXVlbmNldWlmC2dldFNlcXVlbmNlfQABB+EIAAEGBHRSZXEdAAEH0woKAmQypb8WACYANhB3ZWJoNSYwLjAuMSZodXlhRwAAB4RodXlhX3VhPXdlYmg1JjAuMC4xJmh1eWE7X195YW1pZF9uZXc9Q0I4Mjk4ODMzMjgwMDAwMTEwMjc0RkEwMUQ2MDE1NEY7Z2FtZV9kaWQ9bWUtMlVMN3BzYTdhZ3pYdW9kWDhEZTlTM1JKcUtqSTZiZ2w7Z3VpZD0wYTg5M2E1YjRkYTk2ODY5NDgwMTZhOWQ2YjlkY2Y4MDt1ZGJfZ3VpZGRhdGE9ZWYxZTY5OTllMGFkNGI1NWE4MWNmMWFiZjdkNzE4N2M7X3FpbWVpX3V1aWQ0Mj0xYTEwZjEwMmUwNjEwMDYwMDNhNGJiNDE4YmMwYjUwZTNjM2EwNTNiM2Y7dWRiX2RldmljZWlkPXdfMTA2NDIyMzk2OTI3MzQzODIwODtfcWltZWlfaDM4PWU2ODFhZDhjMDNhNGJiNDE4YmMwYjUwZTAzMDAwMDA4ZjFhMTBmO19feWFzbWlkPTAuMjg1OTcwMzY0NDIwNzMyMjtfX3lhbWlkX3R0MT0wLjI4NTk3MDM2NDQyMDczMjI7YWxwaGFWYWx1ZT0wLjgwO19yZXBfY250PTI7dWRiX3Bhc3NkYXRhPTM7aGRpZD1iYWI4ZWUwNzMwYjRjZjRmZTRhNTY2ZWUxNDYwNTAyZTE0ZmEyN2ZiO0htX2x2dF81MTcwMGI2YzcyMmY1YmI0Y2YzOTkwNmE1OTZlYTQxZj0xNzY4NDY2Nzg4O0hNQUNDT1VOVD03OEQxNjQ1Q0JDNTRBOTkxO3VkYl9hcHBpZD01MDAyO251bGxfcmVwX2NudD0yO2h1eWF3YXBfcmVwX2NudD03O3VkYl9jcmVkPUNoQXRnTjVYeWNBS2poYzBOQXlWNlJZRm5PN3ZfTEhoVm5xMzZaeFpHa1pCdS1hZ0JZTGhJMmJCX0Z0MXpoaTNNNEt4aGlyTjhJZ3FnREM1eVJ5dTlvZ1VHbzVDbVpDWEZjc0k4YWppTUZXWll2QlFpSVVoZG9YMTlOSVlVQ2UwTVNIYzZUdGp4VTZVb0xvWUs0T1VJZmtkO3VkYl9vcmlnaW49MTAwO3VkYl9vdGhlcj0lN0IlMjJsdCUyMiUzQSUyMjE3NjkyNjA2NjQ4MzMlMjIlMkMlMjJpc1JlbSUyMiUzQSUyMjElMjIlN0Q7dWRiX3Bhc3Nwb3J0PW5ld3FxX2phMzB1eHBzbjt1ZGJfc3RhdHVzPTE7dWRiX3VpZD0xNjgxMDQwODMxO3VkYl92ZXJzaW9uPTEuMDt1c2VybmFtZT1uZXdxcV9qYTMwdXhwc247eXl1aWQ9MTY4MTA0MDgzMTt1ZGJfYWNjZGF0YT0wODYxNzMxNTAzNzkwMztfX3lhb2xkeXl1aWQ9MTY4MTA0MDgzMTtfeWFzaWRzPV9fcm9vdHNpZCUzRENCODU4REEyQzMyMDAwMDFGRjNFQUQ2ODFFNDlGQkMwO19xaW1laV9maW5nZXJwcmludD0xYzIxNmRiODUyMzUwMWNhYWRkMzM4YjY4ZTYwODQxNTtndWlkPTBhODkzYTViNGRhOTY4Njk0ODAxNmE5ZDZiOWRjZjgwO3JlcF9jbnQ9MzAzO1NvdW5kVmFsdWU9MC4wMDt2aWRlb0xpbmU9MTQ7aF91bnQ9MTc2OTQ5ODI2Mztpc0luTGl2ZVJvb209dHJ1ZTt1ZGJfYml6dG9rZW49QVFBZDNjUDlVQ2llRUdhQWYzYXB5VDdHcXN2UGJFYzZSVXctSE1yVWRIc2RHLW5JNHJheDhaZnFuMXl1RDROMzMxMEg3cVd6ajl3bXBibXptNXhselN0ckJ3Vkg5QkphT19RblBMOVE4ODhtM0VhcmJCcGZoVkYxTEI5NFBKMW1hZXFiclMxbFAxQzJOT1B3bElFREQyR2h0T1ZDWUMxUkxfR3VlQjNQMElNVlpueHNmRTg5d2dFOGczWFdwM0k5Z2RVcWRheVlYWkE2SjRvUGt3NERaYlpQa3RxNVdYWVBKa201UW5nVmhweXpKVUNENHJCOEhnRDNmZUQ4dXFmbEdKN3NScEllV2tmaHdUUS1jWFpoVGhqN01IRTkxbnhQMS1BTXBKZGNmR3R3MGJTazVTRk8zbjhEOEZhS1VvSGlPczlMVXhqV0c2alg4bHhRdHNkaVdDR1o7c2RpZD0wVW5IVWd2MF9xbWZENEtBS2x3emhxUWw3QVE2U1FQVUo4VkNtYUlxcS1zS0ZIQ0dfNkpicUMxaDFXakdIb05heHhtdGVfZ3B5N0hfemNEV2RRQVBQZl9fa2lWNlJxeGtMMkNjZVpndXFkbGpXVmtuOUx0ZkZKd19RbzRrZ0tyOE9aSERxTm51d2c2MTJzR3lmbEZuMWRoUEtRZmJWa3k2RVcwSWh0WEtXU0lfMnJnYzlWa05vWEhiajZVMkQ5blFVO0htX2xwdnRfNTE3MDBiNmM3MjJmNWJiNGNmMzk5MDZhNTk2ZWE0MWY9MTc2OTUwMjI4OTtodXlhX2hkX3JlcF9jbnQ9Mjk3O2h1eWFfZmxhc2hfcmVwX2NudD05MzI7aHV5YV93ZWJfcmVwX2NudD0zMDE4XGYACxABIAUwAUYgZjc2YWVmYTA5YWJkYTAwOWQzODVhODJkOWUxMGFiMjgLjJgMqAwsNiU3YmNjZjFiMGQ0ZGEwYWZiOjdiY2NmMWIwZDRkYTBhZmI6MDowTFxmAA==");

        WebSocketCommand webSocketCommand = new WebSocketCommand();
        webSocketCommand.readFrom(HuyaCodecUtil.newUtf8TarsInputStream(decode));

        WupReq wupReq = new WupReq();
        wupReq.decode(webSocketCommand.getVData());

        GetSequenceReq getSequenceReq = new GetSequenceReq();
        getSequenceReq = wupReq.getUniAttribute().getByClass("tReq", getSequenceReq);
    }

    @Test
    void decodeGetSequenceRsq() {
        byte[] decode = Base64.decode("AAQdAABlAAAAZRADLDxAFFYKc2VxdWVuY2V1aWYLZ2V0U2VxdWVuY2V9AAA5CAACBgAdAAABDAYEdFJzcB0AACUKDBYgMjAyNjAxMjcxNjI5MTQwNTAwMTEwQUxBQUFBQUFBQUELjJgMqAwsNhJmZjAwOTJiZjZkYjhiZjVhYTNMXGYA");

        WebSocketCommand webSocketCommand = new WebSocketCommand();
        webSocketCommand.readFrom(HuyaCodecUtil.newUtf8TarsInputStream(decode));

        WupRsp wupRsq = new WupRsp();
        wupRsq.decode(webSocketCommand.getVData());

        GetSequenceRsp getSequenceRsp = new GetSequenceRsp();
        getSequenceRsp = wupRsq.getUniAttribute().getByClass("tRsp", getSequenceRsp);
    }
}