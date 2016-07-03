package graduation.project.exhibition.ui.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * ViewPager速度可控滚动器
 */
public class FixedSpeedScroller extends Scroller {

    private int mDuration = 700;// 滑动速度

    public FixedSpeedScroller(Context context) {
        super(context);
    }

    public FixedSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    public void setmDuration(int time) {
        mDuration = time;
    }

    /**
     * 修改切换速度
     */
    public void bindViewPager(ViewPager viewPager){
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            field.set(viewPager, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


