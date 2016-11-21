package voda;

import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URL;
import org.json.*;

public class Main {
	public static void main(String[] args) throws Exception {
		URL url = new URL("http://gis.sofiyskavoda.bg/");
		String address = url.getHost();

		Socket socket = new Socket(address, 6080);
		
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		OutputStreamWriter outWriter = new OutputStreamWriter(socket.getOutputStream());
		
		outWriter.write("GET /arcgis/rest/services/InfrastructureAlerts/mapserver/3/query?d=1479737252291&f=json&where=ACTIVESTATUS%20%3D%20%27Confirmed%27&returnGeometry=true&spatialRel=esriSpatialRelIntersects&outFields=*&outSR=102100 HTTP/1.1\r\n");
		outWriter.write("Host:gis.sofiyskavoda.bg:6080\r\n");
		outWriter.write("Accept:*/*\r\n");
		outWriter.write("Content-Type:application/x-www-form-urlencoded\r\n\n");
		outWriter.flush();
		
		boolean more = true;
		String input;
		String output = "";
		
		while(more) {
			input = inFromServer.readLine();
			if(input == null) {
				more = false;
			} else {
				output += input;
			}
		}
		
		String json = output.substring(output.indexOf("{"));
		System.out.println(json);
	
		
		
		socket.close();
		System.out.println(socket.isClosed());
	}
}
