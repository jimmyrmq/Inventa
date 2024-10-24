package com.djm.inventa.admin.vista.ingreso.archivo;

import com.djm.inventa.admin.util.LoggerApp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadFileCVS {
    //private List<ColumnSelected> nameColumns;
    private String nameColumns[];
    private BufferedReader br = null;
    private List<RowProduct> listProd;
    private boolean alive = false;
    private int amountProd = 0;
    private int currentCount = 0;
    private List<String> msgError;
    public ReadFileCVS(File file){
        msgError = new ArrayList<>();
        readColumn(file);
    }

    private synchronized void readColumn(File archivo){
        FileReader fr = null;
        //nameColumns = new ArrayList<>();
        listProd = new ArrayList<>();
        try {

            fr = new FileReader (archivo);
            br = new BufferedReader(fr);

            String linea;
            if((linea=br.readLine())!=null) {
                //System.out.println(linea);
                nameColumns = linea.split(";");
            }
            fillData();
        }
        catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if( fr != null ){
                    fr.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
    }
    
    private synchronized void fillData(){
        String linea;
        alive = true;
        amountProd = 0;
        try {
            while((linea=br.readLine())!=null) {
                String row[] = linea.split(";");
                listProd.add(new RowProduct(row));
            }
        }catch (IOException exc){
            LoggerApp.error("Metodo: ReadFileCVS.fillData() - "+exc.getMessage());
        }
        finally {
            try {
                amountProd = listProd.size();
                br.close();
            } catch (IOException exc) {
                LoggerApp.error("Metodo: ReadFileCVS.fillData() - "+exc.getMessage());

            }
        }
        alive = false;
    }

    public void getData(int codigo, int codigoBarra,
                        int nombre, int unidad, int modelo, int serie,
                        int marca, int disponible, int costo, int precio1,
                        int precio2, int precio3, int esExcento,
                        int stockCritico, int isServicio, int cantidadDisponible){

        /*msgError.clear();
        currentCount = 0;
        alive = true;

        int l = listProd.size();

        int countOk  = 0;
        int countErr = 0;
        int countWarning = 0;
        boolean err = false;
        String nombre0;
        String codigo0;
        ConsultaProducto consultaProducto = new ConsultaProducto();
        cont:for (int i = 0;i < l;i++){
            currentCount ++;
            nombre0 = null;
            codigo0 = null;
            err = false;
            RowProduct rp = listProd.get(i);
            String row[] = rp.row;
            Producto prod = null;
            if(codigo!=-1) {
                codigo0 = row[codigo];
                if(codigo0==null || codigo0.isEmpty()){
                    countErr++;
                    msgError.add("Error no se pudo obtener el codigo");
                    continue cont;
                }else {

                    prod = consultaProducto.getProducto(codigo0,false);
                    if(prod == null) {
                        prod = new Producto();
                    }

                    prod.setCodigo(codigo0);
                }

                //System.out.print"Codigo: " + codigo0);
            }

            if(prod != null) {
                if (codigoBarra != -1) {
                    prod.setCodigoBarra(row[codigoBarra]);
                    //System.out.print", codigoBarra:" + row[codigoBarra]);
                }

                if (nombre != -1) {
                    nombre0 = row[nombre];
                    if (nombre0 == null || nombre0.isEmpty()) {
                        countErr++;
                        msgError.add("Error no se pudo obtener el nombre del producto.");
                        continue cont;
                    } else {
                        prod.setNombre(nombre0);
                    }
                    //System.out.print", nombre:" + nombre0);
                }

                if (unidad != -1) {
                    prod.setUnidadMedida(row[unidad]);
                    //System.out.print", unidad:" + row[unidad]);
                }

                if (modelo != -1) {
                    prod.setModelo(row[modelo]);
                    //System.out.print", modelo:" + row[modelo]);
                }

                if (serie != -1) {
                    prod.setSerie(row[serie]);
                    //System.out.print", serie:" + row[serie]);
                }

                if (marca != -1) {
                    Marca marca1 = new Marca();
                    marca1.setNombre(row[marca]);
                    prod.setMarca(marca1);
                    //System.out.print", marca:" + row[marca]);
                }

                if (disponible > -1) {
                    Boolean disp = stringToBoolean(row[disponible]);
                    if (disp == null) {
                        err = true;
                        disp = false;
                        msgError.add("No se pudo reconocer el valor disponible: row[" + disponible + "]: " + row[disponible] + " (" + codigo0 + " " + nombre0 + ")");
                    }
                    prod.setDisponible(disp);
                }
                else if (disponible == -2 || disponible == -3) {
                    boolean disp = disponible == -2;
                    prod.setDisponible(disp);
                }

                if (costo != -1) {
                    Double v = stringToDouble(row[costo]);
                    if (v == null) {
                        err = true;
                        v = 0.0;
                        msgError.add("No se pudo reconocer el valor costo: row[" + costo + "]: " + row[costo] + " (" + codigo0 + " " + nombre0 + ")");
                    }
                    prod.setPrecioCosto(v);

                    //System.out.print", costo" + v);
                }

                if (precio1 != -1) {
                    Double v = stringToDouble(row[precio1]);
                    if (v == null) {
                        err = true;
                        v = 0.0;
                        msgError.add("No se pudo reconocer el valor precio1: row[" + precio1 + "]: " + row[precio1] + " (" + codigo0 + " " + nombre0 + ")");
                    }
                    prod.setPrecio1(v);
                    //System.out.print", precio1" + v);
                }
                if (precio2 != -1) {
                    Double v = stringToDouble(row[precio2]);
                    if (v == null) {
                        err = true;
                        v = 0.0;
                        msgError.add("No se pudo reconocer el valor precio2: row[" + precio2 + "]: " + row[precio2] + " (" + codigo0 + " " + nombre0 + ")");
                    }
                    prod.setPrecio2(v);
                    //System.out.print", precio2" + v);
                }

                if (precio3 != -1) {
                    Double v = stringToDouble(row[precio3]);
                    if (v != null) {
                        prod.setPrecio3(v);
                    }
                    //System.out.print", precio1" +v);
                }
                if (esExcento > -1) {
                    Boolean v = stringToBoolean(row[esExcento]);
                    if (v == null) {
                        err = true;
                        v = false;
                        msgError.add("No se pudo reconocer el valor Excento: row[" + esExcento + "]: " + row[esExcento] + " (" + codigo0 + " " + nombre0 + ")");
                    }
                    prod.setPrecioIncluyeImpuesto(v);
                    //System.out.print", esExcento" + v);
                } else if (esExcento == -2 || esExcento == -3) {
                    boolean disp = esExcento == -2;
                    prod.setPrecioIncluyeImpuesto(disp);
                    //System.out.print", esExcento" + disp);
                }

                if (stockCritico != -1) {
                    Integer v = stringToInt(row[stockCritico]);
                    if (v == null) {
                        err = true;
                        v = 0;
                        msgError.add("No se pudo reconocer el valor stockCritico: row[" + stockCritico + "]: " + row[stockCritico] + " (" + codigo0 + " " + nombre0 + ")");
                    }
                    prod.setStockCritico(v);
                    //System.out.print", stockCritico" + v);
                }

                if (isServicio > -1) {
                    Boolean v = stringToBoolean(row[isServicio]);
                    if (v == null) {
                        err = true;
                        v = false;
                        msgError.add("No se pudo reconocer el valor isServicio: row[" + isServicio + "]: " + row[isServicio] + " (" + codigo0 + " " + nombre0 + ")");
                    }
                    prod.setNoRequiereStock(v);
                    //System.out.print", isServicio" + v);
                } else if (isServicio == -2 || isServicio == -3) {
                    boolean disp = isServicio == -2;
                    prod.setNoRequiereStock(disp);
                    //System.out.print", isServicio" + disp);
                }

                if (cantidadDisponible != -1) {
                    Integer v = stringToInt(row[cantidadDisponible]);
                    if (v == null) {
                        err = true;
                        v = 0;
                        msgError.add("No se pudo reconocer el valor cantidadDisponible: row[" + cantidadDisponible + "]: " + row[cantidadDisponible] + " (" + codigo0 + " " + nombre0 + ")");
                    }
                    prod.setCantidadDisponible(v);
                    //System.out.print", cantidadDisponible" + row[cantidadDisponible]);
                }

                try {
                    guardar(prod);
                } catch (OperationDBException exc) {
                    countErr++;
                    msgError.add("Error al guardar el producto: "+exc.getMessage());
                }

                if (err) {
                    countWarning++;
                } else {
                    countOk++;
                }
            }
        }*/

        alive = false;

/*        System.out.println("Registro: "+currentCount);
        System.out.println("Registro OK: "+countOk);
        System.out.println("Registro Warning: "+countWarning);
        System.out.println("Registro Error: "+countErr);*/

        for(String msg:msgError){
            LoggerApp.error(msg);
        }
    }


    /*private synchronized void guardar(Producto producto)throws OperationDBException{
        AdministracionProducto administracionProducto = new AdministracionProducto();
        try{
            administracionProducto.guardar(producto);
            if(producto.getCantidadDisponible() > 0){
                ConsultaStock consultaStock = new ConsultaStock();
                Stock stock = consultaStock.obtenerStock(producto.getID(),CONSTANTS.ALMACEN.getID());
                //Stock stock = producto.getStock();
                if(stock == null) {
                    stock = new Stock();
                    stock.setProductoID(producto.getID());
                    stock.setAlmacenID(CONSTANTS.ALMACEN.getID());
                }

                stock.addCantidad(producto.getCantidadDisponible());


                String fechaISO8601 = ObtenerNumeroTransaccion.fechaTransaccion();
                int count = ObtenerNumeroTransaccion.nroDocumento(CONSTANTS.TIPODOCUMENTO_INVENTARIO.getID());
                String numero = ObtenerNumeroTransaccion.fotmatoNroDocumento(count, CONSTANTS.TIPODOCUMENTO_INVENTARIO.getCodigo());

                Documento documento = new Documento();
                documento.setUsuario(CONSTANTS.USUARIO);
                documento.setNumero(numero);
                documento.setFechaEmision(fechaISO8601);
                documento.setFechaActualizacion(fechaISO8601);
                documento.setNota("Inventario-Stock desde archivo");
                documento.setTipoDocumento(CONSTANTS.TIPODOCUMENTO_INVENTARIO);
                documento.setAlmacen(CONSTANTS.ALMACEN);
                documento.setEstado(EstadoDocumento.Disponible.getValor());//Es 0 por que no requiere si es pagado

                DocumentoItem documentoItem = new DocumentoItem();
                documentoItem.setCantidad(producto.getCantidadDisponible());
                documentoItem.setDocumento(documento);
                documentoItem.setProducto(producto);

                AdministracionDocumento administracionDocumento = new AdministracionDocumento();
                administracionDocumento.guardarInventario(documentoItem, stock);

                AdministracionStock administracionStock = new AdministracionStock();
                administracionStock.save(stock);
            }

        } catch (OperationDBException exc) {
            LoggerApp.error(exc.getMessage());
            throw new OperationDBException(exc.getMessage());
        }
    }*/

    private Boolean stringToBoolean(String value){
        Boolean rtn = null;
        if(value!=null && value.length() > 0){
            if(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
                rtn = value.equalsIgnoreCase("true");
            }
            else if(value.equalsIgnoreCase("si") ||value.equalsIgnoreCase("yes")) {
                rtn = true;
            }
            else if(value.equalsIgnoreCase("no")) {
                rtn = false;
            }
            else if(value.equalsIgnoreCase("0") ||value.equalsIgnoreCase("1")) {
                rtn = value.equalsIgnoreCase("1");
            }
        }
        return rtn;
    }

    private Double stringToDouble(String value){
        Double rtn = null;

        if(value!=null && value.length() > 0){

            try {
                rtn = Double.parseDouble(value);
            }catch (NumberFormatException exc){}
        }
        return rtn;
    }

    private Integer stringToInt(String value){
        Integer rtn = null;

        if(value!=null && value.length() > 0){
            if(value.indexOf(".")!=-1){
                Double d = Double.parseDouble(value);
                int a = (int)Math.round(d);
                value = String.valueOf(a);
            }
            try {
                rtn = Integer.parseInt(value);
            }catch (NumberFormatException exc){}
        }
        return rtn;
    }


    public String[] getNameColumns() {
        return nameColumns;
    }

    private class RowProduct{
        private String row[];
        public RowProduct(String row[]){
            this.row = row;
        }

        public String getItem(int index){
            return row[index];
        }
    }

    public int getAmountProduct(){
        return amountProd;
    }
    public int getCurrentCount(){
        return currentCount;
    }

    public boolean isAlive(){
        return alive;
    }

    public void runExport(){
        alive = true;
    }
    public void stopExport(){
        alive = false;
    }

    public List<String> getMsgError(){
        return msgError;
    }
}
