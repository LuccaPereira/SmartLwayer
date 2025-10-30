package smartLegalApi.domain.processo.exception;

import smartLegalApi.domain.shared.exception.NotFoundException;

public class ProcessoNaoEncontradoException extends NotFoundException {
    
    public ProcessoNaoEncontradoException(Long id) {
        super("Processo", id);
    }
    
    public ProcessoNaoEncontradoException(String numeroProcesso) {
        super(String.format("Processo %s não encontrado", numeroProcesso));
    }
}

