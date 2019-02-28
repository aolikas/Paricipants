package com.example.paricipants.database.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import com.example.paricipants.database.converters.DateRoomConverter;
import com.example.paricipants.database.entity.Participant;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
@TypeConverters(DateRoomConverter.class)
public interface ParticipantDao {

    @Query("SELECT * FROM participants")
    LiveData<List<Participant>> getAllParticipants();

    @Query("SELECT * FROM participants WHERE partId = :partId")
    Participant getParticipantById(int partId);

    @Query("SELECT * FROM participants WHERE gender = :partGender")
    List<Participant> getParticipantListByGender(String partGender);

    @Insert(onConflict = REPLACE)
    void addParticipant(Participant participant);

    @Update(onConflict = REPLACE)
    void updateParticipant(Participant participant);

    @Delete
    void deleteParticipant(Participant participant);
}