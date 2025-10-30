package smartLegalApi.application.cliente.mapper;

import org.springframework.stereotype.Component;
import smartLegalApi.application.cliente.dto.response.ClienteResponse;
import smartLegalApi.domain.cliente.entity.Cliente;

/**
 * Mapper entre entidade de domínio e DTOs
 */
@Component
public class ClienteMapper {
    
    /**
     * Converte entidade de domínio para DTO de resposta
     */
    public ClienteResponse toResponse(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        
        return ClienteResponse.builder()
            .id(cliente.getId())
            .nomeCompleto(cliente.getNomeCompleto())
            .cpfCnpj(cliente.getCpfCnpj().formatado())
            .tipoPessoa(cliente.getTipoPessoa())
            .email(cliente.getEmail() != null ? cliente.getEmail().toString() : null)
            .telefone(cliente.getTelefone() != null ? cliente.getTelefone().formatado() : null)
            .endereco(cliente.getEndereco() != null ? cliente.getEndereco().toString() : null)
            .dataCadastro(cliente.getDataCadastro())
            .dataAtualizacao(cliente.getDataAtualizacao())
            .ativo(cliente.isAtivo())
            .build();
    }
}

