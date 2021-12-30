package com.example.account;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.account.Entity.ItemData;
import com.example.account.Entity.ItemEntity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageButton button;

    public static final int REQUEST_CODE_ADD=123;
    public static final int REQUEST_CODE_EDIT=REQUEST_CODE_ADD+1;
    public static final int RESULT_CODE_ADD_DATA =1;

    ItemData charge;
    private String NAME,TIME;
    private int VALUE;
    private String Inout;

    private List<ItemData> itemDatas;

    private myRecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //RecyclerView Building:
        setContentView(R.layout.activity_main);

        //Prepare for data
        initData();

        RecyclerView mainRecyclerView=findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        mainRecyclerView.setLayoutManager(layoutManager);

        mainRecyclerView.setAdapter(new myRecyclerViewAdapter(itemDatas));
        recyclerViewAdapter=new myRecyclerViewAdapter(itemDatas);
        mainRecyclerView.setAdapter(recyclerViewAdapter);

//        添加默认动画效果
        mainRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        分割线添加
        mainRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        button=(ImageButton) findViewById(R.id.button_add);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent();
                intent.setClass(com.example.account.MainActivity.this,com.example.account.AddActivity.class);
                startActivityForResult(intent,1);
            }
        });

        recyclerViewAdapter.setOnItemClickListener(new myRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemLongClick(View view, int pos) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(),view);
                popupMenu.getMenuInflater().inflate(R.menu.menu,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch(menuItem.getItemId()){
                            case R.id.item_delete:
                                itemDatas.remove(pos);
                                recyclerViewAdapter.notifyItemRemoved(pos);
                                dataBank.saveData();
                                break;
                            case R.id.item_add:
                                Toast.makeText(MainActivity.this,"添加",Toast.LENGTH_LONG).show();
                                break;
                            case R.id.item_change:
                                Toast.makeText(MainActivity.this,"修改了",Toast.LENGTH_LONG).show();
                                break;
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==REQUEST_CODE_ADD){
            if(resultCode == RESULT_CODE_ADD_DATA) {
                String returnname = data.getStringExtra("name");
//                String returnvalue = data.getStringExtra("value");
                String returndate = data.getStringExtra("date");
                int returnpicture = data.getIntExtra("picture", -1);
                float returnmoney=data.getFloatExtra("money",-1);
                int returnposition = data.getIntExtra("position", itemDatas.size());
                int returninout =data.getIntExtra("inout",-1);
                int icon=ItemEntity.icons[returnpicture-1];

                String returnvalue=Float.toString(returnmoney);
                itemDatas.add(returnposition,
                        new ItemData(returnname, returnvalue, returndate, icon,returninout));
                recyclerViewAdapter.notifyItemInserted(returnposition);
                dataBank.saveData();
//            }

        }

    }
    private DataBank dataBank;

    //Init Data;
    public void initData(){
        dataBank=new DataBank(MainActivity.this);
        itemDatas=dataBank.loadData();
    }

    private static class myRecyclerViewAdapter extends RecyclerView.Adapter {
        private List<ItemData> itemDatas;

        public myRecyclerViewAdapter(List<ItemData> itemDatas) {
            this.itemDatas = itemDatas;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_view, parent, false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            MyViewHolder holder1 = (MyViewHolder) holder;

            holder1.getPicture().setImageResource(itemDatas.get(position).getIcon());
            holder1.getName().setText(itemDatas.get(position).getTitle());
            holder1.getMoney().setText(itemDatas.get(position).getMoney());
            holder1.getDate().setText(itemDatas.get(position).getDate());
            holder1.getInout().setText(itemDatas.get(position).getInout());

            if(onItemClickListener != null){
                holder1.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {

                        onItemClickListener.onItemLongClick(holder1.itemView, holder.getBindingAdapterPosition());
                        return false;
                    }
                });
            }

        }


        @Override
        public int getItemCount() {
            return itemDatas.size();
        }

        private class MyViewHolder extends RecyclerView.ViewHolder  {

            public static final int CONTEXT_MENU_ID_ADD = 1;
            public static final int CONTEXT_MENU_ID_UPDATE = 2;
            public static final int CONTEXT_MENU_ID_DELETE = 3;

            //            Recylcerview数据
            private final ImageFilterView Picture;
            private final AppCompatTextView Name;
            private final AppCompatTextView Money;
            private final AppCompatTextView Date;
            private final AppCompatTextView Inout;



            public MyViewHolder(View view) {
                super(view);

                this.Picture = view.findViewById(R.id.imageFilterView);
                this.Name = view.findViewById(R.id.item_title);
                this.Money = view.findViewById(R.id.item_money);
                this.Date = view.findViewById(R.id.item_date);
                this.Inout= view.findViewById(R.id.item_inout);
            }

            public ImageView getPicture() {
                return Picture;
            }

            public TextView getName() {
                return Name;
            }

            public TextView getMoney() {
                return Money;
            }

            public TextView getDate() {
                return Date;
            }

            public TextView getInout(){
                return Inout;
            }
        }

//        Recyclerview添加ItemData
//        public void addItemData(){
//            if(itemDatas == null){
//                itemDatas = new ArrayList<ItemData>();
//            }
//            itemDatas.add();
//            notifyItemInserted(0);
//        }

        private OnItemClickListener onItemClickListener;
        public interface OnItemClickListener{
            void onItemLongClick(View view , int pos);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }


    }
}