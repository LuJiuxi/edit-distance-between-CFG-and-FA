// -*- coding: utf-8 -*-
// @Time    : 2022/11/11 8:28
// @Author  : LuJiuxi
// @File    : Tools.java
// @Software: IntelliJ IDEA 
// @Comment :

import java.util.ArrayList;

public class Tools {
    public static CFG initCFGFromFile(String path) {
        MyFileReader myFileReader = new MyFileReader(path);
        CFG cfg = new CFG();
        String line = "";
        while (!line.equals("<CFG>")) {
            line = myFileReader.readLine();
        }
        int end = 1;
        int state = 0;
        line = myFileReader.readLine();
        do {
            switch (state) {
                case 0:
                    switch (line) {
                        case "<VN>": state = 1; break;
                        case "<VT>": state = 2;break;
                        case "<R>": state = 3; break;
                        case "<S>": state = 4; break;
                        default:
                            System.out.println("CFG未定义的成员: " + line.trim());
                            System.exit(-1);
                    }
                    end += 1;
                    break;
                case 1:
                    String[] vns = line.replaceAll("\\s", "").split(",");
                    for (String vn: vns) {
                        cfg.getVns().put(vn, new Vn(vn));
                    }
                    break;
                case 2:
                    String[] vts = line.replaceAll("\\s", "").split(",");
                    for (String vt: vts) {
                        cfg.getVts().put(vt, new Vt(vt));
                    }
                    break;
                case 3:
                    String[] r = line.replaceAll("\\s", "").split("-->");
                    if (r.length != 2) {
                        System.out.println("<R>格式错误: " + line.trim());
                        System.exit(-1);
                    }
                    Vn vn = cfg.getVns().get(r[0]);
                    ArrayList<Variable> deduction = new ArrayList<>();
                    char[] chars = r[1].toCharArray();
                    for (char c: chars) {
                        deduction.add(cfg.getVariable(String.valueOf(c)));
                    }
                    vn.addDeduction(deduction);
                    break;
                case 4:
                    cfg.setStart(line.trim());
                    break;
            }
            line = myFileReader.readLine();
            while (line.equals("<END>")) {
                state = 0;
                end--;
                line = myFileReader.readLine();
            }
        } while (end != 0);
        return cfg;
    }

    public static NFA initNFAFromFile(String path) {
        MyFileReader myFileReader = new MyFileReader(path);
        NFA nfa = new NFA();
        String line = "";
        while (!line.equals("<NFA>")) {
            line = myFileReader.readLine();
        }
        int end = 1;
        int state = 0;
        line = myFileReader.readLine();
        do {
            switch (state) {
                case 0:
                    switch (line) {
                        case "<STATE>": state = 1; break;
                        case "<VT>": state = 2;break;
                        case "<STF>": state = 3; break;
                        case "<START>": state = 4; break;
                        case "<FINAL>": state = 5; break;
                        default:
                            System.out.println("NFA未定义的成员: " + line.trim());
                            System.exit(-1);
                    }
                    end += 1;
                    break;
                case 1:
                    String[] nfaStates = line.replaceAll("\\s", "").split(",");
                    for (String nfaState: nfaStates) {
                        nfa.getStates().put(nfaState, new State(nfaState));
                    }
                    break;
                case 2:
                    String[] vts = line.replaceAll("\\s", "").split(",");
                    for (String vt: vts) {
                        nfa.getVts().put(vt, new Vt(vt));
                    }
                    break;
                case 3:
                    String[] stf = line.replaceAll("\\s", "").split("=");
                    if (stf.length != 2) {
                        System.out.println("<STF>格式错误: " + line.trim());
                        System.exit(-1);
                    }
                    String[] input = stf[0].substring(1, stf[0].length() - 1).split(",");
                    String beginState = input[0];
                    String inputStr = input[1];
                    String[] output = stf[1].substring(1, stf[1].length() - 1).split(",");
                    for (String endState: output) {
                        Edge edge = new Edge(nfa.getStates().get(beginState),
                                inputStr, nfa.getStates().get(endState));
                        nfa.getStates().get(beginState).getEdges().add(edge);
                    }
                    break;
                case 4:
                    nfa.setStartState(nfa.getStates().get(line.trim()));
                    break;
                case 5:
                    String[] finalStates = line.replaceAll("\\s", "").split(",");
                    for (String finalState: finalStates) {
                        if (nfa.getStates().containsKey(finalState)) {
                            nfa.getFinalStates().add(nfa.getStates().get(finalState));
                        } else {
                            System.out.println("NFA未定义的状态: " + finalState);
                            System.exit(-1);
                        }
                    }
                    break;
            }
            line = myFileReader.readLine();
            while (line.equals("<END>")) {
                state = 0;
                end--;
                line = myFileReader.readLine();
            }
        } while (end != 0);
        return nfa;
    }
}
