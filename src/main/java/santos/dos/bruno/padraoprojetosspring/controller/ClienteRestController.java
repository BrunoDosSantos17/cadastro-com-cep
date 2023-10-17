package santos.dos.bruno.padraoprojetosspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import santos.dos.bruno.padraoprojetosspring.exception.CepInvaliteException;
import santos.dos.bruno.padraoprojetosspring.exception.IdInvaliteException;
import santos.dos.bruno.padraoprojetosspring.model.Cliente;
import santos.dos.bruno.padraoprojetosspring.service.ClienteService;

@RestController
@RequestMapping("clientes")
public class ClienteRestController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<Iterable<Cliente>> buscarTodos(){
        return ResponseEntity.ok(clienteService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarId(@PathVariable Long id){
        try{
            return ResponseEntity.ok(clienteService.buscarPorId(id));
        }catch (IdInvaliteException ex){
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping
    public ResponseEntity<Cliente> inserir(@RequestBody Cliente cliente){
        try {
            clienteService.inserir(cliente);
            return ResponseEntity.ok(cliente);
        }catch (CepInvaliteException ex){
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity <Cliente> atualizar(@PathVariable Long id, @RequestBody Cliente cliente){
        try {
            clienteService.atualizar(id, cliente);
            return ResponseEntity.ok(cliente);
        }catch (CepInvaliteException | IdInvaliteException ex){
            return ResponseEntity.badRequest().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity <Cliente> deletar(@PathVariable Long id){
        try {
            clienteService.deletar(id);
            return ResponseEntity.ok().build();
        }catch (IdInvaliteException exceptionId){
            return ResponseEntity.badRequest().build();
        }
    }

}
