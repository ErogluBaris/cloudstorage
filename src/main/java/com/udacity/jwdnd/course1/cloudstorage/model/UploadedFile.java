package com.udacity.jwdnd.course1.cloudstorage.model;

public class UploadedFile {

    private final Integer fileId;
    private final String fileName;

    public UploadedFile(Integer fileId, String fileName) {
        this.fileId = fileId;
        this.fileName = fileName;
    }

    public Integer getFileId() {
        return fileId;
    }

    public String getFileName() {
        return fileName;
    }
}
