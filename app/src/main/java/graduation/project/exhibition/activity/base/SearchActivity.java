package graduation.project.exhibition.activity.base;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import graduation.project.exhibition.R;
import graduation.project.exhibition.activity.discover.DiscoverResultActivity;
import graduation.project.exhibition.activity.home.HomeResultActivity;
import graduation.project.exhibition.utils.IntentUtils;
import graduation.project.exhibition.utils.SharePreferencesUtils;

/**
 * 搜索界面
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private View backBtn;
    private EditText editText;
    private View searchBtn;

    private TextView label1;
    private TextView label2;
    private TextView label3;
    private TextView label4;
    private TextView label5;
    private TextView label6;
    private TextView label7;
    private TextView label8;
    private TextView label9;
    private View hisLayout1;
    private TextView hisText1;
    private View hisLayout2;
    private TextView hisText2;
    private View hisLayout3;
    private TextView hisText3;
    private View clearLayout;
    private View hisHit;

    private String searchType;

    @Override
    protected int setViewId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        setTranslucentBar(R.color.main_theme, R.id.ll_root);

        backBtn = findViewById(R.id.iv_right);
        editText = (EditText) findViewById(R.id.et_text);
        searchBtn = findViewById(R.id.tv_search);

        label1 = (TextView) findViewById(R.id.label_1);
        label2 = (TextView) findViewById(R.id.label_2);
        label3 = (TextView) findViewById(R.id.label_3);
        label4 = (TextView) findViewById(R.id.label_4);
        label5 = (TextView) findViewById(R.id.label_5);
        label6 = (TextView) findViewById(R.id.label_6);
        label7 = (TextView) findViewById(R.id.label_7);
        label8 = (TextView) findViewById(R.id.label_8);
        label9 = (TextView) findViewById(R.id.label_9);

        hisHit = findViewById(R.id.tv_history);
        hisLayout1 = findViewById(R.id.mrl_his_1);
        hisText1 = (TextView) findViewById(R.id.tv_his_1);
        hisLayout2 = findViewById(R.id.mrl_his_2);
        hisText2 = (TextView) findViewById(R.id.tv_his_2);
        hisLayout3 = findViewById(R.id.mrl_his_3);
        hisText3 = (TextView) findViewById(R.id.tv_his_3);
        clearLayout = findViewById(R.id.mrl_his_clear);
    }

    @Override
    protected void initData() {
        searchType = getIntent().getStringExtra("search");

        hideHistoryLayout();

        if (searchType.equals("discover")) {
            setLabel("咖啡", "零食", "便利店", "酒店", "银行", "海关", "机票", "打车", "娱乐");
        } else if (searchType.equals("home")) {
            setLabel("家具", "沙发", "实木", "红木", "简约", "现代", "双人", "复古", "布艺");
        }

        loadHistoryKeyword();

        label1.setOnClickListener(new LabelOnClickListener());
        label2.setOnClickListener(new LabelOnClickListener());
        label3.setOnClickListener(new LabelOnClickListener());
        label4.setOnClickListener(new LabelOnClickListener());
        label5.setOnClickListener(new LabelOnClickListener());
        label6.setOnClickListener(new LabelOnClickListener());
        label7.setOnClickListener(new LabelOnClickListener());
        label8.setOnClickListener(new LabelOnClickListener());
        label9.setOnClickListener(new LabelOnClickListener());

        hisLayout1.setOnClickListener(new HistoryOnClickListener(hisText1));
        hisLayout2.setOnClickListener(new HistoryOnClickListener(hisText2));
        hisLayout3.setOnClickListener(new HistoryOnClickListener(hisText3));

        clearLayout.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
    }


    private void loadHistoryKeyword() {
        if (searchType.equals("discover")) {
            loadHistory("dis_his_1", "dis_his_2", "dis_his_3");
        } else if (searchType.equals("home")) {
            loadHistory("home_his_1", "home_his_2", "home_his_3");
        }
    }

    private void loadHistory(String key1, String key2, String key3) {
        String his1 = SharePreferencesUtils.newGetTask().get(key1, null);
        String his2 = SharePreferencesUtils.newGetTask().get(key2, null);
        String his3 = SharePreferencesUtils.newGetTask().get(key3, null);

        if (!TextUtils.isEmpty(his1)) {
            hisHit.setVisibility(View.VISIBLE);
            clearLayout.setVisibility(View.VISIBLE);

            hisText1.setText(his1);
            hisLayout1.setVisibility(View.VISIBLE);

            if (!TextUtils.isEmpty(his2)) {

                hisText2.setText(his2);
                hisLayout2.setVisibility(View.VISIBLE);

                if (!TextUtils.isEmpty(his3)) {

                    hisText3.setText(his3);
                    hisLayout3.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    private void hideHistoryLayout() {
        hisHit.setVisibility(View.GONE);
        hisLayout1.setVisibility(View.GONE);
        hisLayout2.setVisibility(View.GONE);
        hisLayout3.setVisibility(View.GONE);
        clearLayout.setVisibility(View.GONE);
    }

    /**
     * 设置热门标签
     *
     * @param labelText 必须长度为9
     */
    public void setLabel(String... labelText) {

        if (labelText.length < 9) {
            return;
        }

        label1.setText(labelText[0]);
        label2.setText(labelText[1]);
        label3.setText(labelText[2]);
        label4.setText(labelText[3]);
        label5.setText(labelText[4]);
        label6.setText(labelText[5]);
        label7.setText(labelText[6]);
        label8.setText(labelText[7]);
        label9.setText(labelText[8]);
    }

    /**
     * 标签点击事件监听器
     */
    class LabelOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            editText.setText(((TextView) v).getText());
            toSearch();

        }
    }

    /**
     * 标签点击事件监听器
     */
    class HistoryOnClickListener implements View.OnClickListener {

        private TextView textView;

        public HistoryOnClickListener(TextView textView) {
            this.textView = textView;
        }

        @Override
        public void onClick(View v) {
            editText.setText(textView.getText());
            toSearch();
        }
    }

    /**
     * 搜索
     */
    private void toSearch() {
        String keywords = editText.getText().toString();
        saveKeywordHistory(keywords);

        if (searchType.equals("discover")) {
            IntentUtils.newIntent(this)
                    .setActivityClass(DiscoverResultActivity.class)
                    .putExtra("keyword", keywords)
                    .startActivity();
        } else if (searchType.equals("home")) {
            IntentUtils.newIntent(this)
                    .setActivityClass(HomeResultActivity.class)
                    .putExtra("keyword", keywords)
                    .startActivity();
        }
    }

    /**
     * 保存关键字历史记录
     */
    private void saveKeywordHistory(String keywords) {
        if (searchType.equals("discover")) {
            saveKeyword(keywords, "dis_his_1", "dis_his_2", "dis_his_3");
        } else if (searchType.equals("home")) {
            saveKeyword(keywords, "home_his_1", "home_his_2", "home_his_3");
        }
        loadHistoryKeyword();
    }

    private void saveKeyword(String keywords, String key1, String key2, String key3) {

        String his1 = SharePreferencesUtils.newGetTask().get(key1, null);
        String his2 = SharePreferencesUtils.newGetTask().get(key2, null);
        String his3 = SharePreferencesUtils.newGetTask().get(key3, null);

        if (TextUtils.isEmpty(his1)) {
            SharePreferencesUtils.newPutTask().put(key1, keywords).commit();
        } else if (TextUtils.isEmpty(his2)) {
            SharePreferencesUtils.newPutTask().put(key2, keywords).commit();
        } else if (TextUtils.isEmpty(his3)) {
            SharePreferencesUtils.newPutTask().put(key3, keywords).commit();
        } else {//替换原来的，不要第一个
            SharePreferencesUtils.newPutTask()
                    .put(key1, keywords)
                    .put(key2, his1)
                    .put(key3, his2)
                    .commit();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_right://后退
                onBackPressed();
                break;
            case R.id.mrl_his_clear://清空历史记录
            case R.id.tv_his_clear:
                if (searchType.equals("discover")) {
                    SharePreferencesUtils.newPutTask()
                            .put("dis_his_1", "").put("dis_his_2", "").put("dis_his_3", "")
                            .commit();
                } else if (searchType.equals("home")) {
                    SharePreferencesUtils.newPutTask()
                            .put("home_his_1", "").put("home_his_2", "").put("home_his_3", "")
                            .commit();
                }
                hideHistoryLayout();
                break;
            case R.id.tv_search:
                toSearch();
                break;

        }
    }
}
