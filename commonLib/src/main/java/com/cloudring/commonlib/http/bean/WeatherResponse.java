package com.cloudring.commonlib.http.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by zjq on 2016/7/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
    /**
     * 地理位置
     * as : AS4134 Chinanet
     * city : Shenzhen
     * country : China
     * countryCode : CN
     * isp : China Telecom Guangdong
     * lat : 22.5333
     * lon : 114.1333
     * org : China Telecom Guangdong
     * query : 183.16.197.132
     * region : 44
     * regionName : Guangdong
     * status : success
     * timezone : Asia/Shanghai
     * zip :
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Location {
        @JsonProperty("as")
        public String as;
        @JsonProperty("city")
        public String city;
        @JsonProperty("country")
        public String country;
        @JsonProperty("countryCode")
        public String countryCode;
        @JsonProperty("isp")
        public String isp;
        @JsonProperty("lat")
        public double lat;
        @JsonProperty("lon")
        public double lon;
        @JsonProperty("org")
        public String org;
        @JsonProperty("query")
        public String query;
        @JsonProperty("region")
        public String region;
        @JsonProperty("regionName")
        public String regionName;
        @JsonProperty("status")
        public String status;
        @JsonProperty("timezone")
        public String timezone;
        @JsonProperty("zip")
        public String zip;
    }

    /**
     * errNum : 0
     * errMsg : success
     * retData : {"city":"深圳","pinyin":"shenzhen","citycode":"101280601","date":"16-04-01","time":"11:00","postCode":"518001","longitude":114.109,"latitude":22.544,"altitude":"40","weather":"阴","temp":"26","l_tmp":"20","h_tmp":"26","WD":"无持续风向","WS":"微风(<10km/h)","sunrise":"06:15","sunset":"18:38"}
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WeatherData {
        @JsonProperty("errNum")
        public int errNum;
        @JsonProperty("errMsg")
        public String errMsg;
        @JsonProperty("retData")
        public RetData retData;
        /**
         * city : 深圳
         * pinyin : shenzhen
         * citycode : 101280601
         * date : 16-04-01
         * time : 11:00
         * postCode : 518001
         * longitude : 114.109
         * latitude : 22.544
         * altitude : 40
         * weather : 阴
         * temp : 26
         * l_tmp : 20
         * h_tmp : 26
         * WD : 无持续风向
         * WS : 微风(<10km/h)
         * sunrise : 06:15
         * sunset : 18:38
         */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class RetData {
            @JsonProperty("city")
            public int city;
            @JsonProperty("pinyin")
            public String pinyin;
            @JsonProperty("citycode")
            public String citycode;
            @JsonProperty("date")
            public String date;
            @JsonProperty("time")
            public String time;
            @JsonProperty("postCode")
            public String postCode;
            @JsonProperty("longitude")
            public String longitude;
            @JsonProperty("latitude")
            public String latitude;
            @JsonProperty("altitude")
            public String altitude;
            @JsonProperty("weather")
            public String weather;
            @JsonProperty("temp")
            public String temp;
            @JsonProperty("l_tmp")
            public String l_tmp;
            @JsonProperty("h_tmp")
            public String h_tmp;
            @JsonProperty("WD")
            public String WD;
            @JsonProperty("WS")
            public String WS;
            @JsonProperty("sunrise")
            public String sunrise;
            @JsonProperty("sunset")
            public String sunset;
        }
    }

    /**
     * errNum : 0
     * errMsg : success
     * retData : {"city":"北京","cityid":"101010100","today":{"date":"2016-04-05","week":"星期二","curTemp":"21℃","aqi":"217","fengxiang":"南风","fengli":"3-4级","hightemp":"24℃","lowtemp":"11℃","type":"晴","index":[{"name":"感冒指数","code":"gm","index":"","details":"各项气象条件适宜，无明显降温过程，发生感冒机率较低。","otherName":""},{"code":"fs","details":"属中等强度紫外辐射天气，外出时应注意防护，建议涂擦SPF指数高于15，PA+的防晒护肤品。","index":"中等","name":"防晒指数","otherName":""},{"code":"ct","details":"建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。","index":"较舒适","name":"穿衣指数","otherName":""},{"code":"yd","details":"天气较好，但风力较大，推荐您进行室内运动，若在户外运动请注意避风保暖。","index":"较适宜","name":"运动指数","otherName":""},{"code":"xc","details":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。","index":"较适宜","name":"洗车指数","otherName":""},{"code":"ls","details":"天气不错，适宜晾晒。赶紧把久未见阳光的衣物搬出来吸收一下太阳的味道吧！","index":"适宜","name":"晾晒指数","otherName":""}]},"forecast":[{"date":"2016-04-06","week":"星期三","fengxiang":"无持续风向","fengli":"微风级","hightemp":"23℃","lowtemp":"12℃","type":"霾"},{"date":"2016-04-07","week":"星期四","fengxiang":"北风","fengli":"3-4级","hightemp":"23℃","lowtemp":"8℃","type":"晴"},{"date":"2016-04-08","week":"星期五","fengxiang":"南风","fengli":"3-4级","hightemp":"24℃","lowtemp":"11℃","type":"晴"},{"date":"2016-04-09","week":"星期六","fengxiang":"无持续风向","fengli":"微风级","hightemp":"26℃","lowtemp":"12℃","type":"晴"}],"history":[{"date":"2016-03-29","week":"星期二","aqi":"66","fengxiang":"无持续风向","fengli":"微风级","hightemp":"22℃","lowtemp":"7℃","type":"晴"},{"date":"2016-03-30","week":"星期三","aqi":"77","fengxiang":"无持续风向","fengli":"微风级","hightemp":"22℃","lowtemp":"7℃","type":"晴"},{"date":"2016-03-31","week":"星期四","aqi":"250","fengxiang":"南风","fengli":"3-4级","hightemp":"23℃","lowtemp":"13℃","type":"晴"},{"date":"2016-04-01","week":"星期五","aqi":"47","fengxiang":"北风","fengli":"3-4级","hightemp":"23℃","lowtemp":"11℃","type":"晴"},{"date":"2016-04-02","week":"星期六","aqi":"43","fengxiang":"北风","fengli":"3-4级","hightemp":"18℃","lowtemp":"8℃","type":"多云"},{"date":"2016-04-03","week":"星期天","aqi":"59","fengxiang":"无持续风向","fengli":"微风级","hightemp":"19℃","lowtemp":"6℃","type":"晴"},{"date":"2016-04-04","week":"星期一","aqi":"104","fengxiang":"无持续风向","fengli":"微风级","hightemp":"22℃","lowtemp":"8℃","type":"晴"}]}
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FourDay {
        @JsonProperty("errNum")
        public String errNum;
        @JsonProperty("errMsg")
        public String errMsg;
        @JsonProperty("retData")
        public RetData retData;
        /**
         * city : 北京
         * cityid : 101010100
         * today : {"date":"2016-04-05","week":"星期二","curTemp":"21℃","aqi":"217","fengxiang":"南风","fengli":"3-4级","hightemp":"24℃","lowtemp":"11℃","type":"晴","index":[{"name":"感冒指数","code":"gm","index":"","details":"各项气象条件适宜，无明显降温过程，发生感冒机率较低。","otherName":""},{"code":"fs","details":"属中等强度紫外辐射天气，外出时应注意防护，建议涂擦SPF指数高于15，PA+的防晒护肤品。","index":"中等","name":"防晒指数","otherName":""},{"code":"ct","details":"建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。","index":"较舒适","name":"穿衣指数","otherName":""},{"code":"yd","details":"天气较好，但风力较大，推荐您进行室内运动，若在户外运动请注意避风保暖。","index":"较适宜","name":"运动指数","otherName":""},{"code":"xc","details":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。","index":"较适宜","name":"洗车指数","otherName":""},{"code":"ls","details":"天气不错，适宜晾晒。赶紧把久未见阳光的衣物搬出来吸收一下太阳的味道吧！","index":"适宜","name":"晾晒指数","otherName":""}]}
         * forecast : [{"date":"2016-04-06","week":"星期三","fengxiang":"无持续风向","fengli":"微风级","hightemp":"23℃","lowtemp":"12℃","type":"霾"},{"date":"2016-04-07","week":"星期四","fengxiang":"北风","fengli":"3-4级","hightemp":"23℃","lowtemp":"8℃","type":"晴"},{"date":"2016-04-08","week":"星期五","fengxiang":"南风","fengli":"3-4级","hightemp":"24℃","lowtemp":"11℃","type":"晴"},{"date":"2016-04-09","week":"星期六","fengxiang":"无持续风向","fengli":"微风级","hightemp":"26℃","lowtemp":"12℃","type":"晴"}]
         * history : [{"date":"2016-03-29","week":"星期二","aqi":"66","fengxiang":"无持续风向","fengli":"微风级","hightemp":"22℃","lowtemp":"7℃","type":"晴"},{"date":"2016-03-30","week":"星期三","aqi":"77","fengxiang":"无持续风向","fengli":"微风级","hightemp":"22℃","lowtemp":"7℃","type":"晴"},{"date":"2016-03-31","week":"星期四","aqi":"250","fengxiang":"南风","fengli":"3-4级","hightemp":"23℃","lowtemp":"13℃","type":"晴"},{"date":"2016-04-01","week":"星期五","aqi":"47","fengxiang":"北风","fengli":"3-4级","hightemp":"23℃","lowtemp":"11℃","type":"晴"},{"date":"2016-04-02","week":"星期六","aqi":"43","fengxiang":"北风","fengli":"3-4级","hightemp":"18℃","lowtemp":"8℃","type":"多云"},{"date":"2016-04-03","week":"星期天","aqi":"59","fengxiang":"无持续风向","fengli":"微风级","hightemp":"19℃","lowtemp":"6℃","type":"晴"},{"date":"2016-04-04","week":"星期一","aqi":"104","fengxiang":"无持续风向","fengli":"微风级","hightemp":"22℃","lowtemp":"8℃","type":"晴"}]
         */

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class RetData {
            @JsonProperty("city")
            public String city;
            @JsonProperty("cityid")
            public String cityid;
            @JsonProperty("today")
            public TodayEntity today;
            /**
             * date : 2016-07-22
             * week : 星期二
             * curTemp : 21℃
             * aqi : 217
             * fengxiang : 南风
             * fengli : 3-4级
             * hightemp : 24℃
             * lowtemp : 11℃
             * type : 晴
             * index : [{"name":"感冒指数","code":"gm","index":"","details":"各项气象条件适宜，无明显降温过程，发生感冒机率较低。","otherName":""},{"code":"fs","details":"属中等强度紫外辐射天气，外出时应注意防护，建议涂擦SPF指数高于15，PA+的防晒护肤品。","index":"中等","name":"防晒指数","otherName":""},{"code":"ct","details":"建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。","index":"较舒适","name":"穿衣指数","otherName":""},{"code":"yd","details":"天气较好，但风力较大，推荐您进行室内运动，若在户外运动请注意避风保暖。","index":"较适宜","name":"运动指数","otherName":""},{"code":"xc","details":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。","index":"较适宜","name":"洗车指数","otherName":""},{"code":"ls","details":"天气不错，适宜晾晒。赶紧把久未见阳光的衣物搬出来吸收一下太阳的味道吧！","index":"适宜","name":"晾晒指数","otherName":""}]
             */
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class TodayEntity {
                @JsonProperty("date")
                public String date;
                @JsonProperty("week")
                public String week;
                @JsonProperty("curTemp")
                public String curTemp;
                @JsonProperty("aqi")
                public String aqi;
                @JsonProperty("fengxiang")
                public String fengxiang;
                @JsonProperty("fengli")
                public String fengli;
                @JsonProperty("hightemp")
                public String hightemp;
                @JsonProperty("lowtemp")
                public String lowtemp;
                @JsonProperty("type")
                public String type;
                @JsonProperty("index")
                public List<IndexEntity> index;

                /**
                 * name : 感冒指数
                 * code : gm
                 * index :
                 * details : 各项气象条件适宜，无明显降温过程，发生感冒机率较低。
                 * otherName :
                 */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class IndexEntity {
                    @JsonProperty("name")
                    public String name;
                    @JsonProperty("code")
                    public String code;
                    @JsonProperty("index")
                    public String index;
                    @JsonProperty("details")
                    public String details;
                    @JsonProperty("otherName")
                    public String otherName;
                }
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class ForecastEntity {
                @JsonProperty("date")
                public String date;
                @JsonProperty("week")
                public String week;
                @JsonProperty("fengxiang")
                public String fengxiang;
                @JsonProperty("fengli")
                public String fengli;
                @JsonProperty("hightemp")
                public String hightemp;
                @JsonProperty("lowtemp")
                public String lowtemp;
                @JsonProperty("type")
                public String type;
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class HistoryEntity {
                @JsonProperty("date")
                public String date;
                @JsonProperty("week")
                public String week;
                @JsonProperty("aqi")
                public String aqi;
                @JsonProperty("fengxiang")
                public String fengxiang;
                @JsonProperty("fengli")
                public String fengli;
                @JsonProperty("hightemp")
                public String hightemp;
                @JsonProperty("lowtemp")
                public String lowtemp;
                @JsonProperty("type")
                public String type;
            }
        }
    }
}
