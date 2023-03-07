package spring.weblux.jwt.models.dto.db;

import java.io.Serializable;
import java.time.LocalDate;

public interface AccountDetails extends Serializable {

    Long getUserId();

    LocalDate getStartDate();

    boolean isEnabled();

    boolean isVisibled();
}
