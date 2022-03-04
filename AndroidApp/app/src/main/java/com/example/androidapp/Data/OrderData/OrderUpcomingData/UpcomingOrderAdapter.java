package com.example.androidapp.Data.OrderData.OrderUpcomingData;//package com.example.androidapp.data.order.orderupcoming;
//
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class UpcomingOrderAdapter extends ListAdapter<UpcomingOrder, UpcomingOrderAdapter.UpcomingOrderViewHolder> {

    private List<UpcomingOrder> mListUpcomingOrder = new ArrayList<>();
    private OnItemClickListener listener;
    private OnItemClickDelListener delListener;
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();


    public UpcomingOrderAdapter(){
        super(DIFF_CALLBACK);
        //Open 1 card only when delete
        viewBinderHelper.setOpenOnlyOne(true);
    }
    //setup for animation
    private static final DiffUtil.ItemCallback<UpcomingOrder> DIFF_CALLBACK = new DiffUtil.ItemCallback<UpcomingOrder>() {
        @Override
        public boolean areItemsTheSame(@NonNull UpcomingOrder oldItem, @NonNull UpcomingOrder newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull UpcomingOrder oldItem, @NonNull UpcomingOrder newItem) {
            return oldItem.getClient().getClientName().equals(newItem.getClient().getClientName()) &&
                    oldItem.getClient().getClientAddress().equals(newItem.getClient().getClientAddress()) &&
                    oldItem.getDate().equals(newItem.getDate()) &&
                    oldItem.getTime().equals(newItem.getTime()) &&
                    oldItem.getClient().getClientNumber().equals(newItem.getClient().getClientNumber()) &&
                    oldItem.getPrice() == newItem.getPrice() &&
                    oldItem.getPaid() == newItem.getPaid() &&
                    oldItem.getShip() == newItem.getShip();
//                   oldItem.getOrderListProduct().equals(newItem.getOrderListProduct());
        }
    };

    public void setUpcomingOrder(List<UpcomingOrder> mListUpcomingOrder) {
        this.mListUpcomingOrder = mListUpcomingOrder;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UpcomingOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);

        return new UpcomingOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingOrderViewHolder holder, int position){
        UpcomingOrder upcomingOrder = getItem(position);
        if (upcomingOrder == null) {
            return;
        }

        //Provide id object
        viewBinderHelper.bind(holder.swipeRevealLayout, Integer.toString(upcomingOrder.getId()));

        holder.tvOrderName.setText(upcomingOrder.getClient().getClientName());
//        holder.tvOrderDate.setText(upcomingOrder.getDate());
        holder.tvOrderTime.setText(upcomingOrder.getTime());
        holder.tvOrderPrice.setText(String.format("%,d", upcomingOrder.getPrice()) + " VND");
        //Read image from file
        if (upcomingOrder.getClient().getImageDir() != null) {
            try {
                File f=new File(upcomingOrder.getClient().getImageDir());
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                holder.imageView.setImageBitmap(b);
            }
            catch (FileNotFoundException e) {
                Resources res = holder.imageView.getResources();
                Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.ava_client_default);
                holder.imageView.setImageBitmap(bitmap);
            }
        }
        //Handle flag
        if (upcomingOrder.getPaid()){
            holder.flagPaid.setVisibility(View.VISIBLE);
        } else {
            holder.flagPaid.setVisibility(View.INVISIBLE);
        }
        holder.flagShip.setVisibility(View.GONE);
    }

    public UpcomingOrder getUpcomingOrderAt(int pos){
        return getItem(pos);
    }

    public class UpcomingOrderViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvOrderName;
//        private final TextView tvOrderDate;
        private final TextView tvOrderTime;
        private final TextView tvOrderPrice;
        private final ImageView imageView;
        private final ImageView flagPaid;
        private final ImageView flagShip;
        private final RelativeLayout item;
        private final SwipeRevealLayout swipeRevealLayout;
        private final RelativeLayout layoutDel;

        public UpcomingOrderViewHolder(@NonNull View itemView) {
            super(itemView);

            swipeRevealLayout = itemView.findViewById(R.id.swipe_reveal_layout);
            layoutDel = itemView.findViewById(R.id.order_item_del);
            tvOrderName = itemView.findViewById(R.id.order_name);
//            tvOrderDate = itemView.findViewById(R.id.order_day);
            tvOrderTime = itemView.findViewById(R.id.order_time);
            tvOrderPrice = itemView.findViewById(R.id.order_price);
            imageView = itemView.findViewById(R.id.order_avatar);
            flagPaid = itemView.findViewById(R.id.paid_icon);
            flagShip = itemView.findViewById(R.id.ship_icon);

            //This is the main layout in order_item_recycler
            item = itemView.findViewById(R.id.order_item);
            //Set onClick method for each item
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (listener != null && pos != RecyclerView.NO_POSITION){
                        listener.onItemClick(getItem(pos));
                    }
                }
            });
            //Set delete when click layout del
            layoutDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Get pos
                    int pos = getAdapterPosition();
                    //Get del order
                    UpcomingOrder upcomingOrder = getUpcomingOrderAt(pos);
                    if (delListener != null && pos != RecyclerView.NO_POSITION){
                        delListener.onItemClickDel(upcomingOrder);
                    }

                }
            });
        }
    }
    public interface OnItemClickListener{
        void onItemClick(UpcomingOrder upcomingOrder);

    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickDelListener{
        void onItemClickDel(UpcomingOrder upcomingOrder);
    }
    public void setOnItemClickDelListener(UpcomingOrderAdapter.OnItemClickDelListener delListener){
        this.delListener = delListener;
    }

}
