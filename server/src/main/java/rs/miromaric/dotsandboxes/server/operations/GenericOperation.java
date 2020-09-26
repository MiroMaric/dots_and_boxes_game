package rs.miromaric.dotsandboxes.server.operations;

import rs.miromaric.dotsandboxes.common.domain.GeneralEntity;
import rs.miromaric.dotsandboxes.common.request.RequestOperation;
import rs.miromaric.dotsandboxes.server.dbbroker.impl.DatabaseBroker;
import rs.miromaric.dotsandboxes.server.dbbroker.IDatabaseBroker;
import rs.miromaric.dotsandboxes.server.dbbroker.connection.DatabaseConnection;

/**
 *
 * @author miro
 * @param <T>
 * @param <R>
 */
public abstract class GenericOperation<T extends GeneralEntity, R> {

    protected IDatabaseBroker databaseBroker;

    public GenericOperation() {
        databaseBroker = new DatabaseBroker();
    }

    public final void templateExecute(T entity) throws Exception {
        try {
            validate(entity);
            try {
                startTransaction();
                execute(entity);
                commitTransaction();
            } catch (Exception ex) {
                rollbackTransaction();
                throw ex;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    protected abstract void validate(T entity) throws Exception;
    
    public abstract R getResult();
    
    public abstract RequestOperation getSupportedOperation();

    private void startTransaction() throws Exception {
        DatabaseConnection.getInstance().startTransaction();
    }

    protected abstract void execute(T entity) throws Exception;

    private void commitTransaction() throws Exception {
        DatabaseConnection.getInstance().commitTransaction();
    }

    private void rollbackTransaction() throws Exception {
        DatabaseConnection.getInstance().rollbackTransaction();
    }
}
