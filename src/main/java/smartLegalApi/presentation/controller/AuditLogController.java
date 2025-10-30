package smartLegalApi.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartLegalApi.application.audit.dto.response.AuditLogResponse;
import smartLegalApi.application.audit.mapper.AuditLogDtoMapper;
import smartLegalApi.domain.audit.entity.AuditLog;
import smartLegalApi.domain.audit.repository.AuditLogRepository;
import smartLegalApi.domain.audit.valueobject.TipoOperacao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller REST para Logs de Auditoria
 */
@RestController
@RequestMapping("/api/audit-logs")
@RequiredArgsConstructor
@Tag(name = "Auditoria", description = "Consulta de logs de auditoria do sistema")
public class AuditLogController {
    
    private final AuditLogRepository auditLogRepository;
    private final AuditLogDtoMapper mapper;
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar log por ID")
    public ResponseEntity<AuditLogResponse> buscarPorId(@PathVariable Long id) {
        return auditLogRepository.findById(id)
            .map(mapper::toResponse)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping
    @Operation(summary = "Listar todos os logs de auditoria")
    public ResponseEntity<List<AuditLogResponse>> listarTodos() {
        List<AuditLog> logs = auditLogRepository.findAll();
        List<AuditLogResponse> response = logs.stream()
            .map(mapper::toResponse)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/usuario/{idUsuario}")
    @Operation(summary = "Listar logs por usuário")
    public ResponseEntity<List<AuditLogResponse>> listarPorUsuario(@PathVariable Long idUsuario) {
        List<AuditLog> logs = auditLogRepository.findByUsuario(idUsuario);
        List<AuditLogResponse> response = logs.stream()
            .map(mapper::toResponse)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/entidade/{entidade}")
    @Operation(summary = "Listar logs por entidade")
    public ResponseEntity<List<AuditLogResponse>> listarPorEntidade(
        @PathVariable String entidade,
        @RequestParam Long idEntidade
    ) {
        List<AuditLog> logs = auditLogRepository.findByEntidade(entidade, idEntidade);
        List<AuditLogResponse> response = logs.stream()
            .map(mapper::toResponse)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/tipo/{tipoOperacao}")
    @Operation(summary = "Listar logs por tipo de operação")
    public ResponseEntity<List<AuditLogResponse>> listarPorTipo(@PathVariable TipoOperacao tipoOperacao) {
        List<AuditLog> logs = auditLogRepository.findByTipoOperacao(tipoOperacao);
        List<AuditLogResponse> response = logs.stream()
            .map(mapper::toResponse)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/periodo")
    @Operation(summary = "Listar logs por período")
    public ResponseEntity<List<AuditLogResponse>> listarPorPeriodo(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim
    ) {
        List<AuditLog> logs = auditLogRepository.findByPeriodo(dataInicio, dataFim);
        List<AuditLogResponse> response = logs.stream()
            .map(mapper::toResponse)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/recentes")
    @Operation(summary = "Listar logs recentes")
    public ResponseEntity<List<AuditLogResponse>> listarRecentes(
        @RequestParam(defaultValue = "50") int limit
    ) {
        List<AuditLog> logs = auditLogRepository.findRecentes(limit);
        List<AuditLogResponse> response = logs.stream()
            .map(mapper::toResponse)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }
}

