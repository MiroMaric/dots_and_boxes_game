package rs.miromaric.dotsandboxes.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 *
 * @author miro
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Move implements GeneralEntity {

    private Long id;
    private Long gameId;
    private Integer[][] moves;
    private Boolean[][] squere;
    private Integer moveOrder;

    public Move() {
    }
    
    public Move(Long id) {
        this.id = id;
    }
    
    public Move(Long gameId, Boolean[][] squere) {
        this(squere);
        this.gameId = gameId;
    }
    
    public Move(Boolean[][] squere) {
        this.squere = squere;
    }
    
    public Move(Integer[][] moves, Boolean[][] squere) {
        this.moves = moves;
        this.squere = squere;
    }

    public Move(Long id, Long gameId, Integer[][] moves, Boolean[][] squere, Integer moveOrder) {
        this.id = id;
        this.gameId = gameId;
        this.moves = moves;
        this.squere = squere;
        this.moveOrder = moveOrder;
    }

    @JsonIgnore
    @Override
    public String getClassName() {
        return "move";
    }

    @JsonIgnore
    @Override
    public String getAtrValues() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMoves = objectMapper.writeValueAsString(moves);
            String jsonSquere = objectMapper.writeValueAsString(squere);
            StringBuilder sb = new StringBuilder();
            sb.append("").append(id).
                    append(",").append(gameId).
                    append(", '").append(jsonMoves).
                    append("', '").append(jsonSquere).
                    append("', ").append(moveOrder);
            return sb.toString();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @JsonIgnore
    @Override
    public String getAtrNames() {
        return "id,gameId,moves,squere,moveOrder";
    }

    @JsonIgnore
    @Override
    public String setAtrValues() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @JsonIgnore
    @Override
    public String getNameByColumn(int i) {
        return getAtrNames().split(",")[i];
    }

    @JsonIgnore
    @Override
    public String getWhereCondition() {
        return "id=" + id;
    }

    @JsonIgnore
    @Override
    public GeneralEntity getNewRecord(ResultSet rs) {
        try {
            Long _id = rs.getLong("id");
            Long _gameId = rs.getLong("gameId");
            String _movesJson = rs.getString("moves");
            String _squereJson = rs.getString("squere");
            Integer _moveOrder = rs.getInt("moveOrder");

            ObjectMapper mapper = new ObjectMapper();
            Boolean[][] _squere = mapper.readValue(_squereJson, Boolean[][].class);
            Integer[][] _moves = mapper.readValue(_movesJson, Integer[][].class);
            return new Move(_id, _gameId, _moves, _squere, _moveOrder);
        } catch (JsonProcessingException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Integer[][] getMoves() {
        return moves;
    }

    public void setMoves(Integer[][] moves) {
        this.moves = moves;
    }

    public Boolean[][] getSquere() {
        return squere;
    }

    public void setSquere(Boolean[][] squere) {
        this.squere = squere;
    }

    public Integer getMoveOrder() {
        return moveOrder;
    }

    public void setMoveOrder(Integer moveOrder) {
        this.moveOrder = moveOrder;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.gameId);
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
        final Move other = (Move) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.gameId, other.gameId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Move{id=").append(id);
        sb.append(", gameId=").append(gameId);
        sb.append(", moves=").append(moves);
        sb.append(", squere=").append(squere);
        sb.append(", moveOrder=").append(moveOrder);
        sb.append('}');
        return sb.toString();
    }

}
