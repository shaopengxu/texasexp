package com.xsp.texas;

/**
 * Created by shpng on 2016/10/22.
 */
public enum SuitPattern {

    STRAIGHT_FLUSH("同花顺"),
    FOUR_OF_A_KIND("四条"),
    FULL_HOURSE("葫芦"),
    FLUSH("同花"),
    STRAIGHT("顺子"),
    THREE_OF_A_KIND("三条"),
    TWO_PAIR("两对"),
    ONE_PAIR("一对"),
    HIGH_CARD("高牌");

    private String name;

    SuitPattern(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * 获得牌型
     * @param combination
     * @return
     */
    public static SuitPattern getSuitPattern(Combination combination) {
        if (!combination.valid()) {
            return null;
        }
        boolean ifStraight = true;
        boolean ifFlush = true;
        int maxCount = 1;
        int suit = combination.cards.get(0).suit;
        int number = combination.cards.get(0).number;

        int currentCardNumber = number;
        int count = 1;
        int totalCount = 0;
        for (int index = 1; index < combination.cards.size(); index++) {
            Card c = combination.cards.get(index);
            if (c.suit != suit) {
                ifFlush = false;
            }

            if (c.number == number + 1 || ( index == 4 && ifStraight && c.number == 13 && number == 4)) {
                number++;
            } else {
                ifStraight = false;
            }
            if (c.number == currentCardNumber) {
                count++;
            } else {
                if (count > maxCount) {
                    maxCount = count;
                }
                if (count >= 2) {
                    totalCount += 1;
                }
                currentCardNumber = c.number;
                count = 1;
            }
        }
        if (count > maxCount) {
            maxCount = count;
        }
        if (count >= 2) {
            totalCount += 1;
        }
        if (ifStraight && ifFlush) {
            return STRAIGHT_FLUSH;
        } else if (ifFlush) {
            return FLUSH;
        }
        if (maxCount == 4) {
            return FOUR_OF_A_KIND;
        } else if (maxCount == 3) {
            if (totalCount == 2) {
                return FULL_HOURSE;
            }
            return THREE_OF_A_KIND;
        }
        if (ifStraight) {
            return STRAIGHT;
        }
        if (maxCount==2) {
            if (totalCount == 2) {
                return TWO_PAIR;
            } else {
                return ONE_PAIR;
            }
        }
        return HIGH_CARD;
    }

}
