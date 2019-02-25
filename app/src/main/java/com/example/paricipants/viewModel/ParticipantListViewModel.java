package com.example.paricipants.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.view.View;

import com.example.paricipants.R;
import com.example.paricipants.database.AppDatabase;
import com.example.paricipants.database.Participant;

import java.util.List;

public class ParticipantListViewModel extends AndroidViewModel {

    private final LiveData<List<Participant>> participantsList;
    private AppDatabase appDb;


    public ParticipantListViewModel(Application aplication) {
        super(aplication);
        appDb = AppDatabase.getDatabase(this.getApplication());
        participantsList = appDb.getParticipantDao().getAllParticipants();

    }

    public LiveData<List<Participant>> getParticipantsList() {
        return participantsList;
    }


    public void deleteParticipant(Participant participant) {
        new deleteAsyncTask(appDb).execute(participant);
    }

    private static class deleteAsyncTask extends AsyncTask<Participant, Void, Void> {

        private AppDatabase db;

        deleteAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final Participant... participants) {
            db.getParticipantDao().deleteParticipant(participants[0]);
            return null;
        }
    }
}
