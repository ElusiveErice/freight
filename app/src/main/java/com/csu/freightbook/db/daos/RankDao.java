package com.csu.freightbook.db.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.csu.freightbook.db.entities.Rank;

import java.util.Date;
import java.util.List;

@Dao
public interface RankDao {

    @Insert
    void insertRanks(List<Rank> ranks);

    @Insert
    void insertRank(Rank rank);

    @Update
    void updateRank(Rank rank);

    @Delete
    void deleteRank(Rank rank);

    @Query("select * from ranks")
    List<Rank> getRanks();

    @Query("select * from ranks where check_out = :checkOut")
    List<Rank> getRanks(boolean checkOut);

    @Query("select * from ranks where create_date_year = :createDateYear")
    List<Rank> getRanks(long createDateYear);
}
