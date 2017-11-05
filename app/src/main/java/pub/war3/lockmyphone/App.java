package pub.war3.lockmyphone;

import android.app.Application;
import android.content.Context;
import android.provider.Settings.Secure;


import com.tencent.bugly.crashreport.CrashReport;
import cn.jpush.android.api.JPushInterface;

public class App extends Application {

    private static App mContext;
    private static String AndroidId;
    private static final String TAG = "Tinker.SampleApp";

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        AndroidId = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
        initBugly();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(mContext);
    }

    /**
     * 初始化bugly
     */
    private void initBugly() {
        CrashReport.initCrashReport(mContext, BuildConfig.BUGLY_ID, BuildConfig.DEBUG);
    }

    /**
     * 获得全局上下文
     *
     * @return
     */
    public static App getContext() {
        return mContext;
    }

    /**
     * 获取Android Id
     *
     * @return
     */
    public static String getAndroidId() {
        return AndroidId;
    }
}
