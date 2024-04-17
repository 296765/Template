package com.lc.template.utils.picSelect;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;

import com.lc.template.base.CommonAppConfig;
import com.lc.template.base.CommonAppContext;
import com.lc.template.utils.Y;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.util.FileUtils;

import java.io.File;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by cxf on 2018/9/29.
 * 选择图片 裁剪,录视频等
 */

public class MediaUtil {
    private static final String FILE_PROVIDER = "com.lc.template.fileprovider";

    /**
     * 拍照获取图片
     */
    public static void getImageByCamera(FragmentActivity activity, ImageResultCallback imageResultCallback) {
        getImageByCamera(activity, true, imageResultCallback);
    }

    /**
     * 拍照获取图片
     */
    public static void getImageByCamera(final FragmentActivity activity, final boolean needCrop, final ImageResultCallback imageResultCallback) {
        //请求拍照和存储的权限的回调
        PermissionCallback permissionCallback = new PermissionCallback() {
            @Override
            public void onAllGranted() {
                if (imageResultCallback != null) {
                    imageResultCallback.beforeCamera();
                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                final File cameraResult = getNewFile();
                Uri uri = null;
                if (Build.VERSION.SDK_INT >= 24) {
                    uri = FileProvider.getUriForFile(activity, FILE_PROVIDER, cameraResult);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                } else {
                    uri = Uri.fromFile(cameraResult);
                }
                final Uri finalURI = uri;
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                //开始拍照
                ActivityResultUtil.startActivityForResult(activity, intent, new ActivityResultCallback() {
                    @Override
                    public void onSuccess(Intent intent) {
                        if (needCrop) {//需要裁剪
                            if (finalURI != null) {
                                crop(activity, finalURI, imageResultCallback);
                            }
                        } else {
                            yasuu(cameraResult, imageResultCallback);
                        }
                    }

                    @Override
                    public void onFailure() {
                        Y.t("取消拍照");
                    }
                });
            }
        };

        //请求拍照和存储的权限
        PermissionUtil.request(
                activity,
                permissionCallback,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        );
    }

    /**
     * 拍照获取图片
     */
    public static void getShenfenzhengByCamera(final FragmentActivity activity, final ImageResultCallback imageResultCallback) {
        //请求拍照和存储的权限的回调
        PermissionCallback permissionCallback = new PermissionCallback() {
            @Override
            public void onAllGranted() {
                if (imageResultCallback != null) {
                    imageResultCallback.beforeCamera();
                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                final File cameraResult = getNewFile();
                Uri uri = null;
                if (Build.VERSION.SDK_INT >= 24) {
                    uri = FileProvider.getUriForFile(activity, FILE_PROVIDER, cameraResult);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                } else {
                    uri = Uri.fromFile(cameraResult);
                }
                final Uri finalURI = uri;
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                //开始拍照
                ActivityResultUtil.startActivityForResult(activity, intent, new ActivityResultCallback() {
                    @Override
                    public void onSuccess(Intent intent) {
                        if (finalURI != null) {
                            cropShenfenzheng(activity, finalURI, imageResultCallback);
                        }
                    }

                    @Override
                    public void onFailure() {
                        Y.t("取消拍照");
                    }
                });
            }
        };

        //请求拍照和存储的权限
        PermissionUtil.request(
                activity,
                permissionCallback,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        );
    }




    /**
     * 相册获取图片
     */
    public static void getImageByAlumb(FragmentActivity activity, ImageResultCallback imageResultCallback) {
        getImageByAlumb(activity, true, imageResultCallback);
    }

    /**
     * 相册获取图片
     */
    public static void getImageByAlumb(final FragmentActivity activity, final boolean needCrop, final ImageResultCallback imageResultCallback) {
        //请求存储的权限的回调
        PermissionCallback permissionCallback = new PermissionCallback() {
            @Override
            public void onAllGranted() {
                Intent intent = new Intent();
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                if (Build.VERSION.SDK_INT < 19) {
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                } else {
                    intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                }
                ActivityResultUtil.startActivityForResult(activity, intent, new ActivityResultCallback() {
                    @Override
                    public void onSuccess(Intent intent) {
                        Uri dataUri = intent.getData();
                        if (dataUri != null) {
                            if (needCrop) {
                                crop(activity, dataUri, imageResultCallback);
                            } else {
                                if (imageResultCallback != null) {
                                    String path = FileUtils.getPath(activity, dataUri);
                                    if (!TextUtils.isEmpty(path)) {
                                        yasuu(new File(path), imageResultCallback);
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure() {
                        Y.t("取消选择");
                    }
                });

            }
        };

        //请求存储的权限
        PermissionUtil.request(
                activity,
                permissionCallback,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        );
    }

    /**
     * 相册获取图片
     */
    public static void getShenfenzhengByAlumb(final FragmentActivity activity, final ImageResultCallback imageResultCallback) {
        //请求存储的权限的回调
        PermissionCallback permissionCallback = new PermissionCallback() {
            @Override
            public void onAllGranted() {
                Intent intent = new Intent();
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                if (Build.VERSION.SDK_INT < 19) {
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                } else {
                    intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                }
                ActivityResultUtil.startActivityForResult(activity, intent, new ActivityResultCallback() {
                    @Override
                    public void onSuccess(Intent intent) {
                        Uri dataUri = intent.getData();
                        if (dataUri != null) {
                            cropShenfenzheng(activity, dataUri, imageResultCallback);
                        }
                    }

                    @Override
                    public void onFailure() {
                        Y.t("取消选择");
                    }
                });

            }
        };

        //请求存储的权限
        PermissionUtil.request(
                activity,
                permissionCallback,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        );
    }



    private static File getNewFile() {
        // 裁剪头像的绝对路径
        File dir = new File(CommonAppConfig.CAMERA_IMAGE_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return new File(dir, DateFormatUtil.getCurTimeString() + ".png");
    }

    /**
     * 裁剪
     */
    private static void crop(FragmentActivity activity, Uri inputUri, final ImageResultCallback imageResultCallback) {
        final File corpResult = getNewFile();
        try {
            Uri resultUri = Uri.fromFile(corpResult);
            if (resultUri == null) {
                return;
            }
            UCrop uCrop = UCrop.of(inputUri, resultUri)
                    .withAspectRatio(1, 1)
                    .withMaxResultSize(400, 400);
            Intent intent = uCrop.getIntent(activity);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            ActivityResultUtil.startActivityForResult(activity, intent, new ActivityResultCallback() {
                @Override
                public void onSuccess(Intent intent) {
                    if (imageResultCallback != null) {
                        yasuu(corpResult, imageResultCallback);
                    }
                }

                @Override
                public void onFailure() {
                    Y.t("取消裁剪");
                }
            });
        } catch (Exception e) {
            try {
                Uri resultUri = FileProvider.getUriForFile(activity, FILE_PROVIDER, corpResult);
                if (resultUri == null) {
                    return;
                }
                UCrop uCrop = UCrop.of(inputUri, resultUri)
                        .withAspectRatio(1, 1)
                        .withMaxResultSize(400, 400);
                Intent intent = uCrop.getIntent(activity);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                ActivityResultUtil.startActivityForResult(activity, intent, new ActivityResultCallback() {
                    @Override
                    public void onSuccess(Intent intent) {
                        if (imageResultCallback != null) {
                            yasuu(corpResult, imageResultCallback);
                        }
                    }

                    @Override
                    public void onFailure() {
                        Y.t("取消裁剪");
                    }
                });
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 裁剪
     */
    private static void cropShenfenzheng(FragmentActivity activity, Uri inputUri, final ImageResultCallback imageResultCallback) {
        final File corpResult = getNewFile();
        try {
            Uri resultUri = Uri.fromFile(corpResult);
            if (resultUri == null) {
                return;
            }
            UCrop uCrop = UCrop.of(inputUri, resultUri)
                    .withAspectRatio(1.58f, 1)
                    .withMaxResultSize(632, 400);
            Intent intent = uCrop.getIntent(activity);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            ActivityResultUtil.startActivityForResult(activity, intent, new ActivityResultCallback() {
                @Override
                public void onSuccess(Intent intent) {
                    if (imageResultCallback != null) {
                        yasuu(corpResult, imageResultCallback);
                    }
                }

                @Override
                public void onFailure() {
                    Y.t("取消裁剪");
                }
            });
        } catch (Exception e) {
            try {
                Uri resultUri = FileProvider.getUriForFile(activity, FILE_PROVIDER, corpResult);
                if (resultUri == null) {
                    return;
                }
                UCrop uCrop = UCrop.of(inputUri, resultUri)
                        .withAspectRatio(1, 1)
                        .withMaxResultSize(400, 400);
                Intent intent = uCrop.getIntent(activity);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                ActivityResultUtil.startActivityForResult(activity, intent, new ActivityResultCallback() {
                    @Override
                    public void onSuccess(Intent intent) {
                        if (imageResultCallback != null) {
                            yasuu(corpResult, imageResultCallback);
                        }
                    }

                    @Override
                    public void onFailure() {
                        Y.t("取消裁剪");
                    }
                });
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private static void yasuu(final File cameraResult, final ImageResultCallback imageResultCallback) {
        Luban.with(CommonAppContext.getInstance())  //context
                .load(cameraResult)  // 需要压缩的图片file
                .ignoreBy(100)   //压缩率 ，默认100
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI


                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        // 这个不是在主线程，跟新ui ，切换到主线程
                        if (imageResultCallback != null) {
                            imageResultCallback.onSuccess(file);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                    }
                }).launch();
    }
}
