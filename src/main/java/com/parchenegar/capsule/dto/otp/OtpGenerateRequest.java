package com.parchenegar.capsule.dto.otp;

import lombok.*;

import javax.validation.constraints.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OtpGenerateRequest
{
    @NotBlank
    @Pattern(regexp = "\\d{11}")
    @Pattern(regexp = "(09)(\\d{2})(\\d{7})")
    String recipient;
    String channel;
}
