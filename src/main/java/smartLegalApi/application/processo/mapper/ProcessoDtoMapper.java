package smartLegalApi.application.processo.mapper;

import org.springframework.stereotype.Component;
import smartLegalApi.application.processo.dto.response.AndamentoResponse;
import smartLegalApi.application.processo.dto.response.DocumentoResponse;
import smartLegalApi.application.processo.dto.response.ProcessoResponse;
import smartLegalApi.domain.processo.entity.Andamento;
import smartLegalApi.domain.processo.entity.Documento;
import smartLegalApi.domain.processo.entity.Processo;

/**
 * Mapper entre entidades de domínio e DTOs de Processo
 */
@Component
public class ProcessoDtoMapper {
    
    public ProcessoResponse toResponse(Processo processo) {
        if (processo == null) return null;
        
        return ProcessoResponse.builder()
            .id(processo.getId())
            .numeroProcesso(processo.getNumeroProcesso().toString())
            .titulo(processo.getTitulo())
            .descricao(processo.getDescricao())
            .status(processo.getStatus())
            .dataAbertura(processo.getDataAbertura())
            .dataEncerramento(processo.getDataEncerramento())
            .idAdvogado(processo.getIdAdvogado())
            .idCliente(processo.getIdCliente())
            .dataCadastro(processo.getDataCadastro())
            .dataAtualizacao(processo.getDataAtualizacao())
            .build();
    }
    
    public AndamentoResponse toResponse(Andamento andamento) {
        if (andamento == null) return null;
        
        return AndamentoResponse.builder()
            .id(andamento.getId())
            .idProcesso(andamento.getIdProcesso())
            .dataAndamento(andamento.getDataAndamento())
            .descricao(andamento.getDescricao())
            .tipo(andamento.getTipo())
            .dataCadastro(andamento.getDataCadastro())
            .build();
    }
    
    public DocumentoResponse toResponse(Documento documento) {
        if (documento == null) return null;
        
        return DocumentoResponse.builder()
            .id(documento.getId())
            .idProcesso(documento.getIdProcesso())
            .nomeDocumento(documento.getNomeDocumento())
            .caminhoArquivo(documento.getCaminhoArquivo())
            .tipoArquivo(documento.getTipoArquivo())
            .tamanhoBytes(documento.getTamanhoBytes())
            .hashArquivo(documento.getHashArquivo())
            .dataUpload(documento.getDataUpload())
            .build();
    }
}

