package com.framgia.fdms.screen.devicehistory;

import com.framgia.fdms.BasePresenter;
import com.framgia.fdms.BaseViewModel;
import com.framgia.fdms.data.model.DeviceUsingHistory;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
interface DeviceHistoryContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel<Presenter> {
        void onLoadDeviceHistorySuccess(List<DeviceUsingHistory> deviceUsingHistories);
        void onLoadDeviceHistoryFailed();
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter {
        void getDeviceUsingHistory();
    }
}
