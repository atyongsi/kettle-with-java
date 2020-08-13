kettle 任务的启动方式有若干种:
windows环境下通过Start设置定时任务,而一般情况下,
大部分的任务跑在linux服务器上,通过./kitchen.sh执行 .kjb文件

kettle 项目存储的方式有三种:文件资源库/数据库资源库/自定义配置
前两种可以方便的跑在win或者linux系统上.第三种需要安装插件,一般不用

该项目是java和kettle集成,完成transformation和job.
注意:国内镜像源不能下载kettle的核心jar包,需要手动下载,
然后执行:mvn install:install-file \
-Dfile=C:\Users\wys\Desktop\kettle-engine-7.1.0.0-12.jar \
-DgroupId=pentaho-kettle \
-DartifactId=kettle-engine \
-Dversion=7.1.0.0-12 \
-Dpackaging=jar
