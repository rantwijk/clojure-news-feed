package info.glennengstrand;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;

import org.skife.jdbi.v2.DBI;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.inject.Inject;
import com.google.inject.Provides;

import info.glennengstrand.db.ParticipantDAO;

import org.hibernate.validator.constraints.*;

import javax.validation.Valid;
import javax.validation.constraints.*;

public class NewsFeedConfiguration extends Configuration {
	
	private static final String DATABASE_PROPERTY_NAME = "database";
	private static final String CACHE_POOL_PROPERTY_NAME = "cache_pool";
	private static final String CACHE_HOST_PROPERTY_NAME = "cache_host";
	private static final String CACHE_PORT_PROPERTY_NAME = "cache_port";
	private static final String CACHE_TTL = "cache_ttl";
	private static final String CACHE_TIMEOUT_PROPERTY_NAME = "cache_timeout";
	private static final String NOSQL_HOST_PROPERTY_NAME = "nosql_host";
	private static final String NOSQL_KEYSPACE_PROPERTY_NAME = "nosql_keyspace";
	private static final String NOSQL_CONSISTENCY_LEVEL_PROPERTY_NAME = "nosql_consistency_level";
	private static final String SEARCH_HOST_PROPERTY_NAME = "search_host";
	private static final String SEARCH_PORT_PROPERTY_NAME = "search_port";
	private static final String SEARCH_INDEX_PROPERTY_NAME = "search_index";
	private static final String SEARCH_MAPPING_PROPERTY_NAME = "search_mapping";
	private static final String MESSAGE_BROKER_PROPERTY_NAME = "message_broker";
	private static final String MESSAGE_TOPIC_PROPERTY_NAME = "message_topic";
	private static final String CIRCUIT_BREAKER_ERROR_THRESHOLD = "circuit_breaker_error_threshold";
	private static final String CIRCUIT_BREAKER_SLEEP_WINDOW = "circuit_breaker_sleep_window";
	
    private DataSourceFactory database = new DataSourceFactory();
    
    private int cachePoolSize = 1;
    private String cacheHost = "localhost";
    private int cachePort = 6379;
    private int cacheTimeout = 60;
    private int cacheTimeToLive = 600;
    private String nosqlHost = "localhost";
    private String nosqlKeyspace = "activity";
    private String nosqlConsistencyLevel = "one";
    private String searchHost = "localhost";
    private int searchPort = 9200;
    private String searchIndex = "feed";
    private String searchMapping = "stories";
    private String messageBroker = "localhost";
    private String messageTopic = "feed";
    private int circuitBreakerErrorThreshold = 20;
    private int circuitBreakerSleepWindow = 5000;
	
