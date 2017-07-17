package com.framgia.fdms.data.source;

import com.framgia.fdms.data.model.DeviceUsingHistory;

import java.util.List;

import rx.Observable;

/**
 * Created by framgia on 14/07/2017.
 */
public class DeviceHistoryDataSource {
    public interface RemoteDataSource {
        Observable<List<DeviceUsingHistory>> getListDeviceHistory();
    }
}
