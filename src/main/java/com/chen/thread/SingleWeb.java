package com.chen.thread;

import com.chen.thread.aim.MakeSrcDoc;
import com.chen.thread.aim.ProblemBank;
import com.chen.thread.service.DocService;
import com.chen.thread.vo.PendingDocVo;

import java.util.List;

/**
 * Hello world!
 */
public class SingleWeb {
    public static void main(String[] args) {
        System.out.println("题库开始初始化....................");
        ProblemBank.initBank();
        System.out.println("题库初始化完成");
        List<PendingDocVo> docVoList = MakeSrcDoc.makeDoc(2);
        long startTotal = System.currentTimeMillis();
        for (PendingDocVo doc : docVoList) {
            System.out.println("开始处理文档：" + doc.getDocName() + "............");
            long start = System.currentTimeMillis();
            String localName = DocService.makeDoc(doc);
            System.out.println("文档" + localName + "生成耗时：" + (System.currentTimeMillis() - start) + "ms");
            String remoteUrl = DocService.upLoadDoc(localName);
            System.out.println("已上传至[" + remoteUrl + "]耗时：" + (System.currentTimeMillis() - start) + "ms");
        }
        System.out.println("共耗时：" + (System.currentTimeMillis() - startTotal));
    }
}
