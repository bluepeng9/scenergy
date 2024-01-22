package com.wbm.scenergyspring.domain.tag.entity;

import jakarta.persistence.*;
import lombok.Getter;

/**
 * 0:전체, 1:기타, 2:드럼, 3:베이스, 4:키보드, 5:보컬, 6:클래식, 7:기타
 */
@Entity
@Getter
public class InstrumentTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String InstrumentName;

    public static InstrumentTag createInstrumentTag(String InstrumentName){
        InstrumentTag instrumentTag = new InstrumentTag();
        instrumentTag.InstrumentName = InstrumentName;
        return instrumentTag;
    }

}
