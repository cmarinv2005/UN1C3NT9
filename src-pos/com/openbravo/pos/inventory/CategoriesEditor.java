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

package com.openbravo.pos.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.gui.JMessageDialog;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.SentenceExec;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSales;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import javax.swing.JPanel;
import com.alee.extended.time.ClockType;
import com.alee.extended.time.WebClock;
import com.alee.managers.notification.NotificationIcon;
import com.alee.managers.notification.NotificationManager;
import com.alee.managers.notification.WebNotification;
import com.openbravo.pos.forms.AppConfig;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import com.openbravo.pos.catalog.CategoryStock;
import java.awt.Dimension;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.table.JTableHeader;

/**
 *
 * @author adrianromero
 */
public final class CategoriesEditor extends JPanel implements EditorRecord {
       
    private Object m_oId;    
    public String cId;
    
    private final SentenceList m_sentcat;
    private ComboBoxValModel m_CategoryModel;
    
    private final SentenceExec m_sentadd;
    private final SentenceExec m_sentdel;
    
    private List<CategoryStock> categoryStockList;
    private CategoriesEditor.StockTableModel stockModel;
    private final DataLogicSales dlSales;
    private Object m_id;
    
    /** Creates new form JPanelCategories
     * @param app
     * @param dirty */
    public CategoriesEditor(AppView app, DirtyManager dirty) {
        
    //  DataLogicSales dlSales = (DataLogicSales) app.getBean("com.openbravo.pos.forms.DataLogicSales");
        dlSales = (DataLogicSales) app.getBean("com.openbravo.pos.forms.DataLogicSales");
        initComponents();
        jTableCategoryStock.setPreferredSize(null); 
        
        // El modelo de categorias        
        m_sentcat = dlSales.getCategoriesList();
        m_CategoryModel = new ComboBoxValModel();
        
        m_sentadd = dlSales.getCatalogCategoryAdd();
        m_sentdel = dlSales.getCatalogCategoryDel();
        
        m_jName.getDocument().addDocumentListener(dirty);
        m_jCategory.addActionListener(dirty);
        m_jImage.addPropertyChangeListener("image", dirty);
        webSwtchCatNameShow.addActionListener(dirty);
        m_jTextTip.getDocument().addDocumentListener(dirty); 
        m_jCatOrder.getDocument().addDocumentListener(dirty);
        
        writeValueEOF();
    }
    
    public void resetTranxTable() {

    jTableCategoryStock.getColumnModel().getColumn(0).setPreferredWidth(250);
    
    // set font for headers
    Font f = new Font("Arial", Font.BOLD, 14);
    JTableHeader header = jTableCategoryStock.getTableHeader();
    header.setFont(f);
      
    jTableCategoryStock.getTableHeader().setReorderingAllowed(true); 
    jTableCategoryStock.setAutoCreateRowSorter(true);    
    jTableCategoryStock.repaint();    
}
    
    class StockTableModel extends AbstractTableModel {
        String nam = AppLocal.getIntString("label.prodname");
        String cod = AppLocal.getIntString("label.prodbarcode");

        List<CategoryStock> stockList;
        String[] columnNames = {nam, cod};

        public StockTableModel(List<CategoryStock> list) {
            stockList = list;
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public int getRowCount() {
            return stockList.size();
        }

        @Override
        public Object getValueAt(int row, int column) {
            CategoryStock categoryStock = stockList.get(row);
        
            switch (column) {
                case 0:
                    return categoryStock.getProductName();                                        
                case 1:
                    return categoryStock.getProductCode();
                case 2:
                    return categoryStock.getProductId();
                default:
                    return "";
            }
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }
    } 
    
    /**
     *
     */
    @Override
    public void refresh() {
        
        List a;
        
        try {
            a = m_sentcat.list();
        } catch (BasicException eD) {
            MessageInf msg = new MessageInf(MessageInf.SGN_NOTICE, AppLocal.getIntString("message.cannotloadlists"), eD);
            msg.show(this);
            a = new ArrayList();
        }
        
        a.add(0, null);
        m_CategoryModel = new ComboBoxValModel(a);
        m_jCategory.setModel(m_CategoryModel);
    }
     
