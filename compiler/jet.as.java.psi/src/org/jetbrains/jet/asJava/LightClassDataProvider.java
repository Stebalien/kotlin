package org.jetbrains.jet.asJava;

import org.jetbrains.annotations.NotNull;

public interface LightClassDataProvider<T extends WithFileStub> {
    @NotNull
    T compute();
}
