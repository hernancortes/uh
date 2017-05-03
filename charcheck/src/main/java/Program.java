import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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
    
    private static Scanner contenidoDeArchivoAVerificar = null;
    private File archivoAVerificar;
    private static Scanner contenidoDeArchivoCaracteresValidos = null;
    private File archivoCaracteresValidos;
    private static List<String> caracteresAVerificar = new ArrayList<>();
    private static List<String> caracteresValidos = new ArrayList<>();
    private static String textoProcesado = "";
    
    public static void main(String[] arg) throws Exception {
       Program programa = new Program();
       contenidoDeArchivoAVerificar = programa.seleccionDeArchivoAProcesar();
       caracteresAVerificar = programa.contenidoArchivoALista(contenidoDeArchivoAVerificar);
       contenidoDeArchivoCaracteresValidos = programa.seleccionDeArchivoCaracteres();
       caracteresValidos = programa.contenidoArchivoALista(contenidoDeArchivoCaracteresValidos);
       //programa.recorrerArchivoAProcesar(caracteresAProcesar, caracteresValidos);
       textoProcesado = programa.compararListasDeCaracteres(caracteresAVerificar, caracteresValidos);
       programa.guardarTextoEnArchivo(textoProcesado);
       contenidoDeArchivoAVerificar.close();
       contenidoDeArchivoCaracteresValidos.close();
    }
   
    public Scanner seleccionDeArchivoAProcesar() throws Exception {
        Scanner contenido = null;
        File archivo = null;
        JFileChooser seleccionadorDeArchivos = new JFileChooser();
        if (seleccionadorDeArchivos.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            archivo = seleccionadorDeArchivos.getSelectedFile();
            contenido = new Scanner(archivo);
        }
        return contenido;
    }
    
    public Scanner seleccionDeArchivoCaracteres() throws Exception {
        Scanner contenido2 = null;
        File archivo2 = null;
        JFileChooser seleccionadorDeArchivos = new JFileChooser();
        if (seleccionadorDeArchivos.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            archivo2 = seleccionadorDeArchivos.getSelectedFile();
            contenido2 = new Scanner(archivo2);
        }
        return contenido2;
    }
    
    public List<String> contenidoArchivoALista(Scanner contenido) {
        List<String> lista = new ArrayList<>();
        while (contenido.hasNext()) {
            String tmpStr = contenido.nextLine();
            for (int i=0; i < tmpStr.length(); i++) {
                lista.add(String.valueOf(tmpStr.charAt(i)));
            }
        }
        return lista;
    }
    
    public String compararListasDeCaracteres(List<String> caracteresAVerificar, List<String> caracteresValidos) {
        boolean encontrado = false;
        String texto = "";
        for (String caracterAVerificar : caracteresAVerificar) {
            for(int i = 0; i < caracteresValidos.size() && !encontrado; i++) {
                //System.out.println("Comparando : " + caracterAVerificar + " con: " + caracteresValidos.get(i));
                encontrado = caracterAVerificar.equals(caracteresValidos.get(i));
            }
            if (!encontrado) {
                System.out.println("El caracter : " + caracterAVerificar + " no es valido");
            }
            texto = texto + caracterAVerificar;
            encontrado = false;
        }
        return texto;
    }
    
    public int recorrerArchivoAProcesar(Scanner contenido, List<String> caracteresValidos) {
        int cantidadDeCaracteres = 0;
        int cantidadDePalabras = 0;
        int cantidadDeLineas = 0;
        while (contenido.hasNext()) {
            String tmpStr = contenido.nextLine();
            if (!tmpStr.equalsIgnoreCase("")) {
                String replaceAll = tmpStr.replaceAll("\\s+", "");
                cantidadDeCaracteres += replaceAll.length();
                cantidadDePalabras += tmpStr.split(" ").length;
                //verifico cada caracter dentro de los rangos
                //verificarSiHayCaracteresInvalidos(tmpStr, RANGODESDE1, RANGOHASTA1, RANGODESDE2, RANGOHASTA2, RANGODESDE3, RANGOHASTA3);
                compararCaracteresConCaracteresValidos(tmpStr, caracteresValidos);
            }
            ++cantidadDeLineas;
        }

        System.out.println("Cantidad de caracteres totales : " + cantidadDeCaracteres);
        System.out.println("Cantidad of palabras totales : " + cantidadDePalabras);
        System.out.println("Cantidad of lineas totales : " + cantidadDeLineas);
        return cantidadDeCaracteres;
    }
    
    public void compararCaracteresConCaracteresValidos(String tmpStr, List<String> caracteresValidos) {
        boolean encontrado = false;
        for (String caracterValido : caracteresValidos) {
            System.out.println("Comparando : " + tmpStr + " con: " + caracterValido);
            if (tmpStr.equals(caracterValido) && !encontrado) {
                }
            }
        }
    
    public void guardarTextoEnArchivo(String textoAGuardar) {
        FileWriter archivo = null;
        PrintWriter pw = null;
        String nombreDeArchivo = "salida.txt";
        //si solo fue ingresado argumento "--output-file=" el archivo se llamara "salida.txt"
        /*
        if (salidaPorArchivo.length()>14){
            nombreDeArchivo = salidaPorArchivo.substring(14, salidaPorArchivo.length());
        } else {
            nombreDeArchivo = "salida.txt";
        }*/
        try{
            archivo = new FileWriter(nombreDeArchivo);
            pw = new PrintWriter(archivo);
            pw.println(textoAGuardar);

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
/*
    public boolean verificarSiHayCaracteresInvalidos(String tmpStr, int rangoDesde1, int rangoHasta1, int rangoDesde2, int rangoHasta2, int rangoDesde3, int rangoHasta3) {
        boolean hay = false;
        for (int posicion = 0; posicion < tmpStr.length(); posicion++) {
            if (tmpStr.codePointAt(posicion) >= rangoDesde1 && tmpStr.codePointAt(posicion) <= rangoHasta1 || 
                    tmpStr.codePointAt(posicion) >= rangoDesde2 && tmpStr.codePointAt(posicion) <= rangoHasta2 ||
                            tmpStr.codePointAt(posicion) >= rangoDesde3 && tmpStr.codePointAt(posicion) <= rangoHasta3) {

            } else {
                String lineaACorregir = tmpStr;
                String caracterACorregir = "Caracter: " + tmpStr.charAt(posicion) + " ASCII: " + tmpStr.codePointAt(posicion);
                createAndShowGUI(lineaACorregir, caracterACorregir);
                
                System.out.println("Caracter: " + tmpStr.charAt(posicion) + " ASCII: " + tmpStr.codePointAt(posicion));
                hay = true;
            }
        }
        return hay;
    }
    
    public void verificarSiHayCaracteresInvalidos(String lineaAVerificar) throws Exception {
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
                //String replaceAll = tmpStr.replaceAll("\\s+", "");
                //verifico cada caracter dentro de los rangos
                for (int posicion = 0; posicion < tmpStr.length(); posicion++) {
                    if (lineaAVerificar.codePointAt(posicion) == tmpStr.codePointAt(posicion)) {

                    } else {
                        mostarPantalla("Caracter: " + tmpStr.charAt(posicion) + " ASCII: " + tmpStr.codePointAt(posicion));
                        //System.out.println("Caracter: " + tmpStr.charAt(posicion) + " ASCII: " + tmpStr.codePointAt(posicion));
                    }
                //verificarSiHayCaracteresInvalidos(tmpStr, RANGODESDE1, RANGOHASTA1, RANGODESDE2, RANGOHASTA2, RANGODESDE3, RANGOHASTA3);
                }
            }
            in.close();
        }
    }
    */
    /*
    private static void createAndShowGUI(String lineaACorregir, String textoAMostrar) {
        //Create and set up the window.
        JFrame frame = new JFrame("Charcheck");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add contents to the window.
        frame.add(new TextDemo(lineaACorregir, textoAMostrar));

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    */
