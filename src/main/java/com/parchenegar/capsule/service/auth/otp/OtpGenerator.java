package com.parchenegar.capsule.service.auth.otp;

import org.springframework.stereotype.Service;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


@Service
public class OtpGenerator
{

    private static final Integer EXPIRE_MIN = 5;
    private LoadingCache<String, String> otpCache;

    /**
     * Constructor configuration.
     */
    public OtpGenerator()
    {
        super();
        otpCache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MIN, TimeUnit.MINUTES)
                .build(new CacheLoader<String, String>()
                {
                    @Override
                    public String load(String s) throws Exception
                    {
                        return "0";
                    }
                });
    }

    /**
     * Method for generating OTP and put it in cache.
     *
     * @param key - cache key
     * @return cache value (generated OTP number)
     */
    public String generate(String key)
    {
        Random random = new Random();
        int OTP = 100000 + random.nextInt(900000);
        String otpStr = String.valueOf(OTP);
        otpCache.put(key, otpStr);

        return otpStr;
    }

    /**
     * Method for getting OTP value by key.
     *
     * @param key - target key
     * @return OTP value
     */
    public String getByKey(String key)
    {
        try
        {
            return otpCache.get(key);
        } catch (ExecutionException e)
        {
            return "-1";
        }
    }

    /**
     * Method for removing key from cache.
     *
     * @param key - target key
     */
    public void clearFromCache(String key)
    {
        otpCache.invalidate(key);
    }
}