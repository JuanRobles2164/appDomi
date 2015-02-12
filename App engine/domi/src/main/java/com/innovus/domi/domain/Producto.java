package com.innovus.domi.domain;
import static com.innovus.domi.service.OfyService.ofy;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.google.common.base.Preconditions;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import com.innovus.domi.form.ProductoForm;


@Entity
public class Producto {
	
	 @Id
	 private long idProducto;
	 
	 @Parent
	 @ApiResourceProperty(ignored=AnnotationBoolean.TRUE)
	 private Key<Categoria> categoriaKey;
	 
	/*
	 @ApiResourceProperty(ignored=AnnotationBoolean.TRUE)
	 private Key<Empresa> empresaKey;
	 */
	 
	 @ApiResourceProperty(ignored=AnnotationBoolean.TRUE)
	 private long idCategoria;
	 
	 /*
	 @ApiResourceProperty(ignored=AnnotationBoolean.TRUE)
	 private long idEmpresa;
	 */
	 
	 @Index
	 private String nombreProducto;
	 
	 @ApiResourceProperty(ignored=AnnotationBoolean.TRUE)
	 private String websafeCategoriaKey;
	 
	 
	 private String descripcionProducto;
	 
	 private int precioProducto;
	 
	 public Producto(){}
	 
	 public Producto(final long idProducto,final String websafeCategoriaKey,final ProductoForm objetoProducto){
		 //Preconditions.checkNotNull(objetoProducto.getNombreProducto(), "El nombre es requerido");
	   	 //Preconditions.checkNotNull(objetoProducto.getDescripcionProducto(), "La descripcion es requerida");
	   	 //Preconditions.checkNotNull(objetoProducto.getPrecioProducto(), "El precio es requerido");
		 
		 this.idProducto=idProducto;
		 //this.idCategoria = idCategoria;
		 
		 /*this.idEmpresa = idEmpresa;
		 this.empresaKey=Key.create(Empresa.class,idEmpresa);
		 */
		 this.websafeCategoriaKey = websafeCategoriaKey;
		 this.categoriaKey=Key.create(websafeCategoriaKey);
		 Categoria categoria = ofy().load().key(categoriaKey).now();
		 this.idCategoria = categoria.getidCategoria();
		// Categoria cateogoria = Key.create(categoriaKey, Categoria.class);
		 actualizaConProductoForm(objetoProducto);
		 
	 }
	 
	 public long getidProducto(){
		 
		 return idProducto;
	 }
	 
     public String getNombreProducto(){
		 
		 return nombreProducto;
	 }
     
     public String getDescripcionProducto(){
		 
		 return descripcionProducto;
	 }
     
     public int getPrecioProducto(){
    	 
    	 return precioProducto;
     }
     
     //actualiza el producto con el formulario delproducto
     public void actualizaConProductoForm(ProductoForm productoForm) {
	     this.nombreProducto = productoForm.getNombreProducto();
	     this.descripcionProducto = productoForm.getDescripcionProducto();
	  
	     this.precioProducto = productoForm.getPrecioProducto();
	       
	 }
     
     @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
	 public Key<Categoria> getKeyCategoria(){
		 return categoriaKey;
		 
	 }
     @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
     public Long getIdCategoria() {
    	 return idCategoria;
    	 }
     
     public String getWebsafeKey(){
		 return Key.create(categoriaKey).getString();
	 }
     
     @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
     public String getWebsafeCategoriaKey() {
    	 return websafeCategoriaKey;
    	 }
     
    /* @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
	 public Key<Empresa> getKeyEmpresa(){
		 return empresaKey;
		 
	 }
     @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
     public Long getIdEmpresa() {
    	 return idEmpresa;
    	 }
	 
	 
	/* public long getLlaveSegura(){
		 
		 //return Key.create(Categoria.class,idCategoria).getId();
	 }
	 */
	 
	 public String getCategoriaPropietaria() {
	        // Profile organizer = ofy().load().key(Key.create(Profile.class, organizerUserId)).now();
	       Categoria organizer = ofy().load().key(getKeyCategoria()).now();
	        if (organizer == null) {
	            return "";
	        } else {
	            return organizer.getNombreCategoria();
	        }
	    }
	 

}