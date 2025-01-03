package com.streamlined.tasks.parser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.streamlined.tasks.entity.Entity;
import com.streamlined.tasks.exception.ParseException;

@Component
public class Parser {

    private static final Logger LOGGER = LoggerFactory.getLogger(Parser.class);

    private final CsvMapper csvMapper;

    public Parser(CsvMapper csvMapper) {
        this.csvMapper = csvMapper;
    }

    public <K, T extends Entity<K>> Map<K, T> parse(Class<T> entityClass,  String sourceFileName) {
        Map<K, T> entityMap = new HashMap<>();
        try (InputStream is = getClass().getResourceAsStream(sourceFileName);
                BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            CsvSchema schema = CsvSchema.emptySchema().withHeader();
            MappingIterator<T> iterator = csvMapper.readerFor(entityClass).with(schema).readValues(reader);
            while (iterator.hasNext()) {
                T entity = iterator.next();
                entityMap.put(entity.getPrimaryKey(), entity);
            }
            return entityMap;
        } catch (Exception e) {
            LOGGER.error("Cannot parse input data", e);
            throw new ParseException("Cannot parse input data", e);
        }
    }

}
