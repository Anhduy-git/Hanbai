package com.example.androidapp.Activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.androidapp.Data.ClientData.Client;
import com.example.androidapp.Data.ClientData.ClientSelectOnlyAdapter;
import com.example.androidapp.Data.ClientData.ClientViewModel;
import androidx.lifecycle.Observer;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        btnBack = findViewById(R.id.btn_back);
    }
}