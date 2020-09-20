package com.xsp.texas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static com.xsp.texas.Main.*;

/**
 * Created by shpng on 2018/1/20.
 */
public class SimulatePlay {


    /**
     * 模拟打牌
     */
    public static void simulatePlayCard() {

        // copy一副牌
        List<Card> playingCards = new ArrayList<Card>(cards);

        // 初始化6个玩家
        Player players[] = new Player[6];
        // 发牌
        for (int index = 0; index < players.length; index++) {
            players[index] = new Player();
            players[index].cards[0] = getRandomCard(playingCards);
        }
        //发牌
        for (Player player : players) {
            player.cards[1] = getRandomCard(playingCards);
        }

        Card[] table = new Card[5];
        for (int index = 0; index < table.length; index++) {
            table[index] = getRandomCard(playingCards);
        }

        System.out.println("公共牌 " + Arrays.toString(table));
        Player winner = null;
        for (Player player : players) {
            //从七张牌中取得最高的牌
            long thisPositon = 0l | 1l << player.cards[0].index | 1l << player.cards[1].index
                    | 1l << table[0].index | 1l << table[1].index | 1l << table[2].index | 1l << table[3].index | 1l << table[4].index;
            for (int index = sortedCombinations.size() - 1; index >= 0; index--) {
                if ((sortedCombinations.get(index).position & thisPositon) == sortedCombinations.get(index).position) {
                    player.bestCombination = sortedCombinations.get(index);
                    System.out.println("玩家" + index + ", 手牌:" + Arrays.toString(player.cards) + ", 最大牌型： " + player.bestCombination);
                    break;
                }
            }
        }

        System.out.println();
        System.out.println();
        System.out.println();

    }


    /**
     * 底牌AA赢的概率
     */
    public static void AAWinOddsAnotherWay() {
        String a1 = "黑桃K";
        String a2 = "红心A";
        Card a1Card = cardMap.get(a1);
        Card a2Card = cardMap.get(a2);
        long l = 0l | (1l << a1Card.index) | (1l << a2Card.index);

        long winCount = 0l;
        long loseCount = 0l;


        int count = 0;
        for (long position : combinationMap.keySet()) {
            if ((position & l) == l) {
                count++;
                Combination aaCombination = combinationMap.get(position);
                // list前面的牌都比后面的牌大或相等
                int aaIndex = sortedCombinations.indexOf(aaCombination);
                long wcount = aaIndex;

                winCount += wcount;
                loseCount += (sortedCombinations.size() - wcount);
                System.out.println(aaCombination + " " + wcount + " " + (sortedCombinations.size() - wcount) + " " + String.format("%.2f", wcount * 1.0 / sortedCombinations.size() * 100));

            }
        }
        System.out.println(String.format(" %d %.2f", count, winCount * 1.0 / (winCount + loseCount) * 100));
    }

    /**
     * preflop，底牌AA，赢的概率
     */
    public static void AAWinOdds() {
        String a1 = "黑桃10";
        String a2 = "红心10";
        Card a1Card = cardMap.get(a1);
        Card a2Card = cardMap.get(a2);
        long l = 0l | (1l << a1Card.index) | (1l << a2Card.index);

        long winCount = 0l;
        long loseCount = 0l;


        for (long position : combinationMap.keySet()) {
            if ((position & l) == l) {
                Combination aaCombination = combinationMap.get(position);
                long thisCombinationL = aaCombination.position;
                int aaIndex = sortedCombinations.indexOf(aaCombination);
                int positionsWithoutThisCombinationSize = 0;
                long wcount = 0;
                // list前面的牌都比后面的牌大或相等
                for (int index = 0; index < positions.size(); index++) {

                    if ((positions.get(index) & thisCombinationL) == 0) {
                        positionsWithoutThisCombinationSize++;
                        if (aaIndex > index) {
                            wcount++;
                        }
                    }
                }
                winCount += wcount;
                loseCount += (positionsWithoutThisCombinationSize - wcount);
                System.out.println(aaCombination + " " + wcount + " "
                        + (positionsWithoutThisCombinationSize - wcount) + " " + String.format("%.2f", wcount * 1.0 / positionsWithoutThisCombinationSize * 100));

            }
        }
        System.out.println(String.format("%.2f", winCount * 1.0 / (winCount + loseCount) * 100));

    }

