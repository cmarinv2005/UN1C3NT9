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
//    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-130

package com.openbravo.pos.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import java.awt.Component;

/**
 *
 * @author adrianromero
 */
public class ProductsWarehouseEditorResumen extends javax.swing.JPanel implements EditorRecord {

    /**
     *
     */
    public Object id;
    public Object prodid;
    public Object prodref;
    public Object prodname;
    public Object location;
    public Object pricebuy;
    public Object pricesell;
    public Object code;
        
    /** Creates new form ProductsWarehouseEditor
     * @param dirty */
    public ProductsWarehouseEditorResumen(DirtyManager dirty) {
        initComponents();
        
        m_jMinimum.getDocument().addDocumentListener(dirty);
        m_jMaximum.getDocument().addDocumentListener(dirty);
    }

    public ProductsWarehouseEditorResumen() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     *
     */
    @Override
    public void writeValueEOF() {
        m_jTitle.setText(AppLocal.getIntString("label.recordeof"));
        id = null;
        prodid = null;
        prodref = null;
        prodname = null;
        location = null;
        pricebuy = null;
        pricesell = null;
        code = null;      
        m_jQuantity.setText(null);
        m_jPricebuy.setText(null);
        m_jPricesell.setText(null);
        m_jCode.setText(null);
        
        
        m_jMinimum.setText(null);
        m_jMaximum.setText(null);
        m_jMinimum.setEnabled(false);
        m_jMaximum.setEnabled(false);
    }

    /**
     *
     */
    @Override
    public void writeValueInsert() {
        m_jTitle.setText(AppLocal.getIntString("label.recordnew"));
        id = null;
        prodid = null;
        prodref = null;
        prodname = null;
        location = null;        
        m_jQuantity.setText(null);
        m_jMinimum.setText(null);
        m_jMaximum.setText(null);
        m_jMinimum.setEnabled(true);
        m_jMaximum.setEnabled(true);
    }

    /**
     *
     * @param value
     */
    @Override
    public void writeValueEdit(Object value) {
        Object[] myprod = (Object[]) value;
        id = myprod[0];
        prodid = myprod[1];
        prodref = myprod[2];
        prodname = myprod[3];
        location = myprod[4];
        pricebuy = myprod[5];  
        pricesell = myprod[6];
        code = myprod[7];
        
        m_jRef.setText(Formats.STRING.formatValue(myprod[2]));
        m_jTitle.setText(Formats.STRING.formatValue(myprod[3]));         
        
        m_jQuantity.setText(Formats.DOUBLE.formatValue(myprod[10]));     
        
        m_jPricebuy.setText(Formats.DOUBLE.formatValue(myprod[5]));
        m_jPricesell.setText(Formats.DOUBLE.formatValue(myprod[6]));        
        m_jCode.setText(Formats.STRING.formatValue(myprod[7])); 
        
        m_jMinimum.setText(Formats.DOUBLE.formatValue(myprod[8]));
        m_jMaximum.setText(Formats.DOUBLE.formatValue(myprod[9]));
        m_jMinimum.setEnabled(true);
        m_jMaximum.setEnabled(true);
     }

    /**
     *
     * @param value
     */
    @Override
    public void writeValueDelete(Object value) {
        Object[] myprod = (Object[]) value;
        id = myprod[0];
        prodid = myprod[1];
        prodref = myprod[2];
        prodname = myprod[3];
        location = myprod[4];
        pricebuy = myprod[5];  
        pricesell = myprod[6];
        code = myprod[7];  
        m_jTitle.setText(Formats.STRING.formatValue(myprod[2]) + " - " + Formats.STRING.formatValue(myprod[3]));
        m_jQuantity.setText(Formats.DOUBLE.formatValue(myprod[10]));  
        
        m_jPricebuy.setText(Formats.DOUBLE.formatValue(myprod[5]));
        m_jPricesell.setText(Formats.DOUBLE.formatValue(myprod[6]));       
        m_jCode.setText(Formats.STRING.formatValue(myprod[7]));       
        m_jMinimum.setText(Formats.DOUBLE.formatValue(myprod[8]));
        m_jMaximum.setText(Formats.DOUBLE.formatValue(myprod[9]));
        m_jMinimum.setEnabled(false);
        m_jMaximum.setEnabled(false);       
    }

