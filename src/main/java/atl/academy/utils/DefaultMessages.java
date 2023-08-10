package atl.academy.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DefaultMessages {
    @Value("${MESSAGE.FOUND}") public String FOUND;
    @Value("${MESSAGE.NOT_FOUND}") public String NOT_FOUND;
    @Value("${MESSAGE.SUCCESS_ADD}") public String ADD;
    @Value("${MESSAGE.SUCCESS_MODIFIED}") public String MODIFIED;
    @Value("${MESSAGE.SUCCESS_DELETE}") public String DELETE;
    @Value("${MESSAGE.EMPTY}") public String EMPTY;
}
