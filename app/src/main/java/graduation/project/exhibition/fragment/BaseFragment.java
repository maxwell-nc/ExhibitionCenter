package graduation.project.exhibition.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 基本的Fragment
 */
public abstract class BaseFragment extends Fragment {

    protected int viewId;
    protected View contentView;
    protected Activity attachActivity;

    public BaseFragment(int viewId) {
        this.viewId = viewId;
    }

    protected View findViewById(@IdRes int id){
        return contentView.findViewById(id);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.attachActivity = getActivity();
        contentView = inflater.inflate(viewId, container, false);
        initView();
        initData();
        return contentView;
    }

    public abstract void initView();

    public abstract void initData();

}
