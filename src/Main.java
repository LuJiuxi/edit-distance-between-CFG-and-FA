// -*- coding: utf-8 -*-
// @Time    : 2022/11/10 23:02
// @Author  : LuJiuxi
// @File    : Main.java
// @Software: IntelliJ IDEA 
// @Comment :

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class Main {
    public static void main(String[] args) {
        CFG cfg = Tools.initCFGFromFile("input4.txt");
        NFA nfa = Tools.initNFAFromFile("input4.txt");
//        System.out.println(cfg);
//        System.out.println(nfa);

        System.out.println("开始计算...");
        long start = System.currentTimeMillis();

        LinkedHashMap<String, Integer> Cost = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> Dst = Tools.floydWarshall(nfa);
        LinkedHashMap<String, String> nfaGraph = nfa.getNfa();
        LinkedHashSet<String> states = nfa.getStates();
        LinkedHashMap<String, Vn> vns = cfg.getVns();
        // 初始化C
        for (String q: states) {
            for (String p: states) {
                for (String A: vns.keySet()) {
                    Cost.put(Tools.genKey(A, q, p), Integer.MAX_VALUE >> 2);
                }
            }
        }


        for (String keyQP: nfaGraph.keySet()) {
            String a = nfaGraph.get(keyQP);
            for (String keyA: vns.keySet()) {
                ArrayList<ArrayList<Variable>> deductions = vns.get(keyA).getDeductions();
                for (ArrayList<Variable> deduction: deductions) {
                    Variable variable = deduction.get(0);
                    if (deduction.size() == 1 && variable instanceof Vt) {
                        String b = variable.getName();
                        int min = Tools.min(Tools.cost(a, b), a.length() + b.length());
                        Cost.put(Tools.genKey(keyA, keyQP), min);
                    }
                }
            }
        }

        for (String q: states) {
            for (String r: states) {
                for (String p: states) {
                    for (String A: vns.keySet()) {
                        String Aqp = Tools.genKey(A, q, p);
                        String Arp = Tools.genKey(A, r, p);
                        String qr = Tools.genKey(q, r);
                        int CAqp = Cost.get(Aqp);
                        int Dqr = Dst.get(qr);
                        int CArp = Cost.get(Arp);
                        int min = Tools.min(CAqp, Dqr + CArp);
                        Cost.put(Aqp, min);
                    }
                }
            }
        }

        for (String q: states) {
            for (String r: states) {
                for (String p: states) {
                    for (String A: vns.keySet()) {
                        String Aqp = Tools.genKey(A, q, p);
                        String Aqr = Tools.genKey(A, q, r);
                        String rp = Tools.genKey(r, p);
                        int CAqp = Cost.get(Aqp);
                        int Drp = Dst.get(rp);
                        int CAqr = Cost.get(Aqr);
                        int min = Tools.min(CAqp, CAqr + Drp);
                        Cost.put(Aqp, min);
                    }
                }
            }
        }

        boolean updated = true;
        while (updated) {
            updated = false;
            for (String q: states) {
                for (String p: states) {
                    for (String A: vns.keySet()) {
                        String Aqp = Tools.genKey(A, q, p);
                        ArrayList<ArrayList<Variable>> deductions = vns.get(A).getDeductions();
                        for (ArrayList<Variable> deduction: deductions) {
                            Variable first = deduction.get(0);
                            if (first instanceof Vn && deduction.size() == 2) {
                                String B = deduction.get(0).getName();
                                String C = deduction.get(1).getName();
                                for (String r: states) {
                                    String Bqr = Tools.genKey(B, q, r);
                                    String Crp = Tools.genKey(C, r, p);
                                    int CApq = Cost.get(Aqp);
                                    int min = Tools.min(CApq, Cost.get(Bqr) + Cost.get(Crp));
                                    if (CApq > min) {
                                        Cost.put(Aqp, min);
                                        updated = true; // 如果Cost被更新
                                    }
                                }
                            } else if (first instanceof Vn && deduction.size() == 1){
                                String B = deduction.get(0).getName();
                                String Bqp = Tools.genKey(B, q, p);
                                int CApq = Cost.get(Aqp);
                                int CBqp = Cost.get(Bqp);
                                if (CApq > CBqp) {
                                    Cost.put(Aqp, CBqp);
                                    updated = true; // 如果Cost被更新
                                }
                            }
                        }
                    }
                }
            }
        }

        String S = cfg.getStart().getName();
        String s = nfa.getStart();
        LinkedHashSet<String> fList = nfa.getEnds();
        int min = Integer.MAX_VALUE;
        for (String f: fList) {
            String Ssf = Tools.genKey(S, s, f);
            if (min > Cost.get(Ssf)) {
                min = Cost.get(Ssf);
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("运行时间：" + (end - start) + "(ms)");
        System.out.println("edit distance between CFG and FA:" + min);
    }
}
