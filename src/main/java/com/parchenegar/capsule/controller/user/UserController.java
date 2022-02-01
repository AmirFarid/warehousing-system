package com.parchenegar.capsule.controller.user;


import com.parchenegar.capsule.controller.BaseRestController;
import com.parchenegar.capsule.dto.base.Response;
import com.parchenegar.capsule.service.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/user")
@PreAuthorize("hasRole('ROLE_USER')")
public class UserController extends BaseRestController
{
    @PostMapping("")
    public ResponseEntity me()
    {
        return Response.ok(SecurityUtils.auth(), translator.get("http.ok"));
    }
}
