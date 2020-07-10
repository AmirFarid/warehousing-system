package com.parchenegar.capsule.dto.otp;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OtpVerifyRequest
{
    String recipient;
    String otp;
}
