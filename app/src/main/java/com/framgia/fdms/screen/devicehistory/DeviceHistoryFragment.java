package com.framgia.fdms.screen.devicehistory;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.fdms.R;
import com.framgia.fdms.databinding.FragmentDeviceHistoryBinding;

/**
 * Devicehistory Screen.
 */
public class DeviceHistoryFragment extends Fragment {
    private DeviceHistoryContract.ViewModel mViewModel;

    public static DeviceHistoryFragment newInstance() {
        return new DeviceHistoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new DeviceHistoryViewModel(getActivity());
        DeviceHistoryContract.Presenter presenter =
            new DeviceHistoryPresenter(mViewModel);
        mViewModel.setPresenter(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentDeviceHistoryBinding binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_device_history, container, false);
        binding.setViewModel((DeviceHistoryViewModel) mViewModel);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    public void onStop() {
        mViewModel.onStop();
        super.onStop();
    }
}
