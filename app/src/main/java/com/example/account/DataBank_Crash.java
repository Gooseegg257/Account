package com.example.account;

import android.content.Context;

import com.example.account.Entity.ItemCrash;
import com.example.account.Entity.ItemCrash;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBank_Crash {
    public static final String DATA_FILE_NAME = "data2";
    private final Context context;
    ItemCrash itemData;

    public DataBank_Crash(Context context) {
        this.context=context;
    }

    @SuppressWarnings("unchecked")
    public ItemCrash loadData() {
        itemData =new ItemCrash();
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(context.openFileInput(DATA_FILE_NAME));
            itemData = (ItemCrash) objectInputStream.readObject();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return itemData;
    }

    public void saveData() {
        ObjectOutputStream objectOutputStream=null;
        try{
            objectOutputStream = new ObjectOutputStream(context.openFileOutput(DATA_FILE_NAME, Context.MODE_PRIVATE));
            objectOutputStream.writeObject(itemData);
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}