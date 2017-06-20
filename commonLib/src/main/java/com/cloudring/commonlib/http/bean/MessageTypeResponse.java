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
public class MessageTypeResponse {


    /**
     * code : 0
     * message : 成功
     * data : {"list":[{"unreadCount":"9","msgType":"1","msgName":"我的资产","sendTime":"2016-10-17 15:33:17","title":"标题","icon":"http://......."}]}
     */

    @JsonProperty("code")
    public String code;
    @JsonProperty("message")
    public String message;
    @JsonProperty("data")
    public DataEntity data;
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataEntity {
        /**
         * unreadCount : 9
         * msgType : 1
         * msgName : 我的资产
         * sendTime : 2016-10-17 15:33:17
         * title : 标题
         * icon : http://.......
         */

        @JsonProperty("list")
        public List<ListEntity> list;

        public static class ListEntity {
            @JsonProperty("unreadCount")
            public String unreadCount;
            @JsonProperty("msgType")
            public String msgType;
            @JsonProperty("typeName")
            public String typeName;
            @JsonProperty("sendTime")
            public String sendTime;
            @JsonProperty("title")
            public String title;
            @JsonProperty("icon")
            public String icon;
            @JsonProperty("lastMsgId")
            public String lastMsgId;
        }
    }
}
