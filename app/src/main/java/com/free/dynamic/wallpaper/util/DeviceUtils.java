package com.free.dynamic.wallpaper.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.free.dynamic.wallpaper.DynamicApplication;


/**
 * 工具类
 */
public class DeviceUtils {

    //获取navigationbar高度
    private static int getNavigationBarHeight() {
        Resources resources = DynamicApplication.Companion.getContext().getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        Log.v("dbw", "Navi height:" + height);
        return height;
    }

    //弹出提示界面
    private static void showTip(Activity activity, int contentID) {
//        Intent intent = new Intent(activity, PermissionTipActivity.class);
//        intent.putExtra("permission_tip", activity.getResources().getString(contentID));
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        activity.startActivity(intent);
    }

    public static int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
