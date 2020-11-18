package junit.dojo;

public class AService {

    public static final String FAKE = "FAKE";
    private final BService bService;
    private final CService cService;

    public AService(BService bService, CService cService) {
        this.bService = bService;
        this.cService = cService;
    }

    public String run(String name) {
        if (FAKE.equals(name)) {
            name = bService.generateOne();
        }
        cService.increaseCounter(name);
        return name;
    }

    public String mayThrowException(boolean isSafe, boolean isIdempotent) {
        if (!isSafe || !isIdempotent) {
            throw new RuntimeException();
        }
        return "ok";
    }
}
