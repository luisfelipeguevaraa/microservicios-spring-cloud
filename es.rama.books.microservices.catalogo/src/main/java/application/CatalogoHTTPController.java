package application;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import application.domain.Producto;
import application.domain.ProductoRQT;
import application.service.ProductoService;
 
@RestController
public class CatalogoHTTPController {
	
	@Autowired
	ProductoService productoService;
	
	public CatalogoHTTPController(){
		super();
	}
	 
	@RequestMapping(
			  value = "/productos", 
			  method = RequestMethod.GET, 
			  produces = "application/json")
	 public List<Producto> listaProductosJSON() {
		 return productoService.getProductos();
	 }
	
	@RequestMapping(
			  value = "/productos/{id}", 
			  method = RequestMethod.GET,
			  produces = "application/json"
			  )
	 public Producto getProductosJSON(@PathVariable(value="id") Integer id) {
		/*int i=0; 
		while(i>=0){
			//throw new RuntimeException("this command always fails");
			System.out.println(i);
			i++;
		 }*/
		 return productoService.getProductoById(id);
	 }
	
	@RequestMapping(
			  value = "/productos/{id}", 
			  method = RequestMethod.DELETE
			  )
	 public void delProducto(@PathVariable(value="id") Integer id) {
		productoService.delProductoById(id);
	 }
	
	
	@RequestMapping(
			  value = "/productos", 
			  method = RequestMethod.POST,
			  consumes = "application/json"
			  )
	 public void addProducto(@RequestBody Producto p) {
		productoService.addProducto(p);
	 }
	
	@RequestMapping(
			  value = "/actualizarstock", 
			  method = RequestMethod.POST,
			  consumes = "application/json"
			  )
	 public void actualizarStock(@RequestBody ProductoRQT p) {
		Producto producto = productoService.getProductoById(p.getIdProducto());
		producto.setStock(producto.getStock() - p.getCantidad());
		productoService.save(producto);
	 }
	
}
