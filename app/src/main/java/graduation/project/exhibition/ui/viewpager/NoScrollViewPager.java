package graduation.project.exhibition.ui.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 无法左右滑动的ViewPager 
 */
public class NoScrollViewPager extends ViewPager {

	public NoScrollViewPager(Context context) {
		super(context);
	}

	public NoScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	
	/**
	 * 禁止拦截触摸事件
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		//return super.onInterceptTouchEvent(ev);
		return false;
	}
	
	
	/**
	 * 禁止响应触摸事件
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		//return super.onTouchEvent(ev);
		return false;
	}
}
