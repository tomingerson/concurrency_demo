package concurrency.fixes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Created by tom on 04.06.2017.
 */
@RestController
public class AppleShopReentrant {

    private final ReentrantLock lock = new ReentrantLock(true);

    private final Map<String, Set<String>> store = new HashMap<>();

    @PostConstruct
    private void initStore() {
        Set<String> grannySmith = new HashSet<>();
        grannySmith.add("gs_1");
        grannySmith.add("gs_2");
        store.put("grannySmith", grannySmith);
    }

    @PostMapping("/appleShop/{appleType}")
    public ResponseEntity<String> buyApples(@PathVariable final String appleType) {
        // TODO use the lock to avoid selling the same apple multiple times
        final Set<String> applesOfType = store.get(appleType);
        final Optional<String> pickedApple = applesOfType.stream().findFirst();
        pickedApple.ifPresent(applesOfType::remove);
        return pickedApple.map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.noContent()::build);
    }
}
