package com.chen.thread.vo;

import java.util.List;

//处理生成文档的实体类
public class PendingDocVo {

    private final String docName;

    private final List<Integer> problemVoList;

    public PendingDocVo(String docName, List<Integer> problemVoList) {
        this.docName = docName;
        this.problemVoList = problemVoList;
    }

    public String getDocName() {
        return docName;
    }

    public List<Integer> getProblemVoList() {
        return problemVoList;
    }
}
