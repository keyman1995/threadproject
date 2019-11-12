package com.chen.thread.aim;

import com.chen.thread.vo.ProblemDBVo;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.chen.thread.aim.EncryptTools.EncryptBySHA1;

public class ProblemBank {

    //题库数据存储
    private static ConcurrentHashMap<Integer, ProblemDBVo> problemBankMap = new ConcurrentHashMap<>();

    //定时任务吃，负责定时更新题库数据
    private static ScheduledExecutorService updateProblemBank = new ScheduledThreadPoolExecutor(1);

    public static void initBank() {
        for (int i = 0; i < Consts.PROBLEM_COUNT; i++) {
            String problemContent = getRandomString(700);
            problemBankMap.put(i, new ProblemDBVo(i, problemContent, EncryptBySHA1(problemContent)));
        }
        updateProblemTimer();
    }

    //生成随机字符串
    //length表示生成字符串的长度
    private static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static ProblemDBVo getProblem(int i) {
        BusiMock.business(20);
        return problemBankMap.get(i);
    }

    public static String gerProblemSha(int i) {
        BusiMock.business(10);
        return problemBankMap.get(i).getSha();
    }

    //更新题库的定时任务
    private static class UpdateProblem implements Runnable {
        @Override
        public void run() {
            Random random = new Random();
            int problemId = random.nextInt(Consts.PROBLEM_COUNT);
            System.out.println("更新了题目id ProblemId......." + problemId);
            String probleContent = getRandomString(700);
            problemBankMap.put(problemId, new ProblemDBVo(problemId, probleContent, EncryptBySHA1(probleContent)));
        }
    }

    //定时更新题库数据
    public static void updateProblemTimer() {
        System.out.println("开始定时更新题库..........................");
        updateProblemBank.scheduleAtFixedRate(new UpdateProblem(), 15, 5, TimeUnit.SECONDS);
    }

}
