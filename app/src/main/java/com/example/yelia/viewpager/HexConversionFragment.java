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

import java.util.ArrayList;
import java.util.List;

import static com.example.yelia.viewpager.MainActivity.mainActivity;

/**
 * Created by yelia on 2017/10/15.
 */

public class HexConversionFragment extends Fragment {
    public static int sourceConversion;
    public static int targetConversion;

    private Spinner numSpin1;
    private Spinner numSpin2;
    private String[] conversionItem;

    private EditText numEdt1;
    private EditText numEdt2;

    private GridView conFunGrid;
    private ArrayAdapter conFunAdapter;
    private final String[] conFunTexts = new String[] { "清  空", "转  换" };
    private List<String> conFunList = new ArrayList<String>();

    private GridView conNumGrid;
    private ArrayAdapter conNumAdapter;
    private final String[] conNumTexts = new String[] {
            "7", "8", "9", "F",
            "4", "5", "6", "E",
            "1", "2", "3", "D",
            "0", "A", "B", "C"
    };
    private List<String> conNumList = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hex_conversion, container, false);

        conversionItem = getResources().getStringArray(R.array.conversionItem);

        numSpin1 = (Spinner) view.findViewById(R.id.numSpin1);
        numSpin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sourceConversion = Integer.valueOf(conversionItem[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        numSpin2 = (Spinner) view.findViewById(R.id.numSpin2);
        numSpin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                targetConversion = Integer.valueOf(conversionItem[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        numEdt1 = (EditText)view.findViewById(R.id.numEdt1);
        numEdt2 = (EditText)view.findViewById(R.id.numEdt2);

        conFunGrid = (GridView)view.findViewById(R.id.conFunGrid);
        for (String s : conFunTexts) { conFunList.add(s); }
        conFunAdapter = new HexConversionAdapter(mainActivity, R.id.textButton, conFunList);
        conFunGrid.setAdapter(conFunAdapter);
        conFunGrid.setOnItemClickListener(new HexConversionItemClickListener(numEdt1, numEdt2));

        conNumGrid = (GridView)view.findViewById(R.id.conNumGrid);
        for (String s : conNumTexts) { conNumList.add(s); }
        conNumAdapter = new HexConversionAdapter(mainActivity, R.id.textButton, conNumList);
        conNumGrid.setAdapter(conNumAdapter);
        conNumGrid.setOnItemClickListener(new HexConversionItemClickListener(numEdt1, numEdt2));

        return view;
    }
}
