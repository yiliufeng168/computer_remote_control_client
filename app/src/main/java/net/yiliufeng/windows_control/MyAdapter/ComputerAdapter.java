package net.yiliufeng.windows_control.MyAdapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.yiliufeng.windows_control.R;
import net.yiliufeng.windows_control.myBeams.Computer;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.List;

public class ComputerAdapter extends BaseAdapter {
    private Context context;
    private List<Computer> computerList;
    private DbManager dbManager;
    private Typeface fontFace;

    public ComputerAdapter(Context context, List<Computer> computerList, DbManager dbManager, Typeface fontFace) {
        this.context = context;
        this.computerList = computerList;
        this.dbManager = dbManager;
        this.fontFace = fontFace;
    }

    public void setComputerList(List<Computer> computerList) {
        this.computerList = computerList;
    }

    @Override
    public void notifyDataSetChanged() {

        super.notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetInvalidated() {
        super.notifyDataSetInvalidated();
    }

    @Override
    public int getCount() {
        return computerList.size();
    }

    @Override
    public Computer getItem(int position) {
        return computerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder{
        TextView tvComputerIcon;
        TextView tvComputerName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.listviewlayout,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.tvComputerIcon= convertView.findViewById(R.id.tv_computer_icon);
            viewHolder.tvComputerIcon.setTypeface(fontFace);
            viewHolder.tvComputerName = convertView.findViewById(R.id.tv_computer_name);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        Computer computer = null;
        try {
            computer=dbManager.selector(Computer.class).where("id","=",getItem(position).getId()).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (computer != null){
            viewHolder.tvComputerName.setText(computer.getName());
        }
        return convertView;
    }
}
