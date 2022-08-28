package com.academicquest.controller;

import com.academicquest.dto.aluno.AlunoTarefaGrupoDTO;
import com.academicquest.dto.projeto.ProjetoGrupoAlunoDTO;
import com.academicquest.dto.tarefaGrupo.TarefaGrupoProjetoDTO;
import com.academicquest.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping("/tarefasPendentes/{idAluno}")
    private ResponseEntity<List<AlunoTarefaGrupoDTO>> buscarTarefasPendenteGrupoPorAlunoId(@PathVariable Long idAluno) {
        List<AlunoTarefaGrupoDTO> alunoGruposDTOList = alunoService.buscarTarefasPendenteGrupoPorAlunoId(idAluno);
        return ResponseEntity.ok(alunoGruposDTOList);
    }

    @GetMapping("/projetosGrupo/{idAluno}")
    private ResponseEntity<List<ProjetoGrupoAlunoDTO>> buscarProjetosGrupoPorAlunoId(@PathVariable Long idAluno) {
        List<ProjetoGrupoAlunoDTO> projetoGrupoAlunoDTOS = alunoService.buscarProjetosGrupoPorAlunoId(idAluno);
        return ResponseEntity.ok(projetoGrupoAlunoDTOS);
    }

    @GetMapping("/tarefas/projetoGrupo/{grupoId}/{projetoId}")
    private ResponseEntity<List<TarefaGrupoProjetoDTO>> buscarTarefasGrupoPorProjeto(@PathVariable Long grupoId, @PathVariable Long projetoId) {
        List<TarefaGrupoProjetoDTO> tarefaGrupoProjetoDTOList = alunoService.buscarTarefasGrupoPorProjeto(grupoId, projetoId);
        return ResponseEntity.ok(tarefaGrupoProjetoDTOList);
    }

    @PostMapping("/entregar")
    private ResponseEntity<?> entregarTarefaGrupo(MultipartFile arquivoUploadEntrega, Long tarefaGrupoId) throws IOException {
        alunoService.entregarTarefaGrupo(arquivoUploadEntrega, tarefaGrupoId);
        return ResponseEntity.ok().build();
    }

}
