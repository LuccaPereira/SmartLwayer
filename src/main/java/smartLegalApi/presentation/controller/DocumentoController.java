package smartLegalApi.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartLegalApi.application.processo.dto.request.AnexarDocumentoRequest;
import smartLegalApi.application.processo.dto.response.DocumentoResponse;
import smartLegalApi.application.processo.mapper.ProcessoDtoMapper;
import smartLegalApi.application.processo.usecase.AnexarDocumentoUseCase;
import smartLegalApi.application.processo.usecase.DeletarDocumentoUseCase;
import smartLegalApi.application.processo.usecase.ListarDocumentosUseCase;
import smartLegalApi.domain.processo.entity.Documento;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller REST para Documento
 */
@RestController
@RequestMapping("/api/processos/{idProcesso}/documentos")
@RequiredArgsConstructor
@Tag(name = "Documentos", description = "Gerenciamento de documentos processuais")
public class DocumentoController {
    
    private final AnexarDocumentoUseCase anexarDocumentoUseCase;
    private final ListarDocumentosUseCase listarDocumentosUseCase;
    private final DeletarDocumentoUseCase deletarDocumentoUseCase;
    private final ProcessoDtoMapper mapper;
    
    @PostMapping
    @Operation(summary = "Anexar documento ao processo")
    public ResponseEntity<DocumentoResponse> anexar(
        @PathVariable Long idProcesso,
        @Valid @RequestBody AnexarDocumentoRequest request
    ) {
        Documento documento = anexarDocumentoUseCase.executar(
            idProcesso,
            request.getNomeDocumento(),
            request.getCaminhoArquivo(),
            request.getTipoArquivo(),
            request.getTamanhoBytes(),
            request.getHashArquivo()
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(documento));
    }
    
    @GetMapping
    @Operation(summary = "Listar documentos do processo")
    public ResponseEntity<List<DocumentoResponse>> listar(
        @PathVariable Long idProcesso,
        @RequestParam(required = false) String tipoArquivo
    ) {
        List<Documento> documentos = listarDocumentosUseCase.executar(idProcesso, tipoArquivo);
        List<DocumentoResponse> response = documentos.stream()
            .map(mapper::toResponse)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{idDocumento}")
    @Operation(summary = "Deletar documento")
    public ResponseEntity<Void> deletar(
        @PathVariable Long idProcesso,
        @PathVariable Long idDocumento
    ) {
        deletarDocumentoUseCase.executar(idDocumento);
        return ResponseEntity.noContent().build();
    }
}

