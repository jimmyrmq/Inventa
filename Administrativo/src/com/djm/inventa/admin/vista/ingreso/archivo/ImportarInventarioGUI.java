package com.djm.inventa.admin.vista.ingreso.archivo;

import com.djm.common.GlobalFrame;
import com.djm.inventa.admin.util.LoggerApp;
import com.djm.inventa.admin.util.PropiedadesSistema;
import com.djm.inventa.admin.vista.CONSTANTS;
import com.djm.ui.component.Button;
import com.djm.ui.component.ComboBox;
import com.djm.ui.component.OptionPane;
import com.djm.util.Image;
import com.djm.util.LayoutPanel;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;

;

public class ImportarInventarioGUI implements ActionListener{
    private final String ID = PropiedadesSistema.getString("ImportarInventarioArchivo.ID");
    private JDialog dialog;
    private boolean reload = false;
    private JButton bSelectFiled,bImportar, bCerrar;
    private JLabel lFile;
    private JProgressBar progressBar;
    private JComboBox<ColumnSelected> cbCodigo, cbCodigoBarra, cbNombre, cbUnidad, cbModelo, cbSerie, cbMarca,
                                    cbDisponible, cbCosto, cbPrecio1, cbPrecio2, cbPrecio3, cbIncluyeImpuesto,
                                    cbStockCritico, cbRequiereStock, cbCantidadDisponible;

    private DefaultComboBoxModel<ColumnSelected> dcbCodigo, dcbCodigoBarra, dcbNombre, dcbUnidad, dcbModelo, dcbSerie,
                                                dcbMarca, dcbDisponible, dcbCosto, dcbPrecio1, dcbPrecio2, dcbPrecio3,
                                                dcbIncluyeImpuesto, dcbStockCritico, dcbRequiereStock, dcbCantidadDisponible;
    private ReadFileCVS rf = null;

    public ImportarInventarioGUI() {

        createDialog();

        Container content = dialog.getContentPane();
        content.setLayout(new GridBagLayout());

        content.add(pPrincipal(), LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 1.0f, 1.0f));

        cerrarEsc();

