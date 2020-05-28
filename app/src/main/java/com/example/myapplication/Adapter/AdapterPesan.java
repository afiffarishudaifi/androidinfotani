package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.ModelPesan;
import com.example.myapplication.R;

import java.util.ArrayList;

public class AdapterPesan extends RecyclerView.Adapter<AdapterPesan.PesanViewHolder>  {
    private ArrayList<ModelPesan> itemPesan;

    public static class PesanViewHolder extends RecyclerView.ViewHolder{
        public TextView txtNoP, txtNamaP, txtKomoditasP, txtTglP, txtJmlP, txtBiayaP, txtStatusP;

        public PesanViewHolder(@NonNull View itemView){
            super(itemView);

            txtNoP = itemView.findViewById(R.id.txtNoP);
            txtNamaP = itemView.findViewById(R.id.txtNamaP);
            txtKomoditasP = itemView.findViewById(R.id.txtKomoditasP);
            txtTglP = itemView.findViewById(R.id.txtTglP);
            txtJmlP = itemView.findViewById(R.id.txtJmP);
            txtBiayaP = itemView.findViewById(R.id.txtTotP);

        }
    }

    public AdapterPesan(ArrayList<ModelPesan> itemPesans){
        itemPesan = itemPesans;
    }

    @NonNull
    @Override
    public AdapterPesan.PesanViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pesan, parent, false);
        AdapterPesan.PesanViewHolder pvh = new AdapterPesan.PesanViewHolder(v);

        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPesan.PesanViewHolder holder, int position){
        ModelPesan temItem = itemPesan.get(position);
        if(temItem.getNo() == 0){
            holder.txtNoP.setText("No");
        }else{
            holder.txtNoP.setText(temItem.getNo() + "");
        }
            holder.txtNamaP.setText(temItem.getNamaP());
            holder.txtKomoditasP.setText(temItem.getKomoditasP());
            holder.txtTglP.setText(temItem.getTglP());
            holder.txtJmlP.setText(temItem.getJmlP()+" Kg");
            holder.txtBiayaP.setText("Rp "+temItem.getBiayaP());

        holder.txtNoP.setBackgroundResource(R.color.abu);
        holder.txtNamaP.setBackgroundResource(R.color.abu);
        holder.txtKomoditasP.setBackgroundResource(R.color.abu);
        holder.txtTglP.setBackgroundResource(R.color.abu);
        holder.txtJmlP.setBackgroundResource(R.color.abu);
        holder.txtBiayaP.setBackgroundResource(R.color.abu);
    }

    @Override
    public int getItemCount(){
        return itemPesan.size();
    }
}
