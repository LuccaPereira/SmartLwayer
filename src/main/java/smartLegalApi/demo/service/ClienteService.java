package smartLegalApi.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smartLegalApi.demo.Model.Advogado;
import smartLegalApi.demo.Model.Cliente;
import smartLegalApi.demo.Repository.AdvogadoRepository;
import smartLegalApi.demo.Repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {


    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteService(Cliente newCliente){
        if(newCliente.getNomeCompleto() == null || newCliente.getNomeCompleto().isBlank()) { 
            throw new IllegalArgumentException ("Nome invalido!");
        }

        if(newCliente.getCpfCnpj() == null){
            throw new IllegalArgumentException("cpf ou cnpj está vazio ou invalido");
        }

        if(newCliente.getEmail() == null || newCliente.getEmail().isBlank()){
            throw new IllegalArgumentException("Email está vazio");
        }
        if (newCliente.getEndereco() == null || newCliente.getEmail().isBlank()){
            throw new IllegalArgumentException("Endereço está vazio");
        }
        if(clienteRepository.findById(newCliente.getId()) != null){
            throw new IllegalArgumentException("Cliente ja cadastrado!");
        }
        if(newCliente.)
    }
}
