package co.grtk.ordering.system.domain.valueobject;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseId<T> {
    private final T value;
}
