import java.util.ArrayList;
import org.junit.Test;
import org.junit.Assert;


public class ProgramTest 
{
    @Test
    public void cuentaCantidadDeCaracteresInvalidos() throws Exception {
        Program nuevoProg = new Program();
        int resultadoEsperado = 4;
        int resultado = nuevoProg.contarCaracteresInvalidos();
        Assert.assertEquals(resultadoEsperado, resultado);
    }
    
    @Test
    public void verificarSiHayCaracteresInvalidos() throws Exception {
        Program nuevoProg = new Program();
        String lineaTest = "a";
        boolean resultadoEsperado = false;
        boolean resultado = nuevoProg.verificarSiHayCaracteresInvalidos(lineaTest, 65, 90, 97, 122, 48, 57);
        Assert.assertEquals(resultadoEsperado, resultado);
    }
}   