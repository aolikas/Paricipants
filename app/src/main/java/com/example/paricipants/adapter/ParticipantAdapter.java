package com.example.paricipants.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paricipants.R;
import com.example.paricipants.database.Participant;

import java.util.List;

public class ParticipantAdapter extends RecyclerView.Adapter<ParticipantAdapter.RecyclerViewHolder> {


    private List<Participant> list;
    private View.OnLongClickListener longClickListener;
    private View.OnClickListener clickListener;

    public ParticipantAdapter(List<Participant> list,
                              View.OnLongClickListener longClickListener,
                              View.OnClickListener clickListener) {
        this.list = list;
        this.longClickListener = longClickListener;
        this.clickListener = clickListener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.participants_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        Participant participant = list.get(position);
        holder.nameTextView.setText(participant.getPartName());
        holder.countryTextView.setText(participant.getPartCountry());
        holder.dateTextView.setText(participant.getPartBirthday().toLocaleString().substring(0,12));
        holder.itemView.setTag(participant);
        holder.itemView.setOnLongClickListener(longClickListener);
        holder.itemView.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addParticipants(List<Participant> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView countryTextView;
        private TextView dateTextView;

        RecyclerViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.tv_name);
            countryTextView = view.findViewById(R.id.tv_country);
            dateTextView = view.findViewById(R.id.tv_dob);
        }
    }


}
