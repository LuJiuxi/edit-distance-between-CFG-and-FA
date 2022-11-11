// -*- coding: utf-8 -*-
// @Time    : 2022/11/11 14:08
// @Author  : LuJiuxi
// @File    : Edge.java
// @Software: IntelliJ IDEA 
// @Comment :

public class Edge {
    private State start;
    private String name;
    private State end;

    public Edge(State start, String name, State end) {
        this.start = start;
        this.name = name;
        this.end = end;
    }

    @Override
    public String toString() {
        return '(' + start.getName() + ", " +
                name + ") = " +
                end.getName();
    }
}
