package com.jankotas.calculator;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import static com.jankotas.mathlib.MathLib.mod;
import static com.jankotas.mathlib.MathLib.div;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mToggle;

    private AdView mAdView;

    private TextView result_text, x10;

    private EditText equation_text;

    /*
        Tlacitka kalkulacky
     */

    private Button btn_dots, btn_sin, btn_cos, btn_tan, btn_abs, btn_pi, btn_xn, btn_x2, btn_sqrt, btn_brackets, btn_seven, btn_eight, btn_nine, btn_clear, btn_mul, btn_four, btn_five, btn_six, btn_div, btn_one, btn_two, btn_three, btn_plus, btn_minus, btn_zero, btn_sign, btn_comma, btn_mod, btn_equal;

    private ImageButton btn_backspace;

    /*
        vibrator - vibrace pri zmacknuti tlacitka
     */

    private Vibrator vibrator;

    /*
        Fonty pro zobrazovani digitalnich cislic
     */

    private Typeface digital_font, digital_font_monospaced, noto_font_light;

    /*
        strNum - retezec slouzici k nastavovani hlavniho ciselniku kalkulacky
        strExp - retezec slouzici k nastavovani exponentu
        operator - slouzi k vyhodnocovani prepinace pro volbu matematicke operace
     */

    private String strNum, strExp, operator = "";

    /*
        firstNum - slouzi k vyjadreni prvniho operandu
        secondNum - slouzi k vyjadreni druheho operandu
     */

    private Number firstNum, secondNum;

    /*
        error - pokud nastane pri operaci chyba, nastavi se na "true"
        isResult - pokud je na displeji zobrazovan vysledek, je nastaveno na "true"
     */

    private Boolean error = false, isInit = false, isResult = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.calculator);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        MobileAds.initialize(this, "ca-app-pub-3879739495024729~5243751307");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        equation_text = findViewById(R.id.equation_text);
        result_text = findViewById(R.id.result_text);
        x10 = findViewById(R.id.x10_text);

        btn_dots = findViewById(R.id.btn_dots);
        btn_sin = findViewById(R.id.btn_sin);
        btn_cos = findViewById(R.id.btn_cos);
        btn_tan = findViewById(R.id.btn_tan);
        btn_abs = findViewById(R.id.btn_abs);

        btn_pi = findViewById(R.id.btn_pi);
        btn_xn = findViewById(R.id.btn_xn);
        btn_x2 = findViewById(R.id.btn_x2);
        btn_sqrt = findViewById(R.id.btn_sqrt);
        btn_brackets = findViewById(R.id.btn_brackets);

        btn_seven = findViewById(R.id.btn_seven);
        btn_eight = findViewById(R.id.btn_eight);
        btn_nine = findViewById(R.id.btn_nine);
        btn_clear = findViewById(R.id.btn_clear);

        btn_four = findViewById(R.id.btn_four);
        btn_five = findViewById(R.id.btn_five);
        btn_six = findViewById(R.id.btn_six);
        btn_mul = findViewById(R.id.btn_mul);
        btn_div = findViewById(R.id.btn_div);

        btn_one = findViewById(R.id.btn_one);
        btn_two = findViewById(R.id.btn_two);
        btn_three = findViewById(R.id.btn_three);
        btn_plus = findViewById(R.id.btn_plus);
        btn_minus = findViewById(R.id.btn_minus);

        btn_zero = findViewById(R.id.btn_zero);
        btn_sign = findViewById(R.id.btn_sign);
        btn_comma = findViewById(R.id.btn_comma);
        btn_mod = findViewById(R.id.btn_mod);
        btn_equal = findViewById(R.id.btn_equal);

        digital_font = Typeface.createFromAsset(getAssets(), "fonts/digital-7.regular.ttf");
        digital_font_monospaced = Typeface.createFromAsset(getAssets(), "fonts/digital-7.mono.ttf");
        noto_font_light = Typeface.createFromAsset(getAssets(), "fonts/NotoSansDisplay-Light.ttf");

        equation_text.setTypeface(digital_font);
        result_text.setTypeface(digital_font_monospaced);
        x10.setTypeface(digital_font);

        btn_dots.setTypeface(noto_font_light);
        btn_sin.setTypeface(noto_font_light);
        btn_cos.setTypeface(noto_font_light);
        btn_tan.setTypeface(noto_font_light);
        btn_abs.setTypeface(noto_font_light);

        btn_pi.setTypeface(noto_font_light);
        btn_xn.setTypeface(noto_font_light);
        btn_x2.setTypeface(noto_font_light);
        btn_sqrt.setTypeface(noto_font_light);
        btn_brackets.setTypeface(noto_font_light);

        btn_seven.setTypeface(noto_font_light);
        btn_eight.setTypeface(noto_font_light);
        btn_nine.setTypeface(noto_font_light);
        btn_clear.setTypeface(noto_font_light);

        btn_four.setTypeface(noto_font_light);
        btn_five.setTypeface(noto_font_light);
        btn_six.setTypeface(noto_font_light);
        btn_mul.setTypeface(noto_font_light);
        btn_div.setTypeface(noto_font_light);

        btn_one.setTypeface(noto_font_light);
        btn_two.setTypeface(noto_font_light);
        btn_three.setTypeface(noto_font_light);
        btn_plus.setTypeface(noto_font_light);
        btn_minus.setTypeface(noto_font_light);

        btn_zero.setTypeface(noto_font_light);
        btn_sign.setTypeface(noto_font_light);
        btn_comma.setTypeface(noto_font_light);
        btn_mod.setTypeface(noto_font_light);
        btn_equal.setTypeface(noto_font_light);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        firstNum = new Number();
        secondNum = new Number();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home) {
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.settings) {
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void onClickListener_history(View v){
        //TODO vyskakovaci okno se seznamem vysledku
    }

    /* Numbers listeners */

    public void onClickListener_Zero(View v) {
        strNum = result_text.getText().toString();

        if (!strNum.equals("0") && (strNum.length() < 9 || (strNum.length() < 10 && strNum.charAt(0) == '-')) && !isInit) {
            strNum = strNum + "0";
            result_text.setText(strNum);
        } else if (strNum.equals("0") || isInit) {
            strNum = "0";
            isInit = false;
            result_text.setText(strNum);
        }
        if (isResult) {
            operator = "";
            isResult = false;
        }
        x10.setText("");

    }

    public void onClickListener_One(View v) {
        strNum = result_text.getText().toString();

        if (!strNum.equals("0") && (strNum.length() < 9 || (strNum.length() < 10 && strNum.charAt(0) == '-')) && !isInit) {
            strNum = strNum + "1";
        } else if (strNum.equals("0") || isInit) {
            strNum = "1";
            isInit = false;
        }
        if (isResult) {
            operator = "";
            isResult = false;
        }
        result_text.setText(strNum);
        x10.setText("");
    }

    public void onClickListener_Two(View v) {
        strNum = result_text.getText().toString();

        if (!strNum.equals("0") && (strNum.length() < 9 || (strNum.length() < 10 && strNum.charAt(0) == '-')) && !isInit) {
            strNum = strNum + "2";
        } else if (strNum.equals("0") || isInit) {
            strNum = "2";
            isInit = false;
        }
        if (isResult) {
            operator = "";
            isResult = false;
        }
        result_text.setText(strNum);
        x10.setText("");
    }

    public void onClickListener_Three(View v) {
        strNum = result_text.getText().toString();

        if (!strNum.equals("0") && (strNum.length() < 9 || (strNum.length() < 10 && strNum.charAt(0) == '-')) && !isInit) {
            strNum = strNum + "3";
        } else if (strNum.equals("0") || isInit) {
            strNum = "3";
            isInit = false;
        }
        if (isResult) {
            operator = "";
            isResult = false;
        }
        result_text.setText(strNum);
        x10.setText("");
    }

    public void onClickListener_Four(View v) {
        strNum = result_text.getText().toString();

        if (!strNum.equals("0") && (strNum.length() < 9 || (strNum.length() < 10 && strNum.charAt(0) == '-')) && !isInit) {
            strNum = strNum + "4";
        } else if (strNum.equals("0") || isInit) {
            strNum = "4";
            isInit = false;
        }
        if (isResult) {
            operator = "";
            isResult = false;
        }
        result_text.setText(strNum);
        x10.setText("");
    }

    public void onClickListener_Five(View v) {
        strNum = result_text.getText().toString();

        if (!strNum.equals("0") && (strNum.length() < 9 || (strNum.length() < 10 && strNum.charAt(0) == '-')) && !isInit) {
            strNum = strNum + "5";
        } else if (strNum.equals("0") || isInit) {
            strNum = "5";
            isInit = false;
        }
        if (isResult) {
            operator = "";
            isResult = false;
        }
        result_text.setText(strNum);
        x10.setText("");
    }

    public void onClickListener_Six(View v) {
        strNum = result_text.getText().toString();

        if (!strNum.equals("0") && (strNum.length() < 9 || (strNum.length() < 10 && strNum.charAt(0) == '-')) && !isInit) {
            strNum = strNum + "6";
        } else if (strNum.equals("0") || isInit) {
            strNum = "6";
            isInit = false;
        }
        if (isResult) {
            operator = "";
            isResult = false;
        }
        result_text.setText(strNum);
        x10.setText("");
    }

    public void onClickListener_Seven(View v) {
        strNum = result_text.getText().toString();

        if (!strNum.equals("0") && (strNum.length() < 9 || (strNum.length() < 10 && strNum.charAt(0) == '-')) && !isInit) {
            strNum = strNum + "7";
        } else if (strNum.equals("0") || isInit) {
            strNum = "7";
            isInit = false;
        }
        if (isResult) {
            operator = "";
            isResult = false;
        }
        result_text.setText(strNum);
        x10.setText("");
    }

    public void onClickListener_Eight(View v) {
        strNum = result_text.getText().toString();

        if (!strNum.equals("0") && (strNum.length() < 9 || (strNum.length() < 10 && strNum.charAt(0) == '-')) && !isInit) {
            strNum = strNum + "8";
        } else if (strNum.equals("0") || isInit) {
            strNum = "8";
            isInit = false;
        }
        if (isResult) {
            operator = "";
            isResult = false;
        }
        result_text.setText(strNum);
        x10.setText("");
    }

    public void onClickListener_Nine(View v) {
        strNum = result_text.getText().toString();

        if (!strNum.equals("0") && (strNum.length() < 9 || (strNum.length() < 10 && strNum.charAt(0) == '-')) && !isInit) {
            strNum = strNum + "9";
        } else if (strNum.equals("0") || isInit) {
            strNum = "9";
            isInit = false;
        }
        if (isResult) {
            operator = "";
            isResult = false;
        }
        result_text.setText(strNum);
        x10.setText("");
    }

    public void onClickListener_Comma(View v) {
        strNum = result_text.getText().toString();

        if (!strNum.contains(".") && (strNum.length() < 9 || (strNum.length() < 10 && strNum.charAt(0) == '-')) && !isInit) {
            strNum = strNum + ".";
        } else if (isInit) {
            strNum = "0.";
            isInit = false;
        }
        if (isResult) {
            operator = "";
            isResult = false;
        }
        result_text.setText(strNum);
        x10.setText("");
    }

    /* ----------------- */


    /* Function listeners */

    public void onClickListener_Dots(View view) {
    }

    public void onClickListener_Sin(View v) {

    }

    public void onClickListener_Cos(View v) {

    }

    public void onClickListener_Tan(View v) {

    }

    public void onClickListener_Abs(View v) {

    }

    public void onClickListener_Pi(View v) {

    }

    public void onClickListener_Xn(View v) {

    }

    public void onClickListener_X2(View v) {

    }

    public void onClickListener_Sqrt(View v) {

    }

    public void onClickListener_Brackets(View v) {

    }

    public void onClickListener_Clear(View v) {

    }

    public void onClickListener_Backspace(View v) {

    }

    public void onClickListener_Multiply(View v) {

    }

    public void onClickListener_Divide(View v) {

    }

    public void onClickListener_Plus(View v) {

    }

    public void onClickListener_Minus(View v) {

    }

    public void onClickListener_Sign(View v) {

    }

    public void onClickListener_Mod(View v) {

    }


    /* ------------------ */

    /* Other Listeners */

    public void onClickListener_Equal(View v) {

    }



    /* --------------- */

    /* Other functions */


    /* --------------- */
}