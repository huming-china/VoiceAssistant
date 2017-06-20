package com.cloudring.magicsound.model;

/**
 * Created by zengpeijin on 2016/5/5.
 */
public class Music {
    private String service;
    private String text;
    private Semantic semantic;


    public Semantic getSemantic() {
        return semantic;
    }

    public void setSemantic(Semantic semantic) {
        this.semantic = semantic;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public  class Slots {
        private String artist;
        private String song;

        public String getSong() {
            return song;
        }

        public void setSong(String song) {
            this.song = song;
        }

        public void setArtist(String artist){
            this.artist = artist;
        }
        public String getArtist(){
            return this.artist;
        }

    }
    public class Semantic {
        private Slots slots;

        public void setSlots(Slots slots){
            this.slots = slots;
        }
        public Slots getSlots(){
            return this.slots;
        }

    }
}


