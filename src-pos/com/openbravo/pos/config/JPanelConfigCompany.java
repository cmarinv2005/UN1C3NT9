//    uniCenta oPOS  - Touch Friendly Point Of Sale
//    Copyright (c) 2009-2016 uniCenta
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

package com.openbravo.pos.config;

import com.openbravo.data.user.DirtyManager;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import java.awt.Component;

/**
 *
 * @author JG uniCenta
 */
public class JPanelConfigCompany extends javax.swing.JPanel implements PanelConfig {
    
    private final DirtyManager dirty = new DirtyManager();


    /**
     *
     */
    public JPanelConfigCompany() {
        
        initComponents();
                          
        jtxtTktHeader1.getDocument().addDocumentListener(dirty);
        jtxtTktHeader2.getDocument().addDocumentListener(dirty);
        jtxtTktHeader3.getDocument().addDocumentListener(dirty);
        jtxtTktHeader4.getDocument().addDocumentListener(dirty);
        jtxtTktHeader5.getDocument().addDocumentListener(dirty);
        jtxtTktHeader6.getDocument().addDocumentListener(dirty);
        jtxtTktHeader7.getDocument().addDocumentListener(dirty);
        jtxtTktHeader8.getDocument().addDocumentListener(dirty);

        jtxtTktFooter1.getDocument().addDocumentListener(dirty);
        jtxtTktFooter2.getDocument().addDocumentListener(dirty);
        jtxtTktFooter3.getDocument().addDocumentListener(dirty);
        jtxtTktFooter4.getDocument().addDocumentListener(dirty);
        jtxtTktFooter5.getDocument().addDocumentListener(dirty);
        jtxtTktFooter6.getDocument().addDocumentListener(dirty); 
        jtxtTktFooter7.getDocument().addDocumentListener(dirty);
        
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

        jtxtTktHeader1.setText(config.getProperty("tkt.header1"));
        jtxtTktHeader2.setText(config.getProperty("tkt.header2"));
        jtxtTktHeader3.setText(config.getProperty("tkt.header3"));  
        jtxtTktHeader4.setText(config.getProperty("tkt.header4"));  
        jtxtTktHeader5.setText(config.getProperty("tkt.header5"));  
        jtxtTktHeader6.setText(config.getProperty("tkt.header6"));  
        jtxtTktHeader7.setText(config.getProperty("tkt.header7")); 
        jtxtTktHeader8.setText(config.getProperty("tkt.header8"));
        
        jtxtTktFooter1.setText(config.getProperty("tkt.footer1"));
        jtxtTktFooter2.setText(config.getProperty("tkt.footer2"));
        jtxtTktFooter3.setText(config.getProperty("tkt.footer3"));  
        jtxtTktFooter4.setText(config.getProperty("tkt.footer4"));  
        jtxtTktFooter5.setText(config.getProperty("tkt.footer5"));  
        jtxtTktFooter6.setText(config.getProperty("tkt.footer6")); 
        jtxtTktFooter7.setText(config.getProperty("tkt.footer7")); 

        dirty.setDirty(false);        
        
    }
   
