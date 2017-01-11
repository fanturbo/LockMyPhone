package pub.war3.lockmyphone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 悬浮球
 */
public class FloatBall extends View {

    //是否在拖动
    private boolean isDrag;

    private Paint ballPaint;

    private Paint textPaint;

    private Bitmap bitmap;
    public int width = 100;
    public int height = 100;

    public FloatBall(Context context) {
        super(context);
        init();
    }

    public FloatBall(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FloatBall(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        ballPaint = new Paint();
        ballPaint.setColor(getResources().getColor(R.color.colorPrimary));
        ballPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setTextSize(48);
        textPaint.setColor(getResources().getColor(R.color.white));
        textPaint.setAntiAlias(true);
        textPaint.setFakeBoldText(true);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.fab_add);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(width / 2, height / 2, height / 2, ballPaint);
        canvas.drawBitmap(bitmap, width / 2 - bitmap.getWidth() / 2, height / 2 - bitmap.getHeight() / 2, null);
    }

    //设置当前移动状态
    public void setDragState(boolean isDrag) {
        this.isDrag = isDrag;
        invalidate();
    }
}
