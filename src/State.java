// -*- coding: utf-8 -*-
// @Time    : 2022/11/10 23:13
// @Author  : LuJiuxi
// @File    : State.java
// @Software: IntelliJ IDEA 
// @Comment :

import java.util.Objects;

public class State {
    private String name;

    public State(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(name, state.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
