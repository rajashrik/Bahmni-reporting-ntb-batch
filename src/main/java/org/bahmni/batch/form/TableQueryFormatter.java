package org.bahmni.batch.form;

import org.bahmni.batch.form.domain.BahmniForm;
import org.bahmni.batch.form.domain.Concept;
import org.bahmni.batch.form.domain.TableColumns;

import java.util.ArrayList;
import java.util.List;

public class TableQueryFormatter {

    private BahmniForm form;

    public TableQueryFormatter(BahmniForm form) {
        this.form = form;
    }

    public String getQuery() {
        String fields = "";
        List<TableColumns> columns = getTableColumns();
        int count;
        for(count = 0 ; count < columns.size(); count++) {
            TableColumns tableColumns = columns.get(count);
            if (count != columns.size() - 1)
                fields = fields + tableColumns.toString() + ",";
            else
                fields = fields + tableColumns.toString() ;
        }
        String params = formatColumnOrTableName(form.getDisplayName()) + " " +  "("+ fields + ");";
        String query  = "CREATE table if not EXISTS " + params;
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


    private List<TableColumns> getTableColumns(){
        String headers = getHeader();
        String[] columns = headers.split(",");
        List<TableColumns> tableColumns = new ArrayList<>();
        for( String columnName : columns){
            columnName = formatColumnOrTableName(columnName);
            tableColumns.add(new TableColumns(columnName,"varchar(40)"));
        }
        return tableColumns;
    }

    private String formatColumnOrTableName(String name){
        return name.replace(' ','_').replace('-','_').replaceAll("[?()]*","");
    }


}
