package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private static final String URL = "jdbc:mysql://localhost:3306/user?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "u1727578l";

    public static Connection getMySQLConnection() {

        Connection connection = null;

        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }


    public static SessionFactory getSessionFactory() {

        SessionFactory sessionFactory = null;


        try {
            Configuration configuration = new Configuration();

            Properties properties = new Properties();
            //Environment - Предоставляет доступ к информации о конфигурации, передаваемой в объект Properties
            properties.put(Environment.DRIVER, DRIVER);
            properties.put(Environment.URL, URL);
            properties.put(Environment.USER, USERNAME);
            properties.put(Environment.PASS, PASSWORD);
            //для связи Hibernate с БД
            properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");

            //SHOW_SQL Включить запись сгенерированного SQL в консоль
            properties.put(Environment.SHOW_SQL, "true");

            //CURRENT_SESSION_CONTEXT_CLASS Определение контекста для SessionFactory.getCurrentSession()обработки.
            properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

            //HBM2DDL_AUTO Автоматический экспорт / обновление схемы с помощью инструмента hbm2ddl.
            properties.put(Environment.HBM2DDL_AUTO, "update");

            configuration.setProperties(properties);
            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sessionFactory;
    }

}

