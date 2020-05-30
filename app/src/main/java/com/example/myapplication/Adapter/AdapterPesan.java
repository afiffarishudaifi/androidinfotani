package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.KonfirmasiPesananActivity;
import com.example.myapplication.Model.ModelPesan;
import com.example.myapplication.R;
import com.example.myapplication.RiwayatPesanActivity;

import java.util.ArrayList;

public class AdapterPesan extends RecyclerView.Adapter<AdapterPesan.PesanViewHolder>  {
    private ArrayList<ModelPesan> itemPesan;
    private Context context;

    public static class PesanViewHolder extends RecyclerView.ViewHolder{
        public TextView txtNoP, txtNamaP, txtKomoditasP, txtTglP, txtJmlP, txtBiayaP, txtIdP;


        public PesanViewHolder(@NonNull View itemView){
            super(itemView);

            txtNoP = itemView.findViewById(R.id.txtNoP);
            txtNamaP = itemView.findViewById(R.id.txtNamaP);
            txtKomoditasP = itemView.findViewById(R.id.txtKomoditasP);
            txtTglP = itemView.findViewById(R.id.txtTglP);
            txtJmlP = itemView.findViewById(R.id.txtJmP);
            txtBiayaP = itemView.findViewById(R.id.txtTotP);
            txtIdP = itemView.findViewById(R.id.txtIdP);

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
    public void onBindViewHolder(@NonNull final AdapterPesan.PesanViewHolder holder, int position){
        final ModelPesan temItem = itemPesan.get(position);
        if(temItem.getNo() == 0){
            holder.txtNoP.setText("No");
            holder.txtIdP.setText("ID Pesan");
            holder.txtNamaP.setText("Nama Perusahaan");
            holder.txtKomoditasP.setText("Komoditas");
            holder.txtTglP.setText("Tanggal");
            holder.txtJmlP.setText("Jumlah Pesan (Kg)");
            holder.txtBiayaP.setText("Total Biaya (Rp)");
        }else {
            holder.txtNoP.setText(temItem.getNo() + "");
            holder.txtIdP.setText(temItem.getIdPesan());
            holder.txtNamaP.setText(temItem.getNamaP());
            holder.txtKomoditasP.setText(temItem.getKomoditasP());
            holder.txtTglP.setText(temItem.getTglP());
            holder.txtJmlP.setText(temItem.getJmlP() + " Kg");
            holder.txtBiayaP.setText("Rp " + temItem.getBiayaP());
        }
        holder.txtNoP.setBackgroundResource(R.color.abu);
        holder.txtNamaP.setBackgroundResource(R.color.abu);
        holder.txtKomoditasP.setBackgroundResource(R.color.abu);
        holder.txtTglP.setBackgroundResource(R.color.abu);
        holder.txtJmlP.setBackgroundResource(R.color.abu);
        holder.txtBiayaP.setBackgroundResource(R.color.abu);

        final String status = temItem.getStatusP().trim();
        context = holder.itemView.getContext();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status.equals("1")) {
                    Intent sendData = new Intent(context, KonfirmasiPesananActivity.class);
                    sendData.putExtra("idPK", temItem.getIdPesan().trim());
                    sendData.putExtra("namaPK", temItem.getNamaP().trim());
                    sendData.putExtra("komoditasPK", temItem.getKomoditasP().trim());
                    sendData.putExtra("tglPK", temItem.getTglP().trim());
                    sendData.putExtra("jmlPK", temItem.getJmlP().trim());
                    sendData.putExtra("biayaPK", temItem.getBiayaP().trim());
                    sendData.putExtra("idPanenK", temItem.getIdPanen().trim());
                    context.startActivity(sendData);
                }else{
                    if(!status.equals("2")) {
                        Toast.makeText(holder.itemView.getContext(), "Klik Data Pada Riwayat Pemesanan Untuk Konfirmasi Pesanan", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount(){
        return itemPesan.size();
    }

}
