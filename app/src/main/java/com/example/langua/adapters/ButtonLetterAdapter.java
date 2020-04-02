package com.example.langua.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.langua.R;
import com.example.langua.activities.mainPlain;
import com.example.langua.activities.utilities.Test;
import com.example.langua.units.buttonLetterUnit;

import java.util.ArrayList;

public class ButtonLetterAdapter extends RecyclerView.Adapter<ButtonLetterAdapter.ButtonLetterViewHolder>{

    private ArrayList<buttonLetterUnit> buttonLetterUnits;
    private BlockListener blockListener;
    private Context context;

    public ButtonLetterAdapter(Context context, ArrayList<buttonLetterUnit> blocks) {
        this.buttonLetterUnits = blocks;
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
    public ButtonLetterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.button_leter, viewGroup, false);
        return new ButtonLetterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ButtonLetterViewHolder holder, int position) {
        buttonLetterUnit unit = buttonLetterUnits.get(position);
        holder.letter.setText(unit.getLetter());
        if (mainPlain.sizeRatio < mainPlain.triggerRatio) {
            holder.letter.setMinWidth((int) (mainPlain.sizeWidth * 0.7 / 7));
            holder.letter.setTextSize((int) (mainPlain.sizeWidth * 0.7 / 7 /(2.5f*Test.multiple)));
        }
        if (unit.isPressed())
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorWhiteSynonym));
        else
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorWhiteTranslate));
    }

    @Override
    public int getItemCount() {
        return buttonLetterUnits.size();
    }

    class ButtonLetterViewHolder extends RecyclerView.ViewHolder {

        private TextView letter;
        private CardView cardView;

        public ButtonLetterViewHolder(@NonNull View itemView) {
            super(itemView);
            letter = itemView.findViewById(R.id.buttonLetter);
            cardView = itemView.findViewById(R.id.cardLetter);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (blockListener != null)
                        blockListener.onClick(getAdapterPosition());
                }});
        }
    }
}
