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
public class ExecFileRepo {

    private static String ktrPath = "C:\\workDir\\ideaCode\\myGithub\\kettle-with-java\\kettle-file-repo-job\\kettle-trs.ktr";
    private static String kjbPath = "C:\\workDir\\ideaCode\\myGithub\\kettle-with-java\\kettle-file-repo-job\\kettle-job.kjb";

    public static void runTransformation(String filePath) throws KettleException, RuntimeException {
        KettleEnvironment.init();
        TransMeta transMeta = new TransMeta(filePath);
        Trans trans = new Trans(transMeta);

        trans.execute(null);
        trans.waitUntilFinished();
        if (trans.getErrors() > 0) {
            throw new RuntimeException("执行转换任务出错!!!");
        }
    }

    public static void runJob(String filePath) throws KettleException {
        KettleEnvironment.init();
        JobMeta jobMeta = new JobMeta(filePath, null);
        Job job = new Job(null, jobMeta);

        job.start();
        job.waitUntilFinished();
    }

    public static void main(String[] args) throws KettleException {

        runTransformation(ktrPath);
        runJob(kjbPath);
    }
}
