package org.example.filerepo;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.filerep.KettleFileRepository;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

/**
 * Created by atyongsi@163.com on 2020/8/12
 */
public class ExecTrans {
    public static void runTransformation() {
        String fileName = "C:\\workDir\\kettleWork\\File_repository\\清洗一部分数据.ktr";
        try {
            KettleEnvironment.init();

            TransMeta transMeta = new TransMeta(fileName);
            Trans trans = new Trans(transMeta);

            trans.execute(null);
//            trans.waitUntilFinished();
            if (trans.getErrors() > 0) {
                throw new RuntimeException("执行转换任务出错!!!");
            }
        } catch (KettleException e) {
            e.printStackTrace();
        }

    }

    public static void runJob() throws KettleException {
        String fileName = "C:\\workDir\\kettleWork\\File_repository\\kettle-test.kjb";

        JobMeta jobMeta = null;
        try {
            KettleEnvironment.init();
            jobMeta = new JobMeta(fileName, null);
        } catch (KettleException e) {
            e.printStackTrace();
        }

        KettleFileRepository fileRepository = new KettleFileRepository();
        RepositoryDirectoryInterface fileRepositoryDirectory = fileRepository.findDirectory("C:\\workDir\\kettleWork\\File_repository");
        Job job = new Job((Repository) fileRepositoryDirectory, jobMeta);
        job.start();
        job.waitUntilFinished();
    }


    public static void main(String[] args) throws RuntimeException {
//        runTransformation();
        try {
            runJob();
        } catch (KettleException e) {
            e.printStackTrace();
        }
    }
}
