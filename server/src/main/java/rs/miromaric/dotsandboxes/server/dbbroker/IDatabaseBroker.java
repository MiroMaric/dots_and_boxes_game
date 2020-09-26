package rs.miromaric.dotsandboxes.server.dbbroker;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import rs.miromaric.dotsandboxes.common.domain.GeneralEntity;

/**
 *
 * @author miro
 */
public interface IDatabaseBroker {
    List<GeneralEntity> getAllRecord(GeneralEntity entity) throws SQLException;
    Optional<GeneralEntity> findRecord(GeneralEntity entity) throws SQLException;
    List<GeneralEntity> findRecords(GeneralEntity entity, GeneralEntity parent) throws SQLException;
    Long insertRecord(GeneralEntity entity) throws SQLException;
    void deleteRecord(GeneralEntity entity) throws SQLException;
    void updateRecord(GeneralEntity entity, GeneralEntity entityld) throws Exception;
    void updateRecord(GeneralEntity entity) throws SQLException;
}