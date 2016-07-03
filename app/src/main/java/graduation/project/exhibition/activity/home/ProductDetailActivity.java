package graduation.project.exhibition.activity.home;

import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import graduation.project.exhibition.R;
import graduation.project.exhibition.activity.base.BaseActivity;
import graduation.project.exhibition.business.HttpRequest;
import graduation.project.exhibition.business.ImageUtil;
import graduation.project.exhibition.domain.Product;
import graduation.project.exhibition.domain.ProductDetail;
import graduation.project.exhibition.http.JsonCallback;
import graduation.project.exhibition.http.ListResponse;
import graduation.project.exhibition.ui.ObservableScrollView;
import graduation.project.exhibition.utils.DimenUtils;
import graduation.project.exhibition.utils.IntentUtils;
import graduation.project.exhibition.utils.MethodUtils;
import graduation.project.exhibition.utils.PhoneInfoUtils;
import graduation.project.exhibition.utils.ShareUtils;
import graduation.project.exhibition.utils.ToastUtils;
import mbanje.kurt.fabbutton.FabButton;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * 展品详细信息
 */
public class ProductDetailActivity extends BaseActivity implements View.OnClickListener {

    private String id;

    private ObservableScrollView root;
    private View bar;
    private ImageView banner;
    private LinearLayout comLayout;
    private View leftBtn;
    private View rightBtn;
    private View comBtn;
    private View topBtn;
    private TextView title;
    private ImageView star1;
    private ImageView star2;
    private ImageView star3;
    private ImageView star4;
    private ImageView star5;
    private TextView starCount;
    private TextView exhibitNo;
    private TextView addr;
    private TextView key1;
    private TextView key2;
    private TextView key3;
    private TextView key4;
    private TextView price;
    private TextView desc;
    private TextView special;
    private TextView barTitle;

    private String titleText = "展品详细信息";
    private Product product;
    private FabButton playBtn;
    private MediaPlayer mMediaPlayer;

    @Override
    protected int setViewId() {
        return R.layout.activity_exhibit_detail;
    }

    @Override
    protected void initView() {
        root = (ObservableScrollView) findViewById(R.id.sv_root);
        bar = findViewById(R.id.rl_bar);
        banner = (ImageView) findViewById(R.id.iv_banner);
        comLayout = (LinearLayout) findViewById(R.id.ll_company);
        leftBtn = findViewById(R.id.iv_left);
        rightBtn = findViewById(R.id.iv_right);
        topBtn = findViewById(R.id.tv_to_top);
        comBtn = findViewById(R.id.tv_company);
        playBtn = (FabButton) findViewById(R.id.fb_play);

        title = (TextView) findViewById(R.id.tv_title);
        star1 = (ImageView) findViewById(R.id.iv_star_1);
        star2 = (ImageView) findViewById(R.id.iv_star_2);
        star3 = (ImageView) findViewById(R.id.iv_star_3);
        star4 = (ImageView) findViewById(R.id.iv_star_4);
        star5 = (ImageView) findViewById(R.id.iv_star_5);
        starCount = (TextView) findViewById(R.id.tv_star_count);
        exhibitNo = (TextView) findViewById(R.id.tv_no);
        addr = (TextView) findViewById(R.id.tv_addr);
        key1 = (TextView) findViewById(R.id.tv_key_1);
        key2 = (TextView) findViewById(R.id.tv_key_2);
        key3 = (TextView) findViewById(R.id.tv_key_3);
        key4 = (TextView) findViewById(R.id.tv_key_4);
        price = (TextView) findViewById(R.id.tv_price);
        desc = (TextView) findViewById(R.id.tv_desc);
        special = (TextView) findViewById(R.id.tv_special);
        barTitle = (TextView) findViewById(R.id.tv_bar_title);
    }

    @Override
    protected void initData() {

        id = getIntent().getStringExtra("id");
        mMediaPlayer = new MediaPlayer();

        requestData();

        leftBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);
        topBtn.setOnClickListener(this);
        comBtn.setOnClickListener(this);


        final int statusBarHeight = PhoneInfoUtils.getStatusBarHeight(mActivity);
        setTranslucentBar(banner.getBackground());
        bar.setPadding(0, statusBarHeight, 0, 0);//防止遮挡

        root.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
                bar.setPadding(0, statusBarHeight + y, 0, 0);//防止遮挡
                if (y > DimenUtils.dp2px(mActivity, 200) - statusBarHeight) {
                    bar.setBackgroundColor(MethodUtils.getColor(scrollView, R.color.main_theme));
                    barTitle.setText(titleText);
                } else {
                    bar.setBackground(null);
                    barTitle.setText("");
                }

