// -*- coding: utf-8 -*-
// @Time    : 2022/11/10 23:02
// @Author  : LuJiuxi
// @File    : Main.java
// @Software: IntelliJ IDEA 
// @Comment :

public class Main {
    public static void main(String[] args) {
        CFG cfg = Tools.initCFGFromFile("input.txt");
        NFA nfa = Tools.initNFAFromFile("input.txt");
        System.out.println(cfg);
        System.out.println();
        System.out.println(nfa);
    }
}
