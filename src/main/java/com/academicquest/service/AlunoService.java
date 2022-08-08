package com.academicquest.service;

import com.academicquest.dto.AlunoGruposDTO;
import com.academicquest.dto.AlunoTarefaGrupoDTO;
import com.academicquest.dto.ProjetoGrupoAlunoDTO;
import com.academicquest.repository.GrupoRepository;
import com.academicquest.repository.ProjetoRepository;
import com.academicquest.repository.TarefaGrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlunoService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private TarefaGrupoRepository tarefaGrupoRepository;
    
    @Autowired
    private ProjetoRepository projetoRepository;
    
    public List<ProjetoGrupoAlunoDTO> buscarProjetosGrupoPorAlunoId(Long alunoId) {
        List<Tuple> tupleList = projetoRepository.buscarProjetosDoGrupoPorAlunoId(alunoId);
        List<ProjetoGrupoAlunoDTO> projetoGrupoAlunoDTOList = new ArrayList<>();

        tupleList.forEach(tuple -> {
            ProjetoGrupoAlunoDTO projetoGrupoAlunoDTO = new ProjetoGrupoAlunoDTO();
            projetoGrupoAlunoDTO.setNomeProjeto(tuple.get(0).toString());
            projetoGrupoAlunoDTO.setStatusProjeto(tuple.get(1).toString());
            projetoGrupoAlunoDTO.setNotaProjeto(tuple.get(2).toString());
            projetoGrupoAlunoDTO.setNomeMateria(tuple.get(3).toString());
            projetoGrupoAlunoDTO.setGrupoId(Long.valueOf(tuple.get(4).toString()));
            projetoGrupoAlunoDTO.setNomeGrupo(tuple.get(5).toString());
            projetoGrupoAlunoDTOList.add(projetoGrupoAlunoDTO);
        });

        return projetoGrupoAlunoDTOList;
    }

    private List<AlunoGruposDTO> buscarGruposAluno(Long idAluno)  {

        List<Tuple> tupleList = grupoRepository.buscarGrupoDoAlunoPorAlunoId(idAluno);

        List<AlunoGruposDTO> alunoGruposDTOList = new ArrayList<>();

        tupleList.forEach(tuple -> {
            AlunoGruposDTO alunoGruposDTO = new AlunoGruposDTO();
            alunoGruposDTO.setId(Long.valueOf(tuple.get(0).toString()));
            alunoGruposDTO.setNomeGrupo(tuple.get(1).toString());
            alunoGruposDTO.setUsuarioLider(Long.valueOf(tuple.get(2).toString()));
            alunoGruposDTOList.add(alunoGruposDTO);
        });

       return alunoGruposDTOList;
    }

    public List<AlunoTarefaGrupoDTO> buscarTarefasPendenteGrupoPorAlunoId(Long idAluno) {

        List<AlunoGruposDTO> alunoGruposDTOList = this.buscarGruposAluno(idAluno);

        List<AlunoTarefaGrupoDTO> alunoTarefaGrupoDTOList = new ArrayList<>();

        alunoGruposDTOList.forEach(grupo -> {
            List<Tuple> tupleList = tarefaGrupoRepository.buscarTarefasPendenteGrupoPorAlunoId(grupo.getId());

            tupleList.forEach(tuple -> {
                AlunoTarefaGrupoDTO alunoTarefaGrupoDTO = new AlunoTarefaGrupoDTO();
                alunoTarefaGrupoDTO.setNomeGrupo(grupo.getNomeGrupo());
                alunoTarefaGrupoDTO.setNomeAtividade(tuple.get(1).toString());
                alunoTarefaGrupoDTO.setNomeMateria(tuple.get(0).toString());
                alunoTarefaGrupoDTO.setIdTarefaGrupo(Long.valueOf(tuple.get(2).toString()));
                alunoTarefaGrupoDTO.setDataEntregaAtividade(tuple.get(3).toString());
                alunoTarefaGrupoDTO.setNomeProjeto(tuple.get(4).toString());
                alunoTarefaGrupoDTOList.add(alunoTarefaGrupoDTO);
            });
        });

        return alunoTarefaGrupoDTOList;

    }



}
