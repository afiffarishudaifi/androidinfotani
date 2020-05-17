package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.ModelPanen;
import com.example.myapplication.R;

import java.util.ArrayList;

public class AdapterPanen extends RecyclerView.Adapter<AdapterPanen.PanenViewHolder>  {
    private ArrayList<ModelPanen> itemPanen;

    public static class PanenViewHolder extends RecyclerView.ViewHolder{
        public TextView txtNo, txtKomoditas, txtHasilawal, txtHasilsisa, txtTgl, txtHarga;

        public PanenViewHolder(@NonNull View itemView){
            super(itemView);

            txtNo = itemView.findViewById(R.id.txtNo);
            txtKomoditas = itemView.findViewById(R.id.txtKomoditas);
            txtHasilawal = itemView.findViewById(R.id.txtHasilawal);
            txtHasilsisa = itemView.findViewById(R.id.txtHasilsisa);
            txtTgl = itemView.findViewById(R.id.txtTgl);
            txtHarga = itemView.findViewById(R.id.txtHarga);

        }
    }

    public AdapterPanen(ArrayList<ModelPanen> itemPanens){
        itemPanen = itemPanens;
    }

    @NonNull
    @Override
    public AdapterPanen.PanenViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_panen, parent, false);
        AdapterPanen.PanenViewHolder pvh = new AdapterPanen.PanenViewHolder(v);

        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPanen.PanenViewHolder holder, int position){
        ModelPanen temItem = itemPanen.get(position);
        if(temItem.getNo() == 0){
            holder.txtNo.setText("No");
        }else{
            holder.txtNo.setText(temItem.getNo() + "");
        }
            holder.txtKomoditas.setText(temItem.getKomoditas());
            holder.txtHasilawal.setText(temItem.getHasil()+" Kg");
            holder.txtHasilsisa.setText(temItem.getSisa()+" Kg");
            holder.txtHarga.setText("Rp "+temItem.getHarga());
            holder.txtTgl.setText(temItem.getTglPanen());

        holder.txtNo.setBackgroundResource(R.color.abu);
        holder.txtKomoditas.setBackgroundResource(R.color.abu);
        holder.txtHasilawal.setBackgroundResource(R.color.abu);
        holder.txtHasilsisa.setBackgroundResource(R.color.abu);
        holder.txtHarga.setBackgroundResource(R.color.abu);
        holder.txtTgl.setBackgroundResource(R.color.abu);
    }

    @Override
    public int getItemCount(){
        return itemPanen.size();
    }
}