    /**
     *
     */
    @Override
    public void writeValueEOF() {
        m_id = null;
        m_jName.setText(null);
        m_CategoryModel.setSelectedKey(null);
        m_jImage.setImage(null);
        m_jName.setEnabled(false);
        m_jCategory.setEnabled(false);
        m_jImage.setEnabled(false);
        m_jTextTip.setText(null);        
        m_jTextTip.setEnabled(false);
        webSwtchCatNameShow.setSelected(false);
        webSwtchCatNameShow.setEnabled(false);
// Added JG Feb 2015
       m_jCatOrder.setEnabled(false);
       
    }
    
    /**
     *
     */
    @Override
    public void writeValueInsert() {
        m_id = UUID.randomUUID().toString();
        m_jName.setText(null);
        m_CategoryModel.setSelectedKey(null);
        m_jImage.setImage(null);
        m_jName.setEnabled(true);
        m_jCategory.setEnabled(true);
        m_jImage.setEnabled(true);
        m_jTextTip.setText(null);
        m_jTextTip.setEnabled(true);   
        webSwtchCatNameShow.setSelected(true);
        webSwtchCatNameShow.setEnabled(true);
// Added JG Feb 2015
       m_jCatOrder.setEnabled(true);        

    }

    /**
     *
     * @param value
     */
    @Override
    public void writeValueDelete(Object value) {
        Object[] cat = (Object[]) value;
        m_id = cat[0];
        m_jName.setText(Formats.STRING.formatValue(cat[1]));
        m_CategoryModel.setSelectedKey(cat[2]);
        m_jImage.setImage((BufferedImage) cat[3]);
        m_jTextTip.setText(Formats.STRING.formatValue(cat[4]));
        webSwtchCatNameShow.setSelected(((Boolean)cat[5]).booleanValue());
        m_jCatOrder.setText(Formats.STRING.formatValue(cat[6]));
        
        m_jName.setEnabled(false);
        m_jCategory.setEnabled(false);
        m_jImage.setEnabled(false);
        m_jTextTip.setEnabled(false);     
        webSwtchCatNameShow.setEnabled(false);
        m_jCatOrder.setEnabled(false);    
        
        stockModel = new CategoriesEditor.StockTableModel(getProductOfName((String) m_oId)); 
        
    }

    /**
     *
     * @param value
     */
    @Override
    public void writeValueEdit(Object value) {
        Object[] cat = (Object[]) value;
        m_id = cat[0];
        m_jName.setText(Formats.STRING.formatValue(cat[1]));
        m_CategoryModel.setSelectedKey(cat[2]);
        m_jImage.setImage((BufferedImage) cat[3]);
        m_jTextTip.setText(Formats.STRING.formatValue(cat[4])); 
        webSwtchCatNameShow.setSelected(((Boolean)cat[5]).booleanValue());

        if(m_jCatOrder.getText().length() == 0) {    
            m_jCatOrder.setText(null);        
        }
        m_jCatOrder.setText(Formats.STRING.formatValue(cat [6]));

        m_jName.setEnabled(true);
        m_jCategory.setEnabled(true);
        m_jImage.setEnabled(true);
        m_jTextTip.setEnabled(true); 
        webSwtchCatNameShow.setEnabled(true);
// Added JG Feb 2015
       m_jCatOrder.setEnabled(true);           
    
    }

    /**
     *
     * @return
     * @throws BasicException
     */
    @Override
    public Object createValue() throws BasicException {
        
        Object[] cat = new Object[8];

        cat[0] = m_id;
        cat[1] = m_jName.getText();
        cat[2] = m_CategoryModel.getSelectedKey();
        cat[3] = m_jImage.getImage();
        cat[4] = m_jTextTip.getText();
        cat[5] = webSwtchCatNameShow.isSelected();
        if(m_jCatOrder.getText().length() == 0) {    
            m_jCatOrder.setText(null);        
        }        
        cat[6] = m_jCatOrder.getText();        

        return cat;
    }

