package pres.mc.maxwell.library.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import pres.mc.maxwell.library.R;

/**
 * 扫描提示框
 */
public class OverlayView extends View {

    //属性
    private int boundsLeft;
    private int boundsTop;
    private int boundsWidth;
    private int boundsHeight;
    private int boundsMarginTop;

    private int cornerLength;
    private int cornerStrokeWidth;
    private int cornerColor;

    private int scanLineColor;
    private int scanLineHeight;
    private int scanLineSpeed;

    private int hintColor;
    private int hintSize;
    private String hintText;
    private int hintMarginTop;

    // 扫描线移动的y
    private int scanLineTop;

    private int viewWidth;
    private int viewHeight;

    private static final long ANIMATION_DELAY = 100L;

    public OverlayView(Context context) {
        this(context, null, 0);
    }

    public OverlayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OverlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.OverlayView, defStyleAttr, 0);

        boundsLeft = (int) array.getDimension(R.styleable.OverlayView_bounds_left, -1);
        boundsTop = (int) array.getDimension(R.styleable.OverlayView_bounds_top, -1);
        boundsWidth = (int) array.getDimension(R.styleable.OverlayView_bounds_width, -1);
        boundsHeight = (int) array.getDimension(R.styleable.OverlayView_bounds_height, -1);
        boundsMarginTop = (int) array.getDimension(R.styleable.OverlayView_bounds_margin_top, 0);

        cornerLength = (int) array.getDimension(R.styleable.OverlayView_corner_length, 0);
        cornerStrokeWidth = (int) array.getDimension(R.styleable.OverlayView_corner_stroke_width, 0);
        cornerColor = array.getColor(R.styleable.OverlayView_corner_color, 0);

        scanLineColor = array.getColor(R.styleable.OverlayView_scan_line_color, 0);
        scanLineHeight = (int) array.getDimension(R.styleable.OverlayView_scan_line_height, 0);
        scanLineSpeed = array.getInt(R.styleable.OverlayView_scan_line_speed, 0);

        hintColor = array.getColor(R.styleable.OverlayView_hint_color, 0);
        hintSize = (int) array.getDimension(R.styleable.OverlayView_hint_size, 0);
        hintText = array.getString(R.styleable.OverlayView_hint_text);
        hintMarginTop = (int) array.getDimension(R.styleable.OverlayView_hint_margin_top, 0);

    }

    public void onDraw(Canvas canvas) {

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Rect frame;
        if (boundsWidth < 0 || boundsHeight < 0) {//没设置时候居中显示
            boundsLeft = viewWidth / 4;
            boundsWidth = viewWidth / 2;
            boundsTop = (viewHeight - boundsWidth) / 2;
            boundsHeight = boundsWidth;
        }
        int top = boundsTop + boundsMarginTop;
        frame = new Rect(boundsLeft, top, boundsLeft + boundsWidth, top + boundsHeight);

        drawFrameBounds(canvas, paint, frame);
        drawScanLight(canvas, paint, frame);
        drawHintText(canvas, paint, frame);

        //刷新界面
        postInvalidateDelayed(ANIMATION_DELAY,
                frame.left, frame.top,
                frame.right, frame.bottom);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
    }

    /**
     * 绘制线框
     */
    private void drawFrameBounds(Canvas canvas, Paint paint, Rect frame) {

        paint.setColor(cornerColor);
        paint.setStyle(Paint.Style.FILL);

        int corWidth = cornerStrokeWidth;
        int corLength = cornerLength;

        // 左上角
        canvas.drawRect(frame.left - corWidth, frame.top, frame.left, frame.top
                + corLength, paint);
        canvas.drawRect(frame.left - corWidth, frame.top - corWidth, frame.left
                + corLength, frame.top, paint);

        // 右上角
        canvas.drawRect(frame.right, frame.top, frame.right + corWidth,
                frame.top + corLength, paint);
        canvas.drawRect(frame.right - corLength, frame.top - corWidth,
                frame.right + corWidth, frame.top, paint);


        //int bottom = (frame.right - frame.left) + frame.top;
        int bottom = frame.bottom;

        // 左下角
        canvas.drawRect(frame.left - corWidth, bottom - corLength,
                frame.left, bottom, paint);
        canvas.drawRect(frame.left - corWidth, bottom, frame.left
                + corLength, bottom + corWidth, paint);

        // 右下角
        canvas.drawRect(frame.right, bottom - corLength, frame.right
                + corWidth, bottom, paint);
        canvas.drawRect(frame.right - corLength, bottom, frame.right
                + corWidth, bottom + corWidth, paint);
    }


    /**
     * 绘制移动扫描线
     */
    private void drawScanLight(Canvas canvas, Paint paint, Rect frame) {

        paint.setColor(scanLineColor);

        if (scanLineTop == 0) {
            scanLineTop = frame.top;
        }

        int bottom = (frame.right - frame.left) + frame.top;
        if (scanLineTop >= bottom) {
            scanLineTop = frame.top;
        } else {
            scanLineTop += scanLineSpeed;
        }
        Rect scanRect = new Rect(frame.left, scanLineTop, frame.right,
                scanLineTop + scanLineHeight);

        canvas.drawRect(scanRect, paint);
    }


    /**
     * 绘制提示文字
     */
    private void drawHintText(Canvas canvas, Paint paint, Rect frame) {

        paint.setColor(hintColor);
        paint.setTextSize(hintSize);

        int textWidth = (int) paint.measureText(hintText);

        int y = frame.bottom + hintMarginTop;
        int x = frame.centerX() - textWidth / 2;

        canvas.drawText(hintText, x, y, paint);

    }
}
