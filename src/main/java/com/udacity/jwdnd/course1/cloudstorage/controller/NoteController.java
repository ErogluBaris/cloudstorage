package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteModel;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/note")
public class NoteController {

    public final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/create")
    public String create(Model model, @ModelAttribute("noteModel") NoteModel noteModel) {

        boolean isSaved = noteService.save(noteModel);

        if ( isSaved )
            model.addAttribute("success", true);
        else {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", String.format("Note could not save. Note Title: %s, Note Description: %s", noteModel.getNoteTitle(), noteModel.getNoteDescription()));
        }

        return "result";
    }

    @GetMapping("/delete/{noteId}")
    public String delete(Model model, @PathVariable("noteId") Integer noteId) {

        boolean isDeleted = noteService.delete(noteId);

        if ( isDeleted )
            model.addAttribute("success", true);
        else {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Note could not delete");
        }

        return "result";
    }
}
