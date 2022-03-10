package com.example.androidapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.androidapp.Data.ProductType.ProductType;
import com.example.androidapp.Data.ProductType.ProductTypeViewModel;
import com.example.androidapp.Fragments.ViewPagerAdapter;
import com.example.androidapp.PickTypeActivity;
import com.example.androidapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private ProductTypeViewModel productTypeViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //view model
        productTypeViewModel = new ViewModelProvider(this).get(ProductTypeViewModel.class);
        //Check if has data in attribute ?
        List<ProductType> typeLst = productTypeViewModel.getAllProductType();

        if (typeLst == null || typeLst.size() == 0) {
            Intent intent = new Intent(MainActivity.this, PickTypeActivity.class);
            startActivity(intent);
        }


        viewPager = findViewById(R.id.view_pager);
        bottomNavigationView = findViewById(R.id.bottom_nav);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.menu_order).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.menu_client).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.menu_product).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.menu_history).setChecked(true);
                        break;
                    case 4:
                        bottomNavigationView.getMenu().findItem(R.id.menu_chart).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_order:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.menu_client:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.menu_product:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.menu_history:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.menu_chart:
                        viewPager.setCurrentItem(4);
                        break;
                }
                return true;
            }
        });


    }
}