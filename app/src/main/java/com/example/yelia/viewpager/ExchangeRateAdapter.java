package com.example.yelia.viewpager;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yelia on 2017/10/16.
 */

public class ExchangeRateAdapter extends ArrayAdapter<String> {
    private final static int COLUMN_NUMBER = 4;

    public ExchangeRateAdapter(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // 获取当前项
        String str = getItem(position);
        // 实例化view
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_button, null);
        // 根据view找到相应的textView
        TextView textView = (TextView)view.findViewById(R.id.textButton);
        // 设置textView文本
        textView.setText(str);
        // 设置高度与宽度相等
        textView.setHeight(parent.getWidth()/COLUMN_NUMBER*13/15);
        // 若当前版本小于5.0，修改效果
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            textView.setBackgroundResource(R.drawable.selector_button);
        }

        if (str.equals("清  空") || str.equals("转  换")) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                textView.setBackgroundResource(R.drawable.selector_button_c);
            }
            else {
                textView.setBackgroundResource(R.drawable.ripple_button_c);
            }
            textView.setTextColor(getContext().getResources().getColor(R.color.colorButtonCText));
        }

        return view;
    }
}
