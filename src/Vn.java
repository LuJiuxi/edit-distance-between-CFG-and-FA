// -*- coding: utf-8 -*-
// @Time    : 2022/11/10 23:07
// @Author  : LuJiuxi
// @File    : Vn.java
// @Software: IntelliJ IDEA 
// @Comment :

import java.util.ArrayList;

public class Vn extends Variable{
    private ArrayList<ArrayList<Variable>> deductions = new ArrayList<>();

    public Vn(String name) {
        super(name);
    }

    public void addDeduction(ArrayList<Variable> deduction) {
        deductions.add(deduction);
    }
}
