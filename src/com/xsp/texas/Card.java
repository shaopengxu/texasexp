package com.xsp.texas;

import java.io.Serializable;

/**
 * Created by shpng on 2016/10/22.
 */
public class Card implements Comparable<Card>, Serializable {

    // 1~13
    //1表示2，2表示3,依此类推，13表示A
    public int number;

    // 1 黑桃 2 红心 3 草花 4 方片
    public int suit;

    //在整副牌的位置
    public int index;

    public boolean equals(Card c) {
        return number == c.number && suit == c.suit;
    }

    public boolean valid() {
        return number <= 13 && number >= 1 && suit <= 4 && suit >= 1;
    }

    public int compareTo(Card o) {
        return number * 10 + suit - (o.number * 10 + o.suit);
    }

    public String toEasyString() {
        StringBuffer sb = new StringBuffer("");

        switch (suit) {
            case 1:
                sb.append("A");
                break;
            case 2:
                sb.append("B");
                break;
            case 3:
                sb.append("C");
                break;
            case 4:
                sb.append("D");
                break;
        }
        switch (number) {
            case 13:
                sb.append("A");
                break;
            case 12:
                sb.append("K");
                break;
            case 11:
                sb.append("Q");
                break;
            case 10:
                sb.append("J");
                break;
            case 9:
                sb.append("T");
                break;
            default:
                sb.append(String.valueOf(number + 1));
        }
        return sb.toString();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("");

        switch (suit) {
            case 1:
                sb.append("黑桃");
                break;
            case 2:
                sb.append("红心");
                break;
            case 3:
                sb.append("草花");
                break;
            case 4:
                sb.append("方片");
                break;
        }
        switch (number) {
            case 13:
                sb.append("A");
                break;
            case 12:
                sb.append("K");
                break;
            case 11:
                sb.append("Q");
                break;
            case 10:
                sb.append("J");
                break;
            case 9:
                sb.append("T");
                break;
            default:
                sb.append(String.valueOf(number + 1));
        }
        return sb.toString();
    }
}
