package graduation.project.exhibition.adapter.home;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import graduation.project.exhibition.fragment.home.AdvertisementFragment;
import graduation.project.exhibition.fragment.home.ServiceFragment;

/**
 * 商务合作
 */
public class CooperationAdapter extends FragmentPagerAdapter {

    public CooperationAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return position == 0 ? new AdvertisementFragment() : new ServiceFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
