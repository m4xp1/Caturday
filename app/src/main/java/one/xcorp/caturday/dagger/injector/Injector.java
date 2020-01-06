package one.xcorp.caturday.dagger.injector;

// TODO: replace on Kotlin class when obtain SAM constructor support in Kotlin 1.4
public interface Injector<Component> {

    void invoke(Component component);
}
