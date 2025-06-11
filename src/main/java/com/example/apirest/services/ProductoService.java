package com.example.apirest.services;

import com.example.apirest.entities.Producto;
import com.example.apirest.entities.Talle;
import com.example.apirest.entities.TalleProducto;
import com.example.apirest.repositories.ProductoRepository;
import com.example.apirest.repositories.TalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ProductoService extends BaseService<Producto, Long> {

    private final TalleRepository talleRepository;
    private final CloudStorageService cloudStorageService;

    @Autowired
    public ProductoService(ProductoRepository productoRepository, TalleRepository talleRepository, CloudStorageService cloudStorageService) {
        super(productoRepository);
        this.talleRepository = talleRepository;
        this.cloudStorageService = cloudStorageService;
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return ((ProductoRepository) baseRepository).findByNombreContainingIgnoreCase(nombre);
    }

    public List<Producto> buscarPorCategoria(Long categoriaId) {
        return ((ProductoRepository) baseRepository).findByCategoria_Id(categoriaId);
    }

    public List<Producto> buscarDisponiblesPorTalle(Long talleId) {
        return ((ProductoRepository) baseRepository).findDisponiblesPorTalle(talleId);
    }

    /**
     * Sube una imagen principal para un producto
     * @param id ID del producto
     * @param file Archivo de imagen a subir
     * @return El producto actualizado con la URL de la imagen
     * @throws Exception Si ocurre algún error durante la subida
     */
    @Transactional
    public Producto subirImagenPrincipal(Long id, MultipartFile file) throws Exception {
        try {
            // Buscar el producto
            Producto producto = buscarPorId(id)
                    .orElseThrow(() -> new Exception("Producto no encontrado con ID: " + id));

            // Subir la imagen al almacenamiento en la nube
            String imageUrl = cloudStorageService.uploadFile(file);

            // Actualizar la URL de la imagen en el producto
            producto.setImagenUrl(imageUrl);

            // Guardar y devolver el producto actualizado
            return actualizar(producto);
        } catch (IOException e) {
            throw new Exception("Error al subir la imagen: " + e.getMessage());
        }
    }

    /**
     * Sube imágenes adicionales para un producto
     * @param id ID del producto
     * @param files Lista de archivos de imagen a subir
     * @return El producto actualizado con las URLs de las imágenes
     * @throws Exception Si ocurre algún error durante la subida
     */
    @Transactional
    public Producto subirImagenesAdicionales(Long id, List<MultipartFile> files) throws Exception {
        try {
            // Buscar el producto
            Producto producto = buscarPorId(id)
                    .orElseThrow(() -> new Exception("Producto no encontrado con ID: " + id));

            // Subir las imágenes al almacenamiento en la nube
            List<String> imageUrls = cloudStorageService.uploadFiles(files);

            // Añadir las URLs de las imágenes a la lista de imágenes adicionales del producto
            producto.getImagenesAdicionales().addAll(imageUrls);

            // Guardar y devolver el producto actualizado
            return actualizar(producto);
        } catch (IOException e) {
            throw new Exception("Error al subir las imágenes: " + e.getMessage());
        }
    }

    /**
     * Crea un producto con sus talles y stock correspondiente
     * @param producto El producto a crear
     * @param tallesConStock Mapa con los IDs de los talles y su stock correspondiente
     * @return El producto creado con sus talles y stock
     * @throws Exception Si ocurre algún error durante la creación
     */
    @Transactional
    public Producto crearProductoConTalles(Producto producto, Map<Long, Integer> tallesConStock) throws Exception {
        try {
            // Guardar primero el producto
            Producto productoGuardado = crear(producto);

            // Agregar los talles con su stock correspondiente
            for (Map.Entry<Long, Integer> entry : tallesConStock.entrySet()) {
                Long talleId = entry.getKey();
                Integer stock = entry.getValue();

                // Obtener el talle
                Talle talle = talleRepository.findById(talleId)
                        .orElseThrow(() -> new Exception("Talle no encontrado con ID: " + talleId));

                // Crear la relación TalleProducto
                TalleProducto talleProducto = new TalleProducto();
                talleProducto.setProducto(productoGuardado);
                talleProducto.setTalle(talle);
                talleProducto.setStock(stock);

                // Agregar a la lista de talles del producto
                productoGuardado.getTallesProducto().add(talleProducto);
            }

            // Guardar el producto actualizado con sus talles
            return actualizar(productoGuardado);
        } catch (Exception ex) {
            throw new Exception("Error al crear producto con talles: " + ex.getMessage());
        }
    }
}
