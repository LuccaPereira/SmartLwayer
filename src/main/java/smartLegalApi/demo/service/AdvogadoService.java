import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smartLegalApi.demo.Model.Advogado;
import smartLegalApi.demo.Repository.AdvogadoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdvogadoService {

    @Autowired
    private AdvogadoRepository advogadoRepository;

    public Advogado criarAdv(smartLegalApi.demo.Model.Advogado newAdv){

        if(newAdv.getOab() == null || newAdv.getOab().isBlank()){
            throw new IllegalArgumentException("OAB é obrigatório");
        }

        if(newAdv.getNome() == null || newAdv.getNome().isBlank()){
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }

        if(newAdv.getTelefone() == null || newAdv.getTelefone().isBlank()){
            throw new IllegalArgumentException("telefone não pode ser nulo ou vazio");
        }
        
        if(newAdv.getSenha() == null || newAdv.getSenha().isBlank()){
            throw new IllegalArgumentException("senha não pode ser nulo ou vazio");
        }

           if(newAdv.getEmail() == null || newAdv.getEmail().isBlank()){
            throw new IllegalArgumentException("email não pode ser nulo ou vazio");
        }

        if(advogadoRepository.findByOab(newAdv.getOab()) != null){
            throw new IllegalArgumentException("OAB já cadastrada");
        }

        if(advogadoRepository.findByEmail(newAdv.getEmail()) != null){
            throw new IllegalArgumentException("Email já cadastrado");
        }

        if(newAdv.getSenha().length()< 6){
            throw new IllegalArgumentException("Senha deve ter no mínimo 6 caracteres");
        }
    }

    public boolean validarLogin(String Oab, String senhaFornecida){
        Optional<Advogado> advogadoOptional = advogadoRepository.findByOab(Oab);

        if(advogadoOptional.isPresent()){
            Advogado advogadoDoBanco = advogadoOptional.get();
            String senhaSalva = advogadoDoBanco.getSenha();
    
            return senhaSalva != null && senhaSalva.equals(senhaFornecida);
        }
        return false;
    }

}