package com.academicquest.service;

import com.academicquest.dto.ProjetoDTO;
import com.academicquest.dto.ProjetoPostDTO;
import com.academicquest.enums.STATUS_PROJETO;
import com.academicquest.model.Materia;
import com.academicquest.model.Projeto;
import com.academicquest.model.Turma;
import com.academicquest.repository.MateriaRepository;
import com.academicquest.repository.ProjetoRepository;
import com.academicquest.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Transactional(readOnly = true)
    public List<ProjetoDTO> getAll() {
        return projetoRepository.findAll().stream().map(ProjetoDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProjetoDTO> getByMateriaId(Long id) {
        return projetoRepository.findyByMateriaId(id).stream().map(ProjetoDTO::new).collect(Collectors.toList());
    }

    @Transactional()
    public void save(ProjetoPostDTO projetoPostDTO) {

        Projeto projeto = convertToEntity(projetoPostDTO);

        Long idMateria = projetoPostDTO.getIdMateria();
        Materia materia = materiaRepository.getById(idMateria);

        projeto.setMateria(materia);
        projetoRepository.save(projeto);

    }


    private Projeto convertToEntity(ProjetoPostDTO dto) {
        Projeto projeto = new Projeto();
        projeto.setNome(dto.getNome());
        projeto.setDescricao(dto.getDescricao());
        projeto.setStatus(STATUS_PROJETO.EM_ANDAMENTO);
        return  projeto;
    }

}
