package com.academicquest.service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.academicquest.dto.ProjetoDTO;
import com.academicquest.dto.ProjetoPostDTO;
import com.academicquest.dto.ProjetoPutDTO;
import com.academicquest.enums.STATUS_PROJETO;
import com.academicquest.model.Grupo;
import com.academicquest.model.Materia;
import com.academicquest.model.Projeto;
import com.academicquest.model.ProjetoGrupo;
import com.academicquest.repository.GrupoRepository;
import com.academicquest.repository.MateriaRepository;
import com.academicquest.repository.ProjetoGrupoRepository;
import com.academicquest.repository.ProjetoRepository;
import com.academicquest.service.exception.ErroAoCriarRegistrosProjetoGrupoException;
import com.academicquest.service.exception.MateriaNaoEncontradaException;
import com.academicquest.service.exception.NenhumGrupoCadastradoNaMateriaException;
import com.academicquest.service.exception.ProjetoJaConcluidoException;
import com.academicquest.service.exception.ProjetoNaoEncontradoException;

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

    @Transactional
    public ProjetoDTO salvar(ProjetoPostDTO projetoPostDTO) {
        Projeto projeto = converterParaEntidade(projetoPostDTO);
        Long idMateria = projetoPostDTO.getMateriaId();
        Materia materia = materiaRepository.findById(idMateria).orElseThrow(() -> new MateriaNaoEncontradaException("Matéria não encontrada"));
        verificarSeExistemGruposNaMateria(projetoPostDTO.getMateriaId());
        projeto.setMateria(materia);
        Projeto projetoSalvo = projetoRepository.save(projeto);
        try {
            criaRegistrosProjetoGrupo(projetoPostDTO,projetoSalvo);
        } catch (Exception e) {
            throw new ErroAoCriarRegistrosProjetoGrupoException("Erro ao gerar registros do projeto grupo");
        }
        ProjetoDTO projetoDTO = new ProjetoDTO(projetoSalvo);
        return projetoDTO;
    }

    private void verificarSeExistemGruposNaMateria(Long materiaId) {

        List<Long> listaGruposIds = grupoRepository.buscaGruposPorMateriaId(materiaId);
        if (listaGruposIds.isEmpty()) {
            throw new NenhumGrupoCadastradoNaMateriaException("Não existem grupos cadastrados para a materia");
        }

    }

    @Transactional
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

    public ProjetoDTO buscarPorId(Long id) {
        Projeto projeto = projetoRepository.findById(id).orElseThrow(() -> new ProjetoNaoEncontradoException("Projeto não encontrado"));
        return new ProjetoDTO(projeto);
    }

    public ProjetoDTO atualizarProjeto(ProjetoPutDTO projetoPutDTO, Long id) {
        Projeto projeto = projetoRepository.findById(id).orElseThrow(() -> new ProjetoNaoEncontradoException("Projeto não encontrado"));
        projeto.setNome(projetoPutDTO.getNome());
        projeto.setDescricao(projetoPutDTO.getDescricao());
        projeto = projetoRepository.save(projeto);
        return new ProjetoDTO(projeto);
    }

    public void avaliarProjeto(Long projetoId) {

        Projeto projeto = projetoRepository.findById(projetoId).orElseThrow(() -> new ProjetoNaoEncontradoException("Projeto não encontrada"));
        if (projeto.getStatus().equals(STATUS_PROJETO.CONCLUIDO)){
            throw new ProjetoJaConcluidoException("O projeto já foi concluido");
        }

        List<ProjetoGrupo> listaProjetoGrupo = projetoGrupoRepository.findByProjetoId(projetoId);

        listaProjetoGrupo.stream().forEach(pg -> {
            DecimalFormat df = new DecimalFormat("#,###.00");
            Double notaProjeto = projetoRepository.calcularMediaProjetoGrupo(pg.getProjeto().getId(), pg.getGrupo().getId());
            Double notaProjetoFormatada = Double.valueOf(df.format(notaProjeto).replace(",","."));
            pg.setNota(notaProjetoFormatada);
            projetoGrupoRepository.save(pg);
        });
        projeto.setStatus(STATUS_PROJETO.CONCLUIDO);
        projetoRepository.save(projeto);
    }

}
