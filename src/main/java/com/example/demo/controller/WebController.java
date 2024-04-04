package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.jdbc.DynamicDataSource;
import com.fasterxml.jackson.databind.util.JSONPObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class WebController {
    @GetMapping(path = "/welcome", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getMethodName(@RequestParam(required = false) String param) {
        DynamicDataSource tempDSServer = new DynamicDataSource("jdbc:h2:~/" + (param == null ? "test" : param), "sa",
                "password");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(tempDSServer.getDs());
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT * FROM SOMESCHEMA.TEST;");
        // Map<Integer, Map<String, Object>> mapOfResults = new HashMap<Integer, Map<String, Object>>();
        ArrayList<Object> listOfResults = new ArrayList<Object>();
        int colCount = sqlRowSet.getMetaData().getColumnCount();
        // int rowIndex = 1;
        while (sqlRowSet.next()) {
            Map<String, Object> columnValueMap = new HashMap<String, Object>();
            for (int i = 1; i <= colCount; i++) {
                columnValueMap.put(sqlRowSet.getMetaData().getColumnLabel(i),
                        sqlRowSet.getObject(i));
            }
            listOfResults.add(columnValueMap);
            // mapOfResults.put(rowIndex, columnValueMap); // now you can access valueMap using index.
            // rowIndex++;
        }
        return new ResponseEntity<Object>(listOfResults, HttpStatus.OK);
    }

    @GetMapping(path = "/put", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> putMethodName(@RequestParam(required = false) String param,
            @RequestParam(required = true) String param_name) {
        DynamicDataSource tempDSServer = new DynamicDataSource("jdbc:h2:~/" + (param == null ? "test" : param), "sa",
                "password");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(tempDSServer.getDs());

        jdbcTemplate.update("INSERT into SOMESCHEMA.TEST(name) VALUES(?)", param_name);

        return new ResponseEntity<String>("{\"response\": \"Insert succesfull\"}", HttpStatus.OK);
    }
}
