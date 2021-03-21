package br.com.mercadolivre.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import br.com.mercadolivre.product.dto.CategoryRequest;
import br.com.mercadolivre.product.model.Category;
import br.com.mercadolivre.product.repository.CategoryRepository;

@SpringBootTest
public class CategoryRequestTest {

	@MockBean
	CategoryRepository repository;

	@Test
	@DisplayName("Should return category with caregory relationship null")
	public void test1() {
		var request = new CategoryRequest("name", null);

		var category = request.parseToCategory(repository);

		assertEquals(null, category.getCategory());

	}

	@Test
	@DisplayName("Should return category with caregory relationship null")
	public void test2() {

		Category cat = new Category("name");
		Mockito.when(repository.findById(1L)).thenReturn(Optional.of(cat));

		var request = new CategoryRequest("name2", 1L);
		var result = request.parseToCategory(repository);

		assertEquals(cat.getName(), result.getCategory().getName());

	}

}
