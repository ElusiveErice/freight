package com.csu.freightbook.db.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.csu.freightbook.db.entities.Maintenance;

import java.util.Date;
import java.util.List;

@Dao
public interface MaintenanceDao {

    @Insert
    void insertMaintenances(List<Maintenance> maintenances);

    @Insert
    void insertMaintenance(Maintenance maintenance);

    @Update
    void updateMaintenance(Maintenance maintenance);

    @Delete
    void deleteMaintenance(Maintenance maintenance);

    @Query("select * from maintenances")
    List<Maintenance> getMaintenances();

    @Query("select * from maintenances where create_date_year=:createDateYear")
    List<Maintenance> getMaintenances(long createDateYear);
}
