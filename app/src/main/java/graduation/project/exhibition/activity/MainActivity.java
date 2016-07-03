package graduation.project.exhibition.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import graduation.project.exhibition.R;
import graduation.project.exhibition.activity.base.SearchActivity;
import graduation.project.exhibition.activity.base.TitleBarActivity;
import graduation.project.exhibition.activity.home.SelectAreaActivity;
import graduation.project.exhibition.activity.setting.UserActivity;
import graduation.project.exhibition.adapter.MainPagerAdapter;
import graduation.project.exhibition.business.UpdateBusiness;
import graduation.project.exhibition.fragment.BaseFragment;
import graduation.project.exhibition.fragment.main.DiscoverFragment;
import graduation.project.exhibition.fragment.main.HomeFragment;
import graduation.project.exhibition.fragment.main.InfoFragment;
import graduation.project.exhibition.fragment.main.SettingFragment;
import graduation.project.exhibition.ui.callback.MultiClickListener;
import graduation.project.exhibition.ui.viewpager.IconIndicator;
import graduation.project.exhibition.ui.viewpager.NoScrollViewPager;
import graduation.project.exhibition.utils.IntentUtils;
import graduation.project.exhibition.utils.MethodUtils;
import graduation.project.exhibition.utils.QRCodeUtils;

/**
 * 主界面
 */
public class MainActivity extends TitleBarActivity implements View.OnClickListener {

    private NoScrollViewPager viewPager;
    private MainPagerAdapter pagerAdapter;
    private IconIndicator tabIndicator;
    private DrawerLayout slidingMenu;
    private SettingFragment mSettingFragment;
    private TextView username;
    private View toMainBtn;
    private View nameBtn;
    private View mapBtn;
    private View searchBtn;
    private View scanBtn;
    private View setBtn;
    private View eggBtn;
    private View exitBtn;

    @Override
    protected int setViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void beforeSetView() {
        setTranslucentBar(R.color.main_theme);
    }

    @Override
    protected void initView() {
        viewPager = (NoScrollViewPager) findViewById(R.id.vp_content);
        tabIndicator = (IconIndicator) findViewById(R.id.indicator);
        slidingMenu = (DrawerLayout) findViewById(R.id.dl_menu);

        initSlidingMenu();
    }


    @Override
    protected void initData() {

        UpdateBusiness.toUpdate(mActivity);

        BaseFragment[] fragmentList = {
                new HomeFragment(),
                new InfoFragment(),
                new DiscoverFragment(),
                mSettingFragment = new SettingFragment()};
        pagerAdapter = new MainPagerAdapter(getFragmentManager(), fragmentList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(fragmentList.length);//缓存页面数
        viewPager.addOnPageChangeListener(
                new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    }

                    @Override
                    public void onPageSelected(int position) {
                        doOnPageChange(position);
                    }


                    @Override
                    public void onPageScrollStateChanged(int state) {
                    }
                }
        );

        tabIndicator
                .bind(viewPager)
                .defaultSetting()
                .textColor(R.color.grey, R.color.main_theme)
                .addTab("展览馆",
                        R.mipmap.icon_main_home,
                        R.mipmap.icon_main_home_selected)
                .addTab("看资讯",
                        R.mipmap.icon_main_info,
                        R.mipmap.icon_main_info_selected)
                .addTab("周边活动",
                        R.mipmap.icon_main_discover,
                        R.mipmap.icon_main_discover_selected)
                .addTab("设置",
                        R.mipmap.icon_main_setting,
                        R.mipmap.icon_main_setting_selected)
                .load();

        doOnPageChange(0);//手动执行显示

        setDrawer();
    }

    /**
     * 设置页面切换事件
     */
    private void doOnPageChange(int position) {
        switch (position) {
            case 0:
                setToolbar("琶洲展览中心",
                        R.mipmap.btn_sliding_menu,
                        R.mipmap.img_scan_white);
                setListener(new SimpleClickListener() {
                    @Override
                    public void leftClick() {
                        //控制抽屉
                        if (!slidingMenu.isDrawerOpen(Gravity.LEFT)) {
                            slidingMenu.openDrawer(Gravity.LEFT);
                        } else {
                            slidingMenu.closeDrawers();
                        }
                    }

                    @Override
                    public void rightClick() {
                        QRCodeUtils.scan(mActivity);
                    }
                });
                break;
            case 1:
                setToolbar("看资讯");
                break;
            case 2://周边活动
                hideToolbar();
                break;
            case 3://设置
                hideToolbar();
                break;
        }
    }


    private void initSlidingMenu() {
        username = (TextView) slidingMenu.findViewById(R.id.tv_username);

        toMainBtn = slidingMenu.findViewById(R.id.ll_to_main);
        nameBtn = slidingMenu.findViewById(R.id.ll_change_name);
        mapBtn = slidingMenu.findViewById(R.id.ll_map);
        searchBtn = slidingMenu.findViewById(R.id.ll_search);
        scanBtn = slidingMenu.findViewById(R.id.ll_scan);
        setBtn = slidingMenu.findViewById(R.id.ll_setting);
        eggBtn = slidingMenu.findViewById(R.id.ll_egg);
        exitBtn = slidingMenu.findViewById(R.id.ll_exit);
    }

    /**
     * 设置侧边栏
     */
    public void setDrawer() {

        showUsername();

        toMainBtn.setOnClickListener(this);
        nameBtn.setOnClickListener(this);
        mapBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        scanBtn.setOnClickListener(this);
        setBtn.setOnClickListener(this);
        exitBtn.setOnClickListener(this);

        eggBtn.setOnClickListener(new MultiClickListener(3) {
            @Override
            public void onMultiClick(View v) {
                IntentUtils.newIntent(mActivity)
                        .setActivityClass(EasterEggActivity.class)
                        .startActivity();
            }

        });
    }

    /**
     * 显示用户名
     */
    private void showUsername() {
        username.setText(MethodUtils.getUserName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0x100://设置用户名
                mSettingFragment.showUsername();
                showUsername();
                break;
        }

    }

    @Override
    public void onBackPressed() {
        MethodUtils.exit(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_to_main://主页
                tabIndicator.select(0);
                break;
            case R.id.ll_change_name://修改用户信息
                IntentUtils.newIntent(mActivity)
                        .setActivityClass(UserActivity.class)
                        .requestCode(0x100)
                        .startActivity();
                break;
            case R.id.ll_map://展会地图
                IntentUtils.newIntent(mActivity)
                        .setActivityClass(SelectAreaActivity.class)
                        .startActivity();
                break;
            case R.id.ll_search://搜索
                IntentUtils.newIntent(mActivity)
                        .setActivityClass(SearchActivity.class)
                        .putExtra("search","home")
                        .startActivity();
                break;
            case R.id.ll_scan://扫描二维码
                QRCodeUtils.scan(mActivity);
                break;
            case R.id.ll_setting://设置页面
                tabIndicator.select(3);
                break;
            case R.id.ll_exit://退出
                MethodUtils.exit(mActivity);
                break;
        }
        slidingMenu.closeDrawers();
    }
}
