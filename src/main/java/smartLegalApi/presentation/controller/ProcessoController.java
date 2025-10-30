package smartLegalApi.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartLegalApi.application.processo.dto.request.AtualizarProcessoRequest;
import smartLegalApi.application.processo.dto.request.CriarProcessoRequest;
import smartLegalApi.application.processo.dto.response.ProcessoResponse;
import smartLegalApi.application.processo.mapper.ProcessoDtoMapper;
import smartLegalApi.application.processo.usecase.*;
import smartLegalApi.domain.processo.entity.Processo;
import smartLegalApi.domain.processo.valueobject.StatusProcesso;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller REST para Processo
 */
@RestController
@RequestMapping("/api/processos")
@RequiredArgsConstructor
@Tag(name = "Processos", description = "Gerenciamento de processos judiciais")
public class ProcessoController {
    
    private final CriarProcessoUseCase criarProcessoUseCase;
    private final BuscarProcessoPorIdUseCase buscarProcessoPorIdUseCase;
    private final BuscarProcessoPorNumeroUseCase buscarProcessoPorNumeroUseCase;
    private final ListarProcessosPorAdvogadoUseCase listarProcessosPorAdvogadoUseCase;
    private final ListarProcessosPorClienteUseCase listarProcessosPorClienteUseCase;
    private final AtualizarProcessoUseCase atualizarProcessoUseCase;
    private final EncerrarProcessoUseCase encerrarProcessoUseCase;
    private final DeletarProcessoUseCase deletarProcessoUseCase;
    private final ProcessoDtoMapper mapper;
    
    @PostMapping
    @Operation(summary = "Criar novo processo")
    public ResponseEntity<ProcessoResponse> criar(@Valid @RequestBody CriarProcessoRequest request) {
        Processo processo = criarProcessoUseCase.executar(
            request.getNumeroProcesso(),
            request.getTitulo(),
            request.getDescricao(),
            request.getIdAdvogado(),
            request.getIdCliente()
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(processo));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar processo por ID")
    public ResponseEntity<ProcessoResponse> buscarPorId(@PathVariable Long id) {
        Processo processo = buscarProcessoPorIdUseCase.executar(id);
        return ResponseEntity.ok(mapper.toResponse(processo));
    }
    
    @GetMapping("/numero/{numeroProcesso}")
    @Operation(summary = "Buscar processo por número")
    public ResponseEntity<ProcessoResponse> buscarPorNumero(@PathVariable String numeroProcesso) {
        Processo processo = buscarProcessoPorNumeroUseCase.executar(numeroProcesso);
        return ResponseEntity.ok(mapper.toResponse(processo));
    }
    
    @GetMapping("/advogado/{idAdvogado}")
    @Operation(summary = "Listar processos por advogado")
    public ResponseEntity<List<ProcessoResponse>> listarPorAdvogado(
        @PathVariable Long idAdvogado,
        @RequestParam(required = false) StatusProcesso status
    ) {
        List<Processo> processos = listarProcessosPorAdvogadoUseCase.executar(idAdvogado, status);
        List<ProcessoResponse> response = processos.stream()
            .map(mapper::toResponse)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/cliente/{idCliente}")
    @Operation(summary = "Listar processos por cliente")
    public ResponseEntity<List<ProcessoResponse>> listarPorCliente(@PathVariable Long idCliente) {
        List<Processo> processos = listarProcessosPorClienteUseCase.executar(idCliente);
        List<ProcessoResponse> response = processos.stream()
            .map(mapper::toResponse)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar processo")
    public ResponseEntity<ProcessoResponse> atualizar(
        @PathVariable Long id,
        @Valid @RequestBody AtualizarProcessoRequest request
    ) {
        Processo processo = atualizarProcessoUseCase.executar(
            id,
            request.getTitulo(),
            request.getDescricao()
        );
        
        return ResponseEntity.ok(mapper.toResponse(processo));
    }
    
    @PatchMapping("/{id}/encerrar")
    @Operation(summary = "Encerrar processo")
    public ResponseEntity<ProcessoResponse> encerrar(@PathVariable Long id) {
        Processo processo = encerrarProcessoUseCase.executar(id);
        return ResponseEntity.ok(mapper.toResponse(processo));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar processo")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        deletarProcessoUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }
}

