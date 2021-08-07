package com.mm.util.gen.controller;

import com.mm.util.gen.metadata.DatabaseFactory;
import com.mm.util.gen.metadata.TypeConverter;
import com.mm.util.gen.utils.ApplicationUtils;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.DatabaseMetaData;
import java.util.HashMap;
import java.util.Map;

/**
 * @类名: 下拉框信息Controller
 * @描述: 下拉框信息
 * @作者: humian
 * @日期: 2021-03-19 22:03:45
 */
@CrossOrigin
@RestController
@RequestMapping("selector")
public class SelectorController {

    public static Map<String, DatabaseFactory> dbMap = new HashMap<>();

    public static Map<String, JdbcTemplate> jtMap = new HashMap<>();

    @RequestMapping("addConnet")
    public Object addConnect(String url, String driver, String username, String password) throws Exception {
        String token = "conn:" + System.currentTimeMillis();
        url = "jdbc:mysql://" + url + "?useUnicode=true&characterEncoding=UTF8&useSSL=false";
        /*url = "jdbc:mysql://sh-cdb-amrvlomi.sql.tencentcdb.com:60217/wechat_interaction?useUnicode=true&characterEncoding=UTF8&useSSL=false";
        driver = "com.mysql.jdbc.Driver";
        username = "root";
        password = "#weilongtian";*/
        try {
            PooledDataSource dataSource = new PooledDataSource();
            dataSource.setDriver(driver);
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(java.net.URLDecoder.decode(password, "UTF-8"));
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath*:mybatis/mapper/**/*.xml");
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setMapperLocations(resources);
            sqlSessionFactoryBean.setDataSource(dataSource);
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
            SqlSession sqlsession = sqlSessionFactory.openSession();
            DatabaseFactory databaseFactory = new DatabaseFactory();
            databaseFactory.setSqlSession(sqlsession);
            TypeConverter bean = ApplicationUtils.application.getBean(TypeConverter.class);
            databaseFactory.setTypeConverter(bean);
            dbMap.put(token, databaseFactory);

            DriverManagerDataSource jdbcDataSource = new DriverManagerDataSource();
            jdbcDataSource.setDriverClassName(driver);
            jdbcDataSource.setUrl(url);
            jdbcDataSource.setUsername(username);
            jdbcDataSource.setPassword(password);
            JdbcTemplate j = new JdbcTemplate();
            j.setDataSource(jdbcDataSource);
            jtMap.put(token, j);

            DatabaseMetaData metaData = j.getDataSource().getConnection().getMetaData();
            if (metaData == null) {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
        return token;
    }

}