package com.app.pns.service;

import com.app.pns.entity.GaleriaHome;
import com.app.pns.repository.GaleriaRepository;
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
public class GaleriaService {
	
	@Autowired
	GaleriaRepository galeriaRepository;
	
	Cloudinary cloudinary;

	private Map<String, String> map = new HashMap<>();
	
	
	// Metodo para la subida de imagen a Cloudinary.
	public GaleriaService() {
		// Estos tres datos lo sacamos de nuestra cuenta de Cloudinary
		map.put("cloud_name", "new-style-home");
		map.put("api_key", "643956122774151");
		map.put("api_secret", "Hd6PuXRo7IFAeySYsIrlHTzhzKc");
		
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
	public List<GaleriaHome> lista() {
		return galeriaRepository.findByOrderById();
	}

	public Optional<GaleriaHome> getOne(int id) {
		return galeriaRepository.findById(id);
	}

	public void guardar(GaleriaHome galeriaHome) {
		galeriaRepository.save(galeriaHome);
	}

	public void borrar(int id) {
		galeriaRepository.deleteById(id);
	}

	public boolean exists(int id) {
		return galeriaRepository.existsById(id);
	}

	public boolean existePorNombre(String name) {
		return galeriaRepository.existsByName(name);
	}

	public Optional<GaleriaHome> obtenerPorNombre(String name) {
		return galeriaRepository.findByName(name);
	}

	public Optional<GaleriaHome> obtenerPorId(int id) {
		return galeriaRepository.findById(id);
	}
}
