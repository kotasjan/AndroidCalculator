package com.jankotas.calculator;

import android.util.Log;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Kotik on 14.11.2016.
 */

public class Number {

    private BigDecimal value;       // neupravene cislo (realna hodnota cisla)
    private BigDecimal expValue;    // upravene cislo o zakladu nachazejicim se v expValue s exponentem exp
    private int exp;                // exponent upraveneho cisla
    private boolean hasExp;         // pokud je cislo upravene a ma exponent, je nastaveno na "true"
    private boolean isInit;         // pokud je cislo inicializovane, je nastaveno na "true"


    public Number() {
        this.value = new BigDecimal("0");
        this.expValue = new BigDecimal("0");
        this.exp = 0;
        this.hasExp = false;
        this.isInit = false;
    }

    public void resetNum() {
        this.value = new BigDecimal("0");
        this.expValue = new BigDecimal("0");
        this.exp = 0;
        this.hasExp = false;
        this.isInit = false;
    }


    public void setNumber(BigDecimal value) {
        this.value = this.expValue = value;
        this.isInit = true;

        String string = removeZeros(value.toString());

        if (string.length() > 10) {
            if (this.value.compareTo(new BigDecimal("1")) == 1) {
                for (int i = 1; (this.expValue = this.expValue.divide(new BigDecimal(10))).compareTo(new BigDecimal(1)) == 1; i++) {
                    this.exp = i;
                    this.hasExp = true;
                }
                this.expValue = this.expValue.multiply(BigDecimal.valueOf(10)).setScale(7, BigDecimal.ROUND_DOWN);
                Log.d("expValue = ", this.expValue.toString());
            } else if ((this.value.compareTo(new BigDecimal("1")) == -1) && (this.value.compareTo(new BigDecimal("0")) == 1)) {
                for (int i = -1; (this.expValue = this.expValue.multiply(new BigDecimal(10))).compareTo(new BigDecimal(1)) == -1; i--) {
                    this.exp = i;
                    this.hasExp = true;
                }
                this.expValue = this.expValue.divide(BigDecimal.valueOf(10)).setScale(7, BigDecimal.ROUND_DOWN);
                Log.d("expValue = ", this.expValue.toString());
            } else if (this.value.compareTo(new BigDecimal(-1)) == -1) {
                for (int i = 1; (this.expValue = this.expValue.divide(new BigDecimal(10))).compareTo(new BigDecimal(-1)) <= 0; i++) {
                    this.exp = i;
                    this.hasExp = true;
                }
                this.expValue = this.expValue.multiply(BigDecimal.valueOf(10)).setScale(7, BigDecimal.ROUND_DOWN);
                Log.d("expValue = ", this.expValue.toString());
            }
        } else {
            this.hasExp = false;
        }
    }

    public String removeZeros(String s){
        if (s.contains(".")) {
            for (int i = s.length() - 1; s.charAt(i) == '0' && s.charAt(i - 1) != '.' && i != 1; i--){
                s = s.substring(0, s.length() - 1);
            }
        }
        return s;
    }



    /* Gettery */

    public int getExp() {
        return exp;
    }

    public BigDecimal getValue() {
        return value;
    }

    public boolean hasExp() {
        return hasExp;
    }

    public boolean isInit() {
        return isInit;
    }

    public BigDecimal getExpValue() {
        return expValue;
    }

    /* ------- */

    /* Settery */

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setHasExp(boolean hasExp) {
        this.hasExp = hasExp;
    }

    public void setExpValue(BigDecimal expValue) {
        this.expValue = expValue;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public void setInit(boolean init) {
        isInit = init;
    }

    /* ------- */





}
