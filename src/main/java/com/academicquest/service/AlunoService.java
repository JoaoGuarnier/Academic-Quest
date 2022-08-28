package com.academicquest.service;

import com.academicquest.dto.aluno.AlunoGruposDTO;
import com.academicquest.dto.aluno.AlunoTarefaGrupoDTO;
import com.academicquest.dto.projeto.ProjetoGrupoAlunoDTO;
import com.academicquest.dto.tarefaGrupo.TarefaGrupoProjetoDTO;
import com.academicquest.enums.STATUS_TAREFA_GRUPO;
import com.academicquest.model.TarefaGrupo;
import com.academicquest.model.Upload;
import com.academicquest.repository.GrupoRepository;
import com.academicquest.repository.ProjetoRepository;
import com.academicquest.repository.TarefaGrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Tuple;
import java.io.IOException;
import java.time.LocalDateTime;
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
            projetoGrupoAlunoDTO.setProjetoId(Long.valueOf(tuple.get(6).toString()));
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

    public List<TarefaGrupoProjetoDTO> buscarTarefasGrupoPorProjeto(Long grupoId, Long projetoId) {
        List<Tuple> tupleList = tarefaGrupoRepository.buscarTarefasGrupoPorProjeto(grupoId, projetoId);

        List<TarefaGrupoProjetoDTO> tarefaGrupoProjetoDTOList = new ArrayList<>();

        tupleList.forEach(tuple -> {
            TarefaGrupoProjetoDTO tarefaGrupoProjetoDTO = new TarefaGrupoProjetoDTO();
            tarefaGrupoProjetoDTO.setNomeTarefa(tuple.get(1).toString());
            tarefaGrupoProjetoDTO.setTarefaGrupoId(Long.valueOf(tuple.get(2).toString()));
            tarefaGrupoProjetoDTO.setStatusTarefa(tuple.get(5).toString());
            tarefaGrupoProjetoDTO.setDataEntrega(tuple.get(3).toString());
            tarefaGrupoProjetoDTOList.add(tarefaGrupoProjetoDTO);
        });
        return tarefaGrupoProjetoDTOList;
    }

    public void entregarTarefaGrupo(MultipartFile arquivoUploadEntrega, Long tarefaGrupoId) {
        TarefaGrupo tarefaGrupo = tarefaGrupoRepository.findById(tarefaGrupoId).orElseThrow(() -> new EntityNotFoundException());
        try {
            Upload upload = formatarUpload(arquivoUploadEntrega);
            tarefaGrupo.setUpload(upload);
            tarefaGrupo.setDataEntrega(LocalDateTime.now());
            tarefaGrupo.setStatusTarefaGrupo(STATUS_TAREFA_GRUPO.ENTREGUE);
            tarefaGrupoRepository.save(tarefaGrupo);
        } catch (Exception e) {
            throw new EntityNotFoundException();
        }
    }

    private Upload formatarUpload(MultipartFile multipartFile) throws IOException {
        Upload upload = new Upload();
        upload.setTitulo(multipartFile.getOriginalFilename());
        upload.setFormato(multipartFile.getContentType());
        upload.setArquivoUpload(multipartFile.getBytes());
        return upload;
    }
}
