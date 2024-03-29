package com.academicquest.dto.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.academicquest.dto.role.RoleDTO;
import com.academicquest.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private List<RoleDTO> roles = new ArrayList<>();

    public UserDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.roles = user.getRoles().stream().map(RoleDTO::new).collect(Collectors.toList());
    }

}
