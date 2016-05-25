/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema_experto;


import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CRISTIAN
 */
public class Interfaz extends javax.swing.JFrame {

    //para los problemas que se creen 
    Problema problema;

    //para almacenar cada problema nuevo
    ArrayList<Problema> lista_problemas_nuevos= new ArrayList<Problema>();
    //para almacenar los programas leidos
    ArrayList<Problema> lista_problemas_leidos= new ArrayList<Problema>();
    //variable temporal para almacenar cada linea del TXT 
    String linea;
    //para almacenar el nombre del TXT
    String nombre_problema;
    //donde queda almacenada la informacion de un problema creado para ser almacenado en un TXT
    ArrayList<String> lista_codigo=new ArrayList<String>();
    String codigo;
    //arraylist donde queda cada linea del TXT
    ArrayList<String> lineas= new ArrayList<String>();
    //expresiones regulares para verificar la sintaxis del TXT.SE
        String patron_comentario="^[%]+[\\w]";
        String patron_variable="^[V]+[-]+[\\w]";
        String patron_objetivo="^[C]+[-]+[\\w]";
        String patron_regla="[\\w]";
     //donde queda el resultado final
     ArrayList<String> resultado=new ArrayList<String>();  
        
    public Interfaz() {
        initComponents();
        //para que los bonotenes no se dejen clicear hasta que no se genere un nuevo problema
        NombreVariable.setEnabled(false);
        setTitle("SHELL SISTEMA EXPERTO");
        agregar_variable.setEnabled(false);
        definir_obejtivo.setEnabled(false);
        lista_objetivos.setEnabled(false);
        mostrarRegla.setEnabled(false);
        definirRegla.setEnabled(false);
        definirPregunta.setEnabled(false);
        verificarModelo.setEnabled(false);
        ejecutar.setEnabled(false);
        
        
        
    
    }

