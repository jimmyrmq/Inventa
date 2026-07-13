package com.djm.inventa.appshell.ui.menu;

import com.djm.inventa.appshell.startup.CONSTANTS;
import com.djm.ui.GlobalFrame;
import com.djm.ui.component.OptionPane;

public class Salir {
    private static Salir salir;
    private Salir(){}

    public static Salir getInstance(){
        if(salir == null)
            salir = new Salir();
        return salir;
    }

    public void exitSystem(){
        int n0 = OptionPane.questionYesOrKey( CONSTANTS.I8N.getValue("sistema.mensaje.salir"));//JOptionPane.showConfirmDialog(GlobalFrame.getInstance().getFrame(), CONSTANT.LANG.getValue("sistema.mensaje.salir"), CONSTANT.TITULO,JOptionPane.YES_NO_OPTION);//
        if(n0 == OptionPane.OK){
            /*try {
                DBConnection.getInstance().close();
            } catch (SQLException e) {
                LoggerApp.error("Error al tratar de cerrar la BD");
            }*/
            //System.out.println(frame.getPreferredSize());
            try {
                Thread.sleep(100);
            }catch (InterruptedException exc){}
            GlobalFrame.getInstance().getFrame().setVisible(false);
            GlobalFrame.getInstance().getFrame().dispose();
            System.gc();
            System.exit(0);
        }
    }
}
