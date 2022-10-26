package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialModel;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteModel;
import com.udacity.jwdnd.course1.cloudstorage.model.UploadedFile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {

    private final FileService fileService;
    private final NoteService noteService;
    private final CredentialService credentialService;

    public HomeService(FileService fileService,
                       NoteService noteService,
                       CredentialService credentialService) {
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    public List<UploadedFile> getFilesByUser() {

        return fileService.getFilesByUser();
    }

    public List<NoteModel> getNotesByUser() {

        return noteService.findNotesByUser();
    }

    public List<CredentialModel> getCredentialsByUser() {

        return credentialService.findCredentialsByUser();
    }
}
