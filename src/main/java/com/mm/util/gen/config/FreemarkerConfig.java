package com.mm.util.gen.config;

import com.mm.util.gen.freemarker.CodeService;
import com.mm.util.gen.metadata.DatabaseFactory;
import com.mm.util.gen.metadata.TypeConverter;
import com.mm.util.gen.task.EasyuiEntityTaskNode;
import com.mm.util.gen.task.EasyuiPageTaskNode;
import com.mm.util.gen.task.EasyuiServiceTaskNode;
import com.mm.util.gen.task.TaskNode;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import java.util.*;

@Configuration
public class FreemarkerConfig {

    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer f = new FreeMarkerConfigurer();
        f.setTemplateLoaderPath("classpath:ftl");
        Properties p = new Properties();
        p.setProperty("template_update_delay", "0");
        p.setProperty("default_encoding", "UTF-8");
        p.setProperty("number_format", "0.##########");
        p.setProperty("datetime_format", "yyyy-MM-dd HH:mm:ss");
        p.setProperty("classic_compatible", "true");
        p.setProperty("template_exception_handler", "ignore");
        f.setFreemarkerSettings(p);
        freeMarkerConfigurer = f;
        return f;
    }

    @Bean
    @DependsOn("freeMarkerConfigurer")
    public CodeService codeService(@Lazy EasyuiEntityTaskNode easyuiEntityTaskNode, @Lazy EasyuiServiceTaskNode easyuiServiceTaskNode, @Lazy EasyuiPageTaskNode easyuiPageTaskNode) {
        Map<String, List<TaskNode>> taskNodeMap = new HashMap<>();
        List<TaskNode> list = Arrays.asList(easyuiEntityTaskNode, easyuiServiceTaskNode, easyuiPageTaskNode);
        taskNodeMap.put("easyui", list);
        CodeService c = new CodeService();
        c.setFreeMarkerConfig(freeMarkerConfigurer);
        c.setTaskNodeMap(taskNodeMap);
        return c;
    }

    @Bean
    public TypeConverter typeConverter() {
        Map<String, String> s = new HashMap<>();
        s.put("Long-INTEGER", "BIGINT,INT,INTEGER,SMALLINT,TINYINT");
        s.put("java.math.BigDecimal-DECIMAL", "DOBULE,FLOAT,DECIMAL,NUMERIC");
        s.put("String-VARCHAR", "VARCHAR,NVARCHAR,LONGTEXT,TEXT,CHAR");
        s.put("java.util.Date-TIMESTAMP", "DATETIME,DATE,TIME,TIMESTAMP");
        s.put("boolean-BOOLEAN", "BOOLEAN");
        TypeConverter t = new TypeConverter(s);
        return t;
    }

    /*@Bean
    public DatabaseFactory databaseFactory(SqlSession sqlSession, TypeConverter typeConverter) {
        DatabaseFactory d = new DatabaseFactory();
        d.setSqlSession(sqlSession);
        d.setTypeConverter(typeConverter);
        return d;
    }*/

}
