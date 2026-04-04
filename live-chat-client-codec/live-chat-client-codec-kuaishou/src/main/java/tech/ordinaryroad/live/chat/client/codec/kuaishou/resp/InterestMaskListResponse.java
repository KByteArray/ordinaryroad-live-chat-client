package tech.ordinaryroad.live.chat.client.codec.kuaishou.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class InterestMaskListResponse {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("categoryType")
    private Integer categoryType;
    @JsonProperty("authorList")
    private List<AuthorListDTO> authorList;

    @NoArgsConstructor
    @Data
    public static class AuthorListDTO {
        @JsonProperty("kwaiId")
        private String kwaiId;
        @JsonProperty("fanCount")
        private String fanCount;
        @JsonProperty("principalId")
        private String principalId;
        @JsonProperty("live")
        private Boolean live;
        @JsonProperty("verified")
        private Boolean verified;
        @JsonProperty("following")
        private Boolean following;
        @JsonProperty("eid")
        private String eid;
        @JsonProperty("visitorBeFollowed")
        private Boolean visitorBeFollowed;
        @JsonProperty("headurls")
        private List<HeadurlsDTO> headurls;
        @JsonProperty("headurl")
        private String headurl;
        @JsonProperty("user_sex")
        private String userSex;
        @JsonProperty("isFavorited")
        private Boolean isFavorited;
        @JsonProperty("user_id")
        private Long userId;
        @JsonProperty("user_name")
        private String userName;
        @JsonProperty("user_text")
        private String userText;
        @JsonProperty("verifiedDetail")
        private VerifiedDetailDTO verifiedDetail;

        @NoArgsConstructor
        @Data
        public static class VerifiedDetailDTO {
            @JsonProperty("description")
            private String description;
            @JsonProperty("iconType")
            private Integer iconType;
            @JsonProperty("viceVerifiedType")
            private Integer viceVerifiedType;
            @JsonProperty("musicCompany")
            private Boolean musicCompany;
            @JsonProperty("newVerified")
            private Boolean newVerified;
            @JsonProperty("type")
            private Integer type;
        }

        @NoArgsConstructor
        @Data
        public static class HeadurlsDTO {
            @JsonProperty("cdn")
            private String cdn;
            @JsonProperty("url")
            private String url;
        }
    }
}