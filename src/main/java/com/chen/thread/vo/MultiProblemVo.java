package com.chen.thread.vo;

import java.util.concurrent.Future;

/**
 * 并发处理题目时 需要题目返回的结果
 *
 */
public class MultiProblemVo {

    //题目处理后的任务
    private final String problemText;

    private final Future<ProblemCacheVo> problemFuture;

    public MultiProblemVo(String problemText) {
        this.problemText = problemText;
        this.problemFuture=null;
    }

    public MultiProblemVo(Future<ProblemCacheVo> problemFuture) {
        this.problemFuture = problemFuture;
        this.problemText=null;
    }

    public String getProblemText() {
        return problemText;
    }

    public Future<ProblemCacheVo> getProblemFuture() {
        return problemFuture;
    }
}
