package org.onetwo.common.db.spi;

import java.util.List;
import java.util.Map;

import org.onetwo.common.db.filequery.ParsedSqlUtils;
import org.onetwo.common.db.sqlext.ExtQueryUtils;
import org.onetwo.common.propconf.JFishProperties;
import org.onetwo.common.propconf.ResourceAdapter;
import org.onetwo.common.utils.LangUtils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

/****
 * 对应每个sql文件里的单个命名查询
 * @author way
 *
 */
public class NamedQueryInfo implements Cloneable{
	public static final char DOT_KEY = '.';
	public static final char UNDERLINE_KEY = '_';
	
	
	public static final String COUNT_POSTFIX = "-count";
	public static final String FRAGMENT_KEY = "fragment";
//	public static final String MATCHER_KEY = "matcher";
//	public static final String MATCHER_SPIT_KEY = "|";
	public static final String PROPERTY_KEY = "property";
	public static final String NAME_KEY = "name";
	public static final String ALIAS_KEY = "alias";
	/***
	 * fragment.
	 */
	public static final String FRAGMENT_DOT_KEY = FRAGMENT_KEY + DOT_KEY;

	public static boolean isCountName(String name){
		return name.endsWith(COUNT_POSTFIX);
	}
	public static String trimCountPostfix(String name){
		if(!isCountName(name))
			return name;
		return name.substring(0, name.length() - COUNT_POSTFIX.length());
	}


	private String name;
	private String value;
	
	private NamedQueryFile dbmNamedQueryFile;
	private String namespace;
	private JFishProperties config;
	private ResourceAdapter<?> srcfile;
	
	
//	private DataBase dataBaseType;
//	private String mappedEntity;
	private String countSql;
	private FileSqlParserType parserType = FileSqlParserType.TEMPLATE;
	
	
	private boolean autoGeneratedCountSql = true;
	
	private Map<String, String> fragment = LangUtils.newHashMap();
	private List<String> aliasList = ImmutableList.of();
//	private List<Object> matchers = ImmutableList.of();

	private boolean nativeSql = true;
	
	private QueryConfigData queryConfig = ParsedSqlUtils.EMPTY_CONFIG;

	public String getNamespace() {
		return namespace;
	}

	public String getFullName(){
		return getFullName(getName());
	}

	public String getFullName(String name){
		if(dbmNamedQueryFile.isGlobal())
			return name;
		return namespace+"."+name;
	}
	
	public ResourceAdapter<?> getSrcfile() {
		return srcfile;
	}

	public void setSrcfile(ResourceAdapter<?> srcfile) {
		this.srcfile = srcfile;
	}

	public JFishProperties getConfig() {
		return config;
	}

	public void setConfig(JFishProperties config) {
		this.config = config;
	}

	public NamedQueryFile getDbmNamedQueryFile() {
		return dbmNamedQueryFile;
	}

	public void setDbmNamedQueryFile(NamedQueryFile namespaceInfo) {
		this.dbmNamedQueryFile = namespaceInfo;
		this.namespace = namespaceInfo.getNamespace();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getSql() {
		return getValue();
	}
	
	public String getCountName(){
		return getFullName() + COUNT_POSTFIX;
	}

	public void setSql(String sql) {
		this.setValue(sql);
	}

	/*public String getMappedEntity() {
		return mappedEntity;
	}

	public Class<?> getMappedEntityClass() {
		return mappedEntityClass;
	}

	public void setMappedEntity(String mappedEntity) {
		this.mappedEntity = mappedEntity;
		if(StringUtils.isNotBlank(mappedEntity)){
			this.mappedEntityClass = ReflectUtils.loadClass(mappedEntity);
		}
	}*/

	public String getCountSql() {
		if(!autoGeneratedCountSql){
			return countSql;
		}else{
//			throw new BaseException("countSql is null, and you shoud generated it by sql.");
			return ExtQueryUtils.buildCountSql(getSql(), null);
		}
	}

	/***
	 * only for toString
	 * @return
	 */
	protected String getCountSqlString() {
		if(!autoGeneratedCountSql){
			return countSql;
		}else{
			return "";
		}
	}

	public boolean isAutoGeneratedCountSql() {
		return autoGeneratedCountSql;
	}
	/*public String getCountSql2() {
		if(StringUtils.isBlank(countSql)){
			this.countSql = ExtQueryUtils.buildCountSql(this.getSql(), "");
		}
		return countSql;
	}*/

	public void setCountSql(String countSql) {
		autoGeneratedCountSql = false;
		this.countSql = countSql;
	}

	public boolean isIgnoreNull() {
		return parserType==FileSqlParserType.IGNORENULL;
	}

	
	public FileSqlParserType getParserType() {
		return parserType;
	}

	public void setParserType(FileSqlParserType parserType) {
		this.parserType = parserType;
	}
	public void setParser(String parser) {
		this.parserType = FileSqlParserType.valueOf(parser.trim().toUpperCase());
	}
	
/*
	public boolean isNeedParseSql(){
		return isIgnoreNull();
	}*/

	public boolean isNativeSql() {
		return nativeSql;
	}
	public void setNativeSql(boolean nativeSql) {
		this.nativeSql = nativeSql;
	}
	public List<String> getAliasList() {
		return aliasList;
	}
	public void setAliasList(List<String> aliasList) {
		this.aliasList = aliasList;
	}
	public Map<String, String> getFragment() {
		return fragment;
	}
	public QueryConfigData getQueryConfig() {
		return queryConfig;
	}
	public void setQueryConfig(QueryConfigData queryConfig) {
		this.queryConfig = queryConfig;
	}
	/***
	 * fullName.fragment.attrName
	 * @param attr
	 * @return
	 */
	public String getFragmentTemplateName(String attr){
		return getFullName() + DOT_KEY + FRAGMENT_DOT_KEY + attr;
	}
	
	public String toString() {
		return LangUtils.append("{namespace:, ", getNamespace(), ", name:", getName(), ", config:", getConfig(), ", sql:", getSql(), ", countSql:", getCountSqlString(), "}");
	}
	
	@Override
	public NamedQueryInfo clone() throws CloneNotSupportedException {
		NamedQueryInfo prop = new NamedQueryInfo();
		this.cloneProperties(prop);
		return prop;
	}

	protected void cloneProperties(NamedQueryInfo prop) {
//		super.cloneProperties(prop);

		prop.name = name;
		prop.value = value;
		prop.namespace = namespace;
		prop.dbmNamedQueryFile = dbmNamedQueryFile;
		prop.srcfile = srcfile;
		prop.config = new JFishProperties(config);
		
//		prop.mappedEntity = mappedEntity;
		prop.autoGeneratedCountSql = autoGeneratedCountSql;
		prop.countSql = countSql;
		prop.fragment = Maps.newHashMap(fragment);
		prop.nativeSql = nativeSql;
		prop.parserType = parserType;
		prop.queryConfig = queryConfig;
	}
}
