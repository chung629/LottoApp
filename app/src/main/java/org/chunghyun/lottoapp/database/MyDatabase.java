package org.chunghyun.lottoapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = {Lotto_Input_MyEntity.class, Lotto_Occur_MyEntity.class, Lotto_Select_MyEntity.class}, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    public abstract MyDao myDao();
    public abstract OccurDao occurDao();
    public abstract SelectDao selectDao();
    private static  volatile MyDatabase INSTANCE;
    // 싱글톤
    public static MyDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (MyDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class, "myDatabase").build();
                }
            }
        }
        return INSTANCE;
    }
    //DB 객체 제거
    public static void destroyInstance(){
        INSTANCE = null;
    }
}