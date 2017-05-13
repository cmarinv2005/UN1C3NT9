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


package com.openbravo.pos.imports;

import com.csvreader.CsvReader;
import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.loader.Session;
import com.openbravo.data.user.SaveProvider;
import com.openbravo.pos.forms.*;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.openbravo.pos.suppliers.SupplierInfoExt;
import com.openbravo.pos.suppliers.DataLogicSuppliers;
import java.awt.Dimension;
import java.util.List;


/**
 * Graphical User Interface and code for importing data from a CSV file allowing
 * adding or updating many suppliers quickly and easily.
 *
 */
public class SuppliersCSVImport extends JPanel implements JPanelView {

    private ArrayList<String> Headers = new ArrayList<>();
    private Session s;
    private Connection con;
    private String csvFileName;

    private String csvMessage = "";
    private CsvReader suppliers;
    private int currentRecord;
    private int rowCount = 0;
    private String last_folder;
    private File config_file;  

    private DataLogicSales m_dlSales;
    private DataLogicSystem m_dlSystem;    
    private DataLogicSuppliers m_dlSupplier;

    protected SaveProvider spr;
         
    private String supplierSearchKey; 
    private String supplierAccount;   
    private String supplierName;
    private String supplierAddress1;
    private String supplierAddress2;
    private String supplierPostal; 
    private String supplierCity; 
    private String supplierRegion;
    private String supplierFirstName; 
    private String supplierLastName;     
    private String supplierEmail; 
    private String supplierPhone;
    private String supplierPhone2;    
    private double supplierMaxdebt;    
    private String supplierNotes;
    private String supplierFax;
  
    
    private DocumentListener documentListener;
    private SupplierInfoExt suppInfo;    
    private String recordType = null;

    private int newRecords = 0;
    private int invalid = 0;
    private int updated = 0;
    private int missing = 0;
    private int noChange = 0;
    private int bad = 0;
    
    private Integer progress = 0;    

    /**
     * Constructs a new supplierCSVImport object
     *
     * @param oApp AppView
     */
    public SuppliersCSVImport(AppView oApp) {
        this(oApp.getProperties());
    }

    /**
     * Constructs a new JPanelCSVImport object
     *
     * @param props AppProperties
     */
    @SuppressWarnings("empty-statement")
    public SuppliersCSVImport(AppProperties props) {
        initComponents();

        try {
            s = AppViewConnection.createSession(props);
            con = s.getConnection();
        } catch (BasicException | SQLException e) {;
        }

        m_dlSales = new DataLogicSales();
        m_dlSales.init(s);

        m_dlSystem = new DataLogicSystem();
        m_dlSystem.init(s);

        jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(30,30));

        spr = new SaveProvider(
                m_dlSales.getSupplierUpdate(),
                m_dlSales.getSupplierInsert(),
                m_dlSales.getSupplierDelete());

        last_folder = props.getProperty("CSV.last_folder");
        config_file = props.getConfigFile();

        jFileName.getDocument().addDocumentListener(documentListener);

