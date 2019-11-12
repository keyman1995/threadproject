package com.chen.thread.aim;

import com.chen.thread.vo.PendingDocVo;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MakeSrcDoc {


    /**
     * 形成待处理文档
     *
     * @param docCount
     * @return
     */
    public static List<PendingDocVo> makeDoc(int docCount) {
        Random r = new Random();
        Random rProbleCount = new Random();
        List<PendingDocVo> docVoList = new LinkedList<>();//文档列表
        for (int i = 0; i < docCount; i++) {
            List<Integer> problemList = new LinkedList<Integer>();
            int docProblemCount = rProbleCount.nextInt(60) + 60;

            for (int j = 0; j < docProblemCount; j++) {
                int problemId = r.nextInt(Consts.PROBLEM_COUNT);
                System.out.println("生成的题目id" + problemId);
                problemList.add(problemId);
            }
            PendingDocVo pendingDocVo = new PendingDocVo("pending_" + i, problemList);
            docVoList.add(pendingDocVo);
        }

        return docVoList;
    }

}
