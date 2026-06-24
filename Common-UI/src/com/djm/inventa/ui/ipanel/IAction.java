package com.djm.inventa.ui.ipanel;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public abstract class IAction {
    /**
     * Para que funcione el evento de cerrar con ESC, se debe llamar al metodo cerrarEsc() y
     * pasarle el boton que se desea que ejecute la accion, generalmente el boton cancelar o cerrar.
     * Y tiene que estar la accion en Listner del boton, es decir, el boton debe tener un actionCommand y
     * el listener debe tener la accion correspondiente a ese actionCommand.
     */
    public abstract void actionEsc();

    public void cerrarEsc(JButton button){

        KeyStroke SR= KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0,false);
        Action action =new AbstractAction(){
            public void actionPerformed(ActionEvent e) {
                actionEsc();
            }
        };
        InputMap inputMap = button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(SR, "CERRAR");
        ActionMap actionMap = button.getActionMap();
        actionMap.put("CERRAR", action);
    }
}
