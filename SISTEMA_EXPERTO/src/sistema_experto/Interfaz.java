/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema_experto;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
        String patron_objetivo="^C+[-]+[\\w]";
        String patron_regla="[\\w]";
        
    public Interfaz() {
        initComponents();
        //para que los bonotenes no se dejen clicear hasta que no se genere un nuevo problema
        NombreVariable.setEnabled(false);
       
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
                          modelo_objetivos.addRow(new Object[]{token2,""});
                         
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
        combo1 = new javax.swing.JComboBox<>();
        combo2 = new javax.swing.JComboBox<>();
        reiniciar = new javax.swing.JButton();
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

        tablaObjetivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "OBJETIVO", "VALOR"
            }
        ));
        jScrollPane3.setViewportView(tablaObjetivos);
        if (tablaObjetivos.getColumnModel().getColumnCount() > 0) {
            tablaObjetivos.getColumnModel().getColumn(1).setCellEditor(new javax.swing.DefaultCellEditor(combo1));
        }

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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelReglasLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(definirRegla)))
                .addGroup(panelReglasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelReglasLayout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(jLabel8))
                    .addGroup(panelReglasLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelReglasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelReglasLayout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelReglasLayout.createSequentialGroup()
                                .addComponent(mostrarRegla)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        panelReglasLayout.setVerticalGroup(
            panelReglasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelReglasLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel8)
                .addGap(31, 31, 31)
                .addGroup(panelReglasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelReglasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jLabel3)))
                .addGap(18, 18, 18)
                .addGroup(panelReglasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelReglasLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(definirRegla)
                        .addGap(20, 20, 20))
                    .addGroup(panelReglasLayout.createSequentialGroup()
                        .addGroup(panelReglasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mostrarRegla))
                        .addContainerGap(22, Short.MAX_VALUE))))
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
                    .addGroup(panelVariblesLayout.createSequentialGroup()
                        .addComponent(agregar_variable)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelVariblesLayout.createSequentialGroup()
                        .addComponent(NombreVariable)
                        .addGap(55, 55, 55))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelVariblesLayout.createSequentialGroup()
                .addContainerGap(120, Short.MAX_VALUE)
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
                .addContainerGap(52, Short.MAX_VALUE))
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
                .addContainerGap(53, Short.MAX_VALUE))
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
                .addContainerGap(112, Short.MAX_VALUE))
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

        combo1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SI", "NO" }));
        combo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo1ActionPerformed(evt);
            }
        });

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
                        .addGap(104, 104, 104)
                        .addComponent(reiniciar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(combo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelReglas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 39, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(combo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        problema=new Problema();
        JFrame frame = new JFrame("INGRESO DE DATOS");
        problema.nombre = JOptionPane.showInputDialog(frame, "¿NOMBRE DEL NUEVO PROBLEMA?");      
 
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void agregar_variableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregar_variableActionPerformed
    DefaultTableModel modelo_reglas=(DefaultTableModel) tablaVariables.getModel(); 
        
    if(NombreVariable.getText().length()!=0){
        //para elminar la tabla
        System.out.println(modelo_reglas.getRowCount());
        for (int i = modelo_reglas.getRowCount() -1; i >= 0; i--){ 
          modelo_reglas.removeRow(i); 
        } 
        
       //para remover la lista de ITEMS
        lista_objetivos.removeAllItems();
        listaPreguntas.removeAllItems();
        problema.variables.put((String)NombreVariable.getText().replaceAll("\\s",""),false);
        
        //para agregar los items a la tabla y las listas
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
        NombreVariable.setText(" ");
        
    }else{
        JOptionPane.showMessageDialog( null, "FALTA EL NOMBRE DE  LA VARIABLE" );
    }
        
      
     
 
    }//GEN-LAST:event_agregar_variableActionPerformed

    private void definir_obejtivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_definir_obejtivoActionPerformed
    DefaultTableModel modelo_objetivos=(DefaultTableModel) tablaObjetivos.getModel(); 
    
          //para agreagar las variables objetivos al diccionario de objetivos
           problema.objetivos.put((String) lista_objetivos.getSelectedItem(),false);
   
          //para borrar la tabla 
          for (int i = modelo_objetivos.getRowCount() -1; i >= 0; i--){ 
          modelo_objetivos.removeRow(i); 
          } 
        
          
        
         //para insertar los objetivos a la tabla
         Iterator it_variables8 = problema.objetivos.entrySet().iterator();
            while (it_variables8.hasNext()) 
             {
                 Map.Entry e = (Map.Entry)it_variables8.next();
                 modelo_objetivos.addRow(new Object[]{e.getKey(),""});
             }

           
          
         
            
        
    }//GEN-LAST:event_definir_obejtivoActionPerformed

    private void definirReglaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_definirReglaActionPerformed
         DefaultTableModel modelo_objetivos=(DefaultTableModel) tablaObjetivos.getModel(); 
        problema.reglas.add(textoRegla.getText().replaceAll("\\s",""));
        textoRegla.setText(" ");
        //para borrar la tabla 
          for (int i = modelo_objetivos.getRowCount() -1; i >= 0; i--){ 
             modelo_objetivos.removeRow(i); 
          } 
        
        
    }//GEN-LAST:event_definirReglaActionPerformed

    private void mostrarReglaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostrarReglaActionPerformed
        String linea1="SI-";
        String regla="if(";
        System.out.println(problema.variables.size());

        textoRegla.setText("");
        
        for (int i = 0; i <problema.variables.size()-1; i++) {
           if(tablaVariables.getValueAt(i,1).equals("SI")&&tablaVariables.getValueAt(i,2).equals("Y")){
                linea1+=(String) tablaVariables.getValueAt(i,0)+"="+"true"+"-Y-";
                regla+=tablaVariables.getValueAt(i,0)+" && ";
                
            }else if(tablaVariables.getValueAt(i,1).equals("SI")&&tablaVariables.getValueAt(i,2).equals("O")){
                linea1+=(String) tablaVariables.getValueAt(i,0)+"="+"true"+"-O-";
                regla+=tablaVariables.getValueAt(i,0)+" || ";
                
            }else if(tablaVariables.getValueAt(i,1).equals("NO")&&tablaVariables.getValueAt(i,2).equals("Y")){
                linea1+=(String) tablaVariables.getValueAt(i,0)+"="+"false"+"-Y-";
                regla+="!"+tablaVariables.getValueAt(i,0)+" && ";
                
            }else if(tablaVariables.getValueAt(i,1).equals("NO")&&tablaVariables.getValueAt(i,2).equals("O")){
                linea1+=(String) tablaVariables.getValueAt(i,0)+"="+"false"+"-O-";
                regla+="!"+tablaVariables.getValueAt(i,0)+" || ";
                
            }  
        }
            
            //linea1+=(String) tablaVariables.getValueAt(problema.variables.size()-1,0)+"="+(String) tablaVariables.getValueAt(problema.variables.size()-1,1);
            if(tablaVariables.getValueAt(problema.variables.size()-1,1).equals("SI")){
                linea1+=(String) tablaVariables.getValueAt(problema.variables.size()-1,0)+"="+"true";
                regla+=tablaVariables.getValueAt(problema.variables.size()-1,0)+"){";
            }else{
                linea1+=(String) tablaVariables.getValueAt(problema.variables.size()-1,0)+"="+"false";
                regla+="!"+tablaVariables.getValueAt(problema.variables.size()-1,0)+"){";
            }
            
        //para agregar el objetivo
            //linea1+=" ENTONCES "+(String)tablaObjetivos.getValueAt(0,0)+"="+(String)tablaObjetivos.getValueAt(0,1);
            if(tablaObjetivos.getValueAt(0,1).equals("SI")){
                linea1+="-ENTONCES-"+(String)tablaObjetivos.getValueAt(0,0)+"="+"true";
                regla+=(String)tablaObjetivos.getValueAt(0,0)+"="+"true"+"}";
            }else{
                linea1+="-ENTONCES-"+(String)tablaObjetivos.getValueAt(0,0)+"="+"false";
                regla+=(String)tablaObjetivos.getValueAt(0,0)+"="+"false"+"}";
            }
            
            
            System.out.println(regla);
        textoRegla.setText(linea1);
         
    }//GEN-LAST:event_mostrarReglaActionPerformed

    private void jButton1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseReleased
          
    }//GEN-LAST:event_jButton1MouseReleased

    private void jButton1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseMoved
   
    }//GEN-LAST:event_jButton1MouseMoved

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    //para mostrar la informacion al usuario de como debe llenar la tabla
        JOptionPane.showMessageDialog( null, "VERIFICAR!\n"+"ingrese los siguientes datos antes de dar clic en MOSTRAR\n"
                                       +"1.columna VALOR: seleccione la valor de la variable\n"+
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
                 lista_codigo.add("V-"+(String) e.getKey());
             }
        //para recorrer los objetivos
        Iterator it_variables2= problema.objetivos.entrySet().iterator();
            while (it_variables2.hasNext()) 
             {
                 Map.Entry e = (Map.Entry)it_variables2.next();
                 objetivos+="C"+" "+e.getKey()+" "+e.getValue()+"\n";
                 lista_codigo.add("C-"+e.getKey());
             }
        //para recorre las reglas
        for (int i = 0; i < problema.reglas.size(); i++) {
            reglas+=problema.reglas.get(i)+"\n";
            lista_codigo.add(problema.reglas.get(i));
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
     
    //para asignar las respuestas al diccionario de variables
        Iterator it_variables2= problema.preguntas.entrySet().iterator();
            while (it_variables2.hasNext()) 
             {
                 Map.Entry e = (Map.Entry)it_variables2.next();
                    
                    int n=JOptionPane.showConfirmDialog (null,(String) e.getValue(),"PREGUNTA", JOptionPane.YES_NO_OPTION);
                    if(JOptionPane.OK_OPTION==n){
                        problema.variables.replace((String) e.getKey(),true);
                    }else{
                         problema.variables.replace((String) e.getKey(),false);
                         
                    }
             }
            
     boolean bandera=true;
     for(int j=0;j<problema.reglas.size();j++){

         String[] tokens = problema.reglas.get(j).split("-");
         System.out.println(Arrays.asList(tokens));
         int i=1;
         while(!"ENTONCES".equals(tokens[i]))
         {
             if(tokens[i].equals("Y")){
                 i++;
             }if(tokens[i].equals("O")){
                 i++;
             }else{
                 String[] tokens2=tokens[i].split("=");
                 System.out.println(Arrays.asList(tokens2));
                 boolean clave=false;
                 if(tokens2[1].equals("true")){
                     clave=true;
                 }else{
                     clave=false;
                 }
                 System.out.println(clave);
                     System.out.println(tokens2[0]);
                     System.out.println(problema.variables.get(tokens2[0]));
                 if(problema.variables.get(tokens2[0]).equals(clave)){
                     System.out.println("SI ESTA");
                        i++;
                 }else{
                      System.out.println("NO ESTA");
                        bandera=false;
                        break;
                 }
            }
             
         }
         //////////////////////ACA VOY
         System.out.println(bandera);
          if(bandera){
             //si la bandera esta true es por que se cumplieron todas las condiciones
                 System.out.println(tokens[tokens.length-1]);
                 String token5[]=tokens[tokens.length-1].split("=");
                 System.out.println(token5[1]);
                 if(token5[1].equals("true")){
                     System.out.println(token5[0]);
                     System.out.println("donde ES");
                      problema.respuestas.put(token5[0],true);
                      
                   }else{
                      problema.respuestas.put(token5[0],false);

                   }
                 
            }else{
              System.out.println(tokens[tokens.length-1]);
                 String token5[]=tokens[tokens.length-1].split("=");
                 System.out.println("donde no es");
                 if(token5[1].equals("true")){
                      problema.respuestas.put(token5[0],false);
                      
                   }else{
                      problema.respuestas.put(token5[0],true);

                   }
            }
        }
     
     
     //para imprimir las respuestas
        System.out.println(problema.respuestas.get(0));
        Iterator it_variables9= problema.respuestas.entrySet().iterator();
            while (it_variables9.hasNext()) 
             {
                 Map.Entry e = (Map.Entry)it_variables9.next();
                 if(e.getKey().equals(true)){
                     JOptionPane.showMessageDialog(null,(String)e.getKey()+" ES IGUAL: "+"NO"+"\n"); 
                 }else{
                     JOptionPane.showMessageDialog(null,(String)e.getKey()+" ES IGUALA: "+"SI"+"\n"); 
                 }
                    
             }
    

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
    problema.preguntas.put((String) listaPreguntas.getSelectedItem(),pregunta.getText());
    pregunta.setText(" ");
    }//GEN-LAST:event_definirPreguntaActionPerformed

    private void combo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combo1ActionPerformed

    private void reiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reiniciarActionPerformed
       String[] args=new String[1];
        Interfaz.main(args);
        dispose();
    }//GEN-LAST:event_reiniciarActionPerformed

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
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.ButtonGroup buttonGroup6;
    private javax.swing.JComboBox<String> combo1;
    private javax.swing.JComboBox<String> combo2;
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
    private javax.swing.JComboBox<String> lista_objetivos;
    private javax.swing.JButton mostrarRegla;
    private javax.swing.JPanel panelObjetivos;
    private javax.swing.JPanel panelReglas;
    private javax.swing.JPanel panelVaribles;
    private javax.swing.JTextArea pregunta;
    private javax.swing.JButton reiniciar;
    private javax.swing.JTable tablaObjetivos;
    private javax.swing.JTable tablaVariables;
    private javax.swing.JTextArea textoRegla;
    private javax.swing.JButton verficarPregunta;
    private javax.swing.JButton verificarModelo;
    // End of variables declaration//GEN-END:variables
}
