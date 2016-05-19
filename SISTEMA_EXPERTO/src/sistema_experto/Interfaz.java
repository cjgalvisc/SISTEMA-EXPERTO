/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema_experto;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JCheckBox;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CRISTIAN
 */
public class Interfaz extends javax.swing.JFrame {

    //para los problemas que se creen o leen con la aplicacion
    Problema problema;
    //para almacenar cada problema
    ArrayList<Problema> lista_problemas= new ArrayList<Problema>();
    //variable temporal para almacenar cada linea del TXT 
    String linea;
    //para almacenar el nombre del TXT
    String nombre_problema;
    //donde queda almacenada la informacion de un problema creado para ser almacenado en un TXT
    String codigo="";
    //arraylist donde queda cada linea del TXT
    ArrayList<String> lineas= new ArrayList<String>();
    //expresiones regulares para verificar la sintaxis del TXT.SE
        String patron_comentario="^[%]+[\\w]";
        String patron_variable="^[\\w]+[\\s]+[N|U|M]";
        String patron_objetivo="^C+[\\s]+[\\w]+[\\s]+[N|U|M]";
        String patron_regla="[\\w]";
        
    public Interfaz() {
        initComponents();
        //para que los bonotenes no se dejen clicear hasta que no se genere un nuevo problema
        NombreVariable.setEnabled(false);
        univalorada.setEnabled(false);
        numerica.setEnabled(false);
        multivalorada.setEnabled(false);
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
         //creo un modelo de la tabla objetivos para meter los objetivos
         DefaultTableModel modelo_objetivos=(DefaultTableModel) tablaObjetivos.getModel(); 
         //instancion un objeto de la clase problema cada vez que se abre un nuevo archivo
         problema=new Problema();
         try{
                BufferedReader bf=new BufferedReader(new FileReader(direccion));
                while((linea=bf.readLine())!=null){
                    //para quitar los espacios en blanco y leer linea a linea
                    if(!(linea.replaceAll("\\s+", " ").trim().equals(""))){
                        lineas.add(linea);
                    }
                }
             //llamo la funcion para comprobar sintaxis
             //si la sintaxis es correcta extraigo la informacion y la monto en las tablas
              if(sintaxis(lineas)){
                  //para habilitar los botnes
                    NombreVariable.setEnabled(true);
                    univalorada.setEnabled(true);
                    numerica.setEnabled(true);
                    multivalorada.setEnabled(true);
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
                          StringTokenizer tokens=new StringTokenizer(lineas.get(i));
                          String token1=tokens.nextToken();
                          String token2=tokens.nextToken();
                          problema.variables.put(token1, token2);
                          modelo_variables.addRow(new Object[]{token1,token2," ",});
                          
                      }else if(coincide_obj){//si se cumple es un objetivo
                          StringTokenizer tokens=new StringTokenizer(lineas.get(i));
                          String token1=tokens.nextToken();
                          String token2=tokens.nextToken();
                          String token3=tokens.nextToken();
                          problema.objetivos.put(token2, token3);
                          modelo_objetivos.addRow(new Object[]{token2,token3,});
                          
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
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jLabel1 = new javax.swing.JLabel();
        panelReglas = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        definirRegla = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaVariables = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaObjetivos = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        textoRegla = new javax.swing.JTextArea();
        mostrarRegla = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        panelVaribles = new javax.swing.JPanel();
        agregar_variable = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        NombreVariable = new javax.swing.JTextField();
        numerica = new javax.swing.JCheckBox();
        univalorada = new javax.swing.JCheckBox();
        multivalorada = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        panelObjetivos = new javax.swing.JPanel();
        definir_obejtivo = new javax.swing.JButton();
        lista_objetivos = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        listaTabla = new javax.swing.JComboBox<>();
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
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        ABRIR = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

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
                "NOMBRE", "TIPO", "VALOR", "CONDICIONAL"
            }
        ));
        jScrollPane1.setViewportView(tablaVariables);
        if (tablaVariables.getColumnModel().getColumnCount() > 0) {
            tablaVariables.getColumnModel().getColumn(3).setCellEditor(new javax.swing.DefaultCellEditor(listaTabla));
        }

        tablaObjetivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "OBJETIVO", "TIPO", "Valor"
            }
        ));
        jScrollPane3.setViewportView(tablaObjetivos);

        textoRegla.setColumns(20);
        textoRegla.setRows(5);
        textoRegla.setEnabled(false);
        jScrollPane2.setViewportView(textoRegla);

        mostrarRegla.setText("MOSTRAR");
        mostrarRegla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mostrarReglaActionPerformed(evt);
            }
        });

        jButton1.setText("...");
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

        javax.swing.GroupLayout panelReglasLayout = new javax.swing.GroupLayout(panelReglas);
        panelReglas.setLayout(panelReglasLayout);
        panelReglasLayout.setHorizontalGroup(
            panelReglasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelReglasLayout.createSequentialGroup()
                .addGroup(panelReglasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelReglasLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelReglasLayout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(definirRegla))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelReglasLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(mostrarRegla)))
                .addGroup(panelReglasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelReglasLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40))
                    .addGroup(panelReglasLayout.createSequentialGroup()
                        .addGroup(panelReglasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelReglasLayout.createSequentialGroup()
                                .addGap(83, 83, 83)
                                .addComponent(jLabel8))
                            .addGroup(panelReglasLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        panelReglasLayout.setVerticalGroup(
            panelReglasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelReglasLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel8)
                .addGroup(panelReglasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelReglasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelReglasLayout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelReglasLayout.createSequentialGroup()
                            .addGap(38, 38, 38)
                            .addGroup(panelReglasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelReglasLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jButton1)))
                .addGroup(panelReglasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelReglasLayout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(definirRegla))
                    .addGroup(panelReglasLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(panelReglasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mostrarRegla)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        numerica.setSelected(true);
        numerica.setText("NUMERICA");
        numerica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numericaActionPerformed(evt);
            }
        });

        univalorada.setText("UNIVALORADA");
        univalorada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                univaloradaActionPerformed(evt);
            }
        });

        multivalorada.setText("MULTIVALORADA");
        multivalorada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                multivaloradaActionPerformed(evt);
            }
        });

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
                    .addGroup(panelVariblesLayout.createSequentialGroup()
                        .addComponent(agregar_variable)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelVariblesLayout.createSequentialGroup()
                        .addGroup(panelVariblesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(NombreVariable)
                            .addGroup(panelVariblesLayout.createSequentialGroup()
                                .addGroup(panelVariblesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(univalorada)
                                    .addComponent(numerica)
                                    .addComponent(multivalorada))
                                .addGap(0, 18, Short.MAX_VALUE)))
                        .addGap(55, 55, 55))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelVariblesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(numerica)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(univalorada)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(multivalorada)
                .addGap(18, 18, 18)
                .addComponent(agregar_variable)
                .addContainerGap(34, Short.MAX_VALUE))
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
                .addContainerGap(14, Short.MAX_VALUE))
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

        listaTabla.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NULL", "Y", "O" }));
        listaTabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listaTablaActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel5.setText("DEFINIR PREGUNTAS");

        pregunta.setColumns(20);
        pregunta.setRows(5);
        jScrollPane4.setViewportView(pregunta);

        definirPregunta.setText("DEFINIR PREGUNTA");
        definirPregunta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                definirPreguntaActionPerformed(evt);
            }
        });

        verficarPregunta.setText("...");
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(listaPreguntas, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(verficarPregunta, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(253, 253, 253)
                        .addComponent(jLabel5)))
                .addContainerGap(62, Short.MAX_VALUE))
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
                        .addComponent(verificarModelo))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(ejecutar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(verificarModelo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ejecutar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelVaribles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelObjetivos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(listaTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelReglas, javax.swing.GroupLayout.PREFERRED_SIZE, 703, Short.MAX_VALUE))
                        .addGap(0, 7, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(listaTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        univalorada.setEnabled(true);
        numerica.setEnabled(true);
        multivalorada.setEnabled(true);
        agregar_variable.setEnabled(true);
         definir_obejtivo.setEnabled(true);
        lista_objetivos.setEnabled(true);
        
        mostrarRegla.setEnabled(true);
        definirRegla.setEnabled(true);
        definirPregunta.setEnabled(true);
        
        verificarModelo.setEnabled(true);
        ejecutar.setEnabled(true);
        problema=new Problema();
        JFrame frame = new JFrame("INGRESO DE DATOS");
        problema.nombre = JOptionPane.showInputDialog(frame, "¿NOMBRE DEL NUEVO PROBLEMA?");      
        
        lista_problemas.add(problema);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void agregar_variableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregar_variableActionPerformed
    DefaultTableModel modelo_reglas=(DefaultTableModel) tablaVariables.getModel(); 
        
    if(NombreVariable.getText().length()==0){
        JOptionPane.showMessageDialog( null, "FALTA EL NOMBRE DE  LA VARIABLE" );
    }else{
        if(numerica.isSelected()){
            System.out.println(NombreVariable.getText());
            problema.variables.put(NombreVariable.getText(),"N");
        }else if(univalorada.isSelected()){
            problema.variables.put(NombreVariable.getText(),"U");
        }else if(multivalorada.isSelected()){
            problema.variables.put(NombreVariable.getText(),"M");
        }
        
    }
      //para elminar la tabla
        for (int i = modelo_reglas.getRowCount() -1; i >= 0; i--){ 
          modelo_reglas.removeRow(i); 
        } 
       //para remover la lisa de ITEMS
        lista_objetivos.removeAllItems();
        listaPreguntas.removeAllItems();
       //para imprimir las variables en la tabla de variables y en el panel de objetivos
       Iterator it_variables4 = problema.variables.entrySet().iterator();
            while (it_variables4.hasNext()) 
             {
             Map.Entry e = (Map.Entry)it_variables4.next();
             lista_objetivos.addItem((String) e.getKey());
             listaPreguntas.addItem((String) e.getKey());
             
             modelo_reglas.addRow(new Object[]{e.getKey(),e.getValue()," ",});
             }
            
            NombreVariable.setText(" ");
  
 
    }//GEN-LAST:event_agregar_variableActionPerformed

    private void numericaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numericaActionPerformed
        if(numerica.isSelected()){
            univalorada.setSelected(false);
            multivalorada.setSelected(false);     
        }
    }//GEN-LAST:event_numericaActionPerformed

    private void univaloradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_univaloradaActionPerformed
        
        if(univalorada.isSelected()){
            numerica.setSelected(false);
            multivalorada.setSelected(false);
        }
    }//GEN-LAST:event_univaloradaActionPerformed

    private void multivaloradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_multivaloradaActionPerformed
        if(multivalorada.isSelected()){
            univalorada.setSelected(false);
            numerica.setSelected(false); 
        }
    }//GEN-LAST:event_multivaloradaActionPerformed

    private void definir_obejtivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_definir_obejtivoActionPerformed
    DefaultTableModel modelo_objetivos=(DefaultTableModel) tablaObjetivos.getModel(); 
    
 
   
       //para agreagar las variables objetivos al diccionario de objetivos
       Iterator it_variables6 = problema.variables.entrySet().iterator();
            while (it_variables6.hasNext()) 
             {
                 Map.Entry e = (Map.Entry)it_variables6.next();
                 if(lista_objetivos.getSelectedItem()==e.getKey())
                 {
                     problema.objetivos.put((String)e.getKey(),(String) e.getValue());
   
                 }
             }
            
          for (int i = modelo_objetivos.getRowCount() -1; i >= 0; i--){ 
          modelo_objetivos.removeRow(i); 
          } 
        
            System.out.println(problema.objetivos.keySet());
        
         //para insertar los objetivos a la tabla
         Iterator it_variables8 = problema.objetivos.entrySet().iterator();
            while (it_variables8.hasNext()) 
             {
                 Map.Entry e = (Map.Entry)it_variables8.next();
                 modelo_objetivos.addRow(new Object[]{e.getKey(),e.getValue()," "});
             }

           
          
         
            
        
    }//GEN-LAST:event_definir_obejtivoActionPerformed

    private void definirReglaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_definirReglaActionPerformed
        problema.reglas.add(textoRegla.getText());
    }//GEN-LAST:event_definirReglaActionPerformed

    private void mostrarReglaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostrarReglaActionPerformed
        String linea1="SI ";
        System.out.println(problema.variables.size());
        System.out.println(tablaVariables.getRowCount());
            textoRegla.setText("");
            System.out.println();
        for (int i = 0; i <problema.variables.size(); i++) {
            System.out.println(linea1);
            if(tablaVariables.getValueAt(i,2)!=""){
             linea1+=(String) tablaVariables.getValueAt(i,0);
            linea1+=" = "+ (String)tablaVariables.getValueAt(i,2);
            if(tablaVariables.getValueAt(i,3).equals("Y")){
                linea1+=" Y ";
            }else if(tablaVariables.getValueAt(i,3).equals("O")){
                linea1+=" O ";
            }else if(tablaVariables.getValueAt(i,3).equals("NULL")||tablaVariables.getValueAt(i,3).equals("")){
                linea1+=" ";
            
            }
           }
        }
        for (int i = 0; i <problema.objetivos.size(); i++) {
            linea1+=" ENTONCES "+(String)tablaObjetivos.getValueAt(i, 0)+" = "+(String)tablaObjetivos.getValueAt(i,2);
        }
    
        textoRegla.setText(linea1);

    }//GEN-LAST:event_mostrarReglaActionPerformed

    private void listaTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listaTablaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_listaTablaActionPerformed

    private void jButton1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseReleased
          
    }//GEN-LAST:event_jButton1MouseReleased

    private void jButton1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseMoved
   
    }//GEN-LAST:event_jButton1MouseMoved

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    //para mostrar la informacion al usuario de como debe llenar la tabla
        JOptionPane.showMessageDialog( null, "VERIFICAR!\n"+"ingrese los siguientes datos antes de dar clic en MOSTRAR\n"
                                       +"1.columna VALOR: valor de la variable segun el TIPO\n"+
                                       "2.columna CONDICIONAL:si no tiene condicion seleccione NULL\n"); 
    }//GEN-LAST:event_jButton1ActionPerformed

    private void verficarPreguntaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verficarPreguntaActionPerformed
       //para mostrar la informacion al usuario de como debe realizar la pregunta
        JOptionPane.showMessageDialog( null, "VERIFICAR!\n"+"realize una pregunta ¿***texto***?" ); 
    }//GEN-LAST:event_verficarPreguntaActionPerformed

    private void verificarModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verificarModeloActionPerformed
        
        String variables="";
        String objetivos="";
        String reglas="";
        //para recorrer las varibles
        Iterator it_variables= problema.variables.entrySet().iterator();
            while (it_variables.hasNext()) 
             {
                 Map.Entry e = (Map.Entry)it_variables.next();
                 variables+=e.getKey()+" "+e.getValue()+"\n";
             }
        //para recorrer los objetivos
        Iterator it_variables2= problema.objetivos.entrySet().iterator();
            while (it_variables2.hasNext()) 
             {
                 Map.Entry e = (Map.Entry)it_variables2.next();
                 objetivos+="C"+" "+e.getKey()+" "+e.getValue()+"\n";
             }
        //para recorre las reglas
        for (int i = 0; i < problema.reglas.size(); i++) {
            reglas+=problema.reglas.get(i)+"\n";
        }
        codigo=variables+objetivos+reglas;
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
     //falta verificar en el diccionario de variables para asignar pregunta segun tipo
        Iterator it_variables2= problema.preguntas.entrySet().iterator();
            while (it_variables2.hasNext()) 
             {
                 Map.Entry e = (Map.Entry)it_variables2.next();
                 if(e.getValue().equals("N")){
                     
                 }else  {
                     JCheckBox   rememberChk1 = new JCheckBox("SI");
                     JCheckBox   rememberChk2 = new JCheckBox("NO");
                     String msg =(String) e.getValue(); 
                     Object[]  msgContent = {msg, rememberChk1,rememberChk2}; 
                     int n=JOptionPane.showConfirmDialog (null,msgContent,"Title", JOptionPane.YES_NO_OPTION);
                 }
             }

    }//GEN-LAST:event_ejecutarActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void definirPreguntaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_definirPreguntaActionPerformed
    problema.preguntas.put((String) listaPreguntas.getSelectedItem(),pregunta.getText());
    }//GEN-LAST:event_definirPreguntaActionPerformed

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
    private javax.swing.JButton agregar_variable;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton definirPregunta;
    private javax.swing.JButton definirRegla;
    private javax.swing.JButton definir_obejtivo;
    private javax.swing.JButton ejecutar;
    private javax.swing.JButton jButton1;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JComboBox<String> listaPreguntas;
    private javax.swing.JComboBox<String> listaTabla;
    private javax.swing.JComboBox<String> lista_objetivos;
    private javax.swing.JButton mostrarRegla;
    private javax.swing.JCheckBox multivalorada;
    private javax.swing.JCheckBox numerica;
    private javax.swing.JPanel panelObjetivos;
    private javax.swing.JPanel panelReglas;
    private javax.swing.JPanel panelVaribles;
    private javax.swing.JTextArea pregunta;
    private javax.swing.JTable tablaObjetivos;
    private javax.swing.JTable tablaVariables;
    private javax.swing.JTextArea textoRegla;
    private javax.swing.JCheckBox univalorada;
    private javax.swing.JButton verficarPregunta;
    private javax.swing.JButton verificarModelo;
    // End of variables declaration//GEN-END:variables
}
