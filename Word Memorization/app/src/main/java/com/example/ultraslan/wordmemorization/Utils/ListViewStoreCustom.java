package com.example.ultraslan.wordmemorization.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ultraslan.wordmemorization.Model.StoreProducts;
import com.example.ultraslan.wordmemorization.R;

import java.util.List;

public class ListViewStoreCustom extends BaseAdapter {

    private Context mContext;
    private List<StoreProducts> mProducts;

    public ListViewStoreCustom(Context mContext, List<StoreProducts> mProducts) {
        this.mContext = mContext;
        this.mProducts = mProducts;
    }

    @Override
    public int getCount() {
        return mProducts.size();
    }

    @Override
    public Object getItem(int i) {
        return mProducts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_customstore,null);
        }

        TextView tvPrice = (TextView) view.findViewById(R.id.tv_storeprice);
        TextView tvDesc = (TextView) view.findViewById(R.id.tv_storeinfo);

        tvPrice.setText(String.valueOf(mProducts.get(i).getPrice() + " TL"));
        tvDesc.setText(mProducts.get(i).getDescription());

        view.setTag(mProducts.get(i).getId());

        return view;
    }
}
