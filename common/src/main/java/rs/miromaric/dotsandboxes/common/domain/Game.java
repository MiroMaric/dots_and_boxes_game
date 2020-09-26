package rs.miromaric.dotsandboxes.common.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author miro
 */
public class Game implements GeneralEntity {

    private Long id;
    private Long playerId;
    private Date date;
    private Boolean firstMove;
    private Integer dimension;
    private Integer computerScore;
    private Integer humanScore;

    public Game() {
    }

    public Game(Long id, Long playerId, Date date, Boolean firstMove, Integer dimension, Integer computerScore, Integer humanScore) {
        this.id = id;
        this.playerId = playerId;
        this.date = date;
        this.firstMove = firstMove;
        this.dimension = dimension;
        this.computerScore = computerScore;
        this.humanScore = humanScore;
    }

    public Game(Long playerId, Date date, Boolean firstMove, Integer dimension) {
        this.playerId = playerId;
        this.date = date;
        this.firstMove = firstMove;
        this.dimension = dimension;
    }
    
    public Game(Long playerId, Integer dimension) {
        this.playerId = playerId;
        this.dimension = dimension;
    }

    public Game(Long id) {
        this.id = id;
    }

    @Override
    public String getClassName() {
        return "game";
    }

    @Override
    public String getAtrValues() {
        StringBuilder sb = new StringBuilder();
        sb.append("").append(id).
                append(",").append(playerId).
                append(", '").append(new Timestamp(date.getTime())).
                append("', ").append(firstMove).
                append(", ").append(dimension).
                append(", ").append(computerScore).
                append(", ").append(humanScore);
        return sb.toString();
    }

    @Override
    public String getAtrNames() {
        return "id,playerId,date,firstMove,dimension,computerScore,humanScore";
    }

    @Override
    public String setAtrValues() {
        StringBuilder sb = new StringBuilder();
        sb.append("computerScore=").append(computerScore).
                append(",humanScore=").append(humanScore);
        return sb.toString();
    }

    @Override
    public String getNameByColumn(int i) {
        return getAtrNames().split(",")[i];
    }

    @Override
    public String getWhereCondition() {
        return "id=" + id;
    }

    @Override
    public GeneralEntity getNewRecord(ResultSet rs) {
        try {
            Long _id = rs.getLong("id");
            Long _playerId = rs.getLong("playerId");
            Date _date = rs.getDate("date");
            Boolean _firstMove = rs.getBoolean("firstMove");
            Integer _dimension = rs.getInt("dimension");
            Integer _computerScore = rs.getInt("computerScore");
            Integer _humanScore = rs.getInt("humanScore");
            return new Game(_id, _playerId, _date, _firstMove, _dimension, _computerScore, _humanScore);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getFirstMove() {
        return firstMove;
    }

    public void setFirstMove(Boolean firstMove) {
        this.firstMove = firstMove;
    }

    public Integer getDimension() {
        return dimension;
    }

    public void setDimension(Integer dimension) {
        this.dimension = dimension;
    }

    public Integer getComputerScore() {
        return computerScore;
    }

    public void setComputerScore(Integer computerScore) {
        this.computerScore = computerScore;
    }

    public Integer getHumanScore() {
        return humanScore;
    }

    public void setHumanScore(Integer humanScore) {
        this.humanScore = humanScore;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.id);
        hash = 43 * hash + Objects.hashCode(this.playerId);
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
        final Game other = (Game) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.playerId, other.playerId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Game{id=").append(id);
        sb.append(", playerId=").append(playerId);
        sb.append(", date=").append(date);
        sb.append(", firstMove=").append(firstMove);
        sb.append(", dimension=").append(dimension);
        sb.append(", computerScore=").append(computerScore);
        sb.append(", humanScore=").append(humanScore);
        sb.append('}');
        return sb.toString();
    }

}
