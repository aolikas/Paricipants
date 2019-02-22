package com.example.paricipants.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;

import com.example.paricipants.database.AppDatabase;
import com.example.paricipants.database.Participant;

public class AddParticipantViewModel extends AndroidViewModel {

    private AppDatabase appDb;

    public AddParticipantViewModel(Application application) {
        super(application);
        appDb = AppDatabase.getDatabase(this.getApplication());
    }

    public void addParticipant(final Participant participant) {
        new addAsyncTask(appDb).execute(participant);

    }

    private static class addAsyncTask extends AsyncTask<Participant, Void, Void> {
        private AppDatabase db;

        addAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final Participant... participants) {
            db.getParticipantDao().addParticipant(participants[0]);
            return null;
        }
    }

}
