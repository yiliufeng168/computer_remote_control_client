package net.yiliufeng.windows_control.MyAdapter;

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
import net.yiliufeng.windows_control.myBeams.Instruction;

import java.util.List;

public class MoreInstructionAdapter extends RecyclerView.Adapter<MoreInstructionAdapter.ViewHolder> implements View.OnClickListener,View.OnLongClickListener{
    private List<Instruction> instructionList;

    public MoreInstructionAdapter(List<Instruction> instructionList) {
        this.instructionList = instructionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_instruction,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Instruction instruction = instructionList.get(position);
        holder.btn_more_instruction.setText(instruction.getName());
        holder.btn_more_instruction.setTag(position);
    }

    @Override
    public int getItemCount() {
        return instructionList.size();
    }



    public enum ViewName {
        MORE_BTN
    }

    public interface OnItemClickListener  {
        void onItemClick(View v,ViewName viewName, int position);
//        void onItemLongClick(View v,ViewName viewName, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View v,ViewName viewName, int position);
    }

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener listener){
        this.mOnItemLongClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        int position = (int)v.getTag();
        if (mOnItemClickListener!=null){
            switch (v.getId()){
                case R.id.btn_more_instruction:
                    mOnItemClickListener.onItemClick(v,ViewName.MORE_BTN,position);
//                    mOnItemClickListener.onItemLongClick(v,ViewName.MORE_BTN,position);
                    break;
            }
        }
    }
    @Override
    public boolean onLongClick(View v) {
        int position = (int)v.getTag();
        if (mOnItemLongClickListener!=null){
            switch (v.getId()){
                case R.id.btn_more_instruction:
                    mOnItemLongClickListener.onItemLongClick(v,ViewName.MORE_BTN,position);
                    break;
            }
        }
        return true;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private Button btn_more_instruction;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            btn_more_instruction = itemView.findViewById(R.id.btn_more_instruction);

            btn_more_instruction.setOnClickListener(MoreInstructionAdapter.this);
            btn_more_instruction.setOnLongClickListener(MoreInstructionAdapter.this);
//            btn_more_instruction.setOnLongClickListener(MoreInstructionAdapter.this);

        }
    }


}
