package graduation.project.exhibition.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

import graduation.project.exhibition.AppApplication;

/**
 * SharePreferences工具类
 */
public class SharePreferencesUtils {

    /**
     * 默认的配置名
     */
    public static final String DEAFULT_SETTING = "settings";

    public static PutBuilder newPutTask() {
        return new PutBuilder(AppApplication.getAppContext(), SharePreferencesUtils.DEAFULT_SETTING);
    }

    public static GetBuilder newGetTask() {
        return new GetBuilder(AppApplication.getAppContext(), SharePreferencesUtils.DEAFULT_SETTING);
    }

    private void put(PutBuilder putBuilder) {

        SharedPreferences sp = putBuilder.context.getSharedPreferences(putBuilder.name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        for (Map.Entry<String, String> set : putBuilder.keyMap.entrySet()) {
            editor.putString(set.getKey(), set.getValue());
        }
        editor.commit();

    }

    public static class GetBuilder {

        SharedPreferences sp;

        public GetBuilder(Context context, String name) {
            sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        }

        /**
         * 获取配置信息
         */
        public String get(String key, String defaultValue) {
            return sp.getString(key, defaultValue);
        }

    }


    public static class PutBuilder {

        HashMap<String, String> keyMap = new HashMap<>();
        Context context;
        String name;

        public PutBuilder(Context context, String name) {
            this.context = context;
            this.name = name;
        }

        /**
         * 存放一条配置
         */
        public PutBuilder put(String key, String value) {
            keyMap.put(key, value);
            return this;
        }

        /**
         * 提交配置
         */
        public void commit() {
            new SharePreferencesUtils().put(this);
        }

    }

}
