package com.csu.freightbook.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.csu.freightbook.utils.DateUtils;

import java.util.Date;
import java.util.UUID;

@Entity(tableName = "maintenances")
public class Maintenance {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "maintenance_id")
    private long mMaintenanceId;

    @ColumnInfo(name = "name")
    private String mName;

    //创建日期年
    @ColumnInfo(name = "create_date_year")
    private long mCreateDateYear;

    //创建日期月
    @ColumnInfo(name = "create_date_month")
    private long mCreateDateMonth;

    //创建日期日
    @ColumnInfo(name = "create_date_day")
    private long mCreateDateDay;

    @ColumnInfo(name = "total_price")
    private int mTotalPrice;

    @ColumnInfo(name = "notes")
    private String mNotes;

    public Maintenance() {

        mName = "";
        mCreateDateYear = DateUtils.getThisYear();
        mCreateDateMonth = DateUtils.getThisMonth();
        mCreateDateDay = DateUtils.getThisDay();
        mTotalPrice = 0;
        mNotes = "";
    }

    public long getMaintenanceId() {
        return mMaintenanceId;
    }

    public void setMaintenanceId(long maintenanceId) {
        mMaintenanceId = maintenanceId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
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

    public int getTotalPrice() {
        return mTotalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        mTotalPrice = totalPrice;
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        mNotes = notes;
    }
}
