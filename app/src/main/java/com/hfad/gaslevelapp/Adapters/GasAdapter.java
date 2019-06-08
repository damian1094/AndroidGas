package com.hfad.gaslevelapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hfad.gaslevelapp.Database.RemoteDb.GasObject;
import com.hfad.gaslevelapp.R;

import java.util.List;

public class GasAdapter extends RecyclerView.Adapter<GasAdapter.ViewHolder> {
    private Context context;
    private List<GasObject> gasObjectList;

    public GasAdapter(Context context, List<GasObject> gasObjectList) {
        this.context = context;
        this.gasObjectList = gasObjectList;
    }

    @NonNull
    @Override
    public GasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.gas_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.object = gasObjectList.get(position);

        if ((viewHolder.object.getWeight() >= 0) && (viewHolder.object.getWeight() <= 2)){
            viewHolder.imageViewWarn.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(R.drawable.quarter)
                    .into(viewHolder.imageView);
            viewHolder.textView.setText(R.string.critical);
        }

        if ((viewHolder.object.getWeight() >= 3) && (viewHolder.object.getWeight() <= 4)){
            viewHolder.imageViewWarn.setVisibility(View.GONE);
            Glide.with(context)
                    .load(R.drawable.we)
                    .into(viewHolder.imageView);
            viewHolder.textView.setText(R.string.half);
        }

        if ((viewHolder.object.getWeight() >= 5) && (viewHolder.object.getWeight() <= 6)){
            viewHolder.imageViewWarn.setVisibility(View.GONE);
            Glide.with(context)
                    .load(R.drawable.threequarter)
                    .into(viewHolder.imageView);
            viewHolder.textView.setText(R.string.three_quarter);
        }

        if ((viewHolder.object.getWeight() >= 7) && (viewHolder.object.getWeight() <= 10)){
            viewHolder.imageViewWarn.setVisibility(View.GONE);
            Glide.with(context)
                    .load(R.drawable.wawe)
                    .into(viewHolder.imageView);
            viewHolder.textView.setText(R.string.full);
        }
    }

    @Override
    public int getItemCount() {
      //  return gasObjectList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView, imageViewWarn;
        private TextView textView;
        private GasObject object;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewWarn = itemView.findViewById(R.id.danger_warning);
            imageView = itemView.findViewById(R.id.gasLevel);
            textView = itemView.findViewById(R.id.desc_gas_level);
        }
    }
}
