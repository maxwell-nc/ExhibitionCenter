package pres.nc.maxwell.asynchttp.callback.impl;

import pres.nc.maxwell.asynchttp.callback.ICallback;
import pres.nc.maxwell.asynchttp.request.Request;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * 抽象回调
 */
public abstract class AbsCallback<T> implements ICallback<T> {

    @Override
    public void onPrepared(Request request) {
    }

    @Override
    public void onSuccess(Response<T> response) {
    }

    @Override
    public void onFailure(Response<T> response) {
    }

    @Override
    public void onAfter(Response<T> response) {
    }
}
