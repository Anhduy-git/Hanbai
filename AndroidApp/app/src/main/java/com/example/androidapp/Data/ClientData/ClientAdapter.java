package com.example.androidapp.Data.ClientData;

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

public class ClientAdapter extends ListAdapter<Client, ClientAdapter.ClientViewHolder> implements Filterable{
    private List<Client> mListClient;
    private List<Client> mListClientFull;
    private OnItemClickListener listener;
    private OnItemClickDelListener delListener;
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public ClientAdapter(List<Client> mListClient) {
        super(DIFF_CALLBACK);
        //Open 1 card only when delete
        viewBinderHelper.setOpenOnlyOne(true);
        this.mListClient = mListClient;
    }

    //setup for animation
    private static final DiffUtil.ItemCallback<Client> DIFF_CALLBACK = new DiffUtil.ItemCallback<Client>() {
        @Override
        public boolean areItemsTheSame(@NonNull Client oldItem, @NonNull Client newItem) {
            return oldItem.getClientId() == newItem.getClientId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Client oldItem, @NonNull Client newItem) {
            return oldItem.getClientName().equals(newItem.getClientName()) &&
                    oldItem.getClientAddress().equals(newItem.getClientAddress()) &&
                    oldItem.getClientNumber().equals(newItem.getClientNumber()) &&
                    oldItem.getClientEmail().equals(newItem.getClientEmail()) &&
                    oldItem.getClientBank().equals(newItem.getClientBank()) &&
                    oldItem.getImageDir().equals(newItem.getImageDir());
        }
    };

    public void setClient(List<Client> mListClient) {
        this.mListClient = mListClient;
        this.mListClientFull = new ArrayList<>(mListClient);

        notifyDataSetChanged();
    }

    //Get client position
    public Client getClientAt(int postition) {
        return getItem(postition);
    }
    @NonNull
    @Override
    public ClientAdapter.ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_client, parent, false);

        return new ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        Client client = getItem(position);
        if (client == null) {
            return;
        }
        //Provide id object
        viewBinderHelper.bind(holder.swipeRevealLayout, Integer.toString(client.getClientId()));

        holder.tvClientName.setText(client.getClientName());
        holder.tvClientNumber.setText(client.getClientNumber());
        holder.tvClientAddress.setText(client.getClientAddress());
        //read image from file
        try {
            File f=new File(client.getImageDir());
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            holder.imageView.setImageBitmap(b);
        }
        catch (FileNotFoundException e) {
            Resources res = holder.imageView.getResources();
            Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.ava_client_default);
            holder.imageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public Filter getFilter() {
        return clientFilter;
    }

    //Create a filter object for searching
    private Filter clientFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Client> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mListClientFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Client item : mListClientFull) {
                    if (item.getClientName().toLowerCase().contains(filterPattern)) {
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
            mListClient.clear();
            mListClient.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class ClientViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvClientName;
        private final TextView tvClientNumber;
        private final TextView tvClientAddress;
        private final ImageView imageView;
        private final SwipeRevealLayout swipeRevealLayout;
        private final RelativeLayout layoutDel;
        private final LinearLayout item;

        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);

            swipeRevealLayout = itemView.findViewById(R.id.swipe_reveal_layout);
            layoutDel = itemView.findViewById(R.id.client_item_del);
            tvClientName = itemView.findViewById(R.id.client_name);
            tvClientNumber = itemView.findViewById(R.id.client_number);
            tvClientAddress = itemView.findViewById(R.id.client_address);
            imageView = itemView.findViewById(R.id.client_avatar);
            item = itemView.findViewById(R.id.client_item);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAbsoluteAdapterPosition();
                    if (listener != null && pos != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(pos));
                    }
                }
            });

            layoutDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Get pos
                    int pos = getAbsoluteAdapterPosition();
                    //Get del client
                    Client client = getClientAt(pos);
                    if (delListener != null && pos != RecyclerView.NO_POSITION){
                        delListener.onItemClickDel(client);
                    }
                }
            });
        }
    }

    //Interface to click on a Client item
    public interface OnItemClickListener {
        void onItemClick(Client client);
    }

    //Method item click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickDelListener{
        void onItemClickDel(Client client);
    }
    public void setOnItemClickDelListener(ClientAdapter.OnItemClickDelListener delListener){
        this.delListener = delListener;
    }

}
