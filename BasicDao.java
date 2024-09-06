package Dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.jdbcUtilByDruid;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BasicDao<T>{
    QueryRunner queryRunner = new QueryRunner();
    public int update(String sql, Object... params) throws SQLException, IOException,ClassNotFoundException{
        Connection connection= jdbcUtilByDruid.getConnection();
        return queryRunner.update(connection, sql, params);
    }

    //返回多个对象（多行）任意表,sql语句，Class对象(反射)（User.class） param(?)
    public List<T> queryMulti(String sql,Class<T> clazz,Object... params)throws SQLException, IOException,
            ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        Connection connection=jdbcUtilByDruid.getConnection();//rs-->List
            return queryRunner.query(connection, sql, new BeanListHandler<T>(clazz), params);
    }

    //查询单行结果的通用方法
    public T querySingle(String sql,Class<T> clazz,Object... params) throws SQLException, IOException,
            NoSuchFieldException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Connection connection=jdbcUtilByDruid.getConnection();
        return queryRunner.query(connection, sql, new BeanHandler<T>(clazz), params);
    }

    //查询单行单列的方法（返回单值）
    public  Object queryScalar(String sql,Object... params) throws SQLException, IOException,
            ClassNotFoundException{
        Connection connection=jdbcUtilByDruid.getConnection();
            Object query = queryRunner.query(connection, sql, new ScalarHandler(), params);
            return query;
    }


}
