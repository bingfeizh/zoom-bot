package zoomapi.baseUnit;

import java.util.List;
import java.util.function.Predicate;

public class IPredicate implements Predicate<Message> {

    @Override
    public boolean test(Message message) {

        return true;
    }
}
