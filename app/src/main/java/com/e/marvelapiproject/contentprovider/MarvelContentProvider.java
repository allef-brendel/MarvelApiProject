package com.e.marvelapiproject.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.e.marvelapiproject.db.Database;

public class MarvelContentProvider extends ContentProvider {

    private SQLiteDatabase db;

    static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Authority.AUTHORITY, "quadrinho", Authority.QUADRINHO);
        uriMatcher.addURI(Authority.AUTHORITY, "quadrinho/#", Authority.QUADRINHO_ID);
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        Database bd = new Database(context);
        db = bd.getWritableDatabase();

        if (db != null){
            return true;
        }
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(Database.PESSOAS_TABLE_NAME);

        switch (uriMatcher.match(uri)){
            case Authority.QUADRINHO:
                break;
            case Authority.QUADRINHO_ID:
                qb.appendWhere( Database._ID + "=" + uri.getPathSegments().get(1));
                break;
        }
        Cursor cursor = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        long rowID = db.insert(	Database.PESSOAS_TABLE_NAME, "", contentValues);

        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(Authority.CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Falha  " + uri);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        int linha = 0;

        switch (uriMatcher.match(uri)){
            case Authority.QUADRINHO:
                db.delete(Database.PESSOAS_TABLE_NAME, null, null);
                break;
            case Authority.QUADRINHO_ID:
                String id = uri.getLastPathSegment();
                linha = db.delete(Database.PESSOAS_TABLE_NAME, Database._ID + "=" + id, strings);
                break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return linha;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        int linha = 0;

        switch (uriMatcher.match(uri)){
            case Authority.QUADRINHO:
                linha = db.update(Database.PESSOAS_TABLE_NAME, contentValues, null, null);
                break;
            case Authority.QUADRINHO_ID:
                String id = uri.getLastPathSegment();
                linha = db.update(Database.PESSOAS_TABLE_NAME, contentValues, Database._ID + "=" + id ,null);
                break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return linha;
    }
}
