package com.example.androidapp.Data.ProductData;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.Data.ProductDetailData.ProductDetail;
import com.example.androidapp.R;

import java.util.ArrayList;
import java.util.List;

public class ProductOrderAdapter extends RecyclerView.Adapter<ProductOrderAdapter.ProductOrderViewHolder> {
    private List<ProductDetail> mListProduct;
    private OnItemClickListener listener;

    public ProductOrderAdapter(List<ProductDetail> mListProduct) {
        this.mListProduct = mListProduct;
    }

    public void setProduct(List<ProductDetail> mListProduct) {
        this.mListProduct = mListProduct;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_for_order, parent, false);

        return new ProductOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductOrderViewHolder holder, int position) {
        ProductDetail productDetail = mListProduct.get(position);
        if (productDetail == null) {
            return;
        }

        holder.tvProductName.setText(productDetail.getName());
        holder.tvProductQuantity.setText(String.format("%,d", productDetail.getQuantity()));
        if (productDetail.getAttribute1() != null) {
            holder.tvProductAttribute1.setText(productDetail.getAttribute1());
        }
        if (productDetail.getAttribute2() != null) {
            holder.tvProductAttribute2.setText(productDetail.getAttribute2());
        }
        holder.tvProductPrice.setText(String.format("%,d", productDetail.getPrice()));

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



    public class ProductOrderViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvProductName;
        private final TextView tvProductQuantity;
        private final TextView tvProductAttribute1;
        private final TextView tvProductAttribute2;
        private final TextView tvProductPrice;
        private final ImageView productImg;


        public ProductOrderViewHolder(@NonNull View itemView) {
            super(itemView);

            tvProductName = itemView.findViewById(R.id.product_name);
            tvProductQuantity = itemView.findViewById(R.id.product_quantity);
            tvProductAttribute1 = itemView.findViewById(R.id.attribute1);
            tvProductAttribute2 = itemView.findViewById(R.id.attribute2);
            tvProductPrice = itemView.findViewById(R.id.product_price);
            productImg = itemView.findViewById(R.id.product_img);
//            tvProductPrice = itemView.findViewById(R.id.dish_price);
//            imageView = itemView.findViewById(R.id.dish_pic_view);
//            item = itemView.findViewById(R.id.menu_item);


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
