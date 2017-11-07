package com.example.yelia.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by yelia on 2017/10/17.
 */

public class WelcomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.welcome, container, false);
        EditText introduction = (EditText)view.findViewById(R.id.introduction);
        introduction.setText(
                "\nHello world" +
                "\nI'm fafafa" +
                "\n" +
                "\n本计算器有以下四个功能:" +
                "\n1. 简单连续计算" +
                "\n2. 进制转换" +
                "\n3. 单位转换" +
                "\n4. 汇率转换(需要网络进行初始化)" +
                "\n" +
                "\nTip: 左划右划可以切换界面" +
                "\n");
        return view;
    }
}
