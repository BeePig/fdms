package com.framgia.fdms.data.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.framgia.fdms.BR;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by framgia on 23/05/2017.
 */
public class DeviceUsingHistory extends BaseObservable {
    @Expose
    @SerializedName("staff")
    private String mStaffName;
    @Expose
    @SerializedName("from_date")
    private Date mStartDate;
    @Expose
    @SerializedName("return_date")
    private Date mEndDate;
    private List<Device> mUsingDevices;

    public DeviceUsingHistory(String staffName, Date startDate, Date endDate, List<Device>
        usingDevices) {
        mStaffName = staffName;
        mStartDate = startDate;
        mEndDate = endDate;
        mUsingDevices = usingDevices;
    }

    @Bindable
    public List<Device> getUsingDevices() {
        return mUsingDevices;
    }

    public void setUsingDevices(List<Device> usingDevices) {
        mUsingDevices = usingDevices;
        notifyPropertyChanged(BR.usingDevices);
    }

    @Bindable
    public String getStaffName() {
        return mStaffName;
    }

    public void setStaffName(String staffName) {
        mStaffName = staffName;
        notifyPropertyChanged(BR.staffName);
    }

    @Bindable
    public Date getStartDate() {
        return mStartDate;
    }

    public void setStartDate(Date startDate) {
        mStartDate = startDate;
        notifyPropertyChanged(BR.startDate);
    }

    @Bindable
    public Date getEndDate() {
        return mEndDate;
    }

    public void setEndDate(Date endDate) {
        mEndDate = endDate;
        notifyPropertyChanged(BR.endDate);
    }
}
