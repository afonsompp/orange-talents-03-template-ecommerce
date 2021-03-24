package br.com.mercadolivre.product.utils;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface CloudSender<T, E> {

	List<T> send(E prod, List<MultipartFile> images, String host);
}
