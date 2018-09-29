/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Volodya
 */
public class CMainJFrame extends javax.swing.JFrame {

    /**
     * Creates new form CMainJFrame
     */
    public CMainJFrame() throws Exception{
        initComponents(); 
        Class.forName("com.mysql.jdbc.Driver");

                String url="jdbc:mysql://localhost:3306/sagebook";
                String name="root";
                String pwd="";
                Connection con = (Connection) DriverManager.getConnection(url,name,pwd);

              // Statement state = con.createStatement();
                m_pState = (Statement) con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE);
                
                initialize_table_sages(); 
                initialize_table_books();
                
                ListSelectionModel cellSelectionModel = jTable1.getSelectionModel();
                cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                jTable2.setCellSelectionEnabled(true);
                ListSelectionModel cellSelectionModel2 = jTable2.getSelectionModel();
                cellSelectionModel2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                jTable2.setCellSelectionEnabled(true);
                
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
                            String Image=(String)jTable1.getValueAt(selectedRow[0], 2);
                            int age=(int)jTable1.getValueAt(selectedRow[0], 3);
                            update_row_sage(id,(String)selectedData,Image,age);
                        }
                            
                            
                        if(selectedColumns[0]==2)
                        {
                            String name=(String)jTable1.getValueAt(selectedRow[0], 1);
                            int age=(int)jTable1.getValueAt(selectedRow[0], 3);
                            update_row_sage(id,name,(String)selectedData,age);
                        }
                        if(selectedColumns[0]==3)
                        {
                            String name=(String)jTable1.getValueAt(selectedRow[0], 1);
                            String Image=(String)jTable1.getValueAt(selectedRow[0], 2);
                            int Age=Integer.parseInt((String) selectedData);
                            update_row_sage(id,name,Image,Age);
                        }   
                        
                        // System.out.println("edited: " + selectedData);
                        // update_row((int)jTable1.getValueAt(selectedRow[0], selectedColumns[0]),,Integer.parseInt((String)selectedData));
                       
                    }
                  });
                 
                 
                 
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
                            String Desc=(String)jTable2.getValueAt(selectedRow[0], 2);
                             update_row_book(id,(String)selectedData,Desc);
                        }
                           
                        if(selectedColumns[0]==2)
                        {
                            
                            String Name=(String)jTable2.getValueAt(selectedRow[0], 1);
                            update_row_book(id,Name,(String)selectedData);
                        }
                           
                        
                        // System.out.println("edited: " + selectedData);
                        // update_row((int)jTable1.getValueAt(selectedRow[0], selectedColumns[0]),,Integer.parseInt((String)selectedData));
                       
                    }
                  });
               

    }
    private Statement m_pState;
    private void update_row_sage(int id,String name,String img,int age)
    {
         try {
            String query="Select * from sage WHERE ID="+id; 
           ResultSet     rs=m_pState.executeQuery(query);
           rs.absolute(1);
          //  rs.updateInt("id", Integer.parseInt(jTextField1.getText()));
            rs.updateString("Name", name);
            rs.updateString("Img", img);
            rs.updateInt("Age", age);
            rs.updateRow();
        } catch (SQLException ex) {
           System.out.println(ex.getMessage());
           ex.printStackTrace();
        }
    }
    private void update_row_book(int id,String Name,String desc)
    {
         try {
            String query="Select * from book WHERE ID="+id; 
           ResultSet     rs=m_pState.executeQuery(query);
           rs.absolute(1);
          //  rs.updateInt("id", Integer.parseInt(jTextField1.getText()));
          rs.updateString("Name", Name);
            rs.updateString("Description", desc);
            
            rs.updateRow();
        } catch (SQLException ex) {
           System.out.println(ex.getMessage());
           ex.printStackTrace();
        }
    }
    private void remove_all_rows(JTable table)
    {
        DefaultTableModel dm = (DefaultTableModel) table.getModel();
        int rowCount = dm.getRowCount();
        //Remove rows one by one from the end of the table
       
        //Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
        dm.removeRow(i);
}
    }
    private void initialize_table_sages()
    {
        try
        {
           String query="Select * from sage";
           ResultSet rs=m_pState.executeQuery(query);
                  //DefaultListModel<String> dlm = new DefaultListModel<String>();
                  remove_all_rows(jTable1);
                  DefaultTableModel model= (DefaultTableModel) jTable1.getModel();
                  
               while(rs.next())
               {
                   int di=rs.getInt("ID");
                   String Name=rs.getString("Name");
                   String img=rs.getString("img");
                   int age=rs.getInt("Age");
                  // String elem="id: "+di+" Desc: "+Name+" price: "+price;
                   
                 model.addRow(new Object[]{di,Name,img,age});
               }
        }
        catch(Exception ex)
        {
         ex.printStackTrace();
        }
              
    }
    private void initialize_table_books()
    {
        try
        {
           String query="Select * from book";
           ResultSet rs=m_pState.executeQuery(query);
                  //DefaultListModel<String> dlm = new DefaultListModel<String>();
                  remove_all_rows(jTable2);
                  DefaultTableModel model= (DefaultTableModel) jTable2.getModel();
                  
               while(rs.next())
               {
                   int di=rs.getInt("ID");
                   String Name=rs.getString("Name");
                   String desc=rs.getString("Description");
                  
                  // String elem="id: "+di+" Desc: "+Name+" price: "+price;
                   
                 model.addRow(new Object[]{di,Name,desc});
               }
        }
        catch(Exception ex)
        {
         ex.printStackTrace();
        }
              
    }
    /**
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setLayout(new java.awt.GridLayout());

        jPanel1.setBackground(new java.awt.Color(240, 240, 0));
        jPanel1.setLayout(new java.awt.GridLayout(4, 1));

        jButton1.setText("Add book");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

        jButton2.setText("Add sage\\");
            jButton2.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton2ActionPerformed(evt);
                }
            });
            jPanel1.add(jButton2);

            jButton3.setText("Add Book to Sage");
            jButton3.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton3ActionPerformed(evt);
                }
            });
            jPanel1.add(jButton3);

            jButton4.setText("Refresh tables");
            jButton4.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton4ActionPerformed(evt);
                }
            });
            jPanel1.add(jButton4);

            jPanel2.add(jPanel1);

            jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                    "Id", "Name", "Img", "age"
                }
            ) {
                boolean[] canEdit = new boolean [] {
                    false, true, true, true
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

            jTabbedPane1.addTab("Sage", jScrollPane1);

            jTable2.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                    "Id", "Name", "Description"
                }
            ) {
                boolean[] canEdit = new boolean [] {
                    false, true, true
                };

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
            });
            jScrollPane2.setViewportView(jTable2);
            if (jTable2.getColumnModel().getColumnCount() > 0) {
                jTable2.getColumnModel().getColumn(0).setResizable(false);
                jTable2.getColumnModel().getColumn(1).setResizable(false);
                jTable2.getColumnModel().getColumn(2).setResizable(false);
            }

            jTabbedPane1.addTab("Book", jScrollPane2);

            jPanel2.add(jTabbedPane1);

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE))
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                    .addContainerGap())
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      CAddBookDialog frame = new CAddBookDialog(this,true,new CBook(),this.m_pState);
      frame.show();
     
               
      
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        CAddSageDialog frame = new CAddSageDialog(this,true,this.m_pState,new CSage());
        frame.show();
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            CSages frame = new CSages(this,true,this.m_pState);
            frame.show();
        } catch (Exception ex) {
            Logger.getLogger(CMainJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
      Thread th = new Thread(()->
      {
          try {
              Thread.sleep(3000);
          } catch (InterruptedException ex) {
              Logger.getLogger(CMainJFrame.class.getName()).log(Level.SEVERE, null, ex);
          }  initialize_table_sages(); 
                initialize_table_books();
                initialize_table_sages();
               // initialize_table_books();
      });
      th.start();
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(CMainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CMainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CMainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CMainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new CMainJFrame().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(CMainJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
