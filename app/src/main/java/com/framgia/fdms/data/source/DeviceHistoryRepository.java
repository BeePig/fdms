package com.framgia.fdms.data.source;

import com.framgia.fdms.data.model.DeviceUsingHistory;
import com.framgia.fdms.data.source.remote.DeviceUsingHistoryDataSource;

import java.util.List;

import rx.Observable;

/**
 * Created by framgia on 14/07/2017.
 */
public class DeviceHistoryRepository {
    private DeviceUsingHistoryDataSource mDeviceUsingHistoryDataSource;
    private static DeviceHistoryRepository sInstances;

    public DeviceHistoryRepository(DeviceUsingHistoryDataSource deviceUsingHistoryDataSource) {
        mDeviceUsingHistoryDataSource = deviceUsingHistoryDataSource;
    }

    private DeviceHistoryRepository() {
        mDeviceUsingHistoryDataSource = DeviceUsingHistoryDataSource.getInstances();
    }

    public static DeviceHistoryRepository getInstances() {
        if (sInstances == null) {
            sInstances = new DeviceHistoryRepository();
        }
        return sInstances;
    }

    public Observable<List<DeviceUsingHistory>> getListDeviceHistory() {
        return mDeviceUsingHistoryDataSource.getListDeviceHistory();
    }
}
