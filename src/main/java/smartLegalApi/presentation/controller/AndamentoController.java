package smartLegalApi.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartLegalApi.application.processo.dto.request.CriarAndamentoRequest;
import smartLegalApi.application.processo.dto.response.AndamentoResponse;
import smartLegalApi.application.processo.mapper.ProcessoDtoMapper;
import smartLegalApi.application.processo.usecase.*;
import smartLegalApi.domain.processo.entity.Andamento;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller REST para Andamento
 */
@RestController
@RequestMapping("/api/processos/{idProcesso}/andamentos")
@RequiredArgsConstructor
@Tag(name = "Andamentos", description = "Gerenciamento de andamentos processuais")
public class AndamentoController {
    
    private final AdicionarAndamentoUseCase adicionarAndamentoUseCase;
    private final ListarAndamentosUseCase listarAndamentosUseCase;
    private final AtualizarAndamentoUseCase atualizarAndamentoUseCase;
    private final DeletarAndamentoUseCase deletarAndamentoUseCase;
    private final ProcessoDtoMapper mapper;
    
    @PostMapping
    @Operation(summary = "Adicionar andamento ao processo")
    public ResponseEntity<AndamentoResponse> adicionar(
        @PathVariable Long idProcesso,
        @Valid @RequestBody CriarAndamentoRequest request
    ) {
        Andamento andamento = adicionarAndamentoUseCase.executar(
            idProcesso,
            request.getDataAndamento(),
            request.getDescricao(),
            request.getTipo()
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(andamento));
    }
    
    @GetMapping
    @Operation(summary = "Listar andamentos do processo")
    public ResponseEntity<List<AndamentoResponse>> listar(@PathVariable Long idProcesso) {
        List<Andamento> andamentos = listarAndamentosUseCase.executar(idProcesso);
        List<AndamentoResponse> response = andamentos.stream()
            .map(mapper::toResponse)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{idAndamento}")
    @Operation(summary = "Atualizar andamento")
    public ResponseEntity<AndamentoResponse> atualizar(
        @PathVariable Long idProcesso,
        @PathVariable Long idAndamento,
        @Valid @RequestBody CriarAndamentoRequest request
    ) {
        Andamento andamento = atualizarAndamentoUseCase.executar(
            idAndamento,
            request.getDataAndamento(),
            request.getDescricao(),
            request.getTipo()
        );
        
        return ResponseEntity.ok(mapper.toResponse(andamento));
    }
    
    @DeleteMapping("/{idAndamento}")
    @Operation(summary = "Deletar andamento")
    public ResponseEntity<Void> deletar(
        @PathVariable Long idProcesso,
        @PathVariable Long idAndamento
    ) {
        deletarAndamentoUseCase.executar(idAndamento);
        return ResponseEntity.noContent().build();
    }
}

