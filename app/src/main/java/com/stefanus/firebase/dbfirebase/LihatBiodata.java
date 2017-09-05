package com.stefanus.firebase.dbfirebase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

/**
 * Created by Stefanus on 05/09/2017.
 */

public class LihatBiodata extends AppCompatActivity {

    private String selected;

    private DatabaseReference root;
    private FirebaseDatabase firebaseDatabase;

    private TextView tvNama, tvTtl, tvJk, tvAlamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_biodata);

        tvNama = (TextView) findViewById(R.id.tvNama);
        tvTtl = (TextView) findViewById(R.id.tvTtl);
        tvJk = (TextView) findViewById(R.id.tvJk);
        tvAlamat = (TextView) findViewById(R.id.tvAlamat);

        selected = getIntent().getStringExtra(BiodataModel.TAG_NAMA);
        firebaseDatabase = FirebaseDatabase.getInstance();
        root = firebaseDatabase.getReference().child(selected);

        root.addValueEventListener(valueGetBiodata);
    }

    private void setBiodata(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();
        while (i.hasNext()) {
            String alamat = String.valueOf(((DataSnapshot) i.next()).getValue());
            String jk = String.valueOf(((DataSnapshot) i.next()).getValue());
            String nama = String.valueOf(((DataSnapshot) i.next()).getValue());
            String ttl = String.valueOf(((DataSnapshot) i.next()).getValue());
            tvNama.setText(nama);
            tvTtl.setText(ttl);
            tvJk.setText(jk);
            tvAlamat.setText(alamat);
        }
    }

    private ValueEventListener valueGetBiodata = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            setBiodata(dataSnapshot);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
