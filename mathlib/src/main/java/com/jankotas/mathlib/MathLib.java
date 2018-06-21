package com.jankotas.mathlib;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathLib {

    public static BigDecimal mod(BigDecimal a, BigDecimal b){
        if(b.signum() == -1)
            return (new BigDecimal(a.toBigInteger().mod(b.negate().toBigInteger()))).negate();
        else if (b.signum() == 0)
            throw new IllegalArgumentException("Argument 'divisor' is 0");
        else if(a.signum() == -1)
            return (new BigDecimal(a.toBigInteger().negate().mod(b.toBigInteger()))).negate();
        else
            return (new BigDecimal(a.toBigInteger().mod(b.toBigInteger())));
    }

    public static BigDecimal add(BigDecimal a, BigDecimal b){
        return a.add(b);
    }

    public static BigDecimal sub(BigDecimal a, BigDecimal b){
        return a.add(b.negate());
    }

    public static BigDecimal mul(BigDecimal a, BigDecimal b){
        return a.multiply(b);
    }

    public static BigDecimal div(BigDecimal a, BigDecimal b){
        return a.divide(b, 1000, RoundingMode.HALF_UP);
    }

    public static BigDecimal pow(BigDecimal x, BigDecimal y)
    {
        return BigDecimalMath.pow(x, y);
    }

    public static BigDecimal sqrt(BigDecimal x)
    {
        return BigDecimalMath.sqrt(x);
    }

    public static BigDecimal sin(BigDecimal x)
    {
        return BigDecimalMath.sin(x);
    }

    public static BigDecimal cos(BigDecimal x)
    {
        return BigDecimalMath.cos(x);
    }

    public static BigDecimal tan(BigDecimal x)
    {
        return BigDecimalMath.tan(x);
    }

    public static BigDecimal ln(BigDecimal x)
    {
        return BigDecimalMath.log(x);
    }

    public static BigDecimal exp(BigDecimal x)
    {
        return BigDecimalMath.exp(x);
    }

}
