package com.example.langua.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.langua.R;
import com.example.langua.activities.mainPlain;
import com.example.langua.units.VocabularyCardLibUnit;

import java.util.ArrayList;

public class VocabularyCardLibAdapter extends RecyclerView.Adapter<VocabularyCardLibAdapter.VocabularyCardLibViewHolder>{

    private ArrayList<VocabularyCardLibUnit> units;
    private BlockListener blockListener;
    private Context context;

    public VocabularyCardLibAdapter(Context context, ArrayList<VocabularyCardLibUnit> blocks) {
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
    public VocabularyCardLibViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_lib_block, viewGroup, false);
        return new VocabularyCardLibViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull VocabularyCardLibViewHolder holder, int position) {
        VocabularyCardLibUnit unit = units.get(position);

        holder.word.setText(unit.getWord());

        if (mainPlain.sizeRatio < mainPlain.triggerRatio){
            holder.word.setTextSize(mainPlain.sizeHeight/(40f*mainPlain.multiple));
            holder.translate.setTextSize(mainPlain.sizeHeight/(50f*mainPlain.multiple));
        }
        holder.translate.setText(unit.getTranslate());
        if (unit.getLevel()<=0)
            holder.indicator.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
        else if (unit.getLevel() <= 1)
            holder.indicator.setBackgroundColor(context.getResources().getColor(R.color.colorLightGray));
        else if (unit.getLevel()<=4)
            holder.indicator.setBackgroundColor(context.getResources().getColor(R.color.colorWhiteSynonym));
        else if (unit.getLevel()<=6)
            holder.indicator.setBackgroundColor(context.getResources().getColor(R.color.colorWhiteMeaning));
        else
            holder.indicator.setBackgroundColor(context.getResources().getColor(R.color.colorBlack));
        //((TextView)holder.indicator).setTextColor();
        //if (position == 0)
        //    holder.divider.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return units.size();
    }

    class VocabularyCardLibViewHolder extends RecyclerView.ViewHolder {

        private TextView word;
        private TextView translate;
        private View indicator;

        public VocabularyCardLibViewHolder(@NonNull View itemView) {
            super(itemView);

            word = itemView.findViewById(R.id.word);
            translate = itemView.findViewById(R.id.translate);
            indicator = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (blockListener != null)
                        blockListener.onClick(getAdapterPosition());
                }});
        }
    }
}
