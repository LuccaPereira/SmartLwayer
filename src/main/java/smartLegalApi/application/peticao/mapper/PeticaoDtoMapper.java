package smartLegalApi.application.peticao.mapper;

import org.springframework.stereotype.Component;
import smartLegalApi.application.peticao.dto.response.PeticaoResponse;
import smartLegalApi.domain.peticao.entity.Peticao;

/**
 * Mapper entre entidade de domínio Petição e DTOs
 */
@Component
public class PeticaoDtoMapper {
    
    public PeticaoResponse toResponse(Peticao peticao) {
        if (peticao == null) return null;
        
        return PeticaoResponse.builder()
            .id(peticao.getId())
            .idProcesso(peticao.getIdProcesso())
            .idAdvogado(peticao.getIdAdvogado())
            .tipo(peticao.getTipo())
            .titulo(peticao.getTitulo())
            .conteudo(peticao.getConteudo())
            .conteudoGeradoIA(peticao.getConteudoGeradoIA())
            .promptUtilizado(peticao.getPromptUtilizado())
            .status(peticao.getStatus())
            .caminhoDocumento(peticao.getCaminhoDocumento())
            .dataCriacao(peticao.getDataCriacao())
            .dataAtualizacao(peticao.getDataAtualizacao())
            .dataProtocolo(peticao.getDataProtocolo())
            .build();
    }
}

