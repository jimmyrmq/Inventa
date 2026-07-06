package com.djm.inventa.producto.dev;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.core.i18n.I18nManager;
import com.djm.inventa.util.TableSQL;
import com.djm.inventa.producto.vista.producto.PanelManagerProducto;
import com.djm.inventa.ui.ipanel.IPanelDataAction;
import com.djm.util.LayoutPanel;

import javax.swing.JFrame;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class FrameRunDev {
    public FrameRunDev(){

        IPanelDataAction managerProducto = new PanelManagerProducto();
        conexionDB();

        I18nManager.initForDev("producto", "producto");

        JFrame frame = new JFrame(managerProducto.getTitle());

        Container content= frame.getContentPane();
        content.setLayout(new GridBagLayout());
        content.add(managerProducto.getPanel(), LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 1.0f, 1.0f));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dimension = new Dimension(600, 500);
        frame.setMinimumSize(dimension);
        //frame.setPreferredSize(dimension);
        //frame.setSize(dimension);
        frame.pack();
        frame.setDefaultLookAndFeelDecorated(true);
        //frame.setBackground(new Color(158,162,144));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void conexionDB() {
        //Establecer conxion con la BD
        DatabaseServiceImpl db = new DatabaseServiceImpl();
        //Establer correcion
        db.connect();
        if(db.isConnected()) {
            AppContext.getInstance().setPropiedad("db.service", db);
            TableSQL tableSQL = new TableSQL("inventa","mysql");
            String[] tables = {"Categoria","Marca","Proveedor","Producto"};
            tableSQL.checkTable(tables);
        }else
            System.exit(1);

    }
}