    /**
     *
     * @return
     */
    @Override
    public Component getComponent() {
        return this;
    }
    
     
    public void Notify(String msg){
        final WebNotification notification = new WebNotification ();
        notification.setIcon ( NotificationIcon.information );
        notification.setDisplayTime ( 4000 );

        final WebClock clock = new WebClock ();
        clock.setClockType ( ClockType.timer );
        clock.setTimeLeft ( 5000 );
        clock.setTimePattern ( msg );        
        notification.setContent ( clock );

        NotificationManager.showNotification ( notification );
        clock.start ();    
    } 
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        jLblName = new javax.swing.JLabel();
        m_jName = new javax.swing.JTextField();
        jLblCategory = new javax.swing.JLabel();
        m_jCategory = new javax.swing.JComboBox();
        jLblTextTip = new javax.swing.JLabel();
        m_jTextTip = new javax.swing.JTextField();
        jLblCatShowName = new javax.swing.JLabel();
        jLblCatOrder = new javax.swing.JLabel();
        m_jCatOrder = new javax.swing.JTextField();
        m_jImage = new com.openbravo.data.gui.JImageEditor();
        webSwtchCatNameShow = new com.alee.extended.button.WebSwitch();
        jBtnShowTrans = new javax.swing.JButton();
        jLblProdCount = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableCategoryStock = new com.alee.laf.table.WebTable();

        jInternalFrame1.setVisible(true);

        setPreferredSize(new java.awt.Dimension(500, 500));

        jLblName.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLblName.setText(AppLocal.getIntString("label.namem")); // NOI18N
        jLblName.setPreferredSize(new java.awt.Dimension(150, 30));

        m_jName.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        m_jName.setPreferredSize(new java.awt.Dimension(250, 30));

        jLblCategory.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLblCategory.setText(AppLocal.getIntString("label.prodcategory")); // NOI18N
        jLblCategory.setPreferredSize(new java.awt.Dimension(150, 30));

        m_jCategory.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        m_jCategory.setPreferredSize(new java.awt.Dimension(250, 30));

        jLblTextTip.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pos_messages"); // NOI18N
        jLblTextTip.setText(bundle.getString("label.texttip")); // NOI18N
        jLblTextTip.setPreferredSize(new java.awt.Dimension(150, 30));

        m_jTextTip.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        m_jTextTip.setPreferredSize(new java.awt.Dimension(250, 30));

        jLblCatShowName.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLblCatShowName.setText(bundle.getString("label.subcategorytitle")); // NOI18N
        jLblCatShowName.setPreferredSize(new java.awt.Dimension(150, 30));

        jLblCatOrder.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLblCatOrder.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLblCatOrder.setText(bundle.getString("label.ccatorder")); // NOI18N
        jLblCatOrder.setPreferredSize(new java.awt.Dimension(60, 30));

        m_jCatOrder.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jCatOrder.setPreferredSize(new java.awt.Dimension(60, 30));

        m_jImage.setPreferredSize(new java.awt.Dimension(300, 250));

