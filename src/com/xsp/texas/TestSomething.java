package com.xsp.texas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by shpng on 2018/1/20.
 */
public class TestSomething {

    public static void test() {
        long start = System.currentTimeMillis();
        int[] counts = new int[SuitPattern.values().length];
        int count = 0;
        List<Combination> straight = new ArrayList<Combination>();
        for (; ; ) {
            Combination c = Main.generateCombination();

            switch (c.suitPattern) {
                case STRAIGHT_FLUSH:
                    counts[0]++;
                    break;
                case FOUR_OF_A_KIND:
                    counts[1]++;
                    break;
                case FULL_HOURSE:
                    counts[2]++;
                    break;
                case FLUSH:
                    counts[3]++;
                    break;
                case STRAIGHT:
                    counts[4]++;
                    break;
                case THREE_OF_A_KIND:
                    counts[5]++;
                    break;
                case TWO_PAIR:
                    counts[6]++;
                    break;
                case ONE_PAIR:
                    counts[7]++;
                    break;
                case HIGH_CARD:
                    counts[8]++;
                    break;
            }
//            if (suitPattern != SuitPattern.ONE_PAIR && suitPattern != SuitPattern.HIGH_CARD) {
//                System.out.println(c);
//            }
//            if (suitPattern == SuitPattern.STRAIGHT || suitPattern == SuitPattern.STRAIGHT_FLUSH) {
//                //System.out.println(c);
//                straight.add(c);
//            }
            straight.add(c);
            Main.recover();

            if (count++ > 1000000) {
                break;
            }
        }
//        Collections.sort(straight);
//        for (Combination c : straight) {
//            System.out.println(c);
//        }

        System.out.println(Arrays.toString(counts));
        System.out.println(System.currentTimeMillis() - start);
    }
}
