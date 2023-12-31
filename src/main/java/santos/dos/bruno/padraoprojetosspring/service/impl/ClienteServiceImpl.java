package santos.dos.bruno.padraoprojetosspring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santos.dos.bruno.padraoprojetosspring.exception.CepInvaliteException;
import santos.dos.bruno.padraoprojetosspring.exception.IdInvaliteException;
import santos.dos.bruno.padraoprojetosspring.model.Cliente;
import santos.dos.bruno.padraoprojetosspring.model.ClienteRepository;
import santos.dos.bruno.padraoprojetosspring.model.Endereco;
import santos.dos.bruno.padraoprojetosspring.model.EnderecoRepository;
import santos.dos.bruno.padraoprojetosspring.service.ClienteService;
import santos.dos.bruno.padraoprojetosspring.service.ViaCepService;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ViaCepService viaCepService;

    @Override
    public Iterable<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) throws IdInvaliteException{
        if(clienteRepository.findById(id).isPresent()){
            return clienteRepository.findById(id).get();
        }else{
            throw new IdInvaliteException();
        }

    }

    @Override
    public void inserir(Cliente cliente) throws CepInvaliteException{
        salvarCliente(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) throws CepInvaliteException, IdInvaliteException{
        Optional<Cliente> clienteBd = clienteRepository.findById(id);
        if(clienteBd.isPresent()){
            cliente.setId(id);
            salvarCliente(cliente);
        }else {
            throw new IdInvaliteException();
        }
    }

    @Override
    public void deletar(Long id) throws IdInvaliteException{
        if(clienteRepository.findById(id).isPresent()){
            clienteRepository.deleteById(id);
        }else {
            throw new IdInvaliteException();
        }

    }

    private void salvarCliente(Cliente cliente) throws CepInvaliteException{
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            if(novoEndereco.getCep() == null) {
                throw new CepInvaliteException();
            }
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });
        cliente.setEndereco(endereco);
        clienteRepository.save(cliente);
    }
}
