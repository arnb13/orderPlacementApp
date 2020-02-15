package com.example.order;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private static final String TAG = "RecyclerAdapter";

    private ArrayList<String> product = new ArrayList<>();
    private ArrayList<String> photo = new ArrayList<>();
    private Context context;

    private OnOrderListener onOrderListener;


    public RecyclerAdapter(ArrayList<String> product, ArrayList<String> photo, OnOrderListener onOrderListener, Context context) {
        this.product = product;
        this.photo = photo;
        this.context = context;
        this.onOrderListener = onOrderListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list, parent, false);
        ViewHolder holder = new ViewHolder(view, onOrderListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        Glide.with(context)
                .asBitmap()
                .load(photo.get(position))
                .into(holder.photo);

        holder.product_name.setText(product.get(position));

    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView photo;
        TextView product_name;
        RelativeLayout parentLayout;
        OnOrderListener onOrderListener;

        public ViewHolder(@NonNull View itemView, OnOrderListener onOrderListener) {
            super(itemView);
            photo = itemView.findViewById(R.id.image);
            product_name = itemView.findViewById(R.id.text_product_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            this.onOrderListener = onOrderListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onOrderListener.OnOrderClick(getAdapterPosition());

        }
    }

    public interface OnOrderListener {
        void OnOrderClick(int position);
    }
}
