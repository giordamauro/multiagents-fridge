package org.unicen.multiagents.ontology;

import jade.content.lang.sl.SLCodec;
import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.schema.AgentActionSchema;
import jade.content.schema.PredicateSchema;
import jade.content.schema.PrimitiveSchema;

public class ProductoOntology extends Ontology {
	private static final long serialVersionUID = 314831391539979184L;

	// Nombre de la ontología
	public static final String ONTOLOGY_NAME = "producto-ontology";
	
	public static final String DETALLE_PRODUCTO = "DetalleProducto";
	public static final String PRODUCTO_DETALLE = "producto";
	public static final String PRECIO_DETALLE = "precio";
	public static final String CANTIDAD_DETALLE = "cantidad";
	
	public static final String COMPRAR_PRODUCTO = "ComprarProducto";
	public static final String PRODUCTO_COMPRA = "producto";
	public static final String CANTIDAD_COMPRA = "cantidad";
	public static final String MONTO_TOTAL = "montoTotal";
	
	private static Ontology instance = new ProductoOntology();
	private static SLCodec codecInstance = new SLCodec();
	
	public static Ontology getInstance() { return instance; }
	
	public static SLCodec getCodecInstance() { return codecInstance; }
	
	public ProductoOntology() {
		super(ONTOLOGY_NAME, BasicOntology.getInstance());
		try {			
			// Predicado utilizado por la heladera para consultar precio de un producto, 
			// en el QUERY IF el campo Precio está vacio, y en el INFORM está completado con el precio del producto
			PredicateSchema ps = new PredicateSchema(DETALLE_PRODUCTO);
			ps.add(PRODUCTO_DETALLE, (PrimitiveSchema)getSchema(BasicOntology.STRING), PrimitiveSchema.MANDATORY);
			ps.add(PRECIO_DETALLE, (PrimitiveSchema)getSchema(BasicOntology.FLOAT), PrimitiveSchema.OPTIONAL);
			ps.add(CANTIDAD_DETALLE, (PrimitiveSchema)getSchema(BasicOntology.FLOAT), PrimitiveSchema.MANDATORY);
			add(ps, DetalleProducto.class);
			
			// Acción utilizada para requerir la compra de un producto
			AgentActionSchema aas = new AgentActionSchema(COMPRAR_PRODUCTO);
			aas.add(PRODUCTO_COMPRA, (PrimitiveSchema)getSchema(BasicOntology.STRING), PrimitiveSchema.MANDATORY);
			aas.add(CANTIDAD_COMPRA, (PrimitiveSchema)getSchema(BasicOntology.FLOAT), PrimitiveSchema.MANDATORY);
			aas.add(MONTO_TOTAL, (PrimitiveSchema)getSchema(BasicOntology.FLOAT), PrimitiveSchema.MANDATORY);
			add(aas,ComprarProducto.class);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}