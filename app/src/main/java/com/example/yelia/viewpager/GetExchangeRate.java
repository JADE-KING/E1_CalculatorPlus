package com.example.yelia.viewpager;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yelia on 2017/10/16.
 */

public class GetExchangeRate {
    private String urlStr = "http://api.avatardata.cn/Currency/CurrencyList?key=a1bd8c3a21ed40b8871876987bc697b1";
    private List<Double> rates = new ArrayList<Double>();
    private String datatime;
    private String date;

    private final static int CURRENCY_NUMBER = 13;

    public GetExchangeRate() {
        BufferedReader in = null;
        try {
            // 申请数据
            URL url = new URL(urlStr);
            URLConnection connection = url.openConnection();
            connection.connect();

            // 读取响应数据
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String dataStr = "";
            String line;
            while ((line = in.readLine()) != null) {
                dataStr += line;
            }

            // 解析JSON字符串
            JSONObject dataJson = new JSONObject(dataStr);
            JSONObject result = dataJson.getJSONObject("result");
            JSONObject data = null;

            for (int i = 0; i < CURRENCY_NUMBER; i++) {
                data = result.getJSONObject("data" + (i + 1));
                switch (i) {
                    case 1: // 美元
                        rates.add(1.0);
                        break;
                    case 0:
                    case 2:
                    case 3:
                    case 4:
                        rates.add(1 / Double.valueOf(data.getString("buyPic")));
                        break;
                    default:
                        rates.add(Double.valueOf(data.getString("buyPic")));
                        break;
                }
            }

            datatime = data.getString("datatime");
            date = data.getString("date");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Double> getRates() {
        return rates;
    }

    public String getDatatime() {
        return datatime;
    }

    public String getDate() {
        return date;
    }

}
