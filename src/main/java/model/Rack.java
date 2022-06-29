package model;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Data
public class Rack {
    private final int id;
    private final Map<String ,BookCopy> booksMap;
    private final int size;
    private final BookCopy[] rack;


    public Rack(int id, int size) {
        this.id = id;
        this.size = size;
        this.booksMap = new HashMap<>();
        rack = new BookCopy[size];
    }
}
