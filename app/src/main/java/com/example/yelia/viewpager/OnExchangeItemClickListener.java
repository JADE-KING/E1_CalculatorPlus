package com.example.yelia.viewpager;

import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.example.yelia.viewpager.Fragment_exchange_rate.exchangeRateItem;
import static com.example.yelia.viewpager.Fragment_exchange_rate.sourceCurrency;
import static com.example.yelia.viewpager.Fragment_exchange_rate.targetCurrency;
import static com.example.yelia.viewpager.MainActivity.rate;

/**
 * Created by yelia on 2017/10/16.
 */

public class OnExchangeItemClickListener implements AdapterView.OnItemClickListener {
    private final static int DECIMAL_PLACES = 4; // 小数位数

    private List<String> exchangeRateItemList = new ArrayList<String>();

    private List<Double> rates = new ArrayList<Double>();
    private String datatime;
    private String date;

    private EditText rateEdit1;
    private EditText rateEdit2;

    private double result;

    public OnExchangeItemClickListener(EditText e1, EditText e2) {
        for (String s : exchangeRateItem)
            exchangeRateItemList.add(s);

        rates = rate.getRates();
        datatime = rate.getDatatime();
        date = rate.getDate();

        rateEdit1 = e1;
        rateEdit2 = e2;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text = (String)parent.getAdapter().getItem(position);

        if (text.equals("0")) {
            if (!rateEdit1.getText().equals("0")) {
                if (rateEdit1.getText().toString().indexOf('.') !=
                        rateEdit1.getText().length() - DECIMAL_PLACES - 1) {
                    rateEdit1.setText(rateEdit1.getText() + text);
                }
            }
        }
        else if (new String("123456789").indexOf(text.charAt(0)) >= 0) {
            if (rateEdit1.getText().toString().indexOf('.') !=
                    rateEdit1.getText().length() - DECIMAL_PLACES - 1) {
                rateEdit1.setText(rateEdit1.getText() + text);
            }
        }
        else if (text.equals(".")) {
            if (rateEdit1.getText().equals("")) rateEdit1.setText("0");
            if (rateEdit1.getText().toString().indexOf('.') < 0) {
                rateEdit1.setText(rateEdit1.getText() + text);
            }
        }
        else if (text.equals("清  空")) {
            rateEdit1.setText("");
            rateEdit2.setText("");
        }
        else if (text.equals("转  换")) {
            if (!rateEdit1.getText().equals("")) {
                result = exchangeToUSD(sourceCurrency, Double.valueOf(rateEdit1.getText().toString()));
                result = exchangeFromUSD(targetCurrency, result);
            }
            rateEdit2.setText(String.format("%.4f", result));

        }
    }

    private double exchangeToUSD(String source, double d) {
        int position = exchangeRateItemList.indexOf(source);
        d = d / rates.get(position);
        return d;
    }

    private double exchangeFromUSD(String target, double d) {
        int position = exchangeRateItemList.indexOf(target);
        d = d * rates.get(position);
        return d;
    }
}
