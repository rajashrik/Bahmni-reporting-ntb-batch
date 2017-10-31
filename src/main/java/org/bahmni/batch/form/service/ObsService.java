package org.bahmni.batch.form.service;

import org.bahmni.batch.BatchUtils;
import org.bahmni.batch.form.TableQueryFormatter;
import org.bahmni.batch.form.domain.BahmniForm;
import org.bahmni.batch.form.domain.Concept;
import org.bahmni.batch.form.domain.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class ObsService {

	@Value("classpath:sql/conceptDetails.sql")
	private Resource conceptDetailsSqlResource;

	@Value("classpath:sql/conceptList.sql")
	private Resource conceptListSqlResource;


	@Value("classpath:sql/allConceptList.sql")
	private Resource allConceptListSqlResource;


	@Value("classpath:sql/createTables.sql")
	private Resource createTablesSqlResource;

	private String conceptDetailsSql;

	private String createTablesSql;

	private String conceptListSql;

	private String allConceptListSql;

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	public List<Concept> getConceptsByNames(String commaSeparatedConceptNames){
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("conceptNames", BatchUtils.convertConceptNamesToSet(commaSeparatedConceptNames));

		return jdbcTemplate.query(conceptDetailsSql,parameters,new BeanPropertyRowMapper<>(Concept.class));
	}

	public List<Concept> getChildConcepts(String parentConceptName){
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("parentConceptName",parentConceptName);

		return jdbcTemplate.query(conceptListSql, parameters, new BeanPropertyRowMapper<>(Concept.class));

	}

	public List<Concept> getAllConcepts() {
		return jdbcTemplate.query(allConceptListSql, new BeanPropertyRowMapper<>(Concept.class));
	}

	@PostConstruct
	public void postConstruct(){
		this.conceptDetailsSql = BatchUtils.convertResourceOutputToString(conceptDetailsSqlResource);
		this.conceptListSql = BatchUtils.convertResourceOutputToString(conceptListSqlResource);
		this.allConceptListSql =  BatchUtils.convertResourceOutputToString(allConceptListSqlResource);
		this.createTablesSql =  BatchUtils.convertResourceOutputToString(createTablesSqlResource);
	}


	public void createTables(BahmniForm form) {
		TableQueryFormatter formatter = new TableQueryFormatter(form);
		String query = formatter.getQuery();

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		jdbcTemplate.update(query,parameters);

	}



}
