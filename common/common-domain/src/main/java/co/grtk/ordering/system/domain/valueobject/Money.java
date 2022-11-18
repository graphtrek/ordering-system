package co.grtk.ordering.system.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class Money {
    private final BigDecimal amount;

    public boolean isGraterThanZero() {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isGraterThan(@NonNull Money money) {
        return amount != null && amount.compareTo(money.getAmount()) > 0;
    }

    public Money add(@NonNull Money money) {
        return new Money(setScale(amount.add(money.getAmount())));
    }

    public Money subtract(@NonNull Money money) {
        return new Money(setScale(amount.subtract(money.getAmount())));
    }

    public Money multiply(int multiplier) {
        return new Money(setScale(amount.multiply(new BigDecimal(multiplier))));
    }

    private BigDecimal setScale(@NonNull BigDecimal input) {
        return input.setScale(2, RoundingMode.HALF_EVEN);
    }
}