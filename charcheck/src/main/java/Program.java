import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import java.io.*;
import java.nio.charset.*;
import javax.swing.JOptionPane;

public class Program {
    
    private static List<Integer> caracteresAVerificar = new ArrayList<>();
    private static List<Integer> caracteresValidos = new ArrayList<>();
    private static String textoProcesado = "";
    //private final static Charset CHARSET = Charset.forName("ISO-8859-1");
    private final static Charset UTF = Charset.forName("UTF-8");
    private final static Charset ISO = Charset.forName("ISO-8859-1");
    private static String nombreArchivoAProcesar;
    private static String nombreArchivoCaracteresValidos;
    private final static int CARACTERESANTESDELCARACTERINVALIDO = 15;
    private final static int CARACTERESDESPUESDELCARACTERINVALIDO = 15;
    private final static int CODIGOQUEREPRESENTACARACTERELIMINADO = 999;
    private final static int CODIGOQUEREPRESENTAENIE = 209;
    private final static int CODIGOQUEREPRESENTA_U = 85;
    private final static int CODIGOQUEREPRESENTA_E = 69;
    private final static int CODIGOQUEREPRESENTA_I = 73;
    private final static int CODIGOQUEREPRESENTA_A = 65;
    private final static int CODIGOQUEREPRESENTA_O = 79;
    private final static int CODIGOQUEREPRESENTA_o_min = 111;
    