                View childView = root.getChildAt(0);
                int bottom = childView.getHeight() - root.getHeight() - DimenUtils.dp2px(mActivity, 50);
                if (y >= bottom) {//快滑动到底部
                    comLayout.setVisibility(View.VISIBLE);
                } else {
                    comLayout.setVisibility(View.GONE);
                }

            }
        });

        try {
            mMediaPlayer.setDataSource(HttpRequest.getFullUrl("/sound/" + id + ".amr"));
            mMediaPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                playBtn.setVisibility(View.VISIBLE);
            }
        });

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playBtn.setIcon(R.mipmap.ic_fab_play, mbanje.kurt.fabbutton.R.drawable.ic_fab_complete);
                playBtn.showProgress(false);
            }
        });

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMediaPlayer.isPlaying()) {
                    pausePlay();
                } else {
                    continuePlay();
                }

            }
        });

    }

    private void pausePlay() {
        mMediaPlayer.pause();
        playBtn.setIcon(R.mipmap.ic_fab_play, mbanje.kurt.fabbutton.R.drawable.ic_fab_complete);
        playBtn.showProgress(false);
    }

    private void continuePlay() {
        mMediaPlayer.start();
        playBtn.setIcon(R.mipmap.ic_fab_pause, mbanje.kurt.fabbutton.R.drawable.ic_fab_complete);
        playBtn.showProgress(true);
    }

    @Override
    protected void onPause() {
        pausePlay();
        super.onPause();
    }

    @Override
    protected void onStop() {
        mMediaPlayer.stop();
        super.onStop();
    }

    private void requestData() {

        HttpRequest.jsonRequest("/product?id=" + id, new JsonCallback<ListResponse<Product>>() {

            @Override
            public void onSuccess(Response<ListResponse<Product>> response) {
                super.onSuccess(response);
                List<Product> productList = response.getResponseData().getResponse();
                if (productList.size() > 0) {

                    product = productList.get(0);

                    ImageUtil.loadWebImage(banner, product.getPic());
                    titleText = product.getTitle();
                    title.setText(product.getTitle());

                    desc.setText(MethodUtils.replaceLineAndAddSpace(product.getText()));
                    price.setText("价格：" + product.getPrice());

                    exhibitNo.setText("展品编号：" + product.getComid() + product.getId());
                }
            }

        });

        HttpRequest.jsonRequest("/product_detail?id=" + id, new JsonCallback<ListResponse<ProductDetail>>() {
            @Override
            public void onSuccess(Response<ListResponse<ProductDetail>> response) {
                super.onSuccess(response);
                List<ProductDetail> list = response.getResponseData().getResponse();

                if (list != null && list.size() > 0) {
                    ProductDetail productDetail = list.get(0);

                    addr.setText(productDetail.getAddr());

                    String k1 = productDetail.getKey1();
                    String k2 = productDetail.getKey2();
                    String k3 = productDetail.getKey3();
                    String k4 = productDetail.getKey4();

                    key1.setVisibility(View.GONE);
                    key2.setVisibility(View.GONE);
                    key3.setVisibility(View.GONE);
                    key4.setVisibility(View.GONE);

                    if (!TextUtils.isEmpty(k1)) {
                        key1.setVisibility(View.VISIBLE);
                        key1.setText(k1);
                    }
                    if (!TextUtils.isEmpty(k2)) {
                        key2.setVisibility(View.VISIBLE);
                        key2.setText(k2);
                    }
                    if (!TextUtils.isEmpty(k3)) {
                        key3.setVisibility(View.VISIBLE);
                        key3.setText(k3);
                    }
                    if (!TextUtils.isEmpty(k4)) {
                        key4.setVisibility(View.VISIBLE);
                        key4.setText(k4);
                    }

                    star1.setImageResource(R.mipmap.img_star_on);
                    star2.setImageResource(R.mipmap.img_star_off);
                    star3.setImageResource(R.mipmap.img_star_off);
                    star4.setImageResource(R.mipmap.img_star_off);
                    star5.setImageResource(R.mipmap.img_star_off);

                    float starNum = Float.valueOf(productDetail.getStar());
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

                    special.setText(MethodUtils.replaceLineAndAddSpace(productDetail.getSpecial()));
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
                if (product != null) {
                    new ShareUtils(v, product.getTitle() + product.getText()).all();
                } else {
                    ToastUtils.toast("请等待数据加载完成！");
                }
                break;
            case R.id.tv_to_top://回到顶部
                root.smoothScrollTo(0, 0);
                break;
            case R.id.tv_company://跳转到公司页面
                if (product != null) {
                    IntentUtils.newIntent(this)
                            .setActivityClass(CompanyDetailActivity.class)
                            .putExtra("id", product.getComid())
                            .startActivity();
                } else {
                    ToastUtils.toast("请等待数据加载完成！");
                }
                break;
        }
    }

}
