package com.example.androidapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.androidapp.Data.ClientData.Client;
import com.example.androidapp.Data.ClientData.ClientSelectOnlyAdapter;
import com.example.androidapp.Data.ClientData.ClientViewModel;
import androidx.lifecycle.Observer;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.R;

import java.util.List;


public class SubClientActivity extends AppCompatActivity {


    public static final String EXTRA_CLIENT =
            "com.example.androidapp.EXTRA_CLIENT";

    private Button btnBack;

    private List<Client> mListClient;
    private ClientViewModel clientViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_contact);

        initUi();

        //Create Recycler View
        RecyclerView rcvData = findViewById(R.id.client_recycler);
        rcvData.setLayoutManager(new LinearLayoutManager(this));

        final ClientSelectOnlyAdapter clientSelectOnlyAdapter = new ClientSelectOnlyAdapter(mListClient);
        rcvData.setAdapter(clientSelectOnlyAdapter);

        //Create client view model
        clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);
        clientViewModel.getAllClients().observe(this, new Observer<List<Client>>() {
            @Override
            public void onChanged(List<Client> clients) {
                clientSelectOnlyAdapter.setClient(clients);
            }
        });

        clientSelectOnlyAdapter.setOnItemClickListener(new ClientSelectOnlyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Client client) {
                Intent data = new Intent();
                data.putExtra(EXTRA_CLIENT, client);

                setResult(RESULT_OK, data);
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    void initUi(){
        btnBack = findViewById(R.id.back_btn);
    }
}