    /**
     *
     * @return
     * @throws BasicException
     */
    @Override
    public Object createValue() throws BasicException {
        return new Object[] {
            id,
            prodid,
            prodref,
            prodname,
            location, 
            pricebuy, 
            pricesell,
            code,
             
            Formats.DOUBLE.parseValue(m_jMinimum.getText()),
            Formats.DOUBLE.parseValue(m_jMaximum.getText()),
            Formats.DOUBLE.parseValue(m_jQuantity.getText()),
            
            Formats.DOUBLE.parseValue(m_jPricebuy.getText()),
            Formats.DOUBLE.parseValue(m_jPricesell.getText()),       
            Formats.STRING.parseValue(m_jCode.getText())
        };
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
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        m_jMinimum = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        m_jMaximum = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        m_jPricebuy = new javax.swing.JTextField();
        m_jPricesell = new javax.swing.JTextField();
        m_jCode = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        m_jRef = new javax.swing.JTextField();
        m_jTitle = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        m_jQuantity = new javax.swing.JTextField();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Resumen Productos por Almacén", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText(AppLocal.getIntString("label.minimum")); // NOI18N
        jLabel4.setPreferredSize(new java.awt.Dimension(110, 30));

        m_jMinimum.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        m_jMinimum.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        m_jMinimum.setPreferredSize(new java.awt.Dimension(0, 30));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText(AppLocal.getIntString("label.maximum")); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(110, 30));

        m_jMaximum.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        m_jMaximum.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        m_jMaximum.setPreferredSize(new java.awt.Dimension(0, 30));

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText(AppLocal.getIntString("Label.Name"));
        jLabel6.setPreferredSize(new java.awt.Dimension(110, 30));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("Precio de Compra");
        jLabel1.setMaximumSize(new java.awt.Dimension(53, 15));
        jLabel1.setMinimumSize(new java.awt.Dimension(53, 15));
        jLabel1.setPreferredSize(new java.awt.Dimension(29, 15));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Precio de Venta");
        jLabel2.setPreferredSize(new java.awt.Dimension(29, 15));

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("Código de barras");
        jLabel7.setPreferredSize(new java.awt.Dimension(29, 15));

        m_jPricebuy.setEditable(false);
        m_jPricebuy.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        m_jPricebuy.setPreferredSize(new java.awt.Dimension(29, 15));

        m_jPricesell.setEditable(false);
        m_jPricesell.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        m_jCode.setEditable(false);
        m_jCode.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText("Referencia");
        jLabel8.setPreferredSize(new java.awt.Dimension(29, 15));

        m_jRef.setEditable(false);
        m_jRef.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        m_jTitle.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        m_jTitle.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        m_jTitle.setPreferredSize(new java.awt.Dimension(300, 30));

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText(AppLocal.getIntString("label.units2"));
        jLabel3.setPreferredSize(new java.awt.Dimension(110, 30));

        m_jQuantity.setEditable(false);
        m_jQuantity.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        m_jQuantity.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        m_jQuantity.setPreferredSize(new java.awt.Dimension(0, 30));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(m_jRef, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(m_jCode, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(m_jPricesell, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(m_jPricebuy, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(m_jMaximum, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(m_jMinimum, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(m_jQuantity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(m_jTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 805, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(152, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_jRef, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_jCode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(m_jTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_jQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_jMinimum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_jMaximum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_jPricebuy, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_jPricesell, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(227, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField m_jCode;
    private javax.swing.JTextField m_jMaximum;
    private javax.swing.JTextField m_jMinimum;
    private javax.swing.JTextField m_jPricebuy;
    private javax.swing.JTextField m_jPricesell;
    private javax.swing.JTextField m_jQuantity;
    private javax.swing.JTextField m_jRef;
    private javax.swing.JLabel m_jTitle;
    // End of variables declaration//GEN-END:variables
    
}
