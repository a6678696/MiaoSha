package com.ledao;

import cn.hutool.core.io.file.FileAppender;

import java.io.File;

/**
 * @author LeDao
 * @company
 * @create 2022-04-02 7:29
 */
public class Test {

    public static void main(String[] args) {
        File file = new File("C:\\Users\\LeDao\\Desktop\\1.txt");
        FileAppender fileAppender = new FileAppender(file, 16, true);
        for (int i = 1; i <= 10; i++) {
            String content = "内容" + i;
            fileAppender.append(content);
        }
        fileAppender.flush();
    }
}
