package com.xsp.texas;


import java.util.*;

import static com.xsp.texas.Main.*;

/**
 * Created by shpng on 2018/1/20.
 */
public class NewSimulate {

    public static void main(String[] args) {

        Main.recoverDataFromFile();
        //System.out.println(Main.sortedCombinations.subList(0, 5));
        // calculateMyOddsThreeTimes();

        /*
        int total = 10000;
        int count = 2;
        for(int i=count ;i>0;i--) {
            simulatePlay(i, total);
        }
        */

        for(int index=0;index< 100;index++) {
            Card c  = null;
            calculateMyOdds(c, c, c, c, c, c, c, 4);
        }

    }

    public static void simulatePlay(int count, int total) {


        TreeMap<SuitPattern, Integer> suitPatternCount = new TreeMap<>();
        suitPatternCount.put(SuitPattern.STRAIGHT_FLUSH, 0);
        suitPatternCount.put(SuitPattern.FOUR_OF_A_KIND, 0);
        suitPatternCount.put(SuitPattern.FULL_HOURSE, 0);
        suitPatternCount.put(SuitPattern.FLUSH, 0);
        suitPatternCount.put(SuitPattern.STRAIGHT, 0);
        suitPatternCount.put(SuitPattern.THREE_OF_A_KIND, 0);
        suitPatternCount.put(SuitPattern.TWO_PAIR, 0);
        suitPatternCount.put(SuitPattern.ONE_PAIR, 0);
        suitPatternCount.put(SuitPattern.HIGH_CARD, 0);

        for(int i=0;i< total;i++) {
            List<Card> playingCards = new ArrayList<Card>(cards);
            Card tableCard1 = getRandomCard(playingCards);
            Card tableCard2 = getRandomCard(playingCards);
            Card tableCard3 = getRandomCard(playingCards);
            Card tableCard4 = getRandomCard(playingCards);
            Card tableCard5 = getRandomCard(playingCards);
            Combination best = null;
            for(int j=0;j<count;j++) {
                Card playerCard1 = getRandomCard(playingCards);
                Card playerCard2 = getRandomCard(playingCards);
                Combination c = Main.getBestFiveCombination(tableCard1, tableCard2, tableCard3, tableCard4, tableCard5, playerCard1, playerCard2);
                if (best == null) {
                    best = c;
                }else{
                    if (best.compareTo(c) < 0) {
                        best = c;
                    }
                }
                //System.out.println(playerCard1 + " " + playerCard2
                // + " " + tableCard1 + " " + tableCard2 + " " + tableCard3 + " " + tableCard4 + " " + tableCard5);
            }

            //System.out.println(best);
            suitPatternCount.put(best.suitPattern, suitPatternCount.get(best.suitPattern) + 1);
        }
        System.out.println(count + "人局");
        for (SuitPattern suitPattern : suitPatternCount.keySet()) {
            System.out.println(suitPattern.getName() + " " + suitPatternCount.get(suitPattern) * 100.0 / total + "%");
        }
        System.out.println();


    }

