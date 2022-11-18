package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
public final class Organization {
    private @NotNull int id;
    private @NotNull String name;
    private @NotNull int inn;
    private @NotNull int checkingAccount;
}
