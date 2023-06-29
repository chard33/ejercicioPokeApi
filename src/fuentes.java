import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class fuentes{
   
   private static Font[] fuente;
   
   public fuentes(){
      
      fuente = new Font[2];
   }
   
   public Font[] obtenerFuentes(){
      
      try{
         
         fuente[0] = Font.createFont(0,
                                     (InputStream) new BufferedInputStream(
                                             new FileInputStream(
                                                     "./src/pokemon/Pokemon-Solid.ttf"))).
                         deriveFont(30f);
         
         fuente[1] = Font.createFont(0,
                                     (InputStream) new BufferedInputStream(
                                             new FileInputStream(
                                                     "./src/pokemon/Pokemon-Hollow.ttf"))).
                         deriveFont(15f);
         
         GraphicsEnvironment ge = GraphicsEnvironment.
                 getLocalGraphicsEnvironment();
         
         ge.registerFont(fuente[0]);
         ge.registerFont(fuente[1]);
         
      }catch(FontFormatException |
             IOException e){
         throw new RuntimeException(e);
      }
      
      return fuente;
   }
}



