//    uniCenta oPOS  - Touch Friendly Point Of Sale
//    Copyright (c) 2009-2016 uniCenta & previous Openbravo POS works
//    https://unicenta.com
//
//    This file is part of uniCenta oPOS
//
//    uniCenta oPOS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//   uniCenta oPOS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with uniCenta oPOS.  If not, see <http://www.gnu.org/licenses/>.

package com.openbravo.pos.panels;

import com.alee.extended.tree.FileTreeRootType;
import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.loader.IKeyed;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.pos.config.JPanelConfigCompany;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppUser;
import com.openbravo.pos.forms.AppView;
import com.sun.java.accessibility.util.AWTEventMonitor;
import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 *
 * @author adrianromero
 */
public final class PaymentsEditor extends javax.swing.JPanel implements EditorRecord {
    
    private final ComboBoxValModel m_ReasonModel;
    
    private String m_sId;
    private String m_sPaymentId;
    private Date datenew;
   
    private final AppView m_App;
    private String m_sNotes;
    
    private String usuario;    
    private AppUser m_User;  
    private int consecutivo; 
    private int imp_consecutivo; 
    private String fecha;
    private String tipo;
    private String valor;
    private String nota;
    
    /** Creates new form JPanelPayments
     * @param oApp
     * @param dirty */
    public PaymentsEditor(AppView oApp, DirtyManager dirty) {
        
        m_App = oApp;
               
        initComponents();
        leerArchivo();
        
        m_ReasonModel = new ComboBoxValModel();
        m_ReasonModel.add(new PaymentReasonPositive("cashin", AppLocal.getIntString("transpayment.cashin")));
        m_ReasonModel.add(new PaymentReasonNegative("cashout", AppLocal.getIntString("transpayment.cashout")));              
        m_jreason.setModel(m_ReasonModel);
        
        jTotal.addEditorKeys(m_jKeys);
        
        m_jreason.addActionListener(dirty);
        jTotal.addPropertyChangeListener("Text", dirty);
        m_jNotes.addPropertyChangeListener("Text", dirty);
        m_jNotes.addEditorKeys(m_jKeys);     
                      
        writeValueEOF();
    }
   
  /**
  * Lee un archivo de propiedades desde una ruta especifica
  */
 private void leerArchivo() {   
    try {
    /**Creamos un Objeto de tipo Properties*/  
    Properties p = new Properties();
    
    /**Cargamos el archivo desde la ruta especificada*/
    p.load(new FileReader("properties/secuencia.properties"));
    
    /**Obtenemos los parametros definidos en el archivo*/  
    String secuencia = p.getProperty("secuencia");    
    consecutivo = Integer.parseInt(secuencia);
    consecutivo++;
    txtSecuencia.setText(String.valueOf(consecutivo));
    
  } catch (FileNotFoundException e) {
   System.out.println("Error, El archivo secuencia.properties no existe");
  } catch (IOException e) {
   System.out.println("Error, No se puede leer el archivo secuencia.properties");
  }
 }
 
