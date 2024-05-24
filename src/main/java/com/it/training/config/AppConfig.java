package com.it.training.config;

import javax.sql.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.logging.stdout.StdOutImpl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Component
@PropertySource("jdbc.properties")
public class AppConfig {
    @Value("${jdbc.url}")
    String jdbcUrl;

    @Value("${jdbc.username}")
    String jdbcUsername;

    @Value("${jdbc.password}")
    String jdbcPassword;

    @Value("${jdbc.dirver}")
    String jdbcDriver;

    @Bean
    DataSource createDataSource(
            // JDBC URL:
            @Value("${jdbc.url}") String jdbcUrl,
            // JDBC username:
            @Value("${jdbc.username}") String jdbcUsername,
            // JDBC password:
            @Value("${jdbc.password}") String jdbcPassword) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(jdbcUsername);
        config.setPassword(jdbcPassword);
        config.addDataSourceProperty("autoCommit", "false");
        config.addDataSourceProperty("connectionTimeout", "5");
        config.addDataSourceProperty("idleTimeout", "60");
        return new HikariDataSource(config);
    }

    @Bean
    SqlSessionFactoryBean createSqlSessionFactoryBean(@Autowired DataSource dataSource) {
        try {
            var sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(dataSource);
            Configuration conf = new Configuration();
            conf.setLogImpl(StdOutImpl.class);
            sqlSessionFactoryBean.setConfiguration(conf);
            return sqlSessionFactoryBean;
        } catch (Throwable e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Bean
    PlatformTransactionManager createTxManager(@Autowired DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
