package com.example.dictionary.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.R;
import com.example.dictionary.dto.DictionaryData;

import java.util.ArrayList;
import java.util.List;

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.MyViewHolder> {

    private ArrayList<DictionaryData> dictionaryDataList;

    public DictionaryAdapter(ArrayList<DictionaryData> dictionaryDataList) {
        this.dictionaryDataList = dictionaryDataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meaning_recycler_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DictionaryData dictionaryData = dictionaryDataList.get(position);
        holder.partOfSpeechTextView.setText(dictionaryData.getPartOfSpeech());
        holder.definitionTextView.setText(dictionaryData.getDefination());
        holder.synonymsTextView.setText(dictionaryData.getSynonyms());
        holder.antonymsTextView.setText(dictionaryData.getAntonyms());
    }

    @Override
    public int getItemCount() {
        return dictionaryDataList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView partOfSpeechTextView, definitionTextView, synonymsTextView, antonymsTextView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            partOfSpeechTextView = itemView.findViewById(R.id.part_of_speech_textview);
            definitionTextView = itemView.findViewById(R.id.definations_textview);
            synonymsTextView = itemView.findViewById(R.id.synonyms_textview);
            antonymsTextView = itemView.findViewById(R.id.antonyms_textview);
        }
    }
}
