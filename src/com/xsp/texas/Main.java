package com.xsp.texas;


import java.io.File;
import java.util.*;

/**
 * Created by shpng on 2016/10/22.
 */
public class Main {

    public static List<Card> cards = new ArrayList<Card>();
    public static Map<String, Card> cardMap = new HashMap<String, Card>();
    public static Map<Long, Card> cardPositionMap = new HashMap<Long, Card>();

    // <Key,Value>,  Key: 每个组合的牌在排序好的52张牌的位置，比如000101110000001 Value: 组合
    public static Map<Long, Combination> combinationMap = new HashMap<Long, Combination>();
    //排好序的组合的List
    public static List<Combination> sortedCombinations = new ArrayList<Combination>();

    public static List<Long> positions = new ArrayList<Long>();
    public static Map<Long, Integer> positionIndexMap = new HashMap<Long, Integer>();

    static {
        recover();
    }

    /**
     * 洗牌
     */
    public static void recover() {
        cards = new ArrayList<Card>();
        cardMap = new HashMap<String, Card>();
        for (int index1 = 1; index1 <= 13; index1++) {
            for (int index2 = 1; index2 <= 4; index2++) {
                Card card = new Card();
                card.number = index1;
                card.suit = index2;
                cards.add(card);
                card.index = cards.indexOf(card);
                cardMap.put(card.toEasyString(), card);
                cardPositionMap.put(1l << card.index, card);
            }
        }
    }

    /**
     * 随机生成五张牌, not good, should use existing combination
     * @return
     */
    public static Combination generateCombination() {
        Combination combination = new Combination();
        for (int index = 0; index < 5; index++) {
            int random = (int) (Math.random() * cards.size());
            Card c = cards.get(random);
            combination.cards.add(c);
            cards.remove(random);
        }
        combination.sortAll();
        return combination;
    }

    public static Card getRandomCard(List<Card> cs) {
        int random = (int) (Math.random() * cs.size());
        Card c = cs.get(random);
        cs.remove(random);
        return c;
    }

    /**
     * 将组合写到文件
     */
    public static void writeAll() {

        File f1 = new File("position.data");
        //File f3 = new File("combination_map.data");
        ReadWriteObjectToFile.writeObjectToFile(positions, f1);
        //ReadWriteObjectToFile.writeObjectToFile(combinationMap, f3);
    }

    /**
     * 从文件load所有组合
     */
    public static void recoverDataFromFile() {
        long start = System.currentTimeMillis();

        recover();
        File f1 = new File("position.data");
        positions = (List<Long>) ReadWriteObjectToFile.readObjectFromFile(f1);
        for (long position : positions) {
            Combination combination = new Combination();
            for (int index = 0; index < cards.size(); index++) {
                long cardPosition = 1l << index;
                if ((position & cardPosition) == cardPosition) {
                    combination.cards.add(cardPositionMap.get(cardPosition));
                }
            }
            combination.position = position;
            combination.sortAll();
            combination.suitPattern = SuitPattern.getSuitPattern(combination);
            combinationMap.put(position, combination);
        }
        sortedCombinations = new ArrayList<Combination>(combinationMap.values());
        Collections.sort(sortedCombinations);
        for (int index = 0; index < positions.size(); index++) {
            positionIndexMap.put(positions.get(index), index);
        }
        System.out.println("耗费时间 " + (System.currentTimeMillis() - start));

    }

    /**
     * 生成所有的组合
     */
    public static void generateAllCombinations() {
        long start = System.currentTimeMillis();

        Set<Long> existSet = new HashSet<Long>();
        for (int index1 = 0; index1 < cards.size(); index1++) {
            for (int index2 = 0; index2 < cards.size(); index2++) {
                for (int index3 = 0; index3 < cards.size(); index3++) {
                    for (int index4 = 0; index4 < cards.size(); index4++) {
                        for (int index5 = 0; index5 < cards.size(); index5++) {
                            if (index1 == index2 || index1 == index3 || index1 == index4 || index1 == index5 ||
                                    index2 == index3 || index2 == index4 || index2 == index5 ||
                                    index3 == index4 || index3 == index5 || index4 == index5) {
                                continue;
                            }
                            long l = 0l;
                            l = l | (1l << index1) | (1l << index2) | (1l << index3) | (1l << index4) | (1l << index5);
                            if (existSet.contains(l)) {
                                continue;
                            } else {
                                existSet.add(l);
                            }
                            Combination combination = new Combination();
                            combination.cards.add(cards.get(index1));
                            combination.cards.add(cards.get(index2));
                            combination.cards.add(cards.get(index3));
                            combination.cards.add(cards.get(index4));
                            combination.cards.add(cards.get(index5));
                            combination.position = l;
                            combination.sortAll();
                            combinationMap.put(l, combination);
                        }
                    }
                }
            }
        }
        System.out.println("生成耗费时间 " + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        sortedCombinations.addAll(combinationMap.values());
        Collections.sort(sortedCombinations);
        for (Combination combination : sortedCombinations) {
            positions.add(combination.position);
        }
        System.out.println("排序耗费时间 " + (System.currentTimeMillis() - start));
        // 2598960
        System.out.println("count " + combinationMap.size());
    }


    //TODO flop阶段，自己的底牌加公共牌各个牌型出现的概率多大!! 根据公共牌对方出现各种牌型的概率

    //TODO 函数 获取一个Set的元素的组合

    public static Combination getBestFiveCombination(Card c1, Card c2, Card c3, Card c4, Card c5, Card c6, Card c7) {
        long l = (1l << c1.index) | (1l << c2.index) | (1l << c3.index)
                | (1l << c4.index) | (1l << c5.index) | (1l << c6.index) | (1l << c7.index);
        int index = getBestFiveCombination(l);
        return sortedCombinations.get(index);
    }

    /**
     * 从7张牌获取最好的五张牌
     * @param l
     * @return
     */
    public static int getBestFiveCombination(long l) {
        Long[] ll = SetOperator.getSetCombinations(l, 5);
        int bestIndex = 0;
        for (long temp : ll) {
            int index = positionIndexMap.get(temp);
            if (index > bestIndex) {
                bestIndex = index;
            }
        }
        return bestIndex;
    }

    public static Combination getCombination(Card c1, Card c2, Card c3, Card c4, Card c5) {
        long l = (1l << c1.index) | (1l << c2.index) | (1l << c3.index) | (1l << c4.index) | (1l << c5.index);
        return combinationMap.get(l);
    }



    public static void main(String[] args) {
        //generateAllCombinations();  95秒
        //writeAll();
        recoverDataFromFile(); // 23秒

    }


    public static void main1(String[] args) {
        recoverDataFromFile();
    }

}
