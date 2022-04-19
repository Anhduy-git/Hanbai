package com.example.androidapp.OrderFragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapp.Activities.NewOrderActivity;
import com.example.androidapp.Activities.OrderInfoTodayActivity;
import com.example.androidapp.Activities.OrderInfoUnpaidActivity;
import com.example.androidapp.Data.AppDatabase;
import com.example.androidapp.Data.ClientData.Client;
import com.example.androidapp.Data.DayRevenueData.DayRevenue;
import com.example.androidapp.Data.DayRevenueData.DayRevenueViewModel;
import com.example.androidapp.Data.HistoryOrder.HistoryOrder;
import com.example.androidapp.Data.HistoryOrder.HistoryOrderViewModel;
import com.example.androidapp.Data.MonthRevenueData.MonthRevenue;
import com.example.androidapp.Data.MonthRevenueData.MonthRevenueViewModel;
import com.example.androidapp.Data.OrderData.OrderTodayData.Order;
import com.example.androidapp.Data.OrderData.OrderTodayData.OrderAdapter;
import com.example.androidapp.Data.OrderData.OrderTodayData.OrderViewModel;
import com.example.androidapp.Data.OrderData.OrderUnpaidData.UnpaidOrder;
import com.example.androidapp.Data.OrderData.OrderUnpaidData.UnpaidOrderAdapter;
import com.example.androidapp.Data.OrderData.OrderUnpaidData.UnpaidOrderViewModel;
import com.example.androidapp.Data.ProductDetailData.ProductDetail;
import com.example.androidapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UnpaidOrderFragment extends Fragment {
    private Button btnAddNewOrder;

    public static final int ADD_ORDER_REQUEST = 1;
    public static final int CONFIRM_ORDER_REQUEST = 2;
    //View model
    private UnpaidOrderViewModel unpaidOrderViewModel;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unpaid_order,
                container, false);

        RecyclerView rcvData = (RecyclerView) view.findViewById(R.id.order_recycler);
        rcvData.setLayoutManager(new LinearLayoutManager(view.getContext()));

        final UnpaidOrderAdapter unpaidOrderAdapter = new UnpaidOrderAdapter();
        rcvData.setAdapter(unpaidOrderAdapter);
        //Set up view model
        unpaidOrderViewModel = new ViewModelProvider(this).get(UnpaidOrderViewModel.class);
        unpaidOrderViewModel.getAllUnpaidOrder().observe(getActivity(), new Observer<List<UnpaidOrder>>() {
            @Override
            public void onChanged(List<UnpaidOrder> unpaidOrders) {
                //Update Recycle View
                unpaidOrderAdapter.submitList(unpaidOrders);
//                //Display number of order today
//                numOrderToday.setText(String.format("%d", orders.size()));
            }
        });


        //Send data to Order Info when click order
        unpaidOrderAdapter.setOnItemClickListener(new UnpaidOrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(UnpaidOrder unpaidOrder) {
                Intent intent = new Intent(getActivity(), OrderInfoUnpaidActivity.class);
                intent.putExtra(OrderInfoUnpaidActivity.EXTRA_ORDER_ID, unpaidOrder.getId());
                intent.putExtra(OrderInfoUnpaidActivity.EXTRA_ORDER_CLIENT, unpaidOrder.getClient());
                intent.putExtra(OrderInfoUnpaidActivity.EXTRA_ORDER_TIME, unpaidOrder.getTime());
                intent.putExtra(OrderInfoUnpaidActivity.EXTRA_ORDER_DATE, unpaidOrder.getDate());
                intent.putExtra(OrderInfoUnpaidActivity.EXTRA_CHECK_PAID, unpaidOrder.getPaid());
                intent.putExtra(OrderInfoUnpaidActivity.EXTRA_ORDER_PRICE, unpaidOrder.getPrice());
                intent.putParcelableArrayListExtra(OrderInfoUnpaidActivity.EXTRA_ORDER_PRODUCT_LIST, (ArrayList<? extends Parcelable>) unpaidOrder.getOrderListProduct());
                startActivityForResult(intent, CONFIRM_ORDER_REQUEST);
            }
        });



//
//Button to launch New Today Order Activity

        return view;
    }

    //Get data return from Intent to update order
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Add new order
        if (requestCode == CONFIRM_ORDER_REQUEST && resultCode == RESULT_OK) {

            int id = data.getIntExtra(OrderInfoUnpaidActivity.EXTRA_ORDER_ID, -1);


            if (id == -1){
                Toast.makeText(getActivity(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                return;
            }

            UnpaidOrder unpaidOrder = new UnpaidOrder(null, "", "", 0, true,  null);
            unpaidOrder.setId(id);
            unpaidOrderViewModel.delete(unpaidOrder);

        }
    }

}
