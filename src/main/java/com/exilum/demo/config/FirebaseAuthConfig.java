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

    private String serviceAccountBase64 = System.getenv("SERVICE_ACCOUNT");

    @Bean
    FirebaseAuth firebaseAuth() throws IOException {
        // Decode the base64 encoded service account JSON (env var)
        byte[] serviceAccountBytes = Base64.getDecoder().decode(serviceAccountBase64);
        String serviceAccountJson = new String(serviceAccountBytes, StandardCharsets.UTF_8);

        // Set options = Read credentials from the decoded JSON
        var options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(new ByteArrayInputStream(serviceAccountJson.getBytes())))
                .build();

        // Initialize app with the options
        var firebaseApp = FirebaseApp.initializeApp(options);

        return FirebaseAuth.getInstance(firebaseApp);
    }
}
