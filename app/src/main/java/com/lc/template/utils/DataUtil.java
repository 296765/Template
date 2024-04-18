package com.lc.template.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wei Ting
 * on 2023/7/27
 * Description
 */
public class DataUtil {
    public static List<String> getListString() {
        List<String>List=new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            List.add(i+"");
        }
        return List;
    }

    public static List<String> getListString2() {
        List<String>List=new ArrayList<>();
        for (int i = 0; i <3; i++) {
            List.add(i+"");
        }
        return List;
    }
    public static List<String> getListString3() {
        List<String>List=new ArrayList<>();
        for (int i = 0; i <20; i++) {
            List.add(i+"");
        }
        return List;
    }


}