    public static void main(String[] arg) throws Exception {
       Program programa = new Program();
       nombreArchivoAProcesar = programa.seleccionDeArchivoAProcesar();
       caracteresAVerificar = programa.contenidoArchivoALista(nombreArchivoAProcesar);
       nombreArchivoCaracteresValidos = programa.seleccionDeArchivoAProcesar();
       caracteresValidos = programa.contenidoArchivoALista(nombreArchivoCaracteresValidos);
       //agrego algunos caracteres especiales
       caracteresValidos.add(65279); //caracter especial agregado por notepad
       caracteresValidos.add(CODIGOQUEREPRESENTACARACTERELIMINADO); //caracter Ñ
       //caracteresValidos.add(CODIGOQUEREPRESENTAENIE); //caracter Ñ
       //caracteresValidos.add(195); //caracter Ñ
       programa.compararListasDeCaracteres(caracteresAVerificar, caracteresValidos);
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
        Reader reader = new InputStreamReader(new FileInputStream(archivo), ISO);
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
    
    public void compararListasDeCaracteres(List<Integer> caracteresAVerificar, List<Integer> caracteresValidos) {
        boolean encontrado = false;
        boolean cambioAutomatico = false;
        for (int x = 0; x < caracteresAVerificar.size(); x++) {
            for (int i = 0; i < caracteresValidos.size() && !encontrado; i++) {
                //System.out.println("Comparando : " + caracteresAVerificar.get(x) + " con: " + caracteresValidos.get(i));
                encontrado = caracteresAVerificar.get(x).equals(caracteresValidos.get(i));
            }
            if (!encontrado) {
                //System.out.println("El caracter : " + caracteresAVerificar.get(x) + " no es valido");
                if (caracteresAVerificar.get(x) == 195 && caracteresAVerificar.get(x + 1) == 8216){
                    caracteresAVerificar.set(x, CODIGOQUEREPRESENTAENIE);
                    caracteresAVerificar.set(x + 1, CODIGOQUEREPRESENTACARACTERELIMINADO);
                    cambioAutomatico = true;
                }
                if (caracteresAVerificar.get(x) == 195 && caracteresAVerificar.get(x + 1) == 145){
                    caracteresAVerificar.set(x, CODIGOQUEREPRESENTAENIE);
                    caracteresAVerificar.set(x + 1, CODIGOQUEREPRESENTACARACTERELIMINADO);
                    cambioAutomatico = true;
                }
                if (caracteresAVerificar.get(x) == 195 && caracteresAVerificar.get(x + 1) == 177){
                    caracteresAVerificar.set(x, CODIGOQUEREPRESENTAENIE);
                    caracteresAVerificar.set(x + 1, CODIGOQUEREPRESENTACARACTERELIMINADO);
                    cambioAutomatico = true;
                }
                if (caracteresAVerificar.get(x) == 195 && caracteresAVerificar.get(x + 1) == 186){
                    caracteresAVerificar.set(x, CODIGOQUEREPRESENTA_U);
                    caracteresAVerificar.set(x + 1, CODIGOQUEREPRESENTACARACTERELIMINADO);
                    cambioAutomatico = true;
                }
                if (caracteresAVerificar.get(x) == 195 && caracteresAVerificar.get(x + 1) == 339){
                    caracteresAVerificar.set(x, CODIGOQUEREPRESENTA_U);
                    caracteresAVerificar.set(x + 1, CODIGOQUEREPRESENTACARACTERELIMINADO);
                    cambioAutomatico = true;
                }
                if (caracteresAVerificar.get(x) == 195 && caracteresAVerificar.get(x + 1) == 188){
                    caracteresAVerificar.set(x, CODIGOQUEREPRESENTA_U);
                    caracteresAVerificar.set(x + 1, CODIGOQUEREPRESENTACARACTERELIMINADO);
                    cambioAutomatico = true;
                }
                if (caracteresAVerificar.get(x) == 195 && caracteresAVerificar.get(x + 1) == 137){
                    caracteresAVerificar.set(x, CODIGOQUEREPRESENTA_E);
                    caracteresAVerificar.set(x + 1, CODIGOQUEREPRESENTACARACTERELIMINADO);
                    cambioAutomatico = true;
                }
                if (caracteresAVerificar.get(x) == 195 && caracteresAVerificar.get(x + 1) == 169){
                    caracteresAVerificar.set(x, CODIGOQUEREPRESENTA_E);
                    caracteresAVerificar.set(x + 1, CODIGOQUEREPRESENTACARACTERELIMINADO);
                    cambioAutomatico = true;
                }
                if (caracteresAVerificar.get(x) == 195 && caracteresAVerificar.get(x + 1) == 173){
                    caracteresAVerificar.set(x, CODIGOQUEREPRESENTA_I);
                    caracteresAVerificar.set(x + 1, CODIGOQUEREPRESENTACARACTERELIMINADO);
                    cambioAutomatico = true;
                }
                if (caracteresAVerificar.get(x) == 195 && caracteresAVerificar.get(x + 1) == 141){
                    caracteresAVerificar.set(x, CODIGOQUEREPRESENTA_I);
                    caracteresAVerificar.set(x + 1, CODIGOQUEREPRESENTACARACTERELIMINADO);
                    cambioAutomatico = true;
                }
                if (caracteresAVerificar.get(x) == 195 && caracteresAVerificar.get(x + 1) == 161){
                    caracteresAVerificar.set(x, CODIGOQUEREPRESENTA_A);
                    caracteresAVerificar.set(x + 1, CODIGOQUEREPRESENTACARACTERELIMINADO);
                    cambioAutomatico = true;
                }
                if (caracteresAVerificar.get(x) == 195 && caracteresAVerificar.get(x + 1) == 129){
                    caracteresAVerificar.set(x, CODIGOQUEREPRESENTA_A);
                    caracteresAVerificar.set(x + 1, CODIGOQUEREPRESENTACARACTERELIMINADO);
                    cambioAutomatico = true;
                }
                if (caracteresAVerificar.get(x) == 195 && caracteresAVerificar.get(x + 1) == 147){
                    caracteresAVerificar.set(x, CODIGOQUEREPRESENTA_O);
                    caracteresAVerificar.set(x + 1, CODIGOQUEREPRESENTACARACTERELIMINADO);
                    cambioAutomatico = true;
                }
                if (caracteresAVerificar.get(x) == 195 && caracteresAVerificar.get(x + 1) == 179){
                    caracteresAVerificar.set(x, CODIGOQUEREPRESENTA_O);
                    caracteresAVerificar.set(x + 1, CODIGOQUEREPRESENTACARACTERELIMINADO);
                    cambioAutomatico = true;
                }
                if (caracteresAVerificar.get(x) == 195 && caracteresAVerificar.get(x + 1) == 156){
                    caracteresAVerificar.set(x, CODIGOQUEREPRESENTA_U);
                    caracteresAVerificar.set(x + 1, CODIGOQUEREPRESENTACARACTERELIMINADO);
                    cambioAutomatico = true;
                }
                if (caracteresAVerificar.get(x) == 250){
                    caracteresAVerificar.set(x, CODIGOQUEREPRESENTA_U);
                    cambioAutomatico = true;
                }
                if (caracteresAVerificar.get(x) == 252){
                    caracteresAVerificar.set(x, CODIGOQUEREPRESENTA_U);
                    cambioAutomatico = true;
                }
                if (caracteresAVerificar.get(x) == 220){
                    caracteresAVerificar.set(x, CODIGOQUEREPRESENTA_U);
                    cambioAutomatico = true;
                }
                if (caracteresAVerificar.get(x) == 237){
                    caracteresAVerificar.set(x, CODIGOQUEREPRESENTA_I);
                    cambioAutomatico = true;
                }
                if (caracteresAVerificar.get(x) == 243){
                    caracteresAVerificar.set(x, CODIGOQUEREPRESENTA_o_min);
                    cambioAutomatico = true;
                }
                if (caracteresAVerificar.get(x) == 241){
                    caracteresAVerificar.set(x, CODIGOQUEREPRESENTAENIE);
                    cambioAutomatico = true;
                }
                if (caracteresAVerificar.get(x) == 233){
                    caracteresAVerificar.set(x, CODIGOQUEREPRESENTA_E);
                    cambioAutomatico = true;
                }
                if (!cambioAutomatico) {
                    caracteresAVerificar.set(x, accionDeCaracterInvalido(caracteresAVerificar, x));
                }
            }
            encontrado = false;
            cambioAutomatico = false;
        }
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
        int codigoADevolver = CODIGOQUEREPRESENTACARACTERELIMINADO; //CODIGO QUE REPRESENTA CARACTER ELIMINADO
        if (!caracter.equals("")) {
            codigoADevolver = caracter.codePointAt(0);
        }
        return codigoADevolver;
    }
    
    public static void guardarTextoEnArchivo(List<Integer> listaCaracteres) throws IOException {
        String nombreDeArchivo = "salida.txt";
        Writer writer = new OutputStreamWriter(new FileOutputStream(nombreDeArchivo), ISO);
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
