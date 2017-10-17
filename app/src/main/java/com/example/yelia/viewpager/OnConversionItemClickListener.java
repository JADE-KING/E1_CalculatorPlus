package com.example.yelia.viewpager;

import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import static com.example.yelia.viewpager.Fragment_conversion.sourceConversion;
import static com.example.yelia.viewpager.Fragment_conversion.targetConversion;

/**
 * Created by yelia on 2017/10/16.
 */

public class OnConversionItemClickListener implements AdapterView.OnItemClickListener {
    private EditText numberEdit1;
    private EditText numberEdit2;

    private String result;

    public OnConversionItemClickListener(EditText e1, EditText e2) {
        numberEdit1 = e1;
        numberEdit2 = e2;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text = (String)parent.getAdapter().getItem(position);

        switch (sourceConversion) {
            case 10:
                if (new String("0123456789").indexOf(text.charAt(0)) >= 0) {
                    numberEdit1.setText(numberEdit1.getText() + text);
                }
                break;

            case 2:
                if (new String("01").indexOf(text.charAt(0)) >= 0) {
                    numberEdit1.setText(numberEdit1.getText() + text);
                }
                break;

            case 8:
                if (new String("01234567").indexOf(text.charAt(0)) >= 0) {
                    numberEdit1.setText(numberEdit1.getText() + text);
                }
                break;

            case 16:
                if (new String("0123456789ABCDEF").indexOf(text.charAt(0)) >= 0) {
                    numberEdit1.setText(numberEdit1.getText() + text);
                }
                break;
        }

        if (text.equals("清  空")) {
            numberEdit1.setText("");
            numberEdit2.setText("");
        }
        else if (text.equals("转  换")) {
            if (!numberEdit1.getText().equals("")) {
                result = numberEdit1.getText().toString();

                switch (sourceConversion) {
                    case 10:
                        switch (targetConversion) {
                            case 10:
                                break;
                            case 2:
                                result = Integer.toBinaryString(Integer.valueOf(result));
                                break;
                            case 8:
                                result = Integer.toOctalString(Integer.valueOf(result));
                                break;
                            case 16:
                                result = Integer.toHexString(Integer.valueOf(result));
                                break;
                        }
                        break;

                    case 2:
                        switch (targetConversion) {
                            case 10:
                                result = Integer.valueOf(result, 2).toString();
                                break;
                            case 2:
                                break;
                            case 8:
                                result = Integer.valueOf(result, 2).toString();
                                result = Integer.toOctalString(Integer.valueOf(result));
                                break;
                            case 16:
                                result = Integer.valueOf(result, 2).toString();
                                result = Integer.toHexString(Integer.valueOf(result));
                                break;
                        }
                        break;

                    case 8:
                        switch (targetConversion) {
                            case 10:
                                result = Integer.valueOf(result, 8).toString();
                                break;
                            case 2:
                                result = Integer.valueOf(result, 8).toString();
                                result = Integer.toBinaryString(Integer.valueOf(result));
                                break;
                            case 8:
                                break;
                            case 16:
                                result = Integer.valueOf(result, 8).toString();
                                result = Integer.toHexString(Integer.valueOf(result));
                                break;
                        }
                        break;

                    case 16:
                        switch (targetConversion) {
                            case 10:
                                result = Integer.valueOf(result, 16).toString();
                                break;
                            case 2:
                                result = Integer.valueOf(result, 16).toString();
                                result = Integer.toBinaryString(Integer.valueOf(result));
                                break;
                            case 8:
                                result = Integer.valueOf(result, 16).toString();
                                result = Integer.toOctalString(Integer.valueOf(result));
                                break;
                            case 16:
                                break;
                        }
                        break;
                }
                numberEdit2.setText(result);
            }
        }
    }
}
