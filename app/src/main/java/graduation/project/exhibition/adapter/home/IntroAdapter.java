package graduation.project.exhibition.adapter.home;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import graduation.project.exhibition.R;
import graduation.project.exhibition.business.ImageUtil;

/**
 * 会展中心介绍
 */
public class IntroAdapter extends PagerAdapter {

    private View[] mViews;

    public IntroAdapter(Context context) {

        View[] views = {
                View.inflate(context, R.layout.view_intro_desc, null),
                View.inflate(context, R.layout.view_intro_facilities, null),
                View.inflate(context, R.layout.view_intro_album, null)
        };

        ImageUtil.loadWebImage((ImageView) views[0].findViewById(R.id.iv_img_1), "/pics/home/intro/1.jpg");
        ImageUtil.loadWebImage((ImageView) views[0].findViewById(R.id.iv_img_2), "/pics/home/intro/2.jpg");

        ImageUtil.loadWebImage((ImageView) views[1].findViewById(R.id.iv_img_1), "/pics/home/intro/3.jpg");
        ImageUtil.loadWebImage((ImageView) views[1].findViewById(R.id.iv_img_2), "/pics/home/intro/4.jpg");
        ImageUtil.loadWebImage((ImageView) views[1].findViewById(R.id.iv_img_3), "/pics/home/intro/5.jpg");
        ImageUtil.loadWebImage((ImageView) views[1].findViewById(R.id.iv_img_4), "/pics/home/intro/6.jpg");

        ImageUtil.loadWebImage((ImageView) views[2].findViewById(R.id.iv_img_1), "/pics/home/intro/7.jpg");
        ImageUtil.loadWebImage((ImageView) views[2].findViewById(R.id.iv_img_2), "/pics/home/intro/8.jpg");
        ImageUtil.loadWebImage((ImageView) views[2].findViewById(R.id.iv_img_3), "/pics/home/intro/9.jpg");
        ImageUtil.loadWebImage((ImageView) views[2].findViewById(R.id.iv_img_4), "/pics/home/intro/10.jpg");
        ImageUtil.loadWebImage((ImageView) views[2].findViewById(R.id.iv_img_5), "/pics/home/intro/11.jpg");

        mViews = views;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViews[position]);
        return mViews[position];
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
