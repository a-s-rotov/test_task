package com.test.task.components;

import com.test.task.exception.ValidateFileException;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

@Component
public class XmlValidatorComponent {

    private Validator validator;


    public XmlValidatorComponent() throws SAXException, IOException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        ClassPathResource classPathResource = new ClassPathResource("/xsd/epaperRequest.xsd");

        URL xsdFile = classPathResource.getURL();

        Schema schema = factory.newSchema(xsdFile);

        validator = schema.newValidator();
    }

    public void valid(MultipartFile file) {
        try {
            validator.validate(new StreamSource(file.getInputStream()));
        } catch (IOException | SAXException e) {
            throw new ValidateFileException(e);
        }
    }
}
