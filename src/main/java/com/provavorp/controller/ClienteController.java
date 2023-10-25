package com.provavorp.controller;

import com.provavorp.domain.Cliente;
import com.provavorp.domain.Conto;
import com.provavorp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@RestController
public class ClienteController {
    @Autowired
    BonificoRepository bonificoRepository;
    @Autowired
    VersamentoRepository versamentoRepository;
    @Autowired
    PrelievoRepository prelievoRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ContoRepository contoRepository;



    //metodo per generare data e ora

    public String impostaDataEora() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        ZoneId europe = ZoneId.of("Europe/Rome");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss").withZone(europe).withLocale(Locale.ITALY);
        String dataEora = formatter.format(localDateTime);
        return dataEora;
    }

    // pagina home

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("home");
    }





    // metodo per aggiungere cliente

    @PostMapping("/aggiungiCliente")
    public RedirectView aggiungiCliente(@RequestParam String nome, @RequestParam String cognome) {
        Cliente clienteSalvato = new Cliente();
        clienteSalvato.setNome(nome);
        clienteSalvato.setCognome(cognome);
        clienteRepository.save(clienteSalvato);
        return new RedirectView("gestioneClienteView");
    }

    // vista card clienti con pulsanti

    @GetMapping("/gestioneClienteView")
    public ModelAndView aggiungiClienteView() {
        List<Cliente> clienti = clienteRepository.findAll();
        return new ModelAndView("gestioneClienteView", "clienti", clienti);
    }

    // metodo per modificare nome e cognome cliente

    @PostMapping("/modificaCliente/{idCliente}")
    public RedirectView modificaCliente(@PathVariable Long idCliente, @RequestParam String nome, @RequestParam String cognome) throws Exception {
        Cliente clienteById = clienteRepository.findById(idCliente).orElseThrow(() -> new Exception("id non esiste"));
        clienteById.setNome(nome);
        clienteById.setCognome(cognome);
        Cliente c = clienteRepository.save(clienteById);
        return new RedirectView("/gestioneClienteView");
    }

    // metodo per cancellare cliente

    @PostMapping("/cancellaCliente/{idCliente}")
    @Transactional
    public RedirectView cancellaCliente(@PathVariable Long idCliente) throws Exception {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(() -> new Exception("id non esiste"));
        List<Conto> conti = cliente.getConti();
        if (!conti.isEmpty()) {
            for (Conto c : conti) {
                contoRepository.deleteByIban(c.getIban());
            }
            cliente.setConti(conti);
            clienteRepository.save(cliente);
        }
        clienteRepository.delete(cliente);
        return new RedirectView("/gestioneClienteView");
    }





    // metodo per rimuovere conto da un cliente

    @PostMapping("/rimuoviContoCliente/{idConto}")
    @Transactional
    public RedirectView rimuoviContoCliente(@PathVariable Long idConto) throws Exception {
        List<Cliente> clienti = clienteRepository.findAll();
        Conto c1 = contoRepository.findById(idConto).orElseThrow(() -> new Exception("id non esiste"));
        for (Cliente c : clienti) {
            if (c.getConti().contains(c1)) {
                c.getConti().remove(c1);
                clienteRepository.save(c);
                break;
            }
        }
        contoRepository.delete(c1);
        return new RedirectView("/gestioneClienteView");
    }

// metodo che ritorna tutti i conti di un cliente

    @GetMapping("/listaContiCliente/{idCliente}")
    public ResponseEntity<List<Conto>> listaContiCliente(@PathVariable Long idCliente) throws Exception {
        Cliente c = clienteRepository.findById(idCliente).orElseThrow(() -> new Exception("id non esiste"));
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
