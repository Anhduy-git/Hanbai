package com.example.androidapp.Data.ClientData;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ClientDao {
    @Insert
    void insertClient(Client client);

    @Update
    void updateClient(Client client);

    @Delete
    void deleteClient(Client client);

    @Query("DELETE FROM client_table")
    void deleteAllClients();

    @Query("SELECT * FROM client_table ORDER BY clientName")
    LiveData<List<Client>> getAllClients();

    @Query("SELECT * FROM client_table WHERE clientName =:newName AND clientAddress =:newAddress AND clientNumber =:newPhoneNumber" +
            " AND clientEmail =:newEmail AND clientBank =:newBank")
    List<Client> checkClientExist(String newName, String newAddress, String newPhoneNumber,
                                  String newEmail, String newBank);



}
