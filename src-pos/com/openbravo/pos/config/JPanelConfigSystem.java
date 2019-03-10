//    uniCenta oPOS  - Touch Friendly Point Of Sale
//    Copyright (c) 2009-2016 uniCenta
//    http://www.unicenta.com
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

package com.openbravo.pos.config;

import com.openbravo.data.user.DirtyManager;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JOptionPane;

import java.awt.Robot;
import java.awt.AWTException;
import java.awt.event.InputEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
        
        

/**
 *
 * @author JG uniCenta
 */
public class JPanelConfigSystem extends javax.swing.JPanel implements PanelConfig {
    
    private DirtyManager dirty = new DirtyManager();
    
    /** Creates new form JPanelConfigDatabase */
    public JPanelConfigSystem() {
        
        initComponents();
        
        jTextAutoLogoffTime.getDocument().addDocumentListener(dirty);
        jchkInstance.addActionListener(dirty);
        jchkTextOverlay.addActionListener(dirty);
        jchkAutoLogoff.addActionListener(dirty);
        jchkAutoLogoffToTables.addActionListener(dirty);
        jchkShowCustomerDetails.addActionListener(dirty);
        jchkShowWaiterDetails.addActionListener(dirty);
        jCustomerColour1.addActionListener(dirty);
        jWaiterColour1.addActionListener(dirty);
        jTableNameColour1.addActionListener(dirty);
        jTaxIncluded.addActionListener(dirty);
        jCheckPrice00.addActionListener(dirty);          
        jMoveAMountBoxToTop.addActionListener(dirty);
        jCloseCashbtn.addActionListener(dirty);

        jchkautoRefreshTableMap.addActionListener(dirty);
        jTxtautoRefreshTimer.getDocument().addDocumentListener(dirty);       

        jchkSCOnOff.addActionListener(dirty);
        jchkSCRestaurant.addActionListener(dirty);        
        jTextSCRate.getDocument().addDocumentListener(dirty);   
        
        jchkPriceUpdate.addActionListener(dirty);
        jchkBarcodetype.addActionListener(dirty);
        jchkTransBtn.addActionListener(dirty);  
        jConsolidate.addActionListener(dirty);
        BigTotal.addActionListener(dirty);
        chbHideStock.addActionListener(dirty);
        chbTeclado.addActionListener(dirty); 
        chbImagen.addActionListener(dirty); 
        jchkAtajos.addActionListener(dirty);    
        chbReadCode.addActionListener(dirty);
        chkInBuy.addActionListener(dirty);
        chkOutBuy.addActionListener(dirty);  
        chkInDevolution.addActionListener(dirty);
        chkOutDevolution.addActionListener(dirty);
        chkInTransfer.addActionListener(dirty);
        chkOutTransfer.addActionListener(dirty);
        chkSubstract.addActionListener(dirty);
        chkOutBreak.addActionListener(dirty);
        chkGivenFree.addActionListener(dirty);
        chkGivenSample.addActionListener(dirty);
        chkUsedItem.addActionListener(dirty);        
        chkTransfer.addActionListener(dirty);   
        chbMinimo.addActionListener(dirty); 
        chbCustomer.addActionListener(dirty);  
        optEliminar.addActionListener(dirty);        
        txtClave.getDocument().addDocumentListener(dirty);
        chbCashin.addActionListener(dirty); 
        chbCashout.addActionListener(dirty); 
        optGuardarSale.addActionListener(dirty); 
    }
        
    /**
     *
     * @return
     */
    @Override
    public boolean hasChanged() {
        return dirty.isDirty();
    }
    
    /**
     *
     * @return
     */
    @Override
    public Component getConfigComponent() {
        return this;
    }
   
