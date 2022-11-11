// -*- coding: utf-8 -*-
// @Time    : 2022/9/14 13:07
// @Author  : LuJiuxi
// @File    : io.MyFileWriter.java
// @Software: IntelliJ IDEA 
// @Comment : 文件写入

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MyFileWriter {
    private FileWriter fileWriter;

    public MyFileWriter(String filePath) {
        File file = new File(filePath);
        try {
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void write(String str) {
        try {
            fileWriter.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
