package com.example.androidapp.Data.ClientData;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import java.util.List;

public class ClientViewModel extends AndroidViewModel {
    private ClientRepository repository;
    private LiveData<List<Client>> allClients;

    public ClientViewModel(@NonNull Application application) {
        super(application);
        repository = new ClientRepository(application);
        allClients = repository.getAllClients();
    }

    public void insertClient(Client client) {
        repository.insertClient(client);
    }

    public void updateClient(Client client) {
        repository.updateClient(client);
    }

    public void deleteClient(Client client) {
        repository.deleteClient(client);
    }

    public LiveData<List<Client>> getAllClients() {
        return allClients;
    }
}