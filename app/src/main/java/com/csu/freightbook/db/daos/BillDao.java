package com.csu.freightbook.db.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.csu.freightbook.db.entities.Bill;

import java.util.Date;
import java.util.List;

@Dao
public interface BillDao {

    @Insert
    void insertBill(Bill bill);

    @Insert
    void insertBills(List<Bill> bills);

    @Update
    void updateBill(Bill bill);

    @Query("select * from bills")
    List<Bill> getBills();

    @Query("select * from bills where create_date_year=:createDateYear")
    List<Bill> getBills(long createDateYear);

    @Query("select * from bills where rank_id=:rankId")
    List<Bill> getBillsByRankId(long rankId);

    @Delete
    void delete(Bill bill);
}
