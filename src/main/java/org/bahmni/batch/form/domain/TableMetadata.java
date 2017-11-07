package org.bahmni.batch.form.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by rajashrk on 11/3/17.
 */
public class TableMetadata {

    private Map<String,String> fieldAndDataType;
    private Map<String,String> foreignKeyAndReferences;

    public TableMetadata() {
        fieldAndDataType = new HashMap<>();
        foreignKeyAndReferences = new HashMap<>();
    }

    public Map<String, String> getFieldAndDataType() {
        return fieldAndDataType;
    }

    public void setColumn(HashMap<String, String> fieldAndDataType) {
        this.fieldAndDataType = fieldAndDataType;
    }

    public Map<String, String> getForeignKeyAndReferences() {
        return foreignKeyAndReferences;
    }

    public void setForeignKeyAndReferences(HashMap<String, String> foreignKeyAndReferences) {
        this.foreignKeyAndReferences = foreignKeyAndReferences;
    }

    public void setColumn(String field, String dataType) {
        List<String> list  = fieldAndDataType.keySet().stream().filter(s -> s.matches("id[_a-z]*")).collect(Collectors.toList());
        if (list.size() > 0 && field.matches("id[_a-z]*")) {
            setForeignKeyAndReferences(field, dataType);
        } else {
            fieldAndDataType.put(field, dataType);
        }
    }

    public void setForeignKeyAndReferences(String field, String reference) {
        String reference_table = field.replace("id_","");
        foreignKeyAndReferences.put(field, reference_table);
    }



}
