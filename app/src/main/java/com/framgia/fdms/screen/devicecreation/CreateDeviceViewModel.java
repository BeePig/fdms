package com.framgia.fdms.screen.devicecreation;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.framgia.fdms.BR;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.Category;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.model.Picture;
import com.framgia.fdms.data.model.Status;
import com.framgia.fdms.screen.main.MainActivity;
import com.framgia.fdms.screen.selection.StatusSelectionActivity;
import com.framgia.fdms.screen.selection.StatusSelectionType;
import com.framgia.fdms.utils.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.framgia.fdms.utils.Constant.BundleConstant.BUNDLE_CATEGORY;
import static com.framgia.fdms.utils.Constant.BundleConstant.BUNDLE_STATUE;
import static com.framgia.fdms.utils.Constant.PICK_IMAGE_REQUEST;
import static com.framgia.fdms.utils.Constant.RequestConstant.REQUEST_BRANCH;
import static com.framgia.fdms.utils.Constant.RequestConstant.REQUEST_CATEGORY;
import static com.framgia.fdms.utils.Constant.RequestConstant.REQUEST_STATUS;

/**
 * Exposes the data to be used in the Createdevice screen.
 */

public class CreateDeviceViewModel extends BaseObservable
        implements CreateDeviceContract.ViewModel, DatePickerDialog.OnDateSetListener {
    private static final int DEFAULT_STATUS_ID = 2;
    private static final String DEFAULT_STATUS_NAME = "available";
    private static final int DEFAULT_BRANCH_ID = 1;
    private static final String DEFAULT_BRANCH_NAME = "Hanoi";

    private DeviceStatusType mDeviceType = DeviceStatusType.CREATE;
    private Context mContext;
    private AppCompatActivity mActivity;
    private CreateDeviceContract.Presenter mPresenter;
    private String mDeviceCodeError;
    private String mNameDeviceError;
    private String mSerialNumberError;
    private String mModelNumberError;
    private String mBoughtDateError;
    private String mCategoryError;
    private String mOriginalPriceError;
    private String mBoughtDate;

    private Device mDevice;

    private List<Status> mStatuses = new ArrayList<>();
    private List<Category> mCategories = new ArrayList<>();
    private List<Status> mBranches = new ArrayList<>();

    private Category mCategory;
    private Status mStatus;
    private Status mBranch;
    private Calendar mCalendar = Calendar.getInstance();
    private boolean mIsQrCode = true;

    public CreateDeviceViewModel(CreateDeviceActivity activity, Device device,
            DeviceStatusType type) {
        mContext = activity.getApplicationContext();
        mActivity = activity;
        if (device == null) {
            mDevice = new Device();
            mStatus = new Status(DEFAULT_STATUS_ID, DEFAULT_STATUS_NAME);
            mBranch = new Status(DEFAULT_BRANCH_ID, DEFAULT_BRANCH_NAME);
        } else {
            mDevice = device;
            mCategory = new Category(device.getDeviceCategoryId(), device.getDeviceCategoryName());
            mStatus = new Status(device.getDeviceStatusId(), device.getDeviceStatusName());
        }
        mDeviceType = type;
    }

    @Override
    public void onCreateDeviceClick() {
        switch (mDeviceType) {
            case CREATE:
                mPresenter.registerDevice(mDevice);
                break;
            case EDIT:
                mPresenter.updateDevice(mDevice);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPickDateTimeClick() {
        if (mCalendar == null) mCalendar = Calendar.getInstance();
        DatePickerDialog datePicker =
                DatePickerDialog.newInstance(this, mCalendar.get(Calendar.YEAR),
                        mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
        datePicker.show(mActivity.getFragmentManager(), "");
    }

    @Override
    public void onGetBranchSuccess(List<Status> branches) {
        updateBranch(branches);
    }

    @Override
    public void onLoadError(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }

    public void onChooseCategory() {
        if (mCategories == null) return;
        mActivity.startActivityForResult(
                StatusSelectionActivity.getInstance(mContext, mCategories, mStatuses,
                        StatusSelectionType.CATEGORY), REQUEST_CATEGORY);
    }

    public void onChooseStatus() {
        if (mStatuses == null) return;
        mActivity.startActivityForResult(
                StatusSelectionActivity.getInstance(mContext, mCategories, mStatuses,
                        StatusSelectionType.STATUS), REQUEST_STATUS);
    }

    public void onChooseBranch() {
        if (mBranches == null) return;
        mActivity.startActivityForResult(
                StatusSelectionActivity.getInstance(mContext, mCategories, mBranches,
                        StatusSelectionType.STATUS), REQUEST_BRANCH);
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
    public void setPresenter(CreateDeviceContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDeviceCategoryLoaded(List<Category> categories) {
        updateCategory(categories);
    }

    public void updateCategory(List<Category> list) {
        if (list == null) {
            return;
        }
        mCategories.clear();
        mCategories.addAll(list);
    }

    public void updateStatus(List<Status> list) {
        if (list == null) {
            return;
        }
        mStatuses.clear();
        mStatuses.addAll(list);
    }

    public void updateBranch(List<Status> list) {
        if (list == null) {
            return;
        }
        mBranches.clear();
        mBranches.addAll(list);
    }

    @Override
    public void showProgressbar() {
        // TODO: later
    }

    @Override
    public void hideProgressbar() {
        // TODO: later
    }

    @Override
    public void onRegisterError() {
        Toast.makeText(mContext, R.string.msg_create_device_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRegisterSuccess() {
        mContext.startActivity(MainActivity.getInstance(mContext));
    }

    @Override
    public void onInputProductionNameError() {
        mNameDeviceError = mContext.getString(R.string.msg_error_user_name);
        notifyPropertyChanged(BR.nameDeviceError);
    }

    @Override
    public void onInputSerialNumberError() {
        mSerialNumberError = mContext.getString(R.string.msg_error_user_name);
        notifyPropertyChanged(BR.serialNumberError);
    }

    @Override
    public void onInputModellNumberError() {
        mModelNumberError = mContext.getString(R.string.msg_error_user_name);
        notifyPropertyChanged(BR.modelNumberError);
    }

    @Override
    public void onInputDeviceCodeError() {
        mDeviceCodeError = mContext.getString(R.string.msg_error_user_name);
        notifyPropertyChanged(BR.deviceCodeError);
    }

    @Override
    public void onInputCategoryError() {
        mCategoryError = mContext.getString(R.string.msg_error_user_name);
        notifyPropertyChanged(BR.categoryError);
    }

    @Override
    public void onInputBoughtDateError() {
        mBoughtDateError = mContext.getString(R.string.msg_error_user_name);
        notifyPropertyChanged(BR.boughtDateError);
    }

    @Override
    public void onInputOriginalPriceError() {
        mOriginalPriceError = mContext.getString(R.string.msg_error_user_name);
        notifyPropertyChanged(BR.originalPriceError);
    }

    @Override
    public void onGetDeviceCodeSuccess(String deviceCode) {
        mDevice.setDeviceCode(deviceCode);
    }

    @Override
    public void onInputStatusError() {
        // TODO: later
    }

    @Override
    public void onAddImageClick() {
        pickImage();
    }

    public void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivity.startActivityForResult(
                Intent.createChooser(intent, mContext.getString(R.string.title_select_file_upload)),
                PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK || data == null) return;
        switch (requestCode) {
            case PICK_IMAGE_REQUEST:
                if (data.getData() != null) {
                    Uri uri = data.getData();
                    mDevice.setPicture(new Picture(Utils.getPathFromUri(mActivity, uri)));
                }
                break;
            case REQUEST_CATEGORY:
                Bundle bundle = data.getExtras();
                Category category = bundle.getParcelable(BUNDLE_CATEGORY);
                if (category.getName().equals(mContext.getString(R.string.action_clear))) {
                    category.setName(mContext.getString(R.string.title_empty));
                }
                setCategory(category);
                mPresenter.getDeviceCode(mCategory.getId(), mBranch.getId());
                break;
            case REQUEST_STATUS:
                bundle = data.getExtras();
                Status status = bundle.getParcelable(BUNDLE_STATUE);
                if (status.getName().equals(mContext.getString(R.string.action_clear))) {
                    status.setName(mContext.getString(R.string.title_empty));
                }
                setStatus(status);
                break;
            case REQUEST_BRANCH:
                bundle = data.getExtras();
                Status branch = bundle.getParcelable(BUNDLE_STATUE);
                if (branch.getName().equals(mContext.getString(R.string.action_clear))) {
                    branch.setId(DEFAULT_BRANCH_ID);
                    branch.setName(DEFAULT_BRANCH_NAME);
                }
                setBranch(branch);
                if (mCategory != null && mCategory.getId() > 0) {
                    mPresenter.getDeviceCode(mCategory.getId(), mBranch.getId());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onDeviceStatusLoaded(List<Status> statuses) {
        updateStatus(statuses);
    }

    @Bindable
    public String getDeviceCodeError() {
        return mDeviceCodeError;
    }

    @Bindable
    public String getNameDeviceError() {
        return mNameDeviceError;
    }

    @Bindable
    public String getSerialNumberError() {
        return mSerialNumberError;
    }

    @Bindable
    public String getModelNumberError() {
        return mModelNumberError;
    }

    @Bindable
    public String getBoughtDateError() {
        return mBoughtDateError;
    }

    @Bindable
    public String getCategoryError() {
        return mCategoryError;
    }

    @Bindable
    public String getOriginalPriceError() {
        return mOriginalPriceError;
    }

    @Bindable
    public Category getCategory() {
        return mCategory;
    }

    public void setCategory(Category category) {
        mDevice.setDeviceCategoryId(category.getId());
        mCategory = category;
        notifyPropertyChanged(BR.category);
    }

    @Bindable
    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        mDevice.setDeviceStatusId(status.getId());
        mStatus = status;
        notifyPropertyChanged(BR.status);
    }

    @Override
    public void onUpdateSuccess(Device device) {
        mDevice = device;
    }

    @Override
    public void onUpdateError() {
        Toast.makeText(mContext, R.string.msg_update_device_error, Toast.LENGTH_LONG).show();
    }

    public AppCompatActivity getActivity() {
        return mActivity;
    }

    public Device getDevice() {
        return mDevice;
    }

    public DeviceStatusType getDeviceType() {
        return mDeviceType;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        mBoughtDate = dayOfMonth + " - " + (monthOfYear + 1) + " - " + year;
        setBoughtDate(mBoughtDate);
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, monthOfYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        mDevice.setBoughtDate(mCalendar.getTime());
    }

    @Bindable
    public String getBoughtDate() {
        return mBoughtDate;
    }

    public void setBoughtDate(String boughtDate) {
        mBoughtDate = boughtDate;
        notifyPropertyChanged(BR.boughtDate);
    }

    @Bindable
    public boolean isQrCode() {
        return mIsQrCode;
    }

    public void setQrCode(boolean qrCode) {
        mIsQrCode = qrCode;
        notifyPropertyChanged(BR.qrCode);
    }

    @Bindable
    public Status getBranch() {
        return mBranch;
    }

    public void setBranch(Status branch) {
        mBranch = branch;
        notifyPropertyChanged(BR.branch);
    }
}
