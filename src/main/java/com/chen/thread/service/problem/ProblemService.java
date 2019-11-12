package com.chen.thread.service.problem;

import com.chen.thread.aim.ProblemBank;

public class ProblemService {


    /**
     * 普通对题目进行处理
     *
     * @param problemId 题目id
     * @return 题目解析后的文本
     */
    public static String makeProblem(Integer problemId) {
        return BaseProblemService.makeProblem(problemId,
                ProblemBank.getProblem(problemId).getContent());
    }

}
