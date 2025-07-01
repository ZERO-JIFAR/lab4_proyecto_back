package com.example.apirest.services;

import com.example.apirest.dto.ProductoConColoresDTO;
import com.example.apirest.dto.ProductoDTO;
import com.example.apirest.entities.*;
import com.example.apirest.repositories.ProductoRepository;
import com.example.apirest.repositories.TalleRepository;
import com.example.apirest.repositories.TalleColorProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService extends BaseService<Producto, Long> {

    private final ProductoRepository productoRepository;
    private final TalleRepository talleRepository;
    private final CategoriaService categoriaService;
    private final TalleColorProductoRepository talleColorProductoRepository;

    @Autowired
    public ProductoService(
            ProductoRepository productoRepository,
            TalleRepository talleRepository,
            CategoriaService categoriaService,
            TalleColorProductoRepository talleColorProductoRepository
    ) {
        super(productoRepository);
        this.productoRepository = productoRepository;
        this.talleRepository = talleRepository;
        this.categoriaService = categoriaService;
        this.talleColorProductoRepository = talleColorProductoRepository;
    }

    @Transactional
    public Producto crearProductoConColores(ProductoConColoresDTO dto) throws Exception {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setPrecioOriginal(dto.getPrecioOriginal());
        producto.setDescripcion(dto.getDescripcion());
        producto.setMarca(dto.getMarca());
        producto.setImagenUrl(dto.getImagenUrl());

        if (dto.getCategoriaId() != null) {
            Categoria categoria = categoriaService.buscarPorId(dto.getCategoriaId())
                    .orElseThrow(() -> new Exception("Categoría no encontrada con ID: " + dto.getCategoriaId()));
            producto.setCategoria(categoria);
        } else {
            producto.setCategoria(null);
        }

        List<ColorProducto> colores = new ArrayList<>();
        for (ProductoConColoresDTO.ColorDTO colorDTO : dto.getColores()) {
            ColorProducto colorProducto = new ColorProducto();
            colorProducto.setColor(colorDTO.getColor());
            colorProducto.setImagenUrl(colorDTO.getImagenUrl());
            colorProducto.setImagenesAdicionales(colorDTO.getImagenesAdicionales());
            colorProducto.setProducto(producto);

            List<TalleColorProducto> tallesColor = new ArrayList<>();
            for (ProductoConColoresDTO.TalleStockDTO talleDTO : colorDTO.getTalles()) {
                Talle talle = talleRepository.findById(talleDTO.getTalleId())
                        .orElseThrow(() -> new Exception("Talle no encontrado con ID: " + talleDTO.getTalleId()));
                TalleColorProducto tcp = new TalleColorProducto();
                tcp.setColorProducto(colorProducto);
                tcp.setTalle(talle);
                tcp.setStock(talleDTO.getStock());
                tallesColor.add(tcp);
            }
            colorProducto.setTallesColor(tallesColor);
            colores.add(colorProducto);
        }
        producto.setColores(colores);

        // Guardar producto y cascada (asegúrate que las relaciones tengan CascadeType.ALL)
        return productoRepository.save(producto);
    }

    @Override
    public Producto crear(Producto producto) {
        return productoRepository.save(producto);
    }

    // Resta stock para un color y talle específico
    public void restarStockColorTalle(Long colorProductoId, Long talleId, Integer cantidad) throws Exception {
        TalleColorProducto tcp = talleColorProductoRepository
                .findByColorProductoIdAndTalleId(colorProductoId, talleId)
                .orElseThrow(() -> new Exception("No existe combinación de color y talle para restar stock"));

        if (tcp.getStock() < cantidad) {
            throw new Exception("Stock insuficiente para restar");
        }
        tcp.setStock(tcp.getStock() - cantidad);
        talleColorProductoRepository.save(tcp);
    }

    // ----------- MÉTODOS PARA DTO -----------

    public ProductoDTO convertToDTO(Producto producto) {
        if (producto == null) return null;
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setCantidad(producto.getCantidad());
        dto.setPrecio(producto.getPrecio());
        dto.setPrecioOriginal(producto.getPrecioOriginal());
        dto.setDescripcion(producto.getDescripcion());
        dto.setMarca(producto.getMarca());
        dto.setImagenUrl(producto.getImagenUrl());
        dto.setEliminado(producto.isEliminado()); // <-- Usa isEliminado() para boolean


        // Categoria
        if (producto.getCategoria() != null) {
            ProductoDTO.CategoriaDTO catDTO = new ProductoDTO.CategoriaDTO();
            catDTO.setId(producto.getCategoria().getId());
            catDTO.setNombre(producto.getCategoria().getNombre());
            if (producto.getCategoria().getTipo() != null) {
                ProductoDTO.TipoDTO tipoDTO = new ProductoDTO.TipoDTO();
                tipoDTO.setId(producto.getCategoria().getTipo().getId());
                tipoDTO.setNombre(producto.getCategoria().getTipo().getNombre());
                catDTO.setTipo(tipoDTO);
            }
            dto.setCategoria(catDTO);
        }

        // Colores
        List<ProductoDTO.ColorDTO> coloresDTO = new ArrayList<>();
        if (producto.getColores() != null) {
            for (ColorProducto color : producto.getColores()) {
                ProductoDTO.ColorDTO colorDTO = new ProductoDTO.ColorDTO();
                colorDTO.setId(color.getId());
                colorDTO.setColor(color.getColor());
                colorDTO.setImagenUrl(color.getImagenUrl());
                colorDTO.setImagenesAdicionales(color.getImagenesAdicionales());

                // Talles
                List<ProductoDTO.TalleStockDTO> tallesDTO = new ArrayList<>();
                if (color.getTallesColor() != null) {
                    for (TalleColorProducto tcp : color.getTallesColor()) {
                        ProductoDTO.TalleStockDTO talleDTO = new ProductoDTO.TalleStockDTO();
                        talleDTO.setTalleId(tcp.getTalle().getId());
                        talleDTO.setTalleValor(tcp.getTalle().getValor());
                        talleDTO.setStock(tcp.getStock());
                        tallesDTO.add(talleDTO);
                    }
                }
                colorDTO.setTalles(tallesDTO);
                coloresDTO.add(colorDTO);
            }
        }
        dto.setColores(coloresDTO);

        return dto;
    }

    public List<ProductoDTO> convertToDTOList(List<Producto> productos) {
        List<ProductoDTO> dtos = new ArrayList<>();
        if (productos != null) {
            for (Producto p : productos) {
                dtos.add(convertToDTO(p));
            }
        }
        return dtos;
    }

    // ----------- MÉTODOS AGREGADOS PARA EL CONTROLLER -----------
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findAll().stream()
                .filter(p -> p.getNombre() != null && p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .toList();
    }

    public List<Producto> buscarPorCategoria(Long categoriaId) {
        return productoRepository.findAll().stream()
                .filter(p -> p.getCategoria() != null && p.getCategoria().getId().equals(categoriaId))
                .toList();
    }

    // ----------- MÉTODO PARA SUBIR IMAGEN PRINCIPAL -----------
    public Producto subirImagenPrincipal(Long id, MultipartFile file) throws Exception {
        Optional<Producto> optionalProducto = productoRepository.findById(id);
        if (optionalProducto.isEmpty()) {
            throw new Exception("Producto no encontrado con ID: " + id);
        }
        Producto producto = optionalProducto.get();

        // Ruta donde se guardará la imagen (ajusta según tu entorno)
        String uploadDir = "uploads/productos/";
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String fileName = "producto_" + id + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File dest = new File(uploadDir + fileName);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new Exception("Error al guardar la imagen: " + e.getMessage());
        }

        // Guarda la URL relativa o absoluta según tu frontend
        producto.setImagenUrl("/" + uploadDir + fileName);
        return productoRepository.save(producto);
    }

    public Categoria getCategoriaById(Long id) throws Exception {
        return categoriaService.buscarPorId(id)
                .orElseThrow(() -> new Exception("Categoría no encontrada con ID: " + id));
    }


}