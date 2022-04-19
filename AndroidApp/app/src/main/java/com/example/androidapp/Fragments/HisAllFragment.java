package com.example.androidapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.Activities.OrderInfoHistoryActivity;
import com.example.androidapp.Activities.OrderInfoTodayActivity;
import com.example.androidapp.Data.HistoryOrder.HistoryOrder;
import com.example.androidapp.Data.HistoryOrder.HistoryOrderAdapter;
import com.example.androidapp.Data.HistoryOrder.HistoryOrderViewModel;
import com.example.androidapp.R;

import java.util.ArrayList;
import java.util.List;

public class HisAllFragment extends Fragment {
    public static final int VIEW_HISTORY_ORDER_REQUEST = 1;
    private HistoryOrderViewModel historyOrderViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Button to launch Add New Order Activity
        View view = inflater.inflate(R.layout.fragment_history_all,
                container, false);

        RecyclerView rcvData = (RecyclerView) view.findViewById(R.id.his_all_recycler);;
        rcvData.setLayoutManager(new LinearLayoutManager(view.getContext()));
        final HistoryOrderAdapter historyOrderAdapter = new HistoryOrderAdapter();
        rcvData.setAdapter(historyOrderAdapter);

        historyOrderViewModel = new ViewModelProvider(this).get(HistoryOrderViewModel.class);
        historyOrderViewModel.getAllHistoryOrder().observe(getActivity(), new Observer<List<HistoryOrder>>() {
            @Override
            public void onChanged(List<HistoryOrder> historyOrders) {
                //Update Recycle View
                historyOrderAdapter.submitList(historyOrders);
            }
        });

        //Send data to Order Info when click order
        historyOrderAdapter.setOnItemClickListener(new HistoryOrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(HistoryOrder historyOrder) {
                Intent intent = new Intent(getActivity(), OrderInfoTodayActivity.class);
                intent.putExtra(OrderInfoHistoryActivity.EXTRA_ORDER_ID, historyOrder.getId());
                intent.putExtra(OrderInfoHistoryActivity.EXTRA_ORDER_CLIENT, historyOrder.getClient());
                intent.putExtra(OrderInfoHistoryActivity.EXTRA_ORDER_TIME, historyOrder.getTime());
                intent.putExtra(OrderInfoHistoryActivity.EXTRA_ORDER_DATE, historyOrder.getDate());
                intent.putExtra(OrderInfoHistoryActivity.EXTRA_ORDER_PRICE, historyOrder.getPrice());
                intent.putParcelableArrayListExtra(OrderInfoHistoryActivity.EXTRA_ORDER_PRODUCT_LIST, (ArrayList<? extends Parcelable>) historyOrder.getOrderListProduct());
                startActivity(intent);
            }
        });
        return view;
    }
}
