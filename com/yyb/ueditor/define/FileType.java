//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yyb.ueditor.define;

import java.util.HashMap;
import java.util.Map;

public class FileType {
    public static final String JPG = "JPG";
    private static final Map<String, String> types = new HashMap<String, String>() {
        {
            this.put("JPG", ".jpg");
        }
    };

    public FileType() {
    }

    public static String getSuffix(String key) {
        return (String)types.get(key);
    }

    public static String getSuffixByFilename(String filename) {
        return filename.substring(filename.lastIndexOf(".")).toLowerCase();
    }
}
