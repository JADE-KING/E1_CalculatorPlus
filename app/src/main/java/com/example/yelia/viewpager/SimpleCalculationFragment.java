package com.example.yelia.viewpager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import static com.example.yelia.viewpager.MainActivity.mainActivity;

/**
 * Created by yelia on 2017/10/15.
 */

public class SimpleCalculationFragment extends Fragment {
    private EditText lastResult = null;
    private EditText outputScreen = null;
    private GridView gridButtons = null;

    // 适配器
    private ArrayAdapter adapter = null;

    // 按键字符集合
    private final String[] textButtons = new String[] {
            "C", "÷", "×", "DEL",
            "7", "8", "9", "%",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "±", "0", ".", "="
    };

    // 用来存储按键字符
    private List<String> textBtnList = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.simple, container, false);

        lastResult = (EditText)view.findViewById(R.id.lastResult);
        lastResult.setSelection(lastResult.length()); // 光标在最后

        outputScreen = (EditText)view.findViewById(R.id.outputScreen);
        outputScreen.setTextColor(Color.BLACK);
        outputScreen.setSelection(outputScreen.length()); // 光标在最后
        outputScreen.setMaxHeight(outputScreen.getLineHeight());

        // 把字符合集添加到List中
        for (String btn : textButtons) {
            textBtnList.add(btn);
        }

        // 创建适配器
        adapter = new SimpleCalculationAdapter(mainActivity, R.id.textButton, textBtnList);
        // 设置适配器
        gridButtons = (GridView)view.findViewById(R.id.gridButtons);
        gridButtons.setAdapter(adapter);
        // 设置按键监听器
        gridButtons.setOnItemClickListener(new SimpleCalculationItemClickListener(lastResult, outputScreen));

        return view;
    }
}
