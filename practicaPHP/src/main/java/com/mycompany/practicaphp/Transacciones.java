/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practicaphp;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author DELL
 */
public class Transacciones {
    @JsonProperty("id_tra")
    private String id_tra;
    @JsonProperty("mov_tra")
    private String mov_tra;
    @JsonProperty("mon_tra")
    private String mon_tra;
    @JsonProperty("fec_tra")
    private String fec_tra;
    @JsonProperty("id_cue_per")
    private String id_cue_per;


    public Transacciones(String id_tra, String mov_tra, String mon_tra, String fec_tra, String id_cue_per) {
        this.id_tra = id_tra;
        this.mov_tra = mov_tra;
        this.mon_tra = mon_tra;
        this.fec_tra = fec_tra;
        this.id_cue_per = id_cue_per;
    }

    public String getId_tra() {
        return id_tra;
    }

    public String getMov_tra() {
        return mov_tra;
    }

    public String getMon_tra() {
        return mon_tra;
    }

    public String getFec_tra() {
        return fec_tra;
    }

    public String getId_cue_per() {
        return id_cue_per;
    }

    @Override
    public String toString() {
        return "Transacciones{" + "id_tra=" + id_tra + ", mov_tra=" + mov_tra + ", mon_tra=" + mon_tra + ", fec_tra=" + fec_tra + ", id_cue_per=" + id_cue_per + '}';
    }
    
    public Transacciones() {
    }

   
    
    
    
}
