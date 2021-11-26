package com.example.EducationDepartment.Util;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Object> generateResponse(HttpStatus status, Boolean error, String message, Object responseObject) throws ParseException {
        Map<String, Object> map= new HashMap<String,Object>();
        Calendar date = Calendar.getInstance();
        try {
            map.put("timestamp",date.getTime());
            map.put("status", status.value());
            map.put("error", error);
            map.put("message", message);
            map.put("data", responseObject);

            return new ResponseEntity<Object>(map,status);
        } catch (Exception e) {
            map.clear();
            map.put("timestamp", date.getTime());
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("error",false);
            map.put("message", e.getMessage());
            map.put("data", null);
            return new ResponseEntity<Object>(map,status);
        }
    }
}
