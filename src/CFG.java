// -*- coding: utf-8 -*-
// @Time    : 2022/11/10 23:01
// @Author  : LuJiuxi
// @File    : CFG.java
// @Software: IntelliJ IDEA 
// @Comment :


import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
// 伪乔姆斯基范式存储
public class CFG {
    private Vn start = new Vn("@NULL");
    private final LinkedHashMap<String, Vn> vns = new LinkedHashMap<>();
    private final LinkedHashMap<String, Vt> vts = new LinkedHashMap<>();

    public LinkedHashMap<String, Vn> getVns() {
        return vns;
    }

    public LinkedHashMap<String, Vt> getVts() {
        return vts;
    }

    public Variable getVariable(String name) {
        if (vns.containsKey(name)) {
            return vns.get(name);
        } else if (vts.containsKey(name)) {
            return vts.get(name);
        } else {
            System.out.println("未定义的符号" + name);
            System.exit(-1);
        }
        return null;
    }

    public void setStart(String start) {
        this.start = vns.get(start);
    }

    public Vn getStart() {
        return start;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("VN:\n").append(vns.keySet()).append('\n');
        stringBuilder.append("VT:\n").append(vts.keySet()).append('\n');
        stringBuilder.append("R:\n");
        for (String key: vns.keySet()) {
            stringBuilder.append(vns.get(key).toString());
        }
        stringBuilder.append("S:\n").append(start.getName());
        return stringBuilder.toString();
    }
}
