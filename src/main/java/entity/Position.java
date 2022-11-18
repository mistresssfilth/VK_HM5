package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
public class Position {
    private @NotNull int id;
    private @NotNull int price;
    private @NotNull int productId;
    private @NotNull int count;
    private @NotNull int invoiceId;
}
