package com.cloudring.commonlib.http.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Manna on 2016/9/28.
 *
 * @Description: ${todo}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageListResponse {

    /**
     * code : 0
     * message  : 成功
     * data : {"list":[{"msgId":"12345657985464346464","title":"魔镜主要功能是","content":"魔镜介绍......","sendTime":"2016-10-17 17:44:17","senderName":"admin","directUrl":"http://......."},{"msgId":"12345657985464346464","title":"魔镜主要功能是","content":"魔镜介绍......","sendTime":"2016-10-17 17:44:17","senderName":"admin","directUrl":"http://......."}]}
     */

    @JsonProperty("code")
    public String code;
    @JsonProperty("message ")
    public String message;
    @JsonProperty("data")
    public DataEntity data;

    public static class DataEntity {
        /**
         * msgId : 12345657985464346464
         * title : 魔镜主要功能是
         * content : 魔镜介绍......
         * sendTime : 2016-10-17 17:44:17
         * senderName : admin
         * directUrl : http://.......
         */

        @JsonProperty("list")
        public List<ListEntity> list;

        public static class ListEntity {
            @JsonProperty("msgId")
            public String msgId;
            @JsonProperty("title")
            public String title;
            @JsonProperty("content")
            public String content;
            @JsonProperty("sendTime")
            public String sendTime;
            @JsonProperty("senderName")
            public String senderName;
            @JsonProperty("directUrl")
            public String directUrl;
        }
    }
}
