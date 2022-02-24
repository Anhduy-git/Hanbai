package com.example.androidapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.androidapp.Activities.NewClientActivity;
import com.example.androidapp.Data.ClientData.Client;
import com.example.androidapp.Data.ClientData.ClientViewModel;
import com.example.androidapp.R;

public class ClientFragment extends Fragment {
    public static final int ADD_CLIENT_REQUEST = 1;

    private ClientViewModel clientViewModel;
    private Button btnAddClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_client, container, false);
        initUI(v);

        //Add client button
        btnAddClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewClientActivity.class);
                activityResultLauncher.launch(intent);
            }
        });

        return v;
    }

    //On activity result
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == -1) {
                        Intent intent = result.getData();

                        if (intent != null) {
                            Client newClient = intent.getParcelableExtra(NewClientActivity.EXTRA_CLIENT);
                            Log.d("NAME", newClient.getClientName());
                            clientViewModel.insertClient(newClient);
                        }
                    }
                }
            }
    );


    private void initUI(View view){
        btnAddClient = view.findViewById(R.id.add_client_button);
    }

}
