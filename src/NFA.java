// -*- coding: utf-8 -*-
// @Time    : 2022/11/10 23:01
// @Author  : LuJiuxi
// @File    : NFA.java
// @Software: IntelliJ IDEA 
// @Comment :

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class NFA {
    private LinkedHashMap<String, State> states = new LinkedHashMap<>();
    private final LinkedHashMap<String, Vt> vts = new LinkedHashMap<>();
    private State startState;
    private ArrayList<State> finalStates = new ArrayList<>();

    public LinkedHashMap<String, State> getStates() {
        return states;
    }

    public LinkedHashMap<String, Vt> getVts() {
        return vts;
    }

    public ArrayList<State> getFinalStates() {
        return finalStates;
    }

    public void setStartState(State startState) {
        this.startState = startState;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("STATEs:\n").append(states.keySet()).append('\n');
        stringBuilder.append("VTs:\n").append(vts.keySet()).append('\n');
        stringBuilder.append("STFs:\n");
        for (String key: states.keySet()) {
            stringBuilder.append(states.get(key).toString());
            stringBuilder.append('\n');
        }
        stringBuilder.append("START:\n").append(startState.getName()).append('\n');
        stringBuilder.append("FINAL:\n");
        for (State finalSate: finalStates) {
            stringBuilder.append(finalSate.getName());
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }
}