    /**
     *
     * @param config
     */
    @Override
    public void saveProperties(AppConfig config) {
        
        config.setProperty("tkt.header1", jtxtTktHeader1.getText());
        config.setProperty("tkt.header2", jtxtTktHeader2.getText()); 
        config.setProperty("tkt.header3", jtxtTktHeader3.getText()); 
        config.setProperty("tkt.header4", jtxtTktHeader4.getText()); 
        config.setProperty("tkt.header5", jtxtTktHeader5.getText()); 
        config.setProperty("tkt.header6", jtxtTktHeader6.getText());
        config.setProperty("tkt.header7", jtxtTktHeader7.getText());
        config.setProperty("tkt.header8", jtxtTktHeader8.getText());
        
        config.setProperty("tkt.footer1", jtxtTktFooter1.getText());
        config.setProperty("tkt.footer2", jtxtTktFooter2.getText()); 
        config.setProperty("tkt.footer3", jtxtTktFooter3.getText()); 
        config.setProperty("tkt.footer4", jtxtTktFooter4.getText()); 
        config.setProperty("tkt.footer5", jtxtTktFooter5.getText()); 
        config.setProperty("tkt.footer6", jtxtTktFooter6.getText()); 
        config.setProperty("tkt.footer7", jtxtTktFooter7.getText()); 
        
        dirty.setDirty(false);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTktHeader1 = new javax.swing.JLabel();
        lblTktHeader2 = new javax.swing.JLabel();
        lblTktHeader3 = new javax.swing.JLabel();
        lblTktHeader4 = new javax.swing.JLabel();
        lblTktHeader5 = new javax.swing.JLabel();
        lblTktHeader6 = new javax.swing.JLabel();
        jtxtTktHeader1 = new javax.swing.JTextField();
        jtxtTktHeader2 = new javax.swing.JTextField();
        jtxtTktHeader3 = new javax.swing.JTextField();
        jtxtTktHeader4 = new javax.swing.JTextField();
        jtxtTktHeader5 = new javax.swing.JTextField();
        jtxtTktHeader6 = new javax.swing.JTextField();
        lblTktFooter1 = new javax.swing.JLabel();
        lblTktFooter2 = new javax.swing.JLabel();
        lblTktFooter3 = new javax.swing.JLabel();
        lblTktFooter4 = new javax.swing.JLabel();
        lblTktFooter5 = new javax.swing.JLabel();
        lblTktFooter6 = new javax.swing.JLabel();
        jtxtTktFooter1 = new javax.swing.JTextField();
        jtxtTktFooter2 = new javax.swing.JTextField();
        jtxtTktFooter3 = new javax.swing.JTextField();
        jtxtTktFooter4 = new javax.swing.JTextField();
        jtxtTktFooter5 = new javax.swing.JTextField();
        jtxtTktFooter6 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        lblTktHeader7 = new javax.swing.JLabel();
        lblTktHeader8 = new javax.swing.JLabel();
        jtxtTktHeader7 = new javax.swing.JTextField();
        jtxtTktHeader8 = new javax.swing.JTextField();
        lblTktFooter7 = new javax.swing.JLabel();
        jtxtTktFooter7 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        setPreferredSize(new java.awt.Dimension(700, 500));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        lblTktHeader1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblTktHeader1.setText(AppLocal.getIntString("label.tktheader1")); // NOI18N
        lblTktHeader1.setMaximumSize(new java.awt.Dimension(0, 25));
        lblTktHeader1.setMinimumSize(new java.awt.Dimension(0, 0));
        lblTktHeader1.setPreferredSize(new java.awt.Dimension(150, 30));

        lblTktHeader2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblTktHeader2.setText(AppLocal.getIntString("label.tktheader2")); // NOI18N
        lblTktHeader2.setMaximumSize(new java.awt.Dimension(0, 25));
        lblTktHeader2.setMinimumSize(new java.awt.Dimension(0, 0));
        lblTktHeader2.setPreferredSize(new java.awt.Dimension(150, 30));

        lblTktHeader3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblTktHeader3.setText(AppLocal.getIntString("label.tktheader3")); // NOI18N
        lblTktHeader3.setMaximumSize(new java.awt.Dimension(0, 25));
        lblTktHeader3.setMinimumSize(new java.awt.Dimension(0, 0));
        lblTktHeader3.setPreferredSize(new java.awt.Dimension(150, 30));

        lblTktHeader4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblTktHeader4.setText(AppLocal.getIntString("label.tktheader4")); // NOI18N
        lblTktHeader4.setMaximumSize(new java.awt.Dimension(0, 25));
        lblTktHeader4.setMinimumSize(new java.awt.Dimension(0, 0));
        lblTktHeader4.setPreferredSize(new java.awt.Dimension(150, 30));

        lblTktHeader5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblTktHeader5.setText(AppLocal.getIntString("label.tktheader5")); // NOI18N
        lblTktHeader5.setMaximumSize(new java.awt.Dimension(0, 25));
        lblTktHeader5.setMinimumSize(new java.awt.Dimension(0, 0));
        lblTktHeader5.setPreferredSize(new java.awt.Dimension(150, 30));

        lblTktHeader6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblTktHeader6.setText(AppLocal.getIntString("label.tktheader6")); // NOI18N
        lblTktHeader6.setMaximumSize(new java.awt.Dimension(0, 25));
        lblTktHeader6.setMinimumSize(new java.awt.Dimension(0, 0));
        lblTktHeader6.setPreferredSize(new java.awt.Dimension(150, 30));

        jtxtTktHeader1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jtxtTktHeader1.setMaximumSize(new java.awt.Dimension(0, 25));
        jtxtTktHeader1.setMinimumSize(new java.awt.Dimension(0, 0));
        jtxtTktHeader1.setPreferredSize(new java.awt.Dimension(300, 30));

        jtxtTktHeader2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jtxtTktHeader2.setMaximumSize(new java.awt.Dimension(0, 25));
        jtxtTktHeader2.setMinimumSize(new java.awt.Dimension(0, 0));
        jtxtTktHeader2.setPreferredSize(new java.awt.Dimension(300, 30));

        jtxtTktHeader3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jtxtTktHeader3.setMaximumSize(new java.awt.Dimension(0, 25));
        jtxtTktHeader3.setMinimumSize(new java.awt.Dimension(0, 0));
        jtxtTktHeader3.setPreferredSize(new java.awt.Dimension(300, 30));

        jtxtTktHeader4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jtxtTktHeader4.setMaximumSize(new java.awt.Dimension(0, 25));
        jtxtTktHeader4.setMinimumSize(new java.awt.Dimension(0, 0));
        jtxtTktHeader4.setPreferredSize(new java.awt.Dimension(300, 30));

        jtxtTktHeader5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jtxtTktHeader5.setMaximumSize(new java.awt.Dimension(0, 25));
        jtxtTktHeader5.setMinimumSize(new java.awt.Dimension(0, 0));
        jtxtTktHeader5.setPreferredSize(new java.awt.Dimension(300, 30));

        jtxtTktHeader6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jtxtTktHeader6.setMaximumSize(new java.awt.Dimension(0, 25));
        jtxtTktHeader6.setMinimumSize(new java.awt.Dimension(0, 0));
        jtxtTktHeader6.setPreferredSize(new java.awt.Dimension(300, 30));

        lblTktFooter1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblTktFooter1.setText(AppLocal.getIntString("label.tktfooter1")); // NOI18N
        lblTktFooter1.setMaximumSize(new java.awt.Dimension(0, 25));
        lblTktFooter1.setMinimumSize(new java.awt.Dimension(0, 0));
        lblTktFooter1.setPreferredSize(new java.awt.Dimension(150, 30));

        lblTktFooter2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblTktFooter2.setText(AppLocal.getIntString("label.tktfooter2")); // NOI18N
        lblTktFooter2.setMaximumSize(new java.awt.Dimension(0, 25));
        lblTktFooter2.setMinimumSize(new java.awt.Dimension(0, 0));
        lblTktFooter2.setPreferredSize(new java.awt.Dimension(150, 30));

        lblTktFooter3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblTktFooter3.setText(AppLocal.getIntString("label.tktfooter3")); // NOI18N
        lblTktFooter3.setMaximumSize(new java.awt.Dimension(0, 25));
        lblTktFooter3.setMinimumSize(new java.awt.Dimension(0, 0));
        lblTktFooter3.setPreferredSize(new java.awt.Dimension(150, 30));

        lblTktFooter4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblTktFooter4.setText(AppLocal.getIntString("label.tktfooter4")); // NOI18N
        lblTktFooter4.setMaximumSize(new java.awt.Dimension(0, 25));
        lblTktFooter4.setMinimumSize(new java.awt.Dimension(0, 0));
        lblTktFooter4.setPreferredSize(new java.awt.Dimension(150, 30));

        lblTktFooter5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblTktFooter5.setText(AppLocal.getIntString("label.tktfooter5")); // NOI18N
        lblTktFooter5.setMaximumSize(new java.awt.Dimension(0, 25));
        lblTktFooter5.setMinimumSize(new java.awt.Dimension(0, 0));
        lblTktFooter5.setPreferredSize(new java.awt.Dimension(150, 30));

        lblTktFooter6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblTktFooter6.setText(AppLocal.getIntString("label.tktfooter6")); // NOI18N
        lblTktFooter6.setToolTipText("");
        lblTktFooter6.setMaximumSize(new java.awt.Dimension(0, 25));
        lblTktFooter6.setMinimumSize(new java.awt.Dimension(0, 0));
        lblTktFooter6.setPreferredSize(new java.awt.Dimension(150, 30));

        jtxtTktFooter1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jtxtTktFooter1.setMaximumSize(new java.awt.Dimension(0, 25));
        jtxtTktFooter1.setMinimumSize(new java.awt.Dimension(0, 0));
        jtxtTktFooter1.setPreferredSize(new java.awt.Dimension(300, 30));

        jtxtTktFooter2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jtxtTktFooter2.setMaximumSize(new java.awt.Dimension(0, 25));
        jtxtTktFooter2.setMinimumSize(new java.awt.Dimension(0, 0));
        jtxtTktFooter2.setPreferredSize(new java.awt.Dimension(300, 30));

        jtxtTktFooter3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jtxtTktFooter3.setMaximumSize(new java.awt.Dimension(0, 25));
        jtxtTktFooter3.setMinimumSize(new java.awt.Dimension(0, 0));
        jtxtTktFooter3.setPreferredSize(new java.awt.Dimension(300, 30));

        jtxtTktFooter4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jtxtTktFooter4.setMaximumSize(new java.awt.Dimension(0, 25));
        jtxtTktFooter4.setMinimumSize(new java.awt.Dimension(0, 0));
        jtxtTktFooter4.setPreferredSize(new java.awt.Dimension(300, 30));

        jtxtTktFooter5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jtxtTktFooter5.setMaximumSize(new java.awt.Dimension(0, 25));
        jtxtTktFooter5.setMinimumSize(new java.awt.Dimension(0, 0));
        jtxtTktFooter5.setPreferredSize(new java.awt.Dimension(300, 30));

        jtxtTktFooter6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jtxtTktFooter6.setMaximumSize(new java.awt.Dimension(0, 25));
        jtxtTktFooter6.setMinimumSize(new java.awt.Dimension(0, 0));
        jtxtTktFooter6.setPreferredSize(new java.awt.Dimension(300, 30));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTktHeader1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lblTktHeader5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                                .addComponent(lblTktHeader4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
                                .addComponent(lblTktHeader3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
                                .addComponent(lblTktHeader2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
                            .addComponent(lblTktHeader6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jtxtTktHeader5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
                    .addComponent(jtxtTktHeader4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
                    .addComponent(jtxtTktHeader3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
                    .addComponent(jtxtTktHeader2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jtxtTktHeader1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jtxtTktHeader6, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblTktFooter5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                            .addComponent(lblTktFooter4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(lblTktFooter3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(lblTktFooter2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(lblTktFooter6, javax.swing.GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lblTktFooter1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtxtTktFooter5, javax.swing.GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
                    .addComponent(jtxtTktFooter4, javax.swing.GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
                    .addComponent(jtxtTktFooter3, javax.swing.GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
                    .addComponent(jtxtTktFooter2, javax.swing.GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
                    .addComponent(jtxtTktFooter6, javax.swing.GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
                    .addComponent(jtxtTktFooter1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtTktFooter1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTktFooter1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTktFooter2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtTktFooter2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTktFooter3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtTktFooter3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTktFooter4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtTktFooter4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTktFooter5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtTktFooter5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTktFooter6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtTktFooter6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTktHeader1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtTktHeader1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTktHeader2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtTktHeader2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtxtTktHeader3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTktHeader3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtxtTktHeader4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTktHeader4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtxtTktHeader5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTktHeader5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtxtTktHeader6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTktHeader6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        lblTktHeader7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblTktHeader7.setText(AppLocal.getIntString("label.tktheader1")); // NOI18N
        lblTktHeader7.setMaximumSize(new java.awt.Dimension(0, 25));
        lblTktHeader7.setMinimumSize(new java.awt.Dimension(0, 0));
        lblTktHeader7.setPreferredSize(new java.awt.Dimension(150, 24));

        lblTktHeader8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblTktHeader8.setText(AppLocal.getIntString("label.tktheader2")); // NOI18N
        lblTktHeader8.setMaximumSize(new java.awt.Dimension(0, 25));
        lblTktHeader8.setMinimumSize(new java.awt.Dimension(0, 0));
        lblTktHeader8.setPreferredSize(new java.awt.Dimension(150, 24));

        jtxtTktHeader7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jtxtTktHeader7.setMaximumSize(new java.awt.Dimension(0, 25));
        jtxtTktHeader7.setMinimumSize(new java.awt.Dimension(0, 0));
        jtxtTktHeader7.setPreferredSize(new java.awt.Dimension(300, 30));

        jtxtTktHeader8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jtxtTktHeader8.setMaximumSize(new java.awt.Dimension(0, 25));
        jtxtTktHeader8.setMinimumSize(new java.awt.Dimension(0, 0));
        jtxtTktHeader8.setPreferredSize(new java.awt.Dimension(300, 30));

        lblTktFooter7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblTktFooter7.setText(AppLocal.getIntString("label.tktfooter1")   ); // NOI18N
        lblTktFooter7.setMaximumSize(new java.awt.Dimension(0, 25));
        lblTktFooter7.setMinimumSize(new java.awt.Dimension(0, 0));
        lblTktFooter7.setPreferredSize(new java.awt.Dimension(150, 24));

        jtxtTktFooter7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jtxtTktFooter7.setMaximumSize(new java.awt.Dimension(0, 25));
        jtxtTktFooter7.setMinimumSize(new java.awt.Dimension(0, 0));
        jtxtTktFooter7.setPreferredSize(new java.awt.Dimension(300, 30));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTktFooter7, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addComponent(lblTktHeader8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(lblTktHeader7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtxtTktHeader7, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
                    .addComponent(jtxtTktHeader8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtxtTktFooter7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtxtTktHeader7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTktHeader7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtxtTktHeader8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTktHeader8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTktFooter7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtTktFooter7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
        );

        jLabel1.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jLabel1.setText("Ticket");

        jLabel2.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jLabel2.setText("Factura");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jtxtTktFooter1;
    private javax.swing.JTextField jtxtTktFooter2;
    private javax.swing.JTextField jtxtTktFooter3;
    private javax.swing.JTextField jtxtTktFooter4;
    private javax.swing.JTextField jtxtTktFooter5;
    private javax.swing.JTextField jtxtTktFooter6;
    private javax.swing.JTextField jtxtTktFooter7;
    private javax.swing.JTextField jtxtTktHeader1;
    private javax.swing.JTextField jtxtTktHeader2;
    private javax.swing.JTextField jtxtTktHeader3;
    private javax.swing.JTextField jtxtTktHeader4;
    private javax.swing.JTextField jtxtTktHeader5;
    private javax.swing.JTextField jtxtTktHeader6;
    private javax.swing.JTextField jtxtTktHeader7;
    private javax.swing.JTextField jtxtTktHeader8;
    private javax.swing.JLabel lblTktFooter1;
    private javax.swing.JLabel lblTktFooter2;
    private javax.swing.JLabel lblTktFooter3;
    private javax.swing.JLabel lblTktFooter4;
    private javax.swing.JLabel lblTktFooter5;
    private javax.swing.JLabel lblTktFooter6;
    private javax.swing.JLabel lblTktFooter7;
    private javax.swing.JLabel lblTktHeader1;
    private javax.swing.JLabel lblTktHeader2;
    private javax.swing.JLabel lblTktHeader3;
    private javax.swing.JLabel lblTktHeader4;
    private javax.swing.JLabel lblTktHeader5;
    private javax.swing.JLabel lblTktHeader6;
    private javax.swing.JLabel lblTktHeader7;
    private javax.swing.JLabel lblTktHeader8;
    // End of variables declaration//GEN-END:variables
    
}
