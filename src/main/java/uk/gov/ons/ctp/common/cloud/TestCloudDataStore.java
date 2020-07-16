package uk.gov.ons.ctp.common.cloud;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.ons.ctp.common.error.CTPException;

/** Access the Cloud data store for testing purposes. */
@Component
public class TestCloudDataStore implements CloudDataStore {

  @Autowired private CloudDataStore cloudDataStore;

  @Override
  public void deleteObject(String schema, String key) throws CTPException {
    cloudDataStore.deleteObject(schema, key);
  }

  @Override
  public Set<String> getCollectionNames() {
    return cloudDataStore.getCollectionNames();
  }

  @Override
  public <T> Optional<T> retrieveObject(Class<T> target, String schema, String key)
      throws CTPException {
    return cloudDataStore.retrieveObject(target, schema, key);
  }

  @Override
  public <T> List<T> search(Class<T> target, String schema, String[] fieldPath, String searchValue)
      throws CTPException {
    return cloudDataStore.search(target, schema, fieldPath, searchValue);
  }

  @Override
  public void storeObject(String schema, String key, Object value) throws CTPException {
    try {
      cloudDataStore.storeObject(schema, key, value);
    } catch (DataStoreContentionException e) {
      throw new RuntimeException(e);
    }
  }
}
