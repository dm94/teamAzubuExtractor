import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Principal {

	private static ArrayList<String> miembros;
	
	public static void main(String[] args) {
		miembros=new ArrayList<String>();
		miembros=sacarMiembros.iniciarCanales();
		
		sacarDatos();

	}
	
	private static void sacarDatos(){
		URL url;
		BufferedReader reader;
		String contadorViews="";
		String contadorSubs="";
		
		String cad="";
		
		System.out.printf("%20s %15s %10s %n","Canal","Seguidores","Views");
		
		for(int i=0;i<miembros.size();i++){
			int inicio=0;
			int fin=0;
			
			try {
	            url = new URL("http://api.azubu.tv/public/channel/"+miembros.get(i)+"/info"); //Bukkit automatically adds the URL tags, remove them when you copy the class
	            reader = new BufferedReader(new InputStreamReader(url.openStream()));
	            
	            cad=reader.readLine();
	            
	            inicio=cad.indexOf("followers_count");
	            fin=cad.indexOf("view_count",inicio);
	            
	            contadorViews=cad.substring(inicio+17, fin-2);
	            
	            inicio=cad.indexOf("vods_view_count");
	            fin=cad.length();
	            
	            contadorSubs=cad.substring(inicio+17, fin-2);
	            
	            System.out.printf("%20s %15s %10s %n",miembros.get(i),contadorViews,contadorSubs);
	            
	            //insertarDatos(miembros.get(i),Integer.parseInt(contadorViews),Integer.parseInt(contadorSubs));
	            
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			
		}
		
	}


	private static void insertarDatos(String canal,int seguidores, int views){
		
		try {
			Class.forName ("oracle.jdbc.driver.OracleDriver");
			Connection conexion = DriverManager.getConnection  
					("jdbc:oracle:thin:@localhost:1521:XE", 
							  "pruebas", "pruebas");  
			
			Statement sentencia = conexion.createStatement();
		
			sentencia.executeQuery("insert into azubu values('"+canal+"',"+seguidores+","+views+")");
			
			sentencia.close();
			conexion.commit();
			conexion.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
