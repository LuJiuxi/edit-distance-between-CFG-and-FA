// -*- coding: utf-8 -*-
// @Time    : 2022/11/10 23:13
// @Author  : LuJiuxi
// @File    : State.java
// @Software: IntelliJ IDEA 
// @Comment :

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Objects;

public class State {
    private String name;
    private ArrayList<Edge> edges = new ArrayList<>();

    public State(String name) {
        this.name = name;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('\t').append(name).append(":\n");
        for (Edge edge: edges) {
            stringBuilder.append('\t');
            stringBuilder.append(edge.toString());
        }
        return stringBuilder.toString();
    }
}
