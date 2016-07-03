package graduation.project.exhibition.ui.callback;

import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 多次点击监听器
 */
public abstract class MultiClickListener implements OnClickListener {

    private final int times;
    long[] mHits;

    public MultiClickListener(int times) {
        this.times = times;
        mHits = new long[times];
    }

    @Override
    public void onClick(View v) {
        if (times > 1) {
            System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
            mHits[mHits.length - 1] = SystemClock.uptimeMillis();//获取离开机的时间
            //单击时间的间隔，以500毫秒为临界值
            if (mHits[0] >= (SystemClock.uptimeMillis() - 500)) {
                onMultiClick(v);

                //把数组置为空并重写初始化，为下一次做准备
                mHits = null;
                mHits = new long[times];
            }
        }
    }

    /**
     * 多次点击触发的监听器
     */
    public abstract void onMultiClick(View v);
}
