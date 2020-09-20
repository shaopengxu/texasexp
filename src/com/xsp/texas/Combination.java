package com.xsp.texas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by shpng on 2016/10/22.
 */
public class Combination implements Comparable<Combination>, Serializable {


    public List<Card> cards = new ArrayList<Card>();

    public List<Card> cardsSortBySuitPattern = null;

    public SuitPattern suitPattern;

    public long position;

    public boolean valid() {
        if (cards.size() != 5) {
            return false;
        }
        for (Card card : cards) {
            if (!card.valid()) {
                return false;
            }
        }
        Collections.sort(cards);

        return !(cards.get(0).equals(cards.get(1)) ||
                cards.get(1).equals(cards.get(2)) ||
                cards.get(2).equals(cards.get(3)) ||
                cards.get(3).equals(cards.get(4)));
    }

    public void sortAll() {
        Collections.sort(cards);
        cardsSortBySuitPattern = new ArrayList<Card>(cards);
        suitPattern = SuitPattern.getSuitPattern(this);
        sortBySuitPattern();
    }

    /**
     * 从小往大排序
     * 45678
     * ABBBB
     * AABBB
     * ABCCC
     * ABBCC
     */
    private void sortBySuitPattern(){

        switch (suitPattern) {
            case STRAIGHT_FLUSH:
            case STRAIGHT:
                if (cardsSortBySuitPattern.get(0).number == 1 && cardsSortBySuitPattern.get(4).number == 13) {
                    Card card = cardsSortBySuitPattern.get(4);
                    cardsSortBySuitPattern.remove(4);
                    cardsSortBySuitPattern.add(0, card);
                }
                break;
            case FLUSH:
            case HIGH_CARD:
                break;
            case FOUR_OF_A_KIND:
                if (cardsSortBySuitPattern.get(0).number == cardsSortBySuitPattern.get(1).number) {
                    Collections.swap(cardsSortBySuitPattern, 0, 4);
                }
                break;
            case FULL_HOURSE:
                if (cardsSortBySuitPattern.get(1).number == cardsSortBySuitPattern.get(2).number) {
                    //AAABB型 变为
                    //BBAAA
                    Collections.swap(cardsSortBySuitPattern, 0, 4);
                    Collections.swap(cardsSortBySuitPattern, 1, 3);
                }
                break;
            case THREE_OF_A_KIND:
                if (cardsSortBySuitPattern.get(0).number == cardsSortBySuitPattern.get(1).number && cardsSortBySuitPattern.get(1).number == cardsSortBySuitPattern.get(2).number) {
                    //AAABC 22234
                    Collections.swap(cardsSortBySuitPattern, 0, 3);
                    Collections.swap(cardsSortBySuitPattern, 1, 4);
                } else if (cardsSortBySuitPattern.get(1).number == cardsSortBySuitPattern.get(2).number && cardsSortBySuitPattern.get(2).number == cardsSortBySuitPattern.get(3).number) {
                    //ABBBC 23334
                    Collections.swap(cardsSortBySuitPattern, 1, 4);
                }
                break;
            case TWO_PAIR:
                if (cardsSortBySuitPattern.get(0).number == cardsSortBySuitPattern.get(1).number && cardsSortBySuitPattern.get(2).number == cardsSortBySuitPattern.get(3).number) {
                    //AABBC -> CAABB
                    Collections.swap(cardsSortBySuitPattern, 3, 4);
                    Collections.swap(cardsSortBySuitPattern, 2, 3);
                    Collections.swap(cardsSortBySuitPattern, 1, 2);
                    Collections.swap(cardsSortBySuitPattern, 0, 1);
                }else if (cardsSortBySuitPattern.get(0).number == cardsSortBySuitPattern.get(1).number && cardsSortBySuitPattern.get(3).number == cardsSortBySuitPattern.get(4).number) {
                    //AABCC
                    Collections.swap(cardsSortBySuitPattern, 0 ,2);
                }
                break;
            case ONE_PAIR:
                if(cardsSortBySuitPattern.get(0).number == cardsSortBySuitPattern.get(1).number) {
                    //AABCD
                    Card card1 = cardsSortBySuitPattern.get(0);
                    Card card2 = cardsSortBySuitPattern.get(1);
                    cardsSortBySuitPattern.remove(0);
                    cardsSortBySuitPattern.remove(0);
                    cardsSortBySuitPattern.add(card1);
                    cardsSortBySuitPattern.add(card2);
                }else if (cardsSortBySuitPattern.get(1).number == cardsSortBySuitPattern.get(2).number) {
                    //ABBCD
                    Card card1 = cardsSortBySuitPattern.get(1);
                    Card card2 = cardsSortBySuitPattern.get(2);
                    cardsSortBySuitPattern.remove(1);
                    cardsSortBySuitPattern.remove(1);
                    cardsSortBySuitPattern.add(card1);
                    cardsSortBySuitPattern.add(card2);
                }else if (cardsSortBySuitPattern.get(2).number == cardsSortBySuitPattern.get(3).number) {
                    //ABCCD
                    Collections.swap(cardsSortBySuitPattern, 2,4);
                }
                break;
        }

    }

    public int compareTo(Combination combination) {
        int comparator = suitPattern.compareTo(combination.suitPattern);

        if (comparator == 0) {
            for (int i = 4; i >=0; i--) {

                if (cardsSortBySuitPattern.get(i).number != combination.cardsSortBySuitPattern.get(i).number) {
                    return cardsSortBySuitPattern.get(i).number - combination.cardsSortBySuitPattern.get(i).number;
                }
            }
            return 0;
        } else {
            return -comparator;
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("");
        sb.append(suitPattern.getName()).append(", ");
        for (int index = 0; index < cardsSortBySuitPattern.size(); index++) {
            sb.append(cardsSortBySuitPattern.get(index).toString());
            if (index != cardsSortBySuitPattern.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public static void generateCombination() {

    }
}


