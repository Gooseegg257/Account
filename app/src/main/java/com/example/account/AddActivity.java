package com.example.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.account.Entity.ItemEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddActivity extends AppCompatActivity implements DatePicker.OnDateChangedListener {
    private Button create,cancel;
    private TextView name1,value1;
    private EditText name2,value2;
    private DatePicker time;
    private Spinner picture,inout;
    private Intent intent1;
    private int picture_number,inout_no;

    private List<Map<String,Object>> data;

    private int year,month,day;
    private List<Map<String,Object>> inout_data;

    @Override
    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add);
        init();
        initSpinner();

//        获取DatePicker中选取的数值；
        Calendar calendar= Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);
        time.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
//                获取选中的年月日
                year=i;
                month=i1+1;
                day=i2;
            }
        });
        intent1=getIntent();
        int position= intent1.getIntExtra("position",0);

//        监听Button传输数据；
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=name2.getText().toString();
                if(name.length()>12){
                    Toast.makeText(AddActivity.this,"名称字符不多于6个！",Toast.LENGTH_LONG).show();
                    finish();
                }
                String value=value2.getText().toString();
                String date=(year+"-"+month+"-"+day);
                float money=Float.parseFloat(value);

//                intent2=new Intent(AddActivity.this,MainActivity.class);
                Intent intent2=new Intent();
                intent2.putExtra("name",name);
                intent2.putExtra("value",value);
                intent2.putExtra("date",date);
                intent2.putExtra("money",money);
                intent2.putExtra("picture",picture_number);
                intent2.putExtra("position",position);
                intent2.putExtra("inout",inout_no);
                setResult(MainActivity.RESULT_CODE_ADD_DATA,intent2);
//                startActivity(intent2);
                AddActivity.this.finish();
//                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

//    初始化控件数值，同时监听当前事件是Add事件还是Edit事件；
    private void init(){
        create=(Button) findViewById(R.id.yes);
        cancel=(Button) findViewById(R.id.no);
        name1=(TextView) findViewById(R.id.button_add_name_text);
        value1=(TextView) findViewById(R.id.button_add_value_text);
        name2=(EditText) findViewById(R.id.button_add_name);
        value2=(EditText) findViewById(R.id.button_add_value);
        time=(DatePicker) findViewById(R.id.button_add_date);

        Intent intent=getIntent();
        String returnname = intent.getStringExtra("name");
        String returndate = intent.getStringExtra("date");
        int returnpicture = intent.getIntExtra("picture", -1);
        float returnmoney=intent.getFloatExtra("money",-1);
        int returninout=intent.getIntExtra("inout",-1);
        if(returndate !=null  || returnname != null ){
            String value=Float.toString(returnmoney);
            name2.setText(returnname);
            value2.setText(value);
//            picture.setSelection(returnpicture,true);
//            inout.setSelection(returninout,true);
        }
    }


//    初始化Spinner
    private void initSpinner(){
        picture=(Spinner) findViewById(R.id.button_add_picture);
        data=new ArrayList<>();
        final SimpleAdapter s_adapter= new SimpleAdapter(this,getData(),R.layout.spinner_picture,new String[]{"no","image","text"},new int[]{R.id.picture_no,R.id.picture_picture,R.id.picture_name});
        picture.setAdapter(s_adapter);

//        点击事件
        picture.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView number=(TextView) view.findViewById(R.id.picture_no);
                picture_number=Integer.parseInt(number.getText().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        再次自定义一个Spinner用于获取账单的收支类型；
        inout=(Spinner) findViewById(R.id.button_add_inout);
        inout_data=new ArrayList<>();
        final SimpleAdapter inout_adapter=new SimpleAdapter(this,getInout_data(),R.layout.inout,new String[]{"inout"},new int[]{R.id.inout});
        //点击
        inout.setAdapter(inout_adapter);
        inout.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String yes="收入";
                TextView inout=(TextView) view.findViewById(R.id.inout);
                String yesorno = inout.getText().toString();
                int no=1;
                if(yesorno != yes){
                    no=0;
                }
                switch(no){
                    case 1:
                        inout_no=no;
                        break;
                    case 0:
                        inout_no=no;
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

//    收支类型Spinner的Map数据填充；
    private List<Map<String,Object>> getInout_data(){
        Map<String,Object> map1=new HashMap<>();
        map1.put("inout","收入");
        inout_data.add(map1);
        Map<String,Object> map2=new HashMap<>();
        map2.put("inout","支出");
        inout_data.add(map2);
        return inout_data;
    }

//    账单类型Spinner的Map数据填充；
    private List<Map<String,Object>> getData(){
        Map<String,Object> map1=new HashMap<>();
        map1.put("no","1");
        map1.put("image",R.mipmap.airplane);
        map1.put("text","旅行");
        data.add(map1);
        Map<String,Object> map2=new HashMap<>();
        map2.put("no","2");
        map2.put("image",R.mipmap.canyin);
        map2.put("text","餐饮");
        data.add(map2);
        Map<String,Object> map3=new HashMap<>();
        map3.put("no","3");
        map3.put("image",R.mipmap.chongwu);
        map3.put("text","宠物");
        data.add(map3);
        Map<String,Object> map4=new HashMap<>();
        map4.put("no","4");
        map4.put("image",R.mipmap.clothes);
        map4.put("text","衣服");
        data.add(map4);
        Map<String,Object> map5=new HashMap<>();
        map5.put("no","5");
        map5.put("image",R.mipmap.hongbao);
        map5.put("text","红包");
        data.add(map5);
        Map<String,Object> map6=new HashMap<>();
        map6.put("no","6");
        map6.put("image",R.mipmap.jiaju);
        map6.put("text","家具");
        data.add(map6);
        Map<String,Object> map7=new HashMap<>();
        map7.put("no","7");
        map7.put("image",R.mipmap.jiaoyu);
        map7.put("text","教育");
        data.add(map7);
        Map<String,Object> map8=new HashMap<>();
        map8.put("no","8");
        map8.put("image",R.mipmap.meironghufu);
        map8.put("text","美容");
        data.add(map8);
        Map<String,Object> map9=new HashMap<>();
        map9.put("no","9");
        map9.put("image",R.mipmap.muying);
        map9.put("text","母婴");
        data.add(map9);
        Map<String,Object> map10=new HashMap<>();
        map10.put("no","10");
        map10.put("image",R.mipmap.shuma);
        map10.put("text","数码");
        data.add(map10);
        Map<String,Object> map11=new HashMap<>();
        map11.put("no","11");
        map11.put("image",R.mipmap.touzi);
        map11.put("text","投资");
        data.add(map11);
        Map<String,Object> map12=new HashMap<>();
        map12.put("no","12");
        map12.put("image",R.mipmap.yiliao);
        map12.put("text","医疗");
        data.add(map12);
        Map<String,Object> map13=new HashMap<>();
        map13.put("no","13");
        map13.put("image",R.mipmap.yundong);
        map13.put("text","运动");
        data.add(map13);
        Map<String,Object> map14=new HashMap<>();
        map14.put("no","14");
        map14.put("image",R.mipmap.zhufang);
        map14.put("text","住房");
        data.add(map14);
        Map<String,Object> map15=new HashMap<>();
        map15.put("no","15");
        map15.put("image",R.mipmap.xiuxianyule);
        map15.put("text","休闲");
        data.add(map15);
        Map<String,Object> map16=new HashMap<>();
        map16.put("no","16");
        map16.put("image",R.mipmap.riyongpin);
        map16.put("text","日用");
        data.add(map16);
        return data;
    }

}
