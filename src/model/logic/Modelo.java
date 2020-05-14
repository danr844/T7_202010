package model.logic;



import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import model.data_structures.Haversine;
import model.data_structures.Queue;

/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo
{
	/**
	 * Atributos del modelo del mundo
	 */



	/**
	 * Constructor del modelo del mundo con capacidad dada
	 * @param tamano
	 */
	public Modelo()
	{

	}



	public void cargarInfo() throws ParseException{
		try {
			////// tesing
			String path = "./data/Comparendos_DEI_2018_Bogotá_D.C_small.geojson";
			JsonReader reader;
			reader = new JsonReader(new FileReader(path));
			JsonElement elem = JsonParser.parseReader(reader);
			JsonArray ja = elem.getAsJsonObject().get("features").getAsJsonArray();
			for(JsonElement e: ja)
			{
				int id = e.getAsJsonObject().get("properties").getAsJsonObject().get("OBJECTID").getAsInt();
				String EPOCOD_PLAN = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOCOD_PLAN").getAsString();
				String EPOCOD_ENT= e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOCOD_ENT").getAsString();
				String EPOCOD_PROY = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOCOD_PROY").getAsString();
				String EPOANIO_GEO=e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOANIO_GEO").getAsString();
				String EPOFECHA_INI=e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOFECHA_INI").getAsString();
				String EPOFECHA_FIN = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOFECHA_FIN").getAsString();
				String EPODESCRIP = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPODESCRIP").getAsString();
				String EPOEST_PROY = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOEST_PROY").getAsString();
				String EPOINTERV_ESP= e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOINTERV_ESP").getAsString();
				String EPODIR_SITIO= e.getAsJsonObject().get("properties").getAsJsonObject().get("EPODIR_SITIO").getAsString();
				String EPOLATITUD= e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOLATITUD").getAsString();
				String EPOONGITU= e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOONGITU").getAsString();
				String EPOSERVICIO= e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOSERVICIO").getAsString();
				String EPOHORARIO= e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOHORARIO").getAsString();
				String EPOTELEFON = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOTELEFON").getAsString();
				String EPOCELECTR = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOCELECTR").getAsString();
				String EPOCONTACT = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOCONTACT").getAsString();
				String EPOPWEB = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOPWEB").getAsString();
				String EPOIUUPLAN = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOIUUPLAN").getAsString();
				String EPOIUSCATA = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOIUSCATA").getAsString();
				String EPOIULOCAL = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOIULOCAL").getAsString();
				String EPOEASOCIA = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOEASOCIA").getAsString();
				String EPOFUNCION = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOFUNCION").getAsString();
				String EPOTEQUIPA = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOTEQUIPA").getAsString();
				String EPONOMBRE = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPONOMBRE").getAsString();
				String EPOIDENTIF = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOIDENTIF").getAsString();
				String EPOFECHA_C = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOFECHA_C").getAsString();

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}