package com.framgia.fdms.screen.devicehistory;

import com.framgia.fdms.data.model.DeviceUsingHistory;
import com.framgia.fdms.data.source.DeviceHistoryRepository;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Listens to user actions from the UI ({@link DeviceHistoryFragment}), retrieves the data and updates
 * the UI as required.
 */
final class DeviceHistoryPresenter implements DeviceHistoryContract.Presenter {
    private static final String TAG = DeviceHistoryPresenter.class.getName();
    private final DeviceHistoryContract.ViewModel mViewModel;
    private DeviceHistoryRepository mDeviceHistoryRepository =
        DeviceHistoryRepository.getInstances();
    private CompositeSubscription mSubscription;

    public DeviceHistoryPresenter(DeviceHistoryContract.ViewModel viewModel) {
        mViewModel = viewModel;
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void onStart() {
        getDeviceUsingHistory();
    }

    @Override
    public void onStop() {
    }

    @Override
    public void getDeviceUsingHistory() {
        Subscription subscription = mDeviceHistoryRepository.getListDeviceHistory()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<List<DeviceUsingHistory>>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                    mViewModel.onLoadDeviceHistoryFailed();
                }

                @Override
                public void onNext(List<DeviceUsingHistory> deviceUsingHistories) {
                    mViewModel.onLoadDeviceHistorySuccess(deviceUsingHistories);
                }
            });
        mSubscription.add(subscription);
    }
}
