//package com.example.myapplication.Adapter;
//
//import android.app.Activity;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.example.myapplication.Model.DataDesa;
//import com.example.myapplication.R;
//
//import java.util.List;
//
//public class AdapterDesa extends BaseAdapter {
//    private Activity activity;
//    private LayoutInflater inflater;
//    private List<DataDesa> item;
//
//    public AdapterDesa(Activity activity, List<DataDesa> item) {
//        this.activity = activity;
//        this.item = item;
//    }
//
//    @Override
//    public int getCount() {
//        return item.size();
//    }
//
//    @Override
//    public Object getItem(int location) {
//        return item.get(location);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        if (inflater == null)
//            inflater = (LayoutInflater) activity
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        if (convertView == null)
//            convertView = inflater.inflate(R.layout.list_desa, null);
//
//        TextView pendidikan = (TextView) convertView.findViewById(R.id.desa);
//
//        DataDesa data;
//        data = item.get(position);
//
//        pendidikan.setText(data.getNamaDesa());
//
//        return convertView;
//    }
//}
