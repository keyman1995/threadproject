package com.chen.thread.service.problem;

import com.chen.thread.aim.BusiMock;

import java.util.Random;

public class BaseProblemService {


    /**
     * 对题目进行处理，解析文本下载图片等工作
     *
     * @param problemId
     * @param problemSrc
     * @return
     */
    public static String makeProblem(Integer problemId, String problemSrc) {
        Random r = new Random();
        BusiMock.business(450 + r.nextInt(100));
        return "CompleteProblem[id" + problemId + "content=:" + problemSrc + "]";
    }


}
