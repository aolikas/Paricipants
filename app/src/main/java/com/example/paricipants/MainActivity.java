package com.example.paricipants;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.paricipants.adapter.ParticipantAdapter;
import com.example.paricipants.db.ParticipantsDb;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements ParticipantAdapter.OnParticipantItemClick {

    private TextView emptMsg;
    private RecyclerView recyclerView;
    private ParticipantsDb pDatabase;
    private List<Participant> participants;
    private ParticipantAdapter pAdapter;
    private int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeVies();
        displayList();
    }

    private void displayList() {
        pDatabase = ParticipantsDb.getInstance(MainActivity.this);
        new RetrieveTask(this).execute();
    }

    private static class RetrieveTask extends AsyncTask<Void, Void, List<Participant>> {

        private WeakReference<MainActivity> activityReference;

        // only retain a weak reference to the activity
        RetrieveTask(MainActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<Participant> doInBackground(Void... voids) {
            if (activityReference.get() != null)
                return activityReference.get().pDatabase.getParticipantDao().getAll();
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<Participant> participants) {
            if (participants!=null && participants.size()>0 ){
                activityReference.get().participants.clear();
                activityReference.get().participants.addAll(participants);
                // hides empty text view
                activityReference.get().emptMsg.setVisibility(View.GONE);
                activityReference.get().pAdapter.notifyDataSetChanged();
            }
        }

    }

    private void initializeVies(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        emptMsg =  (TextView) findViewById(R.id.empty_tv);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(listener);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        participants = new ArrayList<>();
        pAdapter = new ParticipantAdapter(participants,MainActivity.this);
        recyclerView.setAdapter(pAdapter);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivityForResult(new Intent(MainActivity.this,AddActivity.class),100);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode > 0 ){
            if( resultCode == 1){
                participants.add((Participant) data.getSerializableExtra("name"));
            }else if( resultCode == 2){
                participants.set(pos,(Participant) data.getSerializableExtra("name"));
            }
            listVisibility();
        }
    }

    @Override
    public void onParticipantClick(final int pos) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Select Options")
                .setItems(new String[]{"Delete", "Update"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                pDatabase.getParticipantDao().deleteParticipant(participants.get(pos));
                                participants.remove(pos);
                                listVisibility();
                                break;
                            case 1:
                                MainActivity.this.pos = pos;
                                startActivityForResult(
                                        new Intent(MainActivity.this,
                                                AddActivity.class).putExtra("note",participants.get(pos)),
                                        100);

                                break;
                        }
                    }
                }).show();

    }

    private void listVisibility(){
        int emptyMsgVisibility = View.GONE;
        if (participants.size() == 0){ // no item to display
            if (emptMsg.getVisibility() == View.GONE)
                emptyMsgVisibility = View.VISIBLE;
        }
        emptMsg.setVisibility(emptyMsgVisibility);
        pAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        pDatabase.cleanUp();
        super.onDestroy();
    }

}
