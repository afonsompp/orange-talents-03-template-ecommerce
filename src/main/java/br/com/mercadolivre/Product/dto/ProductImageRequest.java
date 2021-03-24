package br.com.mercadolivre.product.dto;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class ProductImageRequest {

	@NotNull
	@Size(min = 1)
	List<MultipartFile> images;

	@Deprecated
	public ProductImageRequest() { // jackson

	}

	public void setImages(List<MultipartFile> images) {
		this.images = images;
	}

	public List<MultipartFile> getImages() {
		return images;
	}

}
