package com.airtribe.meditrack.contract;

public interface Searchable {
    boolean matchesQuery(String query);
    String getIdentifier();
}
