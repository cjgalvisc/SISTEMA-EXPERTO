
package sistema_experto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Problema {
    public String nombre;
    public Map<String, String> variables= new HashMap<String, String>();
    public Map<String, String> objetivos= new HashMap<String, String>();
    public Map<String, String> preguntas= new HashMap<String, String>();
    public ArrayList reglas=new ArrayList();
    
}
