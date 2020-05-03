package com.example.apiconnect.data.database.local.models;

public class ModelBackup {


    public ModelBackup() {

    }

    public void setNewBackUp(String backup_drive, String backup_url, String last_backup, String backup_file_size, String backup_frequency, String backup_account, String include_media){

        setBackup_drive(backup_drive);
        setBackup_url(backup_url);
        setLast_backup(last_backup);
        setBackup_file_size(backup_file_size);
        setBackup_frequency(backup_frequency);
        setBackup_account(backup_account);
        setInclude_media(include_media);

    }

    public String backup_drive;
    public String backup_url;
    public String last_backup;
    public String backup_file_size;
    public String backup_frequency;
    public String backup_account;
    public String include_media;


    public String getBackup_drive() {
        return backup_drive;
    }

    public void setBackup_drive(String backup_drive) {
        this.backup_drive = backup_drive;
    }

    public String getBackup_url() {
        return backup_url;
    }

    public void setBackup_url(String backup_url) {
        this.backup_url = backup_url;
    }

    public String getLast_backup() {
        return last_backup;
    }

    public void setLast_backup(String last_backup) {
        this.last_backup = last_backup;
    }

    public String getBackup_file_size() {
        return backup_file_size;
    }

    public void setBackup_file_size(String backup_file_size) {
        this.backup_file_size = backup_file_size;
    }

    public String getBackup_frequency() {
        return backup_frequency;
    }

    public void setBackup_frequency(String backup_frequency) {
        this.backup_frequency = backup_frequency;
    }

    public String getBackup_account() {
        return backup_account;
    }

    public void setBackup_account(String backup_account) {
        this.backup_account = backup_account;
    }

    public String getInclude_media() {
        return include_media;
    }

    public void setInclude_media(String include_media) {
        this.include_media = include_media;
    }
}
