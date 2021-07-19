package com.csu.freightbook.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.csu.freightbook.utils.DateUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

@Entity(tableName = "bills")
public class Bill {

    //账单id
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "bill_id")
    private long mBillId;

    //运趟id
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

    //出发地
    @ColumnInfo(name = "departure")
    private String mDeparture;

    //目的地
    @ColumnInfo(name = "destination")
    private String mDestination;

    //货物名称
    @ColumnInfo(name = "good_name")
    private String mGoodName;

    //油卡
    @ColumnInfo(name = "oil_card")
    private double mOilCard;

    //单价
    @ColumnInfo(name = "price")
    private double mPrice;

    //重量
    @ColumnInfo(name = "weight")
    private double mWeight;

    //总价
    @ColumnInfo(name = "total_price")
    private double mTotalPrice;

    //注释
    @ColumnInfo(name = "notes")
    private String mNotes;

    //总价收齐
    @ColumnInfo(name = "total_price_solve")
    private boolean mTotalPriceSolve;

    //油卡收齐
    @ColumnInfo(name = "oil_card_solve")
    private boolean mOilCardSolve;

    public Bill() {
        this(0);
    }

    @Ignore
    public Bill(long rankId) {
        mRankId = rankId;
        mCreateDateYear = DateUtils.getThisYear();
        mCreateDateMonth = DateUtils.getThisMonth();
        mCreateDateDay = DateUtils.getThisDay();

        mDeparture = "马鞍山";
        mDestination = "芜湖";
        mGoodName = "钢板";
        mPrice = 0;
        mWeight = 0;
        mTotalPrice = 0;
        mNotes = "";
        mOilCard = 0;
        mTotalPriceSolve = false;
        mOilCardSolve = false;
    }

    public Date getCreateDate() throws ParseException {
        return DateUtils.toDate(getCreateDateYear(), getCreateDateMonth(), getCreateDateDay());
    }

    public void setCreateDate(Date date) {
        long[] dates = DateUtils.fromDate(date);
        setCreateDateDay(dates[2]);
        setCreateDateMonth(dates[1]);
        setCreateDateYear(dates[0]);
    }

    public long getBillId() {
        return mBillId;
    }

    public void setBillId(long billId) {
        mBillId = billId;
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

    public String getDeparture() {
        return mDeparture;
    }

    public void setDeparture(String departure) {
        mDeparture = departure;
    }

    public String getDestination() {
        return mDestination;
    }

    public void setDestination(String destination) {
        mDestination = destination;
    }

    public String getGoodName() {
        return mGoodName;
    }

    public void setGoodName(String goodName) {
        mGoodName = goodName;
    }

    public double getOilCard() {
        return mOilCard;
    }

    public void setOilCard(double oilCard) {
        mOilCard = oilCard;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    public double getWeight() {
        return mWeight;
    }

    public void setWeight(double weight) {
        mWeight = weight;
    }

    public double getTotalPrice() {
        return mTotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        mTotalPrice = totalPrice;
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        mNotes = notes;
    }

    public boolean isTotalPriceSolve() {
        return mTotalPriceSolve;
    }

    public void setTotalPriceSolve(boolean totalPriceSolve) {
        mTotalPriceSolve = totalPriceSolve;
    }

    public boolean isOilCardSolve() {
        return mOilCardSolve;
    }

    public void setOilCardSolve(boolean oilCardSolve) {
        mOilCardSolve = oilCardSolve;
    }

    public String getDateText() {
        return getCreateDateMonth() + "月" + getCreateDateDay() + "日";
    }
}
