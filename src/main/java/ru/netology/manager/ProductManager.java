package ru.netology.manager;

import ru.netology.domain.Book;
import ru.netology.domain.Product;
import ru.netology.domain.Smartphone;
import ru.netology.repository.ProductRepository;

import java.util.ArrayList;

public class ProductManager {
    private ProductRepository repository;

    ProductManager(ProductRepository repository) {
        this.repository = repository;
    }

    public void add(Product product) {
        repository.save(product);
    }

    private boolean matches(Product product, String text) {
        if (text.isEmpty())
            return false;

        if (product.getName().equalsIgnoreCase(text))
            return true;

        if (product instanceof Book) {
            Book book = (Book) product;
            if (book.getAuthor().equalsIgnoreCase(text))
                return true;
        }
        if (product instanceof Smartphone) {
            Smartphone smartphone = (Smartphone) product;
            if (smartphone.getManufacturer().equalsIgnoreCase(text))
                return true;
        }
        return false;

    }

    public Product[] searchBy(String request) {
        Product[] arrayMatches = new Product[0];

        for (Product product : repository.findAll()) {
            if (matches(product, request)) {
                Product[] tmp = new Product[arrayMatches.length + 1];
                System.arraycopy(arrayMatches, 0, tmp, 0, arrayMatches.length);
                tmp[arrayMatches.length] = product;
                arrayMatches = tmp;
            }
        }

        return arrayMatches;
    }
}
