package com.example.account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.EditText;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.view.View.OnClickListener;


import com.example.account.Entity.ItemData;
import com.example.account.Entity.ItemEntity;

import java.util.ArrayList;
import java.util.List;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private static final String TAG="ManActivity";
    private RecyclerView mlist;
    private List<ItemData> mdata;
    private ImageButton button;

    //创建按钮
    private ImageButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //RecyclerView Building:
        setContentView(R.layout.activity_main);
        //Find the RecyclerView
        mlist = (RecyclerView) this.findViewById(R.id.recyclerview);
        //Prepare for data
        initData();

        button=(ImageButton) findViewById(R.id.button_add);
        button.setOnClickListener(this);


/*
        //Button Building:
        add = (ImageButton) findViewById(R.id.button_add);
        //设置ImageButton监听器:
        add.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                //
                switch (v.getId()){
                    case R.id.button_add:
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Selection:");
                        builder.setMessage("Have a choice!");

                        builder.setPositiveButton("Good!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(MainActivity.this,"Yes",Toast.LENGTH_SHORT).show();
                            }
                        });

                        builder.setNegativeButton("Bad!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(MainActivity.this,"No",Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.create().show();
                        break;
                }
            }

        });
*/


    }

    //Init Data;
    private void initData(){
        //创建用于存放数据的集合
        mdata = new ArrayList<>();

        for(int i =0; i<ItemEntity.icons.length; i++){
            ItemData data= new ItemData();
            data.icon = ItemEntity.icons[i];
            data.title="Number " + i + " !";
            data.money= (char) i;
            //添加到集合中
            mdata.add(data);
        }
        //LayoutManager:
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);

        mlist.setLayoutManager(layoutManager);
        //Build Adapter
        ListViewAdapter adapter= new ListViewAdapter(mdata);
        //Make Adapter into Recyclerview;
        mlist.setAdapter(adapter);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            //List_View
            case R.id.list_view_vertical_stander:
                Log.d(TAG,"点击了");
                break;
            case R.id.list_view_vertical_reserve:
                Log.d(TAG,"点击了");
                break;
            case R.id.list_view_horizontal_stander:
                Log.d(TAG,"点击了");
                break;
            case R.id.list_view_horizontal_reserve:
                Log.d(TAG,"点击了");
                break;

                //Grid_View
            case R.id.grid_view_vertical_stander:
                break;
            case R.id.grid_view_vertical_reserve:
                break;
            case R.id.grid_view_horizontal_reserve:
                break;
            case R.id.grid_view_horizontal_stander:
                break;

                //Stagger_View
            case R.id.stagger_view_vertical_stander:
                break;
            case R.id.stagger_view_vertical_reserve:
                break;
            case R.id.stagger_view_horizontal_stander:
                break;
            case R.id.stagger_view_horizontal_reserve:
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}