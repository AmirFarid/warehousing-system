package com.parchenegar.capsule.service.auth.otp;

import com.parchenegar.capsule.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class OtpService
{
    private final Logger LOGGER = LoggerFactory.getLogger(OtpService.class);

    UserRepository userRepository;
    OtpGenerator otpGenerator;

    @Autowired
    OtpService(OtpGenerator otpGenerator, UserRepository userRepository)
    {
        this.otpGenerator = otpGenerator;
        this.userRepository = userRepository;
    }

    /**
     * Method for generate OTP number
     *
     * @param recipient - provided key (username in this case)
     * @return boolean value (true|false)
     */
    public Boolean generate(String recipient, String channel)
    {
        // generate otp
        String otpValue = otpGenerator.generate(recipient);
        if (otpValue == "-1")
        {
            LOGGER.error("OTP generator is not working...");
            return false;
        }

        LOGGER.info("Generated OTP: {}", otpValue);

        //TODO: send sms


        return true;
    }

    /**
     * Method for validating provided OTP
     *
     * @param key       - provided key
     * @param otpNumber - provided OTP number
     * @return boolean value (true|false)
     */
    public Boolean validate(String key, String otpNumber)
    {
        // get OTP from cache
        String cacheOTP = otpGenerator.getByKey(key);
        if (cacheOTP.equals(otpNumber))
        {
            otpGenerator.clearFromCache(key);
            return true;
        }
        return false;
    }
}

