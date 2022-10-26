package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {

    private final UserService userService;
    private final NoteMapper noteMapper;

    public NoteService(UserService userService,
                       NoteMapper noteMapper) {
        this.userService = userService;
        this.noteMapper = noteMapper;
    }

    public boolean save(NoteModel noteModel) {

        int affectedCount;

        User user = userService.findAuthenticatedUser();

        if ( noteModel.getNoteId() == null ) {
            Note note = new Note(noteModel.getNoteId(), noteModel.getNoteTitle(), noteModel.getNoteDescription(), user.getUserId());
            affectedCount = noteMapper.insert(note);
        } else {
            affectedCount = noteMapper.updateNote(noteModel.getNoteId(), noteModel.getNoteTitle(), noteModel.getNoteDescription());
        }

        return affectedCount > 0;
    }

    public List<NoteModel> findNotesByUser() {

        User user = userService.findAuthenticatedUser();
        List<Note> userNotes = noteMapper.findNotesByUserId(user.getUserId());

        List<NoteModel> userNoteModels = new ArrayList<>();
        for ( Note userNote : userNotes ) {
            userNoteModels.add(new NoteModel(userNote.getNoteId(), userNote.getNoteTitle(), userNote.getNoteDescription()));
        }

        return userNoteModels;
    }

    public boolean delete(Integer noteId) {

        int affectedCount = noteMapper.delete(noteId);

        return affectedCount > 0;
    }
}
