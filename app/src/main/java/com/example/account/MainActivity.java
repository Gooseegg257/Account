/*
* Account记账本App
* 移动软件开发课程设计
* 编写者：Robin-Goose
*版本号Version：1.0.0
* 日期Date：2021-21-31
*
*/

package com.example.account;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.preference.PreferenceManager;
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

import com.example.account.Entity.ItemCrash;
import com.example.account.Entity.ItemData;
import com.example.account.Entity.ItemEntity;

import java.util.ArrayList;
import java.util.List;
/*
    Account记账本App
    功能：记账；每条账单存储账单类型、账单备注、账单金额以及账单日期；
 */
public class MainActivity extends AppCompatActivity {


    public static final int REQUEST_CODE_ADD=123;
    public static final int REQUEST_CODE_EDIT=REQUEST_CODE_ADD+1;
    public static final int RESULT_CODE_ADD_DATA =324;

//   声明自定义类的数据；
    ItemData charge;
    private String NAME,TIME;
    private int VALUE;
    private String Inout;

    /*
    * 布局控件声明
    * */
    private TextView all_text;
    private TextView crash_in;
    private TextView crash_out;
    private TextView crash_all;
    private ImageButton button;

    private double item_in;
    private double item_out;
    private double item_all;

//  声明两个类，一个存放RecyclreView的Item，一个存放顶部数据
    private List<ItemData> itemDatas;
    public ItemCrash itemCrash;

    private myRecyclerViewAdapter recyclerViewAdapter;

    private DataBank_Crash dataBank_crash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //RecyclerView Building:
        setContentView(R.layout.activity_main);

        //Prepare for data
        initData();
        initItem();

//      初始化RecyclerView、自定义的LayoutManager
        RecyclerView mainRecyclerView=findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        mainRecyclerView.setLayoutManager(layoutManager);

//      初始化自定义的Adapter
        mainRecyclerView.setAdapter(new myRecyclerViewAdapter(itemDatas));
        recyclerViewAdapter=new myRecyclerViewAdapter(itemDatas);
        mainRecyclerView.setAdapter(recyclerViewAdapter);

//       添加总额默认界面

        all_text=(TextView)findViewById(R.id.item_count_all_text);
        crash_all=(TextView)findViewById(R.id.item_count_all);
        crash_in=(TextView)findViewById(R.id.item_count_in_text);
        crash_out=(TextView)findViewById(R.id.item_count_out_text);

        String item_in=Double.toString(itemCrash.crash_in);
        String item_out=Double.toString(itemCrash.crash_out);
        String item_all=Double.toString(itemCrash.crash_all);

        crash_all.setText(item_all);
        crash_in.setText(item_in);
        crash_out.setText(item_out);

//        添加默认动画效果
        mainRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        分割线添加
        mainRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

//        添加按钮初始化以及单击事件监听；
        button=(ImageButton) findViewById(R.id.button_add);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,AddActivity.class);
                launcherAdd.launch(intent);
            }
        });

