package com.github.chronic.service.util;

import java.io.Serializable;

public class StringUtils implements Serializable{

    private static final long serialVersionUID = 102111L;

    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        }

        if (str.trim().equals("")) {
            return true;
        }
        
        return false;
    }

}
