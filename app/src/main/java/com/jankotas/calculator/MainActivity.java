package com.jankotas.calculator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.jankotas.mathlib.MathLib.mod;
import static com.jankotas.mathlib.MathLib.div;

public class MainActivity extends AppCompatActivity {

    /*
        number - hlavni cast displeje
        exponent - cast displeje zorazujici exponent
        xTen - pro dlouha nebo prilis mala/velka cisla se cislo vyjadri skrz exponent ve tvaru 10^EXP
    */

    TextView number, exponent, xTen;

    /*
        Tlacitka kalkulacky
     */

    Button but_sin, but_cos, but_tan, but_ln, but_xn, but_square, but_root, but_exp, but_ac, but_del,
            but_plus, but_minus, but_seven, but_eight, but_nine, but_multiply, but_four, but_five,
            but_six, but_divide, but_one, but_two, but_three, but_modulo, but_zero, but_comma, but_sign,
            but_equal;

    /*
        vibrator - vibrace pri zmacknuti tlacitka
     */

    Vibrator vibrator;

    /*
        Fonty pro zobrazovani digitalnich cislic
     */

    Typeface digital_font, digital_font_monospaced;

    /*
        strNum - retezec slouzici k nastavovani hlavniho ciselniku kalkulacky
        strExp - retezec slouzici k nastavovani exponentu
        operator - slouzi k vyhodnocovani prepinace pro volbu matematicke operace
     */

    String strNum, strExp, operator = "";

    /*
        firstNum - slouzi k vyjadreni prvniho operandu
        secondNum - slouzi k vyjadreni druheho operandu
     */

    Number firstNum, secondNum;

    /*
        error - pokud nastane pri operaci chyba, nastavi se na "true"
        isResult - pokud je na displeji zobrazovan vysledek, je nastaveno na "true"
     */

    Boolean error = false, isInit = false, isResult = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        number = findViewById(R.id.number);
        exponent = findViewById(R.id.exponent);
        xTen = findViewById(R.id.xTen);

        but_sin = findViewById(R.id.but_sin);
        but_cos = findViewById(R.id.but_cos);
        but_tan = findViewById(R.id.but_tan);
        but_ln = findViewById(R.id.but_ln);

        but_xn = findViewById(R.id.but_xn);
        but_square =  findViewById(R.id.but_square);
        but_root = findViewById(R.id.but_root);
        but_exp = findViewById(R.id.but_exp);

        but_ac = findViewById(R.id.but_ac);
        but_del = findViewById(R.id.but_del);
        but_plus = findViewById(R.id.but_plus);
        but_minus = findViewById(R.id.but_minus);

        but_seven = findViewById(R.id.but_seven);
        but_eight = findViewById(R.id.but_eight);
        but_nine = findViewById(R.id.but_nine);
        but_multiply = findViewById(R.id.but_multiply);

        but_four = findViewById(R.id.but_four);
        but_five = findViewById(R.id.but_five);
        but_six = findViewById(R.id.but_six);
        but_divide = findViewById(R.id.but_divide);

        but_one = findViewById(R.id.but_one);
        but_two = findViewById(R.id.but_two);
        but_three = findViewById(R.id.but_three);
        but_modulo = findViewById(R.id.but_modulo);

        but_zero = findViewById(R.id.but_zero);
        but_comma = findViewById(R.id.but_comma);
        but_sign = findViewById(R.id.but_sign);
        but_equal = findViewById(R.id.but_equal);

        digital_font = Typeface.createFromAsset(getAssets(), "fonts/digital-7.regular.ttf");
        digital_font_monospaced = Typeface.createFromAsset(getAssets(), "fonts/digital-7.mono.ttf");

        number.setTypeface(digital_font_monospaced);
        exponent.setTypeface(digital_font);
        xTen.setTypeface(digital_font);

        but_sin.setTypeface(digital_font);
        but_cos.setTypeface(digital_font);
        but_tan.setTypeface(digital_font);
        but_ln.setTypeface(digital_font);

        but_xn.setTypeface(digital_font);
        but_square.setTypeface(digital_font);
        but_root.setTypeface(digital_font);
        but_exp.setTypeface(digital_font);

        but_ac.setTypeface(digital_font);
        but_del.setTypeface(digital_font);
        but_plus.setTypeface(digital_font);
        but_minus.setTypeface(digital_font);

        but_seven.setTypeface(digital_font);
        but_eight.setTypeface(digital_font);
        but_nine.setTypeface(digital_font);
        but_multiply.setTypeface(digital_font);

        but_four.setTypeface(digital_font);
        but_five.setTypeface(digital_font);
        but_six.setTypeface(digital_font);
        but_divide.setTypeface(digital_font);

        but_one.setTypeface(digital_font);
        but_two.setTypeface(digital_font);
        but_three.setTypeface(digital_font);
        but_modulo.setTypeface(digital_font);

