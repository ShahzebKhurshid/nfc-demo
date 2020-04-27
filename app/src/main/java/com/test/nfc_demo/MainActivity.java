package com.test.nfc_demo;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    // listview
    private ListView listView;

    // Adapter for listview
    ArrayAdapter<String> adapter;

    // Search EditText
    EditText inputSearch;


    // ArrayList for Listview
    ArrayList<HashMap<String, String>> productList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Listview Data
        String[] contacts = {" James Pepe", "Shahzeb Khurshid", "Katie P", "Boxin Chao"};

        //initialize views
        listView = findViewById(R.id.list_view);
        inputSearch = findViewById(R.id.inputSearch);

        // Adding items to listview
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.contact_name, contacts);
        //set adapter to listview
        listView.setAdapter(adapter);

        // callback for edit text
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                MainActivity.this.adapter.getFilter().filter(cs); // this does the filtering
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                //navigate to Detail activity
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("key", position);
                startActivity(intent);

            }
        });
    }

    @Override
    //To call back seek nearby NFC
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getAction() == NfcAdapter.ACTION_TAG_DISCOVERED) {
            Log.d("NFC intent", intent.toString());
        }
        if (intent.getAction() == NfcAdapter.ACTION_TECH_DISCOVERED) {
            Log.d("NFC discovered", intent.toString());
        }
    }

}
