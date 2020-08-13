package org.example.databaserepo;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

/**
 * Created by atyongsi@163.com on 2020/8/13
 */
public class ExecJob {

    public static void runWithDb() throws KettleException {
        KettleEnvironment.init();
        //创建DB资源库
        KettleDatabaseRepository repository = new KettleDatabaseRepository();
        DatabaseMeta databaseMeta = new DatabaseMeta("orcl", "Oracle", "jdbc", "localhost", "test20200812", "1521", "test20200812", "test20200812");
        //选择资源库
        KettleDatabaseRepositoryMeta kettleDatabaseRepositoryMeta = new KettleDatabaseRepositoryMeta("LOCAL_ORACLE", "orcl", "Transformation description", databaseMeta);
        repository.init(kettleDatabaseRepositoryMeta);
        //连接资源库,这里和kettle右上角的connect的作用一样的,默认账号密码admin/admin
        repository.connect("admin", "admin");
        RepositoryDirectoryInterface directoryInterface = repository.loadRepositoryDirectoryTree();
        //选择转换
        TransMeta transMeta = repository.loadTransformation("demo1", directoryInterface, null, true, null);
        Trans trans = new Trans(transMeta);
        trans.execute(null);
        trans.waitUntilFinished();//等待直到数据结束
        if (trans.getErrors() > 0) {
            System.out.println("transformation error");
        } else {
            System.out.println("transformation successfully");
        }
    }

    public static void main(String[] args) {
        try {
            runWithDb();
        } catch (KettleException e) {
            e.printStackTrace();
        }
    }
}
