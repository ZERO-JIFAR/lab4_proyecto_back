package com.example.apirest.controllers;

import com.example.apirest.dto.ProductoConTallesDTO;
import com.example.apirest.entities.Producto;
import com.example.apirest.services.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController extends BaseController<Producto, Long>{

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        super(productoService);
        this.productoService = productoService;
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(productoService.buscarPorNombre(nombre));
    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity<List<Producto>> buscarPorCategoria(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.buscarPorCategoria(id));
    }

    @GetMapping("/disponibles/talle/{id}")
    public ResponseEntity<List<Producto>> buscarDisponiblesPorTalle(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.buscarDisponiblesPorTalle(id));
    }

    /**
     * Crea un producto con sus talles y stock correspondiente
     * @param productoConTallesDTO DTO con los datos del producto y los talles con su stock
     * @return El producto creado con sus talles y stock
     */
    @PostMapping("/con-talles")
    public ResponseEntity<?> crearProductoConTalles(@RequestBody ProductoConTallesDTO productoConTallesDTO) {
        try {
            Producto productoCreado = productoService.crearProductoConTalles(
                    productoConTallesDTO.getProducto(), 
                    productoConTallesDTO.getTallesConStock()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(productoCreado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al crear producto con talles: " + e.getMessage());
        }
    }

    /**
     * Sube una imagen principal para un producto
     * @param id ID del producto
     * @param file Archivo de imagen a subir
     * @return El producto actualizado con la URL de la imagen
     */
    @PostMapping(value = "/{id}/imagen", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> subirImagenPrincipal(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("El archivo está vacío");
            }

            Producto producto = productoService.subirImagenPrincipal(id, file);
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir la imagen: " + e.getMessage());
        }
    }

    /**
     * Sube imágenes adicionales para un producto
     * @param id ID del producto
     * @param files Lista de archivos de imagen a subir
     * @return El producto actualizado con las URLs de las imágenes
     */
    @PostMapping(value = "/{id}/imagenes-adicionales", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> subirImagenesAdicionales(@PathVariable Long id, @RequestParam("files") List<MultipartFile> files) {
        try {
            if (files.isEmpty()) {
                return ResponseEntity.badRequest().body("No se han proporcionado archivos");
            }

            Producto producto = productoService.subirImagenesAdicionales(id, files);
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir las imágenes: " + e.getMessage());
        }
    }
}
