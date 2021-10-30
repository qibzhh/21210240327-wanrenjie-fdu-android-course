package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

        private RecyclerView mRecyclerView;
        private SearchAdapter mSearchAdapter = new SearchAdapter();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            mRecyclerView = findViewById(R.id.rv);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(mSearchAdapter);

            List<String> items = new ArrayList<>();
            for (int i=1;i<100;i++) {
                items.add(String.valueOf(i));
            }
            Log.d("1", ""+items.size());
            mSearchAdapter.notifyItems(items);
        }
}



