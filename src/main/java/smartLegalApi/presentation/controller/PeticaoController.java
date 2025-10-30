package smartLegalApi.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartLegalApi.application.peticao.dto.request.AtualizarConteudoRequest;
import smartLegalApi.application.peticao.dto.request.CriarPeticaoRequest;
import smartLegalApi.application.peticao.dto.request.GerarComIARequest;
import smartLegalApi.application.peticao.dto.response.PeticaoResponse;
import smartLegalApi.application.peticao.mapper.PeticaoDtoMapper;
import smartLegalApi.application.peticao.usecase.*;
import smartLegalApi.domain.peticao.entity.Peticao;
import smartLegalApi.domain.peticao.valueobject.StatusPeticao;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller REST para Petição
 */
@RestController
@RequestMapping("/api/peticoes")
@RequiredArgsConstructor
@Tag(name = "Petições", description = "Gerenciamento de petições judiciais com IA")
public class PeticaoController {
    
    private final CriarPeticaoUseCase criarPeticaoUseCase;
    private final GerarPeticaoComIAUseCase gerarPeticaoComIAUseCase;
    private final BuscarPeticaoPorIdUseCase buscarPeticaoPorIdUseCase;
    private final ListarPeticoesPorProcessoUseCase listarPeticoesPorProcessoUseCase;
    private final ListarPeticoesPorAdvogadoUseCase listarPeticoesPorAdvogadoUseCase;
    private final AtualizarConteudoPeticaoUseCase atualizarConteudoPeticaoUseCase;
    private final AprovarPeticaoUseCase aprovarPeticaoUseCase;
    private final ProtocolarPeticaoUseCase protocolarPeticaoUseCase;
    private final DeletarPeticaoUseCase deletarPeticaoUseCase;
    private final PeticaoDtoMapper mapper;
    
    @PostMapping
    @Operation(summary = "Criar nova petição em rascunho")
    public ResponseEntity<PeticaoResponse> criar(@Valid @RequestBody CriarPeticaoRequest request) {
        Peticao peticao = criarPeticaoUseCase.executar(
            request.getIdProcesso(),
            request.getIdAdvogado(),
            request.getTipo(),
            request.getTitulo()
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(peticao));
    }
    
    @PostMapping("/{id}/gerar-ia")
    @Operation(
        summary = "Gerar petição com IA (Gemini)",
        description = "Utiliza IA para gerar o conteúdo completo da petição baseado no contexto fornecido"
    )
    public ResponseEntity<PeticaoResponse> gerarComIA(
        @PathVariable Long id,
        @Valid @RequestBody GerarComIARequest request
    ) {
        Peticao peticao = gerarPeticaoComIAUseCase.executar(
            id,
            request.getContexto(),
            request.getDetalhesAdicionais()
        );
        
        return ResponseEntity.ok(mapper.toResponse(peticao));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar petição por ID")
    public ResponseEntity<PeticaoResponse> buscarPorId(@PathVariable Long id) {
        Peticao peticao = buscarPeticaoPorIdUseCase.executar(id);
        return ResponseEntity.ok(mapper.toResponse(peticao));
    }
    
    @GetMapping("/processo/{idProcesso}")
    @Operation(summary = "Listar petições por processo")
    public ResponseEntity<List<PeticaoResponse>> listarPorProcesso(@PathVariable Long idProcesso) {
        List<Peticao> peticoes = listarPeticoesPorProcessoUseCase.executar(idProcesso);
        List<PeticaoResponse> response = peticoes.stream()
            .map(mapper::toResponse)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/advogado/{idAdvogado}")
    @Operation(summary = "Listar petições por advogado")
    public ResponseEntity<List<PeticaoResponse>> listarPorAdvogado(
        @PathVariable Long idAdvogado,
        @RequestParam(required = false) StatusPeticao status
    ) {
        List<Peticao> peticoes = listarPeticoesPorAdvogadoUseCase.executar(idAdvogado, status);
        List<PeticaoResponse> response = peticoes.stream()
            .map(mapper::toResponse)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}/conteudo")
    @Operation(summary = "Atualizar conteúdo da petição (edição manual)")
    public ResponseEntity<PeticaoResponse> atualizarConteudo(
        @PathVariable Long id,
        @Valid @RequestBody AtualizarConteudoRequest request
    ) {
        Peticao peticao = atualizarConteudoPeticaoUseCase.executar(id, request.getConteudo());
        return ResponseEntity.ok(mapper.toResponse(peticao));
    }
    
    @PatchMapping("/{id}/aprovar")
    @Operation(summary = "Aprovar petição para protocolo")
    public ResponseEntity<PeticaoResponse> aprovar(@PathVariable Long id) {
        Peticao peticao = aprovarPeticaoUseCase.executar(id);
        return ResponseEntity.ok(mapper.toResponse(peticao));
    }
    
    @PostMapping("/{id}/protocolar")
    @Operation(
        summary = "Protocolar petição",
        description = "Gera documento Word e marca petição como protocolada"
    )
    public ResponseEntity<PeticaoResponse> protocolar(@PathVariable Long id) {
        Peticao peticao = protocolarPeticaoUseCase.executar(id);
        return ResponseEntity.ok(mapper.toResponse(peticao));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar petição")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        deletarPeticaoUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }
}

