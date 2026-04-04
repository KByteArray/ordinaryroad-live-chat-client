package tech.ordinaryroad.live.chat.client.codec.kuaishou.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 快手用户信息响应
 * @author cuobiezi
 * @date 2023/09/05 14:00
 */
@NoArgsConstructor
@Data
public class KuaishouUserInfoResponse {

    @JsonProperty("ownerInfo")
    private OwnerInfoDTO ownerInfo;
    @JsonProperty("kshellBalance")
    private KshellBalanceDTO kshellBalance;

    @NoArgsConstructor
    @Data
    public static class OwnerInfoDTO {
        @JsonProperty("id")
        private String id;
        @JsonProperty("name")
        private String name;
        @JsonProperty("description")
        private String description;
        @JsonProperty("avatar")
        private String avatar;
        @JsonProperty("sex")
        private String sex;
        @JsonProperty("constellation")
        private String constellation;
        @JsonProperty("cityName")
        private String cityName;
        @JsonProperty("originUserId")
        private Long originUserId;
        @JsonProperty("privacy")
        private Boolean privacy;
        @JsonProperty("isNew")
        private Boolean isNew;
        @JsonProperty("timestamp")
        private Long timestamp;
        @JsonProperty("verifiedStatus")
        private VerifiedStatusDTO verifiedStatus;
        @JsonProperty("bannedStatus")
        private BannedStatusDTO bannedStatus;
        @JsonProperty("counts")
        private CountsDTO counts;
        @JsonProperty("isAdult")
        private Boolean isAdult;

        @NoArgsConstructor
        @Data
        public static class VerifiedStatusDTO {
            @JsonProperty("description")
            private String description;
            @JsonProperty("type")
            private Integer type;
            @JsonProperty("new")
            private Boolean newX;
            @JsonProperty("iconUrl")
            private String iconUrl;
        }

        @NoArgsConstructor
        @Data
        public static class BannedStatusDTO {
            @JsonProperty("banned")
            private Boolean banned;
            @JsonProperty("socialBanned")
            private Boolean socialBanned;
            @JsonProperty("isolate")
            private Boolean isolate;
            @JsonProperty("defriend")
            private Boolean defriend;
        }

        @NoArgsConstructor
        @Data
        public static class CountsDTO {
            @JsonProperty("fan")
            private String fan;
            @JsonProperty("follow")
            private String follow;
            @JsonProperty("photo")
            private Integer photo;
            @JsonProperty("playback")
            private Integer playback;
            @JsonProperty("liked")
            private Integer liked;
            @JsonProperty("private")
            private Integer privateCount;
            @JsonProperty("review")
            private Integer review;
            @JsonProperty("open")
            private Integer open;
        }
    }

    @NoArgsConstructor
    @Data
    public static class KshellBalanceDTO {
        @JsonProperty("result")
        private Integer result;
        @JsonProperty("error_msg")
        private String errorMsg;
        @JsonProperty("host-name")
        private String hostname;
    }
}