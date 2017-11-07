package com.example.yelia.viewpager;

import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

/**
 * Created by yelia on 2017/11/6.
 */

public class UnitConversionItemClickListener implements AdapterView.OnItemClickListener {
    private EditText unitEdit1;
    private EditText unitEdit2;

    public UnitConversionItemClickListener(EditText unitEdit1, EditText unitEdit2) {
        this.unitEdit1 = unitEdit1;
        this.unitEdit2 = unitEdit2;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text = (String)parent.getAdapter().getItem(position);

        if (text.equals("0")) {
            if (!unitEdit1.getText().toString().equals("0")) {
                unitEdit1.setText(unitEdit1.getText().toString() + text);
            }
        }
        else if (new String("123456789").indexOf(text.charAt(0)) >= 0) {
            if (unitEdit1.getText().toString().equals("0")) {
                unitEdit1.setText(text);
            }
            else {
                unitEdit1.setText(unitEdit1.getText().toString() + text);
            }
        }
        else if (text.equals(".")) {
            if (unitEdit1.getText().toString().equals("")) unitEdit1.setText("0");
            if (unitEdit1.getText().toString().indexOf(".") < 0) {
                unitEdit1.setText(unitEdit1.getText().toString() + text);
            }
        }
        else if (text.equals("清  空")) {
            unitEdit1.setText("");
            unitEdit2.setText("");
        }
        else if (text.equals("转  换")) {
            double result = unitConversion(Double.valueOf(unitEdit1.getText().toString()));
            unitEdit2.setText(String.valueOf(result));
        }
    }

    private double unitConversion(double d) {
        int unitPosition = UnitConversionFragment.getUnitPosition();
        int sourceUnitPosition = UnitConversionFragment.getSourceUnitPosition();
        int targetUnitPosition = UnitConversionFragment.getTargetUnitPosition();
        d = convertToInternationalUnit(unitPosition, sourceUnitPosition, d);
        d = convertFromInternationalUnit(unitPosition, targetUnitPosition, d);
        return d;
    }

    private double convertToInternationalUnit(int unitPosition, int sourceUnitPosition, double d) {
        switch (unitPosition) {
            case 0: // 长度
                switch (sourceUnitPosition) {
                    case 0: // 千米(km)
                        d /= 0.001d;
                        break;
                    case 1: // 米(m)
                        break;
                    case 2: // 分米(dm)
                        d /= 10d;
                        break;
                    case 3: // 厘米(cm)
                        d /= 100d;
                        break;
                    case 4: // 毫米(mm)
                        d /= 1000d;
                        break;
                    case 5: // 微米(um)
                        d /= 1000000d;
                        break;
                    case 6: // 纳米(nm)
                        d /= 1000000000d;
                        break;
                    case 7: // 皮米(pm)
                        d /= 1000000000000d;
                        break;
                    case 8: // 光年(ly)
                        d /= 1.0570e-16;
                        break;
                    case 9: // 天文单位(AU)
                        d /= 6.6846e-12;
                        break;
                }
                break;
            case 1: // 面积
                switch (sourceUnitPosition) {
                    case 0: // 平方千米(km²)
                        d /= 1e-6;
                        break;
                    case 1: // 公顷(ha)
                        d /= 0.0001;
                        break;
                    case 2: // 公亩(are)
                        d /= 0.01;
                        break;
                    case 3: // 平方米(m²)
                        break;
                    case 4: // 平方分米(dm²)
                        d /= 100d;
                        break;
                    case 5: // 平方厘米(cm²)
                        d /= 10000d;
                        break;
                    case 6: // 平方毫米(mm²)
                        d /= 1000000d;
                        break;
                }
                break;
            case 2: // 体积
                switch (sourceUnitPosition) {
                    case 0: // 立方米(m³)
                        break;
                    case 1: // 立方分米(dm³)
                        d /= 1000d;
                        break;
                    case 2: // 立方厘米(cm³)
                        d /= 1000000d;
                        break;
                    case 3: // 立方毫米(mm³)
                        d /= 1000000000d;
                        break;
                    case 4: // 升(l)
                        d /= 1000d;
                        break;
                    case 5: // 分升(dl)
                        d /= 10000d;
                        break;
                    case 6: // 毫升(ml)
                        d /= 1000000d;
                        break;
                    case 7: // 厘升(cl)
                        d /= 100000d;
                        break;
                    case 8: // 公石(hl)
                        d /= 10d;
                        break;
                    case 9: // 微升(ul)
                        d /= 1000000000d;
                        break;
                }
                break;
            case 3: // 质量
                switch (sourceUnitPosition) {
                    case 0: // 千克(kg)
                        break;
                    case 1: // 克(g)
                        d /= 1000d;
                        break;
                    case 2: // 毫克(mg)
                        d /= 1000000d;
                        break;
                    case 3: // 微克(ug)
                        d /= 1000000000d;
                        break;
                    case 4: // 吨(t)
                        d /= 0.001d;
                        break;
                    case 5: // 公担(q)
                        d /= 0.01d;
                        break;
                    case 6: // 克拉(ct)
                        d /= 5000d;
                        break;
                    case 7: // 分(point)
                        d /= 500000d;
                        break;
                }
                break;
            case 4: // 温度
                switch (sourceUnitPosition) {
                    case 0: // 摄氏度(°C)
                        d += 273.15d;
                        break;
                    case 1: // 华氏度(°F)
                        d = (d - 32) * 5 / 9 + 273.15;
                        break;
                    case 2: // 开式度(K)
                        break;
                }
                break;
            case 5: // 时间
                switch (sourceUnitPosition) {
                    case 0: // 年(yr)
                        d *= 31536000d;
                        break;
                    case 1: // 周(weed)
                        d *= 604800d;
                        break;
                    case 2: // 天(d)
                        d *= 86400d;
                        break;
                    case 3: // 时(h)
                        d *= 3600d;
                        break;
                    case 4: // 分(min)
                        d *= 60d;
                        break;
                    case 5: // 秒(s)
                        break;
                    case 6: // 毫秒(ms)
                        d *= 0.001d;
                        break;
                    case 7: // 微秒(μs)
                        d *= 1e-6;
                        break;
                    case 8: // 纳秒(ns)
                        d *= 1e-9;
                        break;
                }
                break;
            case 6: // 数据存储
                switch (sourceUnitPosition) {
                    case 0: //比特(bit)
                        d *= 0.125d;
                        break;
                    case 1: // 字节(b)
                        break;
                    case 2: // 千字节(kb)
                        d *= 1024d;
                        break;
                    case 3: // 兆字节(mb)
                        d *= 1048576d;
                        break;
                    case 4: // 千兆字节(gb)
                        d *= 1073741824d;
                        break;
                }
                break;
        }
        return d;
    }

