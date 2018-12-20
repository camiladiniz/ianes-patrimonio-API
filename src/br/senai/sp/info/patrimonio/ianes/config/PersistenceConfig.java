package br.senai.sp.info.patrimonio.ianes.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
//Determina que o spring (através da integração com o Hibernate) vai iniciar as transações automaticamente
@EnableTransactionManagement
/**
 * Classe responsável pela persistência dos dados.
 * @author Camila Diniz
 *
 */
public class PersistenceConfig {

	@Bean
	public DataSource getDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/ianespatrimonio?serverTimezone=UTC");
		dataSource.setUsername("root");
		dataSource.setPassword("root132");
		
		return dataSource;
	}
	
	public Properties getHibernateProperties() {
		Properties props = new Properties();
		
		props.setProperty("hibernate.show_sql", "true");
		props.setProperty("hibernate.hbm2ddl.auto", "update");
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		
		return props;
	}
	
	@Bean
	public LocalSessionFactoryBean getSessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(getDataSource());
		sessionFactory.setHibernateProperties(getHibernateProperties());
		sessionFactory.setPackagesToScan("br.senai.sp.info.patrimonio.ianes.models");
		
		return sessionFactory;
	}
	
	/**
	 * Abre as transações
	 * @return
	 */
	@Bean
	@Autowired
	public HibernateTransactionManager getTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(getSessionFactory().getObject());
		
		return transactionManager;
	}
	
}
