//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yyb.ueditor.define;

import java.util.HashMap;
import java.util.Map;

public class MIMEType {
    public static final Map<String, String> types = new HashMap<String, String>() {
        {
            this.put("image/gif", ".gif");
            this.put("image/jpeg", ".jpg");
            this.put("image/jpg", ".jpg");
            this.put("image/png", ".png");
            this.put("image/bmp", ".bmp");
        }
    };

    public MIMEType() {
    }

    public static String getSuffix(String mime) {
        return (String)types.get(mime);
    }
}
