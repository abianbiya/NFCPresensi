package com.cokilabs.nfc.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cokilabs.nfc.R;
import com.cokilabs.nfc.model.Jadwal;
import com.cokilabs.nfc.model.Mahasiswa;
import com.cokilabs.nfc.ui.ReaderActivity;

import java.util.ArrayList;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.MyViewHolder>  {
    private ArrayList<Mahasiswa> mahasiswas;
    private Context context;
    private View v;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView nama, nim, hadirpada;
        public RelativeLayout relativeLayout;

        public MyViewHolder(View v) {
            super(v);
            relativeLayout = v.findViewById(R.id.back);
            nama = v.findViewById(R.id.mahasiswa_name);
            nim = v.findViewById(R.id.mahasiswa_nim);
            hadirpada= v.findViewById(R.id.mahasiswa_time);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MahasiswaAdapter(ArrayList<Mahasiswa> mhsList, Context context) {
        this.mahasiswas = mhsList;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MahasiswaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mahasiswa, parent, false);

        MahasiswaAdapter.MyViewHolder vh = new MahasiswaAdapter.MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MahasiswaAdapter.MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.nama.setText(mahasiswas.get(position).getNama());
        holder.nim.setText(mahasiswas.get(position).getNim());
        holder.hadirpada.setText(mahasiswas.get(position).getHadirPada());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

            }
        });
    }

    // Return the size of your dataset (invoked by the l ayout manager)
    @Override
    public int getItemCount() {
        return mahasiswas.size();
    }
}
