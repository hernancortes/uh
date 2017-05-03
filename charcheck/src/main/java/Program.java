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
    private final static Charset CHARSET = Charset.forName("ISO-8859-1");
    //private final static Charset UTF8 = Charset.forName("UTF-8");
    //private final static Charset ISO = Charset.forName("ISO-8859-1");
    private static String nombreArchivoAProcesar;
    private static String nombreArchivoCaracteresValidos;
    private final static int CARACTERESANTESDELCARACTERINVALIDO = 15;
    private final static int CARACTERESDESPUESDELCARACTERINVALIDO = 15;
    private final static int CODIGOQUEREPRESENTACARACTERELIMINADO = 999;
    
    public static void main(String[] arg) throws Exception {
       Program programa = new Program();
       nombreArchivoAProcesar = programa.seleccionDeArchivoAProcesar();
       caracteresAVerificar = programa.contenidoArchivoALista(nombreArchivoAProcesar);
       nombreArchivoCaracteresValidos = programa.seleccionDeArchivoAProcesar();
       caracteresValidos = programa.contenidoArchivoALista(nombreArchivoCaracteresValidos);
       //agrego algunos caracteres especiales
       caracteresValidos.add(65279); //caracter especial agregado por notepad
       //caracteresValidos.add(209); //caracter Ñ
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
    
    public void compararListasDeCaracteres(List<Integer> caracteresAVerificar, List<Integer> caracteresValidos) {
        boolean encontrado = false;
        for (int x = 0; x < caracteresAVerificar.size(); x++) {
            for (int i = 0; i < caracteresValidos.size() && !encontrado; i++) {
                //System.out.println("Comparando : " + caracteresAVerificar.get(x) + " con: " + caracteresValidos.get(i));
                encontrado = caracteresAVerificar.get(x).equals(caracteresValidos.get(i));
            }
            if (!encontrado) {
                //System.out.println("El caracter : " + caracteresAVerificar.get(x) + " no es valido");
                caracteresAVerificar.set(x, accionDeCaracterInvalido(caracteresAVerificar, x));
            }
            encontrado = false;
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
