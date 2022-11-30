package com.estate.estate.Aerospike;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Record;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.Statement;
import com.aerospike.mapper.tools.AeroMapper;
import com.estate.estate.DB.DAO.OwnerDAO;
import com.estate.estate.DB.DAO.PropertyDAO;
import com.estate.estate.DB.DAO.TransactionDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Aerospike<E> {
    private static AerospikeClient client = new AerospikeClient("localhost", 3000);
    private final Class<E> type;

    public Aerospike(Class<E> type) {
        this.type = type;
    }

    private static AeroMapper mapper = new AeroMapper.Builder(client).build();

    public static final String namespace = "Estate";
    public static final String propertySet = "Property";
    public static final String ownersSet = "Owners";
    public static final String trnasactionSet = "Transaction";
    private static final Map<Class<?>, String> sets = new HashMap<>();

    static {

        sets.put(OwnerDAO.class, ownersSet);
        sets.put(PropertyDAO.class, propertySet);
        sets.put(TransactionDAO.class, trnasactionSet);

    }

    private static final Map<Class<?>, String> PK = new HashMap<>();

    static {
        PK.put(OwnerDAO.class, "userName");
        PK.put(PropertyDAO.class, "propertyId");
        PK.put(TransactionDAO.class, "date");

    }

    public E getRecord(Object id) {
        return mapper.read(type, id);
    }

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

    public void saveRecord(E recordObject) {
        mapper.save(recordObject);
    }

    public void updateRecord(E aerospikeRecord, String... bins) {
        mapper.update(aerospikeRecord, bins);
    }

    public void deleteRecord(E aerospikeRecord) {
        mapper.delete(aerospikeRecord);
    }

    public static void truncateDatabase() {
        client.truncate(null, namespace, null, null);
    }
}
