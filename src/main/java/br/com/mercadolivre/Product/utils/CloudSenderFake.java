package br.com.mercadolivre.product.utils;

import static org.springframework.web.util.UriComponentsBuilder.fromPath;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import br.com.mercadolivre.product.model.Product;
import br.com.mercadolivre.product.model.ProductImage;

@Component
@Profile({ "test", "dev" })
public class CloudSenderFake implements CloudSender<ProductImage, Product> {

	public List<ProductImage> send(Product prod, List<MultipartFile> images,
			String host) {
		return images.stream()
				.map(img -> new ProductImage(
						fromPath("product/{id}/pictures/{UUID}/{img}")
								.host(host).scheme("https")
								.buildAndExpand(prod.getId(), UUID.randomUUID(),
										img.getOriginalFilename())
								.toUriString(),
						prod))
				.collect(Collectors.toList());
	}

}
