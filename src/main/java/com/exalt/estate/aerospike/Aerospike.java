package com.exalt.estate.aerospike;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Record;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.Statement;
import com.aerospike.mapper.tools.AeroMapper;
import com.exalt.estate.dao.OwnerDAO;
import com.exalt.estate.dao.PropertyDAO;
import com.exalt.estate.dao.TransactionDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 * The AerospikeAccess is a generic class that enables doing operations on any object type stored in aerospike.
 *
 * @param <E> the type parameter is the class of the object corresponding to a specific set in the aerospike namespace.
 */
public class Aerospike<E> {
    static ApplicationProperties properties = new ApplicationProperties();
    /**
     * The java client to aerospike database running on docker. Read properties from file.
     */
    private static AerospikeClient client = new AerospikeClient(properties.readProperty("aersopike.host"), Integer.parseInt(properties.readProperty("aersopike.port")));
    private final Class<E> type;
    /**
     * Instantiates a new Aerospike access.
     *
     * @param type is the class of the object that corresponds to its database set
     */
    public Aerospike(Class<E> type) {
        this.type = type;
    }
    /**
     * An aerospike object mapper to map java objects to database entities.
     */
    private static AeroMapper mapper = new AeroMapper.Builder(client).build();
    /**
     * The aerospike namespace where all the project sets are stored inside.
     */
    public static final String namespace = "Estate";
    /**
     * The aerospike set where all the properties records inside.
     */
    public static final String propertySet = "Property";
    /**
     * The aerospike set where all the owners records inside.
     */
    public static final String ownersSet = "Owners";
    /**
     * The aerospike set where all the transaction records inside.
     */
    public static final String trnasactionSet = "Transaction";
    private static final Map<Class<?>, String> sets = new HashMap<>();

    static {

        sets.put(OwnerDAO.class, ownersSet);
        sets.put(PropertyDAO.class, propertySet);
        sets.put(TransactionDAO.class, trnasactionSet);

    }
    /**
     * A constant primary Key Map that maps each class type to it's used primary key in the database.
     * These attributes annotated with @AerospikeKey.
     */
    private static final Map<Class<?>, String> PK = new HashMap<>();

    static {
        PK.put(OwnerDAO.class, "userName");
        PK.put(PropertyDAO.class, "propertyId");
        PK.put(TransactionDAO.class, "date");

    }
    /**
     * Get a single record from the database.
     *
     * @param id the unique id of the record
     * @return the record in the corresponding class object
     */
    public E getRecord(Object id) {
        return mapper.read(type, id);
    }
    /**
     * Get all records of a database set in the form of array list.
     *
     * @return the array list of retrieved records
     */
    public ArrayList<E> getSet() {
        ArrayList<E> setRecords = new ArrayList<>();
        Statement st = new Statement();
        st.setNamespace(namespace);
        st.setSetName(sets.get(type));
        try (RecordSet recordSet = client.query(null, st)) {
            while (recordSet.next()) {
                Record aerospikeRecord = recordSet.getRecord();
                Object key = aerospikeRecord.bins.get(PK.get(type));
                setRecords.add(getRecord(key));
            }
        }
        return setRecords;
    }
    /**
     * Save a new record into the set corresponding to the class type.
     *
     * @param recordObject the object representing the record to be added
     */
    public void saveRecord(E recordObject) {
        mapper.save(recordObject);
    }
    /**
     * Update a single record in the corresponding set.
     *
     * @param aerospikeRecord the object representing the new data record to be updated
     * @param bins            the bins to apply the update to
     */
    public void updateRecord(E aerospikeRecord, String... bins) {
        mapper.update(aerospikeRecord, bins);
    }
    /**
     * Delete a single given record from the corresponding set.
     *
     * @param aerospikeRecord the object representing the record to be deleted
     */
    public void deleteRecord(E aerospikeRecord) {
        mapper.delete(aerospikeRecord);
    }
    /**
     * Reset the database.
     */
    public static void truncateDatabase() {
        client.truncate(null, namespace, null, null);
    }
}
