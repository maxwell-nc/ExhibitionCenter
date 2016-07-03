package graduation.project.exhibition.activity.discover;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import graduation.project.exhibition.R;
import graduation.project.exhibition.activity.base.BaseActivity;
import graduation.project.exhibition.business.HttpRequest;
import graduation.project.exhibition.business.ImageUtil;
import graduation.project.exhibition.domain.Discover;
import graduation.project.exhibition.domain.DiscoverDetail;
import graduation.project.exhibition.domain.Ticket;
import graduation.project.exhibition.fragment.discover.QRCodeDialogFragment;
import graduation.project.exhibition.http.JsonCallback;
import graduation.project.exhibition.http.ListResponse;
import graduation.project.exhibition.ui.ObservableScrollView;
import graduation.project.exhibition.utils.DimenUtils;
import graduation.project.exhibition.utils.MethodUtils;
import graduation.project.exhibition.utils.PhoneInfoUtils;
import graduation.project.exhibition.utils.ShareUtils;
import graduation.project.exhibition.utils.ToastUtils;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * 发现页面详细信息
 */
public class DiscoverDetailActivity extends BaseActivity implements View.OnClickListener {

    private ObservableScrollView root;
    private View bar;
    private ImageView banner;
    private LinearLayout ticketLayout;
    private View leftBtn;
    private View rightBtn;
    private View ticketBtn;
    private View topBtn;
    private Discover discover;
    private TextView title;
    private ImageView star1;
    private ImageView star2;
    private ImageView star3;
    private ImageView star4;
    private ImageView star5;
    private TextView starCount;
    private TextView spendCount;
    private TextView spendMen;
    private TextView addr;
    private TextView express1;
    private TextView express2;
    private TextView express3;
    private TextView express4;
    private TextView discount;
    private TextView discountTime;
    private TextView special;
    private TextView text;
    private TextView barTitle;

    @Override
    protected int setViewId() {
        return R.layout.activity_discover_detail;
    }

    @Override
    protected void initView() {
        root = (ObservableScrollView) findViewById(R.id.sv_root);
        bar = findViewById(R.id.rl_bar);
        banner = (ImageView) findViewById(R.id.iv_banner);
        ticketLayout = (LinearLayout) findViewById(R.id.ll_ticket);
        leftBtn = findViewById(R.id.iv_left);
        rightBtn = findViewById(R.id.iv_right);
        topBtn = findViewById(R.id.tv_to_top);
        ticketBtn = findViewById(R.id.tv_ticket);

        title = (TextView) findViewById(R.id.tv_title);
        star1 = (ImageView) findViewById(R.id.iv_star_1);
        star2 = (ImageView) findViewById(R.id.iv_star_2);
        star3 = (ImageView) findViewById(R.id.iv_star_3);
        star4 = (ImageView) findViewById(R.id.iv_star_4);
        star5 = (ImageView) findViewById(R.id.iv_star_5);
        starCount = (TextView) findViewById(R.id.tv_star_count);
        spendCount = (TextView) findViewById(R.id.tv_spend);
        spendMen = (TextView) findViewById(R.id.tv_spend_men);
        addr = (TextView) findViewById(R.id.tv_addr);
        express1 = (TextView) findViewById(R.id.tv_express_1);
        express2 = (TextView) findViewById(R.id.tv_express_2);
        express3 = (TextView) findViewById(R.id.tv_express_3);
        express4 = (TextView) findViewById(R.id.tv_express_3);
        discount = (TextView) findViewById(R.id.tv_discount);
        discountTime = (TextView) findViewById(R.id.tv_discount_time);
        special = (TextView) findViewById(R.id.tv_special);
        text = (TextView) findViewById(R.id.tv_text);
        barTitle = (TextView) findViewById(R.id.tv_bar_title);
    }

    @Override
    protected void initData() {
        discover = (Discover) getIntent().getSerializableExtra("discover");

        showDefault();
        requestData();

        leftBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);
        topBtn.setOnClickListener(this);
        ticketBtn.setOnClickListener(this);


        final int statusBarHeight = PhoneInfoUtils.getStatusBarHeight(mActivity);
        setTranslucentBar(banner.getBackground());
        bar.setPadding(0, statusBarHeight, 0, 0);//防止遮挡

