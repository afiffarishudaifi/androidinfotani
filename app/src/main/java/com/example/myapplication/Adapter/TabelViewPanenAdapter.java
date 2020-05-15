package com.example.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.PanenModel;
import com.example.myapplication.R;

import java.util.List;

public class TabelViewPanenAdapter extends RecyclerView.Adapter {

        private List panenList;

        public TabelViewPanenAdapter(List panenList) {
            this.panenList = panenList;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.
                    from(parent.getContext()).
                    inflate(R.layout.list_panen, parent, false);

            return new RowViewHolder(itemView);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            RowViewHolder rowViewHolder = (RowViewHolder) holder;

            int rowPos = rowViewHolder.getAdapterPosition();

            if (rowPos == 0) {

                rowViewHolder.txtNo.setBackgroundResource(R.color.abu);
                rowViewHolder.txtKomoditas.setBackgroundResource(R.color.abu);
                rowViewHolder.txtTgl.setBackgroundResource(R.color.abu);
                rowViewHolder.txtHasilawal.setBackgroundResource(R.color.abu);
                rowViewHolder.txtHasilsisa.setBackgroundResource(R.color.abu);
                rowViewHolder.txtHarga.setBackgroundResource(R.color.abu);

                rowViewHolder.txtNo.setText("No.");
                rowViewHolder.txtKomoditas.setText("Komoditas");
                rowViewHolder.txtTgl.setText("Tanggal Panen");
                rowViewHolder.txtHasilawal.setText("Hasil Awal");
                rowViewHolder.txtHasilsisa.setText("Sisa");
                rowViewHolder.txtHarga.setText("Harga");

            } else {
                PanenModel modal = (PanenModel) panenList.get(rowPos - 1);

                rowViewHolder.txtNo.setBackgroundResource(R.color.abu);
                rowViewHolder.txtKomoditas.setBackgroundResource(R.color.abu);
                rowViewHolder.txtTgl.setBackgroundResource(R.color.abu);
                rowViewHolder.txtHasilawal.setBackgroundResource(R.color.abu);
                rowViewHolder.txtHasilsisa.setBackgroundResource(R.color.abu);
                rowViewHolder.txtHarga.setBackgroundResource(R.color.abu);

                rowViewHolder.txtNo.setText(modal.getNo());
                rowViewHolder.txtKomoditas.setText(modal.getKomoditas());
                rowViewHolder.txtTgl.setText(modal.getTgl());
                rowViewHolder.txtHasilawal.setText(modal.getHasilawal());
                rowViewHolder.txtHasilsisa.setText(modal.getHasilsisa());
                rowViewHolder.txtHarga.setText(modal.getHarga());
            }
        }

        @Override
        public int getItemCount() {
            return panenList.size() + 1;
        }

        public class RowViewHolder extends RecyclerView.ViewHolder {
            TextView txtNo;
            TextView txtKomoditas;
            TextView txtTgl;
            TextView txtHasilawal;
            TextView txtHasilsisa;
            TextView txtHarga;

            RowViewHolder(View itemView) {
                super(itemView);
                txtNo = itemView.findViewById(R.id.txtNo);
                txtKomoditas = itemView.findViewById(R.id.txtKomoditas);
                txtTgl = itemView.findViewById(R.id.txtTgl);
                txtHasilawal = itemView.findViewById(R.id.txtHasilawal);
                txtHasilsisa = itemView.findViewById(R.id.txtHasilsisa);
                txtHarga = itemView.findViewById(R.id.txtHarga);
            }
        }
}
