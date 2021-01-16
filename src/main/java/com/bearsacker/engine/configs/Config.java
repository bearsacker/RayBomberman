package com.bearsacker.engine.configs;

import java.util.ArrayList;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;


public abstract class Config {

    protected Configuration configuration;

    public Config(String file) {
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(new Parameters().properties().setFileName(file));

        try {
            configuration = builder.getConfiguration();
        } catch (ConfigurationException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    protected static String[] toStringArray(String array) {
        return array.split(",");
    }

    protected static Integer[] toIntegerArray(String array) {
        String[] values = array.split(",");
        ArrayList<Integer> intValues = new ArrayList<>();
        for (String value : values) {
            intValues.add(Integer.parseInt(value));
        }

        return intValues.toArray(new Integer[intValues.size()]);
    }
}
