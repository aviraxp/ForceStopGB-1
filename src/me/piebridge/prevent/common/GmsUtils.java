package me.piebridge.prevent.common;

import android.content.Context;
import android.content.pm.PackageManager;

import java.util.concurrent.atomic.AtomicInteger;

import me.piebridge.prevent.framework.PreventLog;

/**
 * Created by thom on 15/7/28.
 */
public class GmsUtils {

    private static AtomicInteger gmsCounter = new AtomicInteger();
    public static final String GMS = "com.google.android.gms";
    public static final String GAPPS_PREFIX = "com.google.android.";

    private GmsUtils() {

    }

    public static boolean isGapps(PackageManager pm, String packageName) {
        return pm.checkSignatures(packageName, GMS) == PackageManager.SIGNATURE_MATCH ||
                (packageName.startsWith(GAPPS_PREFIX) && pm.getLaunchIntentForPackage(GAPPS_PREFIX) != null);
    }

    public static void increaseGmsCount(Context context, String packageName) {
        if (!GMS.equals(packageName) && isGapps(context.getPackageManager(), packageName)) {
            int gmsCount = gmsCounter.incrementAndGet();
            PreventLog.v("increase gms reference: " + gmsCount + ", packageName: " + packageName);
        }
    }

    public static int decreaseGmsCount(Context context, String packageName) {
        if (!GMS.equals(packageName) && isGapps(context.getPackageManager(), packageName)) {
            int gmsCount = gmsCounter.decrementAndGet();
            PreventLog.v("decrease reference: " + gmsCount + ", packageName: " + packageName);
            return gmsCount;
        } else {
            return gmsCounter.get();
        }
    }

}
