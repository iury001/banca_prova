package com.provavorp.controller;

import com.provavorp.domain.Cliente;
import com.provavorp.domain.Conto;
import com.provavorp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.util.List;

@RestController
public class ContoController {

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

    // metodo per aprire conto
    @PostMapping("/apriConto/{idCliente}")
    public RedirectView apriConto(@PathVariable Long idCliente) throws Exception {
        Cliente c = clienteRepository.findById(idCliente).orElseThrow(() -> new Exception("id non esiste"));
        List<Conto> conti = c.getConti();
        Conto conto = new Conto();
        conto.setCliente(c);

        Double saldo = 0.0;
        DecimalFormat formatter = new DecimalFormat("#");
        String formattedNumber = formatter.format(saldo);
        double saldoFormattato = Double.parseDouble(formattedNumber);

        conto.setSaldo(saldoFormattato);
        conto.setIban(conto.generateRandomIBAN());
        conti.add(conto);
        c.setConti(conti);
        contoRepository.save(conto);
        Cliente c1 = clienteRepository.save(c);
        return new RedirectView("/gestioneClienteView");
    }

    // metodo per chiudere conto

    @PostMapping("/chiudiConto/{iban}")
    @Transactional
    public RedirectView chiudiConto(@PathVariable String iban) {
        contoRepository.deleteByIban(iban);
        return new RedirectView("/gestioneClienteView");
    }

    // vista di tutti i conti con tutti i dettagli

    @GetMapping("/listaContiView")
    public ModelAndView listaContiView() {
        List<Conto> conti = contoRepository.findAll();
        return new ModelAndView("listaContiView", "conti", conti);
    }

}
