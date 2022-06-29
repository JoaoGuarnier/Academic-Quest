package com.academicquest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import com.academicquest.model.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;

    private String authority;

    public RoleDTO(Role role) {
        this.id = role.getId();
        this.authority = role.getAuthority();
    }

}
