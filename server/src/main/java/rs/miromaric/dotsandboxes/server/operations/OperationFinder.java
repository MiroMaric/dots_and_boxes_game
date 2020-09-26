package rs.miromaric.dotsandboxes.server.operations;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rs.miromaric.dotsandboxes.common.request.RequestOperation;
import rs.miromaric.dotsandboxes.server.settings.SettingsLoader;

/**
 *
 * @author miro
 */
public class OperationFinder {
    
    private static final Map<RequestOperation, Class> SUPPORTED_OPERATIONS = new HashMap<>();
    
    static {
        try {
            String scanPackage = SettingsLoader.getInstance().getProperty("operations.package");
            String operationsPackagePath = "target/classes/" + scanPackage.replace(".", "/");
            File file = new File(operationsPackagePath);
            
            if(!file.exists() || !file.isDirectory()) {
                throw new IllegalArgumentException("operations package path is not valid! Path:" + operationsPackagePath);
            }
            
            URLClassLoader classLoader = new URLClassLoader(new URL[]{new URL("file://" + file.getAbsolutePath() + "/")});
            List<File> operationsClasses = Arrays.asList(file.listFiles((File dir, String name) -> name.endsWith(".class")));

            for(File operationClassFile: operationsClasses) {
                Class operationClass = classLoader.loadClass(scanPackage + "." + operationClassFile.getName().split("\\.")[0]);
                if(!GenericOperation.class.isAssignableFrom(operationClass)) {
                    continue;
                }
                RequestOperation requestOperation = (RequestOperation)operationClass
                                                                      .getMethod("getSupportedOperation")
                                                                      .invoke(operationClass.newInstance());
                SUPPORTED_OPERATIONS.put(requestOperation, operationClass);
            }
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    
    public static GenericOperation findOperation(RequestOperation operation) {
        try {
            return (GenericOperation) SUPPORTED_OPERATIONS.get(operation).newInstance();
        } catch(IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
