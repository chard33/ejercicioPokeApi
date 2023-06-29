import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.*;

public class pokeapi{
   
   URL url;
   InputStream inS;
   Scanner sc, urlTodos;
   StringBuilder contenido = new StringBuilder();
   StringBuilder pokemones = new StringBuilder();
   JSONObject pokemon, nombres;
   
   public pokeapi(){
      
      try{
         urlTodos = new Scanner(
                 new URI("https://pokeapi.co/api/v2/pokemon/?offset=0&limit=1281").
                         toURL().openStream());
         
         while(urlTodos.hasNext()){
            
            pokemones.append(urlTodos.nextLine());
         }
         
         urlTodos.close();
         
         this.nombres = new JSONObject((pokemones.toString()));
         
      }catch(IOException e){
         throw new RuntimeException(e);
      }catch(URISyntaxException e){
         throw new RuntimeException(e);
      }
   }
   
   public pokeapi(String pokemon){
      
      try{
         
         url = new URI("https://pokeapi.co/api/v2/pokemon/" + pokemon).toURL();
         
         inS = url.openStream();
         
         sc = new Scanner(inS);
         
         while(sc.hasNext()){
            
            contenido.append(sc.nextLine());
         }
         
         inS.close();
         
         this.pokemon = new JSONObject(contenido.toString());
         
      }catch(MalformedURLException |
             URISyntaxException e){
         throw new RuntimeException(e);
      }catch(IOException e){
         System.out.println("Error: " + e);
      }
   }
   
   public ArrayList nombres(){
      
      ArrayList nm = new ArrayList(nombres.getJSONArray("results").length());
      
      for(Object nombre: nombres.getJSONArray("results")){
         
         nm.add(((JSONObject) nombre).get("name"));
      }
      
      return nm;
   }
   
   public String nombre(){
      
      return pokemon.
              get("name").
              toString();
   }

   public int id(){

      return (int) pokemon.
              get("id");
   }
   
   public String imagen(){
      
      return pokemon.
              getJSONObject("sprites").
              getJSONObject("other").
              getJSONObject("official-artwork").
              get("front_default").
              toString();
   }
   
   public int altura(){
      
      return (int) pokemon.get("height");
   }
   
   public int peso(){
      
      return (int) pokemon.get("weight");
   }
   
   public ArrayList tipos(){
      
      ArrayList tps = new ArrayList<>();
      
      for(Object tp: pokemon.getJSONArray("types")){
         
         tps.add(((JSONObject) tp).
                         getJSONObject("type").
                         get("name"));
      }
      
      return tps;
   }
   
   public TreeMap estadisticas(){
      
      TreeMap carac = new TreeMap();
      
      pokemon.
              getJSONArray("stats").
              forEach(e -> {
                 
                 carac.put(
                         ((JSONObject) e).
                                 getJSONObject("stat").
                                 get("name").
                                 toString(),
                         (int) ((JSONObject) e).
                                 get("base_stat")
                 );
              });
      
      return carac;
   }
}
