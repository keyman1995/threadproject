package com.chen.thread.vo;

public class ProblemCacheVo {

    private String processedContent;

    private String problemSha;

    public ProblemCacheVo() {
    }

    public ProblemCacheVo(String processedContent, String problemSha) {
        this.processedContent = processedContent;
        this.problemSha = problemSha;
    }

    public void setProcessedContent(String processedContent) {
        this.processedContent = processedContent;
    }

    public void setProblemSha(String problemSha) {
        this.problemSha = problemSha;
    }

    public String getProcessedContent() {
        return processedContent;
    }

    public String getProblemSha() {
        return problemSha;
    }
}
