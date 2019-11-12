package com.chen.thread.service;

import com.chen.thread.aim.BusiMock;
import com.chen.thread.aim.MakeSrcDoc;
import com.chen.thread.aim.ProblemBank;
import com.chen.thread.service.problem.ProblemMultiService;
import com.chen.thread.service.problem.ProblemService;
import com.chen.thread.vo.MultiProblemVo;
import com.chen.thread.vo.PendingDocVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class DocService {

    /**
     * 上传文档到网络
     *
     * @param docFileName 实际文档在本地的存储位置
     * @return 上传后的网络存储地址
     */
    public static String upLoadDoc(String docFileName) {
        Random r = new Random();
        BusiMock.business(5000 + r.nextInt(400));
        return "http://www.xxxx.com/file/upload/" + docFileName;
    }

    /**
     * 将待处理文档处理为本地实际文档
     *
     * @param pendingDocVo 待处理文档
     * @return 实际文档在本地的存储位置
     */
    public static String makeDoc(PendingDocVo pendingDocVo) {
        System.out.println("开始处理文档：" + pendingDocVo.getDocName());
        StringBuffer sb = new StringBuffer();
        for (Integer problemId : pendingDocVo.getProblemVoList()) {
            sb.append(ProblemService.makeProblem(problemId));
        }
        return "complete_" + System.currentTimeMillis() + "_"
                + pendingDocVo.getDocName() + ".pdf";
    }

    public static String makeDocSync(PendingDocVo pendingDocVo) throws ExecutionException, InterruptedException {
        System.out.println("开始处理文档：" + pendingDocVo.getDocName());
        StringBuffer sb = new StringBuffer();
        Map<Integer,MultiProblemVo> resultMap = new HashMap<>();
        for(Integer problemId : pendingDocVo.getProblemVoList()){
            resultMap.put(problemId, ProblemMultiService.makeProblemService(problemId));
        }

        for(Integer problemId : pendingDocVo.getProblemVoList()){
            MultiProblemVo multiProblemVo = resultMap.get(problemId);
            sb.append(multiProblemVo.getProblemText()==null?multiProblemVo.getProblemFuture().get().getProcessedContent():multiProblemVo.getProblemText());
        }
        return "complete_" + System.currentTimeMillis() + "_"
                + pendingDocVo.getDocName() + ".pdf";
    }
}
