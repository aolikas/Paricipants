package com.example.paricipants.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;

import com.example.paricipants.database.AppDatabase;
import com.example.paricipants.database.Participant;

import java.util.concurrent.ExecutionException;

public class UpdateParticipantViewModel extends AndroidViewModel {

    private AppDatabase appDb;

    public UpdateParticipantViewModel(Application application) {
        super(application);
        appDb = AppDatabase.getDatabase(this.getApplication());
    }

    public Participant readParticipant(final int partId) {
        try {
            return new readAsyncTask(appDb).execute(partId).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateParticipant(final Participant participant) {
        new UpdateParticipantViewModel.updateAsyncTask(appDb).execute(participant);
    }


    private static class updateAsyncTask extends AsyncTask<Participant, Void, Void> {
        private AppDatabase db;

        updateAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(Participant... participants) {
            db.getParticipantDao().updateParticipant(participants[0]);
            return null;
        }
    }

    private static class readAsyncTask extends AsyncTask<Integer, Void, Participant> {
        private AppDatabase db;

        readAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Participant doInBackground(Integer... integers) {
            return db.getParticipantDao().getParticipantById(integers[0]);
        }
    }
}
