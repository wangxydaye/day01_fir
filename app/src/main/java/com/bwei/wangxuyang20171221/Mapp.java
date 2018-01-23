package com.bwei.wangxuyang20171221;

import android.app.Application;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

/**
 * Created by wonder on 2017/12/21.
 */

public class Mapp extends Application{
      File cacheFile= new File(Environment.getExternalStorageDirectory()+"/"+"imgages");
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration build = new ImageLoaderConfiguration.Builder(this)
                .diskCache(new UnlimitedDiskCache(cacheFile))
                .build();
        ImageLoader.getInstance().init(build);
    }
}
