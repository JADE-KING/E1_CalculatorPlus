package com.example.yelia.viewpager;

import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static MainActivity mainActivity;
    public static GetExchangeRateOnline rate;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // 竖屏方向锁定
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mainActivity = this;

        // 网络请求获取汇率
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                rate = new GetExchangeRateOnline();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new WelcomeFragment());
        fragments.add(new SimpleCalculationFragment());
        fragments.add(new HexConversionFragment());
        fragments.add(new UnitConversionFragment());
        fragments.add(new ExchangeRateFragment());
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), fragments);

        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount()); // 切换不消除数据
    }
}
