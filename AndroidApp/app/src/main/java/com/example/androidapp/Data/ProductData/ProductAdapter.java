package com.example.androidapp.Data.ProductData;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.androidapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends ListAdapter<Product, ProductAdapter.ProductViewHolder> implements Filterable {
    private List<Product> mListProduct;
    private List<Product> mListProductFull;
    private OnItemClickListener listener;
    private OnItemClickDelListener delListener;
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public ProductAdapter(List<Product> mListProduct) {
        super(DIFF_CALLBACK);
        this.mListProduct = mListProduct;
        //Open 1 card only when delete
        viewBinderHelper.setOpenOnlyOne(true);
    }
    //setup for animation
    private static final DiffUtil.ItemCallback<Product> DIFF_CALLBACK = new DiffUtil.ItemCallback<Product>() {
        @Override
        public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.getProductID() == newItem.getProductID();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getImageDir().equals(newItem.getImageDir());
        }
    };

    public void setProduct(List<Product> mListProduct) {
        this.mListProduct = mListProduct;
        this.mListProductFull = new ArrayList<>(mListProduct);

        notifyDataSetChanged();
    }

    //Get the Product position
    public Product getProductAt(int postition) {
        return getItem(postition);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = getItem(position);
        if (product == null) {
            return;
        }
        //Provide id object
//        viewBinderHelper.bind(holder.swipeRevealLayout, Integer.toString(Product.getProductID()));

        holder.tvProductName.setText(product.getName());
        //read image from file

//        try {
//            File f=new File(Product.getImageDir());
//            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
//            holder.imgView.setImageBitmap(b);
//        }
//        catch (FileNotFoundException e) {
//            Resources res = holder.imgView.getResources();
//            Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.rec_ava_Product_default);
//            holder.imgView.setImageBitmap(bitmap);
//        }
    }

    @Override
    public Filter getFilter() {
        return ProductFilter;
    }

    //Create a filter object for searching
    private Filter ProductFilter = new Filter() {
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

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvProductName;
        private final ImageView imgView;
//        private final SwipeRevealLayout swipeRevealLayout;
////        private final LinearLayout layoutDel;
//        private final RelativeLayout item;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            tvProductName = itemView.findViewById(R.id.product_name);
            imgView = itemView.findViewById(R.id.product_img);
//            swipeRevealLayout = itemView.findViewById(R.id.swipe_reveal_layout);
//            layoutDel = itemView.findViewById(R.id.menu_item_del);
            //This is the main layout in order_item_recycler
//            item = itemView.findViewById(R.id.menu_item);
            //Set onClick method for each item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
            //Set delete when click layout del
//            layoutDel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //Get pos
//                    int pos = getAdapterPosition();
//                    //Get del Product
//                    Product Product = getProductAt(pos);
//                    if (delListener != null && pos != RecyclerView.NO_POSITION){
//                        delListener.onItemClickDel(Product);
//                    }
//                }
//            });
        }
    }

    //Interface to click on a Product item
    public interface OnItemClickListener {
        void onItemClick(Product Product);
    }

    //Method item click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickDelListener{
        void onItemClickDel(Product Product);
    }
    public void setOnItemClickDelListener(ProductAdapter.OnItemClickDelListener delListener){
        this.delListener = delListener;
    }


}