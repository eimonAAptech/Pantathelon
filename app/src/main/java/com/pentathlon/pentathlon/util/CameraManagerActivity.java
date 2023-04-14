package com.pentathlon.pentathlon.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;


import com.pentathlon.pentathlon.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * This Class is used for selecting images from camera and from gallery.<br>
 * USAGE - Extend this class and implement all the methods.<br>
 * It provides Bitmap Image and the bitmap file path.<br>
 * Do mention (android:largeHeap="true") in your manifest application tag.<br><br>
 * CREATED by RANA SAHA RAY on 25-04-2017.
 */

public abstract class CameraManagerActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST_CODE = 300;

    private Uri fileUri = null;

    public static final int MEDIA_TYPE_IMAGE = 10;
    public static final int GALLERY_INTENT_CALLED = 11;
    public static final int CAPTURE_INTENT_CALLED = 13;

    private String mPath = null;

    public static boolean isCamera = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void onCallCameraButton() {
        Log.e("PARENT- ", " onCallCameraButton");
        checkCallpermission();
    }

    File photoFile;

    public void openTheCamera() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        try {
//            someActivityResultLauncher.launch(takePictureIntent);
//
//        } catch (ActivityNotFoundException e) {
//            // display error state to the user
//        }
        try {
            photoFile = createImageFile();

//        if (photoFile != null) {
//            val photoURI = FileProvider.getUriForFile(
//                    this,
//                    "com.example.captureimage.fileprovider",
//                    photoFile
//            );
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(
                    this,
                    "com.pentathlon.pentathlon.provider",
                    photoFile
            ));
            someActivityResultLauncher.launch(takePictureIntent);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
//                         //There are no request codes
//                        Intent data = result.getData();
//                        // doSomeOperations();
//                        Bundle extras = data.getExtras();
//                        Bitmap imageBitmap = (Bitmap) extras.get("data");
//                        // userpic.setImageBitmap(imageBitmap);
//                        File file = savebitmap(imageBitmap);
//                        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image","s", RequestBody.create(MediaType.parse("image/*"), file));
//                        onBitmapReceivedFromCamera(filePart);
                        try {
                            Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                            MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", "s", RequestBody.create(MediaType.parse("image/*"), photoFile));
                            onBitmapReceivedFromCamera(filePart);

                        } catch (Exception e) {
                            Toast.makeText(CameraManagerActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }
                }
            });

    private File savebitmap(Bitmap bmp) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;
        // String temp = null;
        File file = new File(extStorageDirectory, "temp.png");
        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, "temp.png");
        }

        try {
            outStream = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

    private File createImageFile() {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File directory = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        } else {
            directory = Environment.getExternalStorageDirectory();
        }

        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName, /* prefix */
                    ".jpg", /* suffix */
                    directory      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = image.absolutePath
        return image;
    }

    public void openTheGallery() {

//        if (Build.VERSION.SDK_INT < 19) {
//            Intent intent = new Intent();
//            intent.setType("image/*");
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//            startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_INTENT_CALLED);
//        } else {
//            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            intent.setType("image/*");
//            startActivityForResult(intent, GALLERY_INTENT_CALLED);
//        }
        Intent pickIntent = new Intent(Intent.ACTION_GET_CONTENT);
        //  getIntent.setType("image/*");

        //  Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        pickIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

        // Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        //   chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(pickIntent, GALLERY_INTENT_CALLED);
    }

    public File compress(File photoFile) {
        Uri uri = FileProvider.getUriForFile(
                this,
                "com.pentathlon.pentathlon.provider",
                photoFile);
        File compressedFile = null;
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            compressedFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                    .toString() + "/compressed" + photoFile.getName());
            FileOutputStream out = new FileOutputStream(compressedFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, out);
            out.flush();
            out.close();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return compressedFile;

    }

    /*
     * Checks for App Permission
     * (Camera Permission, Write External Storage)
     * */
    private boolean checkCallpermission() {

        if (
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            askCallPermission();
        } else {
            //uploadImageDialog();
            if (isCamera) {
                openTheCamera();
            } else {
                openTheGallery();
            }
        }

        return false;
    }


    /*
     * Ask for App Permission
     * (Camera Permission, Write External Storage)
     * */
    private void askCallPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_REQUEST_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // uploadImageDialog();
                    if (isCamera) {
                        openTheCamera();
                    } else {
                        openTheGallery();
                    }
                } else {

                    if (!ActivityCompat.shouldShowRequestPermissionRationale(CameraManagerActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                        Toast.makeText(this, "Go to app settings to enable storage and camera permission.", Toast.LENGTH_LONG).show();
                    } else {
                        askCallPermission();
                    }
                }
                break;
        }
    }


    /*
     * Dialog options to select camera and gallery
     * */
   /* public void uploadImageDialog() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(CameraManagerActivity.this);
        builder.setTitle("Add Profile Picture!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE, getFileName());
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(intent, CAPTURE_INTENT_CALLED);
                } else if (options[item].equals("Choose from Gallery")) {
                    if (Build.VERSION.SDK_INT < 19) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_INTENT_CALLED);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        intent.setType("image/*");
                        startActivityForResult(intent, GALLERY_INTENT_CALLED);
                    }

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }*/

    /*
     * Receives results from Camera/Gallery
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("", "onActivityResult");

        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(CameraManagerActivity.this, "You have cancelled", Toast.LENGTH_LONG).show();
        } else {
            //AppData.isImaageModified = true;
            Uri selectedImageUri;
            switch (requestCode) {
//                case CAPTURE_INTENT_CALLED:

//                    try {
//
//                        InputStream input = getContentResolver().openInputStream(selectedImageUri);
//                     //   mPath = ImageFilePath.getPath(this, selectedImageUri);
//                      //  mBitmap = getBitmapExifInterface(selectedImageUri, decodeBitmap(input));
//
//                        Log.e("CAMERA_INTENT_CALLED", "" + mPath);
//                       /* InputStream input = getContentResolver().openInputStream(selectedImageUri);
//                        //mPath = ImageFilePath.getPath(this, selectedImageUri);
//                        mPath = selectedImageUri.getPath();
//                        mBitmap = BitmapFactory.decodeFile(mPath);
//                       // mBitmap = getBitmapExifInterface(selectedImageUri, decodeBitmap(input));
//                        Log.e("CAMERA_INTENT_CALLED", "" + mPath);*/
//                        onBitmapReceivedFromCamera(selectedImageUri, mPath);
//                        if (input != null) {
//                            input.close();
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }

