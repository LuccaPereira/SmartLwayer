package smartLegalApi.application.advogado.mapper;

import org.springframework.stereotype.Component;
import smartLegalApi.application.advogado.dto.response.AdvogadoResponse;
import smartLegalApi.domain.advogado.entity.Advogado;

/**
 * Mapper entre entidade de domínio e DTOs
 */
@Component
public class AdvogadoMapper {
    
    /**
     * Converte entidade de domínio para DTO de resposta
     */
    public AdvogadoResponse toResponse(Advogado advogado) {
        if (advogado == null) {
            return null;
        }
        
        return AdvogadoResponse.builder()
            .id(advogado.getId())
            .nome(advogado.getNome())
            .oab(advogado.getOab().formatado())
            .cpf(advogado.getCpf().formatado())
            .email(advogado.getEmail().toString())
            .telefone(advogado.getTelefone() != null ? advogado.getTelefone().formatado() : null)
            .dataCadastro(advogado.getDataCadastro())
            .dataAtualizacao(advogado.getDataAtualizacao())
            .ativo(advogado.isAtivo())
            .build();
    }
}

