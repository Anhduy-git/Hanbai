package com.example.androidapp.Data.ProductData;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.androidapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

//Adapter for RecyclerView
public class ProductSelectOnlyAdapter extends RecyclerView.Adapter<ProductSelectOnlyAdapter.ProductSelectOnlyViewHolder> implements Filterable {
    private List<Product> mListProduct;
    private List<Product> mListProductFull;
    private OnItemClickListener listener;

    public ProductSelectOnlyAdapter(List<Product> mListProduct) {
        this.mListProduct = mListProduct;
    }

    public void setProduct(List<Product> mListProduct) {
        this.mListProduct = mListProduct;
        this.mListProductFull = new ArrayList<>(mListProduct);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductSelectOnlyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);

        return new ProductSelectOnlyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductSelectOnlyViewHolder holder, int position) {
        Product product = mListProduct.get(position);
        if (product == null) {
            return;
        }

        holder.tvProductName.setText(product.getName());

        //read image from file

//        try {
//            File f=new File(product.getImageDir());
//            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
//            holder.imageView.setImageBitmap(b);
//        }
//        catch (FileNotFoundException e) {
//            Resources res = holder.imageView.getResources();
//            Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.rec_ava_dish_default);
//            holder.imageView.setImageBitmap(bitmap);
//        }

    }

    @Override
    public int getItemCount() {
        if (mListProduct != null) {
            return mListProduct.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return productFilter;
    }

    //Create a filter object for searching
    private Filter productFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Product> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mListProductFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Product item : mListProductFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mListProduct.clear();
            mListProduct.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class ProductSelectOnlyViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvProductName;


        public ProductSelectOnlyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvProductName = itemView.findViewById(R.id.product_name);
//            tvProductPrice = itemView.findViewById(R.id.dish_price);
//            imageView = itemView.findViewById(R.id.dish_pic_view);
//            item = itemView.findViewById(R.id.menu_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setFocusableInTouchMode(true);
                    v.requestFocus();
                    v.setFocusableInTouchMode(false);
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(mListProduct.get(position));
                    }
                }
            });
            itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        // run scale animation and make it bigger
                        Animation anim = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.scale_in);
                        itemView.startAnimation(anim);
                        anim.setFillAfter(true);
                    } else {
                        // run scale animation and make it smaller
                        Animation anim = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.scale_out);
                        itemView.startAnimation(anim);
                        anim.setFillAfter(true);
                    }
                }
            });

        }
    }

    //Interface to click on a dish item
    public interface OnItemClickListener {
        void onItemClick(Product dish);
    }

    //Method item click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
