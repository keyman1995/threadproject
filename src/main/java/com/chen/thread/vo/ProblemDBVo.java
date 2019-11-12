package com.chen.thread.vo;

public class ProblemDBVo {

    //题目的id
    private final int problemId;

    //题目的内容
    private final String content;

    //题目的SHA串
    private final String sha;

    public ProblemDBVo(int problemId, String content, String sha) {
        this.problemId = problemId;
        this.content = content;
        this.sha = sha;
    }

    public int getProblemId() {
        return problemId;
    }

    public String getContent() {
        return content;
    }

    public String getSha() {
        return sha;
    }
}
