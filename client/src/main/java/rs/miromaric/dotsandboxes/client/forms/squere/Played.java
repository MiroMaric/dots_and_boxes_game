package rs.miromaric.dotsandboxes.client.forms.squere;

/**
 *
 * @author miro
 */
public enum Played {
    
    PLAYER("blue"),
    COMPUTER("red"),
    NONE("transparent");
    
    private final String color;

    private Played(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
    
}
