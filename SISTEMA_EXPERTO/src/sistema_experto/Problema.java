
package sistema_experto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Problema {
    public String nombre;
    public Map<String,Boolean> variables= new HashMap<String,Boolean>();
    public Map<String, Boolean> objetivos= new HashMap<String,Boolean>();
    public Map<String, String> preguntas= new HashMap<String, String>();
    public ArrayList<String> reglas=new ArrayList<String>();
    public Map<String,Boolean> respuestas= new HashMap<String, Boolean>();
    
}
