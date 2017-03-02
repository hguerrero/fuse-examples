Apache Camel Based Log Collector 
=========================================

This route opens a file stream to read the data grid server log and stores the parsed messages in the infinispan cache. You need to start first the data grid on client-server mode using the standard ports and a configured cache named logs.

To build this project use

    mvn install

To run the project you can execute the following Maven goal

    mvn camel:run

For more help see the blog post

    https://hguerreroo.wordpress.com/2017/02/28/implementing-a-log-collector-using-jboss-fuse-and-jboss-data-grid/
