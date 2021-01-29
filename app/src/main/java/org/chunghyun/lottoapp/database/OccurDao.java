package org.chunghyun.lottoapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface OccurDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) // 중복 ID일경우 교체
    public void insert(Lotto_Occur_MyEntity entity);

    @Update
    void update(Lotto_Occur_MyEntity  myEntity);

    @Delete
    public void delete(Lotto_Occur_MyEntity  entity);

    @Query("DELETE FROM lotto_Occur_database")
    public void deleteAll();

    @Query("SELECT * FROM lotto_Occur_database")
    public LiveData<List<Lotto_Occur_MyEntity>> getAll();

    @Query("DELETE FROM lotto_Occur_database WHERE round LIKE :selectRound")
    public void deleteRoundAll(String selectRound);

}