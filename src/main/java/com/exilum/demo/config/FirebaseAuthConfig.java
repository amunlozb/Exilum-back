package com.exilum.demo.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import java.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseAuthConfig {

    @Value("${SERVICE_ACCOUNT}")
    private String serviceAccountEnv;

    @Bean
    FirebaseAuth firebaseAuth() throws IOException {
        // Decode the base64 encoded service account JSON
        byte[] serviceAccountBytes = Base64.getDecoder().decode(serviceAccountEnv);

        // Set options = Read credentials from the decoded JSON
        var options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(new ByteArrayInputStream(serviceAccountBytes)))
                .build();

        // Initialize app with the options
        var firebaseApp = FirebaseApp.initializeApp(options);

        return FirebaseAuth.getInstance(firebaseApp);
    }
}
