package com.app.pns.service;

import com.app.pns.entity.Corte;
import com.app.pns.repository.CorteRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CorteService {
	
	@Autowired
	CorteRepository corteRepository;
	
	Cloudinary cloudinary;

	private Map<String, String> map = new HashMap<>();
	
	
	// Metodo para la subida de imagen a Cloudinary.
	public CorteService() {
		// Estos tres datos lo sacamos de nuestra cuenta de Cloudinary
		map.put("cloud_name", "new-style");
		map.put("api_key", "769189814492811");
		map.put("api_secret", "QpZY_vxAIGT9BfPZqb-UnUgZk6U");
		
		cloudinary = new Cloudinary(map);
	}
	
	@SuppressWarnings("rawtypes")
	public Map upload(MultipartFile multiparfile) throws IOException {
		File file= convert(multiparfile);
		Map resultado=cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
		file.delete();
		return resultado;
	}
	
	@SuppressWarnings("rawtypes")
	public Map delete(String id) throws IOException {
		Map resultado=cloudinary.uploader().destroy(id,ObjectUtils.emptyMap());
		return resultado;
	}
	
	public File convert(MultipartFile multiparfile) throws IOException {
		File file=new File(multiparfile.getOriginalFilename());
		FileOutputStream os= new FileOutputStream(file);
		os.write(multiparfile.getBytes());
		os.close();
		
		return file;
	}
	
	/************************ FIN METODOS IMAGEN ************************/
	public List<Corte> lista() {
		return corteRepository.findByOrderById();
	}

	public Optional<Corte> getOne(int id) {
		return corteRepository.findById(id);
	}

	public void guardar(Corte corte) {
		corteRepository.save(corte);
	}

	public void borrar(int id) {
		corteRepository.deleteById(id);
	}

	public boolean exists(int id) {
		return corteRepository.existsById(id);
	}

	public boolean existePorNombre(String name) {
		return corteRepository.existsByName(name);
	}

	public Optional<Corte> obtenerPorNombre(String name) {
		return corteRepository.findByName(name);
	}

	public Optional<Corte> obtenerPorId(int id) {
		return corteRepository.findById(id);
	}
}
