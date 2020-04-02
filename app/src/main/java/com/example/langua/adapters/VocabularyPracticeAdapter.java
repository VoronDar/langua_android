package com.example.langua.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.langua.R;
import com.example.langua.activities.mainPlain;
import com.example.langua.units.VocabularyPracticeUnit;

import java.util.ArrayList;

public class VocabularyPracticeAdapter extends RecyclerView.Adapter<VocabularyPracticeAdapter.VocabularyPracticeViewHolder>{

    private ArrayList<VocabularyPracticeUnit> units;
    private BlockListener blockListener;
    private Context context;

    public VocabularyPracticeAdapter(Context context, ArrayList<VocabularyPracticeUnit> blocks) {
        this.units = blocks;
        this.context = context;
    }

    public interface BlockListener {
        public void onClick(int position);

    }


    public void setBlockListener(BlockListener block_listener) {
        this.blockListener = block_listener;
    }

    @NonNull
    @Override
    public VocabularyPracticeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.test_pool_block, viewGroup, false);
        return new VocabularyPracticeViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull VocabularyPracticeViewHolder holder, int position) {
        VocabularyPracticeUnit unit = units.get(position);
        holder.command.setText(unit.getCommand());
        if (mainPlain.sizeRatio<mainPlain.triggerRatio) {
            holder.command.setTextSize(mainPlain.sizeHeight/50f);
        }
    }

    @Override
    public int getItemCount() {
        return units.size();
    }

    class VocabularyPracticeViewHolder extends RecyclerView.ViewHolder {

        private TextView command;

        public VocabularyPracticeViewHolder(@NonNull View itemView) {
            super(itemView);

            command = itemView.findViewById(R.id.command);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (blockListener != null)
                        blockListener.onClick(getAdapterPosition());
                }});
        }
    }
}
