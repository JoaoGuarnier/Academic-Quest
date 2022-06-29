package com.academicquest.service;

import com.academicquest.dto.ProjetoDTO;
import com.academicquest.dto.ProjetoPostDTO;
import com.academicquest.enums.STATUS_PROJETO;
import com.academicquest.model.Grupo;
import com.academicquest.model.Materia;
import com.academicquest.model.Projeto;
import com.academicquest.model.ProjetoGrupo;
import com.academicquest.repository.GrupoRepository;
import com.academicquest.repository.MateriaRepository;
import com.academicquest.repository.ProjetoGrupoRepository;
import com.academicquest.repository.ProjetoRepository;
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

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private ProjetoGrupoRepository projetoGrupoRepository;

    @Transactional(readOnly = true)
    public List<ProjetoDTO> buscarTodos() {
        return projetoRepository.findAll().stream().map(ProjetoDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProjetoDTO> buscarPorMateriaId(Long id) {
        return projetoRepository.findByMateriaId(id).stream().map(ProjetoDTO::new).collect(Collectors.toList());
    }

    @Transactional()
    public void salvar(ProjetoPostDTO projetoPostDTO) {

        Projeto projeto = converterParaEntidade(projetoPostDTO);

        Long idMateria = projetoPostDTO.getMateriaId();
        Materia materia = materiaRepository.getById(idMateria);

        projeto.setMateria(materia);
        Projeto projetoSalvo = projetoRepository.save(projeto);

        criaRegistrosProjetoGrupo(projetoPostDTO,projetoSalvo);

    }

    @Transactional()
    private void criaRegistrosProjetoGrupo(ProjetoPostDTO projetoPostDTO, Projeto projeto) {

        List<Long> idsGrupos = grupoRepository.buscaGruposPorMateriaId(projetoPostDTO.getMateriaId());

        idsGrupos.stream().forEach(idGrupo -> {
            Grupo grupo = grupoRepository.findById(idGrupo).get();
            ProjetoGrupo projetoGrupo = new ProjetoGrupo();
            projetoGrupo.setGrupo(grupo);
            projetoGrupo.setProjeto(projeto);
            projetoGrupoRepository.save(projetoGrupo);
        });

    }



    private Projeto converterParaEntidade(ProjetoPostDTO dto) {
        Projeto projeto = new Projeto();
        projeto.setNome(dto.getNome());
        projeto.setDescricao(dto.getDescricao());
        projeto.setStatus(STATUS_PROJETO.EM_ANDAMENTO);
        return  projeto;
    }

}
