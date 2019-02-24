package com.cokilabs.nfc.ui;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cokilabs.nfc.R;
import com.cokilabs.nfc.adapter.JadwalAdapter;
import com.cokilabs.nfc.model.Jadwal;
import com.cokilabs.nfc.network.APIClient;
import com.cokilabs.nfc.network.APIInterface;
import com.cokilabs.nfc.utility.CircleTransform;
import com.cokilabs.nfc.utility.RecyclerSectionItemDecoration;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PilihJadwalActivity extends AppCompatActivity {

    RecyclerView listJadwal;
    ArrayList<Jadwal> jadwals;
    JadwalAdapter adapter;
    ImageView image;
    TextView nama, nip, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_jadwal);

        jadwals = new ArrayList<>();

        listJadwal = findViewById(R.id.rv_jadwal);

        image = findViewById(R.id.dosen_image);

        nama = findViewById(R.id.dosen_name);
        nip = findViewById(R.id.dosen_nip);
        logout = findViewById(R.id.dosen_logout);

        Picasso.get().load(R.drawable.dosen).transform(new CircleTransform()).into(image);

        listJadwal.setLayoutManager(new LinearLayoutManager(this));

//        queryAPIGetJadwal();
        dummy();

        RecyclerSectionItemDecoration decoration = new RecyclerSectionItemDecoration(dipToPixels(this, 46).intValue(),true,getSectionCallback(jadwals));
        listJadwal.addItemDecoration(decoration);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(listJadwal.getContext(),
                new LinearLayoutManager(this).getOrientation());
