package graduation.project.exhibition.fragment.main;

import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;

import graduation.project.exhibition.R;
import graduation.project.exhibition.fragment.BaseFragment;
import graduation.project.exhibition.adapter.info.InfoAdapter;
import graduation.project.exhibition.utils.DimenUtils;

/**
 * 资讯Fragment
 */
public class InfoFragment extends BaseFragment {

    /**
     * 外部整体的页面内容
     */
    private ViewPager mContentPager;
    private PagerSlidingTabStrip mContentStrip;
    private InfoAdapter mContentAdapter;

    public InfoFragment() {
        super(R.layout.fragment_info);
    }

    @Override
    public void initView() {
        mContentPager = (ViewPager) findViewById(R.id.vp_content);
        mContentStrip = (PagerSlidingTabStrip) findViewById(R.id.pst_indicator);
    }

    @Override
    public void initData() {
        mContentAdapter = new InfoAdapter(getChildFragmentManager());
        mContentPager.setAdapter(mContentAdapter);

        //setAdapter之后设置
        mContentStrip.setViewPager(mContentPager);
        mContentStrip.setTextSize(DimenUtils.dp2px(attachActivity, 12));
    }


}
