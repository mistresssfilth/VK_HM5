package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
public final class Product {
    private int id;
    private @NotNull String name;
    private @NotNull int code;

}
