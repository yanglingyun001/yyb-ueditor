//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yyb.ueditor.define;

public interface State {
    boolean isSuccess();

    void putInfo(String var1, String var2);

    void putInfo(String var1, long var2);

    String toJSONString();
}
