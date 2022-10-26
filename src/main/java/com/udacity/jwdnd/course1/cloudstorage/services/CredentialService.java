package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialModel;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private final EncryptionService encryptionService;
    private final UserService userService;
    private final CredentialMapper credentialMapper;

    public CredentialService(EncryptionService encryptionService,
                             UserService userService,
                             CredentialMapper credentialMapper) {
        this.encryptionService = encryptionService;
        this.userService = userService;
        this.credentialMapper = credentialMapper;
    }

    public boolean saveCredential(CredentialModel credentialModel) {

        int affectedRow;

        String encodedKey = createKeyForEncryption();
        String encryptedPassword = encryptionService.encryptValue(credentialModel.getPassword(), encodedKey);

        User user = userService.findAuthenticatedUser();

        if ( credentialModel.getCredentialId() == null ) {
            affectedRow = credentialMapper.insert(new Credential(credentialModel.getCredentialId(),
                    credentialModel.getUrl(),
                    credentialModel.getUsername(),
                    encodedKey,
                    encryptedPassword,
                    user.getUserId()));
        } else {
            affectedRow = update(credentialModel, encryptedPassword, encodedKey);
        }

        return affectedRow > 0;
    }

    public boolean delete(Integer credentialId) {

        int affectedRow = credentialMapper.delete(credentialId);

        return affectedRow > 0;
    }

    public List<CredentialModel> findCredentialsByUser() {

        User user = userService.findAuthenticatedUser();
        List<Credential> userCredentials = credentialMapper.getCredentialsByUser(user.getUserId());

        List<CredentialModel> userCredentialModels = new ArrayList<>();
        for ( Credential userCredential : userCredentials ) {
            userCredentialModels.add(new CredentialModel(userCredential.getCredentialId(),
                    userCredential.getUrl(), userCredential.getUsername(), userCredential.getKey(),
                    userCredential.getPassword()));
        }

        return userCredentialModels;
    }

    private int update(CredentialModel credentialModel, String encryptedPassword, String encodedKey) {
        return credentialMapper.update(credentialModel.getCredentialId(),
                credentialModel.getUrl(), credentialModel.getUsername(), encodedKey, encryptedPassword);
    }

    private String createKeyForEncryption() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}
