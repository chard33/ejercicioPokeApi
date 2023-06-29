import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

public class pokedex extends JFrame{
   private JPanel panel1;
   private JButton salir;
   private JButton anterior;
   private JButton siguiente;
   private JLabel imagen;
   private JLabel peso;
   private JLabel estadisticas;
   private JLabel tipo1;
   private JLabel tipo2;
   private JLabel nombre;
   private JLabel altura;
    private JTextField buscarP;
    private JButton btnBuscar;
   private JComboBox nombresP;
   private int pokemon;
   private pokeapi poke;
   private int posX, posY;
   
   pokedex() {
      
      Font[] fuente = new fuentes().obtenerFuentes();
      
      anterior.setIcon(new imagenes().
                               obtenerImagen("./src/imgs/btnIzq.png",
                                             45, 45, 4));
      anterior.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
      anterior.setContentAreaFilled(false);
      
      siguiente.setIcon(new imagenes().
                               obtenerImagen("./src/imgs/btnDer.png",
                                             45, 45, 4));
      siguiente.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
      siguiente.setContentAreaFilled(false);
      
      salir.setIcon(new imagenes().
                               obtenerImagen("./src/imgs/btnSalir.png",
                                             30, 35, 4));
      salir.setBorder(null);
      salir.setContentAreaFilled(false);
      
      setSize(550, 300);
      panel1.setBackground(Color.RED);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      setUndecorated(true);
      
      nombre.setFont(fuente[0]);
      tipo1.setFont(fuente[1]);
      tipo2.setFont(fuente[1]);
      peso.setFont(fuente[1]);
      altura.setFont(fuente[1]);
      estadisticas.setFont(fuente[1]);
      btnBuscar.setFont(fuente[1]);
      buscarP.setFont(fuente[1]);
      nombresP.setFont(new Font(buscarP.getFont().getName(), 0, 15));
      
      buscarP.setBorder(BorderFactory.createMatteBorder(
              1, 10, 1, 1, Color.white));
      
      setContentPane(panel1);
      
      MouseAdapter listener = new MouseAdapter(){
         @Override
         public void mouseEntered(MouseEvent e){
            
            ((JButton)e.getSource()).setContentAreaFilled(true);
         }
         
         @Override
         public void mouseExited(MouseEvent e){
            
            ((JButton)e.getSource()).setContentAreaFilled(false);
         }
      };
      
      anterior.addMouseListener(listener);
      siguiente.addMouseListener(listener);
      salir.addMouseListener(listener);
      
      pokemon = 1;
      
      poke = new pokeapi(String.valueOf(pokemon));

      nombresP.setModel(
              new DefaultComboBoxModel<String>(
                      (String[]) new pokeapi().nombres().toArray(new String[0])));
      
      obtenerPokemon();
      
      salir.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            System.exit(0);
         }
      });
      
      siguiente.addActionListener(new ActionListener(){
         @Override
         public void actionPerformed(ActionEvent e){
            
            if(pokemon <= 1281){
               
               pokemon++;
               
               poke = new pokeapi(String.valueOf(pokemon));
               
               obtenerPokemon();
            }
         }
      });
      
      anterior.addActionListener(new ActionListener(){
         @Override
         public void actionPerformed(ActionEvent e){
            
            if(pokemon >= 2){
               
               pokemon--;
               
               poke = new pokeapi(String.valueOf(pokemon));
               
               obtenerPokemon();
            }
         }
      });
      
      btnBuscar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            
            if(buscarP.getText().length() > 0){
               
               poke = new pokeapi(buscarP.getText());
               
               if(poke.pokemon != null){
                  
                  obtenerPokemon();
                  
               }else{
                  buscarP.setText("Pokemon no encontrado");
                  buscarP.setForeground(Color.red);
                  buscarP.setBorder(BorderFactory.createMatteBorder(
                          1, 10, 1, 1,
                          Color.getHSBColor(
                                  0, 1, 0.25f)));
               }
               
            }else{
               buscarP.setText("Ingresa un pokemon");
               buscarP.setForeground(Color.yellow);
               buscarP.setBorder(BorderFactory.createMatteBorder(
                       1, 10, 1, 1,
                       Color.yellow));
            }
            
            buscarP.transferFocusBackward();
            btnBuscar.setEnabled(false);
         }
      });
      
      buscarP.addFocusListener(new FocusAdapter(){
         @Override
         public void focusGained(FocusEvent e){
            
            btnBuscar.setEnabled(true);
            
            if(buscarP.getText().equals("Ingresa un pokemon")){
               
               buscarP.setText("");
            }else if(buscarP.getText().equals("Pokemon no encontrado")){
               
               buscarP.setText("");
            }
         }
      });
      
      nombre.addMouseListener(new MouseAdapter(){
         @Override
         public void mousePressed(MouseEvent e){
            posX = e.getX();
            posY = e.getY();
         }
      });
      nombre.addMouseMotionListener(new MouseMotionAdapter(){
         @Override
         public void mouseDragged(MouseEvent e){
            pokedex.getWindows()[0].setLocation(pokedex.getWindows()[0].getX() + e.getX() - posX,
                                                pokedex.getWindows()[0].getY() + e.getY() - posY);
         }
      });
      
      
      nombre.addMouseListener(new MouseAdapter(){
         @Override
         public void mouseEntered(MouseEvent e){
            pokedex.getWindows()[0].
                    setCursor(new Cursor(Cursor.MOVE_CURSOR));
         }
         
         @Override
         public void mouseExited(MouseEvent e){
            pokedex.getWindows()[0].
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         }
      });
      

      nombresP.addItemListener(new ItemListener() {
         @Override
         public void itemStateChanged(ItemEvent e) {

            poke = new pokeapi(nombresP.getSelectedItem().toString());

            obtenerPokemon();
         }
      });

      setVisible(true);
   }
   private void obtenerPokemon(){
      
      buscarP.setBorder(BorderFactory.createMatteBorder(
              1, 10, 1, 1, Color.white));
      
      buscarP.setText("Ingresa un pokemon");
      buscarP.setForeground(Color.white);
      
          nombre.setText(poke.nombre());
          pokemon = poke.id();
          
          if(poke.tipos().size() >= 2){
             tipo1.setText(poke.tipos()
                               .get(0)
                               .toString());
             tipo2.setText(poke.tipos().
                               get(1).
                               toString());
          }else{
             tipo1.setText(poke.tipos()
                               .get(0)
                               .toString());
             tipo2.setText("");
          }
          
          try{
             imagen.setIcon(
                     new ImageIcon(
                             ImageIO.read(
                                            new URI(
                                                    poke.imagen()
                                            ).toURL()
                                    ).
                                    getScaledInstance(
                                            150,
                                            150,
                                            Image.SCALE_SMOOTH)
                     )
             );
          }catch(IOException |
                 URISyntaxException e){
             throw new RuntimeException(e);
          }
      
      peso.setText("Peso: " + String.valueOf(poke.peso()));
          altura.setText("Altura: " + String.valueOf(poke.altura()));
          
          
          estadisticas.setText("<html>");
          
          for(Object estadistica : poke.estadisticas().keySet()){
             
             estadisticas.setText(estadisticas.getText() + "<br>" +
                                          estadistica +
                                          " = " + poke.estadisticas().get(estadistica) +
                                          "\n");
          }
          
          estadisticas.setText(estadisticas.getText() + "</html>");
   }
   
   public static void main(String[] argumentos){
      
      new pokedex();
   }
}
