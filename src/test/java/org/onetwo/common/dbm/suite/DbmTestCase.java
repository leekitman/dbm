package org.onetwo.common.dbm.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.onetwo.common.base.DbmSessionCacheTest;
import org.onetwo.common.dbm.BaseCrudEntityManagerTest;
import org.onetwo.common.dbm.BatchInsertTest;
import org.onetwo.common.dbm.CustomDaoTest;
import org.onetwo.common.dbm.DbmDaoTest;
import org.onetwo.common.dbm.DbmEntityManagerTest;
import org.onetwo.common.dbm.DbmNestedMappingTest;
import org.onetwo.common.dbm.JsonFieldTest;
import org.onetwo.common.dbm.QueryConfigTest;
import org.onetwo.common.dbm.TransactionalListenerTest;
import org.onetwo.common.dbm.UserDbmIdEntityTest;
import org.onetwo.common.dbm.UserTableIdEntityTest;

@RunWith(Suite.class)
@SuiteClasses({
	DbmDaoTest.class,
	DbmEntityManagerTest.class,
//	DBCheckerTest.class,
//	OneBatchInsertTest.class,
	BatchInsertTest.class,
	BaseCrudEntityManagerTest.class,
	DbmNestedMappingTest.class,
	TransactionalListenerTest.class,
	DbmSessionCacheTest.class,
	UserTableIdEntityTest.class,
	UserDbmIdEntityTest.class,
	QueryConfigTest.class,
	CustomDaoTest.class,
	JsonFieldTest.class
//	RichModelTest.class
})
public class DbmTestCase {

}
