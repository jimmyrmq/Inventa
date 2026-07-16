package com.djm.inventa.util;

import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class FechaUtil {

    public static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Timestamp ahora() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

    public static LocalDateTime parseFecha(String textoFecha) {
        LocalDateTime fecha = LocalDateTime.now();
        try {
            fecha = LocalDateTime.parse(textoFecha, FORMATO_FECHA);
        }catch (DateTimeException exc){

        }

        return fecha;
    }

    public static String parseFecha(LocalDateTime fecha){

        //String fechaFormateada = fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        DateTimeFormatter formato = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.MEDIUM)
                .withLocale(Locale.getDefault());

        String fechaFormateada = fecha.format(formato);


        return fechaFormateada;
    }
}
