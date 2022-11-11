// -*- coding: utf-8 -*-
// @Time    : 2022/11/10 23:07
// @Author  : LuJiuxi
// @File    : Vn.java
// @Software: IntelliJ IDEA 
// @Comment :

import java.util.ArrayList;

public class Vn extends Variable{
    private final ArrayList<ArrayList<Variable>> deductions = new ArrayList<>();

    public Vn(String name) {
        super(name);
    }

    public void addDeduction(ArrayList<Variable> deduction) {
        deductions.add(deduction);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (ArrayList<Variable> variables: deductions) {
            stringBuilder.append(getName()).append(" --> ");
            for (Variable variable: variables) {
                stringBuilder.append(variable.getName());
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }
}
