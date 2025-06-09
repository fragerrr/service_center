package kz.justdika.service_center.model.enums;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * @author justdika
 */
@Accessors(fluent = true)
@Getter
public enum ClaimStatus {
    ACCEPTANCE("Принятие заявки"),
    PROCESSING("Обработка заявки"),
    REPAIR("Выполнение ремонта"),
    FINISH("Завершение работ");

    public final String value;

    ClaimStatus(String value) {
        this.value = value;
    }
}
