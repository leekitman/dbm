package org.onetwo.common.base;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.After;
import org.onetwo.common.base.DbmBaseTest.DbmBaseTestInnerContextConfig;
import org.onetwo.common.dbm.PackageInfo;
import org.onetwo.common.log.JFishLoggerFactory;
import org.onetwo.common.spring.cache.JFishSimpleCacheManagerImpl;
import org.onetwo.common.spring.config.JFishProfile;
import org.onetwo.common.spring.test.SpringBaseJUnitTestCase;
import org.onetwo.dbm.mapping.DbmConfig;
import org.onetwo.dbm.mapping.DefaultDbmConfig;
import org.onetwo.dbm.spring.EnableDbm;
import org.onetwo.dbm.stat.SqlExecutedStatis;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@ActiveProfiles({ "dev" })
//@ContextConfiguration(value="classpath:/applicationContext-test.xml")
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes=DbmBaseTestInnerContextConfig.class)
//@Rollback(false)
public class DbmBaseTest extends SpringBaseJUnitTestCase {
	protected Logger logger = JFishLoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SqlExecutedStatis sqlExecutedStatis;
	
	@Configuration
	@JFishProfile
	@ImportResource("classpath:conf/applicationContext-test.xml")
	@EnableDbm(value="dataSource", packagesToScan="org.onetwo.common.dbm")
	@ComponentScan(basePackageClasses=PackageInfo.class)
	public static class DbmBaseTestInnerContextConfig {

		@Resource
		private DataSource dataSource;
		@Bean
		public CacheManager cacheManager() {
			JFishSimpleCacheManagerImpl cache = new JFishSimpleCacheManagerImpl();
			return cache;
		}
		
		@Bean
		public DbmConfig dbmConfig(){
			return new DefaultDbmConfig();
		}
		
	}
	
	@After
	public void afterTest(){
		String info = sqlExecutedStatis.toFormatedString();
		logger.info(info);
	}
}