    public static void calculateMyOdds() {
        // 黑桃 红心 草花 方片
        //Main.recover();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("输入：");
            String s = scanner.nextLine();

            String ss[] = s.split(" ");
            if (ss.length < 6) {
                continue;
            }

            int count = Integer.parseInt(ss[0]);
            Card myCard1 = cardMap.get(ss[1]);
            Card myCard2 = cardMap.get(ss[2]);
            Card tableCard1 = cardMap.get(ss[3]);
            Card tableCard2 = cardMap.get(ss[4]);
            Card tableCard3 = cardMap.get(ss[5]);
            Card tableCard4 = null;
            if (ss.length > 6) {
                tableCard4 = cardMap.get(ss[6]);
            }
            Card tableCard5 = null;
            if (ss.length > 7) {
                tableCard5 = cardMap.get(ss[7]);
            }

            if (myCard1 != null && myCard2 != null && tableCard1 != null && tableCard2 != null && tableCard3 != null) {
                calculateMyOdds(myCard1, myCard2, tableCard1, tableCard2, tableCard3, tableCard4, tableCard5, count);
            } else {
                System.out.println("输入错误");
            }

        }
    }

    public static void calculateMyOddsThreeTimes() {
        // 黑桃 红心 草花 方片
        //Main.recover();
        Scanner scanner = new Scanner(System.in);
        int stage = 1;
        String before = "";
        while (true) {
            if (stage == 4) {
                stage = 1;
                before = "";
            }

            System.out.println("阶段" + stage + "输入：");

            String s = scanner.nextLine();

            if (s.startsWith("q")) {
                stage = 4;
                continue;
            }

            before = "".equals(before) ? s : (before + " " + s.substring(2));
            String ss[] = before.split(" ");
            if (ss.length < 6) {
                continue;
            }

            int count = Integer.parseInt(s.substring(0, 1));
            Card myCard1 = cardMap.get(ss[1]);
            Card myCard2 = cardMap.get(ss[2]);
            Card tableCard1 = cardMap.get(ss[3]);
            Card tableCard2 = cardMap.get(ss[4]);
            Card tableCard3 = cardMap.get(ss[5]);
            Card tableCard4 = null;
            if (ss.length > 6) {
                tableCard4 = cardMap.get(ss[6]);
            }
            Card tableCard5 = null;
            if (ss.length > 7) {
                tableCard5 = cardMap.get(ss[7]);
            }

            if (myCard1 != null && myCard2 != null && tableCard1 != null && tableCard2 != null && tableCard3 != null) {
                calculateMyOdds(myCard1, myCard2, tableCard1, tableCard2, tableCard3, tableCard4, tableCard5, count);
                stage ++;
            } else {
                System.out.println("输入错误");
            }

        }
    }

    public static void calculateOdds() {

        // 黑桃 红心 草花 方片

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("输入：");
            String s = scanner.nextLine();

            String ss[] = s.split(" ");
            if (ss.length < 5) {
                continue;
            }

            Card myCard1 = cardMap.get(ss[0]);
            Card myCard2 = cardMap.get(ss[1]);
            Card tableCard1 = cardMap.get(ss[2]);
            Card tableCard2 = cardMap.get(ss[3]);
            Card tableCard3 = cardMap.get(ss[4]);
            Card tableCard4 = null;
            if (ss.length > 5) {
                tableCard4 = cardMap.get(ss[5]);
            }
            Card tableCard5 = null;
            if (ss.length > 6) {
                tableCard5 = cardMap.get(ss[6]);
            }

            if (myCard1 != null && myCard2 != null && tableCard1 != null && tableCard2 != null && tableCard3 != null) {
                calculateOddsWithMyCard(myCard1, myCard2, tableCard1, tableCard2, tableCard3, tableCard4, tableCard5);
            } else {
                System.out.println("输入错误");
            }

        }

    }


    /**
     * 根据三张公共牌、四张公共牌、五张公共牌计算可能的各种牌型的出现的概率
     *
     * @param tableCard1
     * @param tableCard2
     * @param tableCard3
     */
    public static void calculateOdds(Card tableCard1, Card tableCard2, Card tableCard3,
                                     Card tableCard4, Card tableCard5) {


        int tableCardCount = 3;
        if (tableCard4 != null) {
            tableCardCount = 4;
            if (tableCard5 != null) {
                tableCardCount = 5;
            }
        }


        List<Card> playingCards = new ArrayList<Card>(cards);
        playingCards.remove(tableCard1);
        playingCards.remove(tableCard2);
        playingCards.remove(tableCard3);
        if (tableCardCount == 4) {
            playingCards.remove(tableCard4);
        }
        if (tableCardCount == 5) {
            playingCards.remove(tableCard4);
            playingCards.remove(tableCard5);
        }

        TreeMap<SuitPattern, Integer> suitPatternCount = new TreeMap<>();
        suitPatternCount.put(SuitPattern.STRAIGHT_FLUSH, 0);
        suitPatternCount.put(SuitPattern.FOUR_OF_A_KIND, 0);
        suitPatternCount.put(SuitPattern.FULL_HOURSE, 0);
        suitPatternCount.put(SuitPattern.FLUSH, 0);
        suitPatternCount.put(SuitPattern.STRAIGHT, 0);
        suitPatternCount.put(SuitPattern.THREE_OF_A_KIND, 0);
        suitPatternCount.put(SuitPattern.TWO_PAIR, 0);
        suitPatternCount.put(SuitPattern.ONE_PAIR, 0);
        suitPatternCount.put(SuitPattern.HIGH_CARD, 0);


        // 模拟一万副牌可能出现各种牌型来得出概率
        for (int i = 0; i < 10000; i++) {

            List<Card> copyPlayingCards = new ArrayList<Card>(playingCards);
            if (tableCardCount == 4) {
                tableCard5 = getRandomCard(copyPlayingCards);
            }
            if (tableCardCount == 3) {
                tableCard4 = getRandomCard(copyPlayingCards);
                tableCard5 = getRandomCard(copyPlayingCards);
            }
            Card playerCard1 = getRandomCard(copyPlayingCards);
            Card playerCard2 = getRandomCard(copyPlayingCards);
            Combination combination = Main.getBestFiveCombination(playerCard1, playerCard2, tableCard1, tableCard2, tableCard3,
                    tableCard4, tableCard5);
            suitPatternCount.put(combination.suitPattern, suitPatternCount.get(combination.suitPattern) + 1);

        }
        System.out.println();
        for (SuitPattern suitPattern : suitPatternCount.keySet()) {
            System.out.println(suitPattern.getName() + " " + suitPatternCount.get(suitPattern)/100.0 + "%");
        }
        System.out.println();


    }

    /**
     * 根据三张公共牌、四张公共牌、五张公共牌计算可能的各种牌型的出现的概率
     *
     * @param tableCard1
     * @param tableCard2
     * @param tableCard3
     */
    public static void calculateOddsWithMyCard(Card myCard1, Card myCard2, Card tableCard1, Card tableCard2, Card tableCard3,
                                     Card tableCard4, Card tableCard5) {


        int tableCardCount = 3;
        if (tableCard4 != null) {
            tableCardCount = 4;
            if (tableCard5 != null) {
                tableCardCount = 5;
            }
        }

        System.out.println(String.format("我的底牌 %s, %s", myCard1.toString(), myCard2.toString()));
        System.out.println(String.format("公共牌 %s, %s, %s, %s, %s",
                tableCard1.toString(), tableCard2.toString(), tableCard3.toString(),
                (tableCard4 == null ? "" : tableCard4.toString()),
                (tableCard5 == null ? "" : tableCard5.toString())
        ));

        List<Card> playingCards = new ArrayList<Card>(cards);
        playingCards.remove(myCard1);
        playingCards.remove(myCard2);
        playingCards.remove(tableCard1);
        playingCards.remove(tableCard2);
        playingCards.remove(tableCard3);
        if (tableCardCount == 4) {
            playingCards.remove(tableCard4);
        }
        if (tableCardCount == 5) {
            playingCards.remove(tableCard4);
            playingCards.remove(tableCard5);
        }

        TreeMap<SuitPattern, Integer> suitPatternCount = new TreeMap<>();
        suitPatternCount.put(SuitPattern.STRAIGHT_FLUSH, 0);
        suitPatternCount.put(SuitPattern.FOUR_OF_A_KIND, 0);
        suitPatternCount.put(SuitPattern.FULL_HOURSE, 0);
        suitPatternCount.put(SuitPattern.FLUSH, 0);
        suitPatternCount.put(SuitPattern.STRAIGHT, 0);
        suitPatternCount.put(SuitPattern.THREE_OF_A_KIND, 0);
        suitPatternCount.put(SuitPattern.TWO_PAIR, 0);
        suitPatternCount.put(SuitPattern.ONE_PAIR, 0);
        suitPatternCount.put(SuitPattern.HIGH_CARD, 0);


        // 模拟一万副牌可能出现各种牌型来得出概率
        for (int i = 0; i < 10000; i++) {

            List<Card> copyPlayingCards = new ArrayList<Card>(playingCards);
            if (tableCardCount == 4) {
                tableCard5 = getRandomCard(copyPlayingCards);
            }
            if (tableCardCount == 3) {
                tableCard4 = getRandomCard(copyPlayingCards);
                tableCard5 = getRandomCard(copyPlayingCards);
            }

            Combination combination = Main.getBestFiveCombination(myCard1, myCard2, tableCard1, tableCard2, tableCard3,
                    tableCard4, tableCard5);
            suitPatternCount.put(combination.suitPattern, suitPatternCount.get(combination.suitPattern) + 1);
            if (tableCardCount == 5) {
                break;
            }
        }
        System.out.println("我的牌可能出现的牌型及概率");
        for (SuitPattern suitPattern : suitPatternCount.keySet()) {
            if (suitPatternCount.get(suitPattern) > 0) {
                System.out.print(suitPattern.getName() + " " + suitPatternCount.get(suitPattern) /
                        (tableCardCount == 5 ? 0.01 : 100.0) + "%, ");
            }
        }
        System.out.println();

        suitPatternCount = new TreeMap<>();
        suitPatternCount.put(SuitPattern.STRAIGHT_FLUSH, 0);
        suitPatternCount.put(SuitPattern.FOUR_OF_A_KIND, 0);
        suitPatternCount.put(SuitPattern.FULL_HOURSE, 0);
        suitPatternCount.put(SuitPattern.FLUSH, 0);
        suitPatternCount.put(SuitPattern.STRAIGHT, 0);
        suitPatternCount.put(SuitPattern.THREE_OF_A_KIND, 0);
        suitPatternCount.put(SuitPattern.TWO_PAIR, 0);
        suitPatternCount.put(SuitPattern.ONE_PAIR, 0);
        suitPatternCount.put(SuitPattern.HIGH_CARD, 0);

        // 模拟一万副牌可能出现各种牌型来得出概率
        for (int i = 0; i < 10000; i++) {

            List<Card> copyPlayingCards = new ArrayList<Card>(playingCards);
            if (tableCardCount == 4) {
                tableCard5 = getRandomCard(copyPlayingCards);
            }
            if (tableCardCount == 3) {
                tableCard4 = getRandomCard(copyPlayingCards);
                tableCard5 = getRandomCard(copyPlayingCards);
            }
            Card playerCard1 = getRandomCard(copyPlayingCards);
            Card playerCard2 = getRandomCard(copyPlayingCards);
            Combination combination = Main.getBestFiveCombination(playerCard1, playerCard2, tableCard1, tableCard2, tableCard3,
                    tableCard4, tableCard5);
            suitPatternCount.put(combination.suitPattern, suitPatternCount.get(combination.suitPattern) + 1);

        }
        System.out.println("别人的牌可能出现的牌型及概率");
        for (SuitPattern suitPattern : suitPatternCount.keySet()) {
            if(suitPatternCount.get(suitPattern) > 0){
                System.out.print(suitPattern.getName() + " " + suitPatternCount.get(suitPattern) / 100.0 + "%, " );
            }

        }
        System.out.println();


    }

    public static void calculateMyOdds(Card myCard1, Card myCard2, Card tableCard1, Card tableCard2, Card tableCard3, Card tableCard4,
                                       Card tableCard5, int otherPlayerCount) {

        List<Card> playingCards = new ArrayList<Card>(cards);
        if (myCard1 == null) {
            myCard1 = getRandomCard(playingCards);
        }

        if (myCard2 == null) {
            myCard2 = getRandomCard(playingCards);
        }

        if (tableCard1 == null) {
            tableCard1 = getRandomCard(playingCards);
        }

        if (tableCard2 == null) {
            tableCard2 = getRandomCard(playingCards);
        }

        if (tableCard3 == null) {
            tableCard3 = getRandomCard(playingCards);
        }

        int tableCardCount = 3;
        if (tableCard4 != null) {
            tableCardCount = 4;
            if (tableCard5 != null) {
                tableCardCount = 5;
            }
        }

        System.out.println(String.format("我的底牌 %s, %s", myCard1.toString(), myCard2.toString()));
        System.out.println(String.format("公共牌 %s, %s, %s, %s, %s",
                tableCard1.toString(), tableCard2.toString(), tableCard3.toString(),
                (tableCard4 == null ? "" : tableCard4.toString()),
                (tableCard5 == null ? "" : tableCard5.toString())
        ));

        playingCards.remove(myCard1);
        playingCards.remove(myCard2);
        playingCards.remove(tableCard1);
        playingCards.remove(tableCard2);
        playingCards.remove(tableCard3);
        if (tableCardCount == 4) {
            playingCards.remove(tableCard4);
        }
        if (tableCardCount == 5) {
            playingCards.remove(tableCard4);
            playingCards.remove(tableCard5);
        }
        int number = 10000;
        int winCount = 0;
        int plainCount = 0;
        int lostCount = 0;
        for(int index = 0; index < number; index ++) {


            List<Card> copyPlayingCards = new ArrayList<Card>(playingCards);
            if (tableCardCount == 4) {
                tableCard5 = getRandomCard(copyPlayingCards);
            }
            if (tableCardCount == 3) {
                tableCard4 = getRandomCard(copyPlayingCards);
                tableCard5 = getRandomCard(copyPlayingCards);
            }
            Combination myCombination = Main.getBestFiveCombination(tableCard1, tableCard2, tableCard3, tableCard4, tableCard5, myCard1, myCard2);
            //System.out.println("我的 " + myCombination);
            Combination otherPlayerBest = null;
            for(int playerIndex=0; playerIndex < otherPlayerCount; playerIndex ++) {
                Card playerCard1 = getRandomCard(copyPlayingCards);
                Card playerCard2 = getRandomCard(copyPlayingCards);
                Combination otherPlayerCombination = getBestFiveCombination(tableCard1, tableCard2, tableCard3, tableCard4, tableCard5, playerCard1, playerCard2);
                if (otherPlayerBest == null) {
                    otherPlayerBest = otherPlayerCombination;
                }else {
                    //if (Main.positionIndexMap.get(otherPlayerBest.position) < Main.positionIndexMap.get(otherPlayerCombination.position)) {
                    //    otherPlayerBest = otherPlayerCombination;
                    //}
                    if (otherPlayerBest.compareTo(otherPlayerCombination) < 0) {
                        otherPlayerBest = otherPlayerCombination;
                    }
                }
            }
            //System.out.println("其他玩家 " + otherPlayerBest);
            int myCombinationCompare = myCombination.compareTo(otherPlayerBest);
            if (myCombinationCompare > 0) {
                winCount ++;
            } else if (myCombinationCompare == 0) {
                plainCount ++;
            }else{
                lostCount ++;
            }

        }
        System.out.println(String.format((otherPlayerCount + 1) + "个人 %.2f %.2f %.2f", winCount * 1.0 / (number),
                lostCount * 1.0 / (number),
                plainCount * 1.0 / (number)
        ));
        double exp = winCount * 1.0 / (lostCount + 1);
        System.out.println(String.format("exp =  %.2f", exp));
        System.out.println("-------------------");
    }

}