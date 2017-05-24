package com.framgia.fdms.utils;

import com.framgia.fdms.data.model.Respone;
import java.text.SimpleDateFormat;
import java.util.Date;
import rx.Observable;

import static com.framgia.fdms.utils.Constant.PERCENT;
import static com.framgia.fdms.utils.Constant.TITLE_UNKNOWN;

/**
 * Created by MyPC on 05/05/2017.
 */

public class Utils {
    public static <T> Observable<T> getResponse(Respone<T> listRespone) {
        if (listRespone == null) {
            return Observable.error(new NullPointerException());
        } else if (listRespone.isError()) {
            return Observable.error(new NullPointerException("ERROR" + listRespone.getStatus()));
        } else {
            return Observable.just(listRespone.getData());
        }
    }

    public static String getStringPercent(int count, int total) {
        float percent;
        if (total == 0) {
            percent = 0;
        } else {
            percent = (float) count / total * 100f;
        }
        return String.format("%.1f", percent) + PERCENT;
    }

    public static String getStringDate(Date date) {
        if (date == null) return TITLE_UNKNOWN;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
    }
}
