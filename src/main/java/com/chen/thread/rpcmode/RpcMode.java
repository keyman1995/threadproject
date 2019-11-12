package com.chen.thread.rpcmode;

import com.chen.thread.aim.Consts;
import com.chen.thread.aim.MakeSrcDoc;
import com.chen.thread.aim.ProblemBank;
import com.chen.thread.service.DocService;
import com.chen.thread.vo.PendingDocVo;

import java.util.List;
import java.util.concurrent.*;

public class RpcMode {

    //生成文档的服务
    private static ExecutorService dockMakeService = Executors.newFixedThreadPool(Consts.THREAD_COUNT_BASE * 2);
    //上传文档的服务
    private static ExecutorService upLoadService = Executors.newFixedThreadPool(Consts.THREAD_COUNT_BASE * 2);
    //文档完成completionService
    private static CompletionService docCompletionService = new ExecutorCompletionService(dockMakeService);
    //文档上传的completionService
    private static CompletionService upLoadCompletionService = new ExecutorCompletionService(upLoadService);


    public static class MakeDocTask implements Callable<String> {
        private PendingDocVo pendingDocVo;

        public MakeDocTask(PendingDocVo pendingDocVo) {
            this.pendingDocVo = pendingDocVo;
        }

        @Override
        public String call() throws Exception {
            System.out.println("开始处理文档：" + pendingDocVo.getDocName() + "............");
            long start = System.currentTimeMillis();
            String localName = DocService.makeDocSync(pendingDocVo);
            System.out.println("文档" + localName + "生成耗时：" + (System.currentTimeMillis() - start) + "ms");
            return localName;
        }
    }

    public static class UploadDocTask implements Callable<String> {
        private String docName;

        public UploadDocTask(String docName) {
            this.docName = docName;
        }

        @Override
        public String call() throws Exception {
            System.out.println("开始上传文档：" + docName + "............");
            long start = System.currentTimeMillis();
            String remoteUrl = DocService.upLoadDoc(docName);
            System.out.println("已上传至[" + remoteUrl + "]耗时：" + (System.currentTimeMillis() - start) + "ms");
            return remoteUrl;
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("题库开始初始化....................");
        ProblemBank.initBank();
        System.out.println("题库初始化完成");
        List<PendingDocVo> docVoList = MakeSrcDoc.makeDoc(10);
        long startTotal = System.currentTimeMillis();
        for (PendingDocVo doc : docVoList) {
            docCompletionService.submit(new MakeDocTask(doc));
        }
        for (PendingDocVo doc : docVoList) {
            Future<String> futureLocalName = docCompletionService.take();
            upLoadCompletionService.submit(new UploadDocTask(futureLocalName.get()));
        }

        for(PendingDocVo docVo : docVoList){
            upLoadCompletionService.take().get();
        }
        System.out.println("共耗时："+(System.currentTimeMillis()-startTotal)+"ms");

    }

}
