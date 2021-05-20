package com.example.demo.controller;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Persona;
import com.example.demo.repository.PersonaRepository;


@RestController
@RequestMapping(value="personas")
public class PersonaController {
	
	@Autowired
	PersonaRepository repository;
	
	//GET Persona
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public Collection<Persona> getListaPersonas(){
		Iterable<Persona> listaPersonas = repository.findAll();
		
		return (Collection<Persona>) listaPersonas;
		}
	
	
	//Persona 
	@GetMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Persona getPersona(@PathVariable(name = "id") Long id) {
		
		Optional<Persona> Persona = repository.findById(id);
		Persona result = null;
		if(Persona.isPresent()) {
			result = Persona.get();
		}
		return result;	
	}
	
	//POST Persona
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Persona createPersona(@RequestBody Persona persona ) {
		Persona nuevoPersona = repository.save(persona);
		return nuevoPersona;
	}
	
	//PUT Persona
	@PutMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public Persona updateCliente(@PathVariable(name = "id") Long id, 
			@RequestBody Persona persona) {
			Optional<Persona> oPersona = repository.findById(id);
			if(oPersona.isPresent()) {
				Persona actual = oPersona.get(); 
				actual.setId(id);
				actual.setNombre(persona.getNombre());
				actual.setApellido(persona.getApellido());
				actual.setCorreo(persona.getCorreo());
				actual.setTelefono(persona.getTelefono());
				Persona updatedPersona = repository.save(actual);
				return updatedPersona;
			}
			return null;
		}
	}