        dialog.pack();
        dialog.setVisible(true);
    }
    public JPanel pPrincipal(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        bSelectFiled = new JButton(CONSTANTS.LANG.getValue("stock.button.import.selectedcvs"), Image.getIcon("16/excel.png"));
        bSelectFiled.setActionCommand("SELECT_FILE");
        bSelectFiled.addActionListener(this);

        bImportar = new JButton(CONSTANTS.LANG.getValue("stock.button.importar"), Image.getIcon("16/import16.png"));
        bImportar.setActionCommand("IMPORTAR");
        bImportar.addActionListener(this);
        bImportar.setEnabled(false);

        bCerrar = new JButton(CONSTANTS.LANG.getValue("button.cerrar"));
        bCerrar.setActionCommand("CANCELAR");
        bCerrar.addActionListener(this);

        progressBar = new JProgressBar(0,0);
        progressBar.setBorderPainted(false);
        progressBar.setVisible(false);

        Dimension dim = new Dimension(146, 14);
        progressBar.setSize(dim);
        progressBar.setPreferredSize(dim);

        lFile = new JLabel();
        lFile.setPreferredSize(new Dimension(400,21));
        lFile.setOpaque(true);
        lFile.setBackground(new Color(204,205,207));

        JLabel lCodigo = new JLabel(CONSTANTS.LANG.getValue("producto.label.codigo"));
        JLabel lCodigoBarra = new JLabel(CONSTANTS.LANG.getValue("producto.label.codigobarra"));
        JLabel lNombre = new JLabel(CONSTANTS.LANG.getValue("producto.label.nombre"));
        JLabel lUnidad = new JLabel(CONSTANTS.LANG.getValue("producto.label.unidad"));
        JLabel lModelo = new JLabel(CONSTANTS.LANG.getValue("producto.label.modelo"));
        JLabel lSerie = new JLabel(CONSTANTS.LANG.getValue("producto.label.serie"));
        JLabel lMarca = new JLabel(CONSTANTS.LANG.getValue("producto.label.marca"));
        JLabel lDisponible = new JLabel(CONSTANTS.LANG.getValue("producto.label.habilitado"));
        JLabel lCosto = new JLabel(CONSTANTS.LANG.getValue("producto.label.preciocompra"));
        JLabel lPrecio1 = new JLabel(CONSTANTS.LANG.getValue("producto.label.precioventa"));
        JLabel lPrecio2 = new JLabel(CONSTANTS.LANG.getValue("producto.label.preciomayor"));
        JLabel lPrecio3 = new JLabel(CONSTANTS.LANG.getValue("producto.label.precioespecial"));
        JLabel lIncluyeImpuesto = new JLabel(CONSTANTS.LANG.getValue("producto.label.precioIncluyeImpuesto"));
        JLabel lStockCritico = new JLabel(CONSTANTS.LANG.getValue("producto.label.stockcritico"));
        JLabel lRequiereStock = new JLabel(CONSTANTS.LANG.getValue("producto.label.no_requiere_stock"));
        JLabel lCantidadDisponible = new JLabel(CONSTANTS.LANG.getValue("producto.label.cantidad_producto"));
        //JLabel lArchivo= new JLabel("Archivo:");

        dcbCodigo = new DefaultComboBoxModel<ColumnSelected> ();
        dcbCodigoBarra = new DefaultComboBoxModel<ColumnSelected> ();
        dcbNombre = new DefaultComboBoxModel<ColumnSelected> ();
        dcbUnidad = new DefaultComboBoxModel<ColumnSelected> ();
        dcbModelo = new DefaultComboBoxModel<ColumnSelected> ();
        dcbSerie = new DefaultComboBoxModel<ColumnSelected> ();
        dcbMarca = new DefaultComboBoxModel<ColumnSelected> ();
        dcbDisponible = new DefaultComboBoxModel<ColumnSelected> ();
        dcbCosto = new DefaultComboBoxModel<ColumnSelected> ();
        dcbPrecio1 = new DefaultComboBoxModel<ColumnSelected> ();
        dcbPrecio2 = new DefaultComboBoxModel<ColumnSelected> ();
        dcbPrecio3 = new DefaultComboBoxModel<ColumnSelected> ();
        dcbIncluyeImpuesto = new DefaultComboBoxModel<ColumnSelected> ();
        dcbStockCritico = new DefaultComboBoxModel<ColumnSelected> ();
        dcbRequiereStock = new DefaultComboBoxModel<ColumnSelected> ();
        dcbCantidadDisponible = new DefaultComboBoxModel<ColumnSelected> ();
        Dimension cbdim = new Dimension(210,21);

        cbCodigo = new JComboBox<ColumnSelected>(dcbCodigo);
        cbCodigoBarra = new JComboBox<ColumnSelected>(dcbCodigoBarra);
        cbNombre = new JComboBox<ColumnSelected>(dcbNombre);
        cbUnidad = new JComboBox<ColumnSelected>(dcbUnidad);
        cbModelo = new JComboBox<ColumnSelected>(dcbModelo);
        cbSerie = new JComboBox<ColumnSelected>(dcbSerie);
        cbMarca = new JComboBox<ColumnSelected>(dcbMarca);
        cbDisponible = new JComboBox<ColumnSelected>(dcbDisponible);
        cbCosto = new JComboBox<ColumnSelected>(dcbCosto);
        cbPrecio1 = new JComboBox<ColumnSelected>(dcbPrecio1);
        cbPrecio2 = new JComboBox<ColumnSelected>(dcbPrecio2);
        cbPrecio3 = new JComboBox<ColumnSelected>(dcbPrecio3);
        cbIncluyeImpuesto = new JComboBox<ColumnSelected>(dcbIncluyeImpuesto);
        cbStockCritico = new JComboBox<ColumnSelected>(dcbStockCritico);
        cbRequiereStock = new JComboBox<ColumnSelected>(dcbRequiereStock);
        cbCantidadDisponible = new JComboBox<ColumnSelected>(dcbCantidadDisponible);

        cbCodigo.setPreferredSize(cbdim);
        cbCodigoBarra.setPreferredSize(cbdim);
        cbNombre.setPreferredSize(cbdim);
        cbUnidad .setPreferredSize(cbdim);
        cbModelo .setPreferredSize(cbdim);
        cbSerie .setPreferredSize(cbdim);
        cbMarca .setPreferredSize(cbdim);
        cbDisponible.setPreferredSize(cbdim);
        cbCosto.setPreferredSize(cbdim);
        cbPrecio1.setPreferredSize(cbdim);
        cbPrecio2.setPreferredSize(cbdim);
        cbPrecio3.setPreferredSize(cbdim);
        cbIncluyeImpuesto.setPreferredSize(cbdim);
        cbStockCritico.setPreferredSize(cbdim);
        cbRequiereStock.setPreferredSize(cbdim);
        cbCantidadDisponible.setPreferredSize(cbdim);

        clearcb();

        panel.add(bSelectFiled, LayoutPanel.constantePane(0, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 10, 0, 0, 0.0f, 0.0f));

        panel.add(lCodigo, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 10, 0, 0, 0.0f, 0.0f));
        panel.add(cbCodigo, LayoutPanel.constantePane(1, 1, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lCodigoBarra, LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(cbCodigoBarra, LayoutPanel.constantePane(1, 2, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lNombre, LayoutPanel.constantePane(0, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(cbNombre, LayoutPanel.constantePane(1, 3, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lUnidad, LayoutPanel.constantePane(0, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(cbUnidad, LayoutPanel.constantePane(1, 4, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lModelo, LayoutPanel.constantePane(0, 5, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(cbModelo, LayoutPanel.constantePane(1, 5, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lSerie, LayoutPanel.constantePane(0, 6, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(cbSerie, LayoutPanel.constantePane(1, 6, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lMarca, LayoutPanel.constantePane(0, 7, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(cbMarca, LayoutPanel.constantePane(1, 7, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lDisponible, LayoutPanel.constantePane(0, 8, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(cbDisponible, LayoutPanel.constantePane(1, 8, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lCosto, LayoutPanel.constantePane(0, 9, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(cbCosto, LayoutPanel.constantePane(1, 9, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lPrecio1, LayoutPanel.constantePane(0, 10, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(cbPrecio1, LayoutPanel.constantePane(1, 10, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lPrecio2, LayoutPanel.constantePane(0, 11, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(cbPrecio2, LayoutPanel.constantePane(1, 11, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lPrecio3, LayoutPanel.constantePane(0, 12, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(cbPrecio3, LayoutPanel.constantePane(1, 12, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lIncluyeImpuesto, LayoutPanel.constantePane(0, 13, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(cbIncluyeImpuesto, LayoutPanel.constantePane(1, 13, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lStockCritico, LayoutPanel.constantePane(0, 14, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(cbStockCritico, LayoutPanel.constantePane(1, 14, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lRequiereStock, LayoutPanel.constantePane(0, 15, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(cbRequiereStock, LayoutPanel.constantePane(1, 15, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lCantidadDisponible, LayoutPanel.constantePane(0, 16, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(cbCantidadDisponible, LayoutPanel.constantePane(1, 16, 2, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        //panel.add(lArchivo, ConstantePaneGrid.constantePane(0, 17, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 10, 0, 0, 1.0f, 0.0f));
        panel.add(lFile, LayoutPanel.constantePane(2, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 5, 0, 0, 1.0f, 0.0f));
        panel.add(bImportar, LayoutPanel.constantePane(1, 18, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 5, 5, 5, 0, 0.0f, 1.0f));
        panel.add(progressBar, LayoutPanel.constantePane(0, 18, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 10, 5, 0, 0.0f, 0.0f));
        panel.add(bCerrar, LayoutPanel.constantePane(3, 18, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_END, 5, 0, 5, 15, 0.0f, 0.0f));

        return panel;

    }

    private void createDialog(){

        dialog = new JDialog(GlobalFrame.getInstance().getFrame(), "Importar Inventario", true);

        Dimension dim = new Dimension(720,550);

        //dialog.setUndecorated(true);
        dialog.setPreferredSize(dim);
        dialog.setSize(dim);
        dialog.setMinimumSize(dim);
        //dialog.pack();
        dialog.setResizable(true);
        //dialog.setDefaultCloseOperation(0);
        dialog.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if("IMPORTAR".equals(command)){
            if(rf !=null ){
                LoggerApp.trace("Iniciando lectura");
                dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));

                rf.runExport();

                Thread t2 = new Thread(()-> {

                    if (progressBar != null) {
                        progressBar.setVisible(true);
                        /*progressBar.repaint();
                        progressBar.updateUI();*/
                        while (rf.isAlive()) {
                            progressBar.setValue(rf.getCurrentCount());
                        }
                        progressBar.setIndeterminate(true);
                    }
                    //System.out.println("Fin de la lectura");
                    eText(true);
                });

                eText(false);

                Thread t1 = new Thread(()-> {
                    progressBar.setIndeterminate(true);
                    int codigo = dcbCodigo.getElementAt(cbCodigo.getSelectedIndex()).getIndex();
                    int codigoBarra = dcbCodigoBarra.getElementAt(cbCodigoBarra.getSelectedIndex()).getIndex();
                    int nombre = dcbNombre.getElementAt(cbNombre.getSelectedIndex()).getIndex();
                    int unidad = dcbUnidad.getElementAt(cbUnidad.getSelectedIndex()).getIndex();
                    int modelo = dcbModelo.getElementAt(cbModelo.getSelectedIndex()).getIndex();
                    int serie = dcbSerie.getElementAt(cbSerie.getSelectedIndex()).getIndex();
                    int marca = dcbMarca.getElementAt(cbMarca.getSelectedIndex()).getIndex();
                    int disponible = dcbDisponible.getElementAt(cbDisponible.getSelectedIndex()).getIndex();
                    int costo = dcbCosto.getElementAt(cbCosto.getSelectedIndex()).getIndex();
                    int precio1 = dcbPrecio1.getElementAt(cbPrecio1.getSelectedIndex()).getIndex();
                    int precio2 = dcbPrecio2.getElementAt(cbPrecio2.getSelectedIndex()).getIndex();
                    int precio3 = dcbPrecio3.getElementAt(cbPrecio3.getSelectedIndex()).getIndex();
                    int incluyeImpuesto = dcbIncluyeImpuesto.getElementAt(cbIncluyeImpuesto.getSelectedIndex()).getIndex();
                    int stockCritico = dcbStockCritico.getElementAt(cbStockCritico.getSelectedIndex()).getIndex();
                    int requiereStock = dcbRequiereStock.getElementAt(cbRequiereStock.getSelectedIndex()).getIndex();
                    int cantidadDisponible = dcbCantidadDisponible.getElementAt(cbCantidadDisponible.getSelectedIndex()).getIndex();

                    if (codigo == -1) {
                        OptionPane.information(CONSTANTS.LANG.getValue("producto.error.codigo"));
                        rf.stopExport();
                    }
                    else{
                    /*else if (nombre == -1) {
                        JOptionPane.showMessageDialog(GlobalFrame.getInstance().getFrame(),"Seleccione el nombre del producto.","Importar Inventario",	JOptionPane.ERROR_MESSAGE);
                        rf.stopExport();
                    } else*/
                        if(progressBar!=null)
                            progressBar.setIndeterminate(false);

                        rf.getData(codigo, codigoBarra, nombre, unidad,modelo, serie, marca, disponible, costo, precio1, precio2, precio3,
                                incluyeImpuesto, stockCritico, requiereStock, cantidadDisponible);

                        progressBar.setIndeterminate(true);
                        progressBar.setVisible(false);
                        progressBar.setValue(0);

                        dialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

                        if(rf.getMsgError().isEmpty()){
                            reload = true;
                           OptionPane.information(CONSTANTS.LANG.getValue("producto.information.importado.ok"));
                            clearcb();
                            bImportar.setEnabled(false);
                            lFile.setText(null);
                            lFile.setIcon(null);
                            bCerrar.setEnabled(true);
                            bSelectFiled.setEnabled(true);
                        }
                        else{
                            StringBuffer errs = new StringBuffer();
                            errs.append("<html>");
                            errs.append(CONSTANTS.LANG.getValue("producto.error.importado"));
                            errs.append("<br>");

                            List <String> le= rf.getMsgError();
                            for(String err:le){
                                errs.append(err);
                                errs.append("<br>");
                            }
                            errs.append("</html>");

                            OptionPane.error( errs.toString());
                            eText(true);
                        }

                    }
                });

                t2.start();
                t1.start();
            }
        }
        else if("CANCELAR".equals(command)){
            progressBar.setIndeterminate(false);
            progressBar.setVisible(false);
            progressBar.setValue(0);

            dialog.setVisible(false);
            dialog.dispose();
        }
        else if("SELECT_FILE".equals(command)){
            final JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Buscar Archivo (csv)...");
            fc.setFileFilter(new OpenFileFilter("csv"," CSV (*.cvs)"));
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fc.setMultiSelectionEnabled(false);
            Color background = UIManager.getColor("Label.background");
            fc.setBackground(background);
            fc.setOpaque(true);
            int seleccion = fc.showOpenDialog(dialog);
            if(seleccion == JFileChooser.APPROVE_OPTION){
                dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                File fd = fc.getSelectedFile();
                if(fd.canRead()) {

                    clearcb();
                    
                    /*System.out.println(fd.getName());
                    System.out.println(fd.getParent());
                    System.out.println(fd.getAbsolutePath());*/
                    lFile.setText(fd.getAbsolutePath());
                    rf = new ReadFileCVS(fd);
                    progressBar.setMaximum(rf.getAmountProduct());
                    //System.out.println("Cantidad de Productos: "+rf.getAmountProduct());
                    //List<ColumnSelected> listCols = rf.getNameColumns();
                    String listCols[] = rf.getNameColumns();
                    if(listCols == null || listCols.length==0){
                        lFile.setIcon(Image.getIcon("error_file.png"));
                    }
                    else {
                        lFile.setIcon(Image.getIcon("ok.png"));

                        for (int i = 0;i<listCols.length;i++) {
                            ColumnSelected cl = new ColumnSelected(listCols[i], i );
                            dcbCodigo.addElement(cl);
                            dcbCodigoBarra.addElement(cl);
                            dcbNombre.addElement(cl);
                            dcbUnidad.addElement(cl);
                            dcbModelo.addElement(cl);
                            dcbSerie.addElement(cl);
                            dcbMarca.addElement(cl);
                            dcbDisponible.addElement(cl);
                            dcbCosto.addElement(cl);
                            dcbPrecio1.addElement(cl);
                            dcbPrecio2.addElement(cl);
                            dcbPrecio3.addElement(cl);
                            dcbIncluyeImpuesto.addElement(cl);
                            dcbStockCritico.addElement(cl);
                            dcbRequiereStock.addElement(cl);
                            dcbCantidadDisponible.addElement(cl);
                        }

                        eCombo(true);
                        bImportar.setEnabled(true);
                    }
                    dialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }else{
                    dialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    lFile.setIcon(new ImageIcon("icon/icon/error_file.png"));
                    lFile.setText(fd.getAbsolutePath());
                    JOptionPane.showMessageDialog(GlobalFrame.getInstance().getFrame(),"Error! No se puede leer archivo.","Importar Inventario",	JOptionPane.ERROR_MESSAGE);
                }
            }

            //FileDialog fd = new FileDialog(FrameMain.frame, sp.getValue("label.buscar_archivo_csv"), FileDialog.LOAD);
            //fd.setDirectory("C:\\");
            /*fd.setFilenameFilter((dir, name)-> {
                    return name.endsWith("*.csv");
                }
            );*

            fd.setFile("*.csv");
            fd.setVisible(true);

            String filename = fd.getFile();

            if (filename != null) {
                String dir = fd.getDirectory();
                System.out.println("You chose " + filename);
                System.out.println("You Directory " + dir);
            }*/

        }
    }

    private void clearcb(){
        dcbCodigo.removeAllElements();
        dcbCodigoBarra.removeAllElements();
        dcbNombre.removeAllElements();
        dcbUnidad.removeAllElements();
        dcbModelo.removeAllElements();
        dcbSerie.removeAllElements();
        dcbMarca.removeAllElements();
        dcbDisponible.removeAllElements();
        dcbCosto.removeAllElements();
        dcbPrecio1.removeAllElements();
        dcbPrecio2.removeAllElements();
        dcbPrecio3.removeAllElements();
        dcbIncluyeImpuesto.removeAllElements();
        dcbStockCritico.removeAllElements();
        dcbRequiereStock.removeAllElements();
        dcbCantidadDisponible.removeAllElements();

        dcbCodigo.addElement(new ColumnSelected(" ",-1));
        dcbCodigoBarra.addElement(new ColumnSelected(" ",-1));
        dcbNombre.addElement(new ColumnSelected(" ",-1));
        dcbUnidad.addElement(new ColumnSelected(" ",-1));
        dcbModelo.addElement(new ColumnSelected(" ",-1));
        dcbSerie.addElement(new ColumnSelected(" ",-1));
        dcbMarca.addElement(new ColumnSelected(" ",-1));
        dcbDisponible.addElement(new ColumnSelected("Si",-2));
        dcbDisponible.addElement(new ColumnSelected("No",-3));
        dcbCosto.addElement(new ColumnSelected(" ",-1));
        dcbPrecio1.addElement(new ColumnSelected(" ",-1));
        dcbPrecio2.addElement(new ColumnSelected(" ",-1));
        dcbPrecio3.addElement(new ColumnSelected(" ",-1));
        dcbIncluyeImpuesto.addElement(new ColumnSelected("Si",-2));
        dcbIncluyeImpuesto.addElement(new ColumnSelected("No",-3));
        dcbStockCritico.addElement(new ColumnSelected(" ",-1));
        dcbRequiereStock.addElement(new ColumnSelected("Si",-2));
        dcbRequiereStock.addElement(new ColumnSelected("No",-3));
        dcbCantidadDisponible.addElement(new ColumnSelected(" ",-1));

        cbRequiereStock.setSelectedIndex(1);

        eCombo(false);
    }

    private void eText(boolean e){
        eCombo(e);
        bImportar.setEnabled(e);
        bCerrar.setEnabled(e);
        bSelectFiled.setEnabled(e);
    }

    public boolean isReload(){
        return reload;
    }

    private void eCombo(boolean e){
        cbCodigo.setEnabled(e);
        cbCodigoBarra.setEnabled(e);
        cbNombre.setEnabled(e);
        cbUnidad.setEnabled(e);
        cbModelo.setEnabled(e);
        cbSerie.setEnabled(e);
        cbMarca.setEnabled(e);
        cbDisponible.setEnabled(e);
        cbCosto.setEnabled(e);
        cbPrecio1.setEnabled(e);
        cbPrecio2.setEnabled(e);
        cbPrecio3.setEnabled(e);
        cbIncluyeImpuesto.setEnabled(e);
        cbStockCritico.setEnabled(e);
        cbRequiereStock.setEnabled(e);
        cbCantidadDisponible.setEnabled(e);
    }

    private void cerrarEsc(){
        KeyStroke SR= KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0,false);
        Action action =new AbstractAction(){
            public void actionPerformed(ActionEvent e) {
                //System.out.println(dialog.getSize());
                progressBar.setIndeterminate(false);
                progressBar.setVisible(false);
                progressBar.setValue(0);

                dialog.setVisible(false);
                dialog.dispose();
            }
        };

        InputMap inputMap = bCerrar.getInputMap();
        inputMap = bCerrar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(SR, "CERRAR_DIALOG");
        ActionMap actionMap = bCerrar.getActionMap();
        actionMap.put("CERRAR_DIALOG", action);
    }
}
