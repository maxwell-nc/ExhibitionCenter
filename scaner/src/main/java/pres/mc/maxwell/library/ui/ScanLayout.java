package pres.mc.maxwell.library.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.widget.FrameLayout;

/**
 * 扫描视图
 */
public class ScanLayout extends FrameLayout {

    private SurfaceView surfaceView;

    public SurfaceView getSurfaceView() {
        return surfaceView;
    }

    public ScanLayout(Context context) {
        super(context);
        init(context);
    }

    public ScanLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScanLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        FrameLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);

        surfaceView = new SurfaceView(context);
        surfaceView.setLayoutParams(params);
        this.addView(surfaceView);
    }

}
