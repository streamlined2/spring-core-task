package com.streamlined.tasks.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.streamlined.tasks.entity.Entity;
import com.streamlined.tasks.exception.ParseException;

public abstract class Parser<K, T extends Entity<K>> {

	private final CsvMapper csvMapper;
	private final String sourceFileName;
	private final Class<T> entityClass;

	protected Parser(Class<T> entityClass, CsvMapper csvMapper, String sourceFileName) {
		this.entityClass = entityClass;
		this.csvMapper = csvMapper;
		this.sourceFileName = sourceFileName;
	}

	public Map<K, T> parse() {
		Map<K, T> entityMap = new HashMap<>();
		try (InputStream is = getClass().getResourceAsStream(sourceFileName);
				BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
			CsvSchema schema = CsvSchema.emptySchema().withHeader();
			MappingIterator<T> iterator = csvMapper.readerFor(entityClass).with(schema).readValues(reader);
			while (iterator.hasNext()) {
				T trainee = iterator.next();
				entityMap.put(trainee.getPrimaryKey(), trainee);
			}
			return entityMap;
		} catch (IOException e) {
			throw new ParseException("Cannot parse input data", e);
		}
	}

}
