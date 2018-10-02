/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author s26-d
 */
public class CTableForAllDialog extends javax.swing.JDialog {

    /**
     * Creates new form CTableForAllDialog
     */
    public CTableForAllDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
       
    }
    public CTableForAllDialog(java.awt.Frame parent, boolean modal,CategoregyDao aCatDao,TovarDao aTovarDao) {
        super(parent, modal);
        initComponents();
        try {
            this.m_pCategoregyDao=aCatDao;
            this.m_pTovarDao=aTovarDao;
            this.m_arCats=m_pCategoregyDao.getAllCategoregies();
            this.m_arTovars=m_pTovarDao.getAllTovars();
            
          initialize_table_tov();
          initialize_table_cat();
            
            ListSelectionModel cellSelectionModel = jTable1.getSelectionModel();
            cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            jTable1.setCellSelectionEnabled(true);
            
            ListSelectionModel cellSelectionModel2 = jTable2.getSelectionModel();
            cellSelectionModel2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            jTable2.setCellSelectionEnabled(true);
            
            addEventChange_table1();
            addEventChange_table2();
        } catch (SQLException ex) {
             System.out.println(ex.getMessage());
             ex.printStackTrace();
        }
        
        
        
        
    }
    private void initialize_table_cat()
    {
     DefaultTableModel model =(DefaultTableModel) jTable2.getModel();
       for(Categoregy tov:m_arCats)
       {    
        model.addRow(new Object[]{tov.getId(),tov.getName()});
       }
    }
    private void initialize_table_tov()
    {
       DefaultTableModel model =(DefaultTableModel) jTable1.getModel();
       for(Tovar tov:m_arTovars)
       {    String nameCat=null;
            int id=tov.getId();
            String name = tov.getName();
            int price =tov.getPrice();
           for(Categoregy cat :m_arCats)
           {
               if(tov.getCat().getId()==cat.getId())
               {
                   nameCat=cat.getName();
                   break;
               }
           }
        model.addRow(new Object[]{id, name,price,nameCat});
       }
      // jTable1.setModel(model);
    
    }
    private void addEventChange_table2()
    {
    jTable2.getModel().addTableModelListener(new TableModelListener() {

                    public void tableChanged(TableModelEvent e) {
                      Object selectedData = null;

                        int[] selectedRow = jTable2.getSelectedRows();
                        int[] selectedColumns = jTable2.getSelectedColumns();

                        for (int i = 0; i < selectedRow.length; i++) {
                          for (int j = 0; j < selectedColumns.length; j++) {
                            selectedData =  jTable2.getValueAt(selectedRow[i], selectedColumns[j]);
                          }
                        }
                        int id=(int) jTable2.getValueAt(selectedRow[0], 0);
                        
                        
                        
                        if(selectedColumns[0]==1)
                        {
                            
                            
                            update_row_cat(id,(String)selectedData);
                        }
                            
                            
                     
                       
                    }
                  });
    
    }
    private void addEventChange_table1()
    {
       jTable1.getModel().addTableModelListener(new TableModelListener() {

                    public void tableChanged(TableModelEvent e) {
                      Object selectedData = null;

                        int[] selectedRow = jTable1.getSelectedRows();
                        int[] selectedColumns = jTable1.getSelectedColumns();

                        for (int i = 0; i < selectedRow.length; i++) {
                          for (int j = 0; j < selectedColumns.length; j++) {
                            selectedData =  jTable1.getValueAt(selectedRow[i], selectedColumns[j]);
                          }
                        }
                        int id=(int) jTable1.getValueAt(selectedRow[0], 0);
                        
                        
                        
                        if(selectedColumns[0]==1)
                        {
                            
                            int price=(int)jTable1.getValueAt(selectedRow[0], 2);
                            update_row_tovar(id,(String)selectedData,price);
                        }
                            
                            
                        if(selectedColumns[0]==2)
                        {
                            String name=(String)jTable1.getValueAt(selectedRow[0], 1);
                            
                            update_row_tovar(id,name,Integer.parseInt((String)selectedData));
                        }
                      
                        
                        // System.out.println("edited: " + selectedData);
                        // update_row((int)jTable1.getValueAt(selectedRow[0], selectedColumns[0]),,Integer.parseInt((String)selectedData));
                       
                    }
                  });
    }
    private void update_row_tovar(int id,String name,int price)
    {
        for(Tovar tov:m_arTovars)
        {
            if(tov.getId()==id)
            {
                try {
                    tov.setName(name);
                    tov.setPrice(price);
                    m_pTovarDao.updateTovar(tov);
                    break;
                } catch (SQLException ex) {
                   System.out.println(ex.getMessage());
                   ex.printStackTrace();
                }
               
                
            }
        }
    }
    private void update_row_cat(int id,String name)
    {
        for(Categoregy cat :this.m_arCats)
        {
            if(cat.getId()==id)
            {
                try {
                    cat.setName(name);
                    this.m_pCategoregyDao.updateCategoregy(cat);
                } catch (SQLException ex) {
                   System.out.println(ex.getMessage());
                   ex.printStackTrace();
                }
            }
        }
    }
    private CategoregyDao m_pCategoregyDao;
    private TovarDao m_pTovarDao;
    private List<Categoregy> m_arCats;
    private List<Tovar> m_arTovars;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Price", "Category"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
        }

        jTabbedPane1.addTab("Tovars", jScrollPane1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(1).setResizable(false);
        }

        jTabbedPane1.addTab("Categories", jScrollPane2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(CTableForAllDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CTableForAllDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CTableForAllDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CTableForAllDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CTableForAllDialog dialog = new CTableForAllDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
