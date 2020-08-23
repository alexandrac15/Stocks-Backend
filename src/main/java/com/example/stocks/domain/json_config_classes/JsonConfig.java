package com.example.stocks.domain.json_config_classes;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonConfig {
    ArrayList<FileDescriptor> InputFiles;
    ArrayList<FileDescriptor> OutputFiles;
    String ModelConfig;
    Integer X_previous;
    Integer Y_Predict;


    @Override
    public String toString() {
        return super.toString();
    }
}
