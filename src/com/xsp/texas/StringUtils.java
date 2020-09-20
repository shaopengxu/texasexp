package com.xsp.texas;

/**
 * Created by shpng on 2017/12/23.
 */
public class StringUtils {

    public static boolean equals(String a, String b){
        if(a == null){
            return b == null;
        }
        return a.equals(b);
    }
}
