package model.logic;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import model.data_structures.Comparendo;
import model.data_structures.Edge;
import model.data_structures.GeographicPoint;
import model.data_structures.GrafoNoDirigido;
import model.data_structures.Haversine;
import model.data_structures.Maps;
import model.data_structures.RedBlackBST;
import model.data_structures.Vertex;
import model.data_structures.estacionPolicia;

/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo
{
	/**
	 * Atributos del modelo del mundo
	 */

	private RedBlackBST<Integer, Comparendo> comparendosRedBlack;
	private RedBlackBST<Integer, estacionPolicia> estacionesPolRedBlack;
	private GrafoNoDirigido<Integer, GeographicPoint> graphRead;
	private GrafoNoDirigido<Integer, GeographicPoint> graphWrite;
	/**
	 * Constructor del modelo del mundo con capacidad dada
	 * @param tamano
	 */
	public Modelo()
	{
		comparendosRedBlack= new RedBlackBST<Integer, Comparendo>();
		estacionesPolRedBlack = new RedBlackBST<Integer, estacionPolicia>();
		graphRead = new GrafoNoDirigido<Integer, GeographicPoint>(200000);
		graphWrite = new GrafoNoDirigido<Integer, GeographicPoint>(200000);
	}



	public void cargarInfoEstacionesPolicia(String pPath) throws ParseException{
		try {
			////// tesing
			String path = pPath;
			JsonReader reader;
			reader = new JsonReader(new FileReader(path));
			JsonElement elem = JsonParser.parseReader(reader);
			JsonArray ja = elem.getAsJsonObject().get("features").getAsJsonArray();
			for(JsonElement e: ja)
			{
				int id =e.getAsJsonObject().get("properties").getAsJsonObject().get("OBJECTID").getAsInt();
				String EPODESCRIP = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPODESCRIP").getAsString();
				String EPODIR_SITIO= e.getAsJsonObject().get("properties").getAsJsonObject().get("EPODIR_SITIO").getAsString();
				String EPOLATITUD= e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOLATITUD").getAsString();
				String EPOLONGITU= e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOLONGITU").getAsString();
				String EPOSERVICIO= e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOSERVICIO").getAsString();
				String EPOTELEFON = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOTELEFON").getAsString();
				String EPONOMBRE = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPONOMBRE").getAsString();
				String EPOIDENTIF = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOIDENTIF").getAsString();
				String EPOFECHA_C = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOFECHA_C").getAsString();
				estacionPolicia epo  = new estacionPolicia(id, EPODESCRIP, EPODIR_SITIO, EPOLATITUD, EPOLONGITU, EPOSERVICIO, EPONOMBRE, EPOIDENTIF);
				estacionesPolRedBlack.put(id, epo);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void cargarInfoVertex(String pPath) throws IOException{
		File file=new File(pPath);
		FileReader fr=new FileReader(file);
		BufferedReader br=new BufferedReader(fr);
		String line=br.readLine();
		while(line!=null)
		{
			String[] vertex=line.split(",");
			int objectId=Integer.parseInt(vertex[0].trim());
			double longitude=Double.parseDouble(vertex[1].trim());
			double latitude=Double.parseDouble(vertex[2].trim());
			graphRead.addVertex(objectId, new GeographicPoint(longitude,latitude));
			line=br.readLine();
		}
		br.close();
		fr.close();

	}


	public void cargarInfoEdges(String pPath) throws IOException{
		File file=new File(pPath);
		FileReader fr=new FileReader(file);
		BufferedReader br=new BufferedReader(fr);
		String line=br.readLine();
		while(line.contains("#"))
		{
			line=br.readLine();
		}
		while(line!=null)
		{
			String[] edges=line.split(" ");
			int idVertexInit=Integer.parseInt(edges[0].trim());
			GeographicPoint geo1=graphRead.getVertex(idVertexInit).getInfo();
			for(int i=1; i<edges.length;++i)
			{
				int idFinalVertex=Integer.parseInt(edges[i].trim());
				GeographicPoint geo2=graphRead.getVertex(idFinalVertex).getInfo();
				graphRead.addEdge(idVertexInit, idVertexInit, idFinalVertex, Haversine.distance(geo1.getlatitude(), geo1.getLongitude(), geo2.getlatitude(), geo2.getLongitude()));
			}
			line=br.readLine();
		}
		br.close();
		fr.close();

	}



	public Comparendo cargarInfoComparendos() throws ParseException{
		Comparendo mayorID1 = null;
		try {
			////// tesing
			int mayorID= 0;
			String path = "./data/Comparendos_DEI_2018_Bogot�_D.C_50000_.geojson";
			JsonReader reader;
			reader = new JsonReader(new FileReader(path));
			JsonElement elem = JsonParser.parseReader(reader);
			JsonArray ja = elem.getAsJsonObject().get("features").getAsJsonArray();
			SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
			for(JsonElement e: ja)
			{
				String []fecha1= e.getAsJsonObject().get("properties").getAsJsonObject().get("FECHA_HORA").getAsString().split("T");
				int id = e.getAsJsonObject().get("properties").getAsJsonObject().get("OBJECTID").getAsInt();
				Date fecha = parser.parse(fecha1[0]);
				String Hora = fecha1[1];
				String medio = e.getAsJsonObject().get("properties").getAsJsonObject().get("MEDIO_DETECCION").getAsString();
				String Clasevehi= e.getAsJsonObject().get("properties").getAsJsonObject().get("CLASE_VEHICULO").getAsString();
				String tipoServicio = e.getAsJsonObject().get("properties").getAsJsonObject().get("TIPO_SERVICIO").getAsString();
				String Infraccion =e.getAsJsonObject().get("properties").getAsJsonObject().get("INFRACCION").getAsString();
				String DescInfra=e.getAsJsonObject().get("properties").getAsJsonObject().get("DES_INFRACCION").getAsString();
				String Localidad = e.getAsJsonObject().get("properties").getAsJsonObject().get("LOCALIDAD").getAsString();
				String Municipio = e.getAsJsonObject().get("properties").getAsJsonObject().get("MUNICIPIO").getAsString();
				double latitud = 0;
				double longitud=0;
				double distancia =0;
				if(e.getAsJsonObject().has("geometry") && !e.getAsJsonObject().get("geometry").isJsonNull())
				{
					JsonArray geoElem = e.getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray();
					latitud=geoElem.get(0).getAsDouble();
					longitud=geoElem.get(1).getAsDouble();
				}

				Comparendo user = new Comparendo(id,fecha,Hora, medio, Clasevehi, tipoServicio, Infraccion, DescInfra, Localidad, Municipio, latitud, longitud, distancia );
				if(id>=mayorID)mayorID1= user;
				comparendosRedBlack.put(id, user);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return mayorID1;
	}


	//ACLARACIÓN PRELIMINAR: El siguiente código fue realizado con base en el código de la página http://javainutil.blogspot.com/2013/03/java-escribir-un-json.html
	/**
	 * Método que imprime los valores de las ubicaciones guardadas en el grafo.
	 * pre: Se inicializa la lista de estaciones.
	 * @param pRutaImpresion Ruta en donde se va a imprimir el archivo JSON.
	 * @throws IOException 
	 */
	public void saveGraphJson(String pRutaImpresion) throws IOException
	{
		FileWriter fw = new FileWriter(pRutaImpresion);

		JsonObject graph = new JsonObject();
		JsonArray array = new JsonArray();

		for (int i = 0; i < 50000; i++) 
		{
			JsonObject obj = new JsonObject();
			obj.addProperty("OBJECT_ID", i);
			obj.addProperty("LATITUD", graphRead.getVertex(i).getInfo().getlatitude());
			obj.addProperty("LONGITUD", graphRead.getVertex(i).getInfo().getLongitude());

			Iterator<Edge<Integer,GeographicPoint>> it = graphRead.getVertex(i).getAdjacencyList().iterator();
			JsonArray list = new JsonArray();
			while(it.hasNext())
			{
				JsonObject arc = new JsonObject();
				Edge<Integer,GeographicPoint> edge = it.next();
				arc.addProperty("VERTICE_DESTINO", edge.getFinalVertex().getID());
				arc.addProperty("COSTO", edge.getCost());
				list.add(arc);
			}

			obj.add("ARCOS",list);
			array.add(obj);
		}

		graph.add("features", array);
		fw.write(graph.toString());
		fw.flush();
		fw.close();
	}

	/**
	 * M�todo que carga los valores del JSON a un grafo.
	 * @param ruta Ruta del JSON
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	public void loadGraphJson(String ruta) throws UnsupportedEncodingException, FileNotFoundException, ParseException
	{
		JsonReader lector = new JsonReader(new InputStreamReader(new FileInputStream(ruta)));
		JsonObject obj = JsonParser.parseReader(lector).getAsJsonObject();

		JsonArray arregloVertices = obj.get("features").getAsJsonArray();  

		for (JsonElement e: arregloVertices) 	
		{
			JsonObject propiedades = e.getAsJsonObject();

			int objectId = propiedades.get("OBJECT_ID").getAsInt();
			double latitud = propiedades.get("LATITUD").getAsDouble();
			double longitud = propiedades.get("LONGITUD").getAsDouble();
			if(graphWrite.containsVertex(objectId))
				graphWrite.getVertex(objectId).changeInfo(new GeographicPoint(longitud, latitud));
			else
				graphWrite.addVertex(objectId, new GeographicPoint(longitud,latitud));

			JsonArray arcos = propiedades.get("ARCOS").getAsJsonArray();
			for (int i = 0; i < arcos.size() ; i++)
			{
				JsonObject arc = arcos.get(i).getAsJsonObject();
				int destino = arc.get("VERTICE_DESTINO").getAsInt();
				double costo = arc.get("COSTO").getAsDouble();
				if(!graphWrite.containsVertex(destino))
					graphWrite.addVertex(destino, null);
				graphWrite.addEdge(objectId,objectId, destino, costo);
			}
		}
	}

	/**
	 * M�todo que genera el mapa de arcos y v�rtices.  Ya se encuentran inicializados.
	 */
	public void generateMap()
	{
		Maps mapa = new Maps(graphWrite, null);
		mapa.initFrame();

	}

	/**
	 * M�todo que genera el mapa de arcos, v�rtices y estaciones de polic�a. Ya se encuentran inicializados.
	 */
	public void generateMapWithPoliceStations()
	{
		Maps mapa = new Maps(graphWrite, estacionesPolRedBlack);
		mapa.initFrame();
	}



	public GrafoNoDirigido<Integer, GeographicPoint> getGraphRead(){
		return graphRead;
	}
	public GrafoNoDirigido<Integer, GeographicPoint> getGraphWrite(){
		return graphWrite;
	}
	public RedBlackBST<Integer, estacionPolicia> getEstacionesDePolicia(){
		return estacionesPolRedBlack;
	}


}