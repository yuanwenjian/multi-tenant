# 该项目通过Mybatis插件与Mycat注解实现多租户方案

项目原理为登录时将租户信息放入SecurityContextHolder,开发Mybatis插件,为Sql添加Mycat注解即(/*!mycat:datanode=tenant*/) ,Mycat根据注解查询真实物理数据库,
数据库底层设计为每个租户创建单独的数据库,且数据库名名称与租户名称一样


数据库连接配置与平常类似,只是连接换成Mycat连接

MycatInterceptor mybatis插件 拦截sql,动态添加注解,租户从UserAuthentication获取

mybatis-config 配置MycatInterceptor 
 
PhoneImeiResource login()方法为模拟登陆,设置租户,真实环境可以配置filter,设置UserAuthentication

Mycat schema.xml 配置
```xml
<?xml version="1.0"?>
<!DOCTYPE mycat:schema SYSTEM "schema.dtd">
<mycat:schema xmlns:mycat="http://io.mycat/">

	<schema name="TESTDB" checkSQLschema="true" sqlMaxLimit="100" randomDataNode="dn1">
		<!-- auto sharding by id (long) -->
		<table name="wms_phone_imei"  dataNode="dn1,dn2,dn3" />
	</schema>
		/> -->
	<dataNode name="dn1" dataHost="localhost1" database="user" />
	<dataNode name="dn2" dataHost="localhost2" database="user" />
	<dataNode name="dn3" dataHost="localhost3" database="phone" />
	<dataHost name="localhost1" maxCon="1000" minCon="10" balance="0"
			  writeType="0" dbType="mysql" dbDriver="native" switchType="1"  slaveThreshold="100">
		<heartbeat>select user()</heartbeat>
		<!-- can have multi write hosts -->
		<writeHost host="db001" url="127.0.0.1:3306" user="root" password="12345">
		</writeHost>
		<!-- <writeHost host="hostM2" url="localhost:3316" user="root" password="123456"/> -->
	</dataHost>
	<dataHost name="localhost2" maxCon="1000" minCon="10" balance="0"
			  writeType="0" dbType="mysql" dbDriver="native" switchType="1"  slaveThreshold="100">
		<heartbeat>select user()</heartbeat>
		<!-- can have multi write hosts -->
		<writeHost host="db002" url="127.0.0.1:3307" user="root"
				   password="12345">
		</writeHost>
		<!-- <writeHost host="hostM2" url="localhost:3316" user="root" password="123456"/> -->
	</dataHost>

	<dataHost name="localhost3" maxCon="1000" minCon="10" balance="0"
			  writeType="0" dbType="mysql" dbDriver="native" switchType="1"  slaveThreshold="100">
		<heartbeat>select user()</heartbeat>
		<!-- can have multi write hosts -->
		<writeHost host="db002" url="127.0.0.1:3308" user="root"
				   password="12345">
		</writeHost>
		<!-- <writeHost host="hostM2" url="localhost:3316" user="root" password="123456"/> -->
	</dataHost>
	
</mycat:schema>
```

真实环境可以配置中心服务并且配置一中心数据库,记录所有用户的名称,密码及租户,登陆请求走中心服务,验证用户及获取租户,再把租户信息生成token,放入请求头,
业务项目进行解析token,获取租户,这样可以实现多租户方案