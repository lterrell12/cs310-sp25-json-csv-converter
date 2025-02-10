package edu.jsu.mcis.cs310;

import com.github.cliftonlabs.json_simple.*;
import com.opencsv.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
public class Converter {
    
    /*
        
        Consider the following CSV data, a portion of a database of episodes of
        the classic "Star Trek" television series:
        
        "ProdNum","Title","Season","Episode","Stardate","OriginalAirdate","RemasteredAirdate"
        "6149-02","Where No Man Has Gone Before","1","01","1312.4 - 1313.8","9/22/1966","1/20/2007"
        "6149-03","The Corbomite Maneuver","1","02","1512.2 - 1514.1","11/10/1966","12/9/2006"
        
        (For brevity, only the header row plus the first two episodes are shown
        in this sample.)
    
        The corresponding JSON data would be similar to the following; tabs and
        other whitespace have been added for clarity.  Note the curly braces,
        square brackets, and double-quotes!  These indicate which values should
        be encoded as strings and which values should be encoded as integers, as
        well as the overall structure of the data:
        
        {
            "ProdNums": [
                "6149-02",
                "6149-03"
            ],
            "ColHeadings": [
                "ProdNum",
                "Title",
                "Season",
                "Episode",
                "Stardate",
                "OriginalAirdate",
                "RemasteredAirdate"
            ],
            "Data": [
                [
                    "Where No Man Has Gone Before",
                    1,
                    1,
                    "1312.4 - 1313.8",
                    "9/22/1966",
                    "1/20/2007"
                ],
                [
                    "The Corbomite Maneuver",
                    1,
                    2,
                    "1512.2 - 1514.1",
                    "11/10/1966",
                    "12/9/2006"
                ]
            ]
        }
        
        Your task for this program is to complete the two conversion methods in
        this class, "csvToJson()" and "jsonToCsv()", so that the CSV data shown
        above can be converted to JSON format, and vice-versa.  Both methods
        should return the converted data as strings, but the strings do not need
        to include the newlines and whitespace shown in the examples; again,
        this whitespace has been added only for clarity.
        
        NOTE: YOU SHOULD NOT WRITE ANY CODE WHICH MANUALLY COMPOSES THE OUTPUT
        STRINGS!!!  Leave ALL string conversion to the two data conversion
        libraries we have discussed, OpenCSV and json-simple.  See the "Data
        Exchange" lecture notes for more details, including examples.
        
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String result = "{}"; // default return value; replace later!
        StringBuilder csvFile = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(csvString));
            String line;
            while((line = reader.readLine()) !=null) {
            csvFile.append(line) .append('\n');
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        return result.trim();
        
    }
    
    @SuppressWarnings("unchecked")
    public static String jsonToCsv(String jsonString) {
        
        String result = ""; // default return value; replace later!
        
        try {
            jsonString = jsonString.trim();
            if (jsonString.startsWith("[") && jsonString.endsWith("]")) {
                jsonString = jsonString.substring(1, jsonString.length() - 1);
            }
            String[] objects = jsonString.split("},\\s*\\{}");
            
            List<Map<String, String>> rows = new ArrayList<>();
            
            for (String object : objects) {
            object = object.replace("{", "").replace("}", "").trim();
            String[] keyValuePairs = object.split(",\\s*");
            
                Map<String, String> row = new LinkedHashMap<>();
                for (String pair : keyValuePairs) {
                    String[] keyValue = pair.split(":\\s*");
                    String key = keyValue[0].replace("\"", "").trim();
                    String value = keyValue[1].replace("\"", "").trim();
                    row.put(key, value);
                }
                rows.add(row);
            }
             if (!rows.isEmpty()) {
                Map<String, String> firstRow = rows.get(0);
                List<String> headers = new ArrayList<>(firstRow.keySet());
                StringBuilder csvBuilder = new StringBuilder();

                csvBuilder.append(String.join(",", headers)).append("\n");

                for (Map<String, String> row : rows) {
                    List<String> values = new ArrayList<>();
                    for (String header : headers) {
                        String value = row.getOrDefault(header, "");
                        values.add("\"" + value.replace("\"", "\"\"") + "\"");
                    }
                    csvBuilder.append(String.join(",", values)).append("\n");
                }
             }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return result.trim();
        
    }
    }
 
