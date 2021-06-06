package com.app.pns.service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.pns.entity.Servicio;
import com.app.pns.repository.ServicioRepository;

@Service
@Transactional
public class ServicioService {

	@Autowired
	ServicioRepository servicioRepository;

	public List<Servicio> lista() {
		List<Servicio> lista = servicioRepository.findAll();
		return lista;
	}

	public Optional<Servicio> obtenerPorId(int id) {
		return servicioRepository.findById(id);
	}

	public Optional<Servicio> obtenerPorTipoServicio(String tipoServicio) {
		return servicioRepository.findByTipoServicio(tipoServicio);
	}

	public void guardar(Servicio servicio) {
		servicioRepository.save(servicio);
	}

	public void borrar(int id) {
		servicioRepository.deleteById(id);
	}

	public boolean existePorTipoServicio(String tipoServicio) {
		return servicioRepository.existsByTipoServicio(tipoServicio);
	}

	public boolean existePorId(int id) {
		return servicioRepository.existsById(id);
	}
}