    //funcion para leer los archivos y almacenarlos en el arraylist lista_lineas
     public void  leerTxt(String direccion)
     {
         //creo un modelo de la tabla vairables para meter las variables
         DefaultTableModel modelo_variables=(DefaultTableModel) tablaVariables.getModel(); 
         
         //instancion un objeto de la clase problema cada vez que se abre un nuevo archivo
         problema=new Problema();
         
         try{
                BufferedReader bf=new BufferedReader(new FileReader(direccion));
                while((linea=bf.readLine())!=null){
                    //para quitar los espacios en blanco y leer linea a linea
                    if(linea.length()!=0){
                        lineas.add(linea.replaceAll("\\s",""));
                    }
                       
                    
                }
                
             //llamo la funcion para comprobar sintaxis
             //si la sintaxis es correcta extraigo la informacion y la monto en las tablas
              if(sintaxis(lineas)){
                  //para habilitar los botnes
                    NombreVariable.setEnabled(true);
            
                    agregar_variable.setEnabled(true);
                     definir_obejtivo.setEnabled(true);
                    lista_objetivos.setEnabled(true);

                    mostrarRegla.setEnabled(true);
                    definirRegla.setEnabled(true);
                    definirPregunta.setEnabled(true);

                    verificarModelo.setEnabled(true);
                    ejecutar.setEnabled(true);
                    
                  String token;
                  for (int i = 0; i <lineas.size(); i++) {
                      //parte en tokens separados por espacios en blanco
                      
                      //para ver si es un comentario
                        Pattern patron_com=Pattern.compile(patron_comentario);
                        Matcher emparejador_com= patron_com.matcher(lineas.get(i));
                        boolean coincide_com= emparejador_com.find();
                        //para comparar con el patron variable
                        Pattern patron_var=Pattern.compile(patron_variable);
                        Matcher emparejador_var= patron_var.matcher(lineas.get(i));
                        boolean coincide_var= emparejador_var.find();
                        //para comparar con el patron objetivo
                        Pattern patron_obj=Pattern.compile(patron_objetivo);
                        Matcher emparejador_obj= patron_obj.matcher(lineas.get(i));
                        boolean coincide_obj= emparejador_obj.find();
                        //para comparar con el patron regla
                        Pattern patron_reg=Pattern.compile(patron_regla);
                        Matcher emparejador_reg= patron_reg.matcher(lineas.get(i));
                        boolean coincide_reg= emparejador_reg.find();
                       
                      if(coincide_var){//si se cumple es una variable 
                          StringTokenizer tokens=new StringTokenizer(lineas.get(i),"-");
                          String token1=tokens.nextToken();
                          String token2=tokens.nextToken();
                              problema.variables.put(token2,false);
                              modelo_variables.addRow(new Object[]{token2,"",""});
                             listaPreguntas.addItem(token2);
                              lista_objetivos.addItem(token2);
                          
                          
                          
                      }else if(coincide_obj){//si se cumple es un objetivo
                          StringTokenizer tokens=new StringTokenizer(lineas.get(i),"-");
                          String token1=tokens.nextToken();
                          String token2=tokens.nextToken();
                          problema.objetivos.put(token2, false);
                          //objetivo.setText(token2);
                                              
                      }else if(coincide_reg){//si se cumple es una regla
                          
                          problema.reglas.add(lineas.get(i));
                          textoRegla.setText(lineas.get(i)+"\n");
                      }
                  }
              }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"EL ARCHIVO NO SE PUDO LEER");
            }
    }
     
     //funcion para verificar sintaxis
     public boolean sintaxis(ArrayList<String> p)
     {
         for (int i = 0; i <p.size(); i++) {
             //para comparar con el patron comentario
             Pattern patron_com=Pattern.compile(patron_comentario);
             Matcher emparejador_com= patron_com.matcher(lineas.get(i));
             boolean coincide_com= emparejador_com.find();
             //para comparar con el patron variable
             Pattern patron_var=Pattern.compile(patron_variable);
             Matcher emparejador_var= patron_var.matcher(lineas.get(i));
             boolean coincide_var= emparejador_var.find();
             //para comparar con el patron objetivo
             Pattern patron_obj=Pattern.compile(patron_objetivo);
             Matcher emparejador_obj= patron_obj.matcher(lineas.get(i));
             boolean coincide_obj= emparejador_obj.find();
             //para comparar con el patron regla
             Pattern patron_reg=Pattern.compile(patron_regla);
             Matcher emparejador_reg= patron_reg.matcher(lineas.get(i));
             boolean coincide_reg= emparejador_reg.find();
              //si todos son falsos es por que noingun patron sirvio
             if(!coincide_com && !coincide_var && !coincide_obj && !coincide_reg ){
                 System.out.println(i);
                 JOptionPane.showMessageDialog( null, "ERROR DE SINTAXIS" );
                 return false;
             }
         }
               JOptionPane.showMessageDialog( null, "SINTAXIS CORRECTA" );
               return true;
     
     }
     
     //funcion para encontrar el siguiente condicional
     public int posop(String[] vector,int inicio,int fin){
         int posicion=0;
         //CORRECCION
         //recorro todo el vector buscando donde hay UN operador 
         //cuadno lo entcuentre guardo el idicie el la variable posicion
         //y la devuelvo
         
         for (int i =inicio; i <=fin ; i++) {
             if(vector[i].equals("Y")||vector[i].equals("O")){
                 posicion=i;
                 i=fin+1;
              }
         }
         return posicion;
      }
     
     //funcion para verificar si la repuesta del usuario es igual a la del condicional
     public boolean valor(String valor){
         boolean clave;
         //este pedazo es por ejemplo A=true
         //por eso lo vuelvo a parir con el separador =
         String pedazos[]=valor.split("=");
         
         if(pedazos[1].equals("true")){
                clave=true;
         }else{
                clave=false;
           }
         //preginto si las varibales que respondio el usuario tienen el mismo
         //valor del encotnrado en la condicion
         //si es asi entonces devuelvo true de lo contrario devuelvo false
         if(problema.variables.get(pedazos[0]).equals(clave)){
             return true;
         }else{
             return false;
         }
     }
     
     //funcion para evaluar el condicional en forma de String 
     //inicio= pocision del vector mas 1
     //fin= el tamaño del vector menos 3
     public boolean evaluar(String[] vector,int inicio,int fin){
         int op;
         if(inicio==fin){
             return valor(vector[inicio]);
         }else{
             op=posop(vector,inicio,fin);
             if(vector[op].equals("Y")){
                 //  si se cumple realiza una comparacion true  and true
                 return (evaluar(vector,inicio,op-1)) &&(evaluar(vector,op+1,fin));
             }else{ 
                 //  si no se cumple realiza una comparacion true  or true
                 return (evaluar(vector,inicio,op-1)) || (evaluar(vector,op+1,fin));
             }
         }
     }
     
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        buttonGroup6 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        panelReglas = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        definirRegla = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaVariables = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        textoRegla = new javax.swing.JTextArea();
        mostrarRegla = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        objetivo = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        opcionObjetivo = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        panelVaribles = new javax.swing.JPanel();
        agregar_variable = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        NombreVariable = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        panelObjetivos = new javax.swing.JPanel();
        definir_obejtivo = new javax.swing.JButton();
        lista_objetivos = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        listaPreguntas = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        pregunta = new javax.swing.JTextArea();
        definirPregunta = new javax.swing.JButton();
        verficarPregunta = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        verificarModelo = new javax.swing.JButton();
        ejecutar = new javax.swing.JButton();
        combo2 = new javax.swing.JComboBox<>();
        reiniciar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        ABRIR = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        manualt1 = new javax.swing.JMenuItem();
        acercade = new javax.swing.JMenuItem();

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setText("SHELL SISTEMA EXPERTO");
        jLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        panelReglas.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel2.setText("si");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel3.setText("entonces");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel8.setText("REGLAS");

        definirRegla.setText("DEFINIR REGLA");
        definirRegla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                definirReglaActionPerformed(evt);
            }
        });

        tablaVariables.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NOMBRE", "VALOR", "CONDICIONAL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaVariables);
        if (tablaVariables.getColumnModel().getColumnCount() > 0) {
            tablaVariables.getColumnModel().getColumn(1).setCellEditor(new javax.swing.DefaultCellEditor(combo2));
        }

        textoRegla.setColumns(20);
        textoRegla.setLineWrap(true);
        textoRegla.setRows(5);
        textoRegla.setWrapStyleWord(true);
        textoRegla.setEnabled(false);
        jScrollPane2.setViewportView(textoRegla);

        mostrarRegla.setText("MOSTRAR");
        mostrarRegla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mostrarReglaActionPerformed(evt);
            }
        });

        jButton1.setText("?");
        jButton1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton1MouseMoved(evt);
            }
        });
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton1MouseReleased(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        objetivo.setEnabled(false);
        objetivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                objetivoActionPerformed(evt);
            }
        });

        jLabel10.setText("OBJETIVO");

        jLabel11.setText("=");

        jLabel12.setText("VALOR");

        opcionObjetivo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SI", "NO" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(objetivo, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(opcionObjetivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel10)
                        .addGap(36, 36, 36)
                        .addComponent(jLabel12)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(objetivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(opcionObjetivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37))
        );

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel13.setText("REGLA GENERADA");

        javax.swing.GroupLayout panelReglasLayout = new javax.swing.GroupLayout(panelReglas);
        panelReglas.setLayout(panelReglasLayout);
        panelReglasLayout.setHorizontalGroup(
            panelReglasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelReglasLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(panelReglasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(panelReglasLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelReglasLayout.createSequentialGroup()
                        .addGroup(panelReglasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(definirRegla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(mostrarRegla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(panelReglasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelReglasLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2))
                            .addGroup(panelReglasLayout.createSequentialGroup()
                                .addGap(121, 121, 121)
                                .addComponent(jLabel13)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelReglasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(263, 263, 263))
        );
        panelReglasLayout.setVerticalGroup(
            panelReglasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelReglasLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addGroup(panelReglasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelReglasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jLabel3))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelReglasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(panelReglasLayout.createSequentialGroup()
                        .addComponent(mostrarRegla)
                        .addGap(50, 50, 50)
                        .addComponent(definirRegla))
                    .addComponent(jScrollPane2))
                .addGap(22, 22, Short.MAX_VALUE))
        );

        panelVaribles.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        agregar_variable.setText("AGREGAR");
        agregar_variable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregar_variableActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel4.setText("NOMBRE");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel6.setText("VARIABLES");

        javax.swing.GroupLayout panelVariblesLayout = new javax.swing.GroupLayout(panelVaribles);
        panelVaribles.setLayout(panelVariblesLayout);
        panelVariblesLayout.setHorizontalGroup(
            panelVariblesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVariblesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(panelVariblesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(NombreVariable, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelVariblesLayout.createSequentialGroup()
                        .addComponent(agregar_variable)
                        .addGap(74, 74, 74)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelVariblesLayout.createSequentialGroup()
                .addContainerGap(93, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(70, 70, 70))
        );
        panelVariblesLayout.setVerticalGroup(
            panelVariblesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVariblesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(15, 15, 15)
                .addGroup(panelVariblesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(NombreVariable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(agregar_variable)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelObjetivos.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        definir_obejtivo.setText("DEFINIR OBJETIVOS");
        definir_obejtivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                definir_obejtivoActionPerformed(evt);
            }
        });

        lista_objetivos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lista_objetivosActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel7.setText("OBJETIVOS");

        javax.swing.GroupLayout panelObjetivosLayout = new javax.swing.GroupLayout(panelObjetivos);
        panelObjetivos.setLayout(panelObjetivosLayout);
        panelObjetivosLayout.setHorizontalGroup(
            panelObjetivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelObjetivosLayout.createSequentialGroup()
                .addGroup(panelObjetivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelObjetivosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lista_objetivos, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(definir_obejtivo))
                    .addGroup(panelObjetivosLayout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jLabel7)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        panelObjetivosLayout.setVerticalGroup(
            panelObjetivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelObjetivosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(20, 20, 20)
                .addGroup(panelObjetivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lista_objetivos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(definir_obejtivo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel5.setText("DEFINIR PREGUNTAS");

        pregunta.setColumns(20);
        pregunta.setLineWrap(true);
        pregunta.setRows(5);
        pregunta.setWrapStyleWord(true);
        jScrollPane4.setViewportView(pregunta);

        definirPregunta.setText("DEFINIR PREGUNTA");
        definirPregunta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                definirPreguntaActionPerformed(evt);
            }
        });

        verficarPregunta.setText("?");
        verficarPregunta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verficarPreguntaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(definirPregunta)
                .addGap(242, 242, 242))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(listaPreguntas, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(verficarPregunta, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(212, 212, 212))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(listaPreguntas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(verficarPregunta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(definirPregunta)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel9.setText("EJECUCION");

        verificarModelo.setText("VERIFICAR MODELO");
        verificarModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verificarModeloActionPerformed(evt);
            }
        });

        ejecutar.setText("EJECUTAR");
        ejecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ejecutarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jLabel9))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(verificarModelo)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(ejecutar)))))
                .addContainerGap(85, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(verificarModelo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ejecutar)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        combo2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SI", "NO" }));

        reiniciar.setText("REINICIAR");
        reiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reiniciarActionPerformed(evt);
            }
        });

        jMenu1.setText("ARCHIVO");

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("NUEVO");
        jMenuItem3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem3MouseClicked(evt);
            }
        });
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        ABRIR.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        ABRIR.setText("ABRIR");
        ABRIR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ABRIRActionPerformed(evt);
            }
        });
        jMenu1.add(ABRIR);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("GUARDAR");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        jMenu7.setText("AYUDA");
        jMenu7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu7ActionPerformed(evt);
            }
        });

        manualt1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
        manualt1.setText("MANUAL USUARIO");
        manualt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manualt1ActionPerformed(evt);
            }
        });
        jMenu7.add(manualt1);

        acercade.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        acercade.setText("ACERCA DE");
        acercade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acercadeActionPerformed(evt);
            }
        });
        jMenu7.add(acercade);

        jMenuBar1.add(jMenu7);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelVaribles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelObjetivos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(reiniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(64, 64, 64)
                        .addComponent(combo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelReglas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(combo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(reiniciar)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelVaribles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelObjetivos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelReglas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lista_objetivosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lista_objetivosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lista_objetivosActionPerformed

    private void jMenuItem3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem3MouseClicked
            
    }//GEN-LAST:event_jMenuItem3MouseClicked

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
       NombreVariable.setEnabled(true);
       
        agregar_variable.setEnabled(true);
         definir_obejtivo.setEnabled(true);
        lista_objetivos.setEnabled(true);
        
        mostrarRegla.setEnabled(true);
        definirRegla.setEnabled(true);
        definirPregunta.setEnabled(true);
        
        verificarModelo.setEnabled(true);
        ejecutar.setEnabled(true);
        
        //se instancia un nuevo objeto de tipo problema
        problema=new Problema();
    
 
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void agregar_variableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregar_variableActionPerformed
    
      // llama el modelo de la tablavariables  
    DefaultTableModel modelo_reglas=(DefaultTableModel) tablaVariables.getModel(); 
        
    if(NombreVariable.getText().length()!=0){
        //para elminar la tabla para que no se sobrescriban 
        //y se cargan de nuevo  con la que se acaba de adicionar
        System.out.println(modelo_reglas.getRowCount());
        for (int i = modelo_reglas.getRowCount() -1; i >= 0; i--){ 
          modelo_reglas.removeRow(i); 
        } 
        
       //para remover la lista de ITEMS
        lista_objetivos.removeAllItems();
        listaPreguntas.removeAllItems();
        //se agregan  las variables al objetos y se le asigna false a todas las variables por defecto
        problema.variables.put((String)NombreVariable.getText().replaceAll("\\s",""),false);
        
        //para agregar los items a la tabla y las listas se recorre el diccionario de variables
        Iterator it = problema.variables.entrySet().iterator();
          while (it.hasNext()) {
            Map.Entry e = (Map.Entry)it.next();
            //para agrrgar los items
             lista_objetivos.addItem((String) e.getKey());
             listaPreguntas.addItem((String) e.getKey());
            //para agregar la linea a la tabla de variables
             modelo_reglas.addRow(new Object[]{e.getKey(),"",""});

            }
        
       //para eliminar el area de texto 
        NombreVariable.setText("");
        
    }else{
        // en caso de que no se coloque nada arroja el mensaje 
        JOptionPane.showMessageDialog( null, "FALTA EL NOMBRE DE  LA VARIABLE" );
    }
        
      
     
 
    }//GEN-LAST:event_agregar_variableActionPerformed

    private void definir_obejtivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_definir_obejtivoActionPerformed

        //se llama la tabla variables para eliminar las que se convierten en objetivos
        DefaultTableModel modelo_reglas=(DefaultTableModel) tablaVariables.getModel();     
    
          //para agreagar las variables objetivos al diccionario de objetivos
           problema.objetivos.put((String) lista_objetivos.getSelectedItem(),false);
           problema.variables.remove((String) lista_objetivos.getSelectedItem());
           // asigna la variable  seleccionada a la casilla de objetivo
           objetivo.setText((String)lista_objetivos.getSelectedItem());
   
        
    
     //para corregir la tabla de variables sin los objetivos
     //para elminar la tabla
        System.out.println(modelo_reglas.getRowCount());
        for (int i = modelo_reglas.getRowCount() -1; i >= 0; i--){ 
          modelo_reglas.removeRow(i); 
        } 
        //se borra de nuevo la tabla variables
        listaPreguntas.removeAllItems();
        //para generar la nueva tabla
           Iterator it = problema.variables.entrySet().iterator();
          while (it.hasNext()) {
            Map.Entry e = (Map.Entry)it.next();
            //para agrrgar los items para reasignar los nuevos objetivos
             lista_objetivos.addItem((String) e.getKey());
             // reasigna las nuevas variables para preguntar 
             listaPreguntas.addItem((String) e.getKey());
            //para agregar la linea a la tabla de variables a la tabla antigua le quita los variables que son objetivos
             modelo_reglas.addRow(new Object[]{e.getKey(),"",""});

            }
          
         
            
        
    }//GEN-LAST:event_definir_obejtivoActionPerformed

    private void definirReglaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_definirReglaActionPerformed
         
        // adiciona en elarray list de reglas la regla generada 
        problema.reglas.add(textoRegla.getText().replaceAll("\\s",""));
        textoRegla.setText("");
        objetivo.setText("");
        
    }//GEN-LAST:event_definirReglaActionPerformed

    private void mostrarReglaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostrarReglaActionPerformed
        // aca se definiran las reglas .....
        
        // variable donde quedara  la regla como string y utiliza como token el -
        String linea1="SI-";
        
       // String regla="if(";
        System.out.println(problema.variables.size());
        //al definir la regla borre el texareano
        textoRegla.setText("");
        
        // recorre todo la tabla variables para formar la parte de la regla anters del ENTNCES
        for (int i = 0; i <problema.variables.size()-1; i++) {
           if(tablaVariables.getValueAt(i,1).equals("SI")&&tablaVariables.getValueAt(i,2).equals("Y")){
                linea1+=(String) tablaVariables.getValueAt(i,0)+"="+"true"+"-Y-";
                //regla+=tablaVariables.getValueAt(i,0)+" && ";
                
            }else if(tablaVariables.getValueAt(i,1).equals("SI")&&tablaVariables.getValueAt(i,2).equals("O")){
                linea1+=(String) tablaVariables.getValueAt(i,0)+"="+"true"+"-O-";
                //regla+=tablaVariables.getValueAt(i,0)+" || ";
                
            }else if(tablaVariables.getValueAt(i,1).equals("NO")&&tablaVariables.getValueAt(i,2).equals("Y")){
                linea1+=(String) tablaVariables.getValueAt(i,0)+"="+"false"+"-Y-";
                //regla+="!"+tablaVariables.getValueAt(i,0)+" && ";
                
            }else if(tablaVariables.getValueAt(i,1).equals("NO")&&tablaVariables.getValueAt(i,2).equals("O")){
                linea1+=(String) tablaVariables.getValueAt(i,0)+"="+"false"+"-O-";
                //regla+="!"+tablaVariables.getValueAt(i,0)+" || ";
                
            }  
        }
            
        // PARA TERMINAR DE LLENAR EL LADO IZQUIERDO  CON LA ULTIMA CONDICION
            //linea1+=(String) tablaVariables.getValueAt(problema.variables.size()-1,0)+"="+(String) tablaVariables.getValueAt(problema.variables.size()-1,1);
            if(tablaVariables.getValueAt(problema.variables.size()-1,1).equals("SI")){
                linea1+=(String) tablaVariables.getValueAt(problema.variables.size()-1,0)+"="+"true";
               // regla+=tablaVariables.getValueAt(problema.variables.size()-1,0)+"){";
            }else{
                linea1+=(String) tablaVariables.getValueAt(problema.variables.size()-1,0)+"="+"false";
               // regla+="!"+tablaVariables.getValueAt(problema.variables.size()-1,0)+"){";
            }
            
        //para agregar el objetivo despues del entonces 
            //linea1+=" ENTONCES "+(String)tablaObjetivos.getValueAt(0,0)+"="+(String)tablaObjetivos.getValueAt(0,1);
            if(opcionObjetivo.getSelectedItem().equals("SI")){
                linea1+="-ENTONCES-"+objetivo.getText()+"="+"true";
                //regla+=(String)tablaObjetivos.getValueAt(0,0)+"="+"true"+"}";
            }else{
                linea1+="-ENTONCES-"+objetivo.getText()+"="+"false";
               // regla+=(String)tablaObjetivos.getValueAt(0,0)+"="+"false"+"}";
            }
            
            
           //muestra la regla generada  en el texarea
        textoRegla.setText(linea1);
         
    }//GEN-LAST:event_mostrarReglaActionPerformed

    private void jButton1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseReleased
          
    }//GEN-LAST:event_jButton1MouseReleased

    private void jButton1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseMoved
   
    }//GEN-LAST:event_jButton1MouseMoved

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    //para mostrar la informacion al usuario de como debe llenar la tabla
        JOptionPane.showOptionDialog( this, "VERIFICAR!\n"+"ingrese los siguientes datos antes de dar clic en MOSTRAR\n"
                                       +"1.columna VALOR: seleccione la valor de la variable\n"+
                                       "2.columna CONDICIONAL:puede ser SI  O  NO\n", "ACERCA DE SHELL SISTEMA EXPERTO"
            ,
            JOptionPane.INFORMATION_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{" OK "},"OK"); 
    }//GEN-LAST:event_jButton1ActionPerformed

    private void verficarPreguntaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verficarPreguntaActionPerformed
       //para mostrar la informacion al usuario de como debe realizar la pregunta
        JOptionPane.showOptionDialog( this, "VERIFICAR!\n"+"realize una pregunta ¿***texto***?" , "ACERCA DE SHELL SISTEMA EXPERTO"
            ,
            JOptionPane.INFORMATION_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{" OK "},"OK"); 
    }//GEN-LAST:event_verficarPreguntaActionPerformed

    private void verificarModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verificarModeloActionPerformed
        // muestra que  tiene el problema en el momento
        String variables="";
        String objetivos="";
        String reglas="";
        //para recorrer el diccionario de variables varibles
        Iterator it_variables= problema.variables.entrySet().iterator();
            while (it_variables.hasNext()) 
             {
                 Map.Entry e = (Map.Entry)it_variables.next();
                 // va almacenando en el string concatenandolas
                 variables+="V"+" "+e.getKey()+" "+e.getValue()+"\n";
                 lista_codigo.add("V-"+(String) e.getKey());
             }
        //para recorrer el diccionario de  objetivos
        Iterator it_variables2= problema.objetivos.entrySet().iterator();
            while (it_variables2.hasNext()) 
             {
                 Map.Entry e = (Map.Entry)it_variables2.next();
                  // va almacenando en el string concatenandolas
                 objetivos+="C"+" "+e.getKey()+" "+e.getValue()+"\n";
                 //almacena  en  la variable global para luego poderla guardar en el 
                 //archivo generado .SE
                 lista_codigo.add("C-"+e.getKey());
             }
        //para recorre EL array list de las  reglas
        for (int i = 0; i < problema.reglas.size(); i++) {
             // va almacenando en el string concatenandolas
            reglas+=problema.reglas.get(i)+"\n";
            lista_codigo.add(problema.reglas.get(i));
        }
        
        codigo=variables+objetivos+reglas;
        //muestra en mensaje que variables hay , 
        //que objetivos y que reglas
        JOptionPane.showMessageDialog( null,codigo); 
    }//GEN-LAST:event_verificarModeloActionPerformed

    private void ABRIRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ABRIRActionPerformed
        JFileChooser ventana = new JFileChooser();
        ventana.setFileFilter(new FileNameExtensionFilter("extensiones validas:"+"*.SE","SE","se"));
        int sel = ventana.showOpenDialog(Interfaz.this); 
        if (sel == JFileChooser.APPROVE_OPTION) {
           File file = ventana.getSelectedFile();
           //funcion para leer el archivo
            leerTxt(file.getPath());  
            //para capturar el nombre del TXT
           nombre_problema=file.getName();
        }
    }//GEN-LAST:event_ABRIRActionPerformed

    private void ejecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ejecutarActionPerformed
    //para mostrar las variables
     String variables="REPUESTA DEL USUARIO\n";
     //para mostrar las reglas
     String reglas="REGLAS\n";
      //para mostrar las respuestas
     String respuestas="RESULTADOS DE LAS REGLAS\n";
     //PAR MOSTRAR EL RESULTADOS TOTAL
     String resultado="";
     
    //cilo para generar cuatnas preguntas haya
        Iterator it_variables2= problema.preguntas.entrySet().iterator();
            while (it_variables2.hasNext()) 
             {
                 Map.Entry e = (Map.Entry)it_variables2.next();
                    //cada vez que hay una pregunta, se muestra un mensaje con dicha pregunta y se almacena su 
                    //respueta en la clave correspondiente de la pregunta("e.getValue")
                    int n=JOptionPane.showConfirmDialog (null,(String) e.getValue(),"PREGUNTA", JOptionPane.YES_NO_OPTION);
                    //so la pregunta es si 
                    if(JOptionPane.OK_OPTION==n){
                       //se modifican los valores en el diccionario de valores 
                        problema.variables.replace((String) e.getKey(),true);
                    //si la pregunta es No
                    }else{
                        //se modifican los valores en el diccionario de valores 
                         problema.variables.replace((String) e.getKey(),false);
                         
                    }
             }
            
            System.out.println(Collections.singletonList(problema.variables));
            
        //MOTOR DE INFERENCIA(version academica)
        
        //recorro el arraylist de las reglas
        for (int i = 0; i <problema.reglas.size(); i++) {
            //parto la regla en tokens 
            String[] tokens = problema.reglas.get(i).split("-");
            
            int inicio=1;// pocision despues del SI e
            int fin=(tokens.length)-3;//posicon antes del ENTONCES
            //evaluar es la funcion que verfica si la regla se cumple
            if(evaluar(tokens,inicio,fin)){
                //si la funcion retorna true entonces se almacena la respuesta 
                //como esta en la condicion(despues del ENTONCES)
                String[] respuesta=tokens[(tokens.length)-1].split("=");
                boolean clave2;
                if(respuesta[1].equals("true")){
                    clave2=true;
                    problema.respuestas.put(respuesta[0],clave2);
                }else{
                    clave2=false;
                    problema.respuestas.put(respuesta[0],clave2);
                }

            }else{
                //si la funcion retorna FALSE entonces se almacena la respuesta 
                //de manera contraria a como esta en la condicion(despues del ENTONCES)
                String[] respuesta2=tokens[(tokens.length)-1].split("=");
                boolean clave3;
                if(respuesta2[1].equals("true")){
                    clave3=false;
                    problema.respuestas.put(respuesta2[0],clave3);
                }else{
                    clave3=true;
                    problema.respuestas.put(respuesta2[0],clave3);
                }
             }  
            
        }
        
        System.out.println(Collections.singletonList(problema.respuestas));
       //para mostrar las variables con su respuesta
        Iterator it_variables= problema.variables.entrySet().iterator();
       while (it_variables2.hasNext()) 
             {
                 Map.Entry e = (Map.Entry)it_variables2.next();
                 variables+=e.getKey()+" = "+e.getValue()+"\n";
              }
       
       //para mostrar las reglas
        for (int i = 0; i <problema.reglas.size(); i++) {
            reglas+="regla "+(i+1)+"------"+" "+problema.reglas.get(i)+"\n";
        }
        //para mostrar los resultados de la reglas
        Iterator it_respuestas= problema.respuestas.entrySet().iterator();
       while (it_respuestas.hasNext()) 
             {
                 Map.Entry e = (Map.Entry)it_respuestas.next();
                 respuestas+=e.getKey()+" = "+e.getValue()+"\n";
              }
            
       resultado=variables+reglas+respuestas;
         //PARA IMPRIMIR EL RESULTADO FINAL
         JOptionPane.showMessageDialog(null,resultado);

    }//GEN-LAST:event_ejecutarActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
       try{
     
      JFileChooser file=new JFileChooser();
      file.setFileFilter(new FileNameExtensionFilter("extensiones validas:"+"*.SE","se","SE"));
       file.showSaveDialog(this);
       File guarda =file.getSelectedFile();
     if(guarda !=null)
     {
      BufferedWriter bw = new BufferedWriter(new FileWriter(guarda+".SE"));
         for (int i = 0; i <lista_codigo.size(); i++) {
             bw.write(lista_codigo.get(i)+"\n");
              bw.newLine();
         }
      
      bw.close();
      JOptionPane.showMessageDialog(null,"El archivo se a guardado Exitosamente","Información",JOptionPane.INFORMATION_MESSAGE);
      }
    }catch(IOException ex){
      JOptionPane.showMessageDialog(null,"Su archivo no se ha guardado","Advertencia",JOptionPane.WARNING_MESSAGE);
    }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void definirPreguntaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_definirPreguntaActionPerformed
        //al diccionario de preguntas adiciona  como clave la variable y como valor  la pregunta realizada por el usuario
        problema.preguntas.put((String) listaPreguntas.getSelectedItem(),pregunta.getText());
    pregunta.setText("");
    }//GEN-LAST:event_definirPreguntaActionPerformed

    private void reiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reiniciarActionPerformed
       String[] args=new String[1];
        Interfaz.main(args);
        temporales();
        dispose();
    }//GEN-LAST:event_reiniciarActionPerformed

    private void objetivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_objetivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_objetivoActionPerformed

    private void manualt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manualt1ActionPerformed
        // carga  el documento  que contiene la documentacion

        try{
            //nuevo archivo en esa direccion
            File temp = new File(mu);
            InputStream is = this.getClass().getResourceAsStream("/documentacion/manualusuario.pdf");
            FileOutputStream archivoDestino = new FileOutputStream(temp);
            //FileWriter fw = new FileWriter(temp);
            byte[] buffer = new byte[1024*1024];
            //lees el archivo hasta que se acabe...
            int nbLectura;
            while ((nbLectura = is.read(buffer)) != -1)
            archivoDestino.write(buffer, 0, nbLectura);
            //cierras el archivo,el inputS y el FileW
            //fw.close();
            archivoDestino.close();
            is.close();
            //abres el archivo temporal
            Desktop.getDesktop().open(temp);

        } catch (IOException ex) {

        }   // TODO add your handling code here:
    }//GEN-LAST:event_manualt1ActionPerformed

    private void acercadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acercadeActionPerformed
        // genera el mensaje de about del sistema 
        
        JOptionPane.showOptionDialog(this, "Product Version: SHELL  SISTEMA EXPERTO   V.1.0\n" +
            "Actualizaciones: en proceso...\n" +
            "Java: 1.7.0_51; Java HotSpot(TM) 64-Bit Server VM 24.51-b03\n" +
            "Runtime: Java(TM) SE Runtime Environment 1.7.0_51-b13\n"
            + "NetBeans IDE 8.1 (Build 201511021428)\n"+
            "System recomendado: Windows 7 y posterior\n" +
            "creado por :\n"
            + "            YEISON AGUIRRE OSORIO \n" +
            "            CRISTIAN JAIR GALVIZ         \n"
            + "            JOHANNY VARGAS GONZALEZ \n\n"+
            "UNIVERSIDAD NACIONAL DE COLOMBIA - SEDE MANIZALES\n\n"
            + "© mayo2016 SHELL SISTEMA EXPERTO  All rights reserved.", "ACERCA DE SHELL SISTEMA EXPERTO"
            ,
            JOptionPane.INFORMATION_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{" OK "},"OK");
    }//GEN-LAST:event_acercadeActionPerformed

    private void jMenu7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu7ActionPerformed
    // ruta temporal del archivo
    String mu="manual usuario.pdf";
    
     // se encarga de borrar los archivos temporales
  public void temporales(){
      
      
      File temp2 = new File(mu);
        temp2.delete();
     
      
  }
  
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interfaz().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem ABRIR;
    private javax.swing.JTextField NombreVariable;
    private javax.swing.JMenuItem acercade;
    private javax.swing.JButton agregar_variable;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.ButtonGroup buttonGroup6;
    private javax.swing.JComboBox<String> combo2;
    private javax.swing.JButton definirPregunta;
    private javax.swing.JButton definirRegla;
    private javax.swing.JButton definir_obejtivo;
    private javax.swing.JButton ejecutar;
    private javax.swing.JButton jButton1;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JComboBox<String> listaPreguntas;
    private javax.swing.JComboBox<String> lista_objetivos;
    private javax.swing.JMenuItem manualt1;
    private javax.swing.JButton mostrarRegla;
    private javax.swing.JTextField objetivo;
    private javax.swing.JComboBox<String> opcionObjetivo;
    private javax.swing.JPanel panelObjetivos;
    private javax.swing.JPanel panelReglas;
    private javax.swing.JPanel panelVaribles;
    private javax.swing.JTextArea pregunta;
    private javax.swing.JButton reiniciar;
    private javax.swing.JTable tablaVariables;
    private javax.swing.JTextArea textoRegla;
    private javax.swing.JButton verficarPregunta;
    private javax.swing.JButton verificarModelo;
    // End of variables declaration//GEN-END:variables
}
