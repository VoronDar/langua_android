package com.example.langua.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.langua.R;
import com.example.langua.activities.NewCardFragment;
import com.example.langua.activities.mainPlain;
import com.example.langua.units.newCardElementUnit;

import java.util.ArrayList;

public class NewCardElementAdapter extends RecyclerView.Adapter<NewCardElementAdapter.TextBlockViewHolder>{

    private ArrayList<newCardElementUnit> units;
    private BlockListener blockListener;
    private Context context;
    private boolean isChange;
    private int lastSentenceIndex;

    public NewCardElementAdapter(boolean isChange, Context context, ArrayList<newCardElementUnit> blocks) {
        this.units = blocks;
        this.context = context;
        this.isChange = isChange;
    }

    public void setLastSentenceIndex(int lastSentenceIndex) {
        this.lastSentenceIndex = lastSentenceIndex;
    }

    public interface BlockListener {
        public void onClick(int position);

    }


    public void setBlockListener(BlockListener block_listener) {
        this.blockListener = block_listener;
    }

    @NonNull
    @Override
    public TextBlockViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.info_card_block, viewGroup, false);
        return new TextBlockViewHolder(view);

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull final TextBlockViewHolder holder, final int position) {
        final newCardElementUnit block = units.get(position);

        holder.name.setText(block.getName());


        /*
        holder.itemView.findViewById(R.id.add_block).setVisibility(View.GONE);
        holder.itemView.findViewById(R.id.delSentence).setVisibility(View.GONE);
        holder.itemView.findViewById(R.id.addNew).setVisibility(View.GONE);


        if (block.getId() == NewCardFragment.ElIds.train){
            holder.itemView.findViewById(R.id.add_block).setVisibility(View.VISIBLE);
            if (!block.isRequired()){
                holder.itemView.findViewById(R.id.delSentence).setVisibility(View.VISIBLE);
                holder.itemView.findViewById(R.id.delSentence).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("main - pos", Integer.toString(position));
                        Log.i("main - last", Integer.toString(lastSentenceIndex));
                        lastSentenceIndex--;
                        units.remove(position);
                        //notifyItemRemoved(holder.getAdapterPosition());
                        notifyDataSetChanged();
                    }
                });
            }
            if (position == lastSentenceIndex){
                holder.itemView.findViewById(R.id.addNew).setVisibility(View.VISIBLE);
                holder.itemView.findViewById(R.id.addNew).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        units.add(++lastSentenceIndex, new newCardElementUnit(
                                context.getString(R.string.exampleTrainLearn) + " (" + context.getString(R.string.variant) + ")",
                                null, false,
                                NewCardFragment.ElIds.train));

                        //notifyItemInserted(lastSentenceIndex);
                        notifyDataSetChanged();
                    }
                });
            }

        }
         */


        if (block.getValue() == null)
            holder.value.setHint(block.getPrevValue());
        else
            holder.value.setHint("");

        if (block.getId() == NewCardFragment.ElIds.transcript){
            holder.value.setInputType(InputType.TYPE_TEXT_VARIATION_PHONETIC);
        }
        if (block.isRequired()){
            holder.name.setTextColor(context.getResources().getColor(R.color.colorBlue));
        }

        if (!block.isEditable()) {
            holder.value.setFocusable(false);
            holder.value.setClickable(false);
            holder.value.setTextColor(context.getResources().getColor(R.color.colorWhiteExample));
        }

        if (isChange){
            block.setValue(block.getPrevValue());
            holder.value.setText(block.getValue());
        }

        if (block.getValue() == null && block.isRequired()){
            holder.name.setTextColor(context.getResources().getColor(R.color.colorOrange));
            holder.value.setHint(block.getPrevValue());
        }
        final TextBlockViewHolder thisHolder = holder;
        holder.value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (thisHolder.value.getText().toString().equals(""))
                    block.setValue(null);
                else
                    block.setValue(thisHolder.value.getText().toString());
                if (block.getValue() == null && block.isRequired()){
                    thisHolder.name.setTextColor(context.getResources().getColor(R.color.colorOrange));
                }
                else if (block.isRequired()){
                    thisHolder.name.setTextColor(context.getResources().getColor(R.color.colorBlue));
                }
                if (block.getValue() == null)
                    thisHolder.value.setHint(block.getPrevValue());
                else
                    thisHolder.value.setHint("");
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        if (block.getId() == NewCardFragment.ElIds.train ||
                block.getId() == NewCardFragment.ElIds.trainNative ||
                block.getId() == NewCardFragment.ElIds.mem ||
                block.getId() == NewCardFragment.ElIds.help ||
                block.getId() == NewCardFragment.ElIds.group ||
                block.getId() == NewCardFragment.ElIds.part){
            holder.help.setVisibility(View.VISIBLE);
            holder.help.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final AlertDialog.Builder adb = new AlertDialog.Builder(context);
                    View my_custom_view = mainPlain.activity.getLayoutInflater().
                            inflate(R.layout.help_element_layout, null);
                    adb.setView(my_custom_view);

                    ((TextView) my_custom_view.findViewById(R.id.name)).setText(block.getName());

                    TextView info = my_custom_view.findViewById(R.id.info);
                    ImageView image = my_custom_view.findViewById(R.id.imageView);
                    if (block.getId() == NewCardFragment.ElIds.train) {
                        info.setText(context.getResources().getString(R.string.trainInfo));
                        image.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_write));
                    } else if (block.getId() == NewCardFragment.ElIds.trainNative){
                        info.setText(context.getResources().getString(R.string.trainNativeInfo));
                    image.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_write));
                    }
                    else if (block.getId() == NewCardFragment.ElIds.help){
                        info.setText(context.getResources().getString(R.string.helpInfo));
                        image.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_help));
                    }
                    else if (block.getId() == NewCardFragment.ElIds.mem){
                        info.setText(context.getResources().getString(R.string.memInfo));
                    image.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_mem));}
                    else if (block.getId() == NewCardFragment.ElIds.group){
                        info.setText(context.getResources().getString(R.string.groupInfo));
                    image.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_group));
                    }
                    else if (block.getId() == NewCardFragment.ElIds.part){
                        info.setText(context.getResources().getString(R.string.partInfo));
                        image.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_part));
                    }

                    final AlertDialog ad = adb.create();
                    Button cancel = my_custom_view.findViewById(R.id.next);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ad.cancel();
                        }
                    });
                    ad.show();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return units.size();
    }

    class TextBlockViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private EditText value;
        private ImageView help;

        public TextBlockViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            value = itemView.findViewById(R.id.value);
            help = itemView.findViewById(R.id.helpView);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (blockListener != null)
                        blockListener.onClick(getAdapterPosition());
                }});
        }
    }
}
