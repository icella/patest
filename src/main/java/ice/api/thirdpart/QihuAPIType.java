package ice.api.thirdpart;

/**
 * Created by lla on 17-5-4.
 */
public enum  QihuAPIType {
    TAG("tag"),
    DIRECT("direct"),
    COMMON("common");

    private String name;

    QihuAPIType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
