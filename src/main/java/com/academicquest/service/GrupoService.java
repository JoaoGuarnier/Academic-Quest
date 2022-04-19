package com.academicquest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.academicquest.dto.GrupoDTO;
import com.academicquest.dto.GrupoMateriaDTO;
import com.academicquest.dto.GrupoPostDTO;
import com.academicquest.dto.UserDTO;
import com.academicquest.model.Grupo;
import com.academicquest.model.Materia;
import com.academicquest.model.User;
import com.academicquest.repository.GrupoRepository;
import com.academicquest.repository.MateriaRepository;
import com.academicquest.repository.UserRepository;

@Service
public class GrupoService {
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MateriaRepository materiaRepository;
	
	
	
	@Transactional
	public Boolean save(GrupoPostDTO dto) {
		
		try {
			Grupo grupo = convertToEntity(dto);
			grupoRepository.save(grupo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public List<GrupoMateriaDTO> getByMateriaId(Long id) {
		List<Grupo> grupoList = grupoRepository.findByMateriaId(id);
		return grupoList.stream().map(GrupoMateriaDTO::new).collect(Collectors.toList());
	}
	
	public GrupoDTO getById(Long id) {
		
		GrupoDTO grupoDTO = new GrupoDTO();
		
		Optional<Grupo> grupoOptional = grupoRepository.findById(id);
		Grupo grupo = grupoOptional.orElseThrow(() -> new EntityNotFoundException("Grupo não encontrado"));
		
		grupoDTO.setId(grupo.getId());
		grupoDTO.setNome(grupo.getNome());
		grupoDTO.setAlunoLiderId(grupo.getIdUserLider());
		grupoDTO.setMateriaId(grupo.getMateria().getId());
		List<UserDTO> userDTOsList = grupo.getAlunos().stream().map(UserDTO::new).collect(Collectors.toList());
		grupoDTO.setAlunos(userDTOsList);
		
		return grupoDTO;
		
		
	}
	

	private Grupo convertToEntity(GrupoPostDTO dto) {
		
		Grupo grupo = new Grupo();
		
		List<User> alunos = new ArrayList<>();
		
		dto.getAlunosId().stream().forEach( userId -> {
			User user = userRepository.getById(userId);
			alunos.add(user);
		});
		
		Materia materia = materiaRepository.getById(dto.getMateriaId());
		
		grupo.setNome(dto.getNome());
		grupo.setAlunos(alunos);
		grupo.setMateria(materia);
		grupo.setIdUserLider(dto.getAlunoLiderId());
		
		return grupo;
		
	} 

}
