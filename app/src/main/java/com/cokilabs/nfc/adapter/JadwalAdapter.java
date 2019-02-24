package com.cokilabs.nfc.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cokilabs.nfc.R;
import com.cokilabs.nfc.model.Jadwal;
import com.cokilabs.nfc.ui.ReaderActivity;

import java.util.ArrayList;
import java.util.Objects;


public class JadwalAdapter extends RecyclerView.Adapter<JadwalAdapter.MyViewHolder> {
    private ArrayList<Jadwal> jadwalList;
    private Context context;
    private View v;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView jam_awal, jam_akhir, kode_makul, nama_makul, ruang, peserta, sks, dosen1, dosen2;
        public RelativeLayout relativeLayout;
        public MyViewHolder(View v) {
            super(v);
            relativeLayout = v.findViewById(R.id.rl_jadwal);
            jam_awal = v.findViewById(R.id.jam_awal);
            jam_akhir = v.findViewById(R.id.jam_akhir);
            kode_makul = v.findViewById(R.id.kode_makul);
            nama_makul = v.findViewById(R.id.makul);
            ruang = v.findViewById(R.id.ruang);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public JadwalAdapter(ArrayList<Jadwal> jadwalArrayList, Context context) {
        this.jadwalList = jadwalArrayList;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public JadwalAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_jadwal_mhs, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.jam_awal.setText(jadwalList.get(position).getJamAwal());
        holder.jam_akhir.setText(jadwalList.get(position).getJamAkhir());
        holder.kode_makul.setText(jadwalList.get(position).getKodeMakul());
        holder.nama_makul.setText(jadwalList.get(position).getNamaMakul());
        holder.ruang.setText("Ruang: "+jadwalList.get(position).getRuangKuliah());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent = new Intent(context, ReaderActivity.class);
                intent.putExtra("jadwal",jadwalList.get(position));
                context.startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the l ayout manager)
    @Override
    public int getItemCount() {
        return jadwalList.size();
    }
}