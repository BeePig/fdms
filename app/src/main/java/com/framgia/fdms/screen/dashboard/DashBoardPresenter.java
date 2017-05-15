package com.framgia.fdms.screen.dashboard;

import android.graphics.Color;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.Dashboard;
import com.framgia.fdms.data.model.Respone;
import com.framgia.fdms.data.source.DeviceRepository;
import com.framgia.fdms.data.source.RequestRepository;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import java.util.ArrayList;
import java.util.List;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.framgia.fdms.screen.dashboard.DashBoardFragment.DEVICE_DASHBOARD;
import static com.framgia.fdms.screen.dashboard.DashBoardFragment.REQUEST_DASHBOARD;

/**
 * Listens to user actions from the UI ({@link DashBoardFragment}), retrieves the data and updates
 * the UI as required.
 */
final class DashBoardPresenter implements DashBoardContract.Presenter {
    private CompositeSubscription mCompositeSubscriptions = new CompositeSubscription();

    private final DashBoardContract.ViewModel mViewModel;
    private DeviceRepository mDeviceRepository;
    private RequestRepository mRequestRepository;
    private int mDashboardType;

    public DashBoardPresenter(DashBoardContract.ViewModel viewModel,
            DeviceRepository deviceRepository, RequestRepository requestRepository,
            int dashboardType) {
        mViewModel = viewModel;
        mDeviceRepository = deviceRepository;
        mRequestRepository = requestRepository;
        mDashboardType = dashboardType;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
        mCompositeSubscriptions.clear();
    }

    @Override
    public void getDeviceDashboard() {
        Subscription subscription = mDeviceRepository.getDashboardDevice()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Dashboard>>() {
                    @Override
                    public void call(List<Dashboard> dashboards) {
                        mViewModel.onDashBoardLoaded(dashboards);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mViewModel.onDashBoardError(throwable.getMessage());
                    }
                });
        mCompositeSubscriptions.add(subscription);
    }

    @Override
    public void getRequestDashboard() {
        Subscription subscription = mRequestRepository.getDashboardRequest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Dashboard>>() {
                    @Override
                    public void call(List<Dashboard> dashboards) {
                        mViewModel.onDashBoardLoaded(dashboards);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mViewModel.onDashBoardError(throwable.getMessage());
                    }
                });
        mCompositeSubscriptions.add(subscription);
    }

    @Override
    public void getData() {
        if (mDashboardType == DEVICE_DASHBOARD) {
            getDeviceDashboard();
        } else if (mDashboardType == REQUEST_DASHBOARD) {
            getRequestDashboard();
        }
    }
}
