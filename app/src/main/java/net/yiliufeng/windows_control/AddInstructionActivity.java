package net.yiliufeng.windows_control;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.yiliufeng.windows_control.MyAdapter.CmdAdapter;
import net.yiliufeng.windows_control.dao.Command;
import net.yiliufeng.windows_control.myBeams.Computer;
import net.yiliufeng.windows_control.myBeams.Instruction;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class AddInstructionActivity extends AppCompatActivity {
    private Typeface fontFace;
    private TextView tv_back;
    private TextView tv_save;
    private EditText et_instruction_name;
    private ListView lv_commands;
    private RecyclerView recyclerView;

    private List<Command> commandList;
    private MyApplication application;
    private DbManager dbManager;
    private Computer computer;
    private Instruction instruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instruction);
        Intent intent=getIntent();
        computer=(Computer)intent.getSerializableExtra("computer");
        instruction = (Instruction)intent.getSerializableExtra("instruction");
        application=(MyApplication)this.getApplication();
        try {
            dbManager= x.getDb(application.daoConfig);
        } catch (DbException e) {
            e.printStackTrace();
        }
        bindView();
    }

    private void bindView(){
        et_instruction_name = findViewById(R.id.et_instruction_name);
        fontFace = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        tv_back = findViewById(R.id.tv_back);
        tv_back.setTypeface(fontFace);
        tv_save = findViewById(R.id.tv_save);
        tv_save.setTypeface(fontFace);
        if (instruction!=null){
            et_instruction_name.setText(instruction.getName());
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            commandList = gson.fromJson(instruction.getCommand_list(),new TypeToken<ArrayList<Command>>(){}.getType());
        }else {
            instruction = new Instruction();
            commandList = new ArrayList<>();
            commandList.add(new Command(Command.PREDEFINED,"",""));
        }

        recyclerView = findViewById(R.id.rv_cmd);
        LinearLayoutManager layoutManager = new LinearLayoutManager(AddInstructionActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        final CmdAdapter adapter = new CmdAdapter(commandList,fontFace,getApplicationContext());
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CmdAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, CmdAdapter.ViewName viewName, int position) {
                switch (v.getId()){
                    case R.id.btn_add_new_command:
                        Log.i("TAG", "onItemClick: "+(position));
                        Log.i("TAG", "onItemClick: "+ commandList);
                        if (position + 1 < commandList.size()){
                            commandList.remove(position);
                            adapter.notifyDataSetChanged();
                        }else {
                            commandList.add(new Command(Command.PREDEFINED,"",""));
                            Button button = (Button)v;
                            button.setText(getResources().getText(R.string.ic_sub));
                            adapter.notifyItemInserted(position+1);
                        }
                        break;
                }
            }

            @Override
            public void onItemLongClick(View v) {

            }
        });
//        lv_commands = findViewById(R.id.lv_commands);
//        commandList.add(new Command(Command.PREDEFINED,""));
//
//        instructionAdapter = new InstructionAdapter(getApplicationContext(),fontFace,commandList);
//        lv_commands.setAdapter(instructionAdapter);
    }

    public void doClick(View view){
        switch (view.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_save:
                for (int i=0;i< recyclerView.getChildCount();i++){
                    LinearLayout layout= (LinearLayout)recyclerView.getChildAt(i);
                    EditText et_command = layout.findViewById(R.id.et_command);
                    EditText et_args = layout.findViewById(R.id.et_args);
                    EditText et_cmd_type = layout.findViewById(R.id.et_cmd_type);
                    commandList.get(i).setType(et_cmd_type.getText().toString());
                    commandList.get(i).setArgs(et_args.getText().toString());
                    commandList.get(i).setCommand(et_command.getText().toString());
                }
                Log.i("save command", "doClick: "+commandList);
                if (et_instruction_name.getText().toString().isEmpty()){
                    Toast.makeText(AddInstructionActivity.this,"指令名称不能为空！",Toast.LENGTH_SHORT).show();
                    break;
                }
                instruction.setName(et_instruction_name.getText().toString());
                instruction.setComputer_id(computer.getId());
                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                instruction.setCommand_list(gson.toJson(commandList));

                try {
                    if (instruction.getId()!=null){
                        dbManager.saveOrUpdate(instruction);
                    }else {
                        dbManager.save(instruction);
                    }
                    finish();
                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}