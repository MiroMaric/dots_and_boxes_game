package rs.miromaric.dotsandboxes.client.forms.squere;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import rs.miromaric.dotsandboxes.client.game.Scoreboard;
import rs.miromaric.dotsandboxes.common.domain.Game;

/**
 *
 * @author miro
 */
public class SquerePane extends AnchorPane {

    private final int sizeFactor = 100;
    private final int edgeWidth = 5;
    private final double gap = sizeFactor * 0.1;
    private final double size;

    private final int dimension;

    public SquerePane(Game game) {
        this.dimension = game.getDimension();
        this.size = (dimension - 1) * sizeFactor + 2 * gap + edgeWidth;
        this.getStylesheets().add("dotsandboxes/css/squere.css");

        List<Edge> edges = populateSquere();
        Scoreboard.startGame(game, edges, this);
        setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private List<Edge> populateSquere() {
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension - 1; j++) {
                //horisontal
                int hPosition = i * (dimension - 1) + j;
                double hdx = gap + j * sizeFactor;
                double hdy = gap + i * sizeFactor;
                Edge edgeHor = new Edge(new Rectangle(
                        hdx,
                        hdy,
                        sizeFactor + edgeWidth,
                        edgeWidth), true, hPosition, hdx, hdy);

                //vertical
                int vPosition = j * dimension + i;
                double vdx = gap + i * sizeFactor;
                double vdy = gap + j * sizeFactor;
                Edge edge1Ver = new Edge(new Rectangle(
                        vdx,
                        vdy,
                        edgeWidth,
                        sizeFactor + edgeWidth), false, vPosition, vdx, vdy);

                getChildren().add(edgeHor.getShape());
                getChildren().add(edge1Ver.getShape());

                edges.add(edgeHor);
                edges.add(edge1Ver);
            }
        }

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                getChildren().add(new Circle(i * sizeFactor + (gap + edgeWidth / 2), j * sizeFactor + (gap + edgeWidth / 2), 2 * edgeWidth, new Color(0, 0, 0, 1)));
            }
        }

        addWithdrawButton();
        return edges;
    }

    public double getSize() {
        return size;
    }

    private void addWithdrawButton() {
        ImageView iw = new ImageView("icons/surrender.png");
        iw.setFitWidth(80);
        iw.setFitHeight(80);
        iw.setLayoutX((size / 2) - 40);
        iw.setLayoutY(size + gap);
        getChildren().add(iw);

        iw.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure?");
            alert.setHeaderText("Surrender");
            alert.setTitle("SURRENDER");
            alert.showAndWait()
                    .ifPresent(bt -> {
                        if (bt == ButtonType.OK) {
                            Scoreboard.getInstance().surrenderGame();
                        }
                    });
        });
    }

    public void closeBox(Edge edge, Integer closeMove) {
        System.out.println("**********");
        System.out.println(closeMove);
        System.out.println("**********");
        double dx;
        double dy;

        if (edge.isHorisontal()) {
            dx = edge.getDx() + (sizeFactor / 2);
            if (edge.getPosition() < (dimension - 1) * (dimension - 1)) {
                if (closeMove == 0) {
                    dy = edge.getDy() + (sizeFactor / 2);
                } else {
                    dy = edge.getDy() - (sizeFactor / 2);
                }
            } else {
                dy = edge.getDy() - (sizeFactor / 2);
            }
        } else {
            dy = edge.getDy() + (sizeFactor / 2);
            if ((edge.getPosition() + 1) % dimension != 0) {
                if (closeMove == 0) {
                    dx = edge.getDx() + (sizeFactor / 2);
                } else {
                    dx = edge.getDx() - (sizeFactor / 2);
                }
            } else {
                dx = edge.getDx() - (sizeFactor / 2);
            }
        }

        Circle circle = new Circle(dx, dy, sizeFactor / 6);
        if (edge.getPlayed() == Played.COMPUTER) {
            circle.setFill(Color.valueOf(Played.COMPUTER.getColor()));
        } else {
            circle.setFill(Color.valueOf(Played.PLAYER.getColor()));
        }
        getChildren().add(circle);
    }

}
