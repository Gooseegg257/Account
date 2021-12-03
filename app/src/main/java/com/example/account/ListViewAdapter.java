package com.example.account;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.account.Entity.ItemData;

import java.util.List;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.InnerHolder>  {

    private final List<ItemData> mData;

    public ListViewAdapter(List<ItemData> data){
        this.mData = data;
    }



    @Override
    public InnerHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= View.inflate(parent.getContext(),R.layout.item_list_view,null);

        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder( ListViewAdapter.InnerHolder holder, int position) {

        holder.setData(mData.get(position));

    }

//  Return Count of list.
    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    public class InnerHolder extends RecyclerView.ViewHolder{

        private ImageFilterView micon;
        private AppCompatTextView mtitle;
        private AppCompatTextView mmoney;

        public InnerHolder(View itemView) {
            super(itemView);

            //找数据条目；
            micon= (ImageFilterView) itemView.findViewById(R.id.imageFilterView);
            mtitle = (AppCompatTextView) itemView.findViewById(R.id.item_title);
//            mmoney = (AppCompatTextView) itemView.findViewById(R.id.item_money);
        }


        public void setData(ItemData itemData){

            //设置数据；
            micon.setImageResource(itemData.icon);
            mtitle.setText(itemData.title);

        }
    }


}
