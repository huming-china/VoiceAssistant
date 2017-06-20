package com.cloudring.commonlib.utils;


import android.util.Log;

public class LampLog
{
	private static final String TAG = "Lamp";

	private static final boolean DEBUG = true;

	public static void d(String str)
	{
		if (DEBUG)
			Log.d(TAG, str);
	}

	public static void i(String str)
	{
		if (DEBUG)
			Log.i(TAG, str);
	}

	public static void e(String str)
	{
		if (DEBUG)
			Log.e(TAG, str);
	}

	public static void w(String str)
	{
		if (DEBUG)
			Log.w(TAG, str);
	}

	public static void e(String str, Throwable e)
	{
		if (DEBUG)
			Log.e(TAG, str, e);
	}
}