        but_zero.setTypeface(digital_font);
        but_comma.setTypeface(digital_font);
        but_sign.setTypeface(digital_font);
        but_equal.setTypeface(digital_font);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        firstNum = new Number();
        secondNum = new Number();
    }

    /* Numbers listeners */

    public void onClickListener_Zero(View v) {
        strNum = number.getText().toString();

        if (!strNum.equals("0") && (strNum.length() < 9 || (strNum.length() < 10 && strNum.charAt(0) == '-')) && !isInit) {
            strNum = strNum + "0";
            number.setText(strNum);
        } else if (strNum.equals("0") || isInit) {
            strNum = "0";
            isInit = false;
            number.setText(strNum);
        }
        if(isResult) {
            operator = "";
            isResult = false;
        }
        xTen.setText("");
        exponent.setText("");

    }

    public void onClickListener_One(View v) {
        strNum = number.getText().toString();

        if (!strNum.equals("0") && (strNum.length() < 9 || (strNum.length() < 10 && strNum.charAt(0) == '-')) && !isInit) {
            strNum = strNum + "1";
        } else if (strNum.equals("0") || isInit) {
            strNum = "1";
            isInit = false;
        }
        if(isResult) {
            operator = "";
            isResult = false;
        }
        number.setText(strNum);
        xTen.setText("");
        exponent.setText("");
    }

    public void onClickListener_Two(View v) {
        strNum = number.getText().toString();

        if (!strNum.equals("0") && (strNum.length() < 9 || (strNum.length() < 10 && strNum.charAt(0) == '-')) && !isInit) {
            strNum = strNum + "2";
        } else if (strNum.equals("0") || isInit) {
            strNum = "2";
            isInit = false;
        }
        if(isResult) {
            operator = "";
            isResult = false;
        }
        number.setText(strNum);
        xTen.setText("");
        exponent.setText("");
    }

    public void onClickListener_Three(View v) {
        strNum = number.getText().toString();

        if (!strNum.equals("0") && (strNum.length() < 9 || (strNum.length() < 10 && strNum.charAt(0) == '-')) && !isInit) {
            strNum = strNum + "3";
        } else if (strNum.equals("0") || isInit) {
            strNum = "3";
            isInit = false;
        }
        if(isResult) {
            operator = "";
            isResult = false;
        }
        number.setText(strNum);
        xTen.setText("");
        exponent.setText("");
    }

    public void onClickListener_Four(View v) {
        strNum = number.getText().toString();

        if (!strNum.equals("0") && (strNum.length() < 9 || (strNum.length() < 10 && strNum.charAt(0) == '-')) && !isInit) {
            strNum = strNum + "4";
        } else if (strNum.equals("0") || isInit) {
            strNum = "4";
            isInit = false;
        }
        if(isResult) {
            operator = "";
            isResult = false;
        }
        number.setText(strNum);
        xTen.setText("");
        exponent.setText("");
    }

    public void onClickListener_Five(View v) {
        strNum = number.getText().toString();

        if (!strNum.equals("0") && (strNum.length() < 9 || (strNum.length() < 10 && strNum.charAt(0) == '-')) && !isInit) {
            strNum = strNum + "5";
        } else if (strNum.equals("0") || isInit) {
            strNum = "5";
            isInit = false;
        }
        if(isResult) {
            operator = "";
            isResult = false;
        }
        number.setText(strNum);
        xTen.setText("");
        exponent.setText("");
    }

    public void onClickListener_Six(View v) {
        strNum = number.getText().toString();

        if (!strNum.equals("0") && (strNum.length() < 9 || (strNum.length() < 10 && strNum.charAt(0) == '-')) && !isInit) {
            strNum = strNum + "6";
        } else if (strNum.equals("0") || isInit) {
            strNum = "6";
            isInit = false;
        }
        if(isResult) {
            operator = "";
            isResult = false;
        }
        number.setText(strNum);
        xTen.setText("");
        exponent.setText("");
    }

    public void onClickListener_Seven(View v) {
        strNum = number.getText().toString();

        if (!strNum.equals("0") && (strNum.length() < 9 || (strNum.length() < 10 && strNum.charAt(0) == '-')) && !isInit) {
            strNum = strNum + "7";
        } else if (strNum.equals("0") || isInit) {
            strNum = "7";
            isInit = false;
        }
        if(isResult) {
            operator = "";
            isResult = false;
        }
        number.setText(strNum);
        xTen.setText("");
        exponent.setText("");
    }

    public void onClickListener_Eight(View v) {
        strNum = number.getText().toString();

        if (!strNum.equals("0") && (strNum.length() < 9 || (strNum.length() < 10 && strNum.charAt(0) == '-')) && !isInit) {
            strNum = strNum + "8";
        } else if (strNum.equals("0") || isInit) {
            strNum = "8";
            isInit = false;
        }
        if(isResult) {
            operator = "";
            isResult = false;
        }
        number.setText(strNum);
        xTen.setText("");
        exponent.setText("");
    }

    public void onClickListener_Nine(View v) {
        strNum = number.getText().toString();

        if (!strNum.equals("0") && (strNum.length() < 9 || (strNum.length() < 10 && strNum.charAt(0) == '-')) && !isInit) {
            strNum = strNum + "9";
        } else if (strNum.equals("0") || isInit) {
            strNum = "9";
            isInit = false;
        }
        if(isResult) {
            operator = "";
            isResult = false;
        }
        number.setText(strNum);
        xTen.setText("");
        exponent.setText("");
    }

    public void onClickListener_Comma(View v) {
        strNum = number.getText().toString();

        if (!strNum.contains(".") && (strNum.length() < 9 || (strNum.length() < 10 && strNum.charAt(0) == '-')) && !isInit) {
            strNum = strNum + ".";
        } else if (isInit) {
            strNum = "0.";
            isInit = false;
        }
        if(isResult) {
            operator = "";
            isResult = false;
        }
        number.setText(strNum);
        xTen.setText("");
        exponent.setText("");
    }

    /* ----------------- */


    /* Function listeners */

    public void onClickListener_Sin(View v) {

    }

    public void onClickListener_Cos(View v) {

    }

    public void onClickListener_Tan(View v) {

    }

    public void onClickListener_Ln(View v) {

    }

    public void onClickListener_Xn(View v) {

    }

    public void onClickListener_Square(View v) {

    }

    public void onClickListener_Root(View v) {

    }

    public void onClickListener_Exp(View v) {

    }

    public void onClickListener_Plus(View v) {

    }

    public void onClickListener_Minus(View v) {

    }

    public void onClickListener_Multiply(View v) {

    }

    public void onClickListener_Divide(View v) {
        if (!operator.equals("") && !isResult) {
            secondNum.setNumber(new BigDecimal(number.getText().toString()));                       // nastavit hodnotu druheho operandu na hodnotu z displeje
            Equal();
        } else if (isResult){
            secondNum.resetNum();
            isResult = false;
        }

        if (!firstNum.isInit()) {                                                                   // pokud neni prvni operand inicializovany
            firstNum.setNumber(new BigDecimal(number.getText().toString()));                        // nastavit hodnotu prvniho operandu na hodnotu z displeje
        }
        isResult = false;
        isInit = true;
        operator = "div";
    }

    public void onClickListener_Modulo(View v) {
        if (!operator.equals("") && !isResult) {
            secondNum.setNumber(new BigDecimal(number.getText().toString()));                       // nastavit hodnotu druheho operandu na hodnotu z displeje
            Equal();
        }

        if (!firstNum.isInit()) {                                                                   // pokud neni prvni operand inicializovany
            firstNum.setNumber(new BigDecimal(number.getText().toString()));                        // nastavit hodnotu prvniho operandu na hodnotu z displeje
        }

        secondNum.resetNum();
        isResult = false;
        isInit = true;
        operator = "mod";
    }

    public void onClickListener_Sign(View v) {

        if (isResult || (firstNum.isInit() && !secondNum.isInit() && operator.equals(""))) {
            Log.d("Kalkulacka", "A");
            firstNum.setNumber(firstNum.getValue().negate());               // hodnotu firstNum nasobit cislem -1
            Log.d("Kalkulacka", "firstNum: " + firstNum.getValue());
            if (firstNum.hasExp()) {                                                                // pokud je cislo upravovane a ma exponent, zobrazime jej na displeji
                strNum = firstNum.removeZeros(firstNum.getExpValue().toString());
                strExp = new BigDecimal(firstNum.getExp()).toString();
                exponent.setText(strExp);
                xTen.setText("x10");
            } else {
                strNum = firstNum.getValue().toString();
                exponent.setText("");
                xTen.setText("");
            }
        } else if (!firstNum.isInit()) {
            Log.d("Kalkulacka", "B");
            firstNum.setNumber(new BigDecimal(number.getText().toString()).negate());   // nastavit hodnotu firstNum na hodnotu z displeje násobenou číslem -1
            if (firstNum.hasExp()) {                                                                // pokud je cislo upravovane a ma exponent, zobrazime jej na displeji
                strNum = firstNum.removeZeros(firstNum.getExpValue().toString());
                strExp = new BigDecimal(firstNum.getExp()).toString();
                exponent.setText(strExp);
                xTen.setText("x10");
            } else {
                strNum = firstNum.getValue().toString();
                exponent.setText("");
                xTen.setText("");
            }
        } else if (firstNum.isInit() && !secondNum.isInit() && !operator.equals("")) {
            Log.d("Kalkulacka", "C");
            secondNum.setNumber(new BigDecimal(number.getText().toString()).negate());              // nastavit hodnotu secondNum na hodnotu z displeje násobenou číslem -1
            if (firstNum.hasExp()) {                                                                // pokud je cislo upravovane a ma exponent, zobrazime jej na displeji
                strNum = secondNum.removeZeros(secondNum.getExpValue().toString());
                strExp = new BigDecimal(secondNum.getExp()).toString();
                exponent.setText(strExp);
                xTen.setText("x10");
            } else {
                strNum = secondNum.getValue().toString();
                exponent.setText("");
                xTen.setText("");
            }
        } else if (firstNum.isInit() && secondNum.isInit() && !operator.equals("")) {
            Log.d("Kalkulacka", "D");
            secondNum.setNumber(secondNum.getValue().negate());                                     // hodnotu secondNum nasobit cislem -1
            if (secondNum.hasExp()) {                                                               // pokud je cislo upravovane a ma exponent, zobrazime jej na displeji
                strNum = secondNum.removeZeros(secondNum.getExpValue().toString());
                strExp = new BigDecimal(secondNum.getExp()).toString();
                exponent.setText(strExp);
                xTen.setText("x10");
            } else {
                strNum = secondNum.getValue().toString();
                exponent.setText("");
                xTen.setText("");
            }
        }

        number.setText(strNum);
    }


    /* ------------------ */

    /* Other Listeners */

    public void onClickListener_Equal(View v) {
        secondNum.setValue(new BigDecimal(number.getText().toString()));
        Equal();
    }

    public void onClickListener_Ac(View v) {
        reset();
    }

    public void reset(){
        number.setText("0");
        exponent.setText("");
        xTen.setText("");
        operator = "";
        error = isInit = isResult = false;
        firstNum.resetNum();
        secondNum.resetNum();
    }

    public void onClickListener_Del(View v) {
        strNum = number.getText().toString();

        if (!strNum.equals("0") && !isInit) {
            strNum = strNum.substring(0, strNum.length() - 1);
            if (strNum.length() >= 1 && strNum.charAt(strNum.length() - 1) == '.')
                strNum = strNum.substring(0, strNum.length() - 1);
            if (strNum.length() == 0 || strNum.equals("-"))
                strNum = "0";
            number.setText(strNum);
        }

        isInit = false;
    }

    /* --------------- */

    /* Other functions */

    public void Equal() {
        Log.d("Kalkulacka", "operator: " + operator);
        switch (operator) {
            case "mod":
                Log.d("Kalkulacka", "firstNum: " + firstNum.getValue());
                try {
                    firstNum.setNumber(mod(firstNum.getValue(), secondNum.getValue()));
                } catch (IllegalArgumentException e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                    builder.setMessage(R.string.error1);
                    builder.setTitle(R.string.error_title);
                    AlertDialog errorDialog = builder.create();
                    errorDialog.show();
                    error = true;
                    break;
                }
                Log.d("Kalkulacka", "secondNum: " + secondNum.getValue());
                Log.d("Kalkulacka", "result: " + firstNum.getValue());
                break;
            case "pow":
                BigDecimal pom = firstNum.getValue();
                int N = Integer.parseInt(number.getText().toString());
                for (int i = 1; i < N; i++) {
                    pom = pom.multiply(new BigDecimal(N));
                }
                firstNum.setNumber(pom);
                break;
            case "pow2":
                firstNum.setNumber(firstNum.getValue().multiply(firstNum.getValue()));
                break;
            case "sqrt":

                break;
            case "mul":

                break;
            case "div":
                try {
                    firstNum.setNumber(div(firstNum.getValue(), secondNum.getValue()));
                } catch (Exception e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                    builder.setMessage(R.string.error1);
                    builder.setTitle(R.string.error_title);
                    AlertDialog errorDialog = builder.create();
                    errorDialog.show();
                    error = true;
                    break;
                }
                break;
            default:

                break;
        }

        if (!error) {
            if (firstNum.hasExp()) {
                strNum = firstNum.removeZeros(firstNum.getExpValue().setScale(8, RoundingMode.FLOOR).toString());
                if(firstNum.getExp()>1000){
                    reset();
                    strNum = "INFINITY";
                }else{
                    strExp = Integer.toString(firstNum.getExp());
                    exponent.setText(strExp);
                    xTen.setText("x10");
                }
            } else {
                strNum = firstNum.removeZeros(firstNum.getValue().setScale(8, RoundingMode.FLOOR).toString());
            }
            isResult = isInit = true;
        } else {
            reset();
        }

        Log.d("Kalkulacka", "str: " + firstNum.getValue().toString());

        number.setText(strNum);
    }


    /* --------------- */
}