package com.cloudring.commonlib.http.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Manna on 2016/9/28.
 *
 * @Description: ${todo}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FamilyListResponse {


    /**
     * code : 0
     * message  : 成功
     * data : {"list":[{"id":"123456","imageUrl":"http://jsdfjksfj.gif","nickName":"发发","name":"发放","mobile":"13723454456"},{"id":"123456789","imageUrl":"http://jsdfjksfj.gif","nickName":"发发","name":"发放","mobile":"13723454456"}]}
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
         * id : 123456
         * imageUrl : http://jsdfjksfj.gif
         * nickName : 发发
         * name : 发放
         * mobile : 13723454456
         */
        @JsonProperty("list")
        public List<ListEntity> list;

        public static class ListEntity implements Serializable{

            @JsonProperty("id")
            public String id;

            @JsonProperty("imageUrl")
            public String imageUrl;

            @JsonProperty("niceName")
            public String niceName;

            @JsonProperty("name")
            public String name;

            @JsonProperty("mobile")
            public String mobile;

            @JsonProperty("userId")
            public String userId;
        }
    }
}
