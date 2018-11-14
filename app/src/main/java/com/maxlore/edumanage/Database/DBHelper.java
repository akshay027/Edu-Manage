//package com.maxlore.inmegh.Database;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
///**
// * Created by Nikhil on 04-06-2016.
// */
//public class DBHelper extends SQLiteOpenHelper {
//
//
//    private SQLiteDatabase db;
//    private static final int DATABASE_VERSION = 1;
//    private static final String DATABASE_NAME = "App_Database";
//    private final Context context;
//
//    // Table Name
//    public static final String TABLE_PRODUCT = "Product";
//    public static final String TABLE_ADS = "Ads";
//
//
//    // Product Table Field
//    public static final String COL_PRODUCT_ID = "product_id";
//    public static final String COL_PRODUCT_CODE = "product_code";
//    public static final String COL_PRODUCT_NAME = "product_name";
//    public static final String COL_COMPANY_NAME = "company_name";
//    public static final String COL_PRICE = "price";
//    public static final String COL_PACKAGE = "package";
//    public static final String COL_GRADE = "grade";
//    public static final String COL_FORMULA = "formula";
//    public static final String COL_MOLAR_MASS = "molar_mass";
//    public static final String COL_PRODUCT_IMG_URL = "product_img_url";
//    public static final String COL_IS_FAVOURITE = "is_favourite";
//
//
//    //  Ads Table Fields
//    public static final String COL_ADS_ID = "ads_id";
//    public static final String COL_COMPANY_ID = "company_id";
//    public static final String COL_PRODUCT_DSC = "product_description";
//    public static final String COL_COMPANY_LOGO_URL = "company_logo_url";
//    //   public static final String COL_PRODUCT_IMG_URL = "product_img_url";
//    //   public static final String COL_PRODUCT_NAME = "product_name";
//    //   public static final String COL_COMPANY_NAME = "company_id"; // Already
//
//
//    public DBHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        this.context = context;
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//
//
//        String CREATE_TABLE_PRODUCT = "CREATE TABLE " + TABLE_PRODUCT + "("
//                + COL_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
//                + COL_PRODUCT_CODE + " TEXT, "
//                + COL_PRODUCT_NAME + " TEXT, "
//                + COL_COMPANY_NAME + " TEXT, "
//                + COL_PRICE + " TEXT, "
//                + COL_PACKAGE + " TEXT, "
//                + COL_GRADE + " TEXT, "
//                + COL_FORMULA + " TEXT, "
//                + COL_MOLAR_MASS + " TEXT, "
//                + COL_PRODUCT_IMG_URL + " TEXT, "
//                + COL_IS_FAVOURITE + " INT " + ");";
//        db.execSQL(CREATE_TABLE_PRODUCT);
//
//
//        String CREATE_TABLE_ADS = "CREATE TABLE " + TABLE_ADS + "("
//                + COL_ADS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
//                + COL_COMPANY_ID + " TEXT, "
//                + COL_PRODUCT_DSC + " TEXT, "
//                + COL_COMPANY_LOGO_URL + " TEXT, "
//                + COL_PRODUCT_NAME + " TEXT, "
//                + COL_COMPANY_NAME + " TEXT, "
//                + COL_PRODUCT_IMG_URL + " TEXT " + ");";
//        db.execSQL(CREATE_TABLE_ADS);
//
//
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//    }
//
//
//    /**
//     * Methods For Product Table
//     */
//
//    public boolean checkForProductExists(String id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String selectQuery;
//        selectQuery = "SELECT * FROM " + TABLE_PRODUCT + " where " + COL_PRODUCT_CODE + " = '" + id + "';";
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        if (cursor.moveToFirst()) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//
///*
//    public void insertProduct(Product product) {
//
//        try {
//
//            ContentValues values = new ContentValues();
//            values.put(COL_PRODUCT_CODE, product.getProductCode());
//            values.put(COL_PRODUCT_NAME, product.getProductName());
//            values.put(COL_COMPANY_NAME, product.getCompanyName());
//            values.put(COL_PRICE, product.getPrice());
//            values.put(COL_PACKAGE, product.getPackage());
//            values.put(COL_GRADE, product.getGrade());
//            values.put(COL_FORMULA, product.getFormula());
//            values.put(COL_MOLAR_MASS, product.getMolarMass());
//            values.put(COL_PRODUCT_IMG_URL, product.getProductImgUrl());
//            values.put(COL_IS_FAVOURITE, product.isFavourite() ? 1 : 0);
//
//            if (db == null)
//                db = this.getWritableDatabase();
//
//            db.insert(TABLE_PRODUCT, null, values);
//            db.close();
//
//        } catch (Exception e) {
//            db.close();
//            e.printStackTrace();
//        }
//
//    }
//
//    // insert Multiple Product
//    public void insertProduct(ArrayList<Product> arrayList) {
//
//        if (db == null)
//            db = this.getWritableDatabase();
//
//        for (int i = 0; i < arrayList.size(); i++) {
//
//            Product product = arrayList.get(i);
//
//            try {
//
//                ContentValues values = new ContentValues();
//                values.put(COL_PRODUCT_CODE, product.getProductCode());
//                values.put(COL_PRODUCT_NAME, product.getProductName());
//                values.put(COL_COMPANY_NAME, product.getCompanyName());
//                values.put(COL_PRICE, product.getPrice());
//                values.put(COL_PACKAGE, product.getPackage());
//                values.put(COL_GRADE, product.getGrade());
//                values.put(COL_FORMULA, product.getFormula());
//                values.put(COL_MOLAR_MASS, product.getMolarMass());
//                values.put(COL_PRODUCT_IMG_URL, product.getProductImgUrl());
//                values.put(COL_IS_FAVOURITE, product.isFavourite() ? 1 : 0);
//
//                db.insert(TABLE_PRODUCT, null, values);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//        db.close();
//    }
//
//
//    public ArrayList<Product> getFavouriteProduct() {
//        ArrayList<Product> arrayList = new ArrayList<>();
//
//        try {
//            String query = "Select * from " + TABLE_PRODUCT + " ;";
//            db = this.getReadableDatabase();
//            Cursor cursor = db.rawQuery(query, null);
//
//
//            if (cursor.moveToFirst()) {
//                do {
//                    Product product = new Product();
//
//                    product.setProductId(cursor.getString(cursor.getColumnIndexOrThrow(COL_PRODUCT_ID)));
//                    product.setProductCode(cursor.getString(cursor.getColumnIndexOrThrow(COL_PRODUCT_CODE)));
//                    product.setProductName(cursor.getString(cursor.getColumnIndexOrThrow(COL_PRODUCT_NAME)));
//                    product.setCompanyName(cursor.getString(cursor.getColumnIndexOrThrow(COL_COMPANY_NAME)));
//                    product.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_PRICE)));
//                    product.setPackage(cursor.getString(cursor.getColumnIndexOrThrow(COL_PACKAGE)));
//                    product.setGrade(cursor.getString(cursor.getColumnIndexOrThrow(COL_GRADE)));
//                    product.setFormula(cursor.getString(cursor.getColumnIndexOrThrow(COL_FORMULA)));
//                    product.setMolarMass(cursor.getString(cursor.getColumnIndexOrThrow(COL_MOLAR_MASS)));
//                    product.setProductImgUrl(cursor.getString(cursor.getColumnIndexOrThrow(COL_PRODUCT_IMG_URL)));
//                    product.setFavourite(cursor.getInt(cursor.getColumnIndexOrThrow(COL_IS_FAVOURITE)) > 0);
//
//                    arrayList.add(product);
//
//                } while (cursor.moveToNext());
//            }
//
//            db.close();
//            cursor.close();
//            return arrayList;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return arrayList;
//    }
//
//
//    */
///**
// * Methods For ADS Table
// *//*
//
//
//    // Insert Single Ads
//    public void insertAds(Ads ads) {
//
//        try {
//
//            ContentValues values = new ContentValues();
//            values.put(COL_COMPANY_ID, ads.getCompanyId());
//            values.put(COL_PRODUCT_DSC, ads.getProductDescription());
//            values.put(COL_COMPANY_LOGO_URL, ads.getCompanyLogoUrl());
//            values.put(COL_PRODUCT_NAME, ads.getProductName());
//            values.put(COL_COMPANY_NAME, ads.getCompanyName());
//            values.put(COL_PRODUCT_IMG_URL, ads.getProductImageUrl());
//
//            if (db == null)
//                db = this.getWritableDatabase();
//
//            db.insert(TABLE_ADS, null, values);
//            db.close();
//
//        } catch (Exception e) {
//            db.close();
//            e.printStackTrace();
//        }
//
//    }
//
//
//    // insert Multiple Ads
//    public void insertAds(ArrayList<Ads> arrayList) {
//
//        if (db == null)
//            db = this.getWritableDatabase();
//
//        for (int i = 0; i < arrayList.size(); i++) {
//
//            Ads ads = arrayList.get(i);
//
//            try {
//
//                ContentValues values = new ContentValues();
//                values.put(COL_COMPANY_ID, ads.getCompanyId());
//                values.put(COL_PRODUCT_DSC, ads.getProductDescription());
//                values.put(COL_COMPANY_LOGO_URL, ads.getCompanyLogoUrl());
//                values.put(COL_PRODUCT_NAME, ads.getProductName());
//                values.put(COL_COMPANY_NAME, ads.getCompanyName());
//                values.put(COL_PRODUCT_IMG_URL, ads.getProductImageUrl());
//
//                db.insert(TABLE_ADS, null, values);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//        db.close();
//    }
//*/
//
//
//}
