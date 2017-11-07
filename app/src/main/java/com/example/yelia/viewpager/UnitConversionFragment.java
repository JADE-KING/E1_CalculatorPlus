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

/**
 * Created by yelia on 2017/11/6.
 */

public class UnitConversionFragment extends Fragment {
    private static int unitPosition;
    private static int sourceUnitPosition;
    private static int targetUnitPosition;

    private String[] unitFunGridText = new String[] { "清  空", "转  换" };
    private String[] unitNumGridText = new String[] {
            "7", "8", "9",
            "4", "5", "6",
            "1", "2", "3"
    };
    private String[] unitNumZeroGridText = new String[] { "0", "." };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.unit_conversion, container, false);

        final String[] internationalUnitItem = getResources().getStringArray(R.array.internationalUnit);
        final String[] unitLengthItem = getResources().getStringArray(R.array.length);
        final String[] unitAreaItem = getResources().getStringArray(R.array.area);
        final String[] unitVolumeItem = getResources().getStringArray(R.array.volume);
        final String[] unitWeightItem = getResources().getStringArray(R.array.weight);
        final String[] unitTemperatureItem = getResources().getStringArray(R.array.temperature);
        final String[] unitTimeItem = getResources().getStringArray(R.array.time);
        final String[] unitDataStorageItem = getResources().getStringArray(R.array.dataStorage);

        Spinner unitSpin0 = (Spinner)view.findViewById(R.id.unitSpin0);
        final Spinner unitSpin1 = (Spinner)view.findViewById(R.id.unitSpin1);
        final Spinner unitSpin2 = (Spinner)view.findViewById(R.id.unitSpin2);
        final EditText unitEdit0 = (EditText)view.findViewById(R.id.unitEdit0);
        EditText unitEdit1 = (EditText)view.findViewById(R.id.unitEdit1);
        EditText unitEdit2 = (EditText)view.findViewById(R.id.unitEdit2);
        GridView unitFunGrid = (GridView)view.findViewById(R.id.unitFunGrid);
        GridView unitNumGrid = (GridView)view.findViewById(R.id.unitNumGrid);
        GridView unitNumZeroGrid = (GridView)view.findViewById(R.id.unitNumZeroGrid);

        unitSpin0.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setUnitPosition(position);
                setSourceUnitPosition(0);
                setTargetUnitPosition(0);
                unitEdit0.setText("国际单位：" + internationalUnitItem[position]);

                String [] unitItem = null;
                switch (position) {
                    case 0:
                        unitItem = unitLengthItem;
                        break;
                    case 1:
                        unitItem = unitAreaItem;
                        break;
                    case 2:
                        unitItem = unitVolumeItem;
                        break;
                    case 3:
                        unitItem = unitWeightItem;
                        break;
                    case 4:
                        unitItem = unitTemperatureItem;
                        break;
                    case 5:
                        unitItem = unitTimeItem;
                        break;
                    case 6:
                        unitItem = unitDataStorageItem;
                        break;
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.mainActivity, android.R.layout.simple_spinner_item, unitItem);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                unitSpin1.setAdapter(adapter);
                unitSpin2.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        unitSpin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setSourceUnitPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        unitSpin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setTargetUnitPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        List<String> unitFunGridList = new ArrayList<String>();
        List<String> unitNumGridList = new ArrayList<String>();
        List<String> unitNumZeroGridList = new ArrayList<String>();

        for (String s : unitFunGridText)
            unitFunGridList.add(s);
        for (String s : unitNumGridText)
            unitNumGridList.add(s);
        for (String s : unitNumZeroGridText)
            unitNumZeroGridList.add(s);

        ArrayAdapter unitFunGridAdapter = new UnitConversionAdapter(MainActivity.mainActivity, R.id.textButton, unitFunGridList);
        ArrayAdapter unitNumGridAdapter = new UnitConversionAdapter(MainActivity.mainActivity, R.id.textButton, unitNumGridList);
        ArrayAdapter unitNumZeroGridAdapter = new UnitConversionAdapter(MainActivity.mainActivity, R.id.textButton, unitNumZeroGridList);

        unitFunGrid.setAdapter(unitFunGridAdapter);
        unitNumGrid.setAdapter(unitNumGridAdapter);
        unitNumZeroGrid.setAdapter(unitNumZeroGridAdapter);

        unitFunGrid.setOnItemClickListener(new UnitConversionItemClickListener(unitEdit1, unitEdit2));
        unitNumGrid.setOnItemClickListener(new UnitConversionItemClickListener(unitEdit1, unitEdit2));
        unitNumZeroGrid.setOnItemClickListener(new UnitConversionItemClickListener(unitEdit1, unitEdit2));

        return view;
    }

    public static int getUnitPosition() {
        return unitPosition;
    }

    public static void setUnitPosition(int unitPosition) {
        UnitConversionFragment.unitPosition = unitPosition;
    }

    public static int getSourceUnitPosition() {
        return sourceUnitPosition;
    }

    public static void setSourceUnitPosition(int sourceUnitPosition) {
        UnitConversionFragment.sourceUnitPosition = sourceUnitPosition;
    }

    public static int getTargetUnitPosition() {
        return targetUnitPosition;
    }

    public static void setTargetUnitPosition(int targetUnitPosition) {
        UnitConversionFragment.targetUnitPosition = targetUnitPosition;
    }
}
