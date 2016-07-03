package graduation.project.exhibition.activity.home;

import android.support.v4.view.ViewPager;

import com.sevenheaven.segmentcontrol.SegmentControl;

import graduation.project.exhibition.R;
import graduation.project.exhibition.activity.base.BackActivity;
import graduation.project.exhibition.adapter.home.CooperationAdapter;

/**
 * 商务合作
 */
public class CooperationActivity extends BackActivity {
    private ViewPager mViewPager;
    private SegmentControl mSegmentHorzontal;
    private CooperationAdapter mViewPagerAdapter;

    @Override
    protected String initBackBar() {
        return "商务合作";
    }

    @Override
    protected int setViewId() {
        return R.layout.activity_cooperation;
    }

    @Override
    protected void initView() {
        super.initView();
        mSegmentHorzontal = (SegmentControl) findViewById(R.id.segment_control);
        mViewPager = (ViewPager) findViewById(R.id.vp_content);
    }

    @Override
    protected void initData() {
        super.initData();

        mViewPager.setAdapter(mViewPagerAdapter = new CooperationAdapter(getFragmentManager()));

        mSegmentHorzontal.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                mViewPager.setCurrentItem(index, true);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mSegmentHorzontal.setSelectedIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
