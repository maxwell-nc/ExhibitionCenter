package graduation.project.exhibition.activity.info;

import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import graduation.project.exhibition.R;
import graduation.project.exhibition.activity.base.BackActivity;
import graduation.project.exhibition.business.HttpRequest;
import graduation.project.exhibition.business.ImageUtil;
import graduation.project.exhibition.domain.ActivitiesDetail;
import graduation.project.exhibition.http.JsonCallback;
import graduation.project.exhibition.http.ListResponse;
import graduation.project.exhibition.utils.MethodUtils;
import graduation.project.exhibition.utils.ShareUtils;
import graduation.project.exhibition.utils.ToastUtils;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * 活动详情
 */
public class ActivitiesDetailActivity extends BackActivity {

    private ActivitiesDetail activitiesDetail;
    private TextView actTitle;
    private TextView shortText;
    private TextView tap1;
    private TextView tap2;
    private ImageView imageView;
    private TextView time;
    private TextView addr;
    private TextView text;
    private TextView price;

    @Override
    protected String initBackBar() {
        return "活动详情";
    }

    @Override
    protected int setViewId() {
        return R.layout.activity_activities_detail;
    }

    @Override
    protected void initView() {
        actTitle = (TextView) findViewById(R.id.item_title);
        shortText = (TextView) findViewById(R.id.item_short);
        tap1 = (TextView) findViewById(R.id.item_state_1);
        tap2 = (TextView) findViewById(R.id.item_state_2);
        imageView = (ImageView) findViewById(R.id.item_img);
        time = (TextView) findViewById(R.id.item_time);
        addr = (TextView) findViewById(R.id.item_addr);
        text = (TextView) findViewById(R.id.item_text);
        price = (TextView) findViewById(R.id.item_price);

        setRightBtn(R.mipmap.btn_share);
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activitiesDetail != null) {
                    new ShareUtils(v, activitiesDetail.getTitle() + activitiesDetail.getText()).all();
                } else {
                    ToastUtils.toast("请等待加载完成！");
                }
            }
        });
    }

    @Override
    protected void initData() {
        String id = getIntent().getStringExtra("id");
        requestData(id);
    }

    private void requestData(String id) {
        HttpRequest.jsonRequest("activities_details?id=" + id, new JsonCallback<ListResponse<ActivitiesDetail>>() {
            @Override
            public void onSuccess(Response<ListResponse<ActivitiesDetail>> response) {
                super.onSuccess(response);
                activitiesDetail = response.getResponseData().getResponse().get(0);
                actTitle.setText(MethodUtils.replaceLine(activitiesDetail.getTitle()));

                String fullText = MethodUtils.replaceLineAndAddSpace(activitiesDetail.getText());
                shortText.setText(fullText);

                String tag1Text = activitiesDetail.getTag1();
                if (!TextUtils.isEmpty(tag1Text)) {
                    tap1.setText(tag1Text);
                    tap1.setBackground(new ColorDrawable(MethodUtils.getColor(tap1, R.color.orange)));
                }
                String tap2Text = activitiesDetail.getTag2();
                if (!TextUtils.isEmpty(tap2Text)) {
                    tap2.setText(tap2Text);
                    tap2.setBackground(new ColorDrawable(MethodUtils.getColor(tap2, R.color.green_blue)));
                }

                ImageUtil.loadWebImage(imageView, activitiesDetail.getPic());
                time.setText(activitiesDetail.getTime());
                addr.setText(activitiesDetail.getAddr());
                text.setText(fullText);
                price.setText(MethodUtils.replaceLine(activitiesDetail.getPrice()));

            }
        });
    }
}
