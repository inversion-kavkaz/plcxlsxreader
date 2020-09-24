package ru.inversionkavkaz.plcxlsxreader.utils;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

public class HibernateUtils {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    static {
        determineAndSetTnsHome();
    }

    private static void determineAndSetTnsHome() {
        String tnsAdmin = System.getenv("TNS_ADMIN");
        if (tnsAdmin == null) {
            String oracleHome = System.getenv("ORACLE_HOME");
            if (oracleHome == null) {
                return; //failed to find any useful env variables
            }
            tnsAdmin = oracleHome + File.separatorChar + "network" + File.separatorChar + "admin";
        }
        System.setProperty("oracle.net.tns_admin", tnsAdmin);
    }

    private static SessionFactory getSessionFactory(String user, String password, String sid) {
        if (sessionFactory == null) {
            try {

                // Create registry builder
                StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Map<String, String> settings = new HashMap<>();
                settings.put(Environment.DRIVER, "oracle.jdbc.driver.OracleDriver");
                settings.put(Environment.URL, "jdbc:oracle:thin:@" + sid);
                settings.put(Environment.USER, user);
                settings.put(Environment.PASS, password);
                //settings.put(Environment.DIALECT, "org.hibernate.dialect.Oracle10gDialect");
                settings.put(Environment.DIALECT, "ru.inversionkavkaz.plcxlsxreader.utils.CustomOracleDialect");

                // Apply settings
                registryBuilder.applySettings(settings);

                // Create registry
                registry = registryBuilder.build();

                // Create MetadataSources
                MetadataSources sources = new MetadataSources(registry);//.addAnnotatedClass(XLSXType26Row.class);

                // Create Metadata
                Metadata metadata = sources.getMetadataBuilder().build();

                // Create SessionFactory
                sessionFactory = metadata.getSessionFactoryBuilder().build();

            } catch (Exception e) {
                e.printStackTrace();
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static Session getSession(String connectionStr){
        Session session = null;
        try {
            String[] userPasSid = connectionStr.split("/");
            String[] passSid = userPasSid[1].split("@");
            String user = userPasSid[0];
            String password = passSid[0];
            String sid = passSid[1];
            return HibernateUtils.getSessionFactory(user, password, sid).openSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {

        String connection = "xxi/NEW8I@odb12";

        String[] userPasSid = connection.split("/");
        String[] passSid = userPasSid[1].split("@");

        String user = userPasSid[0];
        String password = passSid[0];
        String sid = passSid[1];

        Session session = getSessionFactory(user, password, sid).openSession();
        session.beginTransaction();

        // Check database version
        String sql = "select 1 from dual";

        BigDecimal result = (BigDecimal) session.createNativeQuery(sql).getSingleResult();
        System.out.println(result);

        session.getTransaction().commit();
        session.close();
    }
}