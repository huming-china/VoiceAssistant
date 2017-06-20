/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fge.voice.util;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;

/**
 * Utility class to hold wake lock in app.
 */
public class VoicesWakeLock {

    private static PowerManager.WakeLock sCpuWakeLock;

    public static void createPartialWakeLock(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        sCpuWakeLock =pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK|PowerManager.ON_AFTER_RELEASE, "voices");
        //pm.wakeUp(100);


        sCpuWakeLock.setReferenceCounted(false);
    }

    public static void acquire(Context context) {
        if (sCpuWakeLock != null) {
            sCpuWakeLock.acquire();
            return;
        }
        createPartialWakeLock(context);
        sCpuWakeLock.acquire();
    }



    public static void releaseLock() {
        if (sCpuWakeLock != null) {
            sCpuWakeLock.release();
            sCpuWakeLock = null;
        }
    }
    public static void wakeUpAndUnlock(Context context) {
        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
        //解锁
        kl.disableKeyguard();
        //获取电源管理器对象
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
        //点亮屏幕
        wl.acquire();
        //释放
        wl.release();
    }
}
