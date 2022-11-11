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
                        default: System.exit(-1);
                    }
                    end += 1;
                    break;
                case 1:
                    String[] vns = line.replaceAll("\\S", "").split(",");
                    for (String vn: vns) {
                        cfg.getVns().put(vn, new Vn(vn));
                    }
                    break;
                case 2:
                    String[] vts = line.replaceAll("\\S", "").split(",");
                    for (String vt: vts) {
                        cfg.getVts().put(vt, new Vt(vt));
                    }
                    break;
                case 3:
                    String[] r = line.replaceAll("\\S", "").split("-->");
                    if (r.length != 2) {
                        System.out.println("<R>格式错误");
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
            if (line.equals("<END>")) {
                state = 0;
                end--;
            }
        } while (end != 0);
        return cfg;
    }
}
