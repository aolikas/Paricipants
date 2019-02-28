package com.example.paricipants.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.paricipants.database.AppDatabase;
import com.example.paricipants.database.dao.ParticipantDao;
import com.example.paricipants.database.entity.Participant;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ParticipantsRepository {

    public ParticipantDao participantDao;
    private LiveData<List<Participant>> allParticipants;

    public ParticipantsRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        participantDao = db.getParticipantDao();
        allParticipants = participantDao.getAllParticipants();
    }

    public LiveData<List<Participant>> getAllParticipants() {
        return allParticipants;
    }

    public void addParticipant(Participant participant) {
        new addAsyncTask(participantDao).execute(participant);
    }

    public Participant readParticipant(int partId) {
        try {
            return new readAsyncTask(participantDao).execute(partId).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateParticipant(Participant participant) {
        new updateAsyncTask(participantDao).execute(participant);
    }


    public void deleteParticipant(Participant participant) {
        new deleteAsyncTask(participantDao).execute(participant);
    }


    private static class addAsyncTask extends AsyncTask<Participant, Void, Void> {
        private ParticipantDao mAsyncTaskDao;

        addAsyncTask(ParticipantDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Participant... participants) {
            mAsyncTaskDao.addParticipant(participants[0]);
            return null;
        }
    }

    private static class readAsyncTask extends AsyncTask<Integer, Void, Participant> {
        private ParticipantDao mAsyncTaskDao;

        readAsyncTask(ParticipantDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Participant doInBackground(Integer... integers) {
            return mAsyncTaskDao.getParticipantById(integers[0]);
        }
    }


    private static class updateAsyncTask extends AsyncTask<Participant, Void, Void> {
        private ParticipantDao mAsyncTaskDao;

        updateAsyncTask(ParticipantDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Participant... participants) {
            mAsyncTaskDao.updateParticipant(participants[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Participant, Void, Void> {

        private ParticipantDao mAsyncTaskDao;

        deleteAsyncTask(ParticipantDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Participant... participants) {
            mAsyncTaskDao.deleteParticipant(participants[0]);
            return null;
        }
    }
}
