package com.bilgeadam.personelapp.controller;

import com.bilgeadam.personelapp.entity.Personel;
import com.bilgeadam.personelapp.exception.PersonelNotFound;
import com.bilgeadam.personelapp.pojo.Bolum;
import com.bilgeadam.personelapp.pojo.Sehir;
import com.bilgeadam.personelapp.repository.PersonelRepository;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.converters.Auto;
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
    private EurekaClient client;

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

    @GetMapping("/personel-tam/{id}")
    public String getPersonelTam(@PathVariable("id") long no) {

        Personel personel = getPersonel(no);

        Bolum bolum = getBolum(personel.getBolumNo());
        Sehir sehir = getSehir(bolum.getSehirNo());

        return personel.getNo() + " " + personel.getAd() + " " + personel.getSoyad()
                + " " + bolum.getAd() + " " + sehir.getAd();
    }

    @GetMapping("/personel/bolum/{bolum-no}")
    public List<Personel> getPersonelByBolumNo(@PathVariable("bolum-no") long bolumNo) {

        return personelRepository.findByBolumNo(bolumNo);
    }

    private Bolum getBolum(long bolumNo) {

        //String bolumURL = "http://localhost:8230";

        RestTemplate restTemplate = new RestTemplate();
        InstanceInfo instanceInfo = client.getNextServerFromEureka("bolum-app", false);
        String bolumURL = instanceInfo.getHomePageUrl();

        Bolum bolum = restTemplate.getForObject(bolumURL+"/bolum/" + bolumNo, Bolum.class);

        return bolum;
    }

    private Sehir getSehir(long sehirNo) {

        //String sehirURL = "http://localhost:8240";

        RestTemplate restTemplate = new RestTemplate();
        InstanceInfo instanceInfo = client.getNextServerFromEureka("sehir-app", false);
        String sehirURL = instanceInfo.getHomePageUrl();

        Sehir sehir = restTemplate.getForObject(sehirURL+"/sehir/" + sehirNo, Sehir.class);

        return sehir;
    }

}
