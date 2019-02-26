package com.example.paricipants.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

@Entity
public class Participant {

    @PrimaryKey(autoGenerate = true)
    public int partId;

    private String partName;

    private String partCountry;

    @TypeConverters(DateRoomConverter.class)
    private Date partBirthday;

    private String partGender;

    public Participant(int partId, String partName, String partCountry, Date partBirthday, String partGender) {
        this.partId = partId;
        this.partName = partName;
        this.partCountry = partCountry;
        this.partBirthday = partBirthday;
        this.partGender = partGender;
    }

    //setters
    public void setPartName(String partName) {
        this.partName = partName;
    }

    public void setPartCountry(String partCountry) {
        this.partCountry = partCountry;
    }

    public void setPartBirthday(Date partBirthday) {
        this.partBirthday = partBirthday;
    }

    public void setPartGender(String partGender) {this.partGender = partGender;}


    //getters
    public int getPartId() {
        return partId;
    }

    public String getPartName() {
        return partName;
    }

    public String getPartCountry() {
        return partCountry;
    }

    public Date getPartBirthday() {
        return partBirthday;
    }

    public String getPartGender() { return partGender; }

}
