package graduation.project.exhibition.adapter.info;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import graduation.project.exhibition.fragment.info.ActivitiesFragment;
import graduation.project.exhibition.fragment.info.InformationFragment;
import graduation.project.exhibition.fragment.info.RecommendedFragment;

/**
 * 看资讯页面数据适配器
 */
public class InfoAdapter extends FragmentPagerAdapter {

    private String[] pageTitles = {"推荐热点", "最新资讯", "展会活动"};

    public InfoAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles[position];
    }



    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0://推荐热点
                return new RecommendedFragment();
            case 1://最新资讯
                return new InformationFragment();
            case 2://展会活动
                return new ActivitiesFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return pageTitles.length;
    }
}
