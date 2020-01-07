package one.xcorp.caturday.dagger.initializer;

import androidx.annotation.NonNull;

public interface ComponentInitializer<Factory, Component> {

    @NonNull
    Component invoke(@NonNull Factory factory);
}
