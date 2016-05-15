/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema_experto;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CRISTIAN
 */
public class Interfaz extends javax.swing.JFrame {

    Problema problema;
    ArrayList<Problema> lista_problemas= new ArrayList<Problema>();
    public Interfaz() {
        initComponents();
    
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaVariables = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaObjetivos = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        textoRegla = new javax.swing.JTextArea();
        mostrarRegla = new javax.swing.JButton();
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
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        ABRIR = new javax.swing.JMenuItem();

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

        jButton1.setText("DEFINIR REGLA");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tablaVariables.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
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
        jScrollPane2.setViewportView(textoRegla);

        mostrarRegla.setText("MOSTRAR");
        mostrarRegla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mostrarReglaActionPerformed(evt);
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
                    .addGroup(panelReglasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelReglasLayout.createSequentialGroup()
                            .addGap(94, 94, 94)
                            .addComponent(mostrarRegla))
                        .addGroup(panelReglasLayout.createSequentialGroup()
                            .addGap(88, 88, 88)
                            .addComponent(jButton1))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelReglasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelReglasLayout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jLabel8))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelReglasLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        panelReglasLayout.setVerticalGroup(
            panelReglasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelReglasLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addGroup(panelReglasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelReglasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelReglasLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(mostrarRegla)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap(22, Short.MAX_VALUE))
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
                                .addGap(0, 21, Short.MAX_VALUE)))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jMenu1.setText("ARCHIVO");

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

        ABRIR.setText("ABRIR");
        jMenu1.add(ABRIR);

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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelVaribles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelObjetivos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelReglas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(listaTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(175, 175, 175)
                        .addComponent(jLabel1)))
                .addGap(32, 32, 32))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelVaribles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelObjetivos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelReglas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(listaTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lista_objetivosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lista_objetivosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lista_objetivosActionPerformed

    private void jMenuItem3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem3MouseClicked
            
    }//GEN-LAST:event_jMenuItem3MouseClicked

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
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
       //para imprimir las variables en la tabla de variables y en el panel de objetivos
       Iterator it_variables4 = problema.variables.entrySet().iterator();
            while (it_variables4.hasNext()) 
             {
             Map.Entry e = (Map.Entry)it_variables4.next();
             lista_objetivos.addItem((String) e.getKey());
             modelo_reglas.addRow(new Object[]{e.getKey(),e.getValue()," ",});
             }
            
            
  
 
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void mostrarReglaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostrarReglaActionPerformed
        String linea1="";
        System.out.println(problema.variables.size());
        System.out.println(tablaVariables.getRowCount());
            textoRegla.setText("");
        for (int i = 0; i <problema.variables.size(); i++) {
            linea1+=(String) tablaVariables.getValueAt(i,0);
            linea1+=" = "+ (String)tablaVariables.getValueAt(i,2);
            if(tablaVariables.getValueAt(i,3).equals("Y")){
                linea1+=" Y ";
            }else if(tablaVariables.getValueAt(i,3).equals("O")){
                linea1+=" O ";
            }else if(tablaVariables.getValueAt(i,3).equals("NULL")){
                linea1+="NULL";
            }
 
        }
        for (int i = 0; i <problema.objetivos.size(); i++) {
            linea1+=" entonces "+(String)tablaObjetivos.getValueAt(i, 0)+" = "+(String)tablaObjetivos.getValueAt(i,2);
        }
    
        textoRegla.setText(linea1);

    }//GEN-LAST:event_mostrarReglaActionPerformed

    private void listaTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listaTablaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_listaTablaActionPerformed

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
    private javax.swing.JButton definir_obejtivo;
    private javax.swing.JButton jButton1;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JComboBox<String> listaTabla;
    private javax.swing.JComboBox<String> lista_objetivos;
    private javax.swing.JButton mostrarRegla;
    private javax.swing.JCheckBox multivalorada;
    private javax.swing.JCheckBox numerica;
    private javax.swing.JPanel panelObjetivos;
    private javax.swing.JPanel panelReglas;
    private javax.swing.JPanel panelVaribles;
    private javax.swing.JTable tablaObjetivos;
    private javax.swing.JTable tablaVariables;
    private javax.swing.JTextArea textoRegla;
    private javax.swing.JCheckBox univalorada;
    // End of variables declaration//GEN-END:variables
}
