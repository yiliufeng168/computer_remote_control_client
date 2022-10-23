package net.yiliufeng.windows_control.MyAdapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.yiliufeng.windows_control.R;
import net.yiliufeng.windows_control.dao.Command;

import java.util.HashMap;
import java.util.List;

public  class CmdAdapter extends RecyclerView.Adapter<CmdAdapter.ViewHolder> implements View.OnClickListener{
    private List<Command> commandList;
    private Typeface fontFace;
    private Context context;

    public CmdAdapter(List<Command> commandList,Typeface fontFace,Context context){
        this.commandList = commandList;
        this.fontFace = fontFace;
        this.context = context;
    }

    @NonNull
    @Override
    public CmdAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_add_command,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Command command = commandList.get(position);
//        HashMap<String,Integer> hashMap = new HashMap<>();
//        hashMap.put("predefined",0);
//        hashMap.put("keyboard",1);
//        hashMap.put("mouse",2);
//        hashMap.put("input_msg",3);
//        hashMap.put("shell",4);
//        hashMap.put("speak",5);
//        holder.sp_cmd_type.setSelection(hashMap.get(command.getType()));
        holder.et_command.setText(command.getCommand());
        holder.et_cmd_type.setText(command.getType());
        holder.et_cmd_args.setText(command.getArgs());
        holder.btn_add_new_command.setTypeface(fontFace);
        holder.et_command.setTag(position);
        holder.btn_add_new_command.setTag(position);
    }

    @Override
    public int getItemCount() {
        return commandList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private EditText et_command;
        private EditText et_cmd_type;
        private EditText et_cmd_args;
//        private Spinner sp_cmd_type;
        private Button btn_add_new_command;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            et_command = (EditText) itemView.findViewById(R.id.et_command);
            et_cmd_type = (EditText) itemView.findViewById(R.id.et_cmd_type);
            et_cmd_args = (EditText) itemView.findViewById(R.id.et_args);
            btn_add_new_command = itemView.findViewById(R.id.btn_add_new_command);
            btn_add_new_command.setOnClickListener(CmdAdapter.this);
        }
    }
    public enum ViewName {
        ITEM,
        BTN
    }
    public interface OnItemClickListener  {
        void onItemClick(View v, ViewName viewName, int position);
        void onItemLongClick(View v);
    }




    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        int position = (int)v.getTag();
        if (mOnItemClickListener != null){
            switch (v.getId()){
                case R.id.btn_add_new_command:
                    mOnItemClickListener.onItemClick(v,ViewName.BTN,position);
                    break;
            }
        }
    }


}
