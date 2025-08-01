package com.huysor.saas.keycloak_admin.controller;

import com.huysor.saas.common.dto.res.ApiRes;
import com.huysor.saas.common.dto.res.PageRes;
import com.huysor.saas.keycloak_admin.dto.req.user.UserDelReq;
import com.huysor.saas.keycloak_admin.dto.req.user.UserFilter;
import com.huysor.saas.keycloak_admin.dto.req.user.UserReq;
import com.huysor.saas.keycloak_admin.dto.req.user.UserRoleReq;
import com.huysor.saas.keycloak_admin.dto.resp.UserRes;
import com.huysor.saas.keycloak_admin.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @Value("${custom.pageSize}")
    private int pageSize;

    @PreAuthorize("hasAuthority('ROLE_create:user') && hasAuthority('ROLE_update:user')")
    @PostMapping("/saveOrUpdate")
    public ApiRes<String> saveOrUpdate(@RequestBody UserReq request) {
        return userService.saveOrUpdateUser(request);
    }

    @PreAuthorize("hasAuthority('ROLE_view:user')")
    @PostMapping("/listAllUsers")
    public ApiRes<PageRes<List<UserRes>>> list(@RequestBody UserFilter filter) {
        var userRes = userService.getAllUsers(filter);
        return ApiRes.success(userRes);
    }

    @PreAuthorize("hasAuthority('ROLE_view:user')")
    @PostMapping("{id}/detail")
    public ApiRes<UserRes> getUserById(@PathVariable("id") Long id, @RequestHeader("language_id") Integer language_id) {
        return userService.getUserDetailById(id, language_id);
    }

    @PreAuthorize("hasAuthority('ROLE_create:user') && hasAuthority('ROLE_update:user')")
    public ApiRes<String> assignUseRole(@PathVariable("id") Long id, @RequestBody UserRoleReq request) {
        return userService.assignUserRole(request);
    }

    @PreAuthorize("hasAuthority('ROLE_delete:user)")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/delete")
    public ApiRes<String> deleteUser(@RequestBody @Valid UserDelReq req) {
        return userService.deleteUser(req);
    }


}
