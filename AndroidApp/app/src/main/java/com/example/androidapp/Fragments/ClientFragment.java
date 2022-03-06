package com.example.androidapp.Fragments;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.Activities.InfoClientActivity;
import com.example.androidapp.Activities.NewClientActivity;
import com.example.androidapp.Data.ClientData.Client;
import com.example.androidapp.Data.ClientData.ClientAdapter;
import com.example.androidapp.Data.ClientData.ClientViewModel;
import com.example.androidapp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClientFragment extends Fragment {
    private ClientViewModel clientViewModel;
    private Button btnAddClient;
    private ClientAdapter clientAdapter;
    private List<Client> mListClient;
    private EditText edtSearchBar;
    private MediaPlayer sound = null;

    public ClientFragment() {
        //Empty on purpose
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_client, container, false);
        initUI(v);
        mListClient = new ArrayList<>();

        //Sound
        //sound = MediaPlayer.create(getActivity(), R.raw.confirm_sound);

        //Create RecyclerView
        RecyclerView rcvData = v.findViewById(R.id.client_recycler);;
        //rcvData.setHasFixedSize(true);
        rcvData.setLayoutManager(new LinearLayoutManager(v.getContext()));

        //Create Client Adapter
        clientAdapter = new ClientAdapter(mListClient);
        rcvData.setAdapter(clientAdapter);

        //Create view model
        clientViewModel = new ViewModelProvider(getActivity()).get(ClientViewModel.class);
        clientViewModel.getAllClients().observe(getActivity(), new Observer<List<Client>>() {
            //Method DISPLAY the list on screen
            @Override
            public void onChanged(List<Client> clients) {
                //use for filter
                clientAdapter.setClient(clients);
                //use for animation
                clientAdapter.submitList(clients);

            }
        });

        //Create search bar listener for SEARCH METHOD
        edtSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Empty on purpose
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Empty on purpose
            }

            @Override
            public void afterTextChanged(Editable s) {
                clientAdapter.getFilter().filter(s.toString());
            }
        });

        //Click on an item to view info
        clientAdapter.setOnItemClickListener(new ClientAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(com.example.androidapp.Data.ClientData.Client client) {
                Intent intent = new Intent(getActivity(), InfoClientActivity.class);
                intent.putExtra(InfoClientActivity.EXTRA_CLIENT, client);
                activityResultLauncher.launch(intent);
            }
        });

        //Delete item
        clientAdapter.setOnItemClickDelListener(new ClientAdapter.OnItemClickDelListener() {
            @Override
            public void onItemClickDel(Client client) {
                confirmDelDialog(client);
            }
        });

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
                            //Log.d("NAME", newClient.getClientName());
                            clientViewModel.insertClient(newClient);
                        }
                    }
                }
            }
    );

    private void initUI(View view){
        btnAddClient = view.findViewById(R.id.add_client_button);
        edtSearchBar = view.findViewById(R.id.client_search_bar);
    }

    private void confirmDelDialog(Client client) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.alert_dialog_delete, (RelativeLayout)getView().findViewById(R.id.layout_dialog)
        );
        builder.setView(view);
        AlertDialog alertDialog = builder.create();

        //confirm delete btn
        view.findViewById(R.id.confirm_dialog_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                //sound.start();
                //delete the old image
                File oldImage = new File(client.getImageDir());
                boolean deleted = oldImage.delete();
                clientViewModel.deleteClient(client);
            }
        });
        //cancel btn
        view.findViewById(R.id.cancel_dialog_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
}
