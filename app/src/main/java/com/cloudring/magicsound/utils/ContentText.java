package com.cloudring.magicsound.utils;


import java.util.Random;

/**
 * Created by zengpeijin on 2016/11/14.
 */

public class ContentText {
    static String []welcomes={"主人你好","主人你说","主人有什么吩咐","主人请说","主人我在呢"};
    public static String getWelcome(){
        Random rand = new Random();
        return welcomes[rand.nextInt(welcomes.length)];

    }



}