//        自定义RecyclerView长按事件；
        recyclerViewAdapter.setOnItemClickListener(new myRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemLongClick(View view, int pos) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(),view);
                popupMenu.getMenuInflater().inflate(R.menu.longclick_menu,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch(menuItem.getItemId()){
                            case R.id.item_delete:

                                String oldvalue=itemDatas.get(pos).getMoney();
                                String oldinout=itemDatas.get(pos).getInout();

                                double oldmoney=Double.parseDouble(oldvalue);
                                if(oldinout.equals("+")){
                                    double item_in=itemCrash.getCrash_in();
                                    double item_in2=item_in-oldmoney;
                                    double itemall=itemCrash.getCrash_all();
                                    itemall=itemall-oldmoney;
                                    itemCrash.setCrash_all(itemall);
                                    itemCrash.setCrash_in(item_in2);

                                    String all=Double.toString(itemall);
                                    String in=Double.toString(item_in2);

                                    crash_all.setText(all);
                                    crash_in.setText(in);
                                    dataBank_crash.saveData();

                                }else if(oldinout.equals("-")){
                                    double item_out=itemCrash.getCrash_out();
                                    double item_out2=item_out-oldmoney;
                                    double itemall=itemCrash.getCrash_all();
                                    itemall=itemall+oldmoney;
                                    itemCrash.setCrash_all(itemall);
                                    itemCrash.setCrash_out(item_out2);

                                    String all=Double.toString(itemall);
                                    String out=Double.toString(item_out2);

                                    crash_all.setText(all);
                                    crash_out.setText(out);
                                    dataBank_crash.saveData();
                                }
                                itemDatas.remove(pos);
                                recyclerViewAdapter.notifyItemRemoved(pos);
                                dataBank.saveData();
                                break;

                            case R.id.item_change:
                                Intent intent=new Intent(MainActivity.this,AddActivity.class);
                                intent.putExtra("position",pos);
                                String title=itemDatas.get(pos).getTitle();
                                intent.putExtra("name",title);
                                String Money=itemDatas.get(pos).getMoney();
                                double money=Double.parseDouble(Money);
                                intent.putExtra("money",money);
                                intent.putExtra("date",itemDatas.get(pos).getDate());
                                intent.putExtra("inout",itemDatas.get(pos).getInout());
                                intent.putExtra("picture",itemDatas.get(pos).getIcon());
                                launcherEdit.launch(intent);
                                break;
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }

//    自定义一个用于Activity之间数据传输的函数，其功能为添加一个RecyclerView的Item
    ActivityResultLauncher<Intent> launcherAdd= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Intent data=result.getData();
            int resultCode=result.getResultCode();
            if(resultCode == RESULT_CODE_ADD_DATA)
            {
                if(null==data)return;
                String returnname = data.getStringExtra("name");
//                String returnvalue = data.getStringExtra("value");
                String returndate = data.getStringExtra("date");
                int returnpicture = data.getIntExtra("picture", -1);
                double returnmoney=data.getFloatExtra("money",-1);
                int returnposition = data.getIntExtra("position", itemDatas.size());
                int returninout =data.getIntExtra("inout",-1);
                int icon=ItemEntity.icons[returnpicture-1];

                //添加到收入或支出总计算值中；
                if(returninout==1){
                    double item_in=itemCrash.getCrash_in();
                    double item_in2=item_in+returnmoney;
                    double itemall=itemCrash.getCrash_all();
                    itemall=itemall+returnmoney;
                    itemCrash.setCrash_in(item_in2);
                    itemCrash.setCrash_all(itemall);

                    String all=Double.toString(itemall);
                    String in=Double.toString(item_in2);

                    crash_all.setText(all);
                    crash_in.setText(in);

                    dataBank_crash.saveData();

                }else if(returninout==0){
                    double item_out=itemCrash.getCrash_out();
                    double item_out2=item_out+returnmoney;
                    double itemall=itemCrash.getCrash_all();
                    itemall=itemall-returnmoney;
                    itemCrash.setCrash_all(itemall);
                    itemCrash.setCrash_out(item_out2);

                    String all=Double.toString(itemall);
                    String out=Double.toString(item_out2);

                    crash_all.setText(all);
                    crash_out.setText(out);

                    dataBank_crash.saveData();
                }
                String returnvalue=Double.toString(returnmoney);
                itemDatas.add(returnposition,
                        new ItemData(returnname, returnvalue, returndate, icon,returninout));
                recyclerViewAdapter.notifyItemInserted(returnposition);
                dataBank.saveData();
            }
        }
    });

