package pub.war3.lockmyphone.util;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;


import java.lang.reflect.Field;

/**
 * Created by turbo on 2016/1/11.
 * 悬浮窗管理类
 */
public class ViewManager {

    static final int margin = 15;
    private FloatBall floatBall;
    private WindowManager windowManager;

    private static ViewManager manager;
    private Context mContext;
    private LayoutParams mLayoutParams;
    private View mView;
    private LayoutParams mFloatBallParams;
    //快捷悬浮小球是否显示
    public boolean isShowing;
    //统计点击屏幕次数
    public static int mClickNum = 0;


    //私有化构造函数
    private ViewManager(Context context) {
        this.mContext = context;
        init();
    }

    //获取ViewManager实例
    public static ViewManager getInstance(Context context) {
        if (manager == null) {
            manager = new ViewManager(context);
        }
        return manager;
    }

    public void init() {
        windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        floatBall = new FloatBall(mContext);
        View.OnTouchListener touchListener = new View.OnTouchListener() {
            float startX;
            float startY;
            float tempX;
            float tempY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getRawX();
                        startY = event.getRawY();

                        tempX = event.getRawX();
                        tempY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float x = event.getRawX() - startX;
                        float y = event.getRawY() - startY;
                        //计算偏移量，刷新视图
                        mFloatBallParams.x += x;
                        mFloatBallParams.y += y;
                        floatBall.setDragState(true);
                        windowManager.updateViewLayout(floatBall, mFloatBallParams);
                        startX = event.getRawX();
                        startY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        //判断松手时View的横坐标是靠近屏幕哪一侧，将View移动到依靠屏幕
                        float endX = event.getRawX();
                        float endY = event.getRawY();
                        if (endX < getScreenWidth() / 2) {
                            endX = margin;
                        } else {
                            endX = getScreenWidth() - floatBall.width - margin;
                        }
                        mFloatBallParams.x = (int) endX;
                        floatBall.setDragState(false);
                        windowManager.updateViewLayout(floatBall, mFloatBallParams);
                        //如果初始落点与松手落点的坐标差值超过6个像素，则拦截该点击事件
                        //否则继续传递，将事件交给OnClickListener函数处理
                        if (Math.abs(endX - tempX) > 6 && Math.abs(endY - tempY) > 6) {
                            return true;
                        }
                        break;
                }
                return false;
            }
        };
        OnClickListener clickListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                showEmpty();
                if ("0".equals(SPUtils.getAutoStatus(mContext))) {
                    AppManager.getAppManager().finishAllActivity();
                }
            }
        };
        floatBall.setOnTouchListener(touchListener);
        floatBall.setOnClickListener(clickListener);
    }

    //显示浮动小球
    public void showFloatBall() {
        if (mFloatBallParams == null) {
            mFloatBallParams = new LayoutParams();
            mFloatBallParams.width = floatBall.width;
            mFloatBallParams.height = floatBall.height - getStatusHeight();
            mFloatBallParams.gravity = Gravity.TOP | Gravity.LEFT;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mFloatBallParams.type = WindowManager.LayoutParams.TYPE_TOAST;
            } else {
                mFloatBallParams.type = WindowManager.LayoutParams.TYPE_PHONE;
            }
            mFloatBallParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCH_MODAL;
            mFloatBallParams.format = PixelFormat.RGBA_8888;
        }
        windowManager.addView(floatBall, mFloatBallParams);
        //最初是位置设置为左上角是为了方便计算，小球最开始显示的位置设置右下角
        mFloatBallParams.x = getScreenWidth() - margin - floatBall.width;
        mFloatBallParams.y = getScreenHeight() / 2;
        windowManager.updateViewLayout(floatBall, mFloatBallParams);
        isShowing = true;
    }

    //显示空白透明界面
    public void showEmpty() {
        if (mLayoutParams == null) {
            mLayoutParams = new LayoutParams();
            mLayoutParams.width = getScreenWidth();
            mLayoutParams.gravity = Gravity.BOTTOM;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
            } else {
                mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
            }
            mLayoutParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCH_MODAL;
            mLayoutParams.format = PixelFormat.RGBA_8888;
            if (SPUtils.getCoverStatus(mContext)) {
                mLayoutParams.height = getScreenHeight();
            } else {
                mLayoutParams.height = getScreenHeight() - getStatusHeight();
            }
        }
        mView = new ImageView(mContext);
        mView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //根据消失规则来隐藏
                mClickNum++;
                if (mClickNum >= Integer.parseInt(SPUtils.getClickNum(mContext))) {
                    hideDrinkAnim();
                    mClickNum = 0;
                }
            }
        });
        windowManager.addView(mView, mLayoutParams);
    }

    //隐藏
    public void hideDrinkAnim() {
        if (mView != null) {
            try {
                windowManager.removeView(mView);
            } catch (IllegalArgumentException e) {
            }
        }
    }

    //隐藏悬浮球
    public void hideFloatBall() {
        if (floatBall != null) {
            try {
                windowManager.removeView(floatBall);
                isShowing = false;
            } catch (IllegalArgumentException e) {
            }
        }
    }

    //获取屏幕宽度
    public int getScreenWidth() {
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        return point.x;
    }

    //获取屏幕高度
    public int getScreenHeight() {
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        return point.y;
    }

    //获取状态栏高度
    public int getStatusHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object object = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = (Integer) field.get(object);
            return mContext.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            return 0;
        }
    }
}
