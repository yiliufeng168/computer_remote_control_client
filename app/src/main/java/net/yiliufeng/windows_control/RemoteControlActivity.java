package net.yiliufeng.windows_control;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.yiliufeng.windows_control.MyAdapter.MoreInstructionAdapter;
import net.yiliufeng.windows_control.dao.Command;
import net.yiliufeng.windows_control.dao.Location;
import net.yiliufeng.windows_control.myBeams.Computer;
import net.yiliufeng.windows_control.myBeams.Instruction;
import net.yiliufeng.windows_control.myView.ControllerView;
import net.yiliufeng.windows_control.service.UdpService;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class RemoteControlActivity extends AppCompatActivity {

    private Computer computer;
    private Typeface fontFace;

    private TextView tv_control_title;
    private TextView tv_back;
    private EditText et_msg;
    private Button btn_send_msg;
    private ControllerView ctl_view;

    private UdpService udpService;
    private MoreInstructionAdapter moreInstructionAdapter;

    // 变量
    private String cmd;
    private String type;
    private String TAG = "遥控页面";
    private List<Instruction> instructionList;
    private MyApplication application;
    private DbManager dbManager;
    private PopupMenu popupMenu;
    private Context applicationContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_control);
        application=(MyApplication)this.getApplication();
        applicationContext = getApplicationContext();
        try {
            dbManager= x.getDb(application.daoConfig);
        } catch (DbException e) {
            e.printStackTrace();
        }
        Intent intent=getIntent();
        computer=(Computer)intent.getSerializableExtra("computer");
        loadInstructionList();
        bindView();
        udpService = new UdpService(computer.getHost(),computer.getPort(),computer.getPassword());
    }
    private void loadInstructionList(){
        if(instructionList == null){
            instructionList=new ArrayList<Instruction>();
        }
        instructionList.clear();
        List<Instruction> tempList = null ;
        try {

            tempList = dbManager.selector(Instruction.class).where("computer_id","=",computer.getId()).findAll();

//            instructionList = dbManager.selector(Instruction.class).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (tempList!=null){
            instructionList.addAll(tempList);
        }

    }
    private void bindView(){

        et_msg = findViewById(R.id.et_msg);
        btn_send_msg = findViewById(R.id.btn_send_msg);
        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Command command = new Command(Command.INPUT_MSG, et_msg.getText().toString(),null);
                udpService.sendCommand(command);
                et_msg.setText("");
            }
        });
        tv_control_title = findViewById(R.id.tv_control_title);
        tv_control_title.setText(computer.getName());
        fontFace = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        tv_back = findViewById(R.id.tv_back);
        tv_back.setTypeface(fontFace);

        ctl_view = findViewById(R.id.ctl_view);
        ctl_view.setMoveListener(new ControllerView.MoveListener() {
            @Override
            public void move(float dx, float dy) {
                type = Command.MOUSE;
                Gson gson = new GsonBuilder().disableHtmlEscaping().create();;
                Location location = new Location(dx, dy);
                String json = gson.toJson(location);
                Command command = new Command(type, json,"");
                udpService.sendCommand(command);
            }
        });
    }

    public void showMusicWindow(View view){
        Dialog dialog = new Dialog(RemoteControlActivity.this, R.style.DialogTheme);
        View inflate = View.inflate(this, R.layout.music_window, null);

        dialog.setContentView(inflate);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        dialog.findViewById(R.id.btn_open_music).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                udpService.sendCommand(new Command(Command.PREDEFINED,"open_cloudmusic",""));
            }
        });
        dialog.findViewById(R.id.btn_close_music).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                udpService.sendCommand(new Command(Command.PREDEFINED,"close_cloudmusic",""));
            }
        });
        dialog.findViewById(R.id.btn_wy_volume_down).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                udpService.sendCommand(new Command(Command.KEYBOARD,"ctrl+alt+down",""));
            }
        });
        dialog.findViewById(R.id.btn_wy_volume_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                udpService.sendCommand(new Command(Command.KEYBOARD,"ctrl+alt+up",""));
            }
        });
        dialog.findViewById(R.id.btn_prev_song).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                udpService.sendCommand(new Command(Command.KEYBOARD,"ctrl+alt+left",""));
            }
        });
        dialog.findViewById(R.id.btn_next_song).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                udpService.sendCommand(new Command(Command.KEYBOARD,"ctrl+alt+right",""));
            }
        });
        dialog.findViewById(R.id.btn_stop_song).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                udpService.sendCommand(new Command(Command.KEYBOARD,"ctrl+alt+p",""));
            }
        });
    }


    public void showMoreWindow(View view){

        Dialog dialog = new Dialog(RemoteControlActivity.this, R.style.DialogTheme);
        View inflate = View.inflate(this, R.layout.more_window, null);
        RecyclerView recyclerView = inflate.findViewById(R.id.rv_more_cmd);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(RemoteControlActivity.this,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        moreInstructionAdapter = new MoreInstructionAdapter(instructionList);
        recyclerView.setAdapter(moreInstructionAdapter);
        moreInstructionAdapter.setOnItemLongClickListener(new MoreInstructionAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, MoreInstructionAdapter.ViewName viewName, final int position) {
                Log.i(TAG, "onItemLongClick: "+position);
                popupMenu = new PopupMenu(applicationContext, v);
                popupMenu.getMenuInflater().inflate(R.menu.computer_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.it_edit:
                                Intent intent=new Intent(RemoteControlActivity.this,AddInstructionActivity.class);
                                intent.putExtra("computer", computer);
                                intent.putExtra("instruction",instructionList.get(position));
                                startActivityForResult(intent,1);

                                overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
                                break;
                            case R.id.it_delete:
                                try {
                                    Instruction instruction = instructionList.get(position);
                                    dbManager.deleteById(Instruction.class,instruction.getId());
                                    instructionList.remove(position);
                                    moreInstructionAdapter.notifyItemRemoved(position);
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        moreInstructionAdapter.setOnItemClickListener(new MoreInstructionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, MoreInstructionAdapter.ViewName viewName, int position) {
                Log.i(TAG, "onItemClick: "+position);
                Instruction instruction = instructionList.get(position);

                udpService.sendInstruction(instruction);
            }

        });
        dialog.setContentView(inflate);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        dialog.findViewById(R.id.btn_add_custom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RemoteControlActivity.this,AddInstructionActivity.class);
                intent.putExtra("computer", computer);
//                        startActivity(intent);
                startActivityForResult(intent,1);
                overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
            }
        });

    }

    public void doClick(View view){
        switch (view.getId()) {
            case R.id.btn_volume_up:
                type = Command.PREDEFINED;
                cmd = "volume_up";
                break;
            case R.id.btn_volume_down:
                type = Command.PREDEFINED;
                cmd = "volume_down";
                break;
            case R.id.btn_mute:
                type = Command.PREDEFINED;
                cmd = "mute";
                break;

            case R.id.btn_shutdown:
                type = Command.PREDEFINED;
                cmd = "shutdown";
                break;

            case R.id.btn_mouse_left_click:
                type = Command.PREDEFINED;
                cmd = "mouse_left_click";
                break;
            case R.id.btn_mouse_right_click:
                type = Command.PREDEFINED;
                cmd = "mouse_right_click";
                break;
            case R.id.btn_backspace:
                type = Command.PREDEFINED;
                cmd = "backspace";
                break;
            case R.id.btn_close_window:
                type = Command.PREDEFINED;
                cmd = "close_window";
                break;
        }
        Log.i(TAG, "doClick: start");
        Command command = new Command(type,cmd,"");
        command.setType(type);
        command.setCommand(cmd);
        udpService.sendCommand(command);

    }

    public void goBack(View view){
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loadInstructionList();

        moreInstructionAdapter.notifyDataSetChanged();

        Log.i("TAG", "onActivityResult: 触发");
    }
}