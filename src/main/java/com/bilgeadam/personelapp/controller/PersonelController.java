package com.bilgeadam.personelapp.controller;

import com.bilgeadam.personelapp.entity.Personel;
import com.bilgeadam.personelapp.exception.PersonelNotFound;
import com.bilgeadam.personelapp.repository.PersonelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
public class PersonelController {

    @Autowired
    private PersonelRepository personelRepository;

    @GetMapping("/personel/{id}")
    public Personel getPersonel(@PathVariable("id") long personelNo) {

        Personel personel = null;
        Optional<Personel> personelDB = personelRepository.findById(personelNo);

        if(personelDB.isPresent()) {
            personel = personelDB.get();
            return personel;
        } else {
            throw new PersonelNotFound(personelNo + " nolu Personel bulunamadı.");
        }

    }

    @GetMapping("/personel")
    public List<Personel> getPersonelList() {
        return personelRepository.findAll();
    }

    @GetMapping("/personel-bolum/{id}")
    public String getPersonelWithBolumSehir(@PathVariable("id") long no) {

        Personel personel = getPersonel(no);

        String bolumAd = getBolumAd(personel.getNo());

        return personel.getNo() + " " + personel.getAd() + " " + personel.getSoyad()
                + " " + bolumAd;
    }

    private String getBolumAd(long no) {

        String bolumURL = "http://localhost:8230";

        RestTemplate restTemplate = new RestTemplate();

        String bolumAd = restTemplate.getForObject(bolumURL+"/bolum-ad/" + no, String.class);

        return bolumAd;
    }

}
