package com.example.paricipants.database;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
@TypeConverters(DateRoomConverter.class)
public interface ParticipantDao {

    @Query("SELECT * FROM participant")
    LiveData<List<Participant>> getAllParticipants();

    @Query("SELECT * FROM participant WHERE partId = :partId")
    Participant getParticipantById(int partId);

    @Insert(onConflict = REPLACE)
    void addParticipant(Participant participant);

    @Update(onConflict = REPLACE)
    void updateParticipant(Participant participant);

    @Delete
    void deleteParticipant(Participant participant);
}

