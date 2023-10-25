package com.provavorp.controller;

import com.provavorp.domain.Bonifico;
import com.provavorp.domain.Conto;
import com.provavorp.domain.Prelievo;
import com.provavorp.domain.Versamento;
import com.provavorp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@RestController
public class OperazioneController {
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

    // vista per fare prelievi e vedere ultimi 7 prelievi
    @GetMapping("/prelievoView")
    public ModelAndView prelievoView() {
        List<Conto> conti = contoRepository.findAll();
        List<Prelievo> prelieviTot = prelievoRepository.findAll();
        List<Prelievo> ultimiSeiPrelievi = new ArrayList<>();

        for (Prelievo p : prelieviTot) {
            if (ultimiSeiPrelievi.size() > 6) {
                ultimiSeiPrelievi.remove(0);
            }
            ultimiSeiPrelievi.add(p);
        }

        Collections.reverse(ultimiSeiPrelievi);


        ModelAndView modelAndView = new ModelAndView("prelievoView");
        modelAndView.addObject("conti", conti);
        modelAndView.addObject("prelievi", ultimiSeiPrelievi);
        return modelAndView;
    }

    // metodo per effettuare bonifici

    @PostMapping("/bonifico")
    @Transactional
    public RedirectView bonifico(@RequestParam String ibanMittente, @RequestParam Double importo, @RequestParam String ibanDestinatario, HttpSession session) {
        try {
            Conto contoMittente = contoRepository.findByIban(ibanMittente);
            Conto contoDestinatario = contoRepository.findByIban(ibanDestinatario);

            if (contoMittente == null || contoDestinatario == null) {
                throw new RuntimeException("Conto non esistente");
            }

            if (contoMittente.getSaldo() < importo) {
                session.setAttribute("errorMessage", "saldo insufficiente");
                return new RedirectView("/bonificoView");
            }

            Double saldoMittente = contoMittente.getSaldo() - importo;
            Double saldoDestinatario = contoDestinatario.getSaldo() + importo;

            contoMittente.setSaldo(saldoMittente);
            contoRepository.save(contoMittente);

            contoDestinatario.setSaldo(saldoDestinatario);
            contoRepository.save(contoDestinatario);

            String dataEora = impostaDataEora();

            DecimalFormat formatter = new DecimalFormat("#");
            String formattedNumber = formatter.format(importo);
            double importoFormattato = Double.parseDouble(formattedNumber);

            Bonifico bonifico = new Bonifico(ibanMittente, ibanDestinatario, contoMittente.getCliente().getNome(), contoMittente.getCliente().getCognome(), contoDestinatario.getCliente().getNome(), contoDestinatario.getCliente().getCognome(), importoFormattato, dataEora);
            bonificoRepository.save(bonifico);

            return new RedirectView("/bonificoView");
        } catch (Exception e) {
            session.setAttribute("errorMessage", "dati non validi");
            return new RedirectView("/bonificoView");
        }
    }


    // vista per fare versamenti e vedere ultimi 7 versamenti

    @GetMapping("/versamentoView")
    public ModelAndView versamentoView() {
        List<Conto> conti = contoRepository.findAll();
        List<Versamento> versamentiTot = versamentoRepository.findAll();
        List<Versamento> ultimiSeiVersamenti = new ArrayList<>();

        for (Versamento v : versamentiTot) {
            if (ultimiSeiVersamenti.size() > 6) {
                ultimiSeiVersamenti.remove(0);
            }
            ultimiSeiVersamenti.add(v);
        }
        Collections.reverse(ultimiSeiVersamenti);

        ModelAndView modelAndView = new ModelAndView("versamentoView");
        modelAndView.addObject("conti", conti);
        modelAndView.addObject("versamenti", ultimiSeiVersamenti);
        return modelAndView;
    }

    // metodo pre fare versamenti

    @PostMapping("/versamento")
    public RedirectView versamento(@RequestParam String iban, @RequestParam Double importo) {
        Conto conto = contoRepository.findByIban(iban);

        Double saldo = conto.getSaldo() + importo;

        conto.setSaldo(saldo);
        contoRepository.save(conto);

        String dataEora = impostaDataEora();
        DecimalFormat formatter = new DecimalFormat("#");
        String formattedNumber = formatter.format(importo);
        double importoFormattato = Double.parseDouble(formattedNumber);
        Versamento versamento = new Versamento(iban, conto.getCliente().getNome(), conto.getCliente().getCognome(), importoFormattato, dataEora);
        versamentoRepository.save(versamento);

        return new RedirectView("/versamentoView");
    }

    // metodo pre fare prelievi

    @PostMapping("/prelievo")
    public RedirectView prelievo(@RequestParam String iban, @RequestParam Double importo) {
        Conto conto = contoRepository.findByIban(iban);

        Double saldo = conto.getSaldo() - importo;

        conto.setSaldo(saldo);
        contoRepository.save(conto);

        String dataEora = impostaDataEora();
        DecimalFormat formatter = new DecimalFormat("#");
        String formattedNumber = formatter.format(importo);
        double importoFormattato = Double.parseDouble(formattedNumber);

        Prelievo prelievo = new Prelievo(iban, conto.getCliente().getNome(), conto.getCliente().getCognome(), importoFormattato, dataEora);
        prelievoRepository.save(prelievo);

        return new RedirectView("/prelievoView");
    }

    // vista per fare bonifi e vedere ultimi 7 bonifici

    @GetMapping("/bonificoView")
    public ModelAndView bonificoView(HttpSession session) {
        List<Conto> conti = contoRepository.findAll();
        List<Bonifico> bonificiTotali = bonificoRepository.findAll();
        List<Bonifico> ultimiSeiBonifici = new ArrayList<>();
        for (Bonifico b : bonificiTotali) {
            if (ultimiSeiBonifici.size() > 6) {
                ultimiSeiBonifici.remove(0);
            }
            ultimiSeiBonifici.add(b);
        }

        Collections.reverse(ultimiSeiBonifici);
        ModelAndView modelAndView = new ModelAndView("bonificoView");
        modelAndView.addObject("conti", conti);
        modelAndView.addObject("bonifici", ultimiSeiBonifici);
        return modelAndView;
    }

    // vista con accordion con storico operazioni

    @GetMapping("/storicoOperazioniView")
    public ModelAndView storicoOperazioniView() {
        List<Versamento> versamenti = versamentoRepository.findAll();
        List<Prelievo> prelievi = prelievoRepository.findAll();
        List<Bonifico> bonifici = bonificoRepository.findAll();
        Collections.reverse(versamenti);
        Collections.reverse(prelievi);
        Collections.reverse(bonifici);

        ModelAndView modelAndView = new ModelAndView("storicoOperazioniView");
        modelAndView.addObject("versamenti", versamenti);
        modelAndView.addObject("prelievi", prelievi);
        modelAndView.addObject("bonifici", bonifici);

        return modelAndView;
    }
}
