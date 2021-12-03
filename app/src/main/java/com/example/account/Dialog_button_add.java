package com.example.account;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/*
*自定义一个Dialog用于监听按钮；
* Dialog需要与button_add布局文件相结合；
* Dialog_button_add需要有数据接收，同时要利用数据创建一个新的Recyclerview；
*/
public class Dialog_button_add extends Dialog implements View.OnClickListener{

    private Context context;
    private TextView text1,text3;
    private EditText text2,text4;
    private DatePicker date;
    private Spinner spin;
    private LeaveMyDialogListener listener;

    public interface LeaveMyDialogListener{
        public void onClick(View view);
    }

    public Dialog_button_add(Context context){
        super(context);
        this.context=context;
    }

    public Dialog_button_add(Context context,int theme,LeaveMyDialogListener listener){
        super(context,theme);
        this.context=context;
        this.listener=listener;
    }

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        this.setContentView(R.layout.button_add);
        text1=(TextView) findViewById(R.id.button_add_name_text);
        text2=(EditText) findViewById(R.id.button_add_name);
        text3=(TextView) findViewById(R.id.button_add_value_text);
        text4=(EditText) findViewById(R.id.button_add_value);
        spin=(Spinner) findViewById(R.id.button_add_picture);
        date=(DatePicker) findViewById(R.id.button_add_date);

    }

    @Override
    public void onClick(View view) {
        listener.onClick(view);
    }
}
