//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yyb.ueditor.define;

import com.yyb.ueditor.Encoder;

import java.util.*;

public class MultiState implements State {
    private boolean state = false;
    private String info = null;
    private Map<String, Long> intMap = new HashMap();
    private Map<String, String> infoMap = new HashMap();
    private List<String> stateList = new ArrayList();

    public MultiState(boolean state) {
        this.state = state;
    }

    public MultiState(boolean state, String info) {
        this.state = state;
        this.info = info;
    }

    public MultiState(boolean state, int infoKey) {
        this.state = state;
        this.info = com.yyb.ueditor.define.AppInfo.getStateInfo(infoKey);
    }

    public boolean isSuccess() {
        return this.state;
    }

    public void addState(State state) {
        this.stateList.add(state.toJSONString());
    }

    public void putInfo(String name, String val) {
        this.infoMap.put(name, val);
    }

    public String toJSONString() {
        String stateVal = this.isSuccess() ? com.yyb.ueditor.define.AppInfo.getStateInfo(0) : this.info;
        StringBuilder builder = new StringBuilder();
        builder.append("{\"state\": \"" + stateVal + "\"");
        Iterator iterator = this.intMap.keySet().iterator();

        while(iterator.hasNext()) {
            stateVal = (String)iterator.next();
            builder.append(",\"" + stateVal + "\": " + this.intMap.get(stateVal));
        }

        iterator = this.infoMap.keySet().iterator();

        while(iterator.hasNext()) {
            stateVal = (String)iterator.next();
            builder.append(",\"" + stateVal + "\": \"" + (String)this.infoMap.get(stateVal) + "\"");
        }

        builder.append(", list: [");
        iterator = this.stateList.iterator();

        while(iterator.hasNext()) {
            builder.append((String)iterator.next() + ",");
        }

        if (this.stateList.size() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }

        builder.append(" ]}");
        return Encoder.toUnicode(builder.toString());
    }

    public void putInfo(String name, long val) {
        this.intMap.put(name, val);
    }
}