    /**
     *
     * @param config
     */
    @Override
    public void loadProperties(AppConfig config) {
     
        String timerCheck =(config.getProperty("till.autotimer"));
        if (timerCheck == null){
            config.setProperty("till.autotimer","100");
        }                
        jTextAutoLogoffTime.setText(config.getProperty("till.autotimer"));

        String autoRefreshtimerCheck =(config.getProperty("till.autoRefreshTimer"));
        if (autoRefreshtimerCheck == null){
            config.setProperty("till.autoRefreshTimer","5");
        }                
        jTxtautoRefreshTimer.setText(config.getProperty("till.autoRefreshTimer"));      
      
        jchkShowCustomerDetails.setSelected(Boolean.parseBoolean(config.getProperty("table.showcustomerdetails")));
        jchkShowWaiterDetails.setSelected(Boolean.parseBoolean(config.getProperty("table.showwaiterdetails")));               
        jchkAutoLogoff.setSelected(Boolean.parseBoolean(config.getProperty("till.autoLogoff")));    
        jchkAutoLogoffToTables.setSelected(Boolean.parseBoolean(config.getProperty("till.autoLogoffrestaurant")));  
        
        jTaxIncluded.setSelected(Boolean.parseBoolean(config.getProperty("till.taxincluded")));
        jchkInstance.setSelected(Boolean.parseBoolean(config.getProperty("machine.uniqueinstance")));
        jchkTextOverlay.setSelected(Boolean.parseBoolean(config.getProperty("payments.textoverlay"))); 
        jConsolidate.setSelected(Boolean.valueOf(config.getProperty("display.consolidated")));
        jchkPriceUpdate.setSelected(AppConfig.getInstance().getBoolean("db.prodpriceupdate"));        
        jCheckPrice00.setSelected(Boolean.parseBoolean(config.getProperty("till.pricewith00")));        
        jMoveAMountBoxToTop.setSelected(Boolean.parseBoolean(config.getProperty("till.amountattop")));       
        jCloseCashbtn.setSelected(Boolean.parseBoolean(config.getProperty("screen.600800")));
        jchkBarcodetype.setSelected(Boolean.parseBoolean(config.getProperty("machine.barcodetype")));
        BigTotal.setSelected(Boolean.parseBoolean(config.getProperty("till.bigtotal")));
        chbHideStock.setSelected(Boolean.parseBoolean(config.getProperty("till.hidestock")));
        chbTeclado.setSelected(Boolean.parseBoolean(config.getProperty("till.teclado")));
        chbImagen.setSelected(Boolean.parseBoolean(config.getProperty("till.imagen")));
        jchkAtajos.setSelected(Boolean.parseBoolean(config.getProperty("till.atajos")));
        
        jchkautoRefreshTableMap.setSelected(Boolean.parseBoolean(config.getProperty("till.autoRefreshTableMap")));  
        chbReadCode.setSelected(Boolean.parseBoolean(config.getProperty("till.pesobalanza")));  
        
        chkInBuy.setSelected(Boolean.parseBoolean(config.getProperty("till.entradacompra"))); 
        chkOutBuy.setSelected(Boolean.parseBoolean(config.getProperty("till.salidacompra")));   
        chkInDevolution.setSelected(Boolean.parseBoolean(config.getProperty("till.entradadevolucion"))); 
        chkOutDevolution.setSelected(Boolean.parseBoolean(config.getProperty("till.salidadevolucion"))); 
        chkInTransfer.setSelected(Boolean.parseBoolean(config.getProperty("till.entradatraspaso"))); 
        chkOutTransfer.setSelected(Boolean.parseBoolean(config.getProperty("till.salidatraspaso"))); 
        chkSubstract.setSelected(Boolean.parseBoolean(config.getProperty("till.sustraer"))); 
        chkOutBreak.setSelected(Boolean.parseBoolean(config.getProperty("till.salidarotura"))); 
        chkGivenFree.setSelected(Boolean.parseBoolean(config.getProperty("till.entregadogratis"))); 
        chkGivenSample.setSelected(Boolean.parseBoolean(config.getProperty("till.muestraentregada"))); 
        chkUsedItem.setSelected(Boolean.parseBoolean(config.getProperty("till.articuloparauso")));         
        chkTransfer.setSelected(Boolean.parseBoolean(config.getProperty("till.traspaso")));  
        chbMinimo.setSelected(Boolean.parseBoolean(config.getProperty("till.minimo")));
        chbCustomer.setSelected(Boolean.parseBoolean(config.getProperty("till.customer")));
        optEliminar.setSelected(Boolean.parseBoolean(config.getProperty("till.clavedelete")));
        txtClave.setText(config.getProperty("till.claveset"));
        chbCashin.setSelected(Boolean.parseBoolean(config.getProperty("till.cashin")));
        chbCashout.setSelected(Boolean.parseBoolean(config.getProperty("till.cashout")));
        optGuardarSale.setSelected(Boolean.parseBoolean(config.getProperty("till.savesale")));
        
/** Added: JG 23 July 13 */      
        String SCCheck =(config.getProperty("till.SCRate"));
        if (SCCheck == null){
            config.setProperty("till.SCRate","0");
        }                
        jTextSCRate.setText(config.getProperty("till.SCRate"));
        jchkSCOnOff.setSelected(Boolean.parseBoolean(config.getProperty("till.SCOnOff")));    
        jchkSCRestaurant.setSelected(Boolean.parseBoolean(config.getProperty("till.SCRestaurant")));
        
        if (jchkSCOnOff.isSelected()){
                jchkSCRestaurant.setVisible(true);
                jLabelSCRate.setVisible(true);
                jTextSCRate.setVisible(true);
                jLabelSCRatePerCent.setVisible(true);
        }else{    
                jchkSCRestaurant.setVisible(false);
                jLabelSCRate.setVisible(false);
                jTextSCRate.setVisible(false);
                jLabelSCRatePerCent.setVisible(false);
        }                       
        
        if (jchkAutoLogoff.isSelected()){
                jchkAutoLogoffToTables.setVisible(true);
                jLabelInactiveTime.setVisible(true);
                jLabelTimedMessage.setVisible(true);
                jTextAutoLogoffTime.setVisible(true);
        }else{    
                jchkAutoLogoffToTables.setVisible(false);
                jLabelInactiveTime.setVisible(false);
                jLabelTimedMessage.setVisible(false);
                jTextAutoLogoffTime.setVisible(false);
        }
        
        if (jchkautoRefreshTableMap.isSelected()){
                jLblautoRefresh.setVisible(true);
                jLabelInactiveTime1.setVisible(true);
                jTxtautoRefreshTimer.setVisible(true);
        }else{    
                jLblautoRefresh.setVisible(false);
                jLabelInactiveTime1.setVisible(false);
                jTxtautoRefreshTimer.setVisible(false);
        }        

        if (config.getProperty("table.customercolour")==null){
            jCustomerColour1.setText("blue");
        }else{
            jCustomerColour1.setText(config.getProperty("table.customercolour"));
        }
        if (config.getProperty("table.waitercolour")==null){
            jWaiterColour1.setText("red");
        }else{
            jWaiterColour1.setText(config.getProperty("table.waitercolour"));
        }
        if (config.getProperty("table.tablecolour")==null){                
            jTableNameColour1.setText("black");      
        }else{
            jTableNameColour1.setText((config.getProperty("table.tablecolour")));  
        }
        
        jchkTransBtn.setSelected(Boolean.parseBoolean(config.getProperty("table.transbtn")));  
                  
        dirty.setDirty(false);
    }
   
