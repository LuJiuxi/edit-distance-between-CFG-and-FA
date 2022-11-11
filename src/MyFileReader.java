// -*- coding: utf-8 -*-
// @Time    : 2022/9/14 12:46
// @Author  : LuJiuxi
// @File    : io.MyFileReader.java
// @Software: IntelliJ IDEA 
// @Comment : 文件读取

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;

public class MyFileReader {
    private final PushbackReader pushbackReader;

    public MyFileReader(String filePath) {
        File file = new File(filePath);
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert fileReader != null;
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        this.pushbackReader = new PushbackReader(bufferedReader);
    }

    /**
     * @description: 获取一个字符
     * @return: int
     * @author: LuJiuxi
     * @date: 2022/9/13 22:06
     */
    public int getChar() {
        int c = -1;
        try {
            c = pushbackReader.read();
        } catch (IOException e) {
            System.out.println("io.MyFileReader: getChar()IO异常");
            System.exit(0);
        }
        return c;
    }

    /**
     * @description: 回退一个字符
     * @param c: int
     * @return: void
     * @author: LuJiuxi
     * @date: 2022/9/13 22:02
     */
    public void retract(int c) {
        try {
            pushbackReader.unread(c);
        } catch (IOException e) {
            System.out.println("io.MyFileReader: retract()IO异常");
            System.exit(0);
        }
    }

    public String readLine() {
        StringBuilder stringBuilder = new StringBuilder();
        int c = getChar();
        if (c == -1) {
            return "";
        }
        while (c != '\n') {
            if (c != '\r') {
                stringBuilder.append((char) c);
            }
            c = getChar();
            if (c == -1) {
                break;
            }
        }
        return stringBuilder.toString();
    }
}
