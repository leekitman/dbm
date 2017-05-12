package org.onetwo.dbm.spring;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.onetwo.common.db.spi.QueryProvideManager;
import org.springframework.context.annotation.Import;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({DynamicQueryObjectRegisterConfigration.class})
public @interface EnableDbmRepository {
	
	Class<? extends QueryProvideManager> defaultQueryProvideManagerClass() default QueryProvideManager.class;
}
