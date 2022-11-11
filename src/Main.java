// -*- coding: utf-8 -*-
// @Time    : 2022/11/10 23:02
// @Author  : LuJiuxi
// @File    : Main.java
// @Software: IntelliJ IDEA 
// @Comment :

import javax.tools.Tool;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class Main {
    public static void main(String[] args) {
        CFG cfg = Tools.initCFGFromFile("input.txt");
        NFA nfa = Tools.initNFAFromFile("input.txt");
        LinkedHashMap<String, Integer> C = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> D = Tools.floydWarshall(nfa);
        LinkedHashMap<String, String> nfaGraph = nfa.getNfa();
        LinkedHashSet<String> states = nfa.getStates();
        LinkedHashMap<String, Vn> vns = cfg.getVns();
        // 初始化C
        for (String q: states) {
            for (String p: states) {
                for (String A: vns.keySet()) {
                    C.put(Tools.genKey(A, q, p), Integer.MAX_VALUE >> 2);
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
                        C.put(Tools.genKey(keyA, keyQP), min);
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
                        int CAqp = C.get(Aqp);
                        int Dqr = D.get(qr);
                        int CArp = C.get(Arp);
                        int min = Tools.min(CAqp, Dqr + CArp);
                        C.put(Aqp, min);
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
                        int CAqp = C.get(Aqp);
                        int Drp = D.get(rp);
                        int CAqr = C.get(Aqr);
                        int min = Tools.min(CAqp, CAqr + Drp);
                        C.put(Aqp, min);
                    }
                }
            }
        }

        // TODO: 2022/11/11  
    }
}
