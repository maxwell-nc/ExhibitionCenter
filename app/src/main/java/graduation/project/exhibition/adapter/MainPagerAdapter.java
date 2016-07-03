package graduation.project.exhibition.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;


/**
 * 主页
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    private final Fragment[] fragmentList;

    public MainPagerAdapter(FragmentManager fm, Fragment[] fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList[position];
    }

    @Override
    public int getCount() {
        return fragmentList.length;
    }

}
