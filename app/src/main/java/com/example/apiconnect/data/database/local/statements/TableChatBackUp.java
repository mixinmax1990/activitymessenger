package com.example.apiconnect.data.database.local.statements;

public class TableChatBackUp {

    public static final String TABLE_NAME= "chat_backup";

    private static final String BACKUP_ID = "backup_id";
    private static final String BACKUP_SERVICE_NAME = "backup_drive";
    private static final String BACKUP_URL = "backup_url";
    private static final String LAST_BACKUP = "last_backup";
    private static final String BACKUP_FILE_SIZE= "backup_file_size";
    private static final String BACKUP_FREQUENCY = "backup_frequency";
    private static final String BACKUP_ACCOUNT = "backup_account";
    private static final String INCLUDE_MEDIA = "include_media";

    public static final String CREATE_TABLE_BACKUP = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
            + BACKUP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + BACKUP_SERVICE_NAME + " TEXT,"
            + BACKUP_URL + " TEXT,"
            + LAST_BACKUP + " TEXT,"
            + BACKUP_FILE_SIZE + " TEXT,"
            + BACKUP_FREQUENCY + " TEXT,"
            + BACKUP_ACCOUNT + " TEXT,"
            + INCLUDE_MEDIA + " TEXT" +
            ")";


    public static String getTableName() {
        return TABLE_NAME;
    }

    public static String getBackupServiceName() {
        return BACKUP_SERVICE_NAME;
    }

    public static String getBackupUrl() {
        return BACKUP_URL;
    }

    public static String getLastBackup() {
        return LAST_BACKUP;
    }

    public static String getBackupFileSize() {
        return BACKUP_FILE_SIZE;
    }

    public static String getBackupFrequency() {
        return BACKUP_FREQUENCY;
    }

    public static String getBackupAccount() {
        return BACKUP_ACCOUNT;
    }

    public static String getIncludeMedia() {
        return INCLUDE_MEDIA;
    }
}
