package graduation.project.exhibition.activity.setting;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import graduation.project.exhibition.R;
import graduation.project.exhibition.activity.base.BackActivity;
import graduation.project.exhibition.adapter.setting.FeedbackAdapter;
import graduation.project.exhibition.business.HttpRequest;
import graduation.project.exhibition.domain.Feedback;
import graduation.project.exhibition.http.JsonCallback;
import graduation.project.exhibition.http.ListResponse;
import graduation.project.exhibition.utils.MethodUtils;
import graduation.project.exhibition.utils.ToastUtils;
import pres.nc.maxwell.asynchttp.callback.impl.StringCallback;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * 意见反馈
 */
public class FeedbackActivity extends BackActivity {

    protected RecyclerView mRecyclerView;
    private FeedbackAdapter mRecyclerViewAdapter;
    private EditText sendText;
    private TextView sendBtn;
    private View inputLayout;

    @Override
    protected String initBackBar() {
        return "意见反馈";
    }

    @Override
    protected int setViewId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initView() {
        super.initView();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        sendText = (EditText) findViewById(R.id.et_send);
        sendBtn = (TextView) findViewById(R.id.tv_send);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mRecyclerViewAdapter = new FeedbackAdapter());

        inputLayout = findViewById(R.id.ll_input);

        final View decorView = getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                int screenHeight = decorView.getRootView().getHeight();
                int highDiff = screenHeight - rect.bottom;
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) inputLayout.getLayoutParams();
                params.setMargins(0, 0, 0, highDiff);
                inputLayout.requestLayout();
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();

        requestData();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFeedback();
            }
        });
        sendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toBottom();
            }
        });
    }

    private void requestData() {
        HttpRequest.jsonRequestWithoutCache("checkFeed?code=" + MethodUtils.getDrvCode(), new JsonCallback<ListResponse<Feedback>>() {

            @Override
            public void onSuccess(Response<ListResponse<Feedback>> response) {
                super.onSuccess(response);
                List<Feedback> list = response.getResponseData().getResponse();
                mRecyclerViewAdapter.setContentData(list);
            }

            @Override
            public void onAfter(Response<ListResponse<Feedback>> response) {
                super.onAfter(response);
                toBottom();
            }

        });
    }


    private void toBottom() {
        int position = mRecyclerViewAdapter.getItemCount() - 1;
        mRecyclerView.smoothScrollToPosition(position > 0 ? position : 0);
    }

    private void sendFeedback() {

        String content = String.valueOf(sendText.getText());
        if (TextUtils.isEmpty(content) && TextUtils.isEmpty(content.trim())) {
            return;
        }

        String encode = "";
        try {
            encode = URLEncoder.encode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = "/feedback?code=" + MethodUtils.getDrvCode() + "&text=" + encode;

        HttpRequest.jsonRequestWithoutCache(url, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                super.onSuccess(response);
                if ("1".equals(response.getResponseData())) {
                    requestData();
                    sendText.setText("");
                    ToastUtils.toast("反馈成功！");
                } else {
                    ToastUtils.toast("反馈失败！");
                }
            }

            @Override
            public void onFailure(Response<String> response) {
                super.onFailure(response);
                ToastUtils.toast("反馈失败！");
            }
        });

    }

}
