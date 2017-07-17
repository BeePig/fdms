package com.framgia.fdms.screen.devicehistory;

import android.app.Activity;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.framgia.fdms.BR;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.DeviceUsingHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * Exposes the data to be used in the Devicehistory screen.
 */
public class DeviceHistoryViewModel extends BaseObservable
    implements DeviceHistoryContract.ViewModel {
    private DeviceHistoryContract.Presenter mPresenter;
    private ListUserAdapter mAdapter;
    private List<DeviceUsingHistory> mDeviceUsingHistories = new ArrayList<>();
    private int mLoadingMoreVisibility;
    private AppCompatActivity mActivity;

    public DeviceHistoryViewModel(Activity activity) {
        mActivity = (AppCompatActivity) activity;
        mAdapter = new ListUserAdapter(this, mDeviceUsingHistories);
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void setPresenter(DeviceHistoryContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Bindable
    public ListUserAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(ListUserAdapter adapter) {
        mAdapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }

    @Override
    public void onLoadDeviceHistorySuccess(List<DeviceUsingHistory> deviceUsingHistories) {
        if (deviceUsingHistories != null) {
            mDeviceUsingHistories.addAll(deviceUsingHistories);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoadDeviceHistoryFailed() {
        Snackbar.make(mActivity.findViewById(android.R.id.content), R.string.msg_load_data_fails,
            Snackbar.LENGTH_SHORT).show();
        setLoadingMoreVisibility(View.GONE);
    }

    public int getLoadingMoreVisibility() {
        return mLoadingMoreVisibility;
    }

    public void setLoadingMoreVisibility(int loadingMoreVisibility) {
        mLoadingMoreVisibility = loadingMoreVisibility;
    }
}
