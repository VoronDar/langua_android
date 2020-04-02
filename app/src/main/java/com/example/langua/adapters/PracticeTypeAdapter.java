package com.example.langua.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.langua.activities.mainPlain;
import com.example.langua.units.PracticeTestsUnit;
import com.example.langua.R;
import com.example.langua.ruler.Ruler;

import java.util.ArrayList;

public class PracticeTypeAdapter extends RecyclerView.Adapter<PracticeTypeAdapter.ViewHolder>{

    private ArrayList<PracticeTestsUnit> units;
    private BlockListener blockListener;
    private Context context;
    private View view;

    public PracticeTypeAdapter(Context context, ArrayList<PracticeTestsUnit> blocks) {
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.select_type__pool_block, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PracticeTestsUnit unit = units.get(position);
        holder.cards.setText(unit.getCardCount() + " " + Ruler.getCardEnd(unit.getCardCount()));
        holder.name.setText(unit.getChapter());
        if (mainPlain.sizeRatio<mainPlain.triggerRatio){
            holder.name.setTextSize(mainPlain.sizeHeight/(25f*mainPlain.multiple));
            holder.cards.setTextSize(mainPlain.sizeHeight/(40f*mainPlain.multiple));
        }
        holder.image.setImageDrawable(view.getResources().getDrawable(unit.getImageResourse()));



    }

    @Override
    public int getItemCount() {
        return units.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView cards;
        private TextView name;
        private ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView;
            cards = itemView.findViewById(R.id.cardsCount);
            name = itemView.findViewById(R.id.command);
            image = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (blockListener != null)
                        blockListener.onClick(getAdapterPosition());
                }});
        }
    }
}
