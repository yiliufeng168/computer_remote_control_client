package net.yiliufeng.windows_control;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import net.yiliufeng.windows_control.MyAdapter.ComputerAdapter;
import net.yiliufeng.windows_control.myBeams.Computer;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private static String TAG = "tag_ylf";
    private Context applicationContext;


    private Typeface fontFace;
    private TextView tv_new_computer;
    private ListView computer_listView;
    private PopupMenu popupMenu;

    private MyApplication application;
    private DbManager dbManager;
    private ComputerAdapter computerAdapter;

    private List<Computer> computerList = null;
    private Computer computer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        fontFace = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        setContentView(R.layout.activity_main);
        application=(MyApplication)this.getApplication();
        try {
            dbManager= x.getDb(application.daoConfig);
        } catch (DbException e) {
            e.printStackTrace();
        }
        bindView();
        bindListener();

        applicationContext = getApplicationContext();
    }

    private void bindListener(){
        setComputerList();
        computerAdapter = new ComputerAdapter(getApplicationContext(), computerList, dbManager, fontFace);
        computer_listView.setAdapter(computerAdapter);
        computer_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this,RemoteControlActivity.class);
                intent.putExtra("computer",computerList.get(position));
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);

            }
        });
        computer_listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                computer = computerList.get(position);
                popupMenu = new PopupMenu(applicationContext, view);
                popupMenu.getMenuInflater().inflate(R.menu.computer_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.it_edit:
                                Intent intent=new Intent(MainActivity.this,AddComputerActivity.class);
                                intent.putExtra("computer", computer);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
                                break;
                            case R.id.it_delete:
                                try {

                                    dbManager.deleteById(Computer.class,computer.getId());
                                    computerList.remove(position);
                                    computerAdapter.notifyDataSetChanged();
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: s");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setComputerList();
        computerAdapter.setComputerList(computerList);
        computerAdapter.notifyDataSetInvalidated();
        Log.i(TAG, "onRestart: ");

    }


    private void bindView(){
        computer_listView = findViewById(R.id.computer_listView);
        tv_new_computer = findViewById(R.id.tv_new_computer);
        tv_new_computer.setTypeface(fontFace);
        tv_new_computer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddComputerActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
            }
        });

    }

    private void setComputerList(){
        try {
            computerList = dbManager.selector(Computer.class).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        if(computerList == null){
            computerList=new ArrayList<Computer>();
        }else {
            for(Computer album:computerList){
                Log.i("my albums" ,album.toString());
            }
        }
    }


}