        webSwtchCatNameShow.setToolTipText("");
        webSwtchCatNameShow.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        webSwtchCatNameShow.setPreferredSize(new java.awt.Dimension(80, 30));
        webSwtchCatNameShow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                webSwtchCatNameShowActionPerformed(evt);
            }
        });

        jBtnShowTrans.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jBtnShowTrans.setText(bundle.getString("button.CatProds")); // NOI18N
        jBtnShowTrans.setToolTipText("");
        jBtnShowTrans.setPreferredSize(new java.awt.Dimension(140, 30));
        jBtnShowTrans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnShowTransActionPerformed(evt);
            }
        });

        jLblProdCount.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLblProdCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLblProdCount.setOpaque(true);
        jLblProdCount.setPreferredSize(new java.awt.Dimension(237, 30));

        jScrollPane2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jScrollPane2.setPreferredSize(new java.awt.Dimension(650, 300));

        jTableCategoryStock.setAutoCreateRowSorter(true);
        jTableCategoryStock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Name", "Barcode"
            }
        ));
        jTableCategoryStock.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTableCategoryStock.setGridColor(new java.awt.Color(102, 204, 255));
        jTableCategoryStock.setPreferredSize(new java.awt.Dimension(310, 500));
        jTableCategoryStock.setRowHeight(25);
        jTableCategoryStock.setSelectionBackground(new java.awt.Color(0, 120, 215));
        jTableCategoryStock.setShowVerticalLines(false);
        jScrollPane2.setViewportView(jTableCategoryStock);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLblTextTip, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLblName, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLblCategory, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLblCatShowName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(m_jTextTip, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(webSwtchCatNameShow, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(m_jName, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(m_jCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLblCatOrder, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBtnShowTrans, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLblProdCount, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(m_jCatOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(m_jImage, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(m_jImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(m_jName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(m_jCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(m_jTextTip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(webSwtchCatNameShow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLblCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLblName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(43, 43, 43)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLblTextTip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLblCatShowName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLblCatOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(m_jCatOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLblProdCount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBtnShowTrans, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void webSwtchCatNameShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_webSwtchCatNameShowActionPerformed
        // TODO add your handling code here:
        if (webSwtchCatNameShow.isSelected()) {              
            jLblCatShowName.setText(AppLocal.getIntString("label.subcategorytitleyes"));              
        } else {             
            jLblCatShowName.setText(AppLocal.getIntString("label.subcategorytitleno"));             
        }
    }//GEN-LAST:event_webSwtchCatNameShowActionPerformed

    private List<CategoryStock> getProductOfName(String pId) {

        try {
            categoryStockList = dlSales.getCategorysProductList(pId);

        } catch (BasicException ex) {
            Logger.getLogger(CategoriesEditor.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<CategoryStock> categoryList = new ArrayList<>();

        for (CategoryStock categoryStock : categoryStockList) {
            String categoryId = categoryStock.getCategoryId();
            if (categoryId.equals(pId)) {
                categoryList.add(categoryStock);
            }
        }
        
        repaint();
        refresh();

        return categoryList;
    }
    
    private void jBtnShowTransActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnShowTransActionPerformed
        String pId = m_id.toString();
        if (pId != null) {
            stockModel = new CategoriesEditor.StockTableModel(getProductOfName(pId));
            jTableCategoryStock.setModel(stockModel);

            if (stockModel.getRowCount()> 0){
                jTableCategoryStock.setVisible(true);
                String ProdCount = String.valueOf(stockModel.getRowCount());
                jLblProdCount.setText(ProdCount + " para " + m_jName.getText());
            }else{
                jTableCategoryStock.setVisible(false);
                JOptionPane.showMessageDialog(null,
                    "Esta categoria no tiene productos", "Productos", JOptionPane.INFORMATION_MESSAGE);
            }
            resetTranxTable();
        }
    }//GEN-LAST:event_jBtnShowTransActionPerformed
 
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnShowTrans;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLblCatOrder;
    private javax.swing.JLabel jLblCatShowName;
    private javax.swing.JLabel jLblCategory;
    private javax.swing.JLabel jLblName;
    private javax.swing.JLabel jLblProdCount;
    private javax.swing.JLabel jLblTextTip;
    private javax.swing.JScrollPane jScrollPane2;
    private com.alee.laf.table.WebTable jTableCategoryStock;
    private javax.swing.JTextField m_jCatOrder;
    private javax.swing.JComboBox m_jCategory;
    private com.openbravo.data.gui.JImageEditor m_jImage;
    private javax.swing.JTextField m_jName;
    private javax.swing.JTextField m_jTextTip;
    private com.alee.extended.button.WebSwitch webSwtchCatNameShow;
    // End of variables declaration//GEN-END:variables
    
}