        documentListener = new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                jbtnRead.setEnabled(true);
            }

            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                if (!"".equals(jFileName.getText().trim())) {
                    jbtnRead.setEnabled(true);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                if (jFileName.getText().trim().equals("")) {
                    jbtnRead.setEnabled(false);
                }
            }
        };
        jFileName.getDocument().addDocumentListener(documentListener);

    }

    /**
     * Reads the headers from the CSV file and initializes subsequent form
     * fields. This function first reads the headers from the CSVFileName file,
     * then puts them into the header combo boxes and enables the other form
     * inputs.
     *
     * @param CSVFileName Name of the file (including the path) to open and read
     * CSV data from
     * @throws IOException If there is an issue reading the CSV file
     */
    private void GetheadersFromFile(String CSVFileName) throws IOException {

        File f = new File(CSVFileName);
        if (f.exists()) {
            suppliers = new CsvReader(CSVFileName);
            suppliers.setDelimiter(((String) jComboSeparator.getSelectedItem()).charAt(0));
            suppliers.readHeaders();
                          
            if (suppliers.getHeaderCount() < 5) {
                JOptionPane.showMessageDialog(null,
                        "Incorrect header in your source file",
                        "Header Error",
                        JOptionPane.WARNING_MESSAGE);
                suppliers.close();
                return;
            }
            rowCount = 0;
            int i = 0;
            Headers.clear();
            Headers.add("");
            jComboName.addItem("");
            jComboAccountID.addItem("");
            jComboSearchKey.addItem("");
            jComboAddress1.addItem("");
            jComboAddress2.addItem("");
            jComboPostal.addItem("");            
            jComboCity.addItem("");            
            jComboRegion.addItem("");
            jComboFirstName.addItem("");
            jComboLastName.addItem("");            
            jComboEmail.addItem("");
            jComboPhone.addItem("");
            jComboPhone2.addItem("");            
            jComboMaxdebt.addItem("");            
            jComboNotes.addItem("");
            jComboFax.addItem("");
            
            while (i < suppliers.getHeaderCount()) {
                jComboName.addItem(suppliers.getHeader(i));
                jComboAccountID.addItem(suppliers.getHeader(i));
                jComboSearchKey.addItem(suppliers.getHeader(i));
                jComboAddress1.addItem(suppliers.getHeader(i));
                jComboAddress2.addItem(suppliers.getHeader(i));
                jComboPostal.addItem(suppliers.getHeader(i));
                jComboCity.addItem(suppliers.getHeader(i));
                jComboRegion.addItem(suppliers.getHeader(i));
                jComboFirstName.addItem(suppliers.getHeader(i));
                jComboLastName.addItem(suppliers.getHeader(i));                
                jComboEmail.addItem(suppliers.getHeader(i));
                jComboPhone.addItem(suppliers.getHeader(i));   
                jComboPhone2.addItem(suppliers.getHeader(i));               
                jComboMaxdebt.addItem(suppliers.getHeader(i));             
                jComboNotes.addItem(suppliers.getHeader(i));
                jComboFax.addItem(suppliers.getHeader(i));
                
                Headers.add(suppliers.getHeader(i));
                ++i;
            }

            enableCheckBoxes();

            while (suppliers.readRecord()) {
                ++rowCount;
            }

            jtxtRecords.setText(Long.toString(rowCount));
                      
            suppliers.close();

        } else {
            JOptionPane.showMessageDialog(null, "Unable to locate "
                    + CSVFileName,
                    "File not found",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Enables all the selection options on the for to allow the user to
     * interact with the routine.
     *
     */
    private void enableCheckBoxes() {
        jbtnRead.setEnabled(false);
        jbtnImport.setEnabled(false);
        jbtnReset.setEnabled(true);        
        jComboAccountID.setEnabled(true);
        jComboName.setEnabled(true);
        jComboSearchKey.setEnabled(true);
        jComboAddress1.setEnabled(true);
        jComboAddress2.setEnabled(true);
        jComboPostal.setEnabled(true);        
        jComboCity.setEnabled(true);
        jComboRegion.setEnabled(true);
        jComboFirstName.setEnabled(true);
        jComboLastName.setEnabled(true);
        jComboEmail.setEnabled(true);
        jComboPhone.setEnabled(true);
        jComboPhone2.setEnabled(true);        
        jComboMaxdebt.setEnabled(true);        
        jComboNotes.setEnabled(true); 
        jComboFax.setEnabled(true);
        jCheckVisible.setEnabled(true);
    }

    /**
     * Pushes the Import process into a new thread so it doesn't interfere with
     * the UI responsiveness.
    */
    private void setWorker() {
        progress = 0;
        webPBar.setStringPainted(true);

        final SwingWorker<Integer, Integer> pbWorker;
        pbWorker = new SwingWorker<Integer, Integer>() {
 
            @Override
            protected final Integer doInBackground() throws Exception {
                while ((progress >= 0) && (progress < 100)) {
                    Thread.sleep(50);
                    this.publish(progress);
                }
                this.publish(100);
                this.done();
                return 100;
            }

            @Override
            protected final void process(final List<Integer> chunks) {
                webPBar.setValue(chunks.get(0));
                if (progress > 100) {
                    progress = 100;
                    webPBar.setString("Imported 100%");
                } else {
                webPBar.setString("Imported " + progress + "%");
            }
            }
        };
        pbWorker.execute();
    }
    
    /**
     * Runs the setWorker.
     *
     */    
    private class workProcess implements Runnable {

        @Override
        public void run() {
            try {
                ImportCsvFile(jFileName.getText());
            } catch (IOException | BasicException ex) {
                Logger.getLogger(SuppliersCSVImport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Imports the CVS File using specifications from the form.
     *
     * @param CSVFileName Name of the file (including path) to import.
     * @throws IOException If there are file reading issues.
     */
    private void ImportCsvFile(String CSVFileName) throws IOException, BasicException {

        File f = new File(CSVFileName);
        if (f.exists()) {
            webPBar.setString ( "Starting..." );
            webPBar.setVisible(true);
            jbtnImport.setEnabled(false);
            
// Read file
            suppliers = new CsvReader(CSVFileName);
            suppliers.setDelimiter(((String) jComboSeparator.getSelectedItem()).charAt(0));
            suppliers.readHeaders();

            currentRecord = 0;
            
            while (suppliers.readRecord()) {
                supplierSearchKey = suppliers.get((String) jComboSearchKey.getSelectedItem());
                supplierAccount = suppliers.get((String) jComboAccountID.getSelectedItem());
                supplierName = suppliers.get((String) jComboName.getSelectedItem());
                supplierAddress1 = suppliers.get((String) jComboAddress1.getSelectedItem());
                supplierAddress2 = suppliers.get((String) jComboAddress2.getSelectedItem());                
                supplierPostal = suppliers.get((String) jComboPostal.getSelectedItem());
                supplierCity = suppliers.get((String) jComboCity.getSelectedItem());
                supplierRegion = suppliers.get((String) jComboRegion.getSelectedItem());                
                supplierFirstName = suppliers.get((String) jComboFirstName.getSelectedItem());                
                supplierLastName = suppliers.get((String) jComboLastName.getSelectedItem());                
                supplierEmail = suppliers.get((String) jComboEmail.getSelectedItem());
                supplierPhone = suppliers.get((String) jComboPhone.getSelectedItem());
                supplierPhone2 = suppliers.get((String) jComboPhone2.getSelectedItem());                
                supplierMaxdebt = Double.parseDouble(suppliers.get((String) jComboMaxdebt.getSelectedItem()));               
                supplierNotes = suppliers.get((String) jComboNotes.getSelectedItem());
                supplierFax = suppliers.get((String) jComboFax.getSelectedItem());
                
                currentRecord++; 
                progress = currentRecord;

                if ("".equals(supplierSearchKey)
                        | "".equals(supplierName)) {

                    createSupplierCSVEntry(csvMessage, null, null);
                } else {

                    recordType = getRecord();
                    switch (recordType) {
                        case "new":
                            createSupplier("new");
                            newRecords++;
                            createSupplierCSVEntry("New supplier", null, null);
                            break;
                        case "name error":
                        case "searchkey error":
                        case "Duplicate searchkey found.":
                        case "Duplicate name found.":                        
                        case "Exception":
                            invalid++;
                            createSupplierCSVEntry(recordType, null, null);
                            break;
                        default:
                            updateRecord(recordType);
                            break;
                    }
                }
            }

        } else {
            JOptionPane.showMessageDialog(null, 
                    "Unable to locate " 
                + CSVFileName, "File not found", 
                JOptionPane.WARNING_MESSAGE);
        }

            jtxtNew.setText(Integer.toString(newRecords));
            jtxtUpdate.setText(Integer.toString(updated));
            jtxtInvalid.setText(Integer.toString(invalid));
            jtxtMissing.setText(Integer.toString(missing));
            jtxtNoChange.setText(Integer.toString(noChange));
            jtxtBad.setText(Integer.toString(bad));
            
            JOptionPane.showMessageDialog(null,
                "Import Complete",
                "Imported",
                    JOptionPane.WARNING_MESSAGE);            

//            webPBar.setString("Imported " + progress);                
            progress = 100;
            webPBar.setValue(progress);
            webPBar.setString("Imported" + progress); 
//        }
    }

    
    /**
     * testString for validity as a number
     *
     * @param testString the string to be checked
     * @return True if a real number False if not
     */
    private Boolean validateNumber(String testString) {
        try {
            Double res = Double.parseDouble(testString);
            return (true);
        } catch (NumberFormatException e) {
            return (false);
        }
    }    

    /**
     * Updated the record in the database with the new prices and category if
     * needed.
     *
     * @param cID Unique supplier id of the record to be updated It then creates
     * an updated record for the supplier, subject to the ???? be different
     *
     */
    private void updateRecord(String cID) throws BasicException {
//        custInfo = new supplierInfoExt();
            suppInfo = m_dlSales.getSupplierInfo(cID);            
                createSupplier("update");
                noChange++;
    }

    /**
     * Gets the title of the current panel
     *
     * @return The name of the panel
     */
    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.SupplierCSVImport");
    }

    /**
     * Returns this object
     * @return 
     */
    @Override
    public JComponent getComponent() {
        return this;
    }

    /**
     * Set CSV field and line separator
     * @throws com.openbravo.basic.BasicException
     */
    @Override
    public void activate() throws BasicException {

        jComboSeparator.removeAllItems();
        jComboSeparator.addItem(",");
        jComboSeparator.addItem(";");
        jComboSeparator.addItem("~");
        jComboSeparator.addItem("^");

    }
    
    /**
     * Resets all the form fields
     */
    public void resetFields() {

        jComboAccountID.removeAllItems();
        jComboAccountID.setEnabled(false);

        jComboName.removeAllItems();
        jComboName.setEnabled(false);

        jComboSearchKey.removeAllItems();
        jComboSearchKey.setEnabled(false);

        jComboAddress1.removeAllItems();
        jComboAddress1.setEnabled(false);

        jComboAddress2.removeAllItems();
        jComboAddress2.setEnabled(false);

        jComboCity.removeAllItems();        
        jComboCity.setEnabled(false);

        jComboRegion.removeAllItems();
        jComboRegion.setEnabled(false);

        jComboPostal.removeAllItems();        
        jComboPostal.setEnabled(false);
        
        jComboFirstName.removeAllItems();        
        jComboFirstName.setEnabled(false);        

        jComboLastName.removeAllItems();        
        jComboLastName.setEnabled(false);
        
        jComboEmail.removeAllItems();
        jComboEmail.setEnabled(false);

        jComboPhone.removeAllItems();
        jComboPhone.setEnabled(false);   
        
        jComboPhone2.removeAllItems();
        jComboPhone2.setEnabled(false);   
        
        jComboMaxdebt.removeAllItems();
        jComboMaxdebt.setEnabled(false);
        
        jComboNotes.removeAllItems();
        jComboNotes.setEnabled(false);
        
        jComboFax.removeAllItems();
        jComboFax.setEnabled(false);

        jCheckVisible.setSelected(false);
        jCheckVisible.setEnabled(true);
        
        jbtnImport.setEnabled(false);
        jbtnReset.setEnabled(true);        
        jbtnRead.setEnabled(false);

        jFileName.setText(null);
        csvFileName = "";

// clear Status area fields        
        jtxtNew.setText("");
        jtxtUpdate.setText("");
        jtxtInvalid.setText("");
        jtxtMissing.setText("");
        jtxtNoChange.setText("");
        jtxtRecords.setText("");
        jtxtBad.setText("");
        
        progress = 0;

        Headers.clear();
    }

    /**
     * Checks the field mappings to ensure all compulsory fields have been
     * completed to allow import to proceed
     */
    public void checkFieldMapping() {
        if (jComboSearchKey.getSelectedItem() != "" 
                & jComboName.getSelectedItem() != "") {             
            jbtnImport.setEnabled(true);
            jbtnReset.setEnabled(true);            
        } else {
            jbtnImport.setEnabled(false);
            jbtnReset.setEnabled(false);             
        }
    }

    /**
     * Deactivates and resets all form fields.
     *
     * @return
     */
    @Override
    public boolean deactivate() {
        resetFields();
        return (true);
    }  

    /**
     *
     * @param cType
     */
    public void createSupplier(String cType) {

        Object[] mysupp = new Object[26];
        if("new".equals(cType)) {
            mysupp[0] = UUID.randomUUID().toString();
        } else {
            mysupp[0] = suppInfo.getID();            
        }                                                                       // id string
        mysupp[1] = supplierSearchKey;                                          // searchkey String     
        mysupp[2] = supplierAccount;                                            // taxid string
        mysupp[3] = supplierName;                                               // name string                     
        mysupp[4] = supplierMaxdebt;                                            // maxdebt Double                
        mysupp[5] = supplierAddress1;                                           // address1 string
        mysupp[6] = supplierAddress2;                                           // address2 string
        mysupp[7] = supplierPostal;                                             // postal string
        mysupp[8] = supplierCity;                                              // city string
        mysupp[9] = supplierRegion;                                            // region string
        mysupp[10] = null;                                                      // country string
        mysupp[11] = supplierFirstName;                                         // firstname string        
        mysupp[12] = supplierLastName;                                          // lastname string        
        mysupp[13] = supplierEmail;                                             // email string        
        mysupp[14] = supplierPhone;                                             // phone string
        mysupp[15] = supplierPhone2;                                            // phone2 string
        mysupp[16] = supplierFax;                                               // fax string
        mysupp[17] = supplierNotes;                                             // notes string        
        mysupp[18] = jCheckVisible.isSelected();                                // visible flag
        mysupp[19] = null;                                                      // curdate string
        mysupp[20] = 0.0;                                                       // curdebt double   
        mysupp[21] = null;                                                     // vatid
      
        
        try {
            if ("new".equals(cType)) {
                spr.insertData(mysupp);
                webPBar.setString("Adding record " + progress); 
            } else {
                spr.updateData(mysupp);
                webPBar.setString("Updating record " + progress);                
            }
        } catch (BasicException ex) {
            Logger.getLogger(SuppliersCSVImport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Insert file row read and adds to import table log
     * @param csvError
     * @param searchKey
     * @param Name
     */
    public void createSupplierCSVEntry(String csvError, String searchKey, String Name) {

        Object[] mysupp = new Object[5];
        mysupp[0] = UUID.randomUUID().toString();                               // ID string
        mysupp[1] = Integer.toString(currentRecord);                            // Record number
        mysupp[2] = csvError;                                                   // Error description
        mysupp[3] = supplierSearchKey;                                          // SearchKey String        
        mysupp[4] = supplierName;                                               // Name string        
                                                     
        try {
            m_dlSystem.execSupplierAddCSVEntry(mysupp);
        } catch (BasicException ex) {
            Logger.getLogger(SuppliersCSVImport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return
     */
    public String getRecord() {
        // Get record type using using DataLogicSystem
        Object[] mysupp = new Object[2];
        mysupp[0] = supplierSearchKey;
        mysupp[1] = supplierName;

        try {
            return (m_dlSystem.getSupplierRecordType(mysupp));
        } catch (BasicException ex) {
            Logger.getLogger(SuppliersCSVImport.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Exception";
    }
 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooserPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jFileName = new javax.swing.JTextField();
        jbtnFileChoose = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jbtnRead = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jlblRecords = new javax.swing.JLabel();
        jlblNew = new javax.swing.JLabel();
        jlblInvalid = new javax.swing.JLabel();
        jlblUpdates = new javax.swing.JLabel();
        jlblMissing = new javax.swing.JLabel();
        jlblBad = new javax.swing.JLabel();
        jlblNotChanged = new javax.swing.JLabel();
        jtxtRecords = new javax.swing.JTextField();
        jtxtNew = new javax.swing.JTextField();
        jtxtInvalid = new javax.swing.JTextField();
        jtxtUpdate = new javax.swing.JTextField();
        jtxtMissing = new javax.swing.JTextField();
        jtxtBad = new javax.swing.JTextField();
        jtxtNoChange = new javax.swing.JTextField();
        jComboSeparator = new javax.swing.JComboBox();
        webPBar = new com.alee.laf.progressbar.WebProgressBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jComboSearchKey = new javax.swing.JComboBox();
        jComboName = new javax.swing.JComboBox();
        jComboAccountID = new javax.swing.JComboBox();
        jComboAddress1 = new javax.swing.JComboBox();
        jComboAddress2 = new javax.swing.JComboBox();
        jComboCity = new javax.swing.JComboBox();
        jComboRegion = new javax.swing.JComboBox();
        jComboPostal = new javax.swing.JComboBox();
        jComboFirstName = new javax.swing.JComboBox();
        jComboLastName = new javax.swing.JComboBox();
        jComboEmail = new javax.swing.JComboBox();
        jComboPhone = new javax.swing.JComboBox();
        jComboPhone2 = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jComboMaxdebt = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jComboNotes = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jComboFax = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jCheckVisible = new javax.swing.JCheckBox();
        jbtnReset = new javax.swing.JButton();
        jbtnImport = new javax.swing.JButton();

        setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(750, 500));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pos_messages"); // NOI18N
        jLabel1.setText(bundle.getString("label.csvfile")); // NOI18N
        jLabel1.setPreferredSize(new java.awt.Dimension(100, 30));

        jFileName.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jFileName.setPreferredSize(new java.awt.Dimension(400, 30));
        jFileName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileNameActionPerformed(evt);
            }
        });

        jbtnFileChoose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/fileopen.png"))); // NOI18N
        jbtnFileChoose.setMaximumSize(new java.awt.Dimension(64, 32));
        jbtnFileChoose.setMinimumSize(new java.awt.Dimension(64, 32));
        jbtnFileChoose.setPreferredSize(new java.awt.Dimension(80, 45));
        jbtnFileChoose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnFileChooseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jFileChooserPanelLayout = new javax.swing.GroupLayout(jFileChooserPanel);
        jFileChooserPanel.setLayout(jFileChooserPanelLayout);
        jFileChooserPanelLayout.setHorizontalGroup(
            jFileChooserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFileChooserPanelLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jFileName, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnFileChoose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(120, 120, 120))
        );
        jFileChooserPanelLayout.setVerticalGroup(
            jFileChooserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFileChooserPanelLayout.createSequentialGroup()
                .addGroup(jFileChooserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFileName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addComponent(jbtnFileChoose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel18.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel18.setText(bundle.getString("label.csvdelimit")); // NOI18N
        jLabel18.setPreferredSize(new java.awt.Dimension(100, 30));

        jbtnRead.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnRead.setText(bundle.getString("label.csvread")); // NOI18N
        jbtnRead.setEnabled(false);
        jbtnRead.setPreferredSize(new java.awt.Dimension(110, 45));
        jbtnRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnReadActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), bundle.getString("title.CSVImport"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        jlblRecords.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jlblRecords.setText(bundle.getString("label.csvrecordsfound")); // NOI18N
        jlblRecords.setPreferredSize(new java.awt.Dimension(150, 30));

        jlblNew.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jlblNew.setText(bundle.getString("label.csvnewcustomers")); // NOI18N
        jlblNew.setMaximumSize(new java.awt.Dimension(77, 14));
        jlblNew.setMinimumSize(new java.awt.Dimension(77, 14));
        jlblNew.setPreferredSize(new java.awt.Dimension(150, 30));

        jlblInvalid.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jlblInvalid.setText(bundle.getString("label.invalidcustomers")); // NOI18N
        jlblInvalid.setPreferredSize(new java.awt.Dimension(150, 30));

        jlblUpdates.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jlblUpdates.setText(bundle.getString("label.customerupdated")); // NOI18N
        jlblUpdates.setPreferredSize(new java.awt.Dimension(150, 30));

        jlblMissing.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jlblMissing.setText(bundle.getString("label.csvmissing")); // NOI18N
        jlblMissing.setPreferredSize(new java.awt.Dimension(150, 30));

        jlblBad.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jlblBad.setText(bundle.getString("label.csvbad")); // NOI18N
        jlblBad.setPreferredSize(new java.awt.Dimension(150, 30));

        jlblNotChanged.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jlblNotChanged.setText(bundle.getString("label.cvsnotchanged")); // NOI18N
        jlblNotChanged.setPreferredSize(new java.awt.Dimension(150, 30));

        jtxtRecords.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jtxtRecords.setForeground(new java.awt.Color(102, 102, 102));
        jtxtRecords.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtRecords.setBorder(null);
        jtxtRecords.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jtxtRecords.setEnabled(false);
        jtxtRecords.setPreferredSize(new java.awt.Dimension(100, 30));

        jtxtNew.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jtxtNew.setForeground(new java.awt.Color(102, 102, 102));
        jtxtNew.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtNew.setBorder(null);
        jtxtNew.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jtxtNew.setEnabled(false);
        jtxtNew.setPreferredSize(new java.awt.Dimension(100, 30));

        jtxtInvalid.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jtxtInvalid.setForeground(new java.awt.Color(102, 102, 102));
        jtxtInvalid.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtInvalid.setBorder(null);
        jtxtInvalid.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jtxtInvalid.setEnabled(false);
        jtxtInvalid.setPreferredSize(new java.awt.Dimension(100, 30));

        jtxtUpdate.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jtxtUpdate.setForeground(new java.awt.Color(102, 102, 102));
        jtxtUpdate.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtUpdate.setBorder(null);
        jtxtUpdate.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jtxtUpdate.setEnabled(false);
        jtxtUpdate.setPreferredSize(new java.awt.Dimension(100, 30));

        jtxtMissing.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jtxtMissing.setForeground(new java.awt.Color(102, 102, 102));
        jtxtMissing.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtMissing.setBorder(null);
        jtxtMissing.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jtxtMissing.setEnabled(false);
        jtxtMissing.setPreferredSize(new java.awt.Dimension(100, 30));

        jtxtBad.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jtxtBad.setForeground(new java.awt.Color(255, 0, 204));
        jtxtBad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtBad.setBorder(null);
        jtxtBad.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jtxtBad.setEnabled(false);
        jtxtBad.setPreferredSize(new java.awt.Dimension(100, 30));

        jtxtNoChange.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jtxtNoChange.setForeground(new java.awt.Color(102, 102, 102));
        jtxtNoChange.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtNoChange.setBorder(null);
        jtxtNoChange.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jtxtNoChange.setEnabled(false);
        jtxtNoChange.setPreferredSize(new java.awt.Dimension(100, 30));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jlblRecords, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtRecords, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jlblNotChanged, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlblInvalid, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlblUpdates, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlblMissing, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlblBad, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlblNew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtInvalid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtMissing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtBad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtNoChange, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblRecords, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtRecords, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblInvalid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtInvalid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblUpdates, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblMissing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtMissing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblBad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtBad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblNotChanged, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtNoChange, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jComboSeparator.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jComboSeparator.setPreferredSize(new java.awt.Dimension(50, 30));

        webPBar.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        webPBar.setPreferredSize(new java.awt.Dimension(240, 30));

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setAutoscrolls(true);

        jPanel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(430, 650));

        jComboSearchKey.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboSearchKey.setEnabled(false);
        jComboSearchKey.setMinimumSize(new java.awt.Dimension(32, 25));
        jComboSearchKey.setPreferredSize(new java.awt.Dimension(300, 30));
        jComboSearchKey.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboSearchKeyItemStateChanged(evt);
            }
        });
        jComboSearchKey.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jComboSearchKeyFocusGained(evt);
            }
        });

        jComboName.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboName.setEnabled(false);
        jComboName.setMinimumSize(new java.awt.Dimension(32, 25));
        jComboName.setPreferredSize(new java.awt.Dimension(300, 30));
        jComboName.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboNameItemStateChanged(evt);
            }
        });
        jComboName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jComboNameFocusGained(evt);
            }
        });

        jComboAccountID.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboAccountID.setEnabled(false);
        jComboAccountID.setMinimumSize(new java.awt.Dimension(32, 25));
        jComboAccountID.setPreferredSize(new java.awt.Dimension(300, 30));
        jComboAccountID.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboAccountIDItemStateChanged(evt);
            }
        });
        jComboAccountID.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jComboAccountIDFocusGained(evt);
            }
        });

        jComboAddress1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboAddress1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "" }));
        jComboAddress1.setSelectedIndex(-1);
        jComboAddress1.setEnabled(false);
        jComboAddress1.setMinimumSize(new java.awt.Dimension(32, 25));
        jComboAddress1.setPreferredSize(new java.awt.Dimension(300, 30));
        jComboAddress1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboAddress1ItemStateChanged(evt);
            }
        });
        jComboAddress1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jComboAddress1FocusGained(evt);
            }
        });

        jComboAddress2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboAddress2.setEnabled(false);
        jComboAddress2.setMinimumSize(new java.awt.Dimension(32, 25));
        jComboAddress2.setPreferredSize(new java.awt.Dimension(300, 30));
        jComboAddress2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboAddress2ItemStateChanged(evt);
            }
        });
        jComboAddress2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jComboAddress2FocusGained(evt);
            }
        });

        jComboCity.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboCity.setEnabled(false);
        jComboCity.setPreferredSize(new java.awt.Dimension(300, 30));
        jComboCity.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboCityItemStateChanged(evt);
            }
        });
        jComboCity.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jComboCityFocusGained(evt);
            }
        });

        jComboRegion.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboRegion.setEnabled(false);
        jComboRegion.setMinimumSize(new java.awt.Dimension(32, 25));
        jComboRegion.setName(""); // NOI18N
        jComboRegion.setPreferredSize(new java.awt.Dimension(300, 30));
        jComboRegion.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboRegionItemStateChanged(evt);
            }
        });
        jComboRegion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jComboRegionFocusGained(evt);
            }
        });

        jComboPostal.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboPostal.setEnabled(false);
        jComboPostal.setMinimumSize(new java.awt.Dimension(32, 25));
        jComboPostal.setName(""); // NOI18N
        jComboPostal.setPreferredSize(new java.awt.Dimension(300, 30));
        jComboPostal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboPostalItemStateChanged(evt);
            }
        });
        jComboPostal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jComboPostalFocusGained(evt);
            }
        });

        jComboFirstName.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboFirstName.setEnabled(false);
        jComboFirstName.setMinimumSize(new java.awt.Dimension(32, 25));
        jComboFirstName.setPreferredSize(new java.awt.Dimension(300, 30));
        jComboFirstName.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboFirstNameItemStateChanged(evt);
            }
        });
        jComboFirstName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jComboFirstNameFocusGained(evt);
            }
        });

        jComboLastName.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboLastName.setEnabled(false);
        jComboLastName.setMinimumSize(new java.awt.Dimension(32, 25));
        jComboLastName.setPreferredSize(new java.awt.Dimension(300, 30));
        jComboLastName.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboLastNameItemStateChanged(evt);
            }
        });
        jComboLastName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jComboLastNameFocusGained(evt);
            }
        });

        jComboEmail.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboEmail.setEnabled(false);
        jComboEmail.setMinimumSize(new java.awt.Dimension(32, 25));
        jComboEmail.setName(""); // NOI18N
        jComboEmail.setPreferredSize(new java.awt.Dimension(300, 30));
        jComboEmail.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboEmailItemStateChanged(evt);
            }
        });
        jComboEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jComboEmailFocusGained(evt);
            }
        });

        jComboPhone.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboPhone.setEnabled(false);
        jComboPhone.setMinimumSize(new java.awt.Dimension(32, 25));
        jComboPhone.setPreferredSize(new java.awt.Dimension(300, 30));
        jComboPhone.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboPhoneItemStateChanged(evt);
            }
        });
        jComboPhone.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jComboPhoneFocusGained(evt);
            }
        });

        jComboPhone2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboPhone2.setEnabled(false);
        jComboPhone2.setMinimumSize(new java.awt.Dimension(32, 25));
        jComboPhone2.setPreferredSize(new java.awt.Dimension(300, 30));
        jComboPhone2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboPhone2ItemStateChanged(evt);
            }
        });
        jComboPhone2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jComboPhone2FocusGained(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText(bundle.getString("label.taxid")); // NOI18N
        jLabel3.setPreferredSize(new java.awt.Dimension(100, 30));

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText(bundle.getString("label.searchkeym")); // NOI18N
        jLabel4.setPreferredSize(new java.awt.Dimension(100, 30));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText(bundle.getString("label.namem")); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(100, 30));

        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setText(bundle.getString("label.address")); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(100, 30));

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setText(bundle.getString("label.region")); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(100, 30));

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText(bundle.getString("label.city")); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(100, 30));

        jLabel20.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel20.setText(bundle.getString("label.address2")); // NOI18N
        jLabel20.setPreferredSize(new java.awt.Dimension(100, 30));

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText(bundle.getString("label.postal")); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(100, 30));

        jLabel21.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel21.setText(bundle.getString("label.email")); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(100, 30));

        jLabel22.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel22.setText(bundle.getString("label.phone")); // NOI18N
        jLabel22.setPreferredSize(new java.awt.Dimension(100, 30));

        jLabel23.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel23.setText(bundle.getString("label.phone2")); // NOI18N
        jLabel23.setPreferredSize(new java.awt.Dimension(100, 30));

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText(bundle.getString("label.firstname")); // NOI18N
        jLabel9.setPreferredSize(new java.awt.Dimension(100, 30));

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText(bundle.getString("label.lastname")); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(100, 30));

        jComboMaxdebt.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboMaxdebt.setEnabled(false);
        jComboMaxdebt.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboMaxdebtItemStateChanged(evt);
            }
        });
        jComboMaxdebt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jComboMaxdebtFocusGained(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setText("Deuda Máxima");

        jLabel15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel15.setText("Nota");

        jComboNotes.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboNotes.setEnabled(false);
        jComboNotes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboNotesItemStateChanged(evt);
            }
        });
        jComboNotes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jComboNotesFocusGained(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel16.setText("Fax");

        jComboFax.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboFax.setEnabled(false);
        jComboFax.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboFaxItemStateChanged(evt);
            }
        });
        jComboFax.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jComboFaxFocusGained(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboSearchKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboAccountID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboAddress1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboAddress2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboFax, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboNotes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboRegion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboPostal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboMaxdebt, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboPhone2, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboSearchKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboAccountID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboAddress1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboAddress2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboRegion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboPostal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboPhone2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboMaxdebt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jComboNotes, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jComboFax, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addGap(249, 249, 249))
        );

        jScrollPane1.setViewportView(jPanel1);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText(bundle.getString("label.visible")); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(100, 30));

        jCheckVisible.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jCheckVisible.setSelected(true);
        jCheckVisible.setEnabled(false);
        jCheckVisible.setPreferredSize(new java.awt.Dimension(30, 30));

        jbtnReset.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jbtnReset.setText(bundle.getString("button.reset")); // NOI18N
        jbtnReset.setPreferredSize(new java.awt.Dimension(110, 45));
        jbtnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnResetActionPerformed(evt);
            }
        });

        jbtnImport.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jbtnImport.setText(bundle.getString("label.csvimpostbtn")); // NOI18N
        jbtnImport.setEnabled(false);
        jbtnImport.setPreferredSize(new java.awt.Dimension(110, 45));
        jbtnImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnImportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFileChooserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(122, 122, 122)
                                .addComponent(jbtnRead, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(webPBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCheckVisible, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jbtnReset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(jbtnImport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jFileChooserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbtnRead, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(webPBar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckVisible, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbtnImport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnReset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(258, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnReadActionPerformed
        try {
            GetheadersFromFile(jFileName.getText());
                webPBar.setString("Source file Header OK");            
        } catch (IOException ex) {
            Logger.getLogger(SuppliersCSVImport.class.getName()).log(Level.SEVERE, null, ex);
                webPBar.setString("Source file Header error!"); 
        }
    }//GEN-LAST:event_jbtnReadActionPerformed

    private void jFileNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileNameActionPerformed
        jbtnImport.setEnabled(false);
        jbtnRead.setEnabled(true);
    }//GEN-LAST:event_jFileNameActionPerformed

    private void jbtnFileChooseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnFileChooseActionPerformed
        resetFields();
        setWorker();        

        JFileChooser chooser = new JFileChooser(last_folder == null ? "C:\\" : last_folder);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("csv files", "csv");
        chooser.setFileFilter(filter);
        chooser.showOpenDialog(null);
        File csvFile = chooser.getSelectedFile();
      
        if (csvFile == null) {
            return;
        }

        File current_folder = chooser.getCurrentDirectory();

        if (last_folder == null 
                || !last_folder.equals(current_folder.getAbsolutePath())) {
            AppConfig CSVConfig = new AppConfig(config_file);
            CSVConfig.load();
            CSVConfig.setProperty("CSV.last_folder"
                    , current_folder.getAbsolutePath());
            last_folder = current_folder.getAbsolutePath();
            try {
                CSVConfig.save();
            } catch (IOException ex) {
                Logger.getLogger(SuppliersCSVImport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        String csv = csvFile.getName();
        if (!(csv.trim().equals(""))) {
            csvFileName = csvFile.getAbsolutePath();
            jFileName.setText(csvFileName);
        }
    }//GEN-LAST:event_jbtnFileChooseActionPerformed

    private void jComboPostalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboPostalFocusGained
        jComboPostal.removeAllItems();
        int i = 1;
        jComboPostal.addItem("");

        while (i < Headers.size()) {
            if ((Headers.get(i) 
                != jComboSearchKey.getSelectedItem())
                & (Headers.get(i) != jComboAccountID.getSelectedItem())
                & (Headers.get(i) != jComboName.getSelectedItem())                    
                & (Headers.get(i) != jComboAddress1.getSelectedItem())
                & (Headers.get(i) != jComboAddress2.getSelectedItem())
//                & (Headers.get(i) != jComboPostal.getSelectedItem())                    
                & (Headers.get(i) != jComboCity.getSelectedItem())
                & (Headers.get(i) != jComboRegion.getSelectedItem())
                & (Headers.get(i) != jComboFirstName.getSelectedItem())
                & (Headers.get(i) != jComboLastName.getSelectedItem())
                & (Headers.get(i) != jComboEmail.getSelectedItem())
                & (Headers.get(i) != jComboPhone.getSelectedItem())
                & (Headers.get(i) != jComboPhone2.getSelectedItem())                
                & (Headers.get(i) != jComboMaxdebt.getSelectedItem())              
                & (Headers.get(i) != jComboNotes.getSelectedItem())
                & (Headers.get(i) != jComboFax.getSelectedItem())                    
                ) 
            {

                jComboPostal.addItem(Headers.get(i));
            }
            ++i;
        }
    }//GEN-LAST:event_jComboPostalFocusGained

    private void jComboPostalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboPostalItemStateChanged

        checkFieldMapping();
        
    }//GEN-LAST:event_jComboPostalItemStateChanged

    private void jComboLastNameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboLastNameItemStateChanged

        checkFieldMapping();
        
    }//GEN-LAST:event_jComboLastNameItemStateChanged

    private void jComboFirstNameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboFirstNameItemStateChanged

        checkFieldMapping();
        
    }//GEN-LAST:event_jComboFirstNameItemStateChanged

    private void jComboPhone2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboPhone2ItemStateChanged

        checkFieldMapping();
        
    }//GEN-LAST:event_jComboPhone2ItemStateChanged

    private void jComboPhoneItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboPhoneItemStateChanged

        checkFieldMapping();
        
    }//GEN-LAST:event_jComboPhoneItemStateChanged

    private void jComboEmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboEmailFocusGained
        jComboEmail.removeAllItems();
        int i = 1;
        jComboEmail.addItem("");

        while (i < Headers.size()) {
            if ((Headers.get(i) 
                != jComboSearchKey.getSelectedItem())
                & (Headers.get(i) != jComboAccountID.getSelectedItem())
                & (Headers.get(i) != jComboName.getSelectedItem())
                & (Headers.get(i) != jComboAddress1.getSelectedItem())
                & (Headers.get(i) != jComboAddress2.getSelectedItem())
                & (Headers.get(i) != jComboPostal.getSelectedItem())                    
                & (Headers.get(i) != jComboCity.getSelectedItem())
                & (Headers.get(i) != jComboRegion.getSelectedItem())
                & (Headers.get(i) != jComboFirstName.getSelectedItem())
                & (Headers.get(i) != jComboLastName.getSelectedItem())
//                & (Headers.get(i) != jComboEmail.getSelectedItem())
                & (Headers.get(i) != jComboPhone.getSelectedItem())
                & (Headers.get(i) != jComboPhone2.getSelectedItem())             
                & (Headers.get(i) != jComboMaxdebt.getSelectedItem())      
                & (Headers.get(i) != jComboNotes.getSelectedItem())
                & (Headers.get(i) != jComboFax.getSelectedItem())                    
                ) 
            {

                jComboEmail.addItem(Headers.get(i));
            }
            ++i;
        }
    }//GEN-LAST:event_jComboEmailFocusGained

    private void jComboEmailItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboEmailItemStateChanged

        checkFieldMapping();

    }//GEN-LAST:event_jComboEmailItemStateChanged

    private void jbtnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnResetActionPerformed
        resetFields();
        progress = -1;
        webPBar.setString("Waiting...");
    }//GEN-LAST:event_jbtnResetActionPerformed

    private void jbtnImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnImportActionPerformed

        jbtnImport.setEnabled(false);

        workProcess work = new workProcess();
        Thread thread2 = new Thread(work);
        thread2.start();
    }//GEN-LAST:event_jbtnImportActionPerformed

    private void jComboRegionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboRegionFocusGained
        jComboRegion.removeAllItems();
        int i = 1;
        jComboRegion.addItem("");

        while (i < Headers.size()) {
            if ((Headers.get(i) 
                != jComboSearchKey.getSelectedItem())
                & (Headers.get(i) != jComboAccountID.getSelectedItem())
                & (Headers.get(i) != jComboName.getSelectedItem())
                & (Headers.get(i) != jComboAddress1.getSelectedItem())
                & (Headers.get(i) != jComboAddress2.getSelectedItem())
                & (Headers.get(i) != jComboPostal.getSelectedItem())                    
                & (Headers.get(i) != jComboCity.getSelectedItem())
//                & (Headers.get(i) != jComboRegion.getSelectedItem())
                & (Headers.get(i) != jComboFirstName.getSelectedItem())
                & (Headers.get(i) != jComboLastName.getSelectedItem())
                & (Headers.get(i) != jComboEmail.getSelectedItem())
                & (Headers.get(i) != jComboPhone.getSelectedItem())
                & (Headers.get(i) != jComboPhone2.getSelectedItem())             
                & (Headers.get(i) != jComboMaxdebt.getSelectedItem())         
                & (Headers.get(i) != jComboNotes.getSelectedItem())
                & (Headers.get(i) != jComboFax.getSelectedItem())                    
                ) 
            {

                jComboRegion.addItem(Headers.get(i));
            }
            ++i;
        }
    }//GEN-LAST:event_jComboRegionFocusGained

    private void jComboRegionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboRegionItemStateChanged

        checkFieldMapping();
        
    }//GEN-LAST:event_jComboRegionItemStateChanged

    private void jComboCityFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboCityFocusGained
        jComboCity.removeAllItems();
        int i = 1;
        jComboCity.addItem("");

        while (i < Headers.size()) {
            if ((Headers.get(i) 
                != jComboSearchKey.getSelectedItem())
                & (Headers.get(i) != jComboAccountID.getSelectedItem())
                & (Headers.get(i) != jComboName.getSelectedItem())                    
                & (Headers.get(i) != jComboAddress1.getSelectedItem())
                & (Headers.get(i) != jComboAddress2.getSelectedItem())
                & (Headers.get(i) != jComboPostal.getSelectedItem())                    
//                & (Headers.get(i) != jComboCity.getSelectedItem())
                & (Headers.get(i) != jComboRegion.getSelectedItem())
                & (Headers.get(i) != jComboFirstName.getSelectedItem())
                & (Headers.get(i) != jComboLastName.getSelectedItem())
                & (Headers.get(i) != jComboEmail.getSelectedItem())
                & (Headers.get(i) != jComboPhone.getSelectedItem())
                & (Headers.get(i) != jComboPhone2.getSelectedItem())            
                & (Headers.get(i) != jComboMaxdebt.getSelectedItem())    
                & (Headers.get(i) != jComboNotes.getSelectedItem())
                & (Headers.get(i) != jComboFax.getSelectedItem())                    
                ) 
            {

                jComboCity.addItem(Headers.get(i));
            }
            ++i;
        }
    }//GEN-LAST:event_jComboCityFocusGained

    private void jComboCityItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboCityItemStateChanged

        checkFieldMapping();
        
    }//GEN-LAST:event_jComboCityItemStateChanged

    private void jComboAddress2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboAddress2FocusGained
        jComboAddress2.removeAllItems();
        int i = 1;
        jComboAddress2.addItem("");

        while (i < Headers.size()) {
            if ((Headers.get(i) 
                != jComboSearchKey.getSelectedItem())
                & (Headers.get(i) != jComboAccountID.getSelectedItem())
                & (Headers.get(i) != jComboName.getSelectedItem())
                & (Headers.get(i) != jComboAddress1.getSelectedItem())
//                & (Headers.get(i) != jComboAddress2.getSelectedItem())
                & (Headers.get(i) != jComboPostal.getSelectedItem())                    
                & (Headers.get(i) != jComboCity.getSelectedItem())
                & (Headers.get(i) != jComboRegion.getSelectedItem())
                & (Headers.get(i) != jComboFirstName.getSelectedItem())
                & (Headers.get(i) != jComboLastName.getSelectedItem())
                & (Headers.get(i) != jComboEmail.getSelectedItem())
                & (Headers.get(i) != jComboPhone.getSelectedItem())
                & (Headers.get(i) != jComboPhone2.getSelectedItem())          
                & (Headers.get(i) != jComboMaxdebt.getSelectedItem())            
                & (Headers.get(i) != jComboNotes.getSelectedItem())
                & (Headers.get(i) != jComboFax.getSelectedItem())                    
                ) 
            {

                jComboAddress2.addItem(Headers.get(i));
            }
            ++i;
        }
    }//GEN-LAST:event_jComboAddress2FocusGained

    private void jComboAddress2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboAddress2ItemStateChanged

        checkFieldMapping();
        
    }//GEN-LAST:event_jComboAddress2ItemStateChanged

    private void jComboAddress1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboAddress1FocusGained
        jComboAddress1.removeAllItems();
        int i = 1;
        jComboAddress1.addItem("");

        while (i < Headers.size()) {
            if ((Headers.get(i) 
                != jComboSearchKey.getSelectedItem())
                & (Headers.get(i) != jComboAccountID.getSelectedItem())
                & (Headers.get(i) != jComboName.getSelectedItem())
//                & (Headers.get(i) != jComboAddress1.getSelectedItem())
                & (Headers.get(i) != jComboAddress2.getSelectedItem())
                & (Headers.get(i) != jComboPostal.getSelectedItem())                    
                & (Headers.get(i) != jComboCity.getSelectedItem())
                & (Headers.get(i) != jComboRegion.getSelectedItem())
                & (Headers.get(i) != jComboFirstName.getSelectedItem())
                & (Headers.get(i) != jComboLastName.getSelectedItem())
                & (Headers.get(i) != jComboEmail.getSelectedItem())
                & (Headers.get(i) != jComboPhone.getSelectedItem())
                & (Headers.get(i) != jComboPhone2.getSelectedItem())           
                & (Headers.get(i) != jComboMaxdebt.getSelectedItem())          
                & (Headers.get(i) != jComboNotes.getSelectedItem())
                & (Headers.get(i) != jComboFax.getSelectedItem())                    
                ) 
            {

                jComboAddress1.addItem(Headers.get(i));
            }
            ++i;
        }
    }//GEN-LAST:event_jComboAddress1FocusGained

    private void jComboAddress1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboAddress1ItemStateChanged

        checkFieldMapping();
        
    }//GEN-LAST:event_jComboAddress1ItemStateChanged

    private void jComboNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboNameFocusGained
        jComboName.removeAllItems();
        int i = 1;
        jComboName.addItem("");

        while (i < Headers.size()) {
            if ((Headers.get(i) 
                != jComboSearchKey.getSelectedItem())
                & (Headers.get(i) != jComboAccountID.getSelectedItem())
//                & (Headers.get(i) != jComboName.getSelectedItem())
                & (Headers.get(i) != jComboAddress1.getSelectedItem())
                & (Headers.get(i) != jComboAddress2.getSelectedItem())
                & (Headers.get(i) != jComboPostal.getSelectedItem())                    
                & (Headers.get(i) != jComboCity.getSelectedItem())
                & (Headers.get(i) != jComboRegion.getSelectedItem())
                & (Headers.get(i) != jComboFirstName.getSelectedItem())
                & (Headers.get(i) != jComboLastName.getSelectedItem())
                & (Headers.get(i) != jComboEmail.getSelectedItem())
                & (Headers.get(i) != jComboPhone.getSelectedItem())
                & (Headers.get(i) != jComboPhone2.getSelectedItem())            
                & (Headers.get(i) != jComboMaxdebt.getSelectedItem())         
                & (Headers.get(i) != jComboNotes.getSelectedItem())
                & (Headers.get(i) != jComboFax.getSelectedItem())                    
                ) 
            {

                jComboName.addItem(Headers.get(i));
            }
            ++i;
        }
    }//GEN-LAST:event_jComboNameFocusGained

    private void jComboNameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboNameItemStateChanged

        checkFieldMapping();
        
    }//GEN-LAST:event_jComboNameItemStateChanged

    private void jComboSearchKeyFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboSearchKeyFocusGained
        jComboName.removeAllItems();
        int i = 1;
        jComboName.addItem("");

        while (i < Headers.size()) {
            if ((Headers.get(i) 
//                != jComboSearchKey.getSelectedItem())
                != jComboAccountID.getSelectedItem())
                & (Headers.get(i) != jComboName.getSelectedItem())
                & (Headers.get(i) != jComboAddress1.getSelectedItem())
                & (Headers.get(i) != jComboAddress2.getSelectedItem())
                & (Headers.get(i) != jComboPostal.getSelectedItem())                    
                & (Headers.get(i) != jComboCity.getSelectedItem())
                & (Headers.get(i) != jComboRegion.getSelectedItem())
                & (Headers.get(i) != jComboFirstName.getSelectedItem())
                & (Headers.get(i) != jComboLastName.getSelectedItem())
                & (Headers.get(i) != jComboEmail.getSelectedItem())
                & (Headers.get(i) != jComboPhone.getSelectedItem())
                & (Headers.get(i) != jComboPhone2.getSelectedItem())           
                & (Headers.get(i) != jComboMaxdebt.getSelectedItem())          
                & (Headers.get(i) != jComboNotes.getSelectedItem())
                & (Headers.get(i) != jComboFax.getSelectedItem())                    
                ) 
            {

                jComboSearchKey.addItem(Headers.get(i));
            }
            ++i;
        }
    }//GEN-LAST:event_jComboSearchKeyFocusGained

    private void jComboSearchKeyItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboSearchKeyItemStateChanged

        checkFieldMapping();
        
    }//GEN-LAST:event_jComboSearchKeyItemStateChanged

    private void jComboAccountIDFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboAccountIDFocusGained
        jComboAccountID.removeAllItems();
        int i = 1;
        jComboAccountID.addItem("");

        while (i < Headers.size()) {
            if ((Headers.get(i) 
                != jComboSearchKey.getSelectedItem())
//                & (Headers.get(i) != jComboAccountID.getSelectedItem())
                & (Headers.get(i) != jComboName.getSelectedItem())
                & (Headers.get(i) != jComboAddress1.getSelectedItem())
                & (Headers.get(i) != jComboAddress2.getSelectedItem())
                & (Headers.get(i) != jComboPostal.getSelectedItem())                    
                & (Headers.get(i) != jComboCity.getSelectedItem())
                & (Headers.get(i) != jComboRegion.getSelectedItem())
                & (Headers.get(i) != jComboFirstName.getSelectedItem())
                & (Headers.get(i) != jComboLastName.getSelectedItem())
                & (Headers.get(i) != jComboEmail.getSelectedItem())
                & (Headers.get(i) != jComboPhone.getSelectedItem())
                & (Headers.get(i) != jComboPhone2.getSelectedItem())         
                & (Headers.get(i) != jComboMaxdebt.getSelectedItem())        
                & (Headers.get(i) != jComboNotes.getSelectedItem())
                & (Headers.get(i) != jComboFax.getSelectedItem())
                ) 
            {

                jComboAccountID.addItem(Headers.get(i));
            }
            ++i;
        }
    }//GEN-LAST:event_jComboAccountIDFocusGained

    private void jComboAccountIDItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboAccountIDItemStateChanged

        checkFieldMapping();
        
    }//GEN-LAST:event_jComboAccountIDItemStateChanged

    private void jComboFirstNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboFirstNameFocusGained
        jComboFirstName.removeAllItems();
        int i = 1;
        jComboFirstName.addItem("");

        while (i < Headers.size()) {
            if ((Headers.get(i) 
                != jComboSearchKey.getSelectedItem())
                & (Headers.get(i) != jComboAccountID.getSelectedItem())
                & (Headers.get(i) != jComboName.getSelectedItem())
                & (Headers.get(i) != jComboAddress1.getSelectedItem())
                & (Headers.get(i) != jComboAddress2.getSelectedItem())
                & (Headers.get(i) != jComboPostal.getSelectedItem())                    
                & (Headers.get(i) != jComboCity.getSelectedItem())
                & (Headers.get(i) != jComboRegion.getSelectedItem())
//                & (Headers.get(i) != jComboFirstName.getSelectedItem())
                & (Headers.get(i) != jComboLastName.getSelectedItem())
                & (Headers.get(i) != jComboEmail.getSelectedItem())
                & (Headers.get(i) != jComboPhone.getSelectedItem())
                & (Headers.get(i) != jComboPhone2.getSelectedItem())           
                & (Headers.get(i) != jComboMaxdebt.getSelectedItem())          
                & (Headers.get(i) != jComboNotes.getSelectedItem())
                & (Headers.get(i) != jComboFax.getSelectedItem())                    
                ) 
            {

                jComboFirstName.addItem(Headers.get(i));
            }
            ++i;
        }
    }//GEN-LAST:event_jComboFirstNameFocusGained

    private void jComboLastNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboLastNameFocusGained
        jComboLastName.removeAllItems();
        int i = 1;
        jComboLastName.addItem("");

        while (i < Headers.size()) {
            if ((Headers.get(i) 
                != jComboSearchKey.getSelectedItem())
                & (Headers.get(i) != jComboAccountID.getSelectedItem())
                & (Headers.get(i) != jComboName.getSelectedItem())
                & (Headers.get(i) != jComboAddress1.getSelectedItem())
                & (Headers.get(i) != jComboAddress2.getSelectedItem())
                & (Headers.get(i) != jComboPostal.getSelectedItem())                    
                & (Headers.get(i) != jComboCity.getSelectedItem())
                & (Headers.get(i) != jComboRegion.getSelectedItem())
                & (Headers.get(i) != jComboFirstName.getSelectedItem())
//                & (Headers.get(i) != jComboLastName.getSelectedItem())
                & (Headers.get(i) != jComboEmail.getSelectedItem())
                & (Headers.get(i) != jComboPhone.getSelectedItem())
                & (Headers.get(i) != jComboPhone2.getSelectedItem())            
                & (Headers.get(i) != jComboMaxdebt.getSelectedItem())           
                & (Headers.get(i) != jComboNotes.getSelectedItem())
                & (Headers.get(i) != jComboFax.getSelectedItem())                    
                ) 
            {

                jComboLastName.addItem(Headers.get(i));
            }
            ++i;
        }
    }//GEN-LAST:event_jComboLastNameFocusGained

    private void jComboPhoneFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboPhoneFocusGained
        jComboPhone.removeAllItems();
        int i = 1;
        jComboPhone.addItem("");

        while (i < Headers.size()) {
            if ((Headers.get(i) 
                != jComboSearchKey.getSelectedItem())
                & (Headers.get(i) != jComboAccountID.getSelectedItem())
                & (Headers.get(i) != jComboName.getSelectedItem())
                & (Headers.get(i) != jComboAddress1.getSelectedItem())
                & (Headers.get(i) != jComboAddress2.getSelectedItem())
                & (Headers.get(i) != jComboPostal.getSelectedItem())                    
                & (Headers.get(i) != jComboCity.getSelectedItem())
                & (Headers.get(i) != jComboRegion.getSelectedItem())
                & (Headers.get(i) != jComboFirstName.getSelectedItem())
                & (Headers.get(i) != jComboLastName.getSelectedItem())
                & (Headers.get(i) != jComboEmail.getSelectedItem())
//                & (Headers.get(i) != jComboPhone.getSelectedItem())
                & (Headers.get(i) != jComboPhone2.getSelectedItem())            
                & (Headers.get(i) != jComboMaxdebt.getSelectedItem())           
                & (Headers.get(i) != jComboNotes.getSelectedItem())
                & (Headers.get(i) != jComboFax.getSelectedItem())      
                ) 
            {

                jComboPhone.addItem(Headers.get(i));
            }
            ++i;
        }
    }//GEN-LAST:event_jComboPhoneFocusGained

    private void jComboPhone2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboPhone2FocusGained
        jComboPhone2.removeAllItems();
        int i = 1;
        jComboPhone2.addItem("");

        while (i < Headers.size()) {
            if ((Headers.get(i) 
                != jComboSearchKey.getSelectedItem())
                & (Headers.get(i) != jComboAccountID.getSelectedItem())
                & (Headers.get(i) != jComboName.getSelectedItem())
                & (Headers.get(i) != jComboAddress1.getSelectedItem())
                & (Headers.get(i) != jComboAddress2.getSelectedItem())
                & (Headers.get(i) != jComboPostal.getSelectedItem())                    
                & (Headers.get(i) != jComboCity.getSelectedItem())
                & (Headers.get(i) != jComboRegion.getSelectedItem())
                & (Headers.get(i) != jComboFirstName.getSelectedItem())
                & (Headers.get(i) != jComboLastName.getSelectedItem())
                & (Headers.get(i) != jComboEmail.getSelectedItem())
                & (Headers.get(i) != jComboPhone.getSelectedItem())
//                & (Headers.get(i) != jComboPhone2.getSelectedItem())          
                & (Headers.get(i) != jComboMaxdebt.getSelectedItem())           
                & (Headers.get(i) != jComboNotes.getSelectedItem())
                & (Headers.get(i) != jComboFax.getSelectedItem())                    
                ) 
            {

                jComboPhone2.addItem(Headers.get(i));
            }
            ++i;
        }
    }//GEN-LAST:event_jComboPhone2FocusGained

    private void jComboMaxdebtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboMaxdebtFocusGained
        jComboMaxdebt.removeAllItems();
        int i = 1;
        jComboMaxdebt.addItem("");

        while (i < Headers.size()) {
            if ((Headers.get(i) 
                != jComboSearchKey.getSelectedItem())
                & (Headers.get(i) != jComboAccountID.getSelectedItem())
                & (Headers.get(i) != jComboName.getSelectedItem())
                & (Headers.get(i) != jComboAddress1.getSelectedItem())
                & (Headers.get(i) != jComboAddress2.getSelectedItem())
                & (Headers.get(i) != jComboPostal.getSelectedItem())                    
                & (Headers.get(i) != jComboCity.getSelectedItem())
                & (Headers.get(i) != jComboRegion.getSelectedItem())
                & (Headers.get(i) != jComboFirstName.getSelectedItem())
                & (Headers.get(i) != jComboLastName.getSelectedItem())
                & (Headers.get(i) != jComboEmail.getSelectedItem())
                & (Headers.get(i) != jComboPhone.getSelectedItem())
                & (Headers.get(i) != jComboPhone2.getSelectedItem())            
 //             & (Headers.get(i) != jComboMaxdebt.getSelectedItem())            
                & (Headers.get(i) != jComboNotes.getSelectedItem())
                & (Headers.get(i) != jComboFax.getSelectedItem())
                ) 
            {

                jComboMaxdebt.addItem(Headers.get(i));
            }
            ++i;
        }          
    }//GEN-LAST:event_jComboMaxdebtFocusGained

    private void jComboMaxdebtItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboMaxdebtItemStateChanged
        checkFieldMapping();
    }//GEN-LAST:event_jComboMaxdebtItemStateChanged

    private void jComboNotesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboNotesFocusGained
        jComboNotes.removeAllItems();
        int i = 1;
        jComboNotes.addItem("");

        while (i < Headers.size()) {
            if ((Headers.get(i) 
                != jComboSearchKey.getSelectedItem())
                & (Headers.get(i) != jComboAccountID.getSelectedItem())
                & (Headers.get(i) != jComboName.getSelectedItem())
                & (Headers.get(i) != jComboAddress1.getSelectedItem())
                & (Headers.get(i) != jComboAddress2.getSelectedItem())
                & (Headers.get(i) != jComboPostal.getSelectedItem())                    
                & (Headers.get(i) != jComboCity.getSelectedItem())
                & (Headers.get(i) != jComboRegion.getSelectedItem())
                & (Headers.get(i) != jComboFirstName.getSelectedItem())
                & (Headers.get(i) != jComboLastName.getSelectedItem())
                & (Headers.get(i) != jComboEmail.getSelectedItem())
                & (Headers.get(i) != jComboPhone.getSelectedItem())
                & (Headers.get(i) != jComboPhone2.getSelectedItem())             
                & (Headers.get(i) != jComboMaxdebt.getSelectedItem())            
 //             & (Headers.get(i) != jComboNotes.getSelectedItem())
                & (Headers.get(i) != jComboFax.getSelectedItem())
                ) 
            {

                jComboNotes.addItem(Headers.get(i));
            }
            ++i;
        } 
    }//GEN-LAST:event_jComboNotesFocusGained

    private void jComboNotesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboNotesItemStateChanged
       checkFieldMapping(); 
    }//GEN-LAST:event_jComboNotesItemStateChanged

    private void jComboFaxFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboFaxFocusGained
        jComboFax.removeAllItems();
        int i = 1;
        jComboFax.addItem("");

        while (i < Headers.size()) {
            if ((Headers.get(i) 
                != jComboSearchKey.getSelectedItem())
                & (Headers.get(i) != jComboAccountID.getSelectedItem())
                & (Headers.get(i) != jComboName.getSelectedItem())
                & (Headers.get(i) != jComboAddress1.getSelectedItem())
                & (Headers.get(i) != jComboAddress2.getSelectedItem())
                & (Headers.get(i) != jComboPostal.getSelectedItem())                    
                & (Headers.get(i) != jComboCity.getSelectedItem())
                & (Headers.get(i) != jComboRegion.getSelectedItem())
                & (Headers.get(i) != jComboFirstName.getSelectedItem())
                & (Headers.get(i) != jComboLastName.getSelectedItem())
                & (Headers.get(i) != jComboEmail.getSelectedItem())
                & (Headers.get(i) != jComboPhone.getSelectedItem())
                & (Headers.get(i) != jComboPhone2.getSelectedItem())         
                & (Headers.get(i) != jComboMaxdebt.getSelectedItem())            
                & (Headers.get(i) != jComboNotes.getSelectedItem())
 //             & (Headers.get(i) != jComboFax.getSelectedItem())
                ) 
            {

                jComboFax.addItem(Headers.get(i));
            }
            ++i;
        } 
    }//GEN-LAST:event_jComboFaxFocusGained

    private void jComboFaxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboFaxItemStateChanged
        checkFieldMapping(); 
    }//GEN-LAST:event_jComboFaxItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckVisible;
    private javax.swing.JComboBox jComboAccountID;
    private javax.swing.JComboBox jComboAddress1;
    private javax.swing.JComboBox jComboAddress2;
    private javax.swing.JComboBox jComboCity;
    private javax.swing.JComboBox jComboEmail;
    private javax.swing.JComboBox<String> jComboFax;
    private javax.swing.JComboBox jComboFirstName;
    private javax.swing.JComboBox jComboLastName;
    private javax.swing.JComboBox<String> jComboMaxdebt;
    private javax.swing.JComboBox jComboName;
    private javax.swing.JComboBox<String> jComboNotes;
    private javax.swing.JComboBox jComboPhone;
    private javax.swing.JComboBox jComboPhone2;
    private javax.swing.JComboBox jComboPostal;
    private javax.swing.JComboBox jComboRegion;
    private javax.swing.JComboBox jComboSearchKey;
    private javax.swing.JComboBox jComboSeparator;
    private javax.swing.JPanel jFileChooserPanel;
    private javax.swing.JTextField jFileName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtnFileChoose;
    private javax.swing.JButton jbtnImport;
    private javax.swing.JButton jbtnRead;
    private javax.swing.JButton jbtnReset;
    private javax.swing.JLabel jlblBad;
    private javax.swing.JLabel jlblInvalid;
    private javax.swing.JLabel jlblMissing;
    private javax.swing.JLabel jlblNew;
    private javax.swing.JLabel jlblNotChanged;
    private javax.swing.JLabel jlblRecords;
    private javax.swing.JLabel jlblUpdates;
    private javax.swing.JTextField jtxtBad;
    private javax.swing.JTextField jtxtInvalid;
    private javax.swing.JTextField jtxtMissing;
    private javax.swing.JTextField jtxtNew;
    private javax.swing.JTextField jtxtNoChange;
    private javax.swing.JTextField jtxtRecords;
    private javax.swing.JTextField jtxtUpdate;
    private com.alee.laf.progressbar.WebProgressBar webPBar;
    // End of variables declaration//GEN-END:variables
}
