package com.example.passangers.util;

import com.example.passangers.domain.Passengers;
import com.example.passangers.domain.Pclass;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CsvHelper {

    public static String TYPE = "text/csv";
    public static String[] HEADERs = {"Survived", "Pclass", "Name", "Sex", "Age", "Siblings/Spouses Aboard", "Parents/Children Aboard", "Fare"};

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }



    public static List<Passengers> CsvToData(InputStream is) {
        List<Passengers> passangers = new ArrayList<>();
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaders(HEADERs);
        settings.setHeaderExtractionEnabled(true);
        settings.setDelimiterDetectionEnabled(true);

        CsvParser parser = new CsvParser(settings);
        List<Record> parseAllRecords = parser.parseAllRecords(is, StandardCharsets.UTF_8);
        parseAllRecords.forEach(record -> {
            Passengers passanger = new Passengers();
            passanger.setSurvived(Boolean.parseBoolean(record.getString("Survived")));
            passanger.setPclass(Pclass.fromString(Integer.parseInt(record.getString("Pclass"))));
            passanger.setName(record.getString("Name"));
            passanger.setSex(record.getString("Sex"));
            passanger.setAge(Integer.parseInt(record.getString("Age")));
            passanger.setSiblings_aboard(Integer.parseInt(record.getString("Siblings/Spouses Aboard")));
            passanger.setChildren_aboard(Integer.parseInt(record.getString("Parents/Children Aboard")));
            passanger.setFare(Double.parseDouble(record.getString("Fare")));
        });
        return passangers;
    }





}