    private double convertFromInternationalUnit(int unitPosition, int targetUnitPosition, double d) {
        switch (unitPosition) {
            case 0: // 长度
                switch (targetUnitPosition) {
                    case 0: // 千米(km)
                        d *= 0.001d;
                        break;
                    case 1: // 米(m)
                        break;
                    case 2: // 分米(dm)
                        d *= 10d;
                        break;
                    case 3: // 厘米(cm)
                        d *= 100d;
                        break;
                    case 4: // 毫米(mm)
                        d *= 1000d;
                        break;
                    case 5: // 微米(um)
                        d *= 1000000d;
                        break;
                    case 6: // 纳米(nm)
                        d *= 1000000000d;
                        break;
                    case 7: // 皮米(pm)
                        d *= 1000000000000d;
                        break;
                    case 8: // 光年(ly)
                        d *= 1.0570e-16;
                        break;
                    case 9: // 天文单位(AU)
                        d *= 6.6846e-12;
                        break;
                }
                break;
            case 1: // 面积
                switch (targetUnitPosition) {
                    case 0: // 平方千米(km²)
                        d *= 1e-6;
                        break;
                    case 1: // 公顷(ha)
                        d *= 0.0001;
                        break;
                    case 2: // 公亩(are)
                        d *= 0.01;
                        break;
                    case 3: // 平方米(m²)
                        break;
                    case 4: // 平方分米(dm²)
                        d *= 100d;
                        break;
                    case 5: // 平方厘米(cm²)
                        d *= 10000d;
                        break;
                    case 6: // 平方毫米(mm²)
                        d *= 1000000d;
                        break;
                }
                break;
            case 2: // 体积
                switch (targetUnitPosition) {
                    case 0: // 立方米(m³)
                        break;
                    case 1: // 立方分米(dm³)
                        d *= 1000d;
                        break;
                    case 2: // 立方厘米(cm³)
                        d *= 1000000d;
                        break;
                    case 3: // 立方毫米(mm³)
                        d *= 1000000000d;
                        break;
                    case 4: // 升(l)
                        d *= 1000d;
                        break;
                    case 5: // 分升(dl)
                        d *= 10000d;
                        break;
                    case 6: // 毫升(ml)
                        d *= 1000000d;
                        break;
                    case 7: // 厘升(cl)
                        d *= 100000d;
                        break;
                    case 8: // 公石(hl)
                        d *= 10d;
                        break;
                    case 9: // 微升(ul)
                        d *= 1000000000d;
                        break;
                }
                break;
            case 3: // 质量
                switch (targetUnitPosition) {
                    case 0: // 千克(kg)
                        break;
                    case 1: // 克(g)
                        d *= 1000d;
                        break;
                    case 2: // 毫克(mg)
                        d *= 1000000d;
                        break;
                    case 3: // 微克(ug)
                        d *= 1000000000d;
                        break;
                    case 4: // 吨(t)
                        d *= 0.001d;
                        break;
                    case 5: // 公担(q)
                        d *= 0.01d;
                        break;
                    case 6: // 克拉(ct)
                        d *= 5000d;
                        break;
                    case 7: // 分(point)
                        d *= 500000d;
                        break;
                }
                break;
            case 4: // 温度
                switch (targetUnitPosition) {
                    case 0: // 摄氏度(°C)
                        d -= 273.15d;
                        break;
                    case 1: // 华氏度(°F)
                        d = d * 1.8 - 459.7;
                        break;
                    case 2: // 开式度(K)
                        break;
                }
                break;
            case 5: // 时间
                switch (targetUnitPosition) {
                    case 0: // 年(yr)
                        d /= 31536000d;
                        break;
                    case 1: // 周(weed)
                        d /= 604800d;
                        break;
                    case 2: // 天(d)
                        d /= 86400d;
                        break;
                    case 3: // 时(h)
                        d /= 3600d;
                        break;
                    case 4: // 分(min)
                        d /= 60d;
                        break;
                    case 5: // 秒(s)
                        break;
                    case 6: // 毫秒(ms)
                        d /= 0.001d;
                        break;
                    case 7: // 微秒(μs)
                        d /= 1e-6;
                        break;
                    case 8: // 纳秒(ns)
                        d /= 1e-9;
                        break;
                }
                break;
            case 6: // 数据存储
                switch (targetUnitPosition) {
                    case 0: //比特(bit)
                        d /= 0.125d;
                        break;
                    case 1: // 字节(b)
                        break;
                    case 2: // 千字节(kb)
                        d /= 1024d;
                        break;
                    case 3: // 兆字节(mb)
                        d /= 1048576d;
                        break;
                    case 4: // 千兆字节(gb)
                        d /= 1073741824d;
                        break;
                }
                break;
        }
        return d;
    }
}
