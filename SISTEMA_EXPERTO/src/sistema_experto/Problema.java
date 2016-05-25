
package sistema_experto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Problema {
    public String nombre;
    // diccionario  de variables  (valor de tipo string, y clave de tibo true o false)
    public Map<String,Boolean> variables= new HashMap<String,Boolean>();
    // diccionario de objetivos
    public Map<String, Boolean> objetivos= new HashMap<String,Boolean>();
    //diciionario que almacena las preguntas de cada variables (nombre de la variable, pregunta que realiza el usuario)
    public Map<String, String> preguntas= new HashMap<String, String>();
    //cada linea del array contiene una regla
    public ArrayList<String> reglas=new ArrayList<String>();
    //almacena  los calores que toman los objetivos despues de evaluar la condicion
    public Map<String,Boolean> respuestas= new HashMap<String, Boolean>();
    
}
