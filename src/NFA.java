// -*- coding: utf-8 -*-
// @Time    : 2022/11/10 23:01
// @Author  : LuJiuxi
// @File    : NFA.java
// @Software: IntelliJ IDEA 
// @Comment :

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class NFA {
    private LinkedHashSet<State> states = new LinkedHashSet<>();
    private LinkedHashMap<State, StateFunc> stateFuncs = new LinkedHashMap<>();
    private State q;
    private State p;
}
