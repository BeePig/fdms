package com.framgia.fdms.data.source.remote;

import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.model.DeviceUsingHistory;
import com.framgia.fdms.data.source.DeviceHistoryDataSource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;

/**
 * Created by framgia on 14/07/2017.
 */
public class DeviceUsingHistoryDataSource implements DeviceHistoryDataSource.RemoteDataSource {
    private static DeviceUsingHistoryDataSource sInstances;

    public DeviceUsingHistoryDataSource() {
    }

    public static DeviceUsingHistoryDataSource getInstances() {
        if (sInstances == null) {
            sInstances = new DeviceUsingHistoryDataSource();
        }
        return sInstances;
    }

    @Override
    public Observable<List<DeviceUsingHistory>> getListDeviceHistory() {
        List<DeviceUsingHistory> deviceUsingHistoryList = new ArrayList<>();
        List<Device> deviceList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Device device = new Device(i + "FHN_05_504_0001", i + "sony", i + "Camera");
            deviceList.add(device);
        }
        for (int i = 0; i < 10; i++) {
            DeviceUsingHistory deviceUsingHistory =
                new DeviceUsingHistory(i + "Dat", new Date(), null, deviceList);
            deviceUsingHistoryList.add(deviceUsingHistory);
        }
        return Observable.just(deviceUsingHistoryList);
    }
}
