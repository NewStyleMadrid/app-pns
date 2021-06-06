package com.app.pns.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.app.pns.entity.Cita;
import com.app.pns.repository.CitaRepository;

@Service
@Transactional
public class CitaService {
	
	@Autowired
	CitaRepository citaRepository;
	
	public List<Cita> lista() {
		List<Cita> lista = citaRepository.findAll();
		return lista;
	}

	public Optional<Cita> obtenerPorId(int id) {
		return citaRepository.findById(id);
	}

	public void guardar(Cita cita) {
		citaRepository.save(cita);
	}

	public void borrar(int id) {
		citaRepository.deleteById(id);
	}

	public boolean existePorId(int id) {
		return citaRepository.existsById(id);
	}
	
	public boolean existePorFecha(Date fecha) {
		return citaRepository.existsByFecha(fecha);
	}
	
	public boolean existePorHora(Integer hora) {
		return citaRepository.existsByHora(hora);
	}
	
	public Optional<Cita> obtenerPorUserId(String userId) {
		return citaRepository.findByUsuarioId(userId);
	}
}
