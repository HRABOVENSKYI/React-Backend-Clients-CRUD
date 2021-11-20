package org.example.controllers;

import lombok.AllArgsConstructor;
import org.example.dao.ClientRepository;
import org.example.models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/clients")
@AllArgsConstructor
public class ClientController {

    private final ClientRepository clientRepository;

    @GetMapping
    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    @GetMapping("/{id}")
    public Client getClient(@PathVariable Long id) {
        return clientRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PostMapping
    public ResponseEntity createClient(@RequestBody Client client) throws URISyntaxException {
        Client savedClient = clientRepository.save(client);
        return ResponseEntity.created(new URI("/clients/" + savedClient.getId())).body(savedClient);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateClient(@PathVariable Long id, @RequestBody Client client) {
        Client currentClient = clientRepository.findById(id).orElseThrow(RuntimeException::new);
        currentClient.setName(client.getName());
        currentClient.setEmail(client.getEmail());
        currentClient = clientRepository.save(client);

        return ResponseEntity.ok(currentClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteClient(@PathVariable Long id) {
        clientRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
