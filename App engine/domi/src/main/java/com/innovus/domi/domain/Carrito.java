package com.innovus.domi.domain;

import static com.innovus.domi.service.OfyService.ofy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import com.innovus.domi.form.CarritoForm;

@Entity
public class Carrito {
	@Id
	private long idCarrito;
	 @Parent
	 @ApiResourceProperty(ignored=AnnotationBoolean.TRUE)
	 private Key<Sucursal> sucursalKey;	 
	 
	 @ApiResourceProperty(ignored=AnnotationBoolean.TRUE)
	 private long idSucursal;
	 @Index 
     private String websafeKeyUsuario;
	 private List<String> listaPedidosKey = new ArrayList<>(0);	 
	 private String observacion;
	 private GeoPt ubicacion;
     private String formaDePago;
     private Boolean estado;    
     private Date fechaHora;
     private float total;
     
     private Carrito(){}
     
    
     public Carrito(long idCarrito,CarritoForm carritoForm,String websafeKeySucursal) {
		
		this.idCarrito = idCarrito;
		
		ActualizarCarrito(carritoForm);
	   	 
	   	 //creamos llaves padres
	   	 this.sucursalKey=Key.create(websafeKeySucursal);	
	  
	   	 //creamos padres cn las llaves padres
		 Sucursal sucursal = ofy().load().key(this.sucursalKey).now();	 
		 //obtenemos ids padres
		 
		 this.idSucursal = sucursal.getIdSucursal();	 
		 this.estado = true;
		 this.fechaHora = new Date();
	
	}

     @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
	public long getIdCarrito() {
		return idCarrito;
	}



	public void setIdCarrito(long idCarrito) {
		this.idCarrito = idCarrito;
	}



	public String getWebsafeKeyUsuario() {
		return websafeKeyUsuario;
	}



	public void setWebsafeKeyUsuario(String websafeKeyUsuario) {
		this.websafeKeyUsuario = websafeKeyUsuario;
	}



	public List<String> getListaPedidosKey() {
		return listaPedidosKey;
	}



	public void setListaPedidosKey(List<String> listaPedidosKey) {
		this.listaPedidosKey = listaPedidosKey;
	}



	public String getObservacion() {
		return observacion;
	}



	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}



	public GeoPt getUbicacion() {
		return ubicacion;
	}



	public void setUbicacion(GeoPt ubicacion) {
		this.ubicacion = ubicacion;
	}



	public String getFormaDePago() {
		return formaDePago;
	}



	public void setFormaDePago(String formaDePago) {
		this.formaDePago = formaDePago;
	}



	public Boolean getEstado() {
		return estado;
	}



	public void setEstado(Boolean estado) {
		this.estado = estado;
	}



	public Date getFechaHora() {
		return fechaHora;
	}



	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}



	public float getTotal() {
		return total;
	}



	public void setTotal(float total) {
		this.total = total;
	}
	
	 
	 
	 @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
	 public Key<Sucursal> getSucursalKey(){
		 return sucursalKey;
		 
	 }
	 
	 public String getWebsafeKey(){
		 return Key.create(sucursalKey, Carrito.class,idCarrito).getString();
	 }

	public void ActualizarCarrito(CarritoForm carritoForm){
    	 this.total=carritoForm.getTotal();
    	 this.observacion=carritoForm.getObservacion();
    	 this.ubicacion = carritoForm.obtenerUbicacion();
    	 this.formaDePago=carritoForm.getFormaDePago();
    	 this.websafeKeyUsuario = carritoForm.getWebsafeKeyUsuario();
    	
     }
	
	public void addPedidoKeys(String pedidoKey){
		listaPedidosKey.add(pedidoKey);
//				
	}
	/*public void addPedidosKeys(List<String> listaKeysPedidos){
		for (String keyString : listaKeysPedidos) {
			listaKeysPedidos.add(keyString);
	    }
	}*/
	public void addPedidosKeys(List<Pedido> listaKeysPedidos){
		for (Pedido keyString : listaKeysPedidos) {
			this.listaPedidosKey.add(keyString.getWebsafeKey());
			
	    }
	}
	
}
