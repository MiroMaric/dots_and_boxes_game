package rs.miromaric.dotsandboxes.client.forms.squere;

import java.util.Objects;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

/**
 *
 * @author miro
 */
public class Edge {

    private final Shape shape;
    private Played played;
    private final boolean horisontal;
    private final int position;
    private final double dx;
    private final double dy;

    public Edge(Shape shape, Played played, boolean horisontal, int position, double dx, double dy) {
        this.shape = shape;
        this.setPlayed(played);
        this.horisontal = horisontal;
        this.position = position;
        this.dx = dx;
        this.dy = dy;
        addHoverListener();
    }
    

    public Edge(Shape shape,boolean horisontal, int position,double dx, double dy) {
        this(shape, Played.NONE,horisontal, position, dx, dy);
    }

    
    private void addHoverListener() {
        shape.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (played == Played.NONE) {
                if (newValue) {
                    shape.setFill(Color.BLACK);
                    shape.getStrokeDashArray().addAll(2d);
                } else {
                    shape.setFill(Color.TRANSPARENT);
                    shape.getStrokeDashArray().clear();
                }
            }
        });
    }

    public Shape getShape() {
        return shape;
    }

    public boolean isHorisontal() {
        return horisontal;
    }

    public int getPosition() {
        return position;
    }

    public Played getPlayed() {
        return played;
    }
    
    public final void setPlayed(Played played) {
        this.played = played;
        shape.setFill(Color.valueOf(played.getColor()));
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.played);
        hash = 67 * hash + (this.horisontal ? 1 : 0);
        hash = 67 * hash + this.position;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Edge other = (Edge) obj;
        if (this.horisontal != other.horisontal) {
            return false;
        }
        if (this.position != other.position) {
            return false;
        }
        if (this.played != other.played) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Edge{played=").append(played);
        sb.append(", horisontal=").append(horisontal);
        sb.append(", position=").append(position);
        sb.append('}');
        return sb.toString();
    }

}
