package com.cloudring.magicsound.model.vmodel;

public class VWeather extends VObject {

    public String date;// ": "2016-07-11",

    public String wind;// ": "无持续风向,微风",

    public String weather;// ": "大雨转中雨",

    public String temperature;// ": "26~30℃"

    public String area;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}