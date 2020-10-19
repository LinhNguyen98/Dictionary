package com.example.dictionary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.TabActivity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity{

    EditText etxtTimkiem;
    Button btnTim;
    TextView txtKetqua;
    TextView numbers;
    TextView family;
    TextView colors;
    TextView phrases;

    TabHost tabHost;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        CategoryAdapter adapter = new CategoryAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        etxtTimkiem = (EditText) findViewById( R.id.etxtTimkiem);
        btnTim = (Button) findViewById(R.id.btnTim);
        txtKetqua = (TextView) findViewById(R.id.txtKetqua);
        btnTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(etxtTimkiem.getText().toString()))
                {
                    Toast.makeText(MainActivity.this , "Không được để từ khóa trống",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("meaning");
                    mRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String keyword = etxtTimkiem.getText().toString();
                            if(dataSnapshot.child(keyword).exists())
                            {
                                txtKetqua.setText(dataSnapshot.child(keyword).getValue().toString());
                            }else
                            {
                                Toast.makeText(MainActivity.this, "Không tìm thấy kết quả", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();
        TabHost.TabSpec spec = host.newTabSpec("Vocabulary");
        spec.setContent(R.id.vocabulary);
        spec.setIndicator("Vocabulary");
        host.addTab(spec);


        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.search);
        spec.setIndicator("Search");
        host.addTab(spec);



        /*TabHost.TabSpec spec = getTabHost().newTabSpec("tag1");
        spec.setContent(R.id.vocabulary);
        spec.setIndicator("Vocabulary",getResources().getDrawable(R.drawable.list));
        getTabHost().addTab(spec);
        spec = getTabHost().newTabSpec("tag2");
        spec.setContent(R.id.search);
        spec.setIndicator("Search",getResources().getDrawable(R.drawable.restaurant));
        getTabHost().addTab(spec);
        getTabHost().setCurrentTab(0); */


    }





}