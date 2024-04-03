package com.example.dictionary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.R;
import com.example.dictionary.dto.DictionaryData;

import java.util.ArrayList;
import java.util.Dictionary;

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.MyViewHolder> {
    public ArrayList<DictionaryData> dictionaryDatalist;
    Context context;
    public DictionaryAdapter(ArrayList<DictionaryData> dictionaryDataArraylist) {
        this.dictionaryDatalist =dictionaryDatalist;

    }

    @NonNull
    @Override
    public DictionaryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meaning_recycler_row, parent, false);
        context = parent.getContext();
        return new DictionaryAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DictionaryData dictionaryData = dictionaryDatalist.get(position); // DictionaryData is dto class & dictionaryDatalist is arraylist of type DictionayData(dto)
//        holder.wordTV.setText(String.valueOf(dictionaryData.getWord()));
        holder.POSTV.setText(dictionaryData.getPartOfSpeech());
        holder.defTV.setText(dictionaryData.getDefination());
        holder.synonymTV.setText(String.valueOf(dictionaryData.getSynonyms()));
        holder.antonymTV.setText(String.valueOf(dictionaryData.getAntonyms()));
    }

    @Override
    public int getItemCount() {
        return dictionaryDatalist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView wordTV,POSTV, defTV, synonymTV, antonymTV;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
//            wordTV = (TextView) itemView.findViewById(R.id.word_textview);
            POSTV=(TextView) itemView.findViewById(R.id.part_of_speech_textview);
            defTV = (TextView) itemView.findViewById(R.id.definations_textview);
            synonymTV = (TextView) itemView.findViewById(R.id.synonyms_textview);
            antonymTV = (TextView) itemView.findViewById(R.id.antonyms_textview);
        }
    }
}
