package graduation.project.exhibition.activity.setting;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import graduation.project.exhibition.R;
import graduation.project.exhibition.activity.base.BackActivity;
import graduation.project.exhibition.business.UserInfoBusiness;
import graduation.project.exhibition.utils.MethodUtils;
import graduation.project.exhibition.utils.SharePreferencesUtils;
import graduation.project.exhibition.utils.ToastUtils;
import pres.nc.maxwell.asynchttp.callback.impl.StringCallback;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * 用户页面
 */
public class UserActivity extends BackActivity {

    private EditText nameText;
    private View commitBtn;

    @Override
    protected String initBackBar() {
        return "修改用户名";
    }

    @Override
    protected int setViewId() {
        return R.layout.activity_user;
    }

    @Override
    protected void initView() {
        nameText = (EditText) findViewById(R.id.et_name);
        commitBtn = findViewById(R.id.btn_commit);
    }

    @Override
    protected void initData() {
        nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                commitBtn.setEnabled(s.length() > 0);
                commitBtn.setBackgroundColor(MethodUtils.getColor(nameText, s.length() > 0 ? R.color.main_theme : R.color.light_grey));
            }
        });

        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = nameText.getText().toString();
                UserInfoBusiness.setUsername(name, new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        super.onSuccess(response);

                        if ("1".equals(response.getResponseData())) {
                            ToastUtils.toast("修改用户名成功！");
                            SharePreferencesUtils.newPutTask().put("username", name).commit();
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            ToastUtils.toast("修改用户名失败！");
                        }

                    }

                    @Override
                    public void onFailure(Response<String> response) {
                        super.onFailure(response);
                        ToastUtils.toast("修改用户名失败！");
                    }
                });
            }
        });
    }

}
