package com.example.yelia.viewpager;

import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import java.util.Stack;

/**
 * Created by yelia on 2017/9/15.
 */

public class OnButtonItemClickListener implements AdapterView.OnItemClickListener {
    private EditText lastResult;
    private EditText outputScreen;

    private String numStr; // outputScreen里的字符串
    private double lastNumber; // 上一个数
    private char lastSymbol; // 上一个运算符
    private double number; // 当前数
    private char symbol; // 当前运算符
    private double interimResult = 0; // 中间结果

    private Stack<Double> numbers = new Stack<Double>(); // 数栈
    private Stack<Character> symbols = new Stack<Character>(); // 符栈

    private boolean isResult = true; // outputScreen当前显示是否为最终计算结果
    private boolean isFirstNumber = true; // isResult为false时赋值为true，输入第一个运算符后赋值为false

    private static final String FIGHTING = "(๑•̀ㅂ•́)و✧"; // (●'◡'●)

    // private int numberCounter = 0; // 数字统计
    // private boolean isMaxCount = false; // 是否达到最大值
    // private static final int MAX_COUNT = 10; // 数字统计最大值为10


    public OnButtonItemClickListener(EditText l, EditText o) {
        lastResult = l;
        outputScreen = o;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text = (String)parent.getAdapter().getItem(position);

        // 数字
        if (text.equals("0")) {
            clickedZeroNumber();
        }
        else if (isNonzeroNumber(text)) {
            clickedNonzeroNumber(text);
        }

        /**
         * 即时计算
         *
         * 运算符为+或-时
         *      将栈内所有的数和运算符进行运算
         *      然后将结果和当前运算符压入栈中
         *
         * 运算符为×或÷时
         *      若上一个运算符也是×或÷
         *          需要将上一步的结果计算出来
         *          和当前运算符压入栈中；
         *      否则
         *          直接将当前数和当前运算符压入栈中
         *          等待下一个运算符
         *
         * 运算符为=时
         *      将栈内所有的数和当前数全部进行计算并显示
         *      isResult赋值为true
         */

        // 运算符 + -
        else if (text.equals("+") || text.equals("-")) {
            try {
                numStr = outputScreen.getText().toString();
                number = Double.parseDouble(numStr);
                symbol = text.charAt(0);
                interimResult = number;

                while (!symbols.empty()) {
                    number = interimResult;
                    isFirstNumber = false;
                    lastNumber = numbers.pop();
                    lastSymbol = symbols.pop();
                    interimResult = basicCalculation(lastNumber, lastSymbol, interimResult);
                }

                numbers.push(interimResult);
                symbols.push(symbol);

                numStr = Double.toString(interimResult);
                numStr = subZeroAndDot(numStr);
                lastResult.setText(numStr);
                outputScreen.setText(text);
            } catch (NumberFormatException e) {
                // [a-], [a×], [a÷] -> [a+]
                // [a+], [a×], [a÷] -> [a-]
                if (isFirstNumber) {
                    if (text.charAt(0) != symbol) {
                        symbol = text.charAt(0);
                        symbols.pop();
                        symbols.push(symbol);

                        outputScreen.setText(text);
                    }
                }

                // [(a[+-×÷]b)[+-]] -> [(a[+-×÷]b)[-+]]
                // [(a [×÷] b)[×÷]] -> [(a [×÷] b)[+-]]
                else if (text.charAt(0) != symbol && (symbol == '+' || symbol == '-' || lastSymbol == '×' || lastSymbol =='÷')) {
                    symbol = text.charAt(0);
                    symbols.pop();
                    symbols.push(symbol);

                    outputScreen.setText(text);
                }

                // [a±b×], [a±b÷] -> [(a±b)±]
                else {
                    symbol = text.charAt(0);
                    interimResult = basicCalculation(lastNumber, lastSymbol, number);
                    numbers.clear();
                    symbols.clear();
                    numbers.push(interimResult);
                    symbols.push(symbol);

                    numStr = Double.toString(interimResult);
                    numStr = subZeroAndDot(numStr);
                    lastResult.setText(numStr);
                    outputScreen.setText(text);
                }
            }
        }

        // 运算符 × ÷
        else if (text.equals("×") || text.equals("÷")) {
            try {
                numStr = outputScreen.getText().toString();
                number = Double.parseDouble(numStr);
                symbol = text.charAt(0);
                interimResult = number;

                if (!symbols.empty()) {
                    isFirstNumber = false;
                    lastNumber = numbers.pop();
                    lastSymbol = symbols.pop();

                    if (lastSymbol == '×' || lastSymbol == '÷') {
                        interimResult = basicCalculation(lastNumber, lastSymbol, interimResult);
                        lastResult.setText(lastResult.getText().toString().replace(subZeroAndDot(Double.toString(lastNumber)) + lastSymbol, ""));
                    }
                    else {
                        numbers.push(lastNumber);
                        symbols.push(lastSymbol);
                    }
                }

                numbers.push(interimResult);
                symbols.push(symbol);

                numStr = Double.toString(interimResult);
                numStr = subZeroAndDot(numStr);
                lastResult.setText(lastResult.getText().toString().replace(FIGHTING, "") + numStr);
                outputScreen.setText(text);
            } catch (NumberFormatException e) {
                // [a+], [a-], [a÷] -> [a×]
                // [a+], [a-], [a×] -> [a÷]
                if (isFirstNumber) {
                    if (text.charAt(0) != symbol) {
                        symbol = text.charAt(0);
                        symbols.pop();
                        symbols.push(symbol);

                        outputScreen.setText(text);
                    }
                }

                // [(a[×÷]b)[+-×÷]] -> [(a[×÷]b)[×÷]]
                //   a[+-]b [×÷]    ->   a[+-]b [÷×]
                else if (text.charAt(0) != symbol && (symbol == '×' || symbol == '÷' || lastSymbol == '×' || lastSymbol == '÷')) {
                    symbol = text.charAt(0);
                    symbols.pop();
                    symbols.push(symbol);

                    outputScreen.setText(text);
                }

                // [(a±b)±] -> [a±b×], [a±b÷]
                else {
                    symbol = text.charAt(0);
                    interimResult = number;
                    numbers.clear();
                    symbols.clear();
                    numbers.push(lastNumber);
                    symbols.push(lastSymbol);
                    numbers.push(number);
                    symbols.push(symbol);

                    numStr = Double.toString(interimResult);
                    numStr = subZeroAndDot(numStr);
                    lastResult.setText(subZeroAndDot(Double.toString(lastNumber)) + lastSymbol + numStr);
                    outputScreen.setText(text);
                }
            }
        }

        // 运算符 =
        else if (text.equals("=")) {
            try {
                numStr = outputScreen.getText().toString();
                number = Double.parseDouble(numStr);
                interimResult = number;

                while (!symbols.empty()) {
                    isFirstNumber = false;
                    lastNumber = numbers.pop();
                    lastSymbol = symbols.pop();
                    interimResult = basicCalculation(lastNumber, lastSymbol, interimResult);
                }

                numStr = Double.toString(interimResult);
                numStr = subZeroAndDot(numStr);
                lastResult.setText(FIGHTING);
                outputScreen.setText(numStr);
                isResult = true;
            } catch (NumberFormatException e) {
                number = numbers.pop();
                symbols.pop();
                interimResult = number;

                while (!symbols.empty()) {
                    isFirstNumber = false;
                    lastNumber = numbers.pop();
                    lastSymbol = symbols.pop();
                    interimResult = basicCalculation(lastNumber, lastSymbol, interimResult);
                }

                numStr = Double.toString(interimResult);
                numStr = subZeroAndDot(numStr);
                lastResult.setText(FIGHTING);
                outputScreen.setText(numStr);
            }
        }

        // 正负号
        else if (text.equals("±")) {
            if (isResult) {
                isResult = false;
                isFirstNumber = true;
            }
            if (outputScreen.getText().charAt(0) == '-' && outputScreen.getText().length() > 1)
                outputScreen.setText(outputScreen.getText().subSequence(1, outputScreen.length()));
            else if (!isSymbol(outputScreen.getText().toString())) {
                outputScreen.setText('-' + outputScreen.getText().toString());
            }
            else {
                lastResult.setText(lastResult.getText().toString() + outputScreen.getText().toString());
                outputScreen.setText("-0");
            }
        }

        // 小数点
        else if (text.equals(".")) {
            if (isResult) {
                isResult = false;
                isFirstNumber = true;
                outputScreen.setText("0");
            }
            if (outputScreen.getText().toString().indexOf('.') > 0);
            else if (!isSymbol(outputScreen.getText().toString())) {
                outputScreen.setText(outputScreen.getText().toString() + '.');
            }
            else {
                lastResult.setText(lastResult.getText().toString() + outputScreen.getText().toString());
                outputScreen.setText("0.");
            }
        }

        // 百分号
        else if (text.equals("%")) {
            if (isResult) {
                isResult = false;
                isFirstNumber = true;
            }
            if (!isSymbol(outputScreen.getText().toString()) && !outputScreen.getText().toString().equals("0")) {
                double d = Double.parseDouble(outputScreen.getText().toString());
                d = d / 100;
                outputScreen.setText(subZeroAndDot(Double.toString(d)));
            }
        }

        // 清空键
        else if (text.equals("C")) {
           if (!lastResult.getText().equals(FIGHTING) || !outputScreen.getText().toString().equals("0")) {
                numbers.clear();
                symbols.clear();

                lastResult.setText(FIGHTING);
                outputScreen.setText("0");
            }
            isResult = false;
            isFirstNumber = true;
        }

        // 退回键
        else if (text.equals("DEL")) {
            if (isResult) {
                isResult = false;
                isFirstNumber = true;
                outputScreen.setText("0");
            }
            if (isSymbol(outputScreen.getText().toString()));
            else if (!outputScreen.getText().toString().equals("0")) {
                outputScreen.setText(outputScreen.getText().subSequence(0, outputScreen.length() - 1));
                if (outputScreen.getText().toString().equals(""))
                    outputScreen.setText("0");
                else if (outputScreen.getText().toString().equals("-")) {
                    outputScreen.setText("-0");
                }
            }
        }

        lastResult.setSelection(lastResult.length()); // 光标在最后
        outputScreen.setSelection(outputScreen.length()); // 光标在最后
    }

