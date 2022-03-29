package com.example.androidapp.Data.ProductType;





import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.androidapp.HelperClass.ProductAttributeAdapter;
import com.example.androidapp.R;

import java.util.List;

public class ProductTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ProductType> mListProductType;
    private static final int FOOTER_VIEW = 1;
    //    private OnItemClickDelListener listener;
    private OnItemClickAddListener addListener;

    public ProductTypeAdapter(List<ProductType> mListProductType) {
        this.mListProductType = mListProductType;
    }


    public void setProductType(List<ProductType> mListProductType) {
        this.mListProductType = mListProductType;
        notifyDataSetChanged();
    }

    //Get the client position
    public ProductType getAttributeItemAt(int position) {
        return mListProductType.get(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == FOOTER_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adding_type, parent, false);
            return new ProductTypeViewHolderFooter(view);
        }
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_type_product, parent, false);

        return new ProductTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//

        try {
            if (holder instanceof ProductTypeViewHolder) {
                ProductType productType = mListProductType.get(position);
                if (productType == null) {
                    return;
                }
                ProductTypeViewHolder vh = (ProductTypeViewHolder) holder;
                ((ProductTypeViewHolder) holder).typeName.setText(productType.getName());
                vh.bindView(position);
            } else if (holder instanceof ProductTypeViewHolderFooter) {
                ProductTypeViewHolderFooter vh = (ProductTypeViewHolderFooter) holder;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        holder.tvAttributeItemName.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                mListProductType.get(holder.getAdapterPosition()).setAttributeItemName(s.toString());
//            }
//        });

//        holder.tvClientNumber.setText(client.getClientNumber());
//        holder.tvClientAddress.setText(client.getClientAddress());

    }

    @Override
    public int getItemCount() {
        if (mListProductType == null) {
            return 0;
        }

        if (mListProductType.size() == 0) {
            //Return 1 here to show nothing
            return 1;
        }

        // Add extra view to show the footer view
        return mListProductType.size() + 1;
    }
    @Override
    public int getItemViewType(int position) {
        if (position == mListProductType.size()) {
            // This is where we'll add footer.
            return FOOTER_VIEW;
        }

        return super.getItemViewType(position);
    }



    public class ProductTypeViewHolder extends ViewHolder {

        //        private final TextView tvClientName;
//        private final TextView tvClientNumber;
        private TextView typeName;
//        private final LinearLayout item;

        public ProductTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            typeName = itemView.findViewById(R.id.type_name);
//
//
//            btnDel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //Get pos
//
//                    if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION){
//                        mListProductType.remove(getAdapterPosition());
//                        notifyItemRemoved(getAdapterPosition());
//                        notifyItemRangeChanged(getAdapterPosition(), mListProductType.size());
//                    }
//                }
//            });
        }


    }
    public class ProductTypeViewHolderFooter extends ViewHolder {

        public ProductTypeViewHolderFooter(@NonNull View itemView) {
            super(itemView);
          itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (addListener != null && position != RecyclerView.NO_POSITION) {
                        addListener.onItemClickAdd(position);
                    }
                }
            });
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Define elements of a row here
        public ViewHolder(View itemView) {
            super(itemView);
            // Find view by ID and initialize here
        }

        public void bindView(int position) {
            // bindView() method to implement actions
        }
    }

    //Interface to click on a dish item
    public interface OnItemClickAddListener {
        void onItemClickAdd(int position);
    }

    //Method item click listener
    public void setOnItemClickAddListener(OnItemClickAddListener addListener) {
        this.addListener = addListener;
    }
//    //Interface to click on a dish item
//    public interface OnItemClickDelListener {
//        void onItemClickDel(ProductType attributeItem);
//    }

//    //Method item click listener
//    public void setOnItemClickDelListener(com.example.androidapp.HelperClass.ProductTypeAdapter.OnItemClickDelListener delListener) {
//        this.delListener = delListener;
//    }



//    public interface OnItemClickDelListener{
//        void onItemClickDel(String attribute);
//    }
//    public void setOnItemClickDelListener(OnItemClickDelListener delListener){
//        this.delListener = delListener;
//    }
}