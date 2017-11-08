package org.bahmni.batch.form;

import org.bahmni.batch.form.domain.BahmniForm;
import org.bahmni.batch.form.domain.Concept;
import org.bahmni.batch.form.domain.TableColumns;
import org.bahmni.batch.form.domain.TableMetadata;

import java.util.List;

public class TableQueryFormatter {

    private BahmniForm form;

    private TableMetadata metadata;

    public TableQueryFormatter(BahmniForm form) {
        this.form = form;
    }

    public String getQuery() {
        final String[] fields = {""};
        setUpTableMetadata();
        metadata.getFieldAndDataType().forEach((key,value) -> fields[0] += key + " " + value + "," );
        fields[0] += "primary key  (" + metadata.getPrimaryKey() + "),";
        metadata.getForeignKeyAndReferences().forEach((key,value) -> fields[0] +=  value + " varchar(40),"+ "  foreign key "+ value + "_fk" + "(" +  value + ")"+ " references " + value + "("+ key+ ")"+"," );

        String formattedFields = fields[0].replaceAll("[,]$","");
        String params = formatColumnOrTableName(form.getDisplayName()) + " " + "(" + formattedFields + ");";

        String query = "CREATE table if not EXISTS " + params;
        return query;

    }

    private String getHeader() {
        StringBuilder sb = new StringBuilder();

        sb.append("id_" + form.getDisplayName()).append(",");
        if (form.getParent() != null) {
            sb.append("id_" + form.getParent().getDisplayName()).append(",");
        }

        sb.append("regnum");
        for (Concept field : form.getFields()) {
            sb.append(",");
            sb.append(field.getFormattedTitle());
        }
        return sb.toString();
    }


    private void setUpTableMetadata() {
        metadata = new TableMetadata();
        String headers = getHeader();
        String[] columns = headers.split(",");
        for (String columnName : columns) {
            columnName = formatColumnOrTableName(columnName);
            metadata.setColumn(columnName, "varchar(40)");
        }
    }

    private String formatColumnOrTableName(String name) {
        return name.replace(' ', '_').replace('-', '_').replaceAll("[?()]*", "");
    }


}
