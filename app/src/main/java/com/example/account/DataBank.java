package com.example.account;

import android.content.Context;

import com.example.account.Entity.ItemData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBank {
    public static final String DATA_FILE_NAME = "data";
    private final Context context;
    List<ItemData> itemDataList;

    public DataBank(Context context) {
        this.context=context;
    }

    @SuppressWarnings("unchecked")
    public List<ItemData> loadData() {
        itemDataList =new ArrayList<>();
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(context.openFileInput(DATA_FILE_NAME));
            itemDataList = (ArrayList<ItemData>) objectInputStream.readObject();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return itemDataList;
    }

    public void saveData() {
        ObjectOutputStream objectOutputStream=null;
        try{
            objectOutputStream = new ObjectOutputStream(context.openFileOutput(DATA_FILE_NAME, Context.MODE_PRIVATE));
            objectOutputStream.writeObject(itemDataList);
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