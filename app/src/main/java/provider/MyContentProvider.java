package provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import dao.MyOpenHelper;


public class MyContentProvider extends ContentProvider {

    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI("ultimate.ninja.fasting","programmation", 1);
        MATCHER.addURI("ultimate.ninja.fasting","programmations", 1);
        MATCHER.addURI("ultimate.ninja.fasting","programmation/#", 2);
    }

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        MyOpenHelper helper = new MyOpenHelper(getContext());
        SQLiteDatabase db = helper.getReadableDatabase();
        String tableName = null;
        int uriCase = MATCHER.match(uri);
        switch (uriCase){
            case 1:
                tableName = "programmation";
                break;
            case 2:
                tableName = "programmation";
                String id = uri.getLastPathSegment();
                if(TextUtils.isEmpty(selection)){
                    selection = "_id = " + id;
                }else{
                    selection += " AND _id = " + id;
                }
                break;
        }
        if(tableName == null){
            throw new UnsupportedOperationException("Bad URI: table does not exists");
        }

        return db.query(tableName,projection,selection,selectionArgs,null,null,sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