    @JsonProperty(DATABASE_PROPERTY_NAME)
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }
    
    @JsonProperty(DATABASE_PROPERTY_NAME)
    public void setDataSourceFactory(DataSourceFactory factory) {
    	database = factory;
    }

    @JsonProperty(CACHE_POOL_PROPERTY_NAME)
	public int getCachePoolSize() {
		return cachePoolSize;
	}

    @JsonProperty(CACHE_POOL_PROPERTY_NAME)
	public void setCachePoolSize(int cachePoolSize) {
		this.cachePoolSize = cachePoolSize;
	}

	@JsonProperty(CACHE_HOST_PROPERTY_NAME)
	public String getCacheHost() {
		return cacheHost;
	}

	@JsonProperty(CACHE_HOST_PROPERTY_NAME)
	public void setCacheHost(String cacheHost) {
		this.cacheHost = cacheHost;
	}

	@JsonProperty(CACHE_PORT_PROPERTY_NAME)
	public int getCachePort() {
		return cachePort;
	}

	@JsonProperty(CACHE_PORT_PROPERTY_NAME)
	public void setCachePort(int cachePort) {
		this.cachePort = cachePort;
	}

	@JsonProperty(CACHE_TIMEOUT_PROPERTY_NAME)
	public int getCacheTimeout() {
		return cacheTimeout;
	}

	@JsonProperty(CACHE_TIMEOUT_PROPERTY_NAME)
	public void setCacheTimeout(int cacheTimeout) {
		this.cacheTimeout = cacheTimeout;
	}

	@JsonProperty(NOSQL_HOST_PROPERTY_NAME)
	public String getNosqlHost() {
		return nosqlHost;
	}

	@JsonProperty(NOSQL_HOST_PROPERTY_NAME)
	public void setNosqlHost(String nosqlHost) {
		this.nosqlHost = nosqlHost;
	}

	@JsonProperty(NOSQL_KEYSPACE_PROPERTY_NAME)
	public String getNosqlKeyspace() {
		return nosqlKeyspace;
	}

	@JsonProperty(NOSQL_KEYSPACE_PROPERTY_NAME)
	public void setNosqlKeyspace(String nosqlKeyspace) {
		this.nosqlKeyspace = nosqlKeyspace;
	}

	@JsonProperty(NOSQL_CONSISTENCY_LEVEL_PROPERTY_NAME)
	public String getNosqlConsistencyLevel() {
		return nosqlConsistencyLevel;
	}

	@JsonProperty(NOSQL_CONSISTENCY_LEVEL_PROPERTY_NAME)
	public void setNosqlConsistencyLevel(String nosqlConsistencyLevel) {
		this.nosqlConsistencyLevel = nosqlConsistencyLevel;
	}

	@JsonProperty(SEARCH_HOST_PROPERTY_NAME)
	public String getSearchHost() {
		return searchHost;
	}

	@JsonProperty(SEARCH_HOST_PROPERTY_NAME)
	public void setSearchHost(String searchHost) {
		this.searchHost = searchHost;
	}

	@JsonProperty(SEARCH_PORT_PROPERTY_NAME)
	public int getSearchPort() {
		return searchPort;
	}

	@JsonProperty(SEARCH_PORT_PROPERTY_NAME)
	public void setSearchPort(int searchPort) {
		this.searchPort = searchPort;
	}

	@JsonProperty(SEARCH_INDEX_PROPERTY_NAME)
	public String getSearchIndex() {
		return searchIndex;
	}

	@JsonProperty(SEARCH_INDEX_PROPERTY_NAME)
	public void setSearchIndex(String searchIndex) {
		this.searchIndex = searchIndex;
	}

	@JsonProperty(SEARCH_MAPPING_PROPERTY_NAME)
	public String getSearchMapping() {
		return searchMapping;
	}

	@JsonProperty(SEARCH_MAPPING_PROPERTY_NAME)
	public void setSearchMapping(String searchMapping) {
		this.searchMapping = searchMapping;
	}

	@JsonProperty(MESSAGE_BROKER_PROPERTY_NAME)
	public String getMessageBroker() {
		return messageBroker;
	}

	@JsonProperty(MESSAGE_BROKER_PROPERTY_NAME)
	public void setMessageBroker(String messageBroker) {
		this.messageBroker = messageBroker;
	}

	@JsonProperty(MESSAGE_TOPIC_PROPERTY_NAME)
	public String getMessageTopic() {
		return messageTopic;
	}

	@JsonProperty(MESSAGE_TOPIC_PROPERTY_NAME)
	public void setMessageTopic(String messageTopic) {
		this.messageTopic = messageTopic;
	}

	@JsonProperty(CIRCUIT_BREAKER_ERROR_THRESHOLD)
	public int getCircuitBreakerErrorThreshold() {
		return circuitBreakerErrorThreshold;
	}

	@JsonProperty(CIRCUIT_BREAKER_ERROR_THRESHOLD)
	public void setCircuitBreakerErrorThreshold(int circuitBreakerErrorThreshold) {
		this.circuitBreakerErrorThreshold = circuitBreakerErrorThreshold;
	}

	@JsonProperty(CIRCUIT_BREAKER_SLEEP_WINDOW)
	public int getCircuitBreakerSleepWindow() {
		return circuitBreakerSleepWindow;
	}

	@JsonProperty(CIRCUIT_BREAKER_SLEEP_WINDOW)
	public void setCircuitBreakerSleepWindow(int circuitBreakerSleepWindow) {
		this.circuitBreakerSleepWindow = circuitBreakerSleepWindow;
	}

    @JsonProperty(CACHE_TTL)
	public int getCacheTimeToLive() {
		return cacheTimeToLive;
	}

    @JsonProperty(CACHE_TTL)
	public void setCacheTimeToLive(int cacheTimeToLive) {
		this.cacheTimeToLive = cacheTimeToLive;
	}

}
