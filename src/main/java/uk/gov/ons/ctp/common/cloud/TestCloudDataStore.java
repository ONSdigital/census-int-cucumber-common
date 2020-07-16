package uk.gov.ons.ctp.common.cloud;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.ons.ctp.common.error.CTPException;
import uk.gov.ons.ctp.common.firestore.FirestoreWait;

/** Access the Cloud data store for testing purposes. */
@Component
public class TestCloudDataStore implements CloudDataStore {

  @Autowired private FirestoreDataStore dataStore;

  @Override
  public void deleteObject(String schema, String key) throws CTPException {
    dataStore.deleteObject(schema, key);
  }

  @Override
  public Set<String> getCollectionNames() {
    return dataStore.getCollectionNames();
  }

  @Override
  public <T> Optional<T> retrieveObject(Class<T> target, String schema, String key)
      throws CTPException {
    return dataStore.retrieveObject(target, schema, key);
  }

  @Override
  public <T> List<T> search(Class<T> target, String schema, String[] fieldPath, String searchValue)
      throws CTPException {
    return dataStore.search(target, schema, fieldPath, searchValue);
  }

  @Override
  public void storeObject(String schema, String key, Object value) throws CTPException {
    try {
      dataStore.storeObject(schema, key, value);
    } catch (DataStoreContentionException e) {
      throw new RuntimeException(e);
    }
  }

  public boolean waitForObject(String collection, String key, long timeoutMillis)
      throws CTPException {

    if (collection == null || key == null) {
      throw new IllegalArgumentException("collection and key must be provided");
    }

    FirestoreWait firestore =
        FirestoreWait.builder().collection(collection).key(key).timeout(timeoutMillis).build();

    return firestore.waitForObject() != null;
  }
}
