import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class imagenes{
   
   public ImageIcon obtenerImagen(String url, int ancho, int alto, int tipo){
      
      try{
         
         return new ImageIcon(
                 ImageIO.
                         read(
                                 new File(
                                         url)
                         ).getScaledInstance(
                                 ancho,
                                 alto,
                                 tipo));
      }catch(IOException e){
         throw new RuntimeException(e);
      }
   }
}
