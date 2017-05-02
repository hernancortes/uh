import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFileChooser;

public class Program {
    
    public final static int RANGODESDE1 = 65;
    public final static int RANGOHASTA1 = 90;
    public final static int RANGODESDE2 = 97;
    public final static int RANGOHASTA2 = 122;
    public final static int RANGODESDE3 = 48;
    public final static int RANGOHASTA3 = 57;
    public final static int CARACTERESPECIAL1 = 32;
    
    public static final void main(String[] arg) throws Exception {
       Program programa = new Program();
       programa.contarCaracteresInvalidos();
    }
   
    public int contarCaracteresInvalidos() throws Exception {
        int cantidadDeCaracteres = 0;
        int cantidadDePalabras = 0;
        int cantidadDeLineas = 0;
        
        Scanner in = null;
        File selectedFile = null;
        JFileChooser chooser = new JFileChooser();
        // para elegir un archivo
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            in = new Scanner(selectedFile);
        }

        while (in.hasNext()) {
            String tmpStr = in.nextLine();
            if (!tmpStr.equalsIgnoreCase("")) {
                String replaceAll = tmpStr.replaceAll("\\s+", "");
                cantidadDeCaracteres += replaceAll.length();
                cantidadDePalabras += tmpStr.split(" ").length;
                //verifico cada caracter dentro de los rangos
                verificarSiHayCaracteresInvalidos(tmpStr, RANGODESDE1, RANGOHASTA1, RANGODESDE2, RANGOHASTA2, RANGODESDE3, RANGOHASTA3);
            }
            ++cantidadDeLineas;
        }

        System.out.println("Cantidad de caracteres totales : " + cantidadDeCaracteres);
        System.out.println("Cantidad of palabras totales : " + cantidadDePalabras);
        System.out.println("Cantidad of lineas totales : " + cantidadDeLineas);

        in.close();
        return cantidadDeCaracteres;
    }
    
    public boolean verificarSiHayCaracteresInvalidos(String tmpStr, int rangoDesde1, int rangoHasta1, int rangoDesde2, int rangoHasta2, int rangoDesde3, int rangoHasta3) {
        boolean hay = false;
        for (int posicion = 0; posicion < tmpStr.length(); posicion++) {
            if (tmpStr.codePointAt(posicion) >= rangoDesde1 && tmpStr.codePointAt(posicion) <= rangoHasta1 || 
                    tmpStr.codePointAt(posicion) >= rangoDesde2 && tmpStr.codePointAt(posicion) <= rangoHasta2 ||
                            tmpStr.codePointAt(posicion) >= rangoDesde3 && tmpStr.codePointAt(posicion) <= rangoHasta3) {

            } else {
                System.out.println("Caracter: " + tmpStr.charAt(posicion) + " ASCII: " + tmpStr.codePointAt(posicion));
                hay = true;
            }
        }
        return hay;
    }
    
    public void impresionAArchivo(String textoAImprimir, String salidaPorArchivo){
        FileWriter archivo = null;
        PrintWriter pw = null;
        String nombreDeArchivo;
        //si solo fue ingresado argumento "--output-file=" el archivo se llamara "salida.txt"
        if (salidaPorArchivo.length()>14){
            nombreDeArchivo = salidaPorArchivo.substring(14, salidaPorArchivo.length());
        } else {
            nombreDeArchivo = "salida.txt";
        }
        try{
            archivo = new FileWriter(nombreDeArchivo);
            pw = new PrintWriter(archivo);
            pw.println(textoAImprimir);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
               if (null != archivo) archivo.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }
        
}
