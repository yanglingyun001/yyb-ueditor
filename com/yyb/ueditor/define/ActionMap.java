//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yyb.ueditor.define;

import java.util.HashMap;
import java.util.Map;

public final class ActionMap {
    public static final Map<String, Integer> mapping = new HashMap<String, Integer>() {
        {
            this.put("config", 0);
            this.put("uploadimage", 1);
            this.put("uploadscrawl", 2);
            this.put("uploadvideo", 3);
            this.put("uploadfile", 4);
            this.put("catchimage", 5);
            this.put("listfile", 6);
            this.put("listimage", 7);
        }
    };
    public static final int CONFIG = 0;
    public static final int UPLOAD_IMAGE = 1;
    public static final int UPLOAD_SCRAWL = 2;
    public static final int UPLOAD_VIDEO = 3;
    public static final int UPLOAD_FILE = 4;
    public static final int CATCH_IMAGE = 5;
    public static final int LIST_FILE = 6;
    public static final int LIST_IMAGE = 7;

    public ActionMap() {
    }

    public static int getType(String key) {
        return (Integer)mapping.get(key);
    }
}
