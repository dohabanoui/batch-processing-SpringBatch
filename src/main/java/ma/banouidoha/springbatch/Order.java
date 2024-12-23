package ma.banouidoha.springbatch;

// permet de creer des objets immuables
public record Order(Long orderId, String customerName, Double amount) {
}
