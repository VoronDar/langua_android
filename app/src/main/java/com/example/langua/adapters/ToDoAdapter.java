package com.example.langua.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.langua.R;
import com.example.langua.units.toDoUnit;

import java.util.ArrayList;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>{

    private ArrayList<toDoUnit> units;
    private BlockListener blockListener;
    private Context context;

    public ToDoAdapter(Context context, ArrayList<toDoUnit> blocks) {
        this.units = blocks;
        this.context = context;
    }

    public interface BlockListener {
        public void onClick(int position);
        public void onLongClick(int position);

    }


    public void setBlockListener(BlockListener block_listener) {
        this.blockListener = block_listener;
    }

    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.to_do, viewGroup, false);
        return new ToDoViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ToDoViewHolder holder, int position) {
        toDoUnit unit = units.get(position);
        holder.toDo.setText(unit.getToDo());
        holder.toDoGroup.setText(unit.getChapterName());
        holder.toDoNumber.setText(Integer.toString(unit.getCardCount()));


    }

    @Override
    public int getItemCount() {
        return units.size();
    }

    class ToDoViewHolder extends RecyclerView.ViewHolder {

        private TextView toDoGroup;
        private TextView toDo;
        private TextView toDoNumber;

        public ToDoViewHolder(@NonNull View itemView) {
            super(itemView);

            toDo = itemView.findViewById(R.id.toDo);
            toDoNumber = itemView.findViewById(R.id.toDoNumber);
            toDoGroup = itemView.findViewById(R.id.toDoGroup);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (blockListener != null)
                        blockListener.onClick(getAdapterPosition());
                }});
            itemView.setOnLongClickListener( new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    if (blockListener != null)
                        blockListener.onLongClick(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
