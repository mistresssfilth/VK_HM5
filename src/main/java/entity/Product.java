package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
public final class Product {
    private final int id;
    private final @NotNull String name;
    private final @NotNull String code;

}
