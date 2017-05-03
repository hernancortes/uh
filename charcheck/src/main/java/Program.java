import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFileChooser;
import java.io.*;
import java.nio.charset.*;
import javax.swing.JOptionPane;

public class Program {
    
    //para impresion de salto de linea en pantalla o archivo
    public final static char CR  = (char) 0x0D;
    public final static char LF  = (char) 0x0A; 
    public final static String CRLF  = "" + CR + LF;
    //private static Scanner contenidoDeArchivoAVerificar = null;
    private File archivoAVerificar;
    //private static Scanner contenidoDeArchivoCaracteresValidos = null;
    private File archivoCaracteresValidos;
    private static List<Integer> caracteresAVerificar = new ArrayList<>();
    private static List<Integer> caracteresValidos = new ArrayList<>();
    private static String textoProcesado = "";
    private final static Charset CHARSET = Charset.forName("ISO-8859-1");
    //private final static Charset UTF8 = Charset.forName("UTF-8");
    //private final static Charset ISO = Charset.forName("ISO-8859-1");
    private static String nombreArchivoAProcesar;
    private static String nombreArchivoCaracteresValidos;
    
    private final static int CARACTERESANTESDELCARACTERINVALIDO = 15;
    private final static int CARACTERESDESPUESDELCARACTERINVALIDO = 15;
    public static void main(String[] arg) throws Exception {
       Program programa = new Program();
       nombreArchivoAProcesar = programa.seleccionDeArchivoAProcesar();
       caracteresAVerificar = programa.contenidoArchivoALista(nombreArchivoAProcesar);
       nombreArchivoCaracteresValidos = programa.seleccionDeArchivoAProcesar();
       caracteresValidos = programa.contenidoArchivoALista(nombreArchivoCaracteresValidos);
       //agrego algunos caracteres especiales
       caracteresValidos.add(65279); //caracter especial agregado por notepad
       //caracteresValidos.add(209); //caracter Ñ
       textoProcesado = programa.compararListasDeCaracteres(caracteresAVerificar, caracteresValidos);
       programa.guardarTextoEnArchivo(caracteresAVerificar);
    }
   
    public String seleccionDeArchivoAProcesar() throws Exception {
        File archivo = null;
        JFileChooser seleccionadorDeArchivos = new JFileChooser();
        if (seleccionadorDeArchivos.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            archivo = seleccionadorDeArchivos.getSelectedFile();
        }
        return archivo.getAbsolutePath();
    }
    
    public List<Integer> contenidoArchivoALista(String archivo) throws IOException {
        List<Integer> lista = new ArrayList<>();
        Reader reader = new InputStreamReader(new FileInputStream(archivo), CHARSET);
        try {
            int c;
            while (-1 != (c = reader.read())) {
                lista.add(c);
            }  
        } finally {
            reader.close();
        }
        return lista;
    }
    
    public String compararListasDeCaracteres(List<Integer> caracteresAVerificar, List<Integer> caracteresValidos) {
        boolean encontrado = false;
        String texto = "";
        //for (Integer caracterAVerificar : caracteresAVerificar) {
        for (int x = 0; x < caracteresAVerificar.size(); x++) {
            for (int i = 0; i < caracteresValidos.size() && !encontrado; i++) {
                //System.out.println("Comparando : " + caracteresAVerificar.get(x) + " con: " + caracteresValidos.get(i));
                encontrado = caracteresAVerificar.get(x).equals(caracteresValidos.get(i));
            }
            if (!encontrado) {
                //System.out.println("El caracter : " + caracteresAVerificar.get(x) + " no es valido");
                caracteresAVerificar.set(x, accionDeCaracterInvalido(caracteresAVerificar, x));
            }
            //texto = texto + Character.toChars(Integer.parseInt(caracterAVerificar));
            encontrado = false;
        }
        return texto;
    }
    
    public int accionDeCaracterInvalido(List<Integer> caracteresAVerificar, int posicionCaracterInvalido) {
        int charAMostrar = caracteresAVerificar.get(posicionCaracterInvalido);
        String mensajeDeError = "El caracter " + (char)charAMostrar + " no es valido (nro " + charAMostrar + ")";
        //System.out.println("El caracter : " + caracteresAVerificar.get(posicionCaracterInvalido) + " no es valido");
        String contextoDeCaracterInvalido = "";
        //chequeo los rangos del contexto
        int caracteresAntes = CARACTERESANTESDELCARACTERINVALIDO;
        int caracteresDespues = CARACTERESDESPUESDELCARACTERINVALIDO;
        //si esta muy cerca del inicio entonces que el inicio sea 0
        if ((posicionCaracterInvalido - CARACTERESANTESDELCARACTERINVALIDO) < 0) {
            caracteresAntes = posicionCaracterInvalido; 
        }
        //si esta muy cerca del final entonces que el final sea el ultimo
        if ((posicionCaracterInvalido + CARACTERESDESPUESDELCARACTERINVALIDO) > caracteresAVerificar.size()) {
            caracteresDespues = caracteresAVerificar.size() - posicionCaracterInvalido; 
        }
        for (int x = posicionCaracterInvalido - caracteresAntes; x < posicionCaracterInvalido + caracteresDespues; x++) {
            charAMostrar = caracteresAVerificar.get(x);
            contextoDeCaracterInvalido = contextoDeCaracterInvalido + (char)charAMostrar;
        }
        String caracter = JOptionPane.showInputDialog(mensajeDeError + "\n" + "Contexto del caracter: \n" + contextoDeCaracterInvalido, "?");
        int codigoADevolver = 32; //DEVUELVO POR DEFECTO UN ESPACIO
        if (caracter.equals("")) {
            codigoADevolver = 999; //CODIGO QUE REPRESENTA CARACTER ELIMINADO
        } else {
            codigoADevolver = caracter.codePointAt(0);
        }
        return codigoADevolver;
    }
    
    /*
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
    }*/
    
    public void compararCaracteresConCaracteresValidos(String tmpStr, List<String> caracteresValidos) {
        boolean encontrado = false;
        for (String caracterValido : caracteresValidos) {
            System.out.println("Comparando : " + tmpStr + " con: " + caracterValido);
            if (tmpStr.equals(caracterValido) && !encontrado) {
                }
            }
        }
    
    public static void guardarTextoEnArchivo(List<Integer> listaCaracteres) throws IOException {
        String nombreDeArchivo = "salida.txt";
        Writer writer = new OutputStreamWriter(new FileOutputStream(nombreDeArchivo), CHARSET);
        try{            
            for (int caracter : listaCaracteres) {
                if (caracter != 999) {
                    writer.write((char)caracter);
                }
            }   
        } finally {
            writer.close();
        } 
    }

}
