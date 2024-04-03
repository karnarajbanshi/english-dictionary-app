package com.example.dictionary;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dictionary.adapter.DictionaryAdapter;
import com.example.dictionary.dto.DictionaryData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RecyclerView dictionaryRecyclerView;
    TextView wordTV;
    EditText inputET;
    Button searchBTN;
    ArrayList<DictionaryData> dictionaryArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchBTN = findViewById(R.id.search_btn);
        wordTV = findViewById(R.id.word_textview);
        inputET=findViewById(R.id.search_input);
        // Initializing UI
        searchBTN.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick (View view){
            initUI();
    }
    });
}

    private void initUI() {
        dictionaryRecyclerView = (RecyclerView) findViewById(R.id.meaning_recycler_view);
        wordTV = findViewById(R.id.word_textview);
        dictionaryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        String inputWord = inputET.getText().toString();
        getdictionaryData(inputWord);
    }

    private void setDataToUI() {
        wordTV.setText(dictionaryArrayList.get(0).getWord());
        DictionaryAdapter dictionaryAdapter = new DictionaryAdapter(dictionaryArrayList);
        dictionaryRecyclerView.setAdapter(dictionaryAdapter);
    }

    protected void getdictionaryData(String inputword) {
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://api.dictionaryapi.dev/api/v2/entries/en/" + inputword;
        StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        DictionaryData dictionaryData = new DictionaryData();
                        dictionaryData.setWord(jsonObject.getString("word"));
                        // Extract meaning details
                        JSONArray meaningArray = jsonObject.getJSONArray("meanings");
                        for (int j = 0; j < meaningArray.length(); j++) {
                            JSONObject meaningObject = meaningArray.getJSONObject(j);
                            dictionaryData.setPartOfSpeech(meaningObject.getString("partOfSpeech"));
                            dictionaryData.setDefination(meaningObject.getJSONArray("definitions").getJSONObject(0).getString("definition"));

                            // Extract synonyms and antonyms (assuming first definition)
                            JSONArray synonyms = meaningObject.optJSONArray("synonyms");
                            if (synonyms != null) {
                                List<String> synonymList = new ArrayList<>();
                                for (int k = 0; k < synonyms.length(); k++) {
                                    synonymList.add(synonyms.getString(k));
                                }
                                dictionaryData.setSynonyms(synonymList.toString());
                            }
                            JSONArray antonyms = meaningObject.optJSONArray("antonyms");
                            if (antonyms != null) {
                                List<String> antonymList = new ArrayList<>();
                                for (int k = 0; k < antonyms.length(); k++) {
                                    antonymList.add(antonyms.getString(k));
                                }
                                dictionaryData.setAntonyms(antonymList.toString());
                            }
                        dictionaryArrayList.add(dictionaryData);
                        setDataToUI();
                    }
                }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {

        };
        queue.add(request);
    }
    }



