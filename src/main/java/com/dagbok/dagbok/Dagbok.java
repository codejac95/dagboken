package com.dagbok.dagbok;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Dagbok {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String rubrik;
    private String text;
    private LocalDate datum;
    private int raderad;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getRubrik() {
        return rubrik;
    }
    public void setRubrik(String rubrik) {
        this.rubrik = rubrik;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public LocalDate getDatum() {
        return datum;
    }
    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }
    public int getRaderad() {
        return raderad;
    }
    public void setRaderad(int raderad) {
        this.raderad = raderad;
    }
    
}
