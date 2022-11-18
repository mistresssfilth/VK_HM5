package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.sql.Date;


@Data
@AllArgsConstructor
public final class Invoice {
    private  @NotNull int id;
    private  @NotNull Date date;
    private  @NotNull int orgId;
}
