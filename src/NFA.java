// -*- coding: utf-8 -*-
// @Time    : 2022/11/10 23:01
// @Author  : LuJiuxi
// @File    : NFA.java
// @Software: IntelliJ IDEA 
// @Comment :

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class NFA {
    private final LinkedHashSet<String> states = new LinkedHashSet<>();
    private final LinkedHashSet<String> vts = new LinkedHashSet<>();
    private final LinkedHashMap<String, String> nfa = new LinkedHashMap<>();
    private String start;
    private final LinkedHashSet<String> ends = new LinkedHashSet<>();

    public LinkedHashSet<String> getStates() {
        return states;
    }

    public LinkedHashSet<String> getVts() {
        return vts;
    }

    public LinkedHashMap<String, String> getNfa() {
        return nfa;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public LinkedHashSet<String> getEnds() {
        return ends;
    }

    public String getStart() {
        return start;
    }
}
