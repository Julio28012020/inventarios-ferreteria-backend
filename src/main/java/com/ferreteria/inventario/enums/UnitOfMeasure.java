package com.ferreteria.inventario.enums;

public enum UnitOfMeasure {
    // Conteo
    PIECE("Und", "Unidad"),
    BOX("Caja", "Caja"),
    PACK("Paq", "Paquete"),
    ROLL("Rollo", "Rollo"),
    
    // Longitud
    METER("m", "Metro"),
    INCH("in", "Pulgada"),
    
    // Peso
    KILOGRAM("kg", "Kilogramo"),
    POUND("lb", "Libra"),
    TON("t", "Tonelada"),
    
    // Volumen
    GALLON("gal", "Galón"),
    QUART("1/4 gal", "Cuarto"),
    LITER("L", "Litro"),
    
    // Área
    SQUARE_METER("m²", "Metro Cuadrado");

    private final String symbol;
    private final String description;

    UnitOfMeasure(String symbol, String description) {
        this.symbol = symbol;
        this.description = description;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getDescription() {
        return description;
    }

}
