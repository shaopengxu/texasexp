package com.xsp.texas;

import java.math.BigDecimal;

/**
 * Created by shpng on 2018/2/21.
 */
public class probabilityTools {


    public static final BigDecimal zero = BigDecimal.ZERO;
    // 1/3
    public static final BigDecimal threeOfOne = new BigDecimal("0.33333");
    // 1/2
    public static final BigDecimal half = new BigDecimal("0.5");
    // 2/3
    public static final BigDecimal threeOfTwo = new BigDecimal("0.6666666");
    // 1
    public static final BigDecimal one = BigDecimal.ONE;
    // 3/2
    public static final BigDecimal oneAndHalf = new BigDecimal("1.5");
    // 2
    public static final BigDecimal two = new BigDecimal("2");
    // 3
    public static final BigDecimal three = new BigDecimal("3");



    /**
     *
     * 根据胜率计算出下注的底池倍数
     * @param winodd
     * @param base
     *
     */
    public static BigDecimal calculateBetTimes(double winodd, double loseOdd, double base, int count) {


        if (loseOdd <= 0.1) {
            loseOdd = 0.1;
        }
        // winodd * 1 - loseodd * maxPut >= base -> maxPut <= (winodd-base)/loseodd
        BigDecimal maxPut = new BigDecimal(winodd).subtract(new BigDecimal(base))
                .divide(new BigDecimal(loseOdd), 5, BigDecimal.ROUND_HALF_UP);
        System.out.println("maxput: "+maxPut);
        BigDecimal middle = maxPut.divide(new BigDecimal("2"));
        double normalResult = normalProbability(count);
        middle = middle.add(new BigDecimal(normalResult - 0.4));

        if (middle.compareTo(three) >= 0) {
            return three;
        }
        if (middle.compareTo(two) >= 0) {
            return two;
        }
        if (middle.compareTo(oneAndHalf) >= 0) {
            return oneAndHalf;
        }
        if (middle.compareTo(one) >= 0) {
            return one;
        }
        if (middle.compareTo(threeOfTwo) >= 0) {
            return threeOfTwo;
        }
        if (middle.compareTo(half) >= 0) {
            return half;
        }
        if (middle.compareTo(threeOfOne) >= 0) {
            return threeOfOne;
        }
        return zero;
    }

    public static double chooseBase(double winodd) {

        if (winodd > 0.7) {
            return Math.random();
        }
        if (winodd < 0.2) {
            return Math.random() / 4 - 0.1;
        }
        return Math.random() / 2 - 0.15;

    }

    public static int chooseCount(double winodd) {

        if (winodd < 0.4) {

        }
        return 0;
    }

    public static void calculateBetTimes(){

    }

    public static void main(String[] args) {
        double winodd = 0.3;
        for (int i = 0; i < 100; i++) {
            System.out.println(calculateBetTimes(winodd, 1 - winodd, chooseBase(winodd), (int) (Math.random() * 5) + 1));
            //calculateBetTimes(0.3,0.7, 0);
        }

    }

    public static double normalProbability(int count) {
        double value = 0;

        for(int index=0;index<count;index++) {
            value += Math.random();
        }
        value = value / count;
        return value;
    }
}
