/**
 * Constants.java
 * com.ximalaya.ting.android.opensdk.test.constants
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ---------------------------------------
 *   		 2015-7-9 		chadwii
 *
 * Copyright (c) 2015, chadwii All Rights Reserved.
*/

package com.cloudring.magicsound.constants;

import com.cloudring.magicsound.R;
import com.cloudring.magicsound.model.Tips;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:Constants
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   chadwii
 * @version  
 * @Date	 2016-5-5
 *
 * @see 	 
 */
public class Constants
{
	public static List<Tips> tipsArray = new ArrayList<>();

	static {
		Tips tips1 = new Tips();

		tips1.title =R.string.voice_tips_title_call;//please calls
		tips1.tips = R.string.voice_tips_content_call;
		tips1.resId = R.drawable.icon_tips_call;

		Tips tips2 = new Tips();
		tips2.title =R.string.voice_tips_title_calendar;//manage events
		tips2.tips = R.string.voice_tips_content_calendar;
		tips2.resId = R.drawable.icon_tips_calendar;

		Tips tips3 = new Tips();
		tips3.title = R.string.voice_tips_title_remind;//manage alarms
		tips3.tips = R.string.voice_tips_content_remind;
		tips3.resId = R.drawable.icon_tips_remind;

		Tips tips4 = new Tips();
		tips4.title =R.string.voice_tips_title_weather;//check weather info
		tips4.tips = R.string.voice_tips_content_weather;
		tips4.resId = R.drawable.icon_tips_weather;

		Tips tips5 = new Tips();
		tips5.title = R.string.voice_tips_title_music;//play/search songs
		tips5.tips = R.string.voice_tips_content_music;
		tips5.resId = R.drawable.icon_tips_music;

		Tips tips6 = new Tips();
		tips6.title = R.string.voice_tips_title_control;//smart control
		tips6.tips = R.string.voice_tips_content_control;
		tips6.resId = R.drawable.icon_tips_control;

		Tips tips7 = new Tips();
		tips7.title =R.string.voice_tips_title_near;//search nearby
		tips7.tips =R.string.voice_tips_content_near;
		tips7.resId = R.drawable.icon_tips_near;

		Tips tips8 = new Tips();
		tips8.title =R.string.voice_tips_title_cooking;//search cooks
		tips8.tips = R.string.voice_tips_content_cooking;
		tips8.resId = R.drawable.icon_tips_cook;

		Tips tips9 = new Tips();
		tips9.title =R.string.voice_tips_title_app;//open apps
		tips9.tips = R.string.voice_tips_content_app;
		tips9.resId = R.drawable.icon_tips_app;

		Tips tips10 = new Tips();
		tips10.title = R.string.voice_tips_title_comic;//search fun
		tips10.tips = R.string.voice_tips_content_comic;
		tips10.resId = R.drawable.icon_tips_fun;

		Tips tips11 = new Tips();
		tips11.title = R.string.voice_tips_title_stock;//check stocks
		tips11.tips =R.string.voice_tips_content_stock;
		tips11.resId = R.drawable.icon_tips_stock;

		Tips tips12 = new Tips();
		tips12.title = R.string.voice_tips_title_stock;//search poem
		tips12.tips = R.string.voice_tips_content_stock;
		tips12.resId = R.drawable.icon_tips_poem;

		Tips tips13 = new Tips();
		tips13.title =R.string.voice_tips_title_talk;//free chating
		tips13.tips =R.string.voice_tips_content_talk;
		tips13.resId = R.drawable.icon_tips_chat;

		tipsArray.add(tips1);
		tipsArray.add(tips2);
		tipsArray.add(tips3);
		tipsArray.add(tips4);
		tipsArray.add(tips5);
		tipsArray.add(tips6);
		tipsArray.add(tips7);
		tipsArray.add(tips8);
		tipsArray.add(tips9);
		tipsArray.add(tips10);
		tipsArray.add(tips11);
		tipsArray.add(tips12);
		tipsArray.add(tips13);
	}
}

