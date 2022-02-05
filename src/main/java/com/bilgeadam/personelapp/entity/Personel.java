package com.bilgeadam.personelapp.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Personel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long no;
    private String ad;
    private String soyad;
    private long bolumNo;
}
