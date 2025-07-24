package com.huysor.saas.keycloak_admin.controller;

import com.huysor.saas.common.dto.res.ApiRes;
import com.huysor.saas.keycloak_admin.dto.req.user.UserReq;
import com.huysor.saas.keycloak_admin.dto.req.user.UserRoleReq;
import com.huysor.saas.keycloak_admin.dto.resp.UserResp;
import com.huysor.saas.keycloak_admin.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @Value("${custom.pageSize}")
    private int pageSize;

    @PreAuthorize("hasAuthority('ROLE_create:user') && hasAuthority('ROLE_update:user')")
    @PostMapping("/saveOrUpdate")
    public ResponseEntity<ApiRes<String>> saveOrUpdate(@RequestBody UserReq request) {
        return userService.saveOrUpdateUser(request);
    }

    @PreAuthorize("hasAuthority('ROLE_view:user')")
    @PostMapping("/allUsers")
    public ResponseEntity<ApiRes<Page<UserResp>>> list(@RequestBody Map<String, Object> req) {
        int page = (Integer) req.getOrDefault("page", 0);
        int size = (Integer) req.getOrDefault("size", pageSize);
        int offset = page > 0 ? (page - 1) * size : 0;
        return userService.getAllUsers(offset, size);
    }

    @PreAuthorize("hasAuthority('ROLE_view:user')")
    @PostMapping("{id}/detail")
    public ResponseEntity<ApiRes<UserResp>> getUserById(@PathVariable("id") Long id, HttpServletRequest request) {
        int language_id = request.getHeader("language_id") != null ? Integer.parseInt(request.getHeader("language_id")) : 1;
        return userService.getUserDetailById(id, language_id);
    }

    @PreAuthorize("hasAuthority('ROLE_create:user') && hasAuthority('ROLE_update:user')")
    public ResponseEntity <ApiRes<String>> assignUseRole(@PathVariable("id") Long id, @RequestBody UserRoleReq request) {
        return userService.assignUserRole(request);
    }


}