    /**
     *
     * @param config
     */
    @Override
    public void saveProperties(AppConfig config) {        
        config.setProperty("till.autotimer",jTextAutoLogoffTime.getText());
        config.setProperty("machine.uniqueinstance", Boolean.toString(jchkInstance.isSelected()));
        config.setProperty("table.showcustomerdetails", Boolean.toString(jchkShowCustomerDetails.isSelected()));
        config.setProperty("table.showwaiterdetails", Boolean.toString(jchkShowWaiterDetails.isSelected()));        
        config.setProperty("payments.textoverlay", Boolean.toString(jchkTextOverlay.isSelected()));         
        config.setProperty("till.autoLogoff", Boolean.toString(jchkAutoLogoff.isSelected()));                 
        config.setProperty("till.autoLogoffrestaurant", Boolean.toString(jchkAutoLogoffToTables.isSelected()));                        
        config.setProperty("table.customercolour",jCustomerColour1.getText());
        config.setProperty("table.waitercolour",jWaiterColour1.getText());
        config.setProperty("table.tablecolour",jTableNameColour1.getText());         
        config.setProperty("till.taxincluded",Boolean.toString(jTaxIncluded.isSelected()));                     
        config.setProperty("till.pricewith00",Boolean.toString(jCheckPrice00.isSelected()));                         
        config.setProperty("till.amountattop",Boolean.toString(jMoveAMountBoxToTop.isSelected()));         
        config.setProperty("screen.600800",Boolean.toString(jCloseCashbtn.isSelected())); 
        config.setProperty("till.autoRefreshTableMap", Boolean.toString(jchkautoRefreshTableMap.isSelected()));                 
        config.setProperty("till.autoRefreshTimer", jTxtautoRefreshTimer.getText());
        config.setProperty("till.SCOnOff",Boolean.toString(jchkSCOnOff.isSelected()));
        config.setProperty("till.SCRate",jTextSCRate.getText());
        config.setProperty("till.SCRestaurant",Boolean.toString(jchkSCRestaurant.isSelected()));
        config.setProperty("db.prodpriceupdate", Boolean.toString(jchkPriceUpdate.isSelected()));        
        config.setProperty("machine.barcodetype", Boolean.toString(jchkBarcodetype.isSelected()));        
        config.setProperty("table.transbtn", Boolean.toString(jchkTransBtn.isSelected()));
        config.setProperty("display.consolidated", Boolean.toString( jConsolidate.isSelected()));
        config.setProperty("till.bigtotal",Boolean.toString(BigTotal.isSelected())); 
        config.setProperty("till.hidestock", Boolean.toString(chbHideStock.isSelected())); 
        config.setProperty("till.teclado", Boolean.toString(chbTeclado.isSelected())); 
        config.setProperty("till.imagen", Boolean.toString(chbImagen.isSelected())); 
        config.setProperty("till.atajos", Boolean.toString(jchkAtajos.isSelected())); 
        config.setProperty("till.pesobalanza", Boolean.toString(chbReadCode.isSelected()));   
        
        config.setProperty("till.entradacompra", Boolean.toString(chkInBuy.isSelected()));   
        config.setProperty("till.salidacompra", Boolean.toString(chkOutBuy.isSelected()));   
        config.setProperty("till.entradadevolucion", Boolean.toString(chkInDevolution.isSelected()));   
        config.setProperty("till.salidadevolucion", Boolean.toString(chkOutDevolution.isSelected()));   
        config.setProperty("till.entradatraspaso", Boolean.toString(chkInTransfer.isSelected()));   
        config.setProperty("till.salidatraspaso", Boolean.toString(chkOutTransfer.isSelected()));   
        config.setProperty("till.sustraer", Boolean.toString(chkSubstract.isSelected()));   
        config.setProperty("till.salidarotura", Boolean.toString(chkOutBreak.isSelected()));   
        config.setProperty("till.entregadogratis", Boolean.toString(chkGivenFree.isSelected()));   
        config.setProperty("till.muestraentregada", Boolean.toString(chkGivenSample.isSelected()));   
        config.setProperty("till.articuloparauso", Boolean.toString(chkUsedItem.isSelected()));   
        config.setProperty("till.traspaso", Boolean.toString(chkTransfer.isSelected()));   
        config.setProperty("till.minimo", Boolean.toString(chbMinimo.isSelected()));
        config.setProperty("till.customer", Boolean.toString(chbCustomer.isSelected()));
        config.setProperty("till.clavedelete", Boolean.toString(optEliminar.isSelected()));
        config.setProperty("till.claveset", txtClave.getText()); 
        config.setProperty("till.cashin", Boolean.toString(chbCashin.isSelected()));
        config.setProperty("till.cashout", Boolean.toString(chbCashout.isSelected()));
        config.setProperty("till.savesale", Boolean.toString(optGuardarSale.isSelected()));        
        dirty.setDirty(false);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabelInactiveTime = new javax.swing.JLabel();
        jTextAutoLogoffTime = new javax.swing.JTextField();
        jLabelTimedMessage = new javax.swing.JLabel();
        jchkAutoLogoff = new javax.swing.JCheckBox();
        jchkAutoLogoffToTables = new javax.swing.JCheckBox();
        jchkShowCustomerDetails = new javax.swing.JCheckBox();
        jchkShowWaiterDetails = new javax.swing.JCheckBox();
        jLabelTableNameTextColour = new javax.swing.JLabel();
        jchkautoRefreshTableMap = new javax.swing.JCheckBox();
        jLabelInactiveTime1 = new javax.swing.JLabel();
        jTxtautoRefreshTimer = new javax.swing.JTextField();
        jLblautoRefresh = new javax.swing.JLabel();
        jchkSCOnOff = new javax.swing.JCheckBox();
        jLabelSCRate = new javax.swing.JLabel();
        jTextSCRate = new javax.swing.JTextField();
        jLabelSCRatePerCent = new javax.swing.JLabel();
        jchkSCRestaurant = new javax.swing.JCheckBox();
        jCustomerColour1 = new com.alee.extended.colorchooser.WebColorChooserField();
        jWaiterColour1 = new com.alee.extended.colorchooser.WebColorChooserField();
        jTableNameColour1 = new com.alee.extended.colorchooser.WebColorChooserField();
        jchkTransBtn = new javax.swing.JCheckBox();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jConsolidate = new com.alee.extended.button.WebSwitch();
        jLabel6 = new javax.swing.JLabel();
        jchkInstance = new com.alee.extended.button.WebSwitch();
        jLabel7 = new javax.swing.JLabel();
        jTaxIncluded = new com.alee.extended.button.WebSwitch();
        jLabel8 = new javax.swing.JLabel();
        jchkTextOverlay = new com.alee.extended.button.WebSwitch();
        jLabel9 = new javax.swing.JLabel();
        jchkPriceUpdate = new com.alee.extended.button.WebSwitch();
        jLabel10 = new javax.swing.JLabel();
        jchkAtajos = new com.alee.extended.button.WebSwitch();
        jLabel17 = new javax.swing.JLabel();
        chbCashout = new com.alee.extended.button.WebSwitch();
        chbCashin = new com.alee.extended.button.WebSwitch();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jCheckPrice00 = new com.alee.extended.button.WebSwitch();
        jLabel11 = new javax.swing.JLabel();
        jMoveAMountBoxToTop = new com.alee.extended.button.WebSwitch();
        jLabel12 = new javax.swing.JLabel();
        jCloseCashbtn = new com.alee.extended.button.WebSwitch();
        jLabel13 = new javax.swing.JLabel();
        jchkBarcodetype = new com.alee.extended.button.WebSwitch();
        jLabel14 = new javax.swing.JLabel();
        optEliminar = new com.alee.extended.button.WebSwitch();
        txtClave = new javax.swing.JPasswordField();
        jLabel33 = new javax.swing.JLabel();
        optGuardarSale = new com.alee.extended.button.WebSwitch();
        jLabel36 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        BigTotal = new com.alee.extended.button.WebSwitch();
        jLabel1 = new javax.swing.JLabel();
        chbHideStock = new com.alee.extended.button.WebSwitch();
        jLabel5 = new javax.swing.JLabel();
        chbImagen = new com.alee.extended.button.WebSwitch();
        chbTeclado = new com.alee.extended.button.WebSwitch();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        chbMinimo = new com.alee.extended.button.WebSwitch();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        chbCustomer = new com.alee.extended.button.WebSwitch();
        jLabel32 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        chbReadCode = new com.alee.extended.button.WebSwitch();
        jLabel19 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        chkInBuy = new com.alee.extended.button.WebSwitch();
        chkOutBuy = new com.alee.extended.button.WebSwitch();
        chkInDevolution = new com.alee.extended.button.WebSwitch();
        chkOutDevolution = new com.alee.extended.button.WebSwitch();
        chkInTransfer = new com.alee.extended.button.WebSwitch();
        chkOutTransfer = new com.alee.extended.button.WebSwitch();
        chkSubstract = new com.alee.extended.button.WebSwitch();
        chkOutBreak = new com.alee.extended.button.WebSwitch();
        chkGivenFree = new com.alee.extended.button.WebSwitch();
        chkGivenSample = new com.alee.extended.button.WebSwitch();
        chkUsedItem = new com.alee.extended.button.WebSwitch();
        chkTransfer = new com.alee.extended.button.WebSwitch();
        jLabel2 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        setPreferredSize(new java.awt.Dimension(700, 500));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pos_messages"); // NOI18N
        jLabel3.setText(bundle.getString("label.configOptionLogOff")); // NOI18N
        jLabel3.setPreferredSize(new java.awt.Dimension(100, 30));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setText(bundle.getString("label.configOptionRestaurant")); // NOI18N
        jLabel4.setPreferredSize(new java.awt.Dimension(250, 30));

        jLabelInactiveTime.setBackground(new java.awt.Color(255, 255, 255));
        jLabelInactiveTime.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabelInactiveTime.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabelInactiveTime.setText(bundle.getString("label.autolofftime")); // NOI18N
        jLabelInactiveTime.setMaximumSize(new java.awt.Dimension(0, 25));
        jLabelInactiveTime.setMinimumSize(new java.awt.Dimension(0, 0));
        jLabelInactiveTime.setPreferredSize(new java.awt.Dimension(100, 30));

        jTextAutoLogoffTime.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTextAutoLogoffTime.setText("0");
        jTextAutoLogoffTime.setMaximumSize(new java.awt.Dimension(0, 25));
        jTextAutoLogoffTime.setMinimumSize(new java.awt.Dimension(0, 0));
        jTextAutoLogoffTime.setPreferredSize(new java.awt.Dimension(0, 30));

        jLabelTimedMessage.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabelTimedMessage.setText(bundle.getString("label.autologoffzero")); // NOI18N
        jLabelTimedMessage.setMaximumSize(new java.awt.Dimension(0, 25));
        jLabelTimedMessage.setMinimumSize(new java.awt.Dimension(0, 0));
        jLabelTimedMessage.setPreferredSize(new java.awt.Dimension(200, 30));

        jchkAutoLogoff.setBackground(new java.awt.Color(255, 255, 255));
        jchkAutoLogoff.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jchkAutoLogoff.setText(bundle.getString("label.autologonoff")); // NOI18N
        jchkAutoLogoff.setMaximumSize(new java.awt.Dimension(0, 25));
        jchkAutoLogoff.setMinimumSize(new java.awt.Dimension(0, 0));
        jchkAutoLogoff.setPreferredSize(new java.awt.Dimension(200, 30));
        jchkAutoLogoff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkAutoLogoffActionPerformed(evt);
            }
        });

        jchkAutoLogoffToTables.setBackground(new java.awt.Color(255, 255, 255));
        jchkAutoLogoffToTables.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jchkAutoLogoffToTables.setText(bundle.getString("label.autoloffrestaurant")); // NOI18N
        jchkAutoLogoffToTables.setMaximumSize(new java.awt.Dimension(0, 25));
        jchkAutoLogoffToTables.setMinimumSize(new java.awt.Dimension(0, 0));
        jchkAutoLogoffToTables.setPreferredSize(new java.awt.Dimension(0, 30));
        jchkAutoLogoffToTables.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkAutoLogoffToTablesActionPerformed(evt);
            }
        });

        jchkShowCustomerDetails.setBackground(new java.awt.Color(255, 255, 255));
        jchkShowCustomerDetails.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jchkShowCustomerDetails.setText(bundle.getString("label.tableshowcustomerdetails")); // NOI18N
        jchkShowCustomerDetails.setMaximumSize(new java.awt.Dimension(0, 25));
        jchkShowCustomerDetails.setMinimumSize(new java.awt.Dimension(0, 0));
        jchkShowCustomerDetails.setPreferredSize(new java.awt.Dimension(350, 30));
        jchkShowCustomerDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkShowCustomerDetailsActionPerformed(evt);
            }
        });

        jchkShowWaiterDetails.setBackground(new java.awt.Color(255, 255, 255));
        jchkShowWaiterDetails.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jchkShowWaiterDetails.setText(bundle.getString("label.tableshowwaiterdetails")); // NOI18N
        jchkShowWaiterDetails.setMaximumSize(new java.awt.Dimension(0, 25));
        jchkShowWaiterDetails.setMinimumSize(new java.awt.Dimension(0, 0));
        jchkShowWaiterDetails.setPreferredSize(new java.awt.Dimension(350, 30));

        jLabelTableNameTextColour.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTableNameTextColour.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabelTableNameTextColour.setText(bundle.getString("label.textclourtablename")); // NOI18N
        jLabelTableNameTextColour.setMaximumSize(new java.awt.Dimension(0, 25));
        jLabelTableNameTextColour.setMinimumSize(new java.awt.Dimension(0, 0));
        jLabelTableNameTextColour.setPreferredSize(new java.awt.Dimension(350, 30));

        jchkautoRefreshTableMap.setBackground(new java.awt.Color(255, 255, 255));
        jchkautoRefreshTableMap.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jchkautoRefreshTableMap.setText(bundle.getString("label.autoRefreshTableMap")); // NOI18N
        jchkautoRefreshTableMap.setMaximumSize(new java.awt.Dimension(0, 25));
        jchkautoRefreshTableMap.setMinimumSize(new java.awt.Dimension(0, 0));
        jchkautoRefreshTableMap.setPreferredSize(new java.awt.Dimension(200, 30));
        jchkautoRefreshTableMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkautoRefreshTableMapActionPerformed(evt);
            }
        });

        jLabelInactiveTime1.setBackground(new java.awt.Color(255, 255, 255));
        jLabelInactiveTime1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabelInactiveTime1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabelInactiveTime1.setText(bundle.getString("label.autolofftime")); // NOI18N
        jLabelInactiveTime1.setMaximumSize(new java.awt.Dimension(0, 25));
        jLabelInactiveTime1.setMinimumSize(new java.awt.Dimension(0, 0));
        jLabelInactiveTime1.setPreferredSize(new java.awt.Dimension(100, 30));

        jTxtautoRefreshTimer.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTxtautoRefreshTimer.setText("0");
        jTxtautoRefreshTimer.setMaximumSize(new java.awt.Dimension(0, 25));
        jTxtautoRefreshTimer.setMinimumSize(new java.awt.Dimension(0, 0));
        jTxtautoRefreshTimer.setPreferredSize(new java.awt.Dimension(0, 30));

        jLblautoRefresh.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLblautoRefresh.setText(bundle.getString("label.autoRefreshTableMapTimer")); // NOI18N
        jLblautoRefresh.setMaximumSize(new java.awt.Dimension(0, 25));
        jLblautoRefresh.setMinimumSize(new java.awt.Dimension(0, 0));
        jLblautoRefresh.setPreferredSize(new java.awt.Dimension(200, 30));

        jchkSCOnOff.setBackground(new java.awt.Color(255, 255, 255));
        jchkSCOnOff.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jchkSCOnOff.setText(bundle.getString("label.SCOnOff")); // NOI18N
        jchkSCOnOff.setMaximumSize(new java.awt.Dimension(0, 25));
        jchkSCOnOff.setMinimumSize(new java.awt.Dimension(0, 0));
        jchkSCOnOff.setPreferredSize(new java.awt.Dimension(0, 25));
        jchkSCOnOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkSCOnOffActionPerformed(evt);
            }
        });

        jLabelSCRate.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabelSCRate.setText(bundle.getString("label.SCRate")); // NOI18N
        jLabelSCRate.setMaximumSize(new java.awt.Dimension(0, 25));
        jLabelSCRate.setMinimumSize(new java.awt.Dimension(0, 0));
        jLabelSCRate.setPreferredSize(new java.awt.Dimension(190, 30));

        jTextSCRate.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTextSCRate.setText("0");
        jTextSCRate.setMaximumSize(new java.awt.Dimension(0, 25));
        jTextSCRate.setMinimumSize(new java.awt.Dimension(0, 0));
        jTextSCRate.setPreferredSize(new java.awt.Dimension(0, 30));
        jTextSCRate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextSCRateActionPerformed(evt);
            }
        });

        jLabelSCRatePerCent.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabelSCRatePerCent.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelSCRatePerCent.setText(bundle.getString("label.SCZero")); // NOI18N
        jLabelSCRatePerCent.setMaximumSize(new java.awt.Dimension(0, 25));
        jLabelSCRatePerCent.setMinimumSize(new java.awt.Dimension(0, 0));
        jLabelSCRatePerCent.setPreferredSize(new java.awt.Dimension(0, 30));

        jchkSCRestaurant.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jchkSCRestaurant.setText(bundle.getString("label.SCRestaurant")); // NOI18N
        jchkSCRestaurant.setMaximumSize(new java.awt.Dimension(0, 25));
        jchkSCRestaurant.setMinimumSize(new java.awt.Dimension(0, 0));
        jchkSCRestaurant.setPreferredSize(new java.awt.Dimension(0, 25));

        jCustomerColour1.setForeground(new java.awt.Color(0, 153, 255));
        jCustomerColour1.setToolTipText(bundle.getString("tooltip.prodhtmldisplayColourChooser")); // NOI18N
        jCustomerColour1.setColorDisplayType(com.alee.extended.colorchooser.ColorDisplayType.hex);
        jCustomerColour1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jCustomerColour1.setPreferredSize(new java.awt.Dimension(200, 30));

        jWaiterColour1.setForeground(new java.awt.Color(0, 153, 255));
        jWaiterColour1.setToolTipText(bundle.getString("tooltip.prodhtmldisplayColourChooser")); // NOI18N
        jWaiterColour1.setColorDisplayType(com.alee.extended.colorchooser.ColorDisplayType.hex);
        jWaiterColour1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jWaiterColour1.setPreferredSize(new java.awt.Dimension(200, 30));

        jTableNameColour1.setForeground(new java.awt.Color(0, 153, 255));
        jTableNameColour1.setToolTipText(bundle.getString("tooltip.prodhtmldisplayColourChooser")); // NOI18N
        jTableNameColour1.setColorDisplayType(com.alee.extended.colorchooser.ColorDisplayType.hex);
        jTableNameColour1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTableNameColour1.setPreferredSize(new java.awt.Dimension(200, 30));

        jchkTransBtn.setBackground(new java.awt.Color(255, 255, 255));
        jchkTransBtn.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jchkTransBtn.setText(bundle.getString("label.tabletransbutton")); // NOI18N
        jchkTransBtn.setMaximumSize(new java.awt.Dimension(0, 25));
        jchkTransBtn.setMinimumSize(new java.awt.Dimension(0, 0));
        jchkTransBtn.setPreferredSize(new java.awt.Dimension(350, 30));
        jchkTransBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkTransBtnActionPerformed(evt);
            }
        });

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });
        jTabbedPane1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTabbedPane1FocusGained(evt);
            }
        });
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });
        jTabbedPane1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jTabbedPane1ComponentShown(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPanel2FocusGained(evt);
            }
        });
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });
        jPanel2.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel2ComponentShown(evt);
            }
        });

        jConsolidate.setOpaque(true);
        jConsolidate.setPreferredSize(new java.awt.Dimension(80, 30));
        jConsolidate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jConsolidateActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Consolidar Pantalla de Ventas");

        java.util.ResourceBundle bundle1 = java.util.ResourceBundle.getBundle("pos_messages_es"); // NOI18N
        jchkInstance.setToolTipText(bundle1.getString("tooltip.instance")); // NOI18N
        jchkInstance.setOpaque(true);
        jchkInstance.setPreferredSize(new java.awt.Dimension(80, 30));
        jchkInstance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkInstanceActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("Habilitar Unica Instancia");

        jTaxIncluded.setOpaque(true);
        jTaxIncluded.setPreferredSize(new java.awt.Dimension(80, 30));
        jTaxIncluded.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTaxIncludedActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText("Impuestos Incluidos al Inicio");

        jchkTextOverlay.setOpaque(true);
        jchkTextOverlay.setPreferredSize(new java.awt.Dimension(80, 30));
        jchkTextOverlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkTextOverlayActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Mostrar superposicion texto pagos");

        jchkPriceUpdate.setOpaque(true);
        jchkPriceUpdate.setPreferredSize(new java.awt.Dimension(80, 30));
        jchkPriceUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkPriceUpdateActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setText("Permitir Actualizaciones de Precios");

        jchkAtajos.setOpaque(true);
        jchkAtajos.setPreferredSize(new java.awt.Dimension(80, 30));
        jchkAtajos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkAtajosActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel17.setText("No Habilitar Atajos de Teclado");

        chbCashout.setPreferredSize(new java.awt.Dimension(80, 30));

        chbCashin.setPreferredSize(new java.awt.Dimension(80, 30));

        jLabel34.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel34.setText("Habilitar entrada de caja");

        jLabel35.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel35.setText("Habilitar salida de caja");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jchkTextOverlay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jchkInstance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jTaxIncluded, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(chbCashin, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(chbCashout, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jConsolidate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jchkPriceUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jchkAtajos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(752, 752, 752)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jConsolidate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTaxIncluded, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jchkInstance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jchkPriceUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jchkTextOverlay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jchkAtajos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(chbCashin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chbCashout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab(bundle.getString("label.configOptionStartup"), jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });

        jCheckPrice00.setOpaque(true);
        jCheckPrice00.setPreferredSize(new java.awt.Dimension(80, 30));
        jCheckPrice00.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckPrice00ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setText("Usar boton 00 en el teclado");

        jMoveAMountBoxToTop.setOpaque(true);
        jMoveAMountBoxToTop.setPreferredSize(new java.awt.Dimension(80, 30));
        jMoveAMountBoxToTop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMoveAMountBoxToTopActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("Campo de entrada por encima del teclado");

        jCloseCashbtn.setOpaque(true);
        jCloseCashbtn.setPreferredSize(new java.awt.Dimension(80, 30));
        jCloseCashbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCloseCashbtnActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setText("Colocar Arriba botones cerrar caja");

        jchkBarcodetype.setToolTipText(bundle1.getString("tooltip.barcodetype")); // NOI18N
        jchkBarcodetype.setOpaque(true);
        jchkBarcodetype.setPreferredSize(new java.awt.Dimension(80, 30));
        jchkBarcodetype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkBarcodetypeActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel14.setText("Cambiar a codigo de barras UPC");

        optEliminar.setOpaque(true);
        optEliminar.setPreferredSize(new java.awt.Dimension(80, 30));
        optEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optEliminarActionPerformed(evt);
            }
        });

        txtClave.setEditable(false);
        txtClave.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        jLabel33.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel33.setText("Clave para eliminar linea");

        optGuardarSale.setOpaque(true);
        optGuardarSale.setPreferredSize(new java.awt.Dimension(80, 30));
        optGuardarSale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optGuardarSaleActionPerformed(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel36.setText("Clave para guardar venta");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jCheckPrice00, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jMoveAMountBoxToTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(optEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jchkBarcodetype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtClave, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jCloseCashbtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 40, Short.MAX_VALUE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(optGuardarSale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jCheckPrice00, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jchkBarcodetype, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jMoveAMountBoxToTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jCloseCashbtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(optEliminar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtClave))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(optGuardarSale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
        );

        jTabbedPane1.addTab(bundle.getString("label.configOptionKeypad"), jPanel3);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPanel1FocusGained(evt);
            }
        });
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });
        jPanel1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel1ComponentShown(evt);
            }
        });
        jPanel1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jPanel1PropertyChange(evt);
            }
        });

        BigTotal.setOpaque(true);
        BigTotal.setPreferredSize(new java.awt.Dimension(80, 30));
        BigTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BigTotalActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("Mostrar Display Grande en Panel de Ventas");

        chbHideStock.setOpaque(true);
        chbHideStock.setPreferredSize(new java.awt.Dimension(80, 30));
        chbHideStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbHideStockActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Mostrar botón de Inventario");

        chbImagen.setOpaque(true);
        chbImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbImagenActionPerformed(evt);
            }
        });

        chbTeclado.setOpaque(true);
        chbTeclado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbTecladoActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel15.setText("Mostrar cuadro de Imagen");

        jLabel16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel16.setText("Teclado numérico a la Izquierda");

        chbMinimo.setOpaque(true);
        chbMinimo.setPreferredSize(new java.awt.Dimension(80, 30));
        chbMinimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbMinimoActionPerformed(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel30.setText("Mostrar botón de Inventario");

        jLabel31.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel31.setText("Mostrar mensaje si Inventario está en Mínimos");

        chbCustomer.setOpaque(true);
        chbCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbCustomerActionPerformed(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel32.setText("Habilitar captura de clientes con pistola");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(chbMinimo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BigTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chbHideStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(chbCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 651, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(chbTeclado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(chbImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(BigTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chbTeclado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(chbImagen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(chbHideStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chbMinimo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chbCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(bundle.getString("label.apariencia"), jPanel1);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        chbReadCode.setOpaque(true);
        chbReadCode.setPreferredSize(new java.awt.Dimension(80, 30));
        chbReadCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbReadCodeActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel19.setText("Obtener peso de balanza usando pistola lectora de código barras");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chbReadCode, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1020, 1020, 1020))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chbReadCode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(116, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Balanza", jPanel4);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        chkInBuy.setOpaque(true);
        chkInBuy.setPreferredSize(new java.awt.Dimension(80, 30));
        chkInBuy.setSelected(true);
        chkInBuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkInBuyActionPerformed(evt);
            }
        });

        chkOutBuy.setOpaque(true);
        chkOutBuy.setPreferredSize(new java.awt.Dimension(80, 30));
        chkOutBuy.setSelected(true);
        chkOutBuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkOutBuyActionPerformed(evt);
            }
        });

        chkInDevolution.setOpaque(true);
        chkInDevolution.setPreferredSize(new java.awt.Dimension(80, 30));
        chkInDevolution.setSelected(true);
        chkInDevolution.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkInDevolutionActionPerformed(evt);
            }
        });

        chkOutDevolution.setOpaque(true);
        chkOutDevolution.setPreferredSize(new java.awt.Dimension(80, 30));
        chkOutDevolution.setSelected(true);
        chkOutDevolution.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkOutDevolutionActionPerformed(evt);
            }
        });

        chkInTransfer.setOpaque(true);
        chkInTransfer.setPreferredSize(new java.awt.Dimension(80, 30));
        chkInTransfer.setSelected(true);
        chkInTransfer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkInTransferActionPerformed(evt);
            }
        });

        chkOutTransfer.setOpaque(true);
        chkOutTransfer.setPreferredSize(new java.awt.Dimension(80, 30));
        chkOutTransfer.setSelected(true);
        chkOutTransfer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkOutTransferActionPerformed(evt);
            }
        });

        chkSubstract.setOpaque(true);
        chkSubstract.setPreferredSize(new java.awt.Dimension(80, 30));
        chkSubstract.setSelected(true);
        chkSubstract.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSubstractActionPerformed(evt);
            }
        });

        chkOutBreak.setOpaque(true);
        chkOutBreak.setPreferredSize(new java.awt.Dimension(80, 30));
        chkOutBreak.setSelected(true);
        chkOutBreak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkOutBreakActionPerformed(evt);
            }
        });

        chkGivenFree.setOpaque(true);
        chkGivenFree.setPreferredSize(new java.awt.Dimension(80, 30));
        chkGivenFree.setSelected(true);
        chkGivenFree.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkGivenFreeActionPerformed(evt);
            }
        });

        chkGivenSample.setOpaque(true);
        chkGivenSample.setPreferredSize(new java.awt.Dimension(80, 30));
        chkGivenSample.setSelected(true);
        chkGivenSample.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkGivenSampleActionPerformed(evt);
            }
        });

        chkUsedItem.setOpaque(true);
        chkUsedItem.setPreferredSize(new java.awt.Dimension(80, 30));
        chkUsedItem.setSelected(true);
        chkUsedItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkUsedItemActionPerformed(evt);
            }
        });

        chkTransfer.setOpaque(true);
        chkTransfer.setPreferredSize(new java.awt.Dimension(80, 30));
        chkTransfer.setSelected(true);
        chkTransfer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkTransferActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Entrada (Compra)");

        jLabel18.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel18.setText("Salida (Venta)");

        jLabel20.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel20.setText("Entrada (Devolución)");

        jLabel21.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel21.setText("Salida (Devolución)");

        jLabel22.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel22.setText("Entrada (Traspaso)");

        jLabel23.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel23.setText("Salida (Traspaso)");

        jLabel24.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel24.setText("Sustraer");

        jLabel25.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel25.setText("Salida (Rotura)");

        jLabel26.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel26.setText("Entregado Gratis");

        jLabel27.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel27.setText("Muestra Entregada");

        jLabel28.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel28.setText("Artículo para Uso");

        jLabel29.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel29.setText("Traspaso");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(chkOutDevolution, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(chkInDevolution, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(chkOutBuy, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(chkInBuy, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(chkOutBreak, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(chkSubstract, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(chkOutTransfer, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(chkInTransfer, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(chkTransfer, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(chkUsedItem, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(chkGivenSample, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(chkGivenFree, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(chkInTransfer, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkOutTransfer, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkSubstract, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                        .addGap(6, 6, 6)
                        .addComponent(chkOutBreak, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkInBuy, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(chkOutBuy, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(5, 5, 5)))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(chkInDevolution, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                                .addGap(6, 6, 6))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkOutDevolution, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(chkGivenFree, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkGivenSample, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkUsedItem, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                        .addGap(6, 6, 6)
                        .addComponent(chkTransfer, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jTabbedPane1.addTab("Inventario", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(jchkSCOnOff, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jchkautoRefreshTableMap, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabelInactiveTime1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jchkShowWaiterDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabelTableNameTextColour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jchkShowCustomerDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jchkTransBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(50, 50, 50))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabelSCRate, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(jLabelSCRatePerCent, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTextSCRate, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jchkSCRestaurant, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTxtautoRefreshTimer, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(138, 138, 138)
                                        .addComponent(jLblautoRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jTableNameColour1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jWaiterColour1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCustomerColour1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jchkAutoLogoffToTables, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jchkAutoLogoff, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelInactiveTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(100, 100, 100)
                                .addComponent(jTextAutoLogoffTime, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelTimedMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 654, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 832, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jchkAutoLogoff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelInactiveTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextAutoLogoffTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTimedMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jchkAutoLogoffToTables, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jchkautoRefreshTableMap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelInactiveTime1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtautoRefreshTimer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblautoRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jchkSCOnOff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelSCRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelSCRatePerCent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jchkSCRestaurant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextSCRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jchkShowCustomerDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCustomerColour1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jchkShowWaiterDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jWaiterColour1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTableNameTextColour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTableNameColour1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jchkTransBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jCustomerColour1.getAccessibleContext().setAccessibleName("colourChooser");
        jWaiterColour1.getAccessibleContext().setAccessibleName("colourChooser1");
        jTableNameColour1.getAccessibleContext().setAccessibleName("colourChooser2");
    }// </editor-fold>//GEN-END:initComponents

    private void jchkAutoLogoffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkAutoLogoffActionPerformed
        if (jchkAutoLogoff.isSelected()){
                jchkAutoLogoffToTables.setVisible(true);
                jLabelInactiveTime.setVisible(true);
                jLabelTimedMessage.setVisible(true);
                jTextAutoLogoffTime.setVisible(true);
        }else{    
                jchkAutoLogoffToTables.setVisible(false);
                jLabelInactiveTime.setVisible(false);
                jLabelTimedMessage.setVisible(false);
                jTextAutoLogoffTime.setVisible(false);
        }
    }//GEN-LAST:event_jchkAutoLogoffActionPerformed

    private void jchkAutoLogoffToTablesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkAutoLogoffToTablesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkAutoLogoffToTablesActionPerformed

    private void jchkShowCustomerDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkShowCustomerDetailsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkShowCustomerDetailsActionPerformed

    private void jchkautoRefreshTableMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkautoRefreshTableMapActionPerformed
        if (jchkautoRefreshTableMap.isSelected()){
                jLblautoRefresh.setVisible(true);
                jLabelInactiveTime1.setVisible(true);
                jTxtautoRefreshTimer.setVisible(true);
        }else{    
                jLblautoRefresh.setVisible(false);
                jLabelInactiveTime1.setVisible(false);
                jTxtautoRefreshTimer.setVisible(false);
        }  
    }//GEN-LAST:event_jchkautoRefreshTableMapActionPerformed

    private void jchkSCOnOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkSCOnOffActionPerformed
        if (jchkSCOnOff.isSelected()){
            jchkSCRestaurant.setVisible(true);
            jLabelSCRate.setVisible(true);
            jTextSCRate.setVisible(true);
            jLabelSCRatePerCent.setVisible(true);
        }else{
            jchkSCRestaurant.setVisible(false);
            jLabelSCRate.setVisible(false);
            jTextSCRate.setVisible(false);
            jLabelSCRatePerCent.setVisible(false);
        }
    }//GEN-LAST:event_jchkSCOnOffActionPerformed

    private void jTextSCRateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextSCRateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextSCRateActionPerformed

    private void jchkTransBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkTransBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkTransBtnActionPerformed

    private void BigTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BigTotalActionPerformed
        // TODO add your handling code here:
        if (BigTotal.isSelected()) {           
            jLabel1.setText(AppLocal.getIntString("label.bigtotalyes"));              
        } else {        
            jLabel1.setText(AppLocal.getIntString("label.bigtotalno"));             
        }
    }//GEN-LAST:event_BigTotalActionPerformed

    private void chbHideStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbHideStockActionPerformed
        // TODO add your handling code here:
        if (chbHideStock.isSelected()) {             
            jLabel5.setText(AppLocal.getIntString("label.showstockyes"));              
        } else {      
            jLabel5.setText(AppLocal.getIntString("label.showstockno"));             
        }
    }//GEN-LAST:event_chbHideStockActionPerformed

    private void jPanel1ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel1ComponentShown
        // TODO add your handling code here
    }//GEN-LAST:event_jPanel1ComponentShown

    private void jPanel2ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel2ComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel2ComponentShown

    private void jConsolidateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jConsolidateActionPerformed
        // TODO add your handling code here:
        if (jConsolidate.isSelected()) {         
            jLabel6.setText(AppLocal.getIntString("Label.ConsolidatedScreenyes"));              
        } else {         
            jLabel6.setText(AppLocal.getIntString("Label.ConsolidatedScreenno"));             
        }
    }//GEN-LAST:event_jConsolidateActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentShown

    private void jTabbedPane1ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jTabbedPane1ComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_jTabbedPane1ComponentShown

    private void jTabbedPane1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTabbedPane1FocusGained
        // TODO add your handling code he
    }//GEN-LAST:event_jTabbedPane1FocusGained

    private void jPanel2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPanel2FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel2FocusGained

    private void jPanel1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPanel1FocusGained
        // TODO add your handling code here
    }//GEN-LAST:event_jPanel1FocusGained

    private void jPanel1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jPanel1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel1PropertyChange
  
    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here
    }//GEN-LAST:event_formMouseClicked

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel2MouseClicked

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel3MouseClicked

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        // TODO add your handling code here: 
    }//GEN-LAST:event_jPanel1MouseClicked

    private void jchkInstanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkInstanceActionPerformed
        // TODO add your handling code here:
         if (jchkInstance.isSelected()) {                    
            jLabel7.setText(AppLocal.getIntString("label.instanceyes"));              
        } else {                 
            jLabel7.setText(AppLocal.getIntString("label.instanceno"));             
        }
    }//GEN-LAST:event_jchkInstanceActionPerformed

    private void jTaxIncludedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTaxIncludedActionPerformed
        // TODO add your handling code here:
        if (jTaxIncluded.isSelected()) {                     
            jLabel8.setText(AppLocal.getIntString("label.taxincludedyes"));              
        } else {             
            jLabel8.setText(AppLocal.getIntString("label.taxincludedno"));             
        }
    }//GEN-LAST:event_jTaxIncludedActionPerformed

    private void jchkTextOverlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkTextOverlayActionPerformed
        // TODO add your handling code here:
         if (jchkTextOverlay.isSelected()) {                 
            jLabel9.setText(AppLocal.getIntString("label.currencybuttonno"));              
        } else {        
            jLabel9.setText(AppLocal.getIntString("label.currencybuttonyes"));             
        }
    }//GEN-LAST:event_jchkTextOverlayActionPerformed

    private void jchkPriceUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkPriceUpdateActionPerformed
        // TODO add your handling code here:
        if (jchkPriceUpdate.isSelected()) {         
            jLabel10.setText(AppLocal.getIntString("label.priceupdateyes"));              
        } else {         
            jLabel10.setText(AppLocal.getIntString("label.priceupdateno"));             
        }
    }//GEN-LAST:event_jchkPriceUpdateActionPerformed

    private void jCheckPrice00ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckPrice00ActionPerformed
        // TODO add your handling code here:
        if (jCheckPrice00.isSelected()) {        
            jLabel11.setText(AppLocal.getIntString("label.pricewith00yes"));              
        } else {        
            jLabel11.setText(AppLocal.getIntString("label.pricewith00no"));             
        }
    }//GEN-LAST:event_jCheckPrice00ActionPerformed

    private void jMoveAMountBoxToTopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMoveAMountBoxToTopActionPerformed
        // TODO add your handling code here:
        if (jMoveAMountBoxToTop.isSelected()) {         
            jLabel12.setText(AppLocal.getIntString("label.inputamountyes"));              
        } else {        
            jLabel12.setText(AppLocal.getIntString("label.inputamountno"));             
        }
    }//GEN-LAST:event_jMoveAMountBoxToTopActionPerformed

    private void jCloseCashbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCloseCashbtnActionPerformed
        // TODO add your handling code here:
        if (jCloseCashbtn.isSelected()) {         
            jLabel13.setText(AppLocal.getIntString("message.systemclosecashyes"));              
        } else {        
            jLabel13.setText(AppLocal.getIntString("message.systemclosecashno"));             
        }
    }//GEN-LAST:event_jCloseCashbtnActionPerformed

    private void jchkBarcodetypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkBarcodetypeActionPerformed
        // TODO add your handling code here:
        if (jchkBarcodetype.isSelected()) {        
            jLabel14.setText(AppLocal.getIntString("label.barcodetypeyes"));              
        } else {        
            jLabel14.setText(AppLocal.getIntString("label.barcodetypeno"));             
        }
    }//GEN-LAST:event_jchkBarcodetypeActionPerformed

    private void chbTecladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbTecladoActionPerformed
        // TODO add your handling code here:
        if (chbTeclado.isSelected()) {             
            jLabel16.setText(AppLocal.getIntString("label.tecladoizq"));              
        } else {      
            jLabel16.setText(AppLocal.getIntString("label.tecladoder"));             
        }
    }//GEN-LAST:event_chbTecladoActionPerformed

    private void chbImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbImagenActionPerformed
        // TODO add your handling code here:
        if (chbImagen.isSelected()) {             
            jLabel15.setText(AppLocal.getIntString("label.imagenyes"));              
        } else {      
            jLabel15.setText(AppLocal.getIntString("label.imagenno"));             
        }
    }//GEN-LAST:event_chbImagenActionPerformed

    private void jchkAtajosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkAtajosActionPerformed
        // TODO add your handling code here:
        if (jchkAtajos.isSelected()) {             
            jLabel17.setText(AppLocal.getIntString("label.atajosyes"));              
        } else {      
            jLabel17.setText(AppLocal.getIntString("label.atajosno"));             
        }
    }//GEN-LAST:event_jchkAtajosActionPerformed

    private void chbReadCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbReadCodeActionPerformed
        // TODO add your handling code here:
        if (chbReadCode.isSelected()) {             
            jLabel19.setText(AppLocal.getIntString("label.pesobalanzayes"));              
        } else {      
            jLabel19.setText(AppLocal.getIntString("label.pesobalanzano"));             
        }
    }//GEN-LAST:event_chbReadCodeActionPerformed

    private void chkInBuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkInBuyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkInBuyActionPerformed

    private void chkOutBuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkOutBuyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkOutBuyActionPerformed

    private void chkInDevolutionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkInDevolutionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkInDevolutionActionPerformed

    private void chkOutDevolutionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkOutDevolutionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkOutDevolutionActionPerformed

    private void chkInTransferActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkInTransferActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkInTransferActionPerformed

    private void chkOutTransferActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkOutTransferActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkOutTransferActionPerformed

    private void chkSubstractActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSubstractActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkSubstractActionPerformed

    private void chkOutBreakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkOutBreakActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkOutBreakActionPerformed

    private void chkGivenFreeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkGivenFreeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkGivenFreeActionPerformed

    private void chkGivenSampleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkGivenSampleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkGivenSampleActionPerformed

    private void chkUsedItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkUsedItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkUsedItemActionPerformed

    private void chkTransferActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkTransferActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkTransferActionPerformed

    private void chbMinimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbMinimoActionPerformed
        // TODO add your handling code here:
        if (chbMinimo.isSelected()) {             
            jLabel31.setText(AppLocal.getIntString("label.showminimoyes"));              
        } else {      
            jLabel31.setText(AppLocal.getIntString("label.showminimono"));             
        }
    }//GEN-LAST:event_chbMinimoActionPerformed

    private void chbCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbCustomerActionPerformed
        // TODO add your handling code here:
        if (chbCustomer.isSelected()) {             
            jLabel32.setText(AppLocal.getIntString("label.customeryes"));              
        } else {      
            jLabel32.setText(AppLocal.getIntString("label.customerno"));             
        }
    }//GEN-LAST:event_chbCustomerActionPerformed

    private void optEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optEliminarActionPerformed
        // TODO add your handling code here:
         if (optEliminar.isSelected()) {      
            txtClave.setEditable(true);
            jLabel33.setText("Clave para eliminar línea Activada");              
        } else {      
            jLabel33.setText("Clave para eliminar línea Desactivada");             
        }
    }//GEN-LAST:event_optEliminarActionPerformed

    private void optGuardarSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optGuardarSaleActionPerformed
        // TODO add your handling code here:
        if (optGuardarSale.isSelected()) {      
            txtClave.setEditable(true);
            jLabel36.setText("Clave para guardar venta Activada");              
        } else {      
            jLabel36.setText("Clave para guardar venta Desactivada");             
        } 
    }//GEN-LAST:event_optGuardarSaleActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.alee.extended.button.WebSwitch BigTotal;
    private com.alee.extended.button.WebSwitch chbCashin;
    private com.alee.extended.button.WebSwitch chbCashout;
    private com.alee.extended.button.WebSwitch chbCustomer;
    private com.alee.extended.button.WebSwitch chbHideStock;
    private com.alee.extended.button.WebSwitch chbImagen;
    private com.alee.extended.button.WebSwitch chbMinimo;
    private com.alee.extended.button.WebSwitch chbReadCode;
    private com.alee.extended.button.WebSwitch chbTeclado;
    private com.alee.extended.button.WebSwitch chkGivenFree;
    private com.alee.extended.button.WebSwitch chkGivenSample;
    private com.alee.extended.button.WebSwitch chkInBuy;
    private com.alee.extended.button.WebSwitch chkInDevolution;
    private com.alee.extended.button.WebSwitch chkInTransfer;
    private com.alee.extended.button.WebSwitch chkOutBreak;
    private com.alee.extended.button.WebSwitch chkOutBuy;
    private com.alee.extended.button.WebSwitch chkOutDevolution;
    private com.alee.extended.button.WebSwitch chkOutTransfer;
    private com.alee.extended.button.WebSwitch chkSubstract;
    private com.alee.extended.button.WebSwitch chkTransfer;
    private com.alee.extended.button.WebSwitch chkUsedItem;
    private com.alee.extended.button.WebSwitch jCheckPrice00;
    private com.alee.extended.button.WebSwitch jCloseCashbtn;
    private com.alee.extended.button.WebSwitch jConsolidate;
    private com.alee.extended.colorchooser.WebColorChooserField jCustomerColour1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelInactiveTime;
    private javax.swing.JLabel jLabelInactiveTime1;
    private javax.swing.JLabel jLabelSCRate;
    private javax.swing.JLabel jLabelSCRatePerCent;
    private javax.swing.JLabel jLabelTableNameTextColour;
    private javax.swing.JLabel jLabelTimedMessage;
    private javax.swing.JLabel jLblautoRefresh;
    private com.alee.extended.button.WebSwitch jMoveAMountBoxToTop;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private com.alee.extended.colorchooser.WebColorChooserField jTableNameColour1;
    private com.alee.extended.button.WebSwitch jTaxIncluded;
    private javax.swing.JTextField jTextAutoLogoffTime;
    private javax.swing.JTextField jTextSCRate;
    private javax.swing.JTextField jTxtautoRefreshTimer;
    private com.alee.extended.colorchooser.WebColorChooserField jWaiterColour1;
    private com.alee.extended.button.WebSwitch jchkAtajos;
    private javax.swing.JCheckBox jchkAutoLogoff;
    private javax.swing.JCheckBox jchkAutoLogoffToTables;
    private com.alee.extended.button.WebSwitch jchkBarcodetype;
    private com.alee.extended.button.WebSwitch jchkInstance;
    private com.alee.extended.button.WebSwitch jchkPriceUpdate;
    private javax.swing.JCheckBox jchkSCOnOff;
    private javax.swing.JCheckBox jchkSCRestaurant;
    private javax.swing.JCheckBox jchkShowCustomerDetails;
    private javax.swing.JCheckBox jchkShowWaiterDetails;
    private com.alee.extended.button.WebSwitch jchkTextOverlay;
    private javax.swing.JCheckBox jchkTransBtn;
    private javax.swing.JCheckBox jchkautoRefreshTableMap;
    private com.alee.extended.button.WebSwitch optEliminar;
    private com.alee.extended.button.WebSwitch optGuardarSale;
    private javax.swing.JPasswordField txtClave;
    // End of variables declaration//GEN-END:variables
    
}
