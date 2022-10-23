package net.yiliufeng.windows_control;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.yiliufeng.windows_control.myBeams.Computer;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.Serializable;

public class AddComputerActivity extends AppCompatActivity {

    private Typeface fontFace;

    private TextView tv_back;
    private EditText et_computer_name;
    private EditText et_computer_host;
    private EditText et_computer_port;
    private EditText et_computer_password;
    private Button btn_save_computer;

    private MyApplication application;
    private DbManager dbManager;

    private Computer computer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_computer);
        application=(MyApplication)this.getApplication();
        try {
            dbManager= x.getDb(application.daoConfig);
        } catch (DbException e) {
            e.printStackTrace();
        }
        Intent intent=getIntent();
        computer = (Computer)intent.getSerializableExtra("computer");

        bindView();
        bindListener();
    }


    private void bindView(){
        fontFace = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        tv_back = findViewById(R.id.tv_back);
        tv_back.setTypeface(fontFace);
        et_computer_name = findViewById(R.id.et_computer_name);
        et_computer_host = findViewById(R.id.et_computer_host);
        et_computer_port = findViewById(R.id.et_computer_port);
        et_computer_password = findViewById(R.id.et_computer_password);
        btn_save_computer = findViewById(R.id.btn_save_computer);
        if (computer!=null){
            Log.i("onCreate:ylf", "onCreate: "+computer.toString());
            et_computer_name.setText(computer.getName());
            et_computer_host.setText(computer.getHost());
            et_computer_port.setText(computer.getPort().toString());
            et_computer_password.setText(computer.getPassword());
        }else {
            computer = new Computer();
        }
    }

    public void goBack(View view){
        finish();
    }

    private void bindListener(){
        btn_save_computer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String computer_name = et_computer_name.getText().toString();
                String computer_host = et_computer_host.getText().toString();
                Integer port = new Integer(et_computer_port.getText().toString());
                String password = et_computer_password.getText().toString();

                computer.setName(computer_name);
                computer.setHost(computer_host);
                computer.setPort(port);
                computer.setPassword(password);
                try {
                    if (computer.getId()!=null){
                        dbManager.saveOrUpdate(computer);

                    }else {
                        dbManager.save(computer);
                    }
                    finish();
                } catch (DbException e) {
                    e.printStackTrace();
                }
//                if (computer!=null){
//                    dbManager.update(Computer.class,);
//                }else {
//                    try {
//                        dbManager.saveOrUpdate(computer);
//                        finish();
//                    } catch (DbException e) {
//                        e.printStackTrace();
//                    }
//
//                }


            }
        });
    }
}