package com.cokilabs.nfc.ui;

import android.app.PendingIntent;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.Tag;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cokilabs.nfc.R;
import com.cokilabs.nfc.adapter.JadwalAdapter;
import com.cokilabs.nfc.model.Jadwal;
import com.cokilabs.nfc.model.Mahasiswa;
import com.cokilabs.nfc.network.APIClient;
import com.cokilabs.nfc.network.APIInterface;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.nfc.NdefRecord.createMime;

public class ReaderActivity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback {

    NfcAdapter nfcAdapter;
    TextView textView;

    private PendingIntent mPendingIntent;
    Jadwal jadwal;
    Button back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        textView = (TextView) findViewById(R.id.textview);

        jadwal = (Jadwal) getIntent().getSerializableExtra("jadwal");

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Check for available NFC Adapter
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Button cek = findViewById(R.id.cek);
        cek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ReaderActivity.this, StatusActivity.class);
                i.putExtra("jadwal", jadwal);
                startActivity(i);
            }
        });

        // Register callback
//        nfcAdapter.setNdefPushMessageCallback(this, ReaderActivity.this);
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass())
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }

    private static NdefMessage getTestMessage() {
        byte[] mimeBytes = "application/com.android.cts.verifier.nfc"
                .getBytes(Charset.forName("US-ASCII"));
        byte[] id = new byte[] {1, 3, 3, 7};
        byte[] payload = "CTS Verifier NDEF Push Tag".getBytes(Charset.forName("US-ASCII"));
        return new NdefMessage(new NdefRecord[] {
                new NdefRecord(NdefRecord.TNF_MIME_MEDIA, mimeBytes, id, payload)
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        nfcAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
        nfcAdapter.setNdefPushMessageCallback(this, this);
    }

    // sending message
    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        return getTestMessage();
    }


    private NdefMessage[] getNdefMessages(Intent intent) {
        Parcelable[] rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        if (rawMessages != null) {
            NdefMessage[] messages = new NdefMessage[rawMessages.length];
            for (int i = 0; i < messages.length; i++) {
                messages[i] = (NdefMessage) rawMessages[i];
            }
            return messages;
        } else {
            return null;
        }
    }

    static String displayByteArray(byte[] bytes) {
        String res="";
        StringBuilder builder = new StringBuilder().append("[");
        for (int i = 0; i < bytes.length; i++) {
            res+=(char)bytes[i];
        }
        return res.substring(7);
    }

    // displaying message
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        NdefMessage[] messages = getNdefMessages(intent);
//        Toast.makeText(this, "Read successfully", Toast.LENGTH_SHORT).show();
        Log.e("hasil", messages[0].toString());
        try {
            Log.e("hasil", new String(messages[0].toByteArray(), "UTF-8").substring(7));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e("log", e.toString());
        }
        textView.setText(displayByteArray(messages[0].toByteArray()));
//        queryAPIPostCard(displayByteArray(messages[0].toByteArray()));
    }

//    @Override
//    public NdefMessage createNdefMessage(NfcEvent event) {
//        String text = ("Beam me up, Android!\n\n" +
//                "Beam Time: " + System.currentTimeMillis());
//        NdefMessage msg = new NdefMessage(
//                new NdefRecord[] { createMime(
//                        "application/vnd.com.example.android.beam", text.getBytes())
//                        /**
//                         * The Android Application Record (AAR) is commented out. When a device
//                         * receives a push with an AAR in it, the application specified in the AAR
//                         * is guaranteed to run. The AAR overrides the tag dispatch system.
//                         * You can add it back in to guarantee that this
//                         * activity starts when receiving a beamed message. For now, this code
//                         * uses the tag dispatch system.
//                        */
//                        //,NdefRecord.createApplicationRecord("com.example.android.beam")
//                });
//        return msg;
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        // Check to see that the Activity started due to an Android Beam
//        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
//            processIntent(getIntent());
//        }
//    }
//
//    @Override
//    public void onNewIntent(Intent intent) {
//        // onResume gets called after this to handle the intent
//        setIntent(intent);
//    }
//
//    /**
//     * Parses the NDEF Message from the intent and prints to the TextView
//     */
//    void processIntent(Intent intent) {
//        textView = (TextView) findViewById(R.id.textview);
//        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
//                NfcAdapter.EXTRA_NDEF_MESSAGES);
//        // only one message sent during the beam
//        NdefMessage msg = (NdefMessage) rawMsgs[0];
//        // record 0 contains the MIME type, record 1 is the AAR, if present
//        textView.setText(new String(msg.getRecords()[0].getPayload()));
//    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_reader);
//
//        NdefRecord uriRecord = new NdefRecord(
//                NdefRecord.TNF_ABSOLUTE_URI ,
//                "http://developer.android.com/index.html".getBytes(Charset.forName("US-ASCII")),
//                new byte[0], new byte[0]);
//
//
//
////        NdefRecord mimeRecord = NdefRecord.createMime("application/vnd.com.example.android.beam",
////                "Beam me up, Android".getBytes(Charset.forName("US-ASCII")));
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//
////        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
//
//        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
//            Parcelable[] rawMessages =
//                    intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
//            if (rawMessages != null) {
//                NdefMessage[] messages = new NdefMessage[rawMessages.length];
//                for (int i = 0; i < rawMessages.length; i++) {
//                    messages[i] = (NdefMessage) rawMessages[i];
//                }
//                // Process the messages array.
//
//            }
//        }
//    }

    public void queryAPIPostCard(String nim) {

        APIInterface service = APIClient.getClient();
        final Gson gson = new Gson();

        Call<ResponseBody> call = service.postKehadiran(nim);

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
                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                            r.play();

                            String data = json.getString("data");
                            Mahasiswa mahasiswa = gson.fromJson(data, Mahasiswa.class);


                            Toast.makeText(getApplicationContext(), api_message, Toast.LENGTH_SHORT).show();

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
}
