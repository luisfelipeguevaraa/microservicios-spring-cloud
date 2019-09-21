package application.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class Cesta {	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;    
    @Column(name="IDPRODUCTO", nullable=false)
	private Integer idProducto;    
    @Column(name="MONTO", nullable=false)
	private Double monto;    
    @Column(name="CANTIDAD", nullable=false)
	private Integer cantidad;
    
	
	public Cesta(){
		super();
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getIdProducto() {
		return idProducto;
	}


	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}


	public Double getMonto() {
		return monto;
	}


	public void setMonto(Double monto) {
		this.monto = monto;
	}


	public Integer getCantidad() {
		return cantidad;
	}


	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

}

