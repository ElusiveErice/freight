package com.csu.freightbook.db.daos;

import android.content.Context;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.csu.freightbook.db.FreightBookDataBase;
import com.csu.freightbook.db.entities.Bill;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class BillDaoTest extends TestCase {

    FreightBookDataBase mDatabase;

    @Before
    public void initDB() {
        mDatabase = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                FreightBookDataBase.class)
                .build();
    }

    @After
    public void closeDb() {
        mDatabase.close();
    }

    @Test
    public void insertBill() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        BillDao billDao = FreightBookDataBase.getInstance(appContext).billDao();
        billDao.insertBill(new Bill());

        List<Bill> bills = billDao.getBills();
        System.out.println(bills.toString());
    }
}