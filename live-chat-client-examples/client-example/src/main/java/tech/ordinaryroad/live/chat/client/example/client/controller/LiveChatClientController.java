package tech.ordinaryroad.live.chat.client.example.client.controller;

import cn.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.ordinaryroad.live.chat.client.commons.base.exception.BaseException;
import tech.ordinaryroad.live.chat.client.servers.netty.client.base.BaseNettyClient;

import java.util.Map;

/**
 * @author mjz
 * @date 2023/8/21
 */
@RestController
@RequestMapping("client")
public class LiveChatClientController {

    @Autowired
    Map<String, BaseNettyClient> clientMap;

    @GetMapping("connect")
    public void connect(@RequestParam String platform) {
        BaseNettyClient client = getClient(platform);
        client.connect();
    }

    @GetMapping("autoReconnect/{autoReconnect}")
    public boolean autoReconnect(@RequestParam String platform, @PathVariable Boolean autoReconnect) {
        getClient(platform).getConfig().setAutoReconnect(autoReconnect);
        return getClient(platform).getConfig().isAutoReconnect();
    }

    @GetMapping("roomId/{roomId}")
    public Object roomId(@RequestParam String platform, @PathVariable String roomId) {
        getClient(platform).getConfig().setRoomId(roomId);
        return getClient(platform).getConfig().getRoomId();
    }

    @GetMapping("disconnect/{cancelReconnect}")
    public void disconnect(@RequestParam String platform, @PathVariable Boolean cancelReconnect) {
        getClient(platform).disconnect(cancelReconnect);
    }

    @GetMapping("cookie")
    public String cookie(@RequestParam String platform, @RequestParam String cookie) {
        getClient(platform).getConfig().setCookie(cookie);
        return getClient(platform).getConfig().getCookie();
    }

    @GetMapping("sendDanmu/{danmu}")
    public void sendDanmu(@RequestParam String platform, @PathVariable String danmu) {
        getClient(platform).sendDanmu(danmu + RandomUtil.randomNumbers(1));
    }

    private <Client extends BaseNettyClient> Client getClient(String platform) {
        String key = platform + "LiveChatClient";
        if (!clientMap.containsKey(key)) {
            throw new BaseException("暂不支持 " + platform);
        }
        return (Client) clientMap.get(key);
    }
}
