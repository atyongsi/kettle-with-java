package org.example.databaserepo;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

/**
 * Created by atyongsi@163.com on 2020/8/13
 */
public class ExecDatabaseRepo {

    /**
     * param:[]
     * return:org.pentaho.di.repository.kdr.KettleDatabaseRepository
     * description:这个主要用来连接数据库资源库.
     */

    public static KettleDatabaseRepository connectRepo() throws KettleException {
        KettleEnvironment.init();//运行环境初始化

        //创建数据库资源库对象,这里类似我们在spoon图形化界面创建资源库
        DatabaseMeta databaseMeta = new DatabaseMeta(
                "LOCAL_ORACLE_CONN",//这里是连接数据库时的名字,Database Connection的名字
                "Oracle",//使用的是Oracle数据库
                "Native(JDBC)",
                "localhost",
                "orcl",//数据库的名字,安装oracle时设置的
                "1521",
                "wys",
                "admin1000");
        //资源库元对象,名称参数,id参数,描述可以随便定义
        KettleDatabaseRepositoryMeta kettleDatabaseRepositoryMeta = new KettleDatabaseRepositoryMeta(
                "KettleDatabaseRepository",//表示资源库的类型,除了这个还有KettleFileRepository
                "LOCAL_ORACLE",//创建资源库数据库时,Display Name的名字
                "any description",
                databaseMeta);

        //创建资源库元对象,此时还是一个空对象
        KettleDatabaseRepository repository = new KettleDatabaseRepository();
        repository.init(kettleDatabaseRepositoryMeta);//加载数据库资源库的所有值
        //连接资源库,这里和spoon界面右上角的connect的作用一样的,默认账号密码admin/admin,或者guest/guest
        repository.connect("admin", "admin");

        return repository;
    }

    /**
     * param:[]
     * return:void
     * description:执行转换
     */

    public static void runTransformation() throws KettleException {

        KettleDatabaseRepository repository = connectRepo();
        //我的任务放在根目录下,从根目录下查找.Spoon界面:工具→资源库→探索资源库
        //这个目录很重要,后面加载任务,都是从这个目录下找的
        RepositoryDirectoryInterface directory = repository.findDirectory("/");

        //创建ktr元对象[transformation元对象]
        TransMeta transMeta = repository.loadTransformation("kettle-test-ktr", directory, null, true, null);
        Trans trans = new Trans(transMeta);//创建 ktr
        trans.execute(null);//执行ktr
        trans.waitUntilFinished();//等待直到数据结束
        if (trans.getErrors() > 0) {
            System.out.println("transformation error");
        } else {
            System.out.println("transformation successfully");
        }
    }

    /**
     * param:[]
     * return:void
     * description:执行Job
     */

    public static void runJob() throws KettleException {
        KettleDatabaseRepository repository = connectRepo();
        //我的任务放在根目录下,从根目录下查找.Spoon界面:工具→资源库→探索资源库
        RepositoryDirectoryInterface directory = repository.findDirectory("/");

        JobMeta jobMeta = repository.loadJob("kettle-test", directory, null, null);
        Job job = new Job(repository, jobMeta);

        job.start();
        job.waitUntilFinished();
    }

    public static void main(String[] args) throws KettleException {
        runTransformation();
        runJob();
    }
}