    // 去掉Double转String时小数点后多余的0和小数点
    private String subZeroAndDot(String s) {
        if (s.indexOf('.') > 0) {
            s = s.replaceAll("0+?$", "");
            s = s.replaceAll("[.]$", "");
        }
        return s;
    }

    private boolean isNonzeroNumber(String text) {
        String[] textNonzeroNumbers = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
        for (String s : textNonzeroNumbers) {
            if (s.equals(text)) {
                return true;
            }
        }
        return false;
    }

    private boolean isSymbol(String text) {
        String[] textSymbols = new String[] { "+", "-", "×", "÷" };
        for (String s : textSymbols) {
            if (s.equals(text)) {
                return true;
            }
        }
        return false;
    }

    private void clickedZeroNumber() {
        if (isResult) {
            outputScreen.setText("");
            isResult = false;
            isFirstNumber = true;
        }
        if (isSymbol(outputScreen.getText().toString())) {
            lastResult.setText(lastResult.getText().toString() + outputScreen.getText().toString());
            outputScreen.setText("");
        }
        if (!outputScreen.getText().toString().equals("0") && !outputScreen.getText().toString().equals("-0")) {
            outputScreen.setText(outputScreen.getText().toString() + '0');
        }
    }

    private void clickedNonzeroNumber(String text) {
        if (isResult) {
            outputScreen.setText("");
            isResult = false;
            isFirstNumber = true;
        }
        if (isSymbol(outputScreen.getText().toString())) {
            lastResult.setText(lastResult.getText().toString() + outputScreen.getText().toString());
            outputScreen.setText("");
        }

        if (outputScreen.getText().toString().equals("0")) {
            outputScreen.setText(text);
        }
        else if (outputScreen.getText().toString().equals("-0")) {
            outputScreen.setText("-" + text);
        }
        else {
            outputScreen.setText(outputScreen.getText().toString() + text);
        }
    }

    private double basicCalculation(double number1, char symbol, double number2) {
        double result = 0;
        switch (symbol) {
            case '+':
                result = number1 + number2;
                break;
            case '-':
                result = number1 - number2;
                break;
            case '×':
                result = number1 * number2;
                break;
            case '÷':
                result = number1 / number2;
                break;
        }
        return result;
    }
}
