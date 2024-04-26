package com.lc.template.model;

import java.io.Serializable;

/**
 * Created by Wei Ting
 * on 2024/4/26
 * Description
 */
public class UpdateEntity implements Serializable {


    /**
     * code : 200
     * message : ok
     * data : {"banben":"ok","android":"ok"}
     */

    public Integer code;
    public String message;
    public DataData data;

    public static class DataData implements Serializable {
        /**
         * banben : ok
         * android : ok
         */

        public String banben;
        public String android;
        public String version;
    }
}
