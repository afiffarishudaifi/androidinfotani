package com.example.myapplication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.Model.DataKomoditas;
import com.example.myapplication.R;

import java.util.List;

public class AdapterKomoditas extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataKomoditas> item;

    public AdapterKomoditas(Activity activity, List<DataKomoditas> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int location) {
        return item.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_komoditas, null);

        TextView komoditas = (TextView) convertView.findViewById(R.id.komoditas);

        DataKomoditas data;
        data = item.get(position);

        komoditas.setText(data.getNamaKomoditas());

        return convertView;
    }
}