 /**
  * Guarda un archivo de propiedades en una ruta especifica
  */
  private void GuardarArchivo() { 
 /**Creamos un Objeto de tipo Properties*/
        Properties p = new Properties();
        
        /**Escribimos en el archivo de tipo Properties*/  
        p.setProperty("secuencia", String.valueOf(consecutivo));
        consecutivo++;
        imp_consecutivo=consecutivo;
        imp_consecutivo--;
        txtSecuencia.setText(String.valueOf(consecutivo));
        try {
            p.store(new FileWriter("properties/secuencia.properties"),"configuracion secuencia de movimientos de caja");
             JOptionPane.showMessageDialog(null, "La secuencia de este movimiento se há guardado correctamente");            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PaymentsEditor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PaymentsEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
  
    /**
     *
     */
    @Override
    public void writeValueEOF() {
        m_sId = null;
        m_sPaymentId = null;
        datenew = null;
        setReasonTotal(null, null);
        m_jreason.setEnabled(false);
        jTotal.setEnabled(false);
// JG Added July 2011
        m_sNotes = null;
        m_jNotes.setEnabled(false);

    }

    /**
     *
     */
    @Override
    public void writeValueInsert() {

        m_sId = null;
        m_sPaymentId = null;
        datenew = null;
        setReasonTotal("cashin", null);
        m_jreason.setEnabled(true);
        jTotal.setEnabled(true);   
        jTotal.activate();
// JG Added July 2011
        m_sNotes = null;
        m_jNotes.setEnabled(true);
        m_jNotes.setText(m_sNotes);
    }
    
    /**
     *
     * @param value
     */
    @Override
    public void writeValueDelete(Object value) {
        Object[] payment = (Object[]) value;
        m_sId = (String) payment[0];
        datenew = (Date) payment[2];
        m_sPaymentId = (String) payment[3];
        setReasonTotal(payment[4], payment[5]);
        m_jreason.setEnabled(false);
        jTotal.setEnabled(false);
// JG Added July 2011
        m_sNotes = (String) payment[6];
        m_jNotes.setEnabled(false);
    }
    
    /**
     *
     * @param value
     */
    @Override
    public void writeValueEdit(Object value) {
        Object[] payment = (Object[]) value;
        m_sId = (String) payment[0];
        datenew = (Date) payment[2];
        m_sPaymentId = (String) payment[3];
        setReasonTotal(payment[4], payment[5]);
        m_jreason.setEnabled(false);
        jTotal.setEnabled(false);
        jTotal.activate();
// JG Added July 2011
        m_sNotes = (String) payment[6];
        m_jNotes.setEnabled(false);
    }
      
    /**
     *
     * @return
     * @throws BasicException
     */
    @Override
    public Object createValue() throws BasicException {
//JG Modified Array + 1 - July 2011
        Object[] payment = new Object[7];
        payment[0] = m_sId == null ? UUID.randomUUID().toString() : m_sId;
        payment[1] = m_App.getActiveCashIndex();
        payment[2] = datenew == null ? new Date() : datenew;
        payment[3] = m_sPaymentId == null ? UUID.randomUUID().toString() : m_sPaymentId;
        payment[4] = m_ReasonModel.getSelectedKey();
        PaymentReason reason = (PaymentReason) m_ReasonModel.getSelectedItem();
        Double dtotal = jTotal.getDoubleValue();
        payment[5] = reason == null ? dtotal : reason.addSignum(dtotal);
// JG Added July 2011
        String snotes = "";
        m_sNotes = m_jNotes.getText();
        payment[6] = m_sNotes == null ? snotes : m_sNotes;
              
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date datenew = new Date();
        String fechaCadena = sdf.format(datenew); 
        usuario = m_App.getAppUserView().getUser().getName();
        fecha = fechaCadena;
        tipo = reason.toString();
        valor = dtotal.toString();
        nota = m_sNotes;    
            
        if(m_jButtonPrint.isSelected()){
           GuardarArchivo();  
           consultarDatosBDImpresora();  
        }       
        
        return payment;
    }
    
    //METODO SELECCIONAR DATOS A IMPRIMIR
    public void consultarDatosBDImpresora(){                                     
                        //DATOS QUEMADOS EN LA FACTURA  
                        Impresora imp;                    		                                                                                                                             
                        String textoBlanco = "\n";		       
                        imp = new Impresora();   
                        String dato1 = "\nSecuencia: "+imp_consecutivo; 
                        String dato2 = "\nUsuario: "+usuario; 
                        String dato3 = "\nFecha:  "+fecha;
                        String dato4 = "\nPago:    "+tipo;
                        String dato5 = "\nValor:   "+"$"+valor;
                        String dato6 = "\nNota:    "+nota;                        
                        
                        if(chb80.isSelected()){
                          String tituloFacturaVenta1 = "********** MOVIMIENTOS DE CAJA ***********\n";                        
                          String separadores1 = "******************************************"; 
                          imp.imprimir(tituloFacturaVenta1 + " " + dato1 + "  " + dato2 + " " + dato3 + " " 
                        + dato4 + " " + dato5 + " " + dato6 + textoBlanco);//IMPRIMIR LO QUE ESCRIBE EL USUARIO 
                        }      
                        if(chb76.isSelected()){
                          String tituloFacturaVenta2 = "*** MOVIMIENTOS DE CAJA ***\n";                        
                          String separadores2 = "****************************";  
                          imp.imprimir(tituloFacturaVenta2 + " " + dato1 + "  " + dato2 + " " + dato3 + " " 
                        + dato4 + " " + dato5 + " " + dato6 + textoBlanco);//IMPRIMIR LO QUE ESCRIBE EL USUARIO 
                        }
                        if(chb58.isSelected()){
                          String tituloFacturaVenta3 = "* MOVIMIENTOS DE CAJA *\n";                        
                          String separadores3 = "**********************";  
                          imp.imprimir(tituloFacturaVenta3 + " " + dato1 + "  " + dato2 + " " + dato3 + " " 
                        + dato4 + " " + dato5 + " " + dato6 + textoBlanco);//IMPRIMIR LO QUE ESCRIBE EL USUARIO 
                        }                                                                                         
    }
    
    /**
     *
     * @return
     */
    @Override
    public Component getComponent() {
        return this;
    }
    
    /**
     *
     */
    @Override
    public void refresh() {
    }  
    
    private void setReasonTotal(Object reasonfield, Object totalfield) {
        
        m_ReasonModel.setSelectedKey(reasonfield);
             
        PaymentReason reason = (PaymentReason) m_ReasonModel.getSelectedItem();     
        
        if (reason == null) {
            jTotal.setDoubleValue((Double) totalfield);
        } else {
            jTotal.setDoubleValue(reason.positivize((Double) totalfield));
        }  
    }
    
    private static abstract class PaymentReason implements IKeyed {
        private String m_sKey;
        private String m_sText;
        
        public PaymentReason(String key, String text) {
            m_sKey = key;
            m_sText = text;
        }
        @Override
        public Object getKey() {
            return m_sKey;
        }
        public abstract Double positivize(Double d);
        public abstract Double addSignum(Double d);
        
        @Override
        public String toString() {
            return m_sText;
        }
    }
    private static class PaymentReasonPositive extends PaymentReason {
        public PaymentReasonPositive(String key, String text) {
            super(key, text);
        }
        @Override
        public Double positivize(Double d) {
            return d;
        }
        @Override
        public Double addSignum(Double d) {
            if (d == null) {
                return null;
            } else if (d.doubleValue() < 0.0) {
                return new Double(-d.doubleValue());
            } else {
                return d;
            }
        }
    }
    private static class PaymentReasonNegative extends PaymentReason {
        public PaymentReasonNegative(String key, String text) {
            super(key, text);
        }
        @Override
        public Double positivize(Double d) {
            return d == null ? null : new Double(-d.doubleValue());
        }
        @Override
        public Double addSignum(Double d) {
            if (d == null) {
                return null;
            } else if (d.doubleValue() > 0.0) {
                return new Double(-d.doubleValue());
            } else {
                return d;
            }
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        m_jreason = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jTotal = new com.openbravo.editor.JEditorCurrency();
        m_jNotes = new com.openbravo.editor.JEditorString();
        m_jButtonPrint = new javax.swing.JToggleButton();
        jlblPrinterStatus = new javax.swing.JLabel();
        chb80 = new com.alee.extended.button.WebSwitch();
        chb76 = new com.alee.extended.button.WebSwitch();
        chb58 = new com.alee.extended.button.WebSwitch();
        txt80 = new javax.swing.JLabel();
        txt76 = new javax.swing.JLabel();
        txt58 = new javax.swing.JLabel();
        txtSecuencia = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        m_jKeys = new com.openbravo.editor.JEditorKeys();

        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        setLayout(new java.awt.BorderLayout());

        jPanel3.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel3ComponentShown(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText(AppLocal.getIntString("label.paymentreason")); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(110, 30));

        m_jreason.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        m_jreason.setFocusable(false);
        m_jreason.setPreferredSize(new java.awt.Dimension(200, 30));

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText(AppLocal.getIntString("label.paymenttotal")); // NOI18N
        jLabel3.setPreferredSize(new java.awt.Dimension(110, 30));

        jTotal.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTotal.setPreferredSize(new java.awt.Dimension(200, 30));

        m_jNotes.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        m_jNotes.setPreferredSize(new java.awt.Dimension(132, 100));

        m_jButtonPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/printer24_off.png"))); // NOI18N
        m_jButtonPrint.setSelected(true);
        m_jButtonPrint.setToolTipText("Print Receipt");
        m_jButtonPrint.setFocusPainted(false);
        m_jButtonPrint.setFocusable(false);
        m_jButtonPrint.setMargin(new java.awt.Insets(8, 16, 8, 16));
        m_jButtonPrint.setPreferredSize(new java.awt.Dimension(80, 45));
        m_jButtonPrint.setRequestFocusEnabled(false);
        m_jButtonPrint.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/printer24.png"))); // NOI18N
        m_jButtonPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jButtonPrintActionPerformed(evt);
            }
        });

        jlblPrinterStatus.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jlblPrinterStatus.setText("IMPRESORA ON");

        chb80.setEnabled(false);
        chb80.setSelected(true);
        chb80.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chb80ActionPerformed(evt);
            }
        });

        chb76.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chb76ActionPerformed(evt);
            }
        });

        chb58.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chb58ActionPerformed(evt);
            }
        });

        txt80.setBackground(new java.awt.Color(0, 255, 0));
        txt80.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt80.setText("Impresora 80 mm");
        txt80.setOpaque(true);

        txt76.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt76.setText("Impresora 76 mm");
        txt76.setOpaque(true);

        txt58.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt58.setText("Impresora 58 mm");
        txt58.setOpaque(true);

        txtSecuencia.setEditable(false);
        txtSecuencia.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtSecuencia.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Secuencia");
        jLabel6.setPreferredSize(new java.awt.Dimension(110, 30));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(m_jButtonPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(m_jNotes, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                                .addComponent(m_jreason, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(chb58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txt58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(chb76, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txt76, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(chb80, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txt80, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(jlblPrinterStatus, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSecuencia, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtSecuencia, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_jreason, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(m_jNotes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(m_jButtonPrint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlblPrinterStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chb80, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt80, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chb76, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt76, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chb58, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt58, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(78, Short.MAX_VALUE))
        );

        add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.BorderLayout());

        m_jKeys.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                m_jKeysPropertyChange(evt);
            }
        });
        jPanel2.add(m_jKeys, java.awt.BorderLayout.NORTH);

        add(jPanel2, java.awt.BorderLayout.LINE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void m_jKeysPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_m_jKeysPropertyChange

    }//GEN-LAST:event_m_jKeysPropertyChange

    private void m_jButtonPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jButtonPrintActionPerformed
        if (!m_jButtonPrint.isSelected()) {
            jlblPrinterStatus.setText("IMPRESORA OFF");
        } else {
            jlblPrinterStatus.setText("IMPRESORA ON");
        }
    }//GEN-LAST:event_m_jButtonPrintActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        m_jButtonPrint.setSelected(true);             
        jlblPrinterStatus.setText("IMPRESORA ON");
    }//GEN-LAST:event_formComponentShown

    private void jPanel3ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel3ComponentShown
        // TODO add your handling code here:
        m_jButtonPrint.setSelected(true);
        jlblPrinterStatus.setText("IMPRESORA ON");
    }//GEN-LAST:event_jPanel3ComponentShown

    private void chb80ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chb80ActionPerformed
        // TODO add your handling code here:
        if(chb80.isSelected()){
            chb80.setEnabled(false);
            chb76.setEnabled(true);
            chb58.setEnabled(true);
            txt80.setBackground(Color.green);
            txt76.setBackground(null);
            txt58.setBackground(null);
            chb76.setSelected(false);
            chb58.setSelected(false);
        }
    }//GEN-LAST:event_chb80ActionPerformed

    private void chb76ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chb76ActionPerformed
        // TODO add your handling code here:
        if(chb76.isSelected()){
            chb76.setEnabled(false);
            chb80.setEnabled(true);
            chb58.setEnabled(true);
            txt76.setBackground(Color.green);
            txt80.setBackground(null);
            txt58.setBackground(null);
            chb80.setSelected(false);
            chb58.setSelected(false);
        }
    }//GEN-LAST:event_chb76ActionPerformed

    private void chb58ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chb58ActionPerformed
        // TODO add your handling code here:
        if(chb58.isSelected()){
            chb58.setEnabled(false);
            chb80.setEnabled(true);
            chb76.setEnabled(true);
            txt58.setBackground(Color.green);
            txt80.setBackground(null);
            txt76.setBackground(null);
            chb80.setSelected(false);
            chb76.setSelected(false);
        }
    }//GEN-LAST:event_chb58ActionPerformed

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // TODO add your handling code here:
        m_jButtonPrint.setSelected(true);
        jlblPrinterStatus.setText("IMPRESORA ON");
    }//GEN-LAST:event_formFocusGained
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private com.alee.extended.button.WebSwitch chb58;
    private com.alee.extended.button.WebSwitch chb76;
    private com.alee.extended.button.WebSwitch chb80;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private com.openbravo.editor.JEditorCurrency jTotal;
    private javax.swing.JLabel jlblPrinterStatus;
    private javax.swing.JToggleButton m_jButtonPrint;
    private com.openbravo.editor.JEditorKeys m_jKeys;
    private com.openbravo.editor.JEditorString m_jNotes;
    private javax.swing.JComboBox m_jreason;
    private javax.swing.JLabel txt58;
    private javax.swing.JLabel txt76;
    private javax.swing.JLabel txt80;
    private javax.swing.JTextField txtSecuencia;
    // End of variables declaration//GEN-END:variables
    
}
