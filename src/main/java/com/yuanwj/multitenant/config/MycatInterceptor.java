package com.yuanwj.multitenant.config;

import com.yuanwj.multitenant.security.UserAuthentication;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;

@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class MycatInterceptor implements Interceptor {

    private static final String preState="/*!mycat:datanode=";
    private static final String afterState="*/";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        //获取到原始sql语句
        String sql = boundSql.getSql();
        String tenant = UserAuthentication.getUserAuthentication().getTenant();
        String newSql = preState + tenant + afterState + sql;

        //通过反射修改sql语句
        Field field = boundSql.getClass().getDeclaredField("sql");
        field.setAccessible(true);
        field.set(boundSql, newSql);

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);

    }

    @Override
    public void setProperties(Properties properties) {

    }
}
