package application;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.logging.Logger;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import application.domain.Cesta;
import application.domain.CestaRQT;
import application.domain.Producto;
import application.eureka.EurekaClient;
import application.service.CestaService;

 
@RestController
public class CestaHTTPController {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	@Autowired
	CestaService cestaService;
	
	
	@Autowired
	private EurekaClient eureka;
	
	@Autowired
	private RestTemplate restTemplate;
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	
	@RequestMapping(
			  value = "/cestas", 
			  method = RequestMethod.GET, 
			  produces = "application/json")
	 public List<Cesta> listaCestasJSON() {
		 return cestaService.getCestas();
	 }
	
	
	@HystrixCommand(fallbackMethod = "fallbackMethod")
	@RequestMapping(
			value = "/cesta/{idProducto}",
			method = RequestMethod.POST
			)
	public void addProductoCesta(@PathVariable(value="idProducto") Integer id) throws IOException, JSONException{
		URI catalogoURI=eureka.getUri("SERVICIO.CATALOGO");
		Producto p = restTemplate.getForObject(catalogoURI.resolve("/productos/"+id),Producto.class);
		//Producto p = restTemplate.getForObject("http://SERVICIO.CATALOGO/productos/"+id,Producto.class);
		Cesta c = new Cesta();
		c.setCantidad(1);
		c.setIdProducto(p.getId());
		c.setMonto(10.0);
		cestaService.addCesta(c);
	}
	
	@RequestMapping(
			value = "/cesta/producto/{id}",
			method = RequestMethod.GET,
			produces = "application/json"
			)
	public Producto getProductoCesta(@PathVariable(value="id") Integer id) throws IOException, JSONException{
		URI catalogoURI=eureka.getUri("SERVICIO.CATALOGO");
		Producto p = restTemplate.getForObject(catalogoURI.resolve("/productos/"+id),Producto.class);
		return p;
	}
	
	@RequestMapping(
			value = "/compra",
			method = RequestMethod.POST
			)
	public void addComprar(@RequestBody CestaRQT peticion) throws IOException, JSONException{
		URI catalogoURI=eureka.getUri("SERVICIO.CATALOGO");
		Producto p = restTemplate.getForObject(catalogoURI.resolve("/productos/"+peticion.getIdProducto()),Producto.class);
		//Producto p = restTemplate.getForObject("http://SERVICIO.CATALOGO/productos/"+id,Producto.class);
		Cesta c = new Cesta();
		c.setCantidad(peticion.getCantidad());
		c.setIdProducto(p.getId());
		c.setMonto(p.getPrecio()*peticion.getCantidad());
		cestaService.addCesta(c);
		
		//Actualizar stock en Catalogo
	
		try 
		{
			restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			JSONObject productoJsonObject = new JSONObject();
			CestaRQT cestaPeticion = new CestaRQT();
			cestaPeticion.setIdProducto(peticion.getIdProducto());
			cestaPeticion.setCantidad(peticion.getCantidad());
			//productoJsonObject.put("idProducto", c.getIdProducto());
			//productoJsonObject.put("cantidad", c.getCantidad());
			//restTemplate.postForObject(catalogoURI.resolve("/actualizarstock"), productoJsonObject, String.class);
			restTemplate.postForObject(catalogoURI.resolve("/actualizarstock"), cestaPeticion, CestaRQT.class);
	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	private void fallbackMethod(Integer id) {

		LOGGER.severe(String.format("Error de conexion al Catalogo. Agregar producto con id[%s]", id));
	}
	
	
	
	
}
