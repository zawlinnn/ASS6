package com.example.aroundthearth.lab7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText txtName;
    private EditText txtid;
    private EditText txttel;
    private EditText txtemail;
    private Button btnSave;
    private ListView dataView;
    private MySQLConnect mySQLConnect;
    private List<String> items;
    private ArrayAdapter<String> adt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        update();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySQLConnect.sentData(txtid.getText().toString(),txtName.getText().toString(),txttel.getText().toString(),txtemail.getText().toString());
                items.add(txtid.getText().toString()+"\n"+ txtName.getText().toString()+"\n"+txttel.getText().toString()+"\n"+txtemail.getText().toString());
                adt.notifyDataSetChanged();
            }
        });
    }
    public void update(){
        items = mySQLConnect.getData();
        adt = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        dataView.setAdapter(adt);
    }
    public void init(){
        txtName = (EditText)findViewById(R.id.txtName);
        txtid = (EditText)findViewById(R.id.txtid);
        txttel = (EditText)findViewById(R.id.txttel);
        txtemail = (EditText)findViewById(R.id.txtemail);
        btnSave = (Button)findViewById(R.id.btnSave);
        dataView = (ListView)findViewById(R.id.dataView);
        mySQLConnect = new MySQLConnect(MainActivity.this);
    }
}
