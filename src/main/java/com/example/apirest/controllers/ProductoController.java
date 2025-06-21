package com.example.apirest.controllers;

import com.example.apirest.dto.ProductoConTallesDTO;
import com.example.apirest.dto.ProductoDTO;
import com.example.apirest.entities.Producto;
import com.example.apirest.services.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductoController extends BaseController<Producto, Long>{

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        super(productoService);
        this.productoService = productoService;
    }

    // Override the base controller methods with compatible return types
    @Override
    @GetMapping()
    public ResponseEntity<List<Producto>> listar() throws Exception {
        return super.listar();
    }

    @Override
    @GetMapping("/{id}")
    public Optional<Producto> buscarPorId(@PathVariable Long id) throws Exception {
        return super.buscarPorId(id);
    }

    // Add new methods with different names to return DTOs
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

    // Update other methods to return DTOs
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

    @GetMapping("/disponibles/talle/{id}")
    public ResponseEntity<List<ProductoDTO>> buscarDisponiblesPorTalle(@PathVariable Long id) {
        List<Producto> productos = productoService.buscarDisponiblesPorTalle(id);
        List<ProductoDTO> productoDTOs = productoService.convertToDTOList(productos);
        return ResponseEntity.ok(productoDTOs);
    }

    // Update the image upload methods to return DTOs
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

    @PostMapping(value = "/{id}/imagenes-adicionales", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> subirImagenesAdicionales(@PathVariable Long id, @RequestParam("files") List<MultipartFile> files) {
        try {
            if (files.isEmpty()) {
                return ResponseEntity.badRequest().body("No se han proporcionado archivos");
            }

            Producto producto = productoService.subirImagenesAdicionales(id, files);
            ProductoDTO productoDTO = productoService.convertToDTO(producto);
            return ResponseEntity.ok(productoDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir las imágenes: " + e.getMessage());
        }
    }

    // Update the create method to return a DTO
    @PostMapping("/con-talles")
    public ResponseEntity<?> crearProductoConTalles(@RequestBody ProductoConTallesDTO productoConTallesDTO) {
        try {
            Producto productoCreado = productoService.crearProductoConTalles(
                    productoConTallesDTO.getProducto(),
                    productoConTallesDTO.getTallesConStock()
            );
            ProductoDTO productoDTO = productoService.convertToDTO(productoCreado);
            return ResponseEntity.status(HttpStatus.CREATED).body(productoDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al crear producto con talles: " + e.getMessage());
        }
    }

    @PutMapping("/con-talles/{id}")
    public ResponseEntity<?> actualizarProductoConTalles(@PathVariable Long id, @RequestBody ProductoConTallesDTO productoConTallesDTO) {
        try {
            // Asegurarse de que el ID en la URL coincida con el ID en el cuerpo
            productoConTallesDTO.getProducto().setId(id);

            Producto productoActualizado = productoService.actualizarProductoConTalles(
                    productoConTallesDTO.getProducto(),
                    productoConTallesDTO.getTallesConStock()
            );
            ProductoDTO productoDTO = productoService.convertToDTO(productoActualizado);
            return ResponseEntity.ok(productoDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al actualizar producto con talles: " + e.getMessage());
        }
    }
}