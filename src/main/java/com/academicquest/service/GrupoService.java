package com.academicquest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.academicquest.service.exception.AlunoLiderNaoEncontradoException;
import com.academicquest.service.exception.AlunoNaoEncontradoException;
import com.academicquest.service.exception.ErroAoSalvarGrupoException;
import com.academicquest.service.exception.GrupoNaoEncontradoException;

@Service
public class GrupoService {
	
	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MateriaRepository materiaRepository;

	@Transactional
	public GrupoDTO salvar(GrupoPostDTO grupoPostDTO) {
		Grupo grupo = converterParaEntidade(grupoPostDTO);
		Grupo grupoSalvo;
		grupoSalvo = grupoRepository.save(grupo);

		if(grupoSalvo == null) {
			throw new ErroAoSalvarGrupoException("Erro ao salvar o grupo");
		}

		return new GrupoDTO(grupo);
	}

	@Transactional(readOnly = true)
	public List<GrupoMateriaDTO> buscarPorMateriaId(Long id) {
		return grupoRepository.findByMateriaId(id).stream().map(GrupoMateriaDTO::new).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public GrupoDTO buscarPorId(Long id) {
		Grupo grupo = grupoRepository.findById(id).orElseThrow(() -> new GrupoNaoEncontradoException("Grupo não encontrado"));
		GrupoDTO grupoDTO = new GrupoDTO();
		grupoDTO.setId(grupo.getId());
		grupoDTO.setNome(grupo.getNome());
		grupoDTO.setAlunoLiderId(grupo.getAlunoLider().getId());
		grupoDTO.setMateriaId(grupo.getMateria().getId());
		List<UserDTO> listaUserDTOs = grupo.getListaAlunos().stream().map(UserDTO::new).collect(Collectors.toList());
		grupoDTO.setListaAlunos(listaUserDTOs);
		return grupoDTO;
	}
	
	@Transactional(readOnly = true)
	public List<UserDTO> buscarAlunosSemGrupo(Long id) {
		List<Long> buscaAlunosTurma = grupoRepository.buscarAlunosPorMateriaId(id);
		List<Long> buscaAlunosComGrupoMateria = grupoRepository.buscaAlunosComGrupoPorMateriaId(id);
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
	public GrupoUpdateDTO atualizarGrupo(GrupoUpdateDTO grupoUpdateDTO, Long id) {
		Grupo grupo = grupoRepository.findById(id).orElseThrow(() -> new GrupoNaoEncontradoException("Grupo não encontrado"));
		User alunoLider = userRepository.findById(grupoUpdateDTO.getAlunoLiderId()).orElseThrow(() -> new AlunoLiderNaoEncontradoException("Aluno lider não encontrado"));

		List<User> listaAlunos = new ArrayList<>();
		
		grupoUpdateDTO.getListaAlunosId().stream().forEach(l -> {
			User aluno = userRepository.findById(l).orElseThrow(() -> new AlunoNaoEncontradoException("Aluno não encontrado"));
			listaAlunos.add(aluno);
		});
		grupo.setNome(grupoUpdateDTO.getNome());
		grupo.setAlunoLider(alunoLider);
		grupo.setListaAlunos(listaAlunos);
		grupoRepository.save(grupo);
		return grupoUpdateDTO;
	}

	private Grupo converterParaEntidade(GrupoPostDTO grupoPostDTO) {
		Grupo grupo = new Grupo();
		List<User> alunos = new ArrayList<>();
		grupoPostDTO.getListaAlunosId().stream().forEach( userId -> {
			User user = userRepository.getById(userId);
			alunos.add(user);
		});
		Materia materia = materiaRepository.getById(grupoPostDTO.getMateriaId());
		User userLider = userRepository.getById(grupoPostDTO.getAlunoLiderId());
		grupo.setNome(grupoPostDTO.getNome());
		grupo.setListaAlunos(alunos);
		grupo.setMateria(materia);
		grupo.setAlunoLider(userLider);
		return grupo;
	}

}
