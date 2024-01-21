package com.wbm.scenergyspring.domain.tag.entity;

import jakarta.persistence.*;

import java.util.List;

/**
 * 0:전체, 1:서울, 2:인천, 3:대전, 4:부산, 5:울산, 6:대구, 7:광주, 8:경기, 9:강원,
 * 10:충북, 11:춤남, 12:전북, 13:전남, 14:경북, 15:경남, 16:세종, 17:제주
 */
@Entity
public class LocationTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String locationName;

}
