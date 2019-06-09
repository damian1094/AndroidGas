package com.hfad.gaslevelapp.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.hfad.gaslevelapp.Database.RemoteDb.Product;
import com.hfad.gaslevelapp.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Context context;
    private CartListener listener;
    private List<Product>productList = new ArrayList<>();
    private static final String TAG = "TAG";

    public ProductAdapter(Context context, CartListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ProductAdapter.ViewHolder viewHolder, int i) {
        viewHolder.product = productList.get(i);
        viewHolder.textViewName.setText(viewHolder.product.getName());
        viewHolder.textViewWeight.setText(viewHolder.product.getWeight());
        viewHolder.textViewPrice.setText(viewHolder.product.getPrice());

        Glide.with(context)
                .load("http://192.168.43.215/sanga/images/" + viewHolder.product.getPath())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource) {
                        viewHolder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable>
                            target, DataSource dataSource, boolean isFirstResource) {
                        viewHolder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(viewHolder.productImage);


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void setProductList (List<Product>productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewName, textViewWeight, textViewPrice;
        private ImageView productImage;
        private Button buttonCart;
        private ProgressBar progressBar;
        private Product product;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_name);
            textViewPrice = itemView.findViewById(R.id.text_price);
            textViewWeight = itemView.findViewById(R.id.text_weight);
            productImage = itemView.findViewById(R.id.product_img);
            buttonCart = itemView.findViewById(R.id.add_to_cart);
            progressBar = itemView.findViewById(R.id.load_img_product);
            buttonCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.passDataToCart(product.getName(),product.getWeight(),
                            product.getPrice());
                }
            });
        }
    }

    public interface CartListener{
        void passDataToCart(String productName, String weight, String price);
    }

}