//                    Log.e("CAPTURE_INTENT_CALLED", " Uri " + selectedImageUri);
//                    break;

                case GALLERY_INTENT_CALLED:
                    selectedImageUri = fileUri;
                    Bitmap mBitmap;
                    selectedImageUri = data.getData();

                    //ClipData clipData = data.getClipData();

                    if (data.getData() != null) {
                        // Only one image selected
                        Uri uri = data.getData();
                        onBitmapReceivedFromGallery(null, uri);
                    } else if (data.getClipData() != null) {
                        // Multiple images selected
                        ClipData clipData = data.getClipData();
                        onBitmapReceivedFromGallery(clipData, null);

//                        for (int i = 0; i < clipData.getItemCount(); i++) {
//                            Uri uri = clipData.getItemAt(i).getUri();
//                            images.add(uri.toString());
//                        }
//                        imageAdapter.notifyDataSetChanged();
                    }


                  //  Log.e("GALLERY_INTENT_CALLED", " Uri " + clipData.getItemCount());
                    try {
//                        InputStream input = getContentResolver().openInputStream(selectedImageUri);
//                        // mPath = ImageFilePath.getPath(this, selectedImageUri);
//                        mBitmap = getBitmapExifInterface(selectedImageUri, decodeBitmap(input));
//                        File file = savebitmap(mBitmap);


//                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                        Cursor cursor = getContentResolver().query(selectedImageUri,
//                                filePathColumn, null, null, null);
//                        cursor.moveToFirst();
//
//                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                        String picturePath = cursor.getString(columnIndex);
//                        File file = new File(picturePath);
//                        cursor.close();
//
//                        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
                        // onBitmapReceivedFromCamera(filePart);


//                        if (input != null) {
//                            input.close();
//                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

            }

        }
    }


    /**
     * Check & Returns the correct oriented Bitmap Image
     */
    private Bitmap getBitmapExifInterface(Uri selectedImageUri, Bitmap bmp) {
        /////for rotation of image
        ExifInterface exif;
        int rotation;
        try {
            mPath = ImageFilePath.getPath(this, selectedImageUri);
            // mPath = selectedImageUri.getPath();// "file:///mnt/sdcard/FileName.mp3"
            Log.e("PARENT- ", " Path: " + mPath);
            exif = new ExifInterface(mPath);
            rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            bmp = getRotatedBitmap(rotation, bmp);

            Log.e("PARENT- ", " Exif Rotation: " + rotation);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;
    }


    /**
     * For checking bitmap image orientation and returns the rotated bitmap.
     */
    public Bitmap getRotatedBitmap(int rotation, Bitmap bmp) {

        Matrix matrix = new Matrix();

        switch (rotation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bmp;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(270);
                break;
            case ExifInterface.ORIENTATION_UNDEFINED:
                return bmp;
            default:
                return bmp;
        }

        try {
            Bitmap bmRotated = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
            bmp.recycle();
            return bmRotated;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * Returns the scaled bitmap (by calculating the sample size)
     */
    private Bitmap decodeBitmap(InputStream inputStream) {

        Bitmap bitmap = null;
        InputStream is1 = null, is2 = null;

        try {
            Log.e("PARENT- ", "BITMAP: InputStream- " + inputStream.toString());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            // Fake code simulating the copy
            // You can generally do better with nio if you need...
            // And please, unlike me, do something about the Exceptions :D
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();

            // Open new InputStreams using the recorded bytes
            // Can be repeated as many times as you wish
            is1 = new ByteArrayInputStream(baos.toByteArray());
            is2 = new ByteArrayInputStream(baos.toByteArray());

            /*DecodeBitmap before sampling*/
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inDither = true;
            BitmapFactory.decodeStream(is1, null, options);
            int imageHeight = options.outHeight;
            int imageWidth = options.outWidth;


            /*Calculate screen height & width*/
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
         /*   int reqHeight = displayMetrics.heightPixels / 2;
             int reqwidth = displayMetrics.widthPixels / 2;*/

            int reqHeight, reqwidth;
           /* if (imageWidth > 500 && imageWidth > imageHeight) {
                reqHeight= (500 * imageHeight) / imageWidth;
                reqwidth = 500;
            } else if (imageWidth > 500) {
                reqHeight = (imageHeight / imageWidth) * 500;
                reqwidth = 500;
            } else {
                reqHeight = imageHeight;
                reqwidth = imageWidth;
            }*/
            if (imageWidth > 500) {
                reqHeight = (500 * displayMetrics.heightPixels) / displayMetrics.widthPixels;
                reqwidth = 500;
            } else {
                reqHeight = imageHeight;
                reqwidth = imageWidth;
            }

            Log.e("SUDIPTA ", "BITMAP: width- " + imageWidth + " height- " + imageHeight +
                    " reqheight- " + reqHeight + " reqWidth- " + reqwidth);

            options.inSampleSize = calculateInSampleSize(options, reqwidth, reqHeight);

            Log.e("SUDIPTA ", "BITMAP: SampleSize- " + options.inSampleSize + " InputStream- " + inputStream.toString());
            // Decode bitmap with inSampleSize set (DecodeSampledBitmap)
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inDither = true;
            bitmap = BitmapFactory.decodeStream(is2, null, options);
            if (imageWidth > 510) {
                bitmap = getResizedBitmap(bitmap, reqwidth, reqHeight);
            }
            if (bitmap != null) {
                Log.e("SUDIPTA ", "BITMAP: height- " + bitmap.getHeight() + " width- " + bitmap.getWidth());
            } else {
                Log.e("SUDIPTA ", "BITMAP: is Null.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (is1 != null) {
                    is1.close();
                }
                if (is2 != null) {
                    is2.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bitmap;
    }
    public Bitmap decodeSampledBitmapFromUri(String uri, int reqWidth, int reqHeight) {
        Bitmap bitmap = null;
        try {
            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(uri, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(uri, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    /*
     * Calculating sample size of the bitmap
     * */
    public int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    /**
     * Abstract method to pass the bitmap and path<br>
     * when image captured from camera
     */
    protected abstract void onBitmapReceivedFromCamera(MultipartBody.Part filePart);

    /**
     * Abstract method to pass the bitmap and path<br>
     * when image selected from gallery
     */
    protected abstract void onBitmapReceivedFromGallery(ClipData data, Uri uri);


    /*---------------------------------------------------------------------------------------------*/
    /*-- Below are the methods for creating Image file, file name when Image capture from camera --*/
    /*---------------------------------------------------------------------------------------------*/

    /*
     * Creates file name for the image to be captured
     * */
    public String getFileName() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyy_hhmmss");
        return simpleDateFormat.format(new Date());
    }


    /**
     * Create a file Uri for saving an image or video
     */
    public Uri getOutputMediaFileUri(int type, String name) {
        // return Uri.fromFile(getOutputMediaFile(type, name));
        return FileProvider.getUriForFile(this, "com.electrical_larning.electrical_e_learning.provider", getOutputMediaFile(type, name));
    }


    /**
     * Create a File for saving an image or video
     */
    public File getOutputMediaFile(int type, String fName) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        /*File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Stock_Up");*/
        // File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), getResources().getString(R.string.app_name));
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), getResources().getString(R.string.app_name));
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.e("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + fName + ".jpg");
        } /*else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        }*/ else {
            return null;
        }

        return mediaFile;
    }

    /*------------------------------------- Ends here --------------------------------------------*/


    /**
     * - Class is used to get Absolute Path from file -<br>
     * Irrespective of any android build version.
     */
    private static class ImageFilePath {

        /**
         * Method for return file path of Gallery image
         *
         * @param context
         * @param uri
         * @return path of the selected image file from gallery
         */
        static String nopath = "Select Image Only";

        @SuppressLint("NewApi")
        static String getPath(final Context context, final Uri uri) {

            // check here to KITKAT or new version
            final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

            // DocumentProvider
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/"
                                + split[1];
                    }
                }
                // DownloadsProvider
                else if (isDownloadsDocument(uri)) {

                    final String id = getDocumentIdRaw(DocumentsContract.getDocumentId(uri));
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));


                    return getDataColumn(context, contentUri, null, null);

                }
                // DrivesProvider
                else if (isDriveDocument(uri)) {

                    // Return the remote address
                    if (isDriveDocument(uri))
                        return getDriveFilePath(uri, context);
                    return getDataColumn(context, uri, null, null);
                }

                // MediaProvider
                else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{split[1]};

                    return getDataColumn(context, contentUri, selection,
                            selectionArgs);
                }
            }
            // MediaStore (and general)
            else if ("content".equalsIgnoreCase(uri.getScheme())) {

                // Return the remote address
                if (isGooglePhotosUri(uri))
                    return uri.getLastPathSegment();
                return getDataColumn(context, uri, null, null);
            }
            // File
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }

            return nopath;
        }

        private static String getDocumentIdRaw(String id) {
            if (!TextUtils.isEmpty(id)) {
                if (id.startsWith("raw:")) {
                    return id.replaceFirst("raw:", "");
                }

            }
            return id;
        }

        /**
         * Get the value of the data column for this Uri. This is <span id="IL_AD2"
         * class="IL_AD">useful</span> for MediaStore Uris, and other file-based
         * ContentProviders.
         *
         * @param context       The context.
         * @param uri           The Uri to query.
         * @param selection     (Optional) Filter used in the query.
         * @param selectionArgs (Optional) Selection arguments used in the query.
         * @return The value of the _data column, which is typically a file path.
         */
        static String getDataColumn(Context context, Uri uri,
                                    String selection, String[] selectionArgs) {

            Cursor cursor = null;
            final String column = "_data";
            final String[] projection = {column};

            try {
                cursor = context.getContentResolver().query(uri, projection,
                        selection, selectionArgs, null);
                if (cursor != null && cursor.moveToFirst()) {
                    final int index = cursor.getColumnIndexOrThrow(column);
                    return cursor.getString(index);
                }
            } finally {
                if (cursor != null)
                    cursor.close();
            }

           /* Cursor cursor = null;//**strong text**
            final String column = "_data";
            final String[] projection = {
                    column
            };
            FileInputStream input = null;
            FileOutputStream output = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
                if (cursor != null && cursor.moveToFirst()) {
                    final int index = cursor.getColumnIndexOrThrow(column);
                    return cursor.getString(index);
                }
            } catch (IllegalArgumentException e){
                e.printStackTrace();

                File file = new File(context.getCacheDir(), "tmp");
                String filePath = file.getAbsolutePath();

                try {
                    ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
                    if (pfd == null)
                        return null;

                    FileDescriptor fd = pfd.getFileDescriptor();
                    input = new FileInputStream(fd);
                    output = new FileOutputStream(filePath);
                    int read;
                    byte[] bytes = new byte[4096];
                    while ((read = input.read(bytes)) != -1) {
                        output.write(bytes, 0, read);
                    }

                    input.close();
                    output.close();
                    return new File(filePath).getAbsolutePath();
                } catch (IOException ignored) {
                    ignored.printStackTrace();
                }
            } finally{
                if (cursor != null)
                    cursor.close();
            }*/
            // return null;
            return nopath;
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is ExternalStorageProvider.
         */
        static boolean isExternalStorageDocument(Uri uri) {
            return "com.android.externalstorage.documents".equals(uri
                    .getAuthority());
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is DownloadsProvider.
         */
        static boolean isDownloadsDocument(Uri uri) {
            return "com.android.providers.downloads.documents".equals(uri
                    .getAuthority());
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is MediaProvider.
         */
        static boolean isMediaDocument(Uri uri) {
            return "com.android.providers.media.documents".equals(uri
                    .getAuthority());
        }

        static boolean isDriveDocument(Uri uri) {
            return "com.google.android.apps.docs.storage".equals(uri
                    .getAuthority()) || "com.google.android.apps.docs.storage.legacy".equals(uri.getAuthority());

        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is Google Photos.
         */
        static boolean isGooglePhotosUri(Uri uri) {
            return "com.google.android.apps.photos.content".equals(uri
                    .getAuthority());
        }
    }

    private static String getDriveFilePath(Uri uri, Context context) {
        Uri returnUri = uri;
        Cursor returnCursor = context.getContentResolver().query(returnUri, null, null, null, null);
        /*
         * Get the column indexes of the data in the Cursor,
         *     * move to the first row in the Cursor, get the data,
         *     * and display it.
         * */
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        String name = (returnCursor.getString(nameIndex));
        String size = (Long.toString(returnCursor.getLong(sizeIndex)));
        File file = new File(context.getCacheDir(), name);
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(file);
            int read = 0;
            int maxBufferSize = 1024 * 1024;
            int bytesAvailable = inputStream.available();

            //int bufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }
            Log.e("File Size", "Size " + file.length());
            inputStream.close();
            outputStream.close();
            Log.e("File Path", "Path " + file.getPath());
            Log.e("File Size", "Size " + file.length());
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
        return file.getPath();
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
       /* int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;*/
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = 1;

        scaleFactor = (int) photoW / newWidth;
        if (scaleFactor == 1) {
            scaleFactor = 2;
        }

        /*if ((newWidth > 0) || (newHeight > 0)) {
            //scaleFactor = Math.min(photoW/newWidth, photoH/newHeight);
        }*/

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true; //Deprecated API 21

        return BitmapFactory.decodeFile(mPath, bmOptions);
    }
}