        root.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
                bar.setPadding(0, statusBarHeight + y, 0, 0);//防止遮挡
                if (y > DimenUtils.dp2px(mActivity, 200) - statusBarHeight) {
                    bar.setBackgroundColor(MethodUtils.getColor(scrollView, R.color.main_theme));
                    barTitle.setText(discover.getTitle());
                } else {
                    bar.setBackground(null);
                    barTitle.setText("");
                }

                View childView = root.getChildAt(0);
                int bottom = childView.getHeight() - root.getHeight() - DimenUtils.dp2px(mActivity, 50);
                if (y >= bottom) {//快滑动到底部
                    ticketLayout.setVisibility(View.VISIBLE);
                } else {
                    ticketLayout.setVisibility(View.GONE);
                }

            }
        });
    }

    private void showDefault() {
        ImageUtil.loadWebImage(banner, discover.getPic());
        title.setText(discover.getTitle());

        star1.setImageResource(R.mipmap.img_star_on);
        star2.setImageResource(R.mipmap.img_star_off);
        star3.setImageResource(R.mipmap.img_star_off);
        star4.setImageResource(R.mipmap.img_star_off);
        star5.setImageResource(R.mipmap.img_star_off);

        float starNum = Float.valueOf(discover.getStar());
        if (starNum >= 4.5) {
            star5.setImageResource(R.mipmap.img_star_on);
        }
        if (starNum >= 3.5) {
            star4.setImageResource(R.mipmap.img_star_on);
        }
        if (starNum >= 2.5) {
            star3.setImageResource(R.mipmap.img_star_on);
        }
        if (starNum >= 1.5) {
            star2.setImageResource(R.mipmap.img_star_on);
        }

        starCount.setText(starNum + "分");
        spendCount.setText("人均￥" + discover.getSpend());

    }

    private void requestData() {
        HttpRequest.jsonRequest("discover_detail?id=" + discover.getId(), new JsonCallback<ListResponse<DiscoverDetail>>() {
            @Override
            public void onSuccess(Response<ListResponse<DiscoverDetail>> response) {
                super.onSuccess(response);
                List<DiscoverDetail> list = response.getResponseData().getResponse();
                response.getResponseData().getResponse();

                if (list != null && list.size() > 0) {
                    DiscoverDetail discoverDetail = list.get(0);
                    spendMen.setText("月消费人次：" + discoverDetail.getCount() + "+");

                    addr.setText(discoverDetail.getAddr());

                    String express = discoverDetail.getExpress();
                    if (express.charAt(0) == '0') {
                        express1.setVisibility(View.GONE);
                    }
                    if (express.charAt(1) == '0') {
                        express2.setVisibility(View.GONE);
                    }
                    if (express.charAt(2) == '0') {
                        express3.setVisibility(View.GONE);
                    }
                    if (express.charAt(3) == '0') {
                        express4.setVisibility(View.GONE);
                    }

                    discount.setText(discoverDetail.getDiscount());
                    discountTime.setText(discoverDetail.getDistime());
                    special.setText(MethodUtils.replaceLineAndAddSpace(discoverDetail.getSpecial()));
                    text.setText(MethodUtils.replaceLineAndAddSpace(discoverDetail.getText()));
                }
            }
        });
    }

    /**
     * 生成优惠券
     */
    private void getTicket() {

        HttpRequest.jsonRequest("ticket?id=" + discover.getId(), new JsonCallback<ListResponse<Ticket>>() {
            @Override
            public void onSuccess(Response<ListResponse<Ticket>> response) {
                super.onSuccess(response);
                List<Ticket> list = response.getResponseData().getResponse();
                if (list != null && list.size() > 0) {
                    ToastUtils.toast("恭喜！你抢到了一张优惠券！");
                    QRCodeDialogFragment dialogFragment = new QRCodeDialogFragment();
                    dialogFragment.setAttachActivity(mActivity);
                    dialogFragment.setTicket(list.get(0));
                    dialogFragment.show(getFragmentManager(), "QRCode");
                } else {
                    ToastUtils.toast("来晚了！票券都被抢光了！");
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left://后退
                onBackPressed();
                break;
            case R.id.iv_right://分享
                if (discover != null) {
                    new ShareUtils(v, discover.getTitle() + discover.getText()).all();
                } else {
                    ToastUtils.toast("请等待数据加载完成！");
                }
                break;
            case R.id.tv_to_top://回到顶部
                root.smoothScrollTo(0, 0);
                break;
            case R.id.tv_ticket://门票
                getTicket();
                break;
        }
    }

}
