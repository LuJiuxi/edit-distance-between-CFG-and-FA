// -*- coding: utf-8 -*-
// @Time    : 2022/11/11 8:28
// @Author  : LuJiuxi
// @File    : Tools.java
// @Software: IntelliJ IDEA 
// @Comment :

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class Tools {
    /**
     * @description: 从文件初始化CFG
     * @param path:
     * @return: CFG
     * @author: LuJiuxi
     * @date: 2022/11/13 15:45
     */
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
        //一次读入一行
        do {
            switch (state) {
                case 0:
                    switch (line) {
                        case "<VN>": state = 1; break;
                        case "<VT>": state = 2;break;
                        case "<R>": state = 3; break;
                        case "<START>": state = 4; break;
                        default:
                            System.out.println("CFG未定义的成员: " + line.trim());
                            System.exit(-1);
                    }
                    end += 1;
                    break;
                case 1: // <VN>
                    String[] vns = line.replaceAll("\\s", "").split(",");
                    for (String vn: vns) {
                        cfg.getVns().put(vn.substring(1,vn.length()-1), new Vn(vn.substring(1,vn.length()-1)));
                    }
                    break;
                case 2: // <VT>
                    String[] vts = line.replaceAll("\\s", "").split(",");
                    for (String vt: vts) {
                        cfg.getVts().put(vt, new Vt(vt));
                    }
                    break;
                case 3: // <R>
                    String[] r = line.replaceAll("\\s", "").split("-->");
                    if (r.length != 2) {
                        System.out.println("<R>格式错误: " + line.trim());
                        System.exit(-1);
                    }
                    Vn vn = cfg.getVns().get(r[0].substring(1,r[0].length() - 1));
                    ArrayList<Variable> deduction = new ArrayList<>();
                    String[] right = r[1].replaceAll("<","").split(">");
                    if (right.length > 2) {
                        System.out.println("CFG不符合范式: " + line.trim());
                        System.exit(-1);
                    }
                    for(String str: right) {
                        deduction.add(cfg.getVariable(str));
                    }
                    vn.addDeduction(deduction);
                    break;
                case 4: // <START>
                    String start = line.replaceAll("\\s", "");
                    cfg.setStart(start.substring(1, start.length() - 1));
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

    /**
     * @description: 从文件初始化NFA
     * @param path:
     * @return: NFA
     * @author: LuJiuxi
     * @date: 2022/11/13 15:44
     */
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
        // 一次循环读入1行
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
                case 1:  // <STATE>
                    String[] nfaStates = line.replaceAll("\\s", "").split(",");
                    for (String nfaState: nfaStates) {
                        nfa.getStates().add(nfaState);
                    }
                    break;
                case 2:  // <VT>
                    String[] vts = line.replaceAll("\\s", "").split(",");
                    for (String vt: vts) {
                        nfa.getVts().add(vt);
                    }
                    break;
                case 3:  // <STF>
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
                        if (!nfa.getVts().contains(inputStr)) {
                            System.out.println("<STF>未定义的终结符: " + line.trim());
                            System.exit(-1);
                        }
                        nfa.getNfa().put(genKey(beginState, endState), inputStr);
                    }
                    break;
                case 4:
                    nfa.setStart(line.trim());
                    break;
                case 5:
                    String[] finalStates = line.replaceAll("\\s", "").split(",");
                    for (String finalState: finalStates) {
                        nfa.getEnds().add(finalState);
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
        for(String s : nfa.getStates()) {
            nfa.getNfa().put(genKey(s,s), "");
        }
        return nfa;
    }

    public static int cost(String str0, String str1) {
        int n = str0.length();
        int m = str1.length();
        char[] s = (" " + str0).toCharArray();
        char[] t = (" " + str1).toCharArray();
        int[][] d = new int[n + 1][m + 1];
        for(int i = 0; i <= m;i++) d[0][i] = i;
        for(int i =0; i <= n;i++) d[i][0] = i;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int c = 0;
                if (s[i] != t[j]) c = 1;
                int replace = d[i - 1][j - 1] + c;
                int delete = d[i - 1][j] + 1;
                int insert = d[i][j - 1] + 1;
                int min = min(replace, delete, insert);
                if (replace == min) {
                    d[i][j] = replace;
                } else if (insert == min) {
                    d[i][j] = insert;
                } else {
                    d[i][j] = delete;
                }
            }
        }
        return d[n][m];
    }

    public static LinkedHashMap<String, Integer> floydWarshall(NFA nfa) {
        LinkedHashMap<String, Integer> d = new LinkedHashMap<>();
        LinkedHashSet<String> states = nfa.getStates();
        LinkedHashMap<String, String> graph = nfa.getNfa();
        for (String si: states) {
            for (String sj: states) {
                String key = genKey(si, sj);
                if (graph.containsKey(key)) {
                    d.put(key, graph.get(key).length());
                } else {
                    if (si.equals(sj))
                        d.put(key, 0);
                    else
                        d.put(key, Integer.MAX_VALUE >> 2);
                }
            }
        }
        for (String sk: states) {
            for (String si: states) {
                for (String sj: states) {
                    String sik = genKey(si, sk);
                    String skj = genKey(sk, sj);
                    String sij = genKey(si, sj);
                    if (d.get(sik) + d.get(skj) < d.get(sij)) {
                        d.put(sij, d.get(sik) + d.get(skj));
                    }
                }
            }
        }
        return d;
    }

    public static int min(int... nums) {
        int min = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (min > nums[i]) min = nums[i];
        }
        return min;
    }

    public static String genKey(String q, String p) {
        return q + '@' + p;
    }

    public static String genKey(String A, String q, String p) {
        return A + '@' + q + '@' + p;
    }

}
