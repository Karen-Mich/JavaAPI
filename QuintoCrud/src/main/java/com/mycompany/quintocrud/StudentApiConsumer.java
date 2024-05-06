/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quintocrud;


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
public class StudentApiConsumer implements CrudRepository<Student>{
    
    private final String URL = "http://localhost/Quinto/Controllers/apiEstudiantes.php";

    @Override
    public List<Student> getAll() {
        RestTemplate restTemplate = new RestTemplate();
        Student[] students = restTemplate.getForObject(URL, Student[].class);
        return students != null ? Arrays.asList(students):Collections.emptyList();
    }

    @Override
    public boolean create(Student student) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Student> requestEntity = new HttpEntity<>(student);
        ResponseEntity<Void> responseEntity = restTemplate.exchange(this.URL, HttpMethod.POST, requestEntity, Void.class);
        return responseEntity.getStatusCode() == HttpStatus.OK;
    }
    

    @Override
    public boolean update(Student student) {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.URL)
                .queryParam("cedula", student.getID())
                .queryParam("nombre", student.getFirstName())
                .queryParam("apellido", student.getLastName())
                .queryParam("direccion", student.getAddress())
                .queryParam("telefono", student.getPhone());
        
        String updateUrl = builder.toUriString();
        ResponseEntity<Void> responseEntity = restTemplate.exchange(updateUrl, HttpMethod.PUT, null, Void.class);
        return responseEntity.getStatusCode() == HttpStatus.OK;
        
    }

    @Override
    public boolean delete(String ID) {
        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.URL)
                .queryParam("cedula", ID);

        String deleteUrl = builder.toUriString();
        ResponseEntity<Void> responseEntity = restTemplate.exchange(deleteUrl, HttpMethod.DELETE, null, Void.class);
        return responseEntity.getStatusCode() == HttpStatus.OK;
    }
    
}
