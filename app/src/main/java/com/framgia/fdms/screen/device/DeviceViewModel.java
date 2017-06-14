package com.framgia.fdms.screen.device;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.Toast;
import com.android.databinding.library.baseAdapters.BR;
import com.framgia.fdms.data.model.User;
import com.framgia.fdms.screen.ViewPagerScroll;
import com.framgia.fdms.screen.device.listdevice.ListDeviceFragment;
import com.framgia.fdms.screen.devicecreation.CreateDeviceActivity;
import com.framgia.fdms.screen.devicecreation.DeviceStatusType;
import com.framgia.fdms.screen.returndevice.ReturnDeviceActivity;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import java.util.ArrayList;
import java.util.List;

import static com.framgia.fdms.screen.device.DeviceViewModel.Tab.TAB_MANAGE_DEVICE;
import static com.framgia.fdms.screen.device.DeviceViewModel.Tab.TAB_MY_DEVICE;

/**
 * Exposes the data to be used in the Device screen.
 */

public class DeviceViewModel extends BaseObservable
        implements DeviceContract.ViewModel, ViewPagerScroll {

    private final Fragment mFragment;
    private DeviceContract.Presenter mPresenter;
    private ViewPagerAdapter mAdapter;
    private int mTab = TAB_MY_DEVICE;
    private boolean mIsBo;

    public DeviceViewModel(Fragment fragment) {
        mFragment = fragment;
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(ListDeviceFragment.newInstance(TAB_MY_DEVICE));
        fragments.add(ListDeviceFragment.newInstance(TAB_MANAGE_DEVICE));
        mAdapter = new ViewPagerAdapter(mFragment.getChildFragmentManager(), fragments);
        setAdapter(mAdapter);
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
    public void setPresenter(DeviceContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setupViewPager(User user) {
        String role = user.getRole();
        if (role == null) return;

        setBo(user.isBo());

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(ListDeviceFragment.newInstance(TAB_MY_DEVICE));
        if (mIsBo) fragments.add(ListDeviceFragment.newInstance(TAB_MANAGE_DEVICE));
        mAdapter = new ViewPagerAdapter(mFragment.getChildFragmentManager(), fragments);
        setAdapter(mAdapter);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(mFragment.getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCurrentPosition(int position) {
        setTab(position);
    }

    @Override
    public void onClickChangeTab(ViewPager viewPager, @Tab int currentTab) {
        viewPager.setCurrentItem(currentTab);
    }

    @Override
    public void onStartReturnDevice(FloatingActionsMenu floatingActionsMenu) {
        floatingActionsMenu.collapse();
        mFragment.startActivity(ReturnDeviceActivity.newIntent(mFragment.getContext()));
    }

    @Override
    public void onRegisterDeviceClick(FloatingActionsMenu floatingActionsMenu) {
        floatingActionsMenu.collapse();
        mFragment.startActivity(
                CreateDeviceActivity.getInstance(mFragment.getContext(), DeviceStatusType.CREATE));
    }

    @Bindable
    public int getTab() {
        return mTab;
    }

    public void setTab(int tab) {
        mTab = tab;
        notifyPropertyChanged(BR.tab);
    }

    @Bindable
    public boolean isBo() {
        return mIsBo;
    }

    public void setBo(boolean bo) {
        mIsBo = bo;
        notifyPropertyChanged(BR.bo);
    }

    @Bindable
    public ViewPagerAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(ViewPagerAdapter adapter) {
        mAdapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }

    @IntDef({ TAB_MY_DEVICE, TAB_MANAGE_DEVICE })
    public @interface Tab {
        int TAB_MY_DEVICE = 0;
        int TAB_MANAGE_DEVICE = 1;
    }
}
