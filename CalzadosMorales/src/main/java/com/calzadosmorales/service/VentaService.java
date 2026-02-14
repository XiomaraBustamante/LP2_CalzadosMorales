package com.calzadosmorales.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.calzadosmorales.entity.DetalleVenta;
import com.calzadosmorales.entity.Producto;
import com.calzadosmorales.entity.Venta;
import com.calzadosmorales.repository.ProductoRepository;
import com.calzadosmorales.repository.VentaRepository;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    // MÉTODO PARA REGISTRAR LA VENTA (CON TRANSACCIÓN)
    @Transactional 
    public void registrarVenta(Venta venta) {
        
        // 1. Asignar datos automáticos
        venta.setFecha(LocalDateTime.now());
        venta.setEstado("REGISTRADA");

        // 2. Validar Stock y Restar
        for (DetalleVenta detalle : venta.getDetalles()) {
            
            Producto productoEnBd = productoRepository.findById(detalle.getProducto().getId_producto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            if (productoEnBd.getStock() < detalle.getCantidad()) {
                throw new RuntimeException("No hay stock suficiente para: " + productoEnBd.getNombre());
            }

            // RESTAR STOCK
            int nuevoStock = productoEnBd.getStock() - detalle.getCantidad();
            productoEnBd.setStock(nuevoStock);

            productoRepository.save(productoEnBd);
            
            // Vincular detalle a venta
            detalle.setVenta(venta);
        }

        // 3. Guardar Venta
        ventaRepository.save(venta);
    }
    
    // MÉTODO PARA LISTAR TODAS LAS VENTAS
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    // ✅✅✅ AQUÍ ESTÁ EL MÉTODO QUE TE FALTABA ✅✅✅
    public Venta buscarPorId(Integer id) {
        // findById devuelve un Optional, usamos .orElse(null) para sacar el objeto real
        return ventaRepository.findById(id).orElse(null);
    }
}