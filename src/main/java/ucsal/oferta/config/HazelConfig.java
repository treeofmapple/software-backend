package ucsal.oferta.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionConfig;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.InMemoryFormat;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizePolicy;
import com.hazelcast.config.NearCacheConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@EnableCaching
@Profile("cluster")
public class HazelConfig {

	@Value("${cache.cluster-name}")
	private String hazelcastName;

	@Value("${cache.address:}")
	private String hazelAddress;
	
	@Value("${cache.name.login-attempt}")
	private String cacheLoginAttemptName;

	@Value("${cache.name.profile-register}")
	private String cacheProfileRegisterAlert;

	@Value("${cache.time.login-attempt:15m}")
	private Duration loginAttemptCacheTime;

	@Value("${cache.time.profile-register:72h}")
	private Duration registerProfileCacheTime;
	
	@Bean
	HazelcastInstance hazelcastInstance() {
		if (hazelAddress == null || hazelAddress.isBlank()) {
            log.warn("No cache address provided. Starting in Embedded mode directly.");
            return createEmbeddedInstance();
        }
		
		try {
			ClientConfig config = new ClientConfig();
			config.setClusterName(hazelcastName);
			config.getNetworkConfig().addAddress(hazelAddress);
			config.getConnectionStrategyConfig().getConnectionRetryConfig().setClusterConnectTimeoutMillis(2000);
			
			NearCacheConfig nearCacheConfig = new NearCacheConfig();
			nearCacheConfig.setName("near-cache-" + cacheProfileRegisterAlert);
			nearCacheConfig.setInMemoryFormat(InMemoryFormat.OBJECT);
			nearCacheConfig.setInvalidateOnChange(true);
			nearCacheConfig.setTimeToLiveSeconds((int) registerProfileCacheTime.toSeconds());
			nearCacheConfig.setEvictionConfig(new EvictionConfig()
					.setEvictionPolicy(EvictionPolicy.LRU)
					.setMaxSizePolicy(MaxSizePolicy.ENTRY_COUNT)
					.setSize(5000));
			
			log.info("Attempt to connect on external Hazelcast at: {}", hazelAddress);
			return HazelcastClient.newHazelcastClient(config);
		} catch (Exception e) {
			log.warn("Could not connect to external Hazelcast cluster. Using Embedded mode.");
			return createEmbeddedInstance();
		}
	}

	private HazelcastInstance createEmbeddedInstance() {
		Config internalConfig = new Config();
        internalConfig.setClusterName(hazelcastName);
        
        var network = internalConfig.getNetworkConfig();
        var join = network.getJoin();
        
        join.getMulticastConfig().setEnabled(false);
        join.getTcpIpConfig().setEnabled(false);
        join.getAwsConfig().setEnabled(false);
        join.getGcpConfig().setEnabled(false);
        join.getAzureConfig().setEnabled(false);
        join.getKubernetesConfig().setEnabled(false);
        
        network.getInterfaces().setEnabled(true).addInterface("127.0.0.1");
        
		MapConfig loginConfig = new MapConfig();
		loginConfig.setName(cacheLoginAttemptName);
		loginConfig.setTimeToLiveSeconds((int) loginAttemptCacheTime.toSeconds());
		internalConfig.addMapConfig(loginConfig);

		MapConfig registerProfile = new MapConfig();
		registerProfile.setName(cacheProfileRegisterAlert);
		registerProfile.setTimeToLiveSeconds((int) registerProfileCacheTime.toSeconds());
		internalConfig.addMapConfig(registerProfile);

        return Hazelcast.newHazelcastInstance(internalConfig);
    }
	
}
