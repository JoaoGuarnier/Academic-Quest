package com.academicquest.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.academicquest.dto.GrupoDTO;
import com.academicquest.dto.GrupoMateriaDTO;
import com.academicquest.dto.GrupoPostDTO;
import com.academicquest.dto.GrupoUpdateDTO;
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
	
	
	
	@Transactional()
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
	
	@Transactional(readOnly = true)
	public GrupoDTO getById(Long id) {
		
		GrupoDTO grupoDTO = new GrupoDTO();
		
		Optional<Grupo> grupoOptional = grupoRepository.findById(id);
		Grupo grupo = grupoOptional.orElseThrow(() -> new EntityNotFoundException("Grupo não encontrado"));
		
		grupoDTO.setId(grupo.getId());
		grupoDTO.setNome(grupo.getNome());
		grupoDTO.setAlunoLiderId(grupo.getAlunoLider().getId());
		grupoDTO.setMateriaId(grupo.getMateria().getId());
		List<UserDTO> userDTOsList = grupo.getAlunos().stream().map(UserDTO::new).collect(Collectors.toList());
		grupoDTO.setAlunos(userDTOsList);
		
		return grupoDTO;
		
		
	}
	
	@Transactional(readOnly = true)
	public List<UserDTO> buscarAlunosSemGrupo(Long id) {
		
		List<Long> buscaAlunosTurma = grupoRepository.buscaAlunosMateria(id);
		List<Long> buscaAlunosComGrupoMateria = grupoRepository.buscaAlunosComGrupoMateria(id);
		List<Long> alunosSemGrupo = new ArrayList<>();
		
		buscaAlunosTurma.stream().forEach(i -> {
			if (!buscaAlunosComGrupoMateria.contains(i)) {
				alunosSemGrupo.add(i);
			}
		});
		
		List<User> findByIdIn = userRepository.findByIdIn(alunosSemGrupo);
		List<UserDTO> users = findByIdIn.stream().map(UserDTO::new).collect(Collectors.toList());
		
		return users;
		
	}
	
	
	@Transactional
	public GrupoUpdateDTO updateGrupo(GrupoUpdateDTO grupoUpdateDTO, Long id) {
		
		Grupo grupo = grupoRepository.getById(id);
		User alunoLider = userRepository.getById(grupoUpdateDTO.getIdAlunoLider());

		List<User> alunos = new ArrayList<>();
		
		grupoUpdateDTO.getIdAlunos().stream().forEach(l -> {
			User aluno = userRepository.getById(l);
			alunos.add(aluno);
		});

		grupo.setNome(grupoUpdateDTO.getNome());
		grupo.setAlunoLider(alunoLider);
		grupo.setAlunos(alunos);
		
		grupoRepository.save(grupo);

		return grupoUpdateDTO;
		
	}
	
	
	

	private Grupo convertToEntity(GrupoPostDTO dto) {
		
		Grupo grupo = new Grupo();
		
		List<User> alunos = new ArrayList<>();
		
		dto.getAlunosId().stream().forEach( userId -> {
			User user = userRepository.getById(userId);
			alunos.add(user);
		});
		
		Materia materia = materiaRepository.getById(dto.getMateriaId());
		User userLider = userRepository.getById(dto.getAlunoLiderId());
		
		grupo.setNome(dto.getNome());
		grupo.setAlunos(alunos);
		grupo.setMateria(materia);
		grupo.setAlunoLider(userLider);
		
		return grupo;
		
	} 
	
	

}
