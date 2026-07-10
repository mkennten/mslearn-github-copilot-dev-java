package com.microsoft.learning.library.repository.json;

import com.microsoft.learning.library.domain.Patron;
import com.microsoft.learning.library.repository.PatronRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class JsonPatronRepository implements PatronRepository {
    private final JsonDataStore jsonDataStore;

    public JsonPatronRepository(JsonDataStore jsonDataStore) {
        this.jsonDataStore = jsonDataStore;
    }

    @Override
    public List<Patron> searchPatrons(String searchInput) {
        jsonDataStore.ensureDataLoaded();

        List<Patron> results = new ArrayList<>();
        for (Patron patron : jsonDataStore.patrons()) {
            if (patron.getName().contains(searchInput)) {
                results.add(patron);
            }
        }

        results.sort(Comparator.comparing(Patron::getName));
        return jsonDataStore.getPopulatedPatrons(results);
    }

    @Override
    public Patron getPatron(int id) {
        jsonDataStore.ensureDataLoaded();

        for (Patron patron : jsonDataStore.patrons()) {
            if (patron.getId() == id) {
                return jsonDataStore.getPopulatedPatron(patron);
            }
        }

        return null;
    }

    @Override
    public void updatePatron(Patron patron) {
        jsonDataStore.ensureDataLoaded();

        Patron existing = null;
        for (Patron current : jsonDataStore.patrons()) {
            if (current.getId() == patron.getId()) {
                existing = current;
                break;
            }
        }

        if (existing != null) {
            existing.setName(patron.getName());
            existing.setImageName(patron.getImageName());
            existing.setMembershipStart(patron.getMembershipStart());
            existing.setMembershipEnd(patron.getMembershipEnd());
            existing.setLoans(patron.getLoans());
            jsonDataStore.savePatrons(jsonDataStore.patrons());
            jsonDataStore.loadData();
        }
    }
}
