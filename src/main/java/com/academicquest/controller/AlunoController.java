package com.academicquest.controller;

import com.academicquest.dto.AlunoTarefaGrupoDTO;
import com.academicquest.dto.ProjetoGrupoAlunoDTO;
import com.academicquest.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
