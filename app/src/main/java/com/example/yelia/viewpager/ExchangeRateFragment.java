package com.example.yelia.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.yelia.viewpager.MainActivity.mainActivity;

/**
 * Created by yelia on 2017/10/16.
 */

public class ExchangeRateFragment extends Fragment {
    public static String sourceCurrency;
    public static String targetCurrency;

    private TextView rateTimeText;

    private Spinner rateSpin1;
    private Spinner rateSpin2;
    public static String[] exchangeRateItem;

    private EditText rateEdt1;
    private EditText rateEdt2;

    private GridView rateFunGrid;
    private ArrayAdapter rateFunAdapter;
    private final String[] rateFunTexts = new String[] { "清  空", "转  换" };
    private List<String> rateFunList = new ArrayList<String>();

    private GridView rateNumGrid;
    private ArrayAdapter rateNumAdapter;
    private final String[] rateNumTexts = new String[] {
            "7", "8", "9",
            "4", "5", "6",
            "1", "2", "3"
    };
    private List<String> rateNumList = new ArrayList<String>();

    private GridView rateNumZeroGrid;
    private ArrayAdapter rateNumZeroAdapter;
    private final String[] rateNumZeroTexts = new String[] { "0", "." };
    private List<String> rateNumZeroList = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exchange_rate, container, false);

        rateTimeText = (TextView)view.findViewById(R.id.rateTimeText);
        rateTimeText.setText("汇率更新时间：" + MainActivity.rate.getDate() + " " + MainActivity.rate.getDatatime());

        exchangeRateItem = getResources().getStringArray(R.array.exchangeRateItem);

        rateSpin1 = (Spinner)view.findViewById(R.id.rateSpin1);
        rateSpin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sourceCurrency = exchangeRateItem[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        rateSpin2 = (Spinner)view.findViewById(R.id.rateSpin2);
        rateSpin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                targetCurrency = exchangeRateItem[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        rateEdt1 = (EditText)view.findViewById(R.id.rateEdt1);
        rateEdt2 = (EditText)view.findViewById(R.id.rateEdt2);

        rateFunGrid = (GridView)view.findViewById(R.id.rateFunGrid);
        for (String s : rateFunTexts) { rateFunList.add(s); }
        rateFunAdapter = new ExchangeRateAdapter(mainActivity, R.id.textButton, rateFunList);
        rateFunGrid.setAdapter(rateFunAdapter);
        rateFunGrid.setOnItemClickListener(new ExchangeRateItemClickListener(rateEdt1, rateEdt2));

        rateNumGrid = (GridView)view.findViewById(R.id.rateNumGrid);
        for (String s : rateNumTexts) { rateNumList.add(s); }
        rateNumAdapter = new ExchangeRateAdapter(mainActivity, R.id.textButton, rateNumList);
        rateNumGrid.setAdapter(rateNumAdapter);
        rateNumGrid.setOnItemClickListener(new ExchangeRateItemClickListener(rateEdt1, rateEdt2));

        rateNumZeroGrid = (GridView)view.findViewById(R.id.rateNumZeroGrid);
        for (String s : rateNumZeroTexts) { rateNumZeroList.add(s); }
        rateNumZeroAdapter = new ExchangeRateAdapter(mainActivity, R.id.textButton, rateNumZeroList);
        rateNumZeroGrid.setAdapter(rateNumZeroAdapter);
        rateNumZeroGrid.setOnItemClickListener(new ExchangeRateItemClickListener(rateEdt1, rateEdt2));

        return view;
    }
}
