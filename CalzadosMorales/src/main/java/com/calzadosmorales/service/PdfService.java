package com.calzadosmorales.service;

import java.awt.Color;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.math.BigDecimal;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.calzadosmorales.entity.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class PdfService {

    public void exportarVentaPDF(HttpServletResponse response, Venta venta) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // --- FUENTES ---
        Font fontEmpresa = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, new Color(154, 85, 255));
        Font fontRucBox = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.BLACK);
        Font fontDetalleBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.BLACK);
        Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.BLACK);

        // --- CABECERA: EMPRESA Y RUC ---
        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new float[]{2.2f, 1.8f});

        PdfPCell cellEmpresa = new PdfPCell();
        cellEmpresa.setBorder(Rectangle.NO_BORDER);
        cellEmpresa.addElement(new Paragraph("CALZADOS MORALES S.A.C.", fontEmpresa));
        cellEmpresa.addElement(new Paragraph("DIRECCIÓN: JIRÓN JUNÍN 847 TIENDA 170", fontNormal)); // Edita esto cuando tengas el dato
        cellEmpresa.addElement(new Paragraph("CONTACTO: 943291489", fontNormal));  // Edita esto cuando tengas el dato
        headerTable.addCell(cellEmpresa);

        String tipo = (venta.getCliente() instanceof PersonaJuridica) ? "FACTURA" : "BOLETA";
        PdfPCell cellRuc = new PdfPCell();
        cellRuc.setPadding(10);
        cellRuc.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellRuc.addElement(new Paragraph("R.U.C. 202550371082", fontRucBox));
        Paragraph pTipo = new Paragraph(tipo + " ELECTRÓNICA", fontRucBox);
        pTipo.setAlignment(Element.ALIGN_CENTER);
        cellRuc.addElement(pTipo);
        Paragraph pSerie = new Paragraph("F001 - " + String.format("%06d", venta.getId_venta()), fontRucBox);
        pSerie.setAlignment(Element.ALIGN_CENTER);
        cellRuc.addElement(pSerie);
        headerTable.addCell(cellRuc);
        
        document.add(headerTable);
        document.add(new Paragraph("\n"));

        // --- DATOS DEL CLIENTE (SIN DIRECCIÓN) ---
        PdfPTable clienteTable = new PdfPTable(1);
        clienteTable.setWidthPercentage(100);
        
        String nomCliente = "";
        String docCliente = "";
        if (venta.getCliente() instanceof PersonaNatural) {
            PersonaNatural pn = (PersonaNatural) venta.getCliente();
            nomCliente = pn.getNombre() + " " + pn.getApellido();
            docCliente = "DNI: " + pn.getDni();
        } else {
            PersonaJuridica pj = (PersonaJuridica) venta.getCliente();
            nomCliente = pj.getRazonSocial();
            docCliente = "RUC: " + pj.getRuc();
        }

        PdfPCell cellCli = new PdfPCell();
        cellCli.setPadding(8);
        cellCli.setBorderColor(new Color(230, 230, 230));
        cellCli.addElement(new Phrase("ADQUIRENTE: " + nomCliente, fontDetalleBold));
        cellCli.addElement(new Phrase("DOCUMENTO: " + docCliente, fontNormal));
        cellCli.addElement(new Phrase("FECHA DE EMISIÓN: " + venta.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), fontNormal));
        clienteTable.addCell(cellCli);
        document.add(clienteTable);
        document.add(new Paragraph("\n"));

        // --- TABLA DE PRODUCTOS ---
        PdfPTable table = new PdfPTable(4); // Quitamos la columna de descuento para simplificar
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1f, 4f, 1.5f, 1.5f});

        String[] headers = {"CANT.", "DESCRIPCIÓN", "P. UNIT", "TOTAL"};
        for (String h : headers) {
            PdfPCell c = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE)));
            c.setBackgroundColor(new Color(154, 85, 255)); 
            c.setHorizontalAlignment(Element.ALIGN_CENTER);
            c.setPadding(6);
            c.setBorderColor(Color.WHITE);
            table.addCell(c);
        }

        for (DetalleVenta d : venta.getDetalles()) {
            PdfPCell cCant = new PdfPCell(new Phrase(String.valueOf(d.getCantidad()), fontNormal));
            cCant.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cCant);

            table.addCell(new Phrase(d.getProducto().getNombre() + " (Talla: " + d.getProducto().getTalla().getNombre() + ")", fontNormal));
            
            PdfPCell cPre = new PdfPCell(new Phrase("S/ " + d.getPrecio(), fontNormal));
            cPre.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cPre);

            PdfPCell cTot = new PdfPCell(new Phrase("S/ " + d.getSubtotal(), fontNormal));
            cTot.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cTot);
        }
        document.add(table);

        // --- SECCIÓN DE TOTALES ALINEADA ---
        // Creamos una tabla invisible de 2 columnas para empujar los totales a la derecha de forma ordenada
        PdfPTable footerTable = new PdfPTable(2);
        footerTable.setWidthPercentage(100);
        footerTable.setWidths(new float[]{3.5f, 1.5f}); // La primera columna empuja a la segunda
        footerTable.setSpacingBefore(15);

        // Celda vacía (Empuje)
        PdfPCell emptyCell = new PdfPCell(new Phrase(""));
        emptyCell.setBorder(Rectangle.NO_BORDER);
        footerTable.addCell(emptyCell);

        // Celda de Totales (Con borde sutil)
        PdfPCell totalsCell = new PdfPCell();
        totalsCell.setPadding(5);
        totalsCell.setBorderColor(new Color(200, 200, 200));
        
        BigDecimal gravada = venta.getTotal().divide(new BigDecimal("1.18"), 2, java.math.RoundingMode.HALF_UP);
        BigDecimal igv = venta.getTotal().subtract(gravada);

        totalsCell.addElement(crearFilaTotal("OP. GRAVADA:", "S/ " + gravada, fontNormal));
        totalsCell.addElement(crearFilaTotal("I.G.V. (18%):", "S/ " + igv, fontNormal));
        totalsCell.addElement(crearFilaTotal("TOTAL A PAGAR:", "S/ " + venta.getTotal(), fontDetalleBold));
        
        footerTable.addCell(totalsCell);
        document.add(footerTable);

        // --- PIE DE PÁGINA ---
        document.add(new Paragraph("\n\nRepresentación impresa de la " + tipo + " Electrónica.", fontNormal));
        document.add(new Paragraph("Gracias por su preferencia.", fontNormal));

        document.close();
    }

    // Función auxiliar para alinear textos dentro de la celda de totales
    private Paragraph crearFilaTotal(String etiqueta, String valor, Font fuente) {
        Paragraph p = new Paragraph();
        p.add(new Chunk(etiqueta + " ", fuente));
        p.add(new Chunk(valor, fuente));
        p.setAlignment(Element.ALIGN_RIGHT);
        return p;
    }
}