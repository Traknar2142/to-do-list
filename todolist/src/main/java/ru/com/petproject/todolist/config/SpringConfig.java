package ru.com.petproject.todolist.config;

import java.util.Properties;

import javax.sql.DataSource;
import javax.transaction.TransactionManager;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.scheduling.config.Task;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import ru.com.petproject.todolist.dao.TaskDao;
import ru.com.petproject.todolist.dao.TaskDaoImpl;
import ru.com.petproject.todolist.service.TaskService;
import ru.com.petproject.todolist.service.TaskServiceImpl;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("ru.com.petproject.todolist")
@EnableTransactionManagement
@EnableWebMvc
public class SpringConfig {
    @Value("${driver}")
    String driver;

    @Value("${url}")
    String url;

    @Value("${user}")
    String user;

    @Value("${password}")
    String password;

    @Value("${schema}")
    String schema;

    private final ApplicationContext applicationContext;

    @Autowired
    public SpringConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public InternalResourceViewResolver templateResolver() {
        InternalResourceViewResolver templateResolver = new InternalResourceViewResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".jsp");
        return templateResolver;
    }

    @Bean
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public SessionFactory getLocalSessionFactoryBean() {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.show_sql", true);
        localSessionFactoryBean.setDataSource(getDataSource());
        localSessionFactoryBean.setAnnotatedClasses(Task.class);
        localSessionFactoryBean.setHibernateProperties(properties);

        return localSessionFactoryBean.getObject();
    }

    @Bean
    public TaskDao getTaskDao() {
        TaskDaoImpl taskDao = new TaskDaoImpl();
        taskDao.setSessionFactory(getLocalSessionFactoryBean());
        return taskDao;
    }

    @Bean
    public TaskService getTaskService() {
        TaskServiceImpl taskService = new TaskServiceImpl();
        taskService.setTaskDao(getTaskDao());
        return taskService;
    }
    
    @Bean
    public TransactionManager getTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(getLocalSessionFactoryBean());
        return (TransactionManager) transactionManager;
    }
}
