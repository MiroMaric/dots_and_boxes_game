package rs.miromaric.dotsandboxes.common.domain;

import java.io.Serializable;
import java.sql.ResultSet;

/**
 *
 * @author miro
 */
public interface GeneralEntity extends Serializable{
    
    String getClassName();
    String getAtrValues();
    String getAtrNames();
    String setAtrValues();
    String getNameByColumn(int i);
    String getWhereCondition();
    GeneralEntity getNewRecord(ResultSet rs);
    
}