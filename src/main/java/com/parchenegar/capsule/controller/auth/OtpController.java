package com.parchenegar.capsule.controller.auth;


import com.parchenegar.capsule.common.i18n.Translator;
import com.parchenegar.capsule.domain.user.User;
import com.parchenegar.capsule.dto.base.Response;
import com.parchenegar.capsule.dto.otp.OtpGenerateRequest;
import com.parchenegar.capsule.dto.otp.OtpVerifyRequest;
import com.parchenegar.capsule.repository.user.UserRepository;
import com.parchenegar.capsule.service.auth.otp.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth/otp")
public class OtpController
{
    OtpService otpService;
    UserRepository userRepository;
    Translator translator;

    @Autowired
    OtpController(OtpService otpService, UserRepository userRepository, Translator messageByLocaleService)
    {
        this.otpService = otpService;
        this.userRepository = userRepository;
        this.translator = messageByLocaleService;
    }

    @PostMapping("/generate")
    public ResponseEntity generate(@RequestBody @Valid OtpGenerateRequest request)
    {
        Boolean isSent = otpService.generate(request.getRecipient(), request.getChannel());

        if (!isSent) {
            return Response.builder().message(translator.translate("otp.problem_on_send")).build().serverError();
        }

        return Response.builder().message(translator.translate("http.ok")).build().ok();
    }

    @PostMapping("/verify")
    public ResponseEntity verify(@RequestBody OtpVerifyRequest request)
    {
        boolean isValid = otpService.validate(request.getRecipient(), request.getOtp());

        if (!isValid) {
            return Response.builder().message(translator.translate("otp.incorrect_code")).build().with(422);
        }

        User user = userRepository.findByUsername(request.getRecipient());

        if (user == null) {
            return Response.builder().message(translator.translate("otp.user_not_found")).build().notFound();
        }

        return Response.builder().body(user).message(translator.translate("http.ok")).build().ok();
    }
}
