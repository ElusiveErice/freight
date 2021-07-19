package com.csu.freightbook.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.csu.freightbook.utils.DateUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

@Entity(tableName = "ranks")
public class Rank {

    //运趟id
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rank_id")
    private long mRankId;

    //创建日期年
    @ColumnInfo(name = "create_date_year")
    private long mCreateDateYear;

    //创建日期月
    @ColumnInfo(name = "create_date_month")
    private long mCreateDateMonth;

    //创建日期日
    @ColumnInfo(name = "create_date_day")
    private long mCreateDateDay;

    @ColumnInfo(name = "check_out")
    private boolean checkOut;

    public Rank() {
        mCreateDateYear = DateUtils.getThisYear();
        mCreateDateMonth = DateUtils.getThisMonth();
        mCreateDateDay = DateUtils.getThisDay();
        checkOut = false;
    }

    public void setCreateDate(Date date) {
        long[] dates = DateUtils.fromDate(date);
        setCreateDateDay(dates[2]);
        setCreateDateMonth(dates[1]);
        setCreateDateYear(dates[0]);
    }

    public Date getCreateDate() throws ParseException {
        return DateUtils.toDate(getCreateDateYear(), getCreateDateMonth(), getCreateDateDay());
    }

    public boolean isCheckOut() {
        return checkOut;
    }

    public void setCheckOut(boolean checkOut) {
        this.checkOut = checkOut;
    }

    public long getRankId() {
        return mRankId;
    }

    public void setRankId(long rankId) {
        mRankId = rankId;
    }

    public long getCreateDateYear() {
        return mCreateDateYear;
    }

    public void setCreateDateYear(long createDateYear) {
        mCreateDateYear = createDateYear;
    }

    public long getCreateDateMonth() {
        return mCreateDateMonth;
    }

    public void setCreateDateMonth(long createDateMonth) {
        mCreateDateMonth = createDateMonth;
    }

    public long getCreateDateDay() {
        return mCreateDateDay;
    }

    public void setCreateDateDay(long createDateDay) {
        mCreateDateDay = createDateDay;
    }
}
