package com.example.paricipants.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.paricipants.database.AppDatabase;
import com.example.paricipants.database.entity.Participant;
import com.example.paricipants.repository.ParticipantsRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ParticipantViewModel extends AndroidViewModel {

    private ParticipantsRepository mRepository;
    private LiveData<List<Participant>> mAllParticipants;


    public ParticipantViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ParticipantsRepository(application);
    }

    public LiveData<List<Participant>> getParticipants() {
        if (mAllParticipants == null) {
            mAllParticipants = mRepository.getAllParticipants();
        }
        return mAllParticipants;
    }

    public void addParticipant(final Participant participant) {
        mRepository.addParticipant(participant);

    }

    public Participant readParticipant(final int partId) {
            return mRepository.readParticipant(partId);

    }



    public void updateParticipant(final Participant participant) {
        mRepository.updateParticipant(participant);
    }

    public void deleteParticipant(Participant participant) {
        mRepository.deleteParticipant(participant);
    }


}