//    自定义一个用于Activity之间数据传输的函数，其功能为修改一个RecyclerView的Item
    ActivityResultLauncher<Intent> launcherEdit= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Intent data=result.getData();
            int resultCode=result.getResultCode();
            if(resultCode == RESULT_CODE_ADD_DATA)
            {
                if(null==data)return;
                int returnposition = data.getIntExtra("position", itemDatas.size());

//                先撤销上一次数值增减再计算；
                String oldvalue=itemDatas.get(returnposition).getMoney();
                String oldinout=itemDatas.get(returnposition).getInout();

                double oldmoney=Double.parseDouble(oldvalue);
                if(oldinout.equals("+")){
                    double item_in=itemCrash.getCrash_in();
                    double item_in2=item_in-oldmoney;
                    double itemall=itemCrash.getCrash_all();
                    itemall=itemall-oldmoney;
                    itemCrash.setCrash_all(itemall);
                    itemCrash.setCrash_in(item_in2);

                    String all=Double.toString(itemall);
                    String in=Double.toString(item_in2);

                    crash_all.setText(all);
                    crash_in.setText(in);
                    dataBank_crash.saveData();

                }else if(oldinout.equals("-")){
                    double item_out=itemCrash.getCrash_out();
                    double item_out2=item_out-oldmoney;
                    double itemall=itemCrash.getCrash_all();
                    itemall=itemall+oldmoney;
                    itemCrash.setCrash_all(item_all);
                    itemCrash.setCrash_out(item_out2);

                    String all=Double.toString(itemall);
                    String out=Double.toString(item_out2);

                    crash_all.setText(all);
                    crash_out.setText(out);
                    dataBank_crash.saveData();
                }
                String returnname = data.getStringExtra("name");
                String returndate = data.getStringExtra("date");
                int returnpicture = data.getIntExtra("picture", -1);
                double returnmoney=data.getFloatExtra("money",-1);

                int returninout =data.getIntExtra("inout",-1);
                int icon=ItemEntity.icons[returnpicture-1];


                //添加到收入或支出总计算值中；
                if(returninout==1){
                    double item_in=itemCrash.getCrash_in();
                    double item_in2=item_in+returnmoney;
                    double itemall=itemCrash.getCrash_all();
                    itemall=itemall+returnmoney;
                    itemCrash.setCrash_all(item_all);
                    itemCrash.setCrash_in(item_in2);

                    String all=Double.toString(itemall);
                    String in=Double.toString(item_in2);

                    crash_all.setText(all);
                    crash_in.setText(in);
                    dataBank_crash.saveData();

                }else if(returninout==0){
                    double item_out=itemCrash.getCrash_out();
                    double item_out2=item_out+returnmoney;
                    double itemall=itemCrash.getCrash_all();
                    itemall=itemall-returnmoney;
                    itemCrash.setCrash_all(itemall);
                    itemCrash.setCrash_out(item_out2);

                    String all=Double.toString(itemall);
                    String out=Double.toString(item_out2);

                    crash_all.setText(all);
                    crash_out.setText(out);
                    dataBank_crash.saveData();
                }
                String returnvalue=Double.toString(returnmoney);
                itemDatas.get(returnposition).setTitle(returnname);
                itemDatas.get(returnposition).setMoney(returnvalue);
                itemDatas.get(returnposition).setDate(returndate);
                itemDatas.get(returnposition).setIcon(icon);
                itemDatas.get(returnposition).setInout(returninout);
                recyclerViewAdapter.notifyItemChanged(returnposition);
                dataBank.saveData();
            }
        }
    });


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
////        if(requestCode==REQUEST_CODE_ADD){
//            if(resultCode == RESULT_CODE_ADD_DATA) {
//
////            }
//
//        }
//
//    }

    private DataBank dataBank;

    //Init Data;
    public void initData(){
        dataBank=new DataBank(MainActivity.this);
        itemDatas=dataBank.loadData();
    }

//    初始化顶部数据及布局控件
    public void initItem(){
        dataBank_crash=new DataBank_Crash(MainActivity.this);
        itemCrash=dataBank_crash.loadData();

//        itemCrash=new ItemCrash();
//        itemCrash.crash_in=0;
//        itemCrash.crash_out=0;
//        itemCrash.crash_all=0;

    }

//    自定义RecyclerView的Adapter
    private static class myRecyclerViewAdapter extends RecyclerView.Adapter {
        private List<ItemData> itemDatas;

        public myRecyclerViewAdapter(List<ItemData> itemDatas) {
            this.itemDatas = itemDatas;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_item, parent, false);

            return new MyViewHolder(view);
        }

//        重写onBindViewHolder，其中需要确定数据的填充以及长按事件的监听；
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

//        自定义ViewHolder，同样需要绑定控件及其数据
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

//        自定义一个RecyclerView的接口用于实现长按点击事件；
        private OnItemClickListener onItemClickListener;
        public interface OnItemClickListener{
            void onItemLongClick(View view , int pos);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }


    }
}