/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practicaphp;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author DELL
 */
public class TransaccionesApi implements CrudRepository<Transacciones>{

    private final String URL = "http://localhost/bancoAPI/Controllers/apiBanco.php";
    
    

    @Override
    public boolean delete(String ID) {
RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.URL)
                .queryParam("id_tra", ID);

        String deleteUrl = builder.toUriString();
        ResponseEntity<Void> responseEntity = restTemplate.exchange(deleteUrl, HttpMethod.DELETE, null, Void.class);
        return responseEntity.getStatusCode() == HttpStatus.OK;
    }

    @Override
    public boolean create(Transacciones transacciones) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Transacciones> requestEntity = new HttpEntity<>(transacciones);
        ResponseEntity<Void> responseEntity = restTemplate.exchange(this.URL, HttpMethod.POST,requestEntity,Void.class);
        return responseEntity.getStatusCode()==HttpStatus.OK;
    }

    @Override
    public boolean update(Transacciones transacciones) {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.URL)
        .queryParam("id_tra", transacciones.getId_tra())
                .queryParam("mov_tra", transacciones.getMov_tra())
                .queryParam("mon_tra", transacciones.getMon_tra())
                .queryParam("fec_tra", transacciones.getFec_tra())
                .queryParam("id_cue_per", transacciones.getId_cue_per());
        
        String updateUrl = builder.toUriString();
        ResponseEntity<Void> responseEntity = restTemplate.exchange(updateUrl, HttpMethod.PUT, null, Void.class);
        return responseEntity.getStatusCode() == HttpStatus.OK;          
    }

    @Override
    public List<Transacciones> getAll() {
        RestTemplate restTemplate = new RestTemplate();
        Transacciones[] transacciones = restTemplate.getForObject(URL, Transacciones[].class);
        return transacciones != null ? Arrays.asList(transacciones):Collections.emptyList();
    }
    
}
