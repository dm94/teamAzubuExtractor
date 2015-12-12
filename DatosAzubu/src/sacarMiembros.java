import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class sacarMiembros{

	private static URL url;
    private static BufferedReader reader;
    private static ArrayList<String> canales;
    private static ArrayList<String> usuariosComunidad;
    private static ArrayList<String> comprob;
    
    public static ArrayList<String> iniciarCanales() {
		canales=new ArrayList<String>();
		usuariosComunidad=new ArrayList<String>();
		comprob=new ArrayList<String>();
		String web;
		web=sacarDatos();
		String canal = "";
		String usuario="";
		int inicio=0;
		int iUsu=0;
		int fUsu=0;
		int fin=0;

		System.out.println("La web tiene "+web.length()+" caracteres");
		
		for(int i = 0;i<web.length();i++){
			inicio=web.indexOf("username",i);
			
			if(inicio>0 & inicio>i){
				fin=web.indexOf("username",inicio);
				if(!canal.equalsIgnoreCase(web.substring(inicio, inicio+25))){
					canal=web.substring(inicio, inicio+30);
					
					//System.out.println(canal);
					
					iUsu=canal.indexOf(":");
					fUsu=canal.indexOf(",");
					
					usuario=canal.substring(iUsu+2, fUsu-1);
					//System.out.println(usuario);
					
					comprob.add((String) usuario);
					
					inicio=fin;
					fin=0;
					
					
				}
				
				
			}
				
			
			i=i+50;
			
		}
		
		String comprobar;
		for(int l=0;l<comprob.size();l++){
			comprobar=comprob.get(l);
			boolean encontrado=false;
			if(estaDato(comprob.get(l))){
				canales.add(comprob.get(l));
			}
			
		}
		
		/*for(int j=0;j<canales.size();j++){
			System.out.println("Canal "+j);
			System.out.println(canales.get(j));
		}*/
		
		return canales;
		
	}

	public static boolean estaDato(String canal){
		boolean esta=true;
		
		for(int n=0;n<canales.size();n++){
			if(!(canales.get(n).equalsIgnoreCase(canal))){
				esta=true;
			}else{
				esta=false;
			}
		}
		
		return esta;
		
	}
	
	public static String sacarDatos(){
		String cad="";
		
		 try {
	            url = new URL("http://api.azubu.tv/public/channel/list?teams=Communidad_G_Zone"); //Bukkit automatically adds the URL tags, remove them when you copy the class
	            reader = new BufferedReader(new InputStreamReader(url.openStream()));
	 
	            cad=reader.readLine();
	            
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		
		
		return cad;
	}

}
