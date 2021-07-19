package com.csu.freightbook.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.csu.freightbook.db.daos.BillDao;
import com.csu.freightbook.db.daos.MaintenanceDao;
import com.csu.freightbook.db.daos.RankDao;
import com.csu.freightbook.db.entities.Bill;
import com.csu.freightbook.db.entities.Maintenance;
import com.csu.freightbook.db.entities.Rank;

@Database(entities = {Bill.class, Rank.class, Maintenance.class}, version = 1, exportSchema = false)
public abstract class FreightBookDataBase extends RoomDatabase {

    private static FreightBookDataBase sFreightBookDataBase;

    public static FreightBookDataBase getInstance(Context context) {
        if (sFreightBookDataBase == null) {
            synchronized (FreightBookDataBase.class) {
                if (sFreightBookDataBase == null) {
                    sFreightBookDataBase = Room.databaseBuilder(context.getApplicationContext(),
                            FreightBookDataBase.class, "FreightBook.db").build();
                }
            }
        }

        return sFreightBookDataBase;
    }

    public abstract BillDao billDao();

    public abstract RankDao rankDao();

    public abstract MaintenanceDao maintenanceDao();

}
