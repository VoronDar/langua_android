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
import com.example.langua.activities.SettingsFragment;
import com.example.langua.activities.mainPlain;
import com.example.langua.ruler.Ruler;
import com.example.langua.units.PracticeTestsUnit;
import com.example.langua.units.SettingUnit;

import java.util.ArrayList;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder>{

    private ArrayList<SettingUnit> units;
    private BlockListener blockListener;
    private Context context;
    private View view;



    public SettingsAdapter(Context context, ArrayList<SettingUnit> blocks) {
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
        View view;
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.settings_block, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SettingUnit unit = units.get(position);
        holder.icon.setImageResource(unit.getImageID());
        holder.text.setText(unit.getCommand());
        if (unit.isChecked()){
            holder.text.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.indicator.setVisibility(View.VISIBLE);
        }
        else{
            holder.text.setTextColor(context.getResources().getColor(R.color.colorWhiteSynonym));
            holder.indicator.setVisibility(View.GONE);
        }
        if (mainPlain.sizeRatio < mainPlain.triggerRatio)
            holder.text.setTextSize(mainPlain.sizeHeight/(40f*mainPlain.multiple));
    }

    @Override
    public int getItemCount() {
        return units.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView icon;
        public TextView text;
        public ImageView indicator;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.image);
            text = itemView.findViewById(R.id.command);
            indicator = itemView.findViewById(R.id.indicator);


            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (blockListener != null)
                        blockListener.onClick(getAdapterPosition());
                }});
        }
    }
}
