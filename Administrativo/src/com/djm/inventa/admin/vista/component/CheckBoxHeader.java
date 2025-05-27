package com.djm.inventa.admin.vista.component;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CheckBoxHeader extends JPanel implements TableCellRenderer, MouseListener {
        private boolean mousePressed = true;
        private JTable tabla;
        private JTableHeader cabecera;
        private JCheckBox chk;
        private boolean selectCheck = false;
        
        public CheckBoxHeader(JTable tabla) {
            this.tabla = tabla;

            this.setOpaque(false);

            chk = new JCheckBox();
            chk.setOpaque(false);
            chk.setSelected(selectCheck);
            chk.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent var1) {
                    for(int i = 0; i < tabla.getRowCount(); i++) {
                        tabla.setValueAt(chk.isSelected(), i, 0);
                    }

                }
            });

            this.add(chk);

            TableColumn var3 = this.tabla.getColumnModel().getColumn(0);
            var3.setCellEditor(this.tabla.getDefaultEditor(Boolean.class));
            var3.setCellRenderer(this.tabla.getDefaultRenderer(Boolean.class));
            var3.setHeaderRenderer(this);
        }

        public Component getTableCellRendererComponent(JTable var1, Object var2, boolean var3, boolean var4, int var5, int var6) {
            if (var1 != null) {
                this.cabecera = var1.getTableHeader();
                if (this.cabecera != null) {
                    this.cabecera.addMouseListener(this);
                }
            }

            return this;
        }

        public void seleccionar(MouseEvent me) {
            TableColumnModel columnModel = this.tabla.getColumnModel();
            int columnSelected = columnModel.getColumnIndexAtX(me.getX());
            if (columnSelected == 0) {
                chk.setSelected(!chk.isSelected());
            }

            ((JTableHeader)me.getSource()).repaint();
        }

        public void mouseClicked(MouseEvent var1) {
            if (this.mousePressed) {
                this.seleccionar(var1);
            }

        }

        public void mousePressed(MouseEvent var1) {
        }

        public void mouseReleased(MouseEvent var1) {
        }

        public void mouseEntered(MouseEvent var1) {
        }

        public void mouseExited(MouseEvent var1) {
        }

        protected void enabled(boolean var1) {
            this.mousePressed = var1;
            chk.setEnabled(var1);
        }

        protected void upd() {
            this.cabecera.repaint();
        }
     }
