package com.example.slambook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;

    OnItemClickListener listener;

    public RecyclerViewAdapter(Context context, int resource, ArrayList<Entry> entryList) {
        this.context = context;
        this.resource = resource;
        this.entryList = entryList;
    }

    int resource;
    ArrayList<Entry> entryList;

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(resource, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Entry entry = entryList.get(position);
//        holder.userImg.setImageResource(entry.ge);
        holder.remark.setText(entry.getRemark());
        holder.fullname.setText(entry.getFullname());
    }

    @Override
    public int getItemCount() {
        return entryList.size();
    }

    public interface OnItemClickListener{
        void OnItemClickListener(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onClick) {
        listener = onClick;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView fullname, remark;
        ImageView userImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fullname = itemView.findViewById(R.id.user_fullname);
            remark = itemView.findViewById(R.id.user_remark);
            userImg = itemView.findViewById(R.id.user_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int pos =getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            listener.OnItemClickListener(pos);
                        }
                    }
                }
            });
        }
    }
}
