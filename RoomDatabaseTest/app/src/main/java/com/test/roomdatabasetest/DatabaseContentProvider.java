package com.test.roomdatabasetest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 自定义内容提供器
 */
public class DatabaseContentProvider extends ContentProvider {

    public static final int BOOK_DIR=0;
    public static final int BOOK_ITEM=1;
    public static final int CATEGORY_DIR=2;
    public static final int CATEGORY_ITEM=3;
    public static final String AUTHORITY="com.test.roomdatabasetest.provider";
    private static UriMatcher uriMatcher;
    private MyDatabase myDatabase;
    private static final String[] BOOK_COLUMN_NAME={"id","name","author","pages","price"};
    private static final String[] CATEGORY_COLUMN_NAME={"id","name","author","bookId"};
    static {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"book",BOOK_DIR);
        uriMatcher.addURI(AUTHORITY,"book/#",BOOK_ITEM);
        uriMatcher.addURI(AUTHORITY,"category",CATEGORY_DIR);
        uriMatcher.addURI(AUTHORITY,"category/#",CATEGORY_ITEM);
    }
    ExecutorService pool = Executors.newCachedThreadPool();
    public DatabaseContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        BookDao bookDao = myDatabase.getBookDao();
        CategoryDao categoryDao = myDatabase.getCategoryDao();
        int delRows=0;
        int match = uriMatcher.match(uri);
        if(match==BOOK_DIR){

        }else if(match==BOOK_ITEM){
            Integer id = Integer.valueOf(uri.getPathSegments().get(1));
            Book book = new Book();
            book.setId(id);
            delRows=bookDao.del(book);
        }else if(match==CATEGORY_DIR){

        }else if(match==CATEGORY_ITEM){
            Integer id = Integer.valueOf(uri.getPathSegments().get(1));
            Category category = new Category();
            category.setId(id);
            delRows=categoryDao.del(category);
        }
        return delRows;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        int match = uriMatcher.match(uri);
        if(match==BOOK_DIR){
            return "vnd.android.cursor.dir/vnd.com.test.roomdatabasetest.book";
        }else if(match==BOOK_ITEM){
            return "vnd.android.cursor.item/vnd.com.test.roomdatabasetest.book";
        }else if(match==CATEGORY_DIR){
            return "vnd.android.cursor.dir/vnd.com.test.roomdatabasetest.category";
        }else if(match==CATEGORY_ITEM){
            return "vnd.android.cursor.item/vnd.com.test.roomdatabasetest.category";
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        //添加数据
        BookDao bookDao = myDatabase.getBookDao();
        CategoryDao categoryDao = myDatabase.getCategoryDao();
        Uri uriReturn=null;
        int match = uriMatcher.match(uri);
        if(match==BOOK_ITEM||match==BOOK_DIR){
            Book book = new Book();
            book.setName(values.getAsString("name"));
            book.setPages(values.getAsInteger("pages"));
            book.setPrice(values.getAsDouble("price"));
            book.setAuthor(values.getAsString("author"));
            Long newBookId = bookDao.insert(book);
            uriReturn=Uri.parse("content://"+AUTHORITY+"/book/"+newBookId);
        }else if(match==CATEGORY_ITEM){
            Category category = new Category();
            category.setName(values.getAsString("name"));
            category.setBookId(values.getAsInteger("bookId"));
            Long categoryId = categoryDao.insert(category);
            uriReturn=Uri.parse("content://"+AUTHORITY+"/category/"+categoryId);
        }
        return uriReturn;
    }

    /**
     * 数据库初始化
     * @return
     */
    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        myDatabase=MyDatabase.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        //查询数据
        BookDao bookDao = myDatabase.getBookDao();
        CategoryDao categoryDao = myDatabase.getCategoryDao();
        int match = uriMatcher.match(uri);
        MatrixCursor cursor;
        if(match==BOOK_DIR){
            List<Book> books = bookDao.getAll();
            cursor = new MatrixCursor(BOOK_COLUMN_NAME);
            books.stream().forEach(e-> cursor.addRow(new Object[]{e.getId(),e.getName(),e.getAuthor(),e.getPages(),e.getPrice()}));
        }else if(match==BOOK_ITEM){
            String id = uri.getPathSegments().get(1);
            Book e = bookDao.getById(id);
            cursor = new MatrixCursor(BOOK_COLUMN_NAME);
            cursor.addRow(new Object[]{e.getId(),e.getName(),e.getAuthor(),e.getPages(),e.getPrice()});
        }else if(match==CATEGORY_DIR){
            List<Category> categories = categoryDao.getAll();
            cursor = new MatrixCursor(CATEGORY_COLUMN_NAME);
            categories.stream().forEach(e-> cursor.addRow(new Object[]{e.getId(),e.getName(),e.getBookId()}));
        }else if(match==CATEGORY_ITEM){
            String id = uri.getPathSegments().get(1);
            Category e = categoryDao.getById(id);
            cursor = new MatrixCursor(CATEGORY_COLUMN_NAME);
            cursor.addRow(new Object[]{e.getId(),e.getName(),e.getBookId()});
        } else {
            cursor = null;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        BookDao bookDao = myDatabase.getBookDao();
        CategoryDao categoryDao = myDatabase.getCategoryDao();
        int match = uriMatcher.match(uri);
        int updateRows=0;
        if(match==BOOK_DIR){
            Book book = new Book();
            book.setId(values.getAsInteger("id"));
            book.setName(values.getAsString("name"));
            updateRows=bookDao.updateBook(book);
        }else if(match==BOOK_ITEM){
            Book book = new Book();
            book.setId(Integer.valueOf(uri.getPathSegments().get(1)));
            book.setName(values.getAsString("name"));
            updateRows=bookDao.updateBook(book);
        }else if(match==CATEGORY_DIR){
            Category category = new Category();
            category.setId(values.getAsInteger("id"));
            category.setName(values.getAsString("name"));
            updateRows=categoryDao.updateCategory(category);
        }else if(match==CATEGORY_ITEM){
            values.put("id",uri.getPathSegments().get(1));
            Category category = new Category();
            category.setId(Integer.valueOf(uri.getPathSegments().get(1)));
            category.setName(values.getAsString("name"));
        }
        return updateRows;
    }
}