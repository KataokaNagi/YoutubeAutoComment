package com.github.kataokanagi.youtubeapi;

import com.github.kataokanagi.utils.Log;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class OAuthHelper {

    private static final String TAG = "OAuthHelper";

    private static final String SCOPE_YOUTUBE = "https://www.googleapis.com/auth/youtube.force-ssl";
    private static final String CREDENTIALS_DIRECTORY = ".oauth-credentials";
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public static Credential authorize() throws IOException {
        // load client secrets
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(OAuthHelper.class.getResourceAsStream("/client_secrets.json")));

        // check whether defaults have been replaced
        if (clientSecrets.getDetails().getClientId().startsWith("Enter")
                || clientSecrets.getDetails().getClientSecret().startsWith("Enter")) {
            Log.e(TAG,
                    "Enter Client ID and Secret from https://console.developers.google.com/project/_/apiui/credential"
                            + "into src/main/resources/client_secrets.json");
            System.exit(1);
        }

        // setup credential store
        FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(new File(CREDENTIALS_DIRECTORY));
        DataStore<StoredCredential> dataStore = fileDataStoreFactory.getDataStore("credential_store");

        // oauth scope: youtube.force-ssl
        List<String> scopes = new ArrayList<>();
        scopes.add(SCOPE_YOUTUBE);

        // setup authorization code flow
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, scopes
                )
                .setCredentialDataStore(dataStore)
                .build();

        // setup local callback server
        LocalServerReceiver localReceiver = new LocalServerReceiver.Builder().setPort(8080).build();

        // do authorization
        Credential credential = new AuthorizationCodeInstalledApp(flow, localReceiver).authorize("user");

        // check if token time limit exceeded
        if (System.currentTimeMillis() > credential.getExpirationTimeMilliseconds()) {
            // refresh the token
            credential.refreshToken();
        }

        return credential;
    }

}
