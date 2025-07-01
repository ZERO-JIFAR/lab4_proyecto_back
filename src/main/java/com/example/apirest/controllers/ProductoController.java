package com.example.apirest.controllers;

import com.example.apirest.dto.ProductoConColoresDTO;
import com.example.apirest.dto.ProductoDTO;
import com.example.apirest.dto.ProductoUpdateDTO;
import com.example.apirest.entities.Producto;
import com.example.apirest.services.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductoController extends BaseController<Producto, Long> {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        super(productoService);
        this.productoService = productoService;
    }

    @Override
    @GetMapping()
    public ResponseEntity<List<Producto>> listar() throws Exception {
        return super.listar();
    }
    @PatchMapping("/{id}/descuento")
    public ResponseEntity<?> actualizarDescuento(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body
    ) {
        try {
            Producto producto = productoService.buscarPorId(id).orElseThrow(() -> new Exception("No encontrado"));
            if (body.containsKey("precio")) {
                producto.setPrecio(Double.valueOf(body.get("precio").toString()));
            }
            if (body.containsKey("precioOriginal")) {
                Object po = body.get("precioOriginal");
                producto.setPrecioOriginal(po == null ? null : Double.valueOf(po.toString()));
            }
            productoService.crear(producto);
            return ResponseEntity.ok(productoService.convertToDTO(producto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    // NUEVO: Resta stock por color y talle
    @PutMapping("/{productoId}/restar-stock")
    public ResponseEntity<?> restarStock(
            @PathVariable Long productoId,
            @RequestBody Map<String, Object> payload
    ) {
        try {
            Long colorProductoId = Long.valueOf(payload.get("colorProductoId").toString());
            Long talleId = Long.valueOf(payload.get("talleId").toString());
            Integer cantidad = Integer.valueOf(payload.get("cantidad").toString());
            productoService.restarStockColorTalle(colorProductoId, talleId, cantidad);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al restar stock: " + e.getMessage());
        }
    }

    @Override
    @GetMapping("/{id}")
    public Optional<Producto> buscarPorId(@PathVariable Long id) throws Exception {
        return super.buscarPorId(id);
    }

    @GetMapping("/dto")
    public ResponseEntity<List<ProductoDTO>> listarDTO() throws Exception {
        List<Producto> productos = productoService.listar();
        List<ProductoDTO> productoDTOs = productoService.convertToDTOList(productos);
        return ResponseEntity.ok(productoDTOs);
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<ProductoDTO> buscarDTOPorId(@PathVariable Long id) throws Exception {
        Optional<Producto> optionalProducto = productoService.buscarPorId(id);
        if (optionalProducto.isPresent()) {
            ProductoDTO productoDTO = productoService.convertToDTO(optionalProducto.get());
            return ResponseEntity.ok(productoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoDTO>> buscarPorNombre(@RequestParam String nombre) {
        List<Producto> productos = productoService.buscarPorNombre(nombre);
        List<ProductoDTO> productoDTOs = productoService.convertToDTOList(productos);
        return ResponseEntity.ok(productoDTOs);
    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity<List<ProductoDTO>> buscarPorCategoria(@PathVariable Long id) {
        List<Producto> productos = productoService.buscarPorCategoria(id);
        List<ProductoDTO> productoDTOs = productoService.convertToDTOList(productos);
        return ResponseEntity.ok(productoDTOs);
    }

    // Métodos de imágenes
    @PostMapping(value = "/{id}/imagen", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> subirImagenPrincipal(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("El archivo está vacío");
            }
            Producto producto = productoService.subirImagenPrincipal(id, file);
            ProductoDTO productoDTO = productoService.convertToDTO(producto);
            return ResponseEntity.ok(productoDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir la imagen: " + e.getMessage());
        }
    }

    @PostMapping("/con-colores")
    public ResponseEntity<?> crearProductoConColores(@RequestBody ProductoConColoresDTO dto) {
        try {
            Producto producto = productoService.crearProductoConColores(dto);
            ProductoDTO productoDTO = productoService.convertToDTO(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(productoDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al crear producto con colores: " + e.getMessage());
        }
    }




    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarParcial(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body
    ) {
        try {
            Producto producto = productoService.buscarPorId(id).orElseThrow(() -> new Exception("No encontrado"));

            if (body.containsKey("nombre")) producto.setNombre((String) body.get("nombre"));
            if (body.containsKey("precio")) producto.setPrecio(Double.valueOf(body.get("precio").toString()));
            if (body.containsKey("precioOriginal")) producto.setPrecioOriginal(body.get("precioOriginal") == null ? null : Double.valueOf(body.get("precioOriginal").toString()));
            if (body.containsKey("descripcion")) producto.setDescripcion((String) body.get("descripcion"));
            if (body.containsKey("marca")) producto.setMarca((String) body.get("marca"));
            if (body.containsKey("imagenUrl")) producto.setImagenUrl((String) body.get("imagenUrl"));
            if (body.containsKey("categoriaId")) {
                Long categoriaId = Long.valueOf(body.get("categoriaId").toString());
                producto.setCategoria(productoService.getCategoriaById(categoriaId));
            }
            if (body.containsKey("eliminado")) producto.setEliminado(Boolean.parseBoolean(body.get("eliminado").toString()));

            productoService.crear(producto);
            return ResponseEntity.ok(productoService.convertToDTO(producto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
}