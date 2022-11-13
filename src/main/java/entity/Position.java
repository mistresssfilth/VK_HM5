package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
public class Position {
    private final @NotNull int id;
    private final @NotNull int price;
    private final @NotNull int productId;
    private final @NotNull int count;
}
