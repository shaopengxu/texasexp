package com.xsp.texas;

import java.util.*;

/**
 * Created by Shaopeng.Xu on 2016-10-27.
 */
public class SetOperator {

    public static Set<Long> getSetCombination(int all, int count) {
        Set<Long> set = new HashSet<Long>();

        for (int i = 0; i < all; i++) {
            set.add(1l << i);
        }
        if (count == 1) {
            return set;
        }
        return getSetCombination(set, all, count - 1, 2);

    }

    public static int getCount(int all, int count) {
        long result = 1;

        for (int i = 0; i < count; i++) {
            result = result * (all - i);
        }
        long temp = 1;
        for (int i = 1; i <= count; i++) {
            temp = temp * i;
        }
        return (int) (result / temp);
    }

    public static Set<Long> getSetCombination(Set<Long> set, int all, int count, int realCount) {
        int number = getCount(all, realCount);
        Set<Long> newSet = new HashSet<Long>(number * 2);
        outer:
        for (Long l : set) {

            for (int i = 0; i < all; i++) {
                if ((l | 1l << i) == l) {
                    break;
                }

                newSet.add(l | 1l << i);
                if (newSet.size() == number) {
                    break outer;
                }
            }
        }

        if (count - 1 != 0) {
            return getSetCombination(newSet, all, count - 1, realCount + 1);
        } else {
            return newSet;
        }
    }



    public static Long[] getSetCombinationAnotherWay(int all, int count) {

        Map m = new HashMap();
        getSetCombinationAnotherWay(m, all, count);
        List<Integer> list = new ArrayList<Integer>(m.keySet());
        Collections.sort(list);

//        Map<Integer, Integer> countMap = new HashMap<Integer, Integer>();

        Map setMap = new HashMap(m.size() * 2);
        for (Integer i : list) {
            int a = i / 10;
            int b = i % 10;
            if (b == 1) {
                Long[] s = new Long[a];
                for (int index = 0; index < a; index++) {
                    s[index] = 1l << index;
//                    s.add(1l << index);
                }
                setMap.put(i, s);
            } else if (a == b) {
                Long[] s = new Long[1];
                long l = 0l;
                for (int index = 0; index < a; index++) {
                    l = l | 1l << index;
                }
                s[0] = l;
                setMap.put(i, s);
            } else {
                int value1 = (a - 1) * 10 + b - 1;
                int value2 = (a - 1) * 10 + b;

                Long[] s0 = (Long[]) setMap.get(value1);
                Long[] s1 = (Long[]) setMap.get(value2);
                Long[] s = new Long[s0.length + s1.length];
//                if (b == 2 ) {
//                    setMap.remove(value1);
//                }
//                if (b == count) {
//                    setMap.remove(value2);
//                }
//                if(a - 1 == b){
//                    setMap.remove(value2);
//                }
//                if (a == b) {
//                    setMap.remove(value1);
//                }
//                if (countMap.get(value1) == null) {
//                    countMap.put(value1, value1);
//                }else{
//                    setMap.remove(value1);
//                }
//                if (countMap.get(value2) == null) {
//                    countMap.put(value2, value2);
//                }else{
//                    setMap.remove(value2);
//                }

                int index = 0;
                if (s0 == null) {
                    System.out.println("errors0 : " + value1);
                } else {
                    for (Long l : s0) {
//                        s.add(l|1l<<(a-1));
                        s[index++] = l | 1l << (a - 1);
                    }
                }
                if (s1 == null) {
                    System.out.println("errors1 : " + value2);
                } else {
//                    s.addAll(s1);
                    System.arraycopy(s1, 0, s, index, s1.length);
                }
                setMap.put(i, s);
            }
        }
        return (Long[]) setMap.get(all * 10 + count);
    }

    public static void getSetCombinationAnotherWay(Map<Integer, Object[]> map, int all, int count) {

        int key = all * 10 + count;

        if (all == count) {

        } else if (count == 1) {

        } else {
            if (map.get((all - 1) * 10 + count) != null) {

            } else {
                getSetCombinationAnotherWay(map, all - 1, count);
            }
            if (map.get((all - 1) * 10 + count - 1) != null) {
                map.get((all - 1) * 10 + count - 1);
            } else {
                getSetCombinationAnotherWay(map, all - 1, count - 1);
            }

        }
        map.put(key, null);
    }

    public static Long[] getSetCombinations(long l, int count) {
        int all = Long.bitCount(l);
        Long[] setCombination = getSetCombinationAnotherWay(all, count);

        Long[] result = new Long[setCombination.length];
        int idx = 0;
        for (Long ll : setCombination) {
            long temp = 0;
            int index1 = 0;
            for (int index = 0; index < 64; index++) {
                if ((l & (1l << index)) > 0 && ((ll & (1l << index1++)) > 0)) {
                    temp = temp | (1l << index);
                }
            }
            result[idx++] = temp;
        }
        return result;
    }



    public static void main(String[] args) {

//        Long [] ls = getSetCombinations(Long.parseLong("11111011", 2), 3);
//        for (long l : ls) {
//            System.out.print(Long.toBinaryString(l) + " ");
//        }
        System.out.println(getCount(52, 7));
    }

}
