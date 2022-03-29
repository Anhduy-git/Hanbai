package com.example.androidapp.Data.ClientData;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "client_table")
public class Client implements Parcelable {
    //Attribute
    @PrimaryKey(autoGenerate = true)
    private int clientId;
    private String clientName;
    private String clientNumber;
    private String clientAddress;
    private String clientEmail;
    private String clientBank;
    private String imageDir;

    public Client(String clientName, String clientNumber, String clientAddress,
                  String clientEmail, String clientBank, String imageDir) {
        this.clientName = clientName;
        this.clientNumber = clientNumber;
        this.clientAddress = clientAddress;
        this.clientEmail = clientEmail;
        this.clientBank = clientBank;
        this.imageDir = imageDir;
    }



    public int getClientId() {
        return clientId;
    }
    public void setClientId(int id) {
        this.clientId = id;
    }
    public String getClientName() {
        return clientName;
    }
    public String getClientNumber() {
        return clientNumber;
    }
    public String getClientAddress() {
        return clientAddress;
    }
    public String getClientEmail() { return clientEmail; }
    public String getClientBank() { return clientBank; }
    public String getImageDir() {
        return imageDir;
    }

    public void setImageDir(String imageDir) {
        this.imageDir = imageDir;
    }


    protected Client(Parcel in) {
        clientId = in.readInt();
        clientName = in.readString();
        clientNumber = in.readString();
        clientAddress = in.readString();
        clientEmail = in.readString();
        clientBank = in.readString();
        imageDir = in.readString();
    }

    public static final Creator<Client> CREATOR = new Creator<Client>() {
        @Override
        public Client createFromParcel(Parcel in) {
            return new Client(in);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(clientId);
        dest.writeString(clientName);
        dest.writeString(clientNumber);
        dest.writeString(clientAddress);
        dest.writeString(clientEmail);
        dest.writeString(clientBank);
        dest.writeString(imageDir);
    }
}
