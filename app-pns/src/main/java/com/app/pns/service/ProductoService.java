package com.app.pns.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.app.pns.entity.Producto;
import com.app.pns.repository.ProductoRepository;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductoService {

	@Autowired
	ProductoRepository productoRepository;
	/*
	Cloudinary cloudinary;

	private Map<String, String> map = new HashMap<>();

	// Metodo para la subida de imagen a Cloudinary.
	public ProductoService() {
		// Estos tres datos lo sacamos de nuestra cuenta de Cloudinary
		map.put("cloud_name", "pns-producto");
		map.put("api_key", "231454281695766");
		map.put("api_secret", "fgRxn1-6KXQPMEkFiI_XnrBRmlM");

		cloudinary = new Cloudinary(map);
	}
	
	public Optional<Producto> getOne(int id) {
		return productoRepository.findById(id);
	}

	@SuppressWarnings("rawtypes")
	public Map upload(MultipartFile multiparfile) throws IOException {
		File file = convert(multiparfile);
		Map resultado = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
		file.delete();
		return resultado;
	}

	@SuppressWarnings("rawtypes")
	public Map delete(String id) throws IOException {
		Map resultado = cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
		return resultado;
	}

	public File convert(MultipartFile multiparfile) throws IOException {
		File file = new File(multiparfile.getOriginalFilename());
		FileOutputStream os = new FileOutputStream(file);
		os.write(multiparfile.getBytes());
		os.close();

		return file;
	}*/

	/************************ FIN METODOS IMAGEN ************************/
	
	public List<Producto> lista() {
		List<Producto> lista = productoRepository.findAll();
		return lista;
	}

	public Optional<Producto> obtenerPorId(int id) {
		return productoRepository.findById(id);
	}

	public Optional<Producto> obtenerPorNombre(String nombre) {
		return productoRepository.findByNombre(nombre);
	}

	public void guardar(Producto producto) {
		productoRepository.save(producto);
	}

	public void borrar(int id) {
		productoRepository.deleteById(id);
	}

	public boolean existePorNombre(String nombre) {
		return productoRepository.existsByNombre(nombre);
	}

	public boolean existePorId(int id) {
		return productoRepository.existsById(id);
	}
}