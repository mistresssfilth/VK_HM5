package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
public final class Organization {
    private final @NotNull int id;
    private final @NotNull String name;
    private final @NotNull int inn;
    private final @NotNull int checkingAccount;
}
