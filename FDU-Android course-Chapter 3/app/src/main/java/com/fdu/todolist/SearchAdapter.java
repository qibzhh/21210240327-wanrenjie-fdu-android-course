package com.fdu.todolist;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchAdapter extends RecyclerView.Adapter<TextViewHolder> {
    @NonNull
    private List<Note> mItems=new ArrayList<>();
    public interface  onItemClickListener{
        void onItemClick(View view ,int position);
        void  onItemLongClick(View view, int position);
    }
    private onItemClickListener onItemClickListener;
    public void setOnItemClickListener(onItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
    @NonNull
    @Override
    public TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TextViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_text,parent,false));
    }



    @Override
    public void onBindViewHolder(@NonNull final TextViewHolder holder, int position) {
       holder.bind(mItems.get(position));
     //   holder.mTextView.setText(position);
        if(onItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int layoutPos=holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView,layoutPos);

                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int layoutPos=holder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(holder.itemView,layoutPos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void notifyItems(@NonNull List<Note> itmems){
        mItems.clear();;
        for (int i=0;i<itmems.size();i++){
            mItems.add(itmems.get(i));

        }

        notifyDataSetChanged();
    }

}
