package com.poc.event.rest.cache;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.poc.event.model.User;

@Component
public class EventAppCacheInterface {

	LoadingCache<String, User> cache = null;

	@Value("${cache.cache_size}")
	private String cacheSize;
	@Value("${cache.expiry_interval}")
	private String expireInterval;
	@Value("${cache.refresh_interval}")
	private String refreshInterval;

	@PostConstruct
	public void initialize() {

		cache = CacheBuilder
				.newBuilder()
				.maximumSize(Integer.parseInt(cacheSize))
				.expireAfterAccess(Long.parseLong(expireInterval),
						TimeUnit.SECONDS)
				.refreshAfterWrite(Long.parseLong(refreshInterval),
						TimeUnit.SECONDS)
				.build(new CacheLoader<String, User>() {
					@Override
					public User load(String key) throws Exception {
						 /*
						  * At present just for demo purpose returning a dummy value. 
						  * Indeed it will have a call to let say some persistance layer like DB to fetch 
						  * te data for the key and 
						  * cache it as per the cache definition above
						  */
						return fetchValueFromPersistance(key);
					}

					private User fetchValueFromPersistance(String key) {
						
						return User.getMockUser(key);
					}
				});

	}
	
	public User getUserFromCache(String key)
	{
		return cache.getUnchecked(key);
	}

}
