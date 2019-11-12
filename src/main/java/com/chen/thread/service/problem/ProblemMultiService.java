package com.chen.thread.service.problem;

import com.chen.thread.aim.Consts;
import com.chen.thread.aim.ProblemBank;
import com.chen.thread.vo.MultiProblemVo;
import com.chen.thread.vo.ProblemCacheVo;
import com.chen.thread.vo.ProblemDBVo;

import java.util.concurrent.*;

public class ProblemMultiService {

    //存放处理过的缓存
    private static ConcurrentHashMap<Integer, ProblemCacheVo> problemCach
            = new ConcurrentHashMap<>();
    //存放正在处理的题目的缓存
    private static ConcurrentHashMap<Integer, Future<ProblemCacheVo>> processingCachVo
            = new ConcurrentHashMap<>();
    //线程池的线程数
    private static ExecutorService makeProbleExec =
            Executors.newFixedThreadPool(Consts.THREAD_COUNT_BASE);

    public static MultiProblemVo makeProblemService(Integer problemId) {
        ProblemCacheVo problemCacheVo = problemCach.get(problemId);
        if (problemCacheVo == null) {
            System.out.println("题目【" + problemId + "】不存在需要新起任务");
            return new MultiProblemVo(getProblemFuture(problemId));
        } else {
            //拿摘要
            String problemSha = ProblemBank.gerProblemSha(problemId);
            if (problemCacheVo.getProblemSha().equals(problemSha)) {
                System.out.println("题目【" + problemId + "】在缓存中已存在并且没有被修改可以直接使用");
                return new MultiProblemVo(problemCacheVo.getProcessedContent());
            } else {
                System.out.println("题目【" + problemId + "】在缓存中存在，但是被修改过，需要重新生成新的任务");
                return new MultiProblemVo(getProblemFuture(problemId));
            }
        }
    }

    //返回题目的工作任务
    public static Future<ProblemCacheVo> getProblemFuture(Integer problemId) {
        Future<ProblemCacheVo> problemFuture = processingCachVo.get(problemId);
        if (problemFuture == null) {
            ProblemDBVo problemDBVo = ProblemBank.getProblem(problemId);
            ProblemTask problemTask = new ProblemTask(problemDBVo, problemId);
            FutureTask<ProblemCacheVo> ft = new FutureTask<>(problemTask);
            problemFuture = processingCachVo.putIfAbsent(problemId, ft);
            if (problemFuture == null) {
                //表示没有别的线程正在处理当前的题目
                problemFuture = ft;
                 makeProbleExec.submit(ft);
                System.out.println("题目【" + problemId + "】计算任务启动，请稍等");
            } else {
                System.out.println("其他线程启动了题目【" + problemId + "】的计算任务，该任务不必开启");
            }
        }
        return problemFuture;
    }


    //处理题目的任务 将数据库的文本题目
    private static class ProblemTask implements Callable<ProblemCacheVo> {

        private ProblemDBVo problemDBVo;
        private Integer problemId;

        public ProblemTask(ProblemDBVo problemDBVo, Integer problemId) {
            this.problemDBVo = problemDBVo;
            this.problemId = problemId;
        }

        @Override
        public ProblemCacheVo call() throws Exception {
            try {
                ProblemCacheVo problemCacheVo = new ProblemCacheVo();
                problemCacheVo.setProcessedContent(BaseProblemService.makeProblem(problemId, problemDBVo.getContent()));
                problemCacheVo.setProblemSha(problemDBVo.getSha());
                problemCach.put(problemId, problemCacheVo);
                return problemCacheVo;
            } finally {
                //需要将该处理该题目的map中移除。
                processingCachVo.remove(problemId);
            }
        }
    }

}
