package graduation.project.exhibition.http;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import graduation.project.exhibition.utils.ToastUtils;
import pres.nc.maxwell.asynchttp.callback.impl.AbsCallback;
import pres.nc.maxwell.asynchttp.io.IOUtils;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * 字符串回调
 */
public abstract class JsonCallback<T> extends AbsCallback<T> {

    @Override
    public void onFailure(Response<T> response) {
        super.onFailure(response);
        ToastUtils.toast(response.getResponseMsg());
    }

    @Override
    public T parseResponseStream(InputStream is, int contentLength) throws Exception {
        Type type = getType();
        try {
            String json = new String(IOUtils.inputStream2bytes(is));
            Log.i("jsonResponse",json);
            return new Gson().fromJson(json, type);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Type getType() {
        Type type;
        Class<?> clazz = this.getClass();
        try {
            ParameterizedType mType = (ParameterizedType) clazz.getGenericInterfaces()[0];
            type = mType.getActualTypeArguments()[0];
        } catch (Throwable e) {
            type = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return type;
    }

    @Override
    public String toCache(T data) {
        return new Gson().toJson(data);
    }
}
