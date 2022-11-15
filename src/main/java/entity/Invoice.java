package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.sql.Date;


@Data
@AllArgsConstructor
public final class Invoice {
    private final @NotNull int id;
    private final @NotNull Date date;
    private final @NotNull int orgId;
}