//        listJadwal.addItemDecoration(dividerItemDecoration);

        Log.e("px", String.valueOf(dipToPixels(this, 30).intValue()));


        adapter = new JadwalAdapter(jadwals, PilihJadwalActivity.this);
        listJadwal.setAdapter(adapter);

        initToolbar("Jadwal Mengajar");
    }

    public Float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    private RecyclerSectionItemDecoration.SectionCallback getSectionCallback(final ArrayList<Jadwal> list) {
        return new RecyclerSectionItemDecoration.SectionCallback() {
            @Override
            public boolean isSection(int position) {
                return position == 0 || !list.get(position).getId_hari().equals(list.get(position - 1).getId_hari());
            }

            @Override
            public CharSequence getSectionHeader(int position) {
//                return list.get(position).subSequence(0, 1);
                return jadwals.get(position).getHari();
            }

            @Override
            public Jadwal getObject(int position) {
                return jadwals.get(position);
            }
        };
    }

    public void initToolbar(String vTitle){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View viewActionBar = getLayoutInflater().inflate(R.layout.toolbar_title, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView tvTitle = viewActionBar.findViewById(R.id.text_title);
        tvTitle.setText(vTitle);
        getSupportActionBar().setCustomView(viewActionBar, params);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_putih);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void queryAPIGetJadwal() {

        APIInterface service = APIClient.getClient();
        final Gson gson = new Gson();

        Call<ResponseBody> call = service.getJadwals();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        String res = response.body().string();

                        JSONObject json = new JSONObject(res);

                        String api_status = json.getString("status");
                        String api_message = json.getString("message");

                        if(api_status.equals("success")){
                            JSONArray list = json.getJSONArray("data");

                            int i;
                            for (i = 0; i < list.length(); i++) {
                                JSONObject obj = list.getJSONObject(i);
                                Jadwal nilai = gson.fromJson(obj.toString(), Jadwal.class);
                                jadwals.add(nilai);
                            }

                            adapter = new JadwalAdapter(jadwals, PilihJadwalActivity.this);
                            listJadwal.setAdapter(adapter);


                        }else {
                            Toast.makeText(getApplicationContext(), api_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException | IOException e) {
                        e.printStackTrace();

                    }

                }else {
                    Toast.makeText(getApplicationContext(),response.message()+" "+response.code(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void dummy(){
        Jadwal a = new Jadwal();
        a.setId(1);
        a.setJamAwal("07.00");
        a.setJamAkhir("10.30");
        a.setDosen1("Anggy Trisnawan Putra, S.Si., M.Si.");
        a.setDosen2("Aji Purwinarko, S.Si., M.Cs.");
        a.setRuangKuliah("D1-301");
        a.setJumlahPeserta(42);
        a.setSks(3);
        a.setHari("Senin");
        a.setId_hari(1);
        a.setNamaMakul("Pemrograman Berorientasi Objek");
        a.setKodeMakul("460001");
        a.setPertemuanKe(9);
        this.jadwals.add(a);

        Jadwal b = new Jadwal();
        b.setId(1);
        b.setJamAwal("12.40");
        b.setJamAkhir("14.30");
        b.setDosen1("Anggy Trisnawan Putra, S.Si., M.Si.");
        b.setDosen2("Aji Purwinarko, S.Si., M.Cs.");
        b.setRuangKuliah("D1-302");
        b.setJumlahPeserta(48);
        b.setSks(2);
        b.setHari("Selasa");
        b.setId_hari(2);
        b.setNamaMakul("Aljabar Linear Elementer");
        b.setKodeMakul("460002");
        b.setPertemuanKe(9);
        this.jadwals.add(b);

        Jadwal c = new Jadwal();
        c.setId(1);
        c.setJamAwal("12.40");
        c.setJamAkhir("14.30");
        c.setDosen1("Anggy Trisnawan Putra, S.Si., M.Si.");
        c.setDosen2("Aji Purwinarko, S.Si., M.Cs.");
        c.setRuangKuliah("D1-302");
        c.setJumlahPeserta(48);
        c.setSks(2);
        c.setHari("Selasa");
        c.setId_hari(2);
        c.setNamaMakul("Artificial Intelligence");
        c.setKodeMakul("460002");
        c.setPertemuanKe(9);
        this.jadwals.add(c);

        Jadwal d = new Jadwal();
        d.setId(1);
        d.setJamAwal("15.40");
        d.setJamAkhir("16.30");
        d.setDosen1("Anggy Trisnawan Putra, S.Si., M.Si.");
        d.setDosen2("Aji Purwinarko, S.Si., M.Cs.");
        d.setRuangKuliah("D1-302");
        d.setJumlahPeserta(48);
        d.setSks(2);
        d.setHari("Selasa");
        d.setId_hari(2);
        d.setNamaMakul("Technopreneur");
        d.setKodeMakul("460002");
        d.setPertemuanKe(9);
        this.jadwals.add(d);

        Jadwal e = new Jadwal();
        e.setId(1);
        e.setJamAwal("07.30");
        e.setJamAkhir("09.30");
        e.setDosen1("Anggy Trisnawan Putra, S.Si., M.Si.");
        e.setDosen2("Aji Purwinarko, S.Si., M.Cs.");
        e.setRuangKuliah("R.5.404");
        e.setJumlahPeserta(48);
        e.setSks(2);
        e.setHari("Selasa");
        e.setId_hari(2);
        e.setNamaMakul("Arsitektur Komputer");
        e.setKodeMakul("460002");
        e.setPertemuanKe(9);
        this.jadwals.add(d);

        Jadwal f = new Jadwal();
        f.setId(1);
        f.setJamAwal("09.40");
        f.setJamAkhir("10.30");
        f.setDosen1("Anggy Trisnawan Putra, S.Si., M.Si.");
        f.setDosen2("Aji Purwinarko, S.Si., M.Cs.");
        f.setRuangKuliah("D1-302");
        f.setJumlahPeserta(48);
        f.setSks(2);
        f.setHari("Selasa");
        f.setId_hari(2);
        f.setNamaMakul("Algoritma Genetika");
        f.setKodeMakul("460002");
        f.setPertemuanKe(9);
        this.jadwals.add(f);
    }
}
