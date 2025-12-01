package com.chinhan.bookingroom.service;

import com.chinhan.bookingroom.dto.request.RoleRequest;
import com.chinhan.bookingroom.dto.response.RoleResponse;
import com.chinhan.bookingroom.mapper.RoleMapper;
import com.chinhan.bookingroom.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public RoleResponse createRole(RoleRequest request){
        var role = roleMapper.toRole(request);

        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<RoleResponse> getAllRole(){
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteRole(String role){
        roleRepository.deleteById(role);
    }
}