    /**
     * 输入五张牌，两张底牌，三种公共牌，计算胜率
     */
    public static void afterFlopOddsWithInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("输入：");
            String s = scanner.nextLine();
            String ss[] = s.split(" ");
            if (ss.length != 5) {
                continue;
            }
            Card c1 = cardMap.get(ss[0]);
            Card c2 = cardMap.get(ss[1]);
            Card c3 = cardMap.get(ss[2]);
            Card c4 = cardMap.get(ss[3]);
            Card c5 = cardMap.get(ss[4]);
            if (c1 == null || c2 == null || c3 == null || c4 == null || c5 == null) {
                continue;
            }

            Combination combination = combinationMap.get(0l | 1l << c1.index | 1l << c2.index | 1l << c3.index | 1l << c4.index | 1l << c5.index);
            System.out.println(combination);

            afterFlopOdds(ss[0], ss[1], ss[2], ss[3], ss[4]);
            //草花2 方片4 草花5 方片8 方片7
        }

    }

    /**
     * 两人局的情况，给出自己的底牌和公共牌，计算胜率
     *
     * @param s1
     * @param s2
     * @param s3
     * @param s4
     * @param s5
     */
    public static void afterFlopOdds(String s1, String s2, String s3, String s4, String s5) {

        Card playerCard1 = cardMap.get(s1);
        Card playerCard2 = cardMap.get(s2);
        Card tableCard1 = cardMap.get(s3);
        Card tableCard2 = cardMap.get(s4);
        Card tableCard3 = cardMap.get(s5);

        //从剩下的47张随机选两张作为turn牌和river牌
        //从7张牌找出自己最大的牌，然后在剩下的45张牌中随机找出两张牌，然后和桌上的5张凑一个最大的牌，和自己最大的牌比较，得出概率
        long l = (1l << 52) - 1;
        l = l & (~(1l << playerCard1.index)) & (~(1l << playerCard2.index)) & (~(1l << tableCard1.index)) & (~(1l << tableCard2.index)) & (~(1l << tableCard3.index));

        Long[] turnRivers = SetOperator.getSetCombinations(l, 2);
        long winCount = 0;
        long loseCount = 0;
        long start = System.currentTimeMillis();
        for (int index = 0; index < turnRivers.length; index++) {


//            Card turnCard = null;
//            Card riverCard = null;
//
//            for (int ind = 0; ind < cards.size(); ind++) {
//                if((turnRivers[index] & (1l<< ind) ) > 0) {
//                    if (turnCard == null) {
//                        turnCard = cards.get(ind);
//                    }else {
//                        riverCard = cards.get(ind);
//                    }
//                }
//            }


            long wholeCards = turnRivers[index] | (1l << playerCard1.index) | (1l << playerCard2.index) | (1l << tableCard1.index) | (1l << tableCard2.index) | (1l << tableCard3.index);
            int bestPositionIndex = getBestFiveCombination(wholeCards);
            long ll = l;
            ll = ll & (~(turnRivers[index]));
//            System.out.println(System.currentTimeMillis());

            // 生成对手底牌
            Long[] otherCards = SetOperator.getSetCombinations(ll, 2);
            int wCount = 0;
            int lCount = 0;
            for (Long otherCard : otherCards) {
                long otherWholeCards = otherCard | (1l << tableCard1.index) | (1l << tableCard2.index) | (1l << tableCard3.index) | turnRivers[index];
                int otherBestPositionIndex = getBestFiveCombination(otherWholeCards);
//                System.out.println(System.currentTimeMillis());
                if (bestPositionIndex < otherBestPositionIndex) {
                    lCount++;
                } else {
                    wCount++;
                }
            }
            winCount += wCount;
            loseCount += lCount;
            //System.out.println(turnCard + " " + riverCard + " " + combinationMap.get(positions.get(bestPositionIndex)) + String.format(" %.2f %.2f", wCount * 1.0 / (wCount + lCount), lCount * 1.0 / (wCount + lCount)));
        }
        System.out.println((System.currentTimeMillis() - start));
        System.out.println(playerCard1 + " " + playerCard2 + " " + tableCard1 + " " + tableCard2
                + " " + tableCard3 + String.format(" %.2f %.2f", winCount * 1.0 / (winCount + loseCount), loseCount * 1.0 / (winCount + loseCount)));
    }

    //模拟两人牌局，自己两张底牌，三种公共牌，自己赢、平、输的概率 通过概率计算
    public static void testify(String s1, String s2, String s3, String s4, String s5) {
        List<Card> playingCards = new ArrayList<Card>(cards);
        Card playerCard1 = cardMap.get(s1);
        Card playerCard2 = cardMap.get(s2);
        Card tableCard1 = cardMap.get(s3);
        Card tableCard2 = cardMap.get(s4);
        Card tableCard3 = cardMap.get(s5);

        playingCards.remove(playerCard1);
        playingCards.remove(playerCard2);
        playingCards.remove(tableCard1);
        playingCards.remove(tableCard2);
        playingCards.remove(tableCard3);

        int winCount = 0;
        int loseCount = 0;
        int plainCount = 0;
        for (int index = 0; index < 100000; index++) {
            List<Card> copyPlayingCards = new ArrayList<Card>(playingCards);
            Card tableCard4 = getRandomCard(copyPlayingCards);
            Card tableCard5 = getRandomCard(copyPlayingCards);

            Card player2Card1 = getRandomCard(copyPlayingCards);
            Card player2Card2 = getRandomCard(copyPlayingCards);


            long myWholeCards = (1l << playerCard1.index) | (1l << playerCard2.index) | (1l << tableCard1.index) | (1l << tableCard2.index) |
                    (1l << tableCard3.index) | (1l << tableCard4.index) | (1l << tableCard5.index);
            int myBestCardIndex = getBestFiveCombination(myWholeCards);
            long otherWholeCards = (1l << player2Card1.index) | (1l << player2Card2.index) | (1l << tableCard1.index) | (1l << tableCard2.index) |
                    (1l << tableCard3.index) | (1l << tableCard4.index) | (1l << tableCard5.index);
            int otherBestCardIndex = getBestFiveCombination(otherWholeCards);
            Combination myCombination = combinationMap.get(positions.get(myBestCardIndex));
            Combination otherCombination = combinationMap.get(positions.get(otherBestCardIndex));
            int com = myCombination.compareTo(otherCombination);
            if (com < 0) {
                loseCount++;
            } else if (com == 0) {
                plainCount++;
            } else {
                winCount++;
            }
            System.out.println("[" + tableCard1 + " " + tableCard2 + " " + tableCard3 + " " + tableCard4 + " " + tableCard5 + "] ["
                    + playerCard1 + " " + playerCard2 + "] ["
                    + player2Card1 + " " + player2Card2 + "] " + "[" + myCombination + "][" + otherCombination + "]" + (com < 0 ? "输" : (com > 0 ? "赢" : "平")));

        }

        System.out.println(String.format(" %.2f %.2f %.2f", winCount * 1.0 / (winCount + plainCount + loseCount),
                loseCount * 1.0 / (winCount + plainCount + loseCount),
                plainCount * 1.0 / (winCount + plainCount + loseCount)
        ));

    }

    //模拟6人牌局，其他同上
    public static void testifyForSix(String s1, String s2, String s3, String s4, String s5) {
        List<Card> playingCards = new ArrayList<Card>(cards);
        Card playerCard1 = cardMap.get(s1);
        Card playerCard2 = cardMap.get(s2);
        Card tableCard1 = cardMap.get(s3);
        Card tableCard2 = cardMap.get(s4);
        Card tableCard3 = cardMap.get(s5);

        playingCards.remove(playerCard1);
        playingCards.remove(playerCard2);
        playingCards.remove(tableCard1);
        playingCards.remove(tableCard2);
        playingCards.remove(tableCard3);

        int winCount = 0;
        int loseCount = 0;
        int plainCount = 0;

        for (int index = 0; index < 100000; index++) {
            List<Card> copyPlayingCards = new ArrayList<Card>(playingCards);
            Card tableCard4 = getRandomCard(copyPlayingCards);
            Card tableCard5 = getRandomCard(copyPlayingCards);

            Card[] otherPlayerCard1 = new Card[5];
            Card[] otherPlayerCard2 = new Card[5];
            for (int idd = 0; idd < otherPlayerCard1.length; idd++) {
                otherPlayerCard1[idd] = getRandomCard(copyPlayingCards);
                otherPlayerCard2[idd] = getRandomCard(copyPlayingCards);
            }

            long myWholeCards = (1l << playerCard1.index) | (1l << playerCard2.index) | (1l << tableCard1.index) | (1l << tableCard2.index) |
                    (1l << tableCard3.index) | (1l << tableCard4.index) | (1l << tableCard5.index);
            int myBestCardIndex = getBestFiveCombination(myWholeCards);
            Combination myCombination = combinationMap.get(positions.get(myBestCardIndex));

            int state = -1; //2-赢，1-平， 0-输
            for (int idd = 0; idd < otherPlayerCard1.length; idd++) {

                long otherWholeCards = (1l << otherPlayerCard1[idd].index) | (1l << otherPlayerCard2[idd].index) | (1l << tableCard1.index) | (1l << tableCard2.index) |
                        (1l << tableCard3.index) | (1l << tableCard4.index) | (1l << tableCard5.index);
                int otherBestCardIndex = getBestFiveCombination(otherWholeCards);
                Combination otherCombination = combinationMap.get(positions.get(otherBestCardIndex));
                int com = myCombination.compareTo(otherCombination);
                if (com < 0) {
                    state = 0;
                    break;
                } else if (com == 0) {
                    state = (state == 2 ? 2 : 1);
                } else {
                    state = 2;
                }
            }
            if (state > 0) {
                winCount++;
            } else {
                loseCount++;
            }
        }

        System.out.println(s1 + " " + s2 + " " + s3 + " " + s4 + " " + s5 + " " + String.format(" %.2f %.2f %.2f", winCount * 1.0 / (winCount + plainCount + loseCount),
                loseCount * 1.0 / (winCount + plainCount + loseCount),
                plainCount * 1.0 / (winCount + plainCount + loseCount)
        ));

    }


    public static void testifyForTwoOnlyWithMyCard(String s1, String s2) {
        testifyOnlyWithMyCard(s1, s2, 1);
    }

    public static void testifyForSixOnlyWithMyCard(String s1, String s2) {
        testifyOnlyWithMyCard(s1, s2, 5);
    }

    //模拟6人牌局, 只有底牌，赢、平、输的概率
    public static void testifyOnlyWithMyCard(String s1, String s2, int players) {
        List<Card> playingCards = new ArrayList<Card>(cards);
        Card playerCard1 = cardMap.get(s1);
        Card playerCard2 = cardMap.get(s2);

        playingCards.remove(playerCard1);
        playingCards.remove(playerCard2);

        int winCount = 0;
        int loseCount = 0;
        int plainCount = 0;

        for (int index = 0; index < 1000000; index++) {
            List<Card> copyPlayingCards = new ArrayList<Card>(playingCards);
            Card tableCard1 = getRandomCard(copyPlayingCards);
            Card tableCard2 = getRandomCard(copyPlayingCards);
            Card tableCard3 = getRandomCard(copyPlayingCards);
            Card tableCard4 = getRandomCard(copyPlayingCards);
            Card tableCard5 = getRandomCard(copyPlayingCards);

            Card[] otherPlayerCard1 = new Card[players];
            Card[] otherPlayerCard2 = new Card[players];
            for (int idd = 0; idd < otherPlayerCard1.length; idd++) {
                otherPlayerCard1[idd] = getRandomCard(copyPlayingCards);
                otherPlayerCard2[idd] = getRandomCard(copyPlayingCards);
            }

            long myWholeCards = (1l << playerCard1.index) | (1l << playerCard2.index) | (1l << tableCard1.index) | (1l << tableCard2.index) |
                    (1l << tableCard3.index) | (1l << tableCard4.index) | (1l << tableCard5.index);
            int myBestCardIndex = getBestFiveCombination(myWholeCards);
            Combination myCombination = combinationMap.get(positions.get(myBestCardIndex));

            int state = -1; //2-赢，1-平， 0-输
            for (int idd = 0; idd < otherPlayerCard1.length; idd++) {

                long otherWholeCards = (1l << otherPlayerCard1[idd].index) | (1l << otherPlayerCard2[idd].index) | (1l << tableCard1.index) | (1l << tableCard2.index) |
                        (1l << tableCard3.index) | (1l << tableCard4.index) | (1l << tableCard5.index);
                int otherBestCardIndex = getBestFiveCombination(otherWholeCards);
                Combination otherCombination = combinationMap.get(positions.get(otherBestCardIndex));
                int com = myCombination.compareTo(otherCombination);
                if (com < 0) {
                    state = 0;
                    break;
                } else if (com == 0) {
                    state = (state == 2 ? 2 : 1);
                } else {
                    state = 2;
                }
            }
            if (state > 0) {
                winCount++;
            } else {
                loseCount++;
            }
        }

        System.out.println(s1 + " " + s2 + String.format(" %.2f %.2f %.2f", winCount * 1.0 / (winCount + plainCount + loseCount),
                loseCount * 1.0 / (winCount + plainCount + loseCount),
                plainCount * 1.0 / (winCount + plainCount + loseCount)
        ));

    }

    //模拟2人牌局, 只有底牌，赢、平、输的概率
    public static void testifyTwoPlayer(String s1, String s2, String s3, String s4) {
        List<Card> playingCards = new ArrayList<Card>(cards);
        Card playerCard1 = cardMap.get(s1);
        Card playerCard2 = cardMap.get(s2);

        Card otherPlayerCard1 = cardMap.get(s3);
        Card otherPlayerCard2 = cardMap.get(s4);

        playingCards.remove(playerCard1);
        playingCards.remove(playerCard2);
        playingCards.remove(otherPlayerCard1);
        playingCards.remove(otherPlayerCard2);

        int winCount = 0;
        int loseCount = 0;
        int plainCount = 0;

        for (int index = 0; index < 1000000; index++) {
            List<Card> copyPlayingCards = new ArrayList<Card>(playingCards);
            Card tableCard1 = getRandomCard(copyPlayingCards);
            Card tableCard2 = getRandomCard(copyPlayingCards);
            Card tableCard3 = getRandomCard(copyPlayingCards);
            Card tableCard4 = getRandomCard(copyPlayingCards);
            Card tableCard5 = getRandomCard(copyPlayingCards);

            long myWholeCards = (1l << playerCard1.index) | (1l << playerCard2.index) | (1l << tableCard1.index) | (1l << tableCard2.index) |
                    (1l << tableCard3.index) | (1l << tableCard4.index) | (1l << tableCard5.index);
            int myBestCardIndex = getBestFiveCombination(myWholeCards);
            Combination myCombination = combinationMap.get(positions.get(myBestCardIndex));

            long otherWholeCards = (1l << otherPlayerCard1.index) | (1l << otherPlayerCard2.index) | (1l << tableCard1.index) | (1l << tableCard2.index) |
                    (1l << tableCard3.index) | (1l << tableCard4.index) | (1l << tableCard5.index);
            int otherBestCardIndex = getBestFiveCombination(otherWholeCards);
            Combination otherCombination = combinationMap.get(positions.get(otherBestCardIndex));
            int com = myCombination.compareTo(otherCombination);
            if (com < 0) {
                loseCount++;
            } else if (com == 0) {
                plainCount++;
            } else {
                winCount++;
            }
        }

        System.out.println(s1 + " " + s2 + String.format(" %.4f %.4f %.4f", winCount * 1.0 / (winCount + plainCount + loseCount),
                loseCount * 1.0 / (winCount + plainCount + loseCount),
                plainCount * 1.0 / (winCount + plainCount + loseCount)
        ));

    }

}
