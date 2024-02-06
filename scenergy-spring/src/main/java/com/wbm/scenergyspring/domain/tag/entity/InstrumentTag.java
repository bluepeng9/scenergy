package com.wbm.scenergyspring.domain.tag.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

/**
 * 1:전체, 2:기타, 3:드럼, 4:베이스, 5:키보드, 6:보컬, 7:클래식, 8:기타
 */
@Entity
@Getter
public class InstrumentTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String instrumentName;

    public void updateInstrumentTag(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public static InstrumentTag createInstrumentTag(String InstrumentName){
        InstrumentTag instrumentTag = new InstrumentTag();
        instrumentTag.instrumentName = InstrumentName;
        return instrumentTag;
    }

}
