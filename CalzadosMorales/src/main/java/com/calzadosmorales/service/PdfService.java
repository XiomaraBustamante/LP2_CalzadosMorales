package com.calzadosmorales.service;

import com.calzadosmorales.entity.*;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PdfService {

    public void exportarVentaPDF(HttpServletResponse response, Venta venta) {
        try {
           
            File file = ResourceUtils.getFile("classpath:reports/comprobante.jrxml");
          
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

            
            Map<String, Object> parameters = new HashMap<>();
            String titulo = "";
            String documento = "";
            String nombreCli = "";

            if (venta.getCliente() instanceof PersonaNatural) {
                PersonaNatural pn = (PersonaNatural) venta.getCliente();
                titulo = "BOLETA DE VENTA ELECTRÓNICA";
                documento = "DNI: " + pn.getDni();
                nombreCli = pn.getNombre() + " " + pn.getApellido();
            } else if (venta.getCliente() instanceof PersonaJuridica) {
                PersonaJuridica pj = (PersonaJuridica) venta.getCliente();
                titulo = "FACTURA ELECTRÓNICA";
                documento = "RUC: " + pj.getRuc();
                nombreCli = pj.getRazonSocial();
            }

            parameters.put("p_titulo", titulo);
            parameters.put("p_cliente", nombreCli);
            parameters.put("p_documento", documento);
            parameters.put("p_total", "S/ " + venta.getTotal());

            // 3. Preparar los datos de la tabla (productos)
            var detallesMap = venta.getDetalles().stream().map(d -> {
                Map<String, Object> item = new HashMap<>();
                item.put("cantidad", d.getCantidad().toString());
                item.put("productoNombre", d.getProducto().getNombre());
                item.put("precioUnitario", d.getPrecio().toString());
                item.put("subtotal", d.getSubtotal().toString());
                return item;
            }).collect(Collectors.toList());

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(detallesMap);

            // 4. Llenar y exportar el reporte
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=comprobante_" + venta.getId_venta() + ".pdf");
            
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

        } catch (Exception e) {
            System.err.println("ERROR GENERANDO PDF: " + e.getMessage());
            e.printStackTrace();
        }
    }
}