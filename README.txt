kettle 任务的启动方式有若干种:
windows环境下通过Start设置定时任务,而一般情况下,
大部分的任务跑在linux服务器上,通过./kitchen.sh执行 .kjb文件

kettle 项目存储的方式有三种:文件资源库/数据库资源库/自定义配置
前两种可以方便的跑在win或者linux系统上.第三种需要安装插件,一般不用

文件资源库:作业在本地配置好,上传至linux任务目录下

该项目是java和kettle集成,完成transformation和job