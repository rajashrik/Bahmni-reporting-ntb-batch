package org.bahmni.batch.form;

import org.bahmni.batch.form.domain.BahmniForm;
import org.bahmni.batch.form.domain.Concept;
import org.bahmni.batch.form.domain.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rajashrk on 10/31/17.
 */
public class TableQueryFormatter {

    private BahmniForm form;

    public TableQueryFormatter(BahmniForm form) {
        this.form = form;
    }

    public String getQuery() {

        String fields = "";
        List<Table> columns = getTableColumns();
        int count = 0;
        for(count = 0 ; count < columns.size(   ); count++) {
            Table table = columns.get(count);
            if (count != columns.size() - 1)
                fields = fields + table.toString() + ",";
            else
                fields = fields + table.toString() ;
        }
        String params = form.getDisplayName().replace(' ','_').replace('-','_').replaceAll("[?()]*","") + " " +  "("+ fields + ");";
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


    private List<Table> getTableColumns(){
        String headers = getHeader();
        String[] columns = headers.split(",");
        List<Table> tables = new ArrayList<>();
        for( String s : columns){
            s = s.replace(' ','_').replace('-','_').replaceAll("[?()]*","");
            tables.add(new Table(s,"varchar(40)"));
        }
        return tables;
    }


}
