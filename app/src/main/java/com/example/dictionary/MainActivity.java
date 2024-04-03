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
import java.util.List;

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

        inputET = findViewById(R.id.search_input);
        searchBTN = findViewById(R.id.search_btn);
        wordTV = findViewById(R.id.word_textview);
        dictionaryRecyclerView = findViewById(R.id.meaning_recycler_view);
        dictionaryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputWord = inputET.getText().toString().trim();
                if (!inputWord.isEmpty()) {
                    getDictionaryData(inputWord);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a word", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getDictionaryData(String inputWord) {
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://api.dictionaryapi.dev/api/v2/entries/en/" + inputWord;
        StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    dictionaryArrayList.clear(); // Clear previous data
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String word = jsonObject.getString("word");

                        // Extract synonyms and antonyms from meanings array if available
                        JSONArray meaningsArray = jsonObject.getJSONArray("meanings");
                        for (int j = 0; j < meaningsArray.length(); j++) {
                            JSONObject meaningObject = meaningsArray.getJSONObject(j);

                            // Check if synonyms and antonyms are available for this meaning
                            JSONArray synonymsArray = meaningObject.optJSONArray("synonyms");
                            JSONArray antonymsArray = meaningObject.optJSONArray("antonyms");

                            // If synonyms and antonyms are not found at meaning level, search at root level
                            if (synonymsArray == null) {
                                synonymsArray = jsonObject.optJSONArray("synonyms");
                            }
                            if (antonymsArray == null) {
                                antonymsArray = jsonObject.optJSONArray("antonyms");
                            }

                            String partOfSpeech = meaningObject.getString("partOfSpeech");
                            JSONArray definitionsArray = meaningObject.getJSONArray("definitions");
                            for (int k = 0; k < definitionsArray.length(); k++) {
                                JSONObject definitionObject = definitionsArray.getJSONObject(k);
                                String definition = definitionObject.getString("definition");

                                // Initialize a new DictionaryData object for each definition
                                DictionaryData dictionaryData = new DictionaryData();
                                dictionaryData.setWord(word);
                                dictionaryData.setPartOfSpeech(partOfSpeech);
                                dictionaryData.setDefination(definition);

                                // Retrieve synonyms
                                if (synonymsArray != null && synonymsArray.length() > 0) {
                                    StringBuilder synonymsBuilder = new StringBuilder();
                                    for (int m = 0; m < synonymsArray.length(); m++) {
                                        synonymsBuilder.append(synonymsArray.getString(m));
                                        if (m < synonymsArray.length() - 1) {
                                            synonymsBuilder.append(", ");
                                        }
                                    }
                                    dictionaryData.setSynonyms(synonymsBuilder.toString());
                                } else {
                                    dictionaryData.setSynonyms("");
                                }

                                // Retrieve antonyms
                                if (antonymsArray != null && antonymsArray.length() > 0) {
                                    StringBuilder antonymsBuilder = new StringBuilder();
                                    for (int n = 0; n < antonymsArray.length(); n++) {
                                        antonymsBuilder.append(antonymsArray.getString(n));
                                        if (n < antonymsArray.length() - 1) {
                                            antonymsBuilder.append(", ");
                                        }
                                    }
                                    dictionaryData.setAntonyms(antonymsBuilder.toString());
                                } else {
                                    dictionaryData.setAntonyms("");
                                }

                                // Add the DictionaryData object to the list
                                dictionaryArrayList.add(dictionaryData);
                            }
                        }
                    }
                    setDataToUI();
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }

    private void setDataToUI() {
        // Set the word in the TextView
        if (!dictionaryArrayList.isEmpty()) {
            wordTV.setText(dictionaryArrayList.get(0).getWord());
        } else {
            // If no data is available, clear the word TextView
            wordTV.setText("");
        }
        // Set the adapter to the RecyclerView
        DictionaryAdapter dictionaryAdapter = new DictionaryAdapter(dictionaryArrayList);
        dictionaryRecyclerView.setAdapter(dictionaryAdapter);
    }
}
