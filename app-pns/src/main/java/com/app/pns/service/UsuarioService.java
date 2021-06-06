package com.app.pns.service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.pns.entity.Usuario;
import com.app.pns.repository.UsuarioRepository;

@Service
@Transactional
public class UsuarioService {

	@Autowired
	UsuarioRepository usuarioRepository;

	public List<Usuario> lista() {
		List<Usuario> lista = usuarioRepository.findAll();
		return lista;
	}

	public Optional<Usuario> obtenerPorId(int id) {
		return usuarioRepository.findById(id);
	}
	
	public Optional<Usuario> obtenerPorUserName(String userName) {
		return usuarioRepository.findByUserName(userName);
	}

	public Optional<Usuario> getByUserName(String userName) {
		return usuarioRepository.findByUserName(userName);
	}

	public boolean existePorId(int id) {
		return usuarioRepository.existsById(id);
	}

	public boolean existePorUserName(String userName) {
		return usuarioRepository.existsByUserName(userName);
	}
	
	public boolean existePorNombre(String userName) {
		return usuarioRepository.existsByUserName(userName);
	}

	public boolean existePorEmail(String email) {
		return usuarioRepository.existsByEmail(email);
	}

	public void guardar(Usuario usuario) {
		usuarioRepository.save(usuario);
	}

	public void borrar(int id) {
		usuarioRepository.deleteById(id);
	}
}
