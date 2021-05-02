package persistence;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class GSONFileManager {

	private static final String MESSAGE_ERROR_SAVE_DATA = "Error al guardar los datos";
	private static final String LOCATION_FILE = "data/infoModelsManager.json";

	public static void writeFile(ArchiveClass object) throws Exception {
		try {
			FileWriter fileWriter;
			fileWriter = new FileWriter(LOCATION_FILE);
			fileWriter.write(new Gson().toJson(object));
			fileWriter.close();
		} catch (IOException e) {
			throw new Exception(MESSAGE_ERROR_SAVE_DATA);
		}
	}

	public static ArchiveClass readFile() {
		ArchiveClass newModel = null;
		try {
			JsonParser json = new JsonParser();
			Gson gson = new Gson();
			Object string = json.parse(new FileReader(LOCATION_FILE));
			newModel = gson.fromJson(string.toString(), ArchiveClass.class);
			return newModel;
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			e.printStackTrace();
		}
		return newModel;
	}
}