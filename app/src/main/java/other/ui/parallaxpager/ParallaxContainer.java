package other.ui.parallaxpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import graduation.project.exhibition.R;
import graduation.project.exhibition.ui.viewpager.FixedSpeedScroller;


/**
 * 視差滾動
 * 感谢https://github.com/prolificinteractive/ParallaxPager
 */
public class ParallaxContainer extends FrameLayout {

    private String TAG = "ParallaxContainer";

    private List<View> parallaxViews = new ArrayList<>();
    private ViewPager viewPager;
    private int pageCount = 0;
    private int containerWidth;
    private boolean isLooping = false;
    private ParallaxPagerAdapter adapter;

    Context context;
    public ViewPager.OnPageChangeListener mCommonPageChangeListener;
    private List<View> viewlist = new ArrayList<>();
    public int currentPosition = 0;
    private FixedSpeedScroller mFixedSpeedScroller;

    public ParallaxContainer(Context context) {
        super(context);
        init(context);
    }

    public ParallaxContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ParallaxContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void init(Context context) {
        this.context = context;
        adapter = new ParallaxPagerAdapter(context);
    }

    public boolean prePager() {
        if (currentPosition == 0) {
            return false;
        }
        viewPager.setCurrentItem(currentPosition - 1, true);
        return true;
    }

    public void nextPager() {
        if (currentPosition == viewPager.getAdapter().getCount()) {
            return;
        }
        viewPager.setCurrentItem(currentPosition + 1, true);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        containerWidth = getMeasuredWidth();
        if (viewPager != null) {
            mCommonPageChangeListener.onPageScrolled(viewPager.getCurrentItem(), 0, 0);
        }
        super.onWindowFocusChanged(hasFocus);
    }


    public void setupChildren(LayoutInflater inflater, int... childIds) {
        if (getChildCount() > 0) {
            throw new RuntimeException("setupChildren should only be called once when ParallaxContainer is empty");
        }

        ParallaxLayoutInflater parallaxLayoutInflater = new ParallaxLayoutInflater(
                inflater, getContext());

        for (int childId : childIds) {
            View view = parallaxLayoutInflater.inflate(childId, this);
            viewlist.add(view);
        }

        pageCount = getChildCount();
        for (int i = 0; i < pageCount; i++) {
            View view = getChildAt(i);
            addParallaxView(view, i);
        }


        adapter.setCount(pageCount);

        viewPager = new ViewPager(getContext());
        viewPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        viewPager.setId(R.id.parallax_pager);
        attachOnPageChangeListener();
        viewPager.setAdapter(adapter);

        mFixedSpeedScroller = new FixedSpeedScroller(viewPager.getContext(), new OvershootInterpolator(0.6F));
        mFixedSpeedScroller.bindViewPager(viewPager);

        addView(viewPager, 0);
    }

    protected void attachOnPageChangeListener() {
        mCommonPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
                Log.v(TAG, "onPageScrollStateChanged" + state);
                switch (state) {
                    case 1:
                        isEnd = false;
                        break;
                }
            }

            boolean isleft = false;

            @Override
            public void onPageScrolled(int pageIndex, float offset, int offsetPixels) {
                //				Log.v(TAG, "onPageScrolled" + pageIndex + "  offset" + offset + "   offsetPixels" + offsetPixels);

                if (offsetPixels < 10) {
                    isleft = false;
                }

                if (pageCount > 0) {
                    pageIndex = pageIndex % pageCount;
                }


                ParallaxViewTag tag;
                for (View view : parallaxViews) {
                    tag = (ParallaxViewTag) view.getTag(R.id.parallax_view_tag);
                    if (tag == null) {
                        continue;
                    }

                    if ((pageIndex == tag.index - 1 || (isLooping && (pageIndex == tag.index
                            - 1 + pageCount)))
                            && containerWidth != 0) {

                        // make visible
                        view.setVisibility(VISIBLE);

                        // slide in from right
                        view.setTranslationX((containerWidth - offsetPixels) * tag.xIn);

                        // slide in from top
                        view.setTranslationY(0 - (containerWidth - offsetPixels) * tag.yIn);

                        // fade in
                        view.setAlpha(1.0f - (containerWidth - offsetPixels) * tag.alphaIn / containerWidth);

                    } else if (pageIndex == tag.index) {

                        // make visible
                        view.setVisibility(VISIBLE);

                        // slide out to left
                        view.setTranslationX(0 - offsetPixels * tag.xOut);

                        // slide out to top
                        view.setTranslationY(0 - offsetPixels * tag.yOut);

                        // fade out
                        view.setAlpha(1.0f - offsetPixels * tag.alphaOut / containerWidth);

                    } else {
                        view.setVisibility(GONE);
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                Log.v(TAG, "onPageSelected" + position);
                currentPosition = position;
            }
        };
        viewPager.setOnPageChangeListener(mCommonPageChangeListener);
    }

    boolean isEnd = false;

    private void addParallaxView(View view, int pageIndex) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0, childCount = viewGroup.getChildCount(); i < childCount; i++) {
                addParallaxView(viewGroup.getChildAt(i), pageIndex);
            }
        }

        ParallaxViewTag tag = (ParallaxViewTag) view.getTag(R.id.parallax_view_tag);
        if (tag != null) {
            tag.index = pageIndex;
            parallaxViews.add(view);
        }
    }